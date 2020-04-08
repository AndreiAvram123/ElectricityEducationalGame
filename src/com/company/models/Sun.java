package com.company.models;

import com.company.ElectricityFuel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sun extends ElectricityFuel {

    public Sun(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img  = new Image("res/sun.png");
    }
}
