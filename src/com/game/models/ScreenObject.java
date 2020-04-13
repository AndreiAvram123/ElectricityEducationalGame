package com.game.models;

import com.game.interfaces.Draggable;
import com.game.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

 public abstract class ScreenObject extends GameObject implements Draggable, HintOnHover {

    private boolean hasDragEnabled = false;
    protected double width = 50;
    protected double height = 50;

    public ScreenObject(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }

    public boolean isNeighbourBottomWith(@NotNull ScreenObject object) {
        return object.getX() == this.x && object.getY() + object.getHeight() == this.getY();
    }

    public boolean isNeighbourTopWith(@NotNull ScreenObject object) {
        return object.getX() == this.getX() && object.getY() == this.getY() + this.getHeight();
    }

    public boolean isNeighbourRightWith(@NotNull ScreenObject object) {
        return object.getY() == this.getY() && object.getX() + object.getWidth() == this.getX();
    }

    public boolean isNeighbourLeftWith(@NotNull ScreenObject object) {
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
