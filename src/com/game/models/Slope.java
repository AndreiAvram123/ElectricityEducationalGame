package com.game.models;

import com.game.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Slope extends ObjectOnScreen implements HintOnHover {
    private static final String HINT = "A slope can make the player go down";

    public Slope(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }


    @Override
    public void update() {
        gc.setFill(Color.GREY);
        gc.fillPolygon(new double[]{x, x, x + 50}, new double[]{y, y + 50, y + 50}, 3);
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
