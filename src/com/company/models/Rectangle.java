package com.company.models;

import com.company.interfaces.HintOnHover;
import com.company.interfaces.MovePlayerRight;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Rectangle extends ReactiveObject implements HintOnHover {
    private String hintMessage = "this is a rectangle,treat it accordingly";

    public Rectangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }


    @Override
    public void setUnderElectricity(boolean underElectricity) {
        super.setUnderElectricity(underElectricity);
        if (underElectricity && img == null) {
            img = new Image("res/thunder.png");
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
