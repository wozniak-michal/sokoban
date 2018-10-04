package com.jsoko.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jsoko.Assets;
import com.jsoko.Constants;
import com.jsoko.Move;
import com.jsoko.objects.GameObject;
import com.jsoko.objects.GameObjects;

public class Sokoban extends GameObject {

    private enum State {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT,
        IDLE,
    }

    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private float animationStateTime = 0.f;

    private final Vector2 targetPosition = new Vector2();
    private State currentState = State.IDLE;

    public Sokoban(float x, float y) {
        super(null, x, y);
        this.type = GameObjects.SOKOBAN;

        loadAnimations();

        targetPosition.set(x, y);
    }

    private void loadAnimations() {
        walkUpAnimation = Assets.getAnimation(Constants.SOKOBAN_WALK_UP_ANIMATION_ID);
        walkDownAnimation = Assets.getAnimation(Constants.SOKOBAN_WALK_DOWN_ANIMATION_ID);
        walkLeftAnimation = Assets.getAnimation(Constants.SOKOBAN_WALK_LEFT_ANIMATION_ID);
        walkRightAnimation = Assets.getAnimation(Constants.SOKOBAN_WALK_RIGHT_ANIMATION_ID);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(targetPosition);
    }

    public void move(Move move) {
        switch (move) {
            case UP:
                currentState = State.MOVE_UP;
                break;
            case DOWN:
                currentState = State.MOVE_DOWN;
                break;
            case LEFT:
                currentState = State.MOVE_LEFT;
                break;
            case RIGHT:
                currentState = State.MOVE_RIGHT;
                break;
        }
        targetPosition.add(move.get());
    }

    @Override
    public void update(float delta) {
        animationStateTime += delta;
        texture = getNextFrame(animationStateTime);
        updateMovement(delta);
    }

    private void updateMovement(float delta) {
        if (currentState != State.IDLE) {
            float progress = delta / Constants.MOVING_SPEED;
            position.lerp(targetPosition, progress);

            if (position.dst(targetPosition) < 1.0f) {
                currentState = State.IDLE;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        walkDownAnimation = null;
        walkLeftAnimation = null;
        walkRightAnimation = null;
        walkUpAnimation = null;
    }

    private TextureRegion getNextFrame(float time) {
        switch (currentState) {
            case MOVE_UP:
                return walkUpAnimation.getKeyFrame(time);

            case MOVE_DOWN:
                return walkDownAnimation.getKeyFrame(time);

            case MOVE_LEFT:
                return walkLeftAnimation.getKeyFrame(time);

            case MOVE_RIGHT:
                return walkRightAnimation.getKeyFrame(time);

            default:
            case IDLE:
                return walkDownAnimation.getKeyFrame(0);
        }
    }
}
