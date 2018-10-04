package com.jsoko.controllers;

import com.badlogic.gdx.utils.Disposable;
import com.jsoko.Assets;
import com.jsoko.MoveEntry;

import java.util.Stack;

public class MoveHistoryController implements Disposable {

    private final Stack<MoveEntry> moveHistory;
    private int maxUndoMoves;
    private int undosAvailable;

    public MoveHistoryController() {
        this.moveHistory = new Stack<>();
        this.maxUndoMoves = Assets.getSettings().getMaxUndoMoves();
    }

    public void add(MoveEntry entry) {
        if (undosAvailable > 0) {
            undosAvailable--;
        }
        moveHistory.push(entry);
    }

    public boolean hasOne() {
        return !moveHistory.empty() && (undosAvailable < maxUndoMoves);
    }

    public MoveEntry getOne() {
        if (hasOne()) {
            undosAvailable++;
            return moveHistory.pop();
        }
        return null;
    }

    @Override
    public void dispose() {
        moveHistory.clear();
    }
}
