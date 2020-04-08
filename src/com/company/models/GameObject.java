package com.company.models;

import com.company.Draggable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameObject implements Draggable {

    protected Image img;
    protected double x, y;
    protected GraphicsContext gc;
    protected double width = 50;
    protected double height = 50;
    private boolean hasDragEnabled = false;

    public GameObject(double x, double y, GraphicsContext gc) {
        this.x = x;
        this.y = y;
        this.gc = gc;
    }

    public void update() {
        if (img != null) {
            gc.drawImage(img, x, y, width, height);
        }
    }


    @Override
    public void setNewCenter(double x, double y) {
        if (hasDragEnabled) {
            this.x = x - width / 2;
            this.y = y - height / 2;
        }
    }


    public void setHasDragEnabled(boolean hasDragEnabled) {
        this.hasDragEnabled = hasDragEnabled;
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
