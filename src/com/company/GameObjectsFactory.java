package com.company;

import com.company.interfaces.MovePlayerDiagonallyDownRight;
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
        }
        return null;
    }
}
