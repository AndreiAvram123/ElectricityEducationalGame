package com.company.collision;

import com.company.models.Sides;
import com.company.models.*;

public class PlayerCollisionHandler {
    private boolean isLevelCompleted = false;


    public void handleCollision(Collision collision) {
        System.out.println();
        ObjectOnScreen objectCollided = collision.getCollisionObject();

        if (objectCollided instanceof Finish && ((Finish) objectCollided).getFinishPlayerSide()
                == collision.getCollisionSidePlayer()) {
            isLevelCompleted = true;
        }

        if (objectCollided instanceof ElectricObject) {

            ((ElectricObject) objectCollided).executeElectricityReaction(collision.getCollisionSidePlayer());
        }
        if (objectCollided instanceof Slope && collision.getCollisionSidePlayer() == Sides.BOTTOM) {
            collision.getPlayer().moveOnX(50);
            collision.getPlayer().moveOnY(50);
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
