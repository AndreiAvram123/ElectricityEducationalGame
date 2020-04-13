package com.game.collision;

import com.game.models.LevelModel;
import com.game.models.Player;
import com.game.models.ScreenObject;
import com.game.models.Sides;
import org.jetbrains.annotations.NotNull;

public class PlayerCollisionDetector {


    private LevelModel levelModel;
    private Player player;
    private final PlayerCollisionHandler playerCollisionHandler = new PlayerCollisionHandler();
    private boolean started = false;

    public void setLevelModel(@NotNull LevelModel levelModel) {
        this.levelModel = levelModel;
        player = levelModel.getPlayer();
        playerCollisionHandler.reset();
    }

    public void start() {
        started = true;
    }

    public void stop() {
        started = false;
    }

    public void checkCollisionWithPlayer() {
        if (started && levelModel != null) {
            //we should not check collision while the player is in the moving state
            if (!player.isMoving()) {
                //we enable gravity only if the player has not collided with an
                //object at the bottom
                boolean enableGravity = true;
                for (ScreenObject screenObject : levelModel.getObjectsOnGameScreen()) {
                    Sides collisionSide = getCollisionSide(screenObject);
                    if (collisionSide == Sides.BOTTOM) {
                        enableGravity = false;
                    }
                    if (collisionSide != Sides.NONE) {
                        Collision collision = new Collision(screenObject, player, collisionSide);
                        playerCollisionHandler.handleCollision(collision);
                    }
                }
                player.setGravityEnabled(enableGravity);
            }
        }
    }


    /**
     * This method return the collision side on witch the player has collided
     * if the player is on top of the game object then the method will return  Sides.Bottom
     * if the player if left of the game object then the method will return Sides.Right
     *
     * @return
     */
    private Sides getCollisionSide(@NotNull ScreenObject screenObject) {
        if (player.isNeighbourTopWith(screenObject)) {
            return Sides.BOTTOM;
        }
        if (player.isNeighbourBottomWith(screenObject)) {
            return Sides.TOP;
        }
        if (player.isNeighbourLeftWith(screenObject)) {
            return Sides.RIGHT;
        }
        if (player.isNeighbourRightWith(screenObject)) {
            return Sides.LEFT;
        }
        return Sides.NONE;
    }

    public boolean hasCollidedWithFinish() {
        return playerCollisionHandler.hasCollidedWithFinish();
    }
}
