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
    public GameObject createObject(@NotNull String type, double x, double y, boolean hasDragEnabled) {
        switch (type) {
            case "rectangle": {
                Rectangle rectangle = new Rectangle(x, y, graphicsContext);
                rectangle.setHasDragEnabled(hasDragEnabled);
                return rectangle;
            }
            case "triangle":
                Triangle triangle = new Triangle(x, y, graphicsContext);
                triangle.setHasDragEnabled(hasDragEnabled);
                return triangle;
            case "ball":
                Player player = new Player(x, y, graphicsContext);
                player.setHasDragEnabled(hasDragEnabled);
                return player;
            case "wind_turbine":
                WindTurbine windTurbine = new WindTurbine(x, y, graphicsContext);
                windTurbine.setHasDragEnabled(hasDragEnabled);
                return windTurbine;
            case "wind":
                Wind wind = new Wind(x, y, graphicsContext);
                wind.setHasDragEnabled(hasDragEnabled);
                return wind;
            case "sun":
                Sun sun = new Sun(x, y, graphicsContext);
                sun.setHasDragEnabled(hasDragEnabled);
                return sun;
            case "solar_panel":
                SolarPanel solarPanel = new SolarPanel(x, y, graphicsContext);
                solarPanel.setHasDragEnabled(hasDragEnabled);
                return solarPanel;
            default:
                return new NullObject(x, y, graphicsContext);
        }

    }
}
