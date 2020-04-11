package com.company.models;

import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wind extends EnergyFactor implements HintOnHover {
    private static final String HINT = "Wind can be used to power up a wind turbine";


    public Wind(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/images/wind.png");
    }

    @Override
    public boolean isPositionValidToGiveEnergy(ElectricitySource electricitySource) {
        return this.isNeighbourLeft(electricitySource) || this.isNeighbourRight(electricitySource);
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
