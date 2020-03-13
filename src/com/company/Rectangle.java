package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends GameObject {
    private Color color = Color.ALICEBLUE;

    public Rectangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);

    }


    @Override
    public void update() {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);

    }
}
