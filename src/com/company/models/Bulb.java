package com.company.models;

import com.company.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bulb extends GameObject {

    public Bulb(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/bulb.jpg");
    }


}
