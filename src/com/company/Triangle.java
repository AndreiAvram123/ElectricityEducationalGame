package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;

public class Triangle extends GameObject {

    public Triangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }

    @Override
    public void update() {
        gc.fillPolygon(new double[]{x, x, x + 50}, new double[]{y, y + 50, y + 50}, 3);

    }
}
