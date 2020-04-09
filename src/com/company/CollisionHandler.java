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
            System.out.println(collision.getCollisionSidePlayer().toString());
            ObjectOnScreen objectCollided = collision.getObjectCollided();

            if (objectCollided instanceof Finish && ((Finish) objectCollided).getFinishPlayerSide()
                    == collision.getCollisionSidePlayer()) {
                isLevelCompleted = true;
            }

            if (objectCollided instanceof ReactiveObject) {

                ((ReactiveObject) objectCollided).executeElectricityReaction(collision.getCollisionSidePlayer());
            }
        }

    }

    public boolean isLevelCompleted() {
        return isLevelCompleted;
    }
}
