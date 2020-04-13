package com.game.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sun extends EnergyFactor  {
    private static final String HINT = "Without it our lives would not exist. Great in combination with a solar panel to produce green energy";

    public Sun(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("images/sun.png");
    }

    @Override
    public boolean isPositionValidToGiveEnergy(ElectricitySource electricitySource) {
        return this.isNeighbourTopWith(electricitySource);
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
