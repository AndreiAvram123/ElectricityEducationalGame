package com.game.models;

import com.game.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Finish extends ObjectOnScreen implements HintOnHover {
    private static final String HINT = "You need to move the ball either on top or on the bottom of this to win";

    public Finish(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/images/finish.png");


    }


    @Override
    public String getHint() {
        return HINT;
    }
}
