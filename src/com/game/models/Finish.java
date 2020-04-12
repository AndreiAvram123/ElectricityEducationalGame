package com.game.models;

import com.game.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Finish extends ObjectOnScreen implements HintOnHover {
    private static final String HINT = "This is where you \n need to move the ball\n to win :(";
    private Sides finishPlayerSide = Sides.BOTTOM;
    public Finish(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("res/images/finish.png");

    }

    public Sides getFinishPlayerSide() {
        return finishPlayerSide;
    }

    public void setFinishPlayerSide(Sides finishPlayerSide) {
        this.finishPlayerSide = finishPlayerSide;
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
