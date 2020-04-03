package com.company;

import com.company.interfaces.MovePlayerHorizontallyReaction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends ReactiveObject {
    private Color color = Color.ALICEBLUE;

    public Rectangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        setElectricityReaction(new MovePlayerHorizontallyReaction(Player.getInstance()));
    }


    @Override
    public void update() {
        gc.setFill(color);
        gc.fillRect(x, y, width, height);

    }
}
