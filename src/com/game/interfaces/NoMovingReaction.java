package com.game.interfaces;

public class NoMovingReaction implements IPlayerReaction {

    @Override
    public void execute() {
        System.out.println("Doing nothing...");
    }
}
