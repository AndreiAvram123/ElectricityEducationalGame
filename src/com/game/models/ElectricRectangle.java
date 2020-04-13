package com.game.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ElectricRectangle extends ElectricObject{
    private final String hintMessage = "This component can move the player right.It needs electricity to function";

    public ElectricRectangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }


    @Override
    public void setUnderElectricity(boolean underElectricity) {
        super.setUnderElectricity(underElectricity);
        if (underElectricity) {
            if (img == null) {
                img = new Image("images/thunder.png");
            }
        } else {
            img = null;
        }
    }

    @Override
    public void update() {
        if (img == null) {
            gc.setFill(color);
            gc.fillRect(x, y, width, height);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, width, height);
        } else {
            super.update();
        }
    }

    @Override
    public String getHint() {
        return hintMessage;
    }
}
