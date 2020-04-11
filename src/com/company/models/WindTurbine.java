package com.company.models;

import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class WindTurbine extends ElectricitySource implements HintOnHover {
    private static final String HINT = "A wind turbine can produce electricity by wind";
    private static final String[] poweredBy = new String[]{Wind.class.getSimpleName()};

    public WindTurbine(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("com/company/res/wind_turbine.png");
    }

    @Override
    public boolean isPoweredBy(EnergyFactor energyFactor) {
        for (String energy : poweredBy) {
            if (energyFactor.getClass().getSimpleName().equals(energy)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String getHint() {
        return HINT;
    }
}
