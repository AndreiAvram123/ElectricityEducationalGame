package com.company.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends ReactiveObject {

    public Triangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        setUnderElectricity(true);
     }

    @Override
    public void update() {
        gc.setFill(Color.GREY);
        gc.fillPolygon(new double[]{x, x, x + 50}, new double[]{y, y + 50, y + 50}, 3);
    }
}
