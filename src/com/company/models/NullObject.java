package com.company.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NullObject extends ObjectOnScreen {

    public NullObject(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        setHasDragEnabled(false);
    }

    @Override
    public void update() {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(x, y, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, width, height);
    }
}
