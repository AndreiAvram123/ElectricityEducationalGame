package com.company.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//todo
//triangle should be a slope
public class Slope extends ReactiveObject {

    public Slope(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        setUnderElectricity(true);
    }

    @Override
    public void update() {
        gc.setFill(Color.GREY);
        gc.fillPolygon(new double[]{x, x, x + 50}, new double[]{y, y + 50, y + 50}, 3);
    }
}
