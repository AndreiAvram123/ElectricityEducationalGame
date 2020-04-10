package com.company;

import com.company.models.ObjectOnScreen;
import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerCollisionDetector {


    private LevelModel levelModel;
    private Player player;
    private PlayerCollisionHandler playerCollisionHandler = new PlayerCollisionHandler();
    private boolean started = false;

    public void setLevelModel(@NotNull LevelModel levelModel) {
        this.levelModel = levelModel;
        player = levelModel.getPlayer();
        playerCollisionHandler.reset();
    }

    public void start(){
        started = true;
    }
    public void stop(){
        started = false;
    }

    public void checkCollisionWithPlayer() {
        if (started && levelModel != null) {
            //we should not check collision while the player is in the moving state
            if (!player.isMoving()) {
                //we enable gravity only if the player has not collided with an
                //object at the bottom
                boolean enableGravity = true;
                for (ObjectOnScreen objectOnScreen : levelModel.getObjectsOnGameScreen()) {
                    Sides collisionSide = getCollisionSide(objectOnScreen);
                    if (collisionSide == Sides.BOTTOM) {
                        enableGravity = false;
                    }
                    Collision collision = new Collision(objectOnScreen, player, collisionSide);
                    playerCollisionHandler.handleCollision(collision);
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
    private Sides getCollisionSide(@NotNull ObjectOnScreen objectOnScreen) {
        if (player.isNeighbourTop(objectOnScreen)) {
            return Sides.BOTTOM;
        }
        if (player.isNeighbourBottom(objectOnScreen)) {
            return Sides.TOP;
        }
        if (player.isNeighbourLeft(objectOnScreen)) {
            return Sides.RIGHT;
        }
        if (player.isNeighbourRight(objectOnScreen)) {
            return Sides.LEFT;
        }
        return Sides.NONE;
    }

    public boolean hasCollidedWithFinish() {
        return playerCollisionHandler.hasCollidedWithFinish();
    }
}
