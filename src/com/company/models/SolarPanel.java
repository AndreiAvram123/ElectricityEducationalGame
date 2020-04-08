package com.company.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SolarPanel extends ElectricitySource {

    public SolarPanel(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/solar_panel.png");
    }

}
