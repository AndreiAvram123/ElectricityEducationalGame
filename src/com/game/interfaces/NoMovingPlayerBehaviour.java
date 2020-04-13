package com.game.interfaces;

public class NoMovingPlayerBehaviour implements IPlayerReaction {

    @Override
    public void execute() {
        System.out.println("Doing nothing...");
    }
}
