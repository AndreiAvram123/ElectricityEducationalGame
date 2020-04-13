package com.game.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wind extends EnergyFactor {
    private static final String HINT = "Wind can be used to power up a wind turbine";


    public Wind(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("images/wind.png");
    }

    @Override
    public boolean isPositionValidToGiveEnergy(ElectricitySource electricitySource) {
        return this.isNeighbourLeftWith(electricitySource) || this.isNeighbourRightWith(electricitySource);
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
