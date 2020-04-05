package com.company;

import com.company.interfaces.MovePlayerHorizontallyReaction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Rectangle extends ReactiveObject {

    public Rectangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        setElectricityReaction(new MovePlayerHorizontallyReaction(Player.getInstance()));
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
}
