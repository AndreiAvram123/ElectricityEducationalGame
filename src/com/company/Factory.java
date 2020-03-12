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
        }
        return null;
    }
}
