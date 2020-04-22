package com.game;

import com.game.interfaces.IFactory;
import com.game.models.*;
import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;


/**
 * A factory used to create instances of ObjectOnScreen type
 */
public class GameObjectsFactory implements IFactory {
    private GraphicsContext graphicsContext;

    public GameObjectsFactory(@NotNull GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * Call this method to create an instance of an ObjectOnScreen
     *
     * @param type - the name of the object to create
     * @param x    - the x position of the object
     * @param y    = the y position of the object
     * @return - the new Instance of the object
     */
    @Override
    public ScreenObject createObject(String type, double x, double y) {
        switch (type) {
            case "finish":
                return new Finish(x, y, graphicsContext);
            case "player":
                return new Player(x, y, graphicsContext);
            case "rectangle":
                return new ElectricRectangle(x, y, graphicsContext);
            case "slope":
                return new Slope(x, y, graphicsContext);
            case "wind_turbine":
                return new WindTurbine(x, y, graphicsContext);
            case "wind":
                return new Wind(x, y, graphicsContext);
            case "sun":
                return new Sun(x, y, graphicsContext);
            case "solar_panel":
                return new SolarPanel(x, y, graphicsContext);
            case "fan":
                return new Fan(x, y, graphicsContext);

            default:
                return null;
        }

    }
}
