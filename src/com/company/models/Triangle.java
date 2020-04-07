package com.company.models;

import com.company.ReactiveObject;
import com.company.interfaces.MovePlayerDiagonallyDownRight;
import javafx.scene.canvas.GraphicsContext;

public class Triangle extends ReactiveObject {

    public Triangle(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        setElectricityReaction(new MovePlayerDiagonallyDownRight(Player.getInstance()));

    }

    @Override
    public void update() {
        gc.setFill(color);
        gc.fillPolygon(new double[]{x, x, x + 50}, new double[]{y, y + 50, y + 50}, 3);
    }
}
