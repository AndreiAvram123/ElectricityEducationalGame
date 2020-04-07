package com.company;

import com.company.models.Finish;
import com.company.models.Rectangle;
import com.company.models.Triangle;

import java.util.Observable;
import java.util.Observer;

public class CollisionHandler implements Observer {
    private boolean isLevelCompleted = false;

    @Override
    public void update(Observable o, Object collider) {
        if (o instanceof CollisionDetector) {
            if (collider instanceof Triangle) {
                Triangle triangle = (Triangle) collider;
                triangle.executeElectricityReaction();
            }
            if (collider instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) collider;
                rectangle.executeElectricityReaction();
            }
            if(collider instanceof Finish){
                isLevelCompleted = true;
            }
        }
    }

    public boolean isLevelCompleted() {
        return isLevelCompleted;
    }
}
