package com.company.models;

import com.company.ElectricityFuel;
import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wind extends ElectricityFuel implements HintOnHover {
    private static final String HINT = "Wind can be used to power up a wind turbine";


    public Wind(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("com/company/res/wind.png");
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
