package com.company;

import com.company.interfaces.IFactory;
import com.company.models.*;
import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;


public class GameObjectsFactory implements IFactory {
    private GraphicsContext graphicsContext;

    public GameObjectsFactory(@NotNull GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    @Override
    public ObjectOnScreen createObject(@NotNull String type, double x, double y) {
        switch (type) {
            case "finish":
                return new Finish(x, y, graphicsContext);
            case "player":
                return new Player(x, y, graphicsContext);
            case "rectangle":
                Rectangle rectangle = new Rectangle(x, y, graphicsContext);
                return rectangle;
            case "triangle":
                Slope slope = new Slope(x, y, graphicsContext);
                return slope;
            case "ball":
                Player player = new Player(x, y, graphicsContext);
                return player;
            case "wind_turbine":
                WindTurbine windTurbine = new WindTurbine(x, y, graphicsContext);
                return windTurbine;
            case "wind":
                Wind wind = new Wind(x, y, graphicsContext);
                return wind;
            case "sun":
                Sun sun = new Sun(x, y, graphicsContext);
                return sun;
            case "solar_panel":
                SolarPanel solarPanel = new SolarPanel(x, y, graphicsContext);
                return solarPanel;
            case "fan":
                Fan fan = new Fan(x, y, graphicsContext);
                return fan;

            default:
                return new NullObject(x, y, graphicsContext);
        }

    }
}
