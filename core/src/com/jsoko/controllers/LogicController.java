package com.jsoko.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.jsoko.Move;
import com.jsoko.MoveEntry;
import com.jsoko.World;
import com.jsoko.interfaces.Game;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.GameObjects;
import com.jsoko.objects.gameobjects.Crate;
import com.jsoko.objects.gameobjects.Place;
import com.jsoko.objects.gameobjects.Sokoban;

public class LogicController implements Disposable {

    private final MoveHistoryController moveHistoryController;
    private final World world;

    public LogicController(Game game) {
        this.world = game.getWorld();
        this.moveHistoryController = new MoveHistoryController();
    }

    public boolean canUndo() {
        return moveHistoryController.hasOne();
    }

    public void undoMove() {
        MoveEntry entry = moveHistoryController.getOne();

        if (entry != null) {
            Sokoban sokoban = (Sokoban) entry.leftObject;
            Crate crate = (Crate) entry.rightObject;
            Move invertedMove = Move.invert(entry.move);

            sokoban.move(invertedMove);

            if (crate != null) {
                crate.move(invertedMove);
                Place place = world.getObjectTypeAt(Place.class, crate.getPosition());
                crate.bindPlace(place);
            }
        }
    }

    public void process(Move move) {
        Sokoban sokoban = world.getPlayer();
        Vector2 moveVector = move.get();

        Vector2 nextObjectPosition = sokoban.getPosition().add(moveVector);
        GameObject nextObject = world.getObjectAt(nextObjectPosition);

        switch (nextObject.getType()) {
            case FLOOR:
            case NULL:
                moveSokoban(sokoban, move);
                break;
            case PLACE:
                checkCratePresentAndMove(sokoban, (Place) nextObject, move);
                break;
            case CRATE:
                tryMoveSokobanAndCrate(sokoban, (Crate) nextObject, move);
                break;
        }
    }

    private void moveSokoban(Sokoban sokoban, Move move) {
        sokoban.move(move);

        MoveEntry entry = MoveEntry.create(sokoban, null, move);
        moveHistoryController.add(entry);
    }

    private void checkCratePresentAndMove(Sokoban sokoban, Place place, Move move) {
        if (place.haveCrate()) {
            Crate crate = place.getCrate();
            tryMoveSokobanAndCrate(sokoban, crate, move);
        } else {
            moveSokoban(sokoban, move);
        }
    }

    private void tryMoveSokobanAndCrate(Sokoban sokoban, Crate crate, Move move) {
        Vector2 moveVector = move.get();
        Vector2 nextObjectPosition = crate.getPosition().add(moveVector);
        GameObject nextObject = world.getObjectAt(nextObjectPosition);

        if (nextObject.isType(GameObjects.NULL) || nextObject.isType(GameObjects.FLOOR)) {
            moveSokobanAndCrate(sokoban, crate, null, move);
        } else if (nextObject.isType(GameObjects.PLACE)) {
            Place place = (Place) nextObject;
            if (!place.haveCrate()) {
                moveSokobanAndCrate(sokoban, crate, place, move);
            }
        }
    }

    private void moveSokobanAndCrate(Sokoban sokoban, Crate crate, Place place, Move move) {
        sokoban.move(move);
        crate.move(move);
        crate.bindPlace(place);

        MoveEntry entry = MoveEntry.create(sokoban, crate, move);
        moveHistoryController.add(entry);
    }


    @Override
    public void dispose() {
        moveHistoryController.dispose();
    }
}
