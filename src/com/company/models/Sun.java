package com.company.models;

import com.company.ElectricityFuel;
import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sun extends ElectricityFuel implements HintOnHover {
    private static final String HINT = "Without it our lives would not exist. Great in combination with a solar panel to produce green energy";

    public Sun(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("com/company/res/sun.png");
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
