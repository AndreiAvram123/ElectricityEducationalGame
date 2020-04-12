package com.game.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Slope extends ObjectOnScreen {

    public Slope(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }


    @Override
    public void update() {
        gc.setFill(Color.GREY);
        gc.fillPolygon(new double[]{x, x, x + 50}, new double[]{y, y + 50, y + 50}, 3);
    }
}
