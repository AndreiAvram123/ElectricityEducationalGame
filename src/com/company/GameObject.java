package com.company;

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

}
