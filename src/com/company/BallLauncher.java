package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BallLauncher extends GameObject {

    public BallLauncher(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/start.jpg");

    }
}
