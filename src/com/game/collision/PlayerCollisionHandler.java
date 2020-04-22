package com.game.collision;


import com.game.models.ElectricObject;
import com.game.models.Finish;
import com.game.models.ScreenObject;
import com.game.models.Slope;
import com.sun.istack.internal.NotNull;

public class PlayerCollisionHandler {
    private boolean isLevelCompleted = false;


    public void handleCollision(@NotNull Collision collision) {

        ScreenObject objectCollided = collision.getCollisionObject();

        if (objectCollided instanceof Finish) {
            isLevelCompleted = true;
        }

        if (objectCollided instanceof ElectricObject) {

            ((ElectricObject) objectCollided).executePlayerReaction(collision.getCollisionSidePlayer());
        }
        if (objectCollided instanceof Slope) {
            ((Slope) objectCollided).executeReaction(collision.getCollisionSidePlayer());
        }


    }

    /**
     * Returns true weather the level is completed
     * A level is completed once the player collided with the finish point
     *
     * @return
     */
    public boolean hasCollidedWithFinish() {
        return isLevelCompleted;
    }

    public void reset() {
        isLevelCompleted = false;
    }
}
