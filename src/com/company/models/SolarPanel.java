package com.company.models;

import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SolarPanel extends ElectricitySource implements HintOnHover {
    private static final String HINT = "A solar panel can produce electricity. All you need is the sun.";

    public SolarPanel(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/solar_panel.png");
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
