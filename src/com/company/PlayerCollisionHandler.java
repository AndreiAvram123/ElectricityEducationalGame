package com.company;

import com.company.models.*;

import java.util.Observable;
import java.util.Observer;

public class PlayerCollisionHandler {
    private boolean isLevelCompleted = false;


    public void handleCollision(Collision collision) {

        ObjectOnScreen objectCollided = collision.getObjectCollided();

        if (objectCollided instanceof Finish && ((Finish) objectCollided).getFinishPlayerSide()
                == collision.getCollisionSidePlayer()) {
            isLevelCompleted = true;
        }

        if (objectCollided instanceof ReactiveObject) {

            ((ReactiveObject) objectCollided).executeElectricityReaction(collision.getCollisionSidePlayer());
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
