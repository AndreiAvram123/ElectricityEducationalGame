package com.company.models;

import com.company.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Finish extends GameObject {
    private static Finish instance = null;

    public static synchronized Finish getInstance(double x,double y, GraphicsContext graphicsContext) {
        if (instance == null) {
            instance = new Finish(x, y, graphicsContext);
        }
        return instance;
    }

    private Finish(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/finish.png");

    }
}
