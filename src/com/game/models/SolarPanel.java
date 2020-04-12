package com.game.models;

import com.game.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SolarPanel extends ElectricitySource implements HintOnHover {
    private static final String HINT = "A solar panel can produce electricity. All you need is the sun.";
    private static final String poweredBy = Sun.class.getSimpleName();

    public SolarPanel(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("images/solar_panel.png");
    }

    @Override
    public boolean isPoweredBy(EnergyFactor energyFactor) {
        return energyFactor.getClass().getSimpleName().equals(poweredBy);
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
