package com.company.models;

import com.company.interfaces.Draggable;
import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

public class ObjectOnScreen extends GameObject implements Draggable {

    private boolean hasDragEnabled = false;
    protected double width = 50;
    protected double height = 50;

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
        return object.getY() == object.getY() && object.getX() + object.getWidth() == this.getX();
    }

    public boolean isNeighbourLeft(@NotNull ObjectOnScreen object) {
        return object.getY() == this.getY() && object.getX() == this.getX() + this.getWidth();
    }

    public void setHasDragEnabled(boolean hasDragEnabled) {
        this.hasDragEnabled = hasDragEnabled;
    }


    @Override
    public void setNewCenter(double x, double y) {
        if (hasDragEnabled) {
            this.x = x - width / 2;
            this.y = y - height / 2;
        }
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
