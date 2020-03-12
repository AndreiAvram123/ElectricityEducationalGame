package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameObject implements Draggable {

    protected Image img;
    protected double x, y;
    protected GraphicsContext gc;
    protected double width = 50;
    protected double height = 50;


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
    public void setCenter(double x,double y ) {
        this.x = x - width / 2;
        this.y = y - height / 2;

    }

}
