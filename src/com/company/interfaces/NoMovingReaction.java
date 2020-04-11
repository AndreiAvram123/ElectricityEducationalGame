package com.company.interfaces;

public class NoMovingReaction implements IPlayerReaction {

    @Override
    public void execute() {
        System.out.println("Doing nothing...");
    }
}
