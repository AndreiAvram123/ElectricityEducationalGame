package com.game.models;

import javafx.scene.canvas.GraphicsContext;

/**
 * Objects of this type allow Electricity Sources to
 * start producing electricity
 */
public abstract class EnergyFactor extends ScreenObject {

    public EnergyFactor(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }
    public abstract boolean isPositionValidToGiveEnergy(ElectricitySource electricitySource);
}
