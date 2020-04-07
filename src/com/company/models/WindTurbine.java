package com.company.models;

import com.company.interfaces.HintOnHover;
import com.company.models.ElectricitySource;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WindTurbine extends ElectricitySource implements HintOnHover {
    private static final String HINT = "A wind turbine can produce electricity by wind";

    public WindTurbine(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/wind_turbine.png");
    }


    @Override
    public String getHint() {
        return HINT;
    }
}
