package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WindTurbine extends GameObject {
    private double windForce;

    public WindTurbine(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/windTurbine.jpg");
    }


}
