package com.game.models;

import com.game.interfaces.IPlayerReaction;
import com.game.interfaces.NoMovingPlayerBehaviour;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Slope extends ScreenObject  {
    private static final String HINT = "A slope can make the player go down";
    private IPlayerReaction playerReaction = new NoMovingPlayerBehaviour();

    public Slope(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }


    @Override
    public void update() {
        gc.setFill(Color.GREY);
        gc.fillPolygon(new double[]{x, x, x + 50}, new double[]{y, y + 50, y + 50}, 3);
    }

    public void executeReaction(Sides playerCollisionSide){
        if(playerCollisionSide == Sides.BOTTOM) {
            playerReaction.execute();
        }
    }

    public void setPlayerReaction(IPlayerReaction playerReaction) {
        this.playerReaction = playerReaction;
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
