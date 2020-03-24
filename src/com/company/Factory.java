package com.company;

import com.company.interfaces.MovePlayerDiagonallyDownRight;
import com.company.interfaces.MovePlayerHorizontally;
import com.company.models.BallLauncher;
import com.company.models.Bulb;
import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;

//todo
//the factory can store the state of the current
//objects on screen
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
                Rectangle rectangle = new Rectangle(x, y, graphicsContext);
                rectangle.setElectricityReaction(new MovePlayerHorizontally(Player.getInstance()));
                return rectangle;
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
