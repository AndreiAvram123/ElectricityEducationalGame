package com.company;

import com.company.models.ElectricitySource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WindTurbine extends ElectricitySource {


    public WindTurbine(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/wind_turbine.png");
    }


}
