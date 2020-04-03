package com.company;

import java.util.Observable;
import java.util.Observer;

public class CollisionHandler implements Observer {


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CollisionDetector) {
            if (arg instanceof Triangle) {
                Triangle triangle = (Triangle) arg;
                triangle.executeElectricityReaction();
            }
            if (arg instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) arg;
                rectangle.executeElectricityReaction();
            }
        }
    }
}
