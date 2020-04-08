package com.company.models;

import javafx.scene.canvas.GraphicsContext;

public class ElectricitySource extends GameObject {

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
}
