package com.company;

import com.company.models.*;

import java.util.Observable;
import java.util.Observer;

public class CollisionHandler implements Observer {
    private boolean isLevelCompleted = false;

    @Override
    public void update(Observable o, Object collisionParam) {
        Collision collision = (Collision) collisionParam;
        if (o instanceof PlayerCollisionDetector) {

            ObjectOnScreen objectCollided = collision.getObjectCollided();

            if (objectCollided instanceof Finish) {
                isLevelCompleted = true;
            }

            if (objectCollided instanceof ReactiveObject) {
                if (objectCollided instanceof Rotating) {
                    if (((Rotating) objectCollided).getPlayerCollisionSideForReaction() == collision.getCollisionSidePlayer()) {
                        ((ReactiveObject) objectCollided).executeElectricityReaction();
                    }
                } else {
                    if (collision.getCollisionSidePlayer() == Sides.BOTTOM) {
                        ((ReactiveObject) objectCollided).executeElectricityReaction();
                    }
                }
            }
        }

    }

    public boolean isLevelCompleted() {
        return isLevelCompleted;
    }
}
