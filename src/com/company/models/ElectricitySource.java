package com.company.models;

import com.company.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ElectricitySource extends GameObject {

    public ElectricitySource(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/bulb.jpg");
    }
}
