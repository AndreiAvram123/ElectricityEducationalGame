package com.company;

import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;

public class Factory implements IFactory {
    private GraphicsContext graphicsContext;

    public Factory(@NotNull GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    @Override
    public GameObject createObject(@NotNull String type, double x, double y) {
        switch (type) {
            case "bulb":
                return new Bulb(x, y, graphicsContext);
            case "launcher":
                return new BallLauncher(x, y, graphicsContext);
            case "rectangle":
                return new Rectangle(x, y, graphicsContext);
            case "triangle":
                return new Triangle(x, y, graphicsContext);
            case "ball":
                return Player.getInstance(x, y, graphicsContext);
            case "wind_turbine":
                return new WindTurbine(x, y, graphicsContext);
        }
        return null;
    }
}
