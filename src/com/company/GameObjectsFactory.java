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
    public GameObject createObject(@NotNull String type, double x, double y) {
        switch (type) {
            case "rectangle": {
                Rectangle rectangle = new Rectangle(x, y, graphicsContext);
                return rectangle;
            }
            case "triangle":
                Triangle triangle = new Triangle(x, y, graphicsContext);
                triangle.setElectricityReaction(new MovePlayerDiagonallyDownRight(Player.getInstance()));
                return triangle;
            case "ball":
                return Player.createInstance(x, y, graphicsContext);
            case "wind_turbine":
                return new WindTurbine(x, y, graphicsContext);
        }
        return null;
    }
}
