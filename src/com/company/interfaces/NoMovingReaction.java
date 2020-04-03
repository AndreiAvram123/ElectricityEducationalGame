package com.company.interfaces;

public class NoMovingReaction implements IElectricityReaction {

    @Override
    public void execute() {
        System.out.println("Doing nothing...");
    }
}
