package com.company;

import java.util.Observable;
import java.util.Observer;

public class CollisionHandler implements Observer {

    private Player player;

    public CollisionHandler(Player player) {
        this.player = player;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CollisionDetector) {
            if (arg instanceof Triangle) {
                if (!player.isInMovingState()) {
                    player.move(50, 50);
                }
            }
            if(arg instanceof WindTurbine){
                if(!player.isInMovingState()){
                    //todo
                    //add a property to the wind tubine
                    player.move(0,-300);
                }
            }
        }
    }
}
