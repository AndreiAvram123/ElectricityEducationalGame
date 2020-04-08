package com.company.models;

import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Finish extends GameObject implements HintOnHover {
    private static final String HINT = "This is where you \n need to move the ball\n to win :(";

    public Finish(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/finish.png");

    }

    @Override
    public String getHint() {
        return HINT;
    }
}
