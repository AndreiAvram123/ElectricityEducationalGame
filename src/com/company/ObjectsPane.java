package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ObjectsPane {

    private double y;
    private double height;
    private double width;
    private GraphicsContext graphicsContext;
    private ArrayList<GameObject> objectsInSelectorPane = new ArrayList<>();

    public ObjectsPane(GraphicsContext graphicsContext, double y, double height, double width) {
        this.y = y;
        this.height = height;
        this.width = width;
        this.graphicsContext = graphicsContext;
    }

    public boolean isObjectFromSelectorPane(@NotNull GameObject gameObject) {
        return gameObject.y >= y;
    }

    public void update() {
        graphicsContext.setFill(Color.PINK);
        graphicsContext.fillRect(0, y, width, height);
    }

    public double getHeight() {
        return height;
    }
}
