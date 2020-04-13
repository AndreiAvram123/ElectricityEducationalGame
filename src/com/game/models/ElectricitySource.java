package com.game.models;

import javafx.scene.canvas.GraphicsContext;

public abstract class ElectricitySource extends ScreenObject {

    private boolean isElectricityEnabled = false;

    public ElectricitySource(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }


    public void setElectricityEnabled(boolean electricityEnabled) {
        isElectricityEnabled = electricityEnabled;
    }

    public boolean isElectricityEnabled() {
        return isElectricityEnabled;
    }

    public abstract boolean isPoweredBy(EnergyFactor energyFactor);
}
