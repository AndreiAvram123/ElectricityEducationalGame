package com.company.models;

import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

public class ObjectOnScreen extends GameObject {

    public ObjectOnScreen(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }


    public boolean isNeighbourBottom(@NotNull ObjectOnScreen object) {
        return object.getX() == this.x && object.getY() + object.getHeight() == this.getY();
    }

    public boolean isNeighbourTop(@NotNull ObjectOnScreen object) {
        return object.getX() == this.getX() && object.getY() == this.getY() + this.getHeight();
    }

    public boolean isNeighbourRight(@NotNull ObjectOnScreen object) {
        return object.getY() == object.getY() && this.getX() + this.getWidth() == object.getX();
    }

    public boolean isNeighbourLeft(@NotNull ObjectOnScreen object) {
        return object.getY() == this.getY() && object.getX() == this.getX() + this.getWidth();
    }
}
