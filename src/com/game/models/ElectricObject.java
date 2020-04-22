package com.game.models;

import com.game.interfaces.IPlayerReaction;
import com.game.interfaces.NoMovingPlayerBehaviour;
import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class ElectricObject extends ScreenObject {
    private IPlayerReaction playerReaction = new NoMovingPlayerBehaviour();
    private boolean isUnderElectricity = false;
    protected Color color = Color.GREY;
    protected Sides playerCollisionSideForReaction = Sides.BOTTOM;
    private double playerPushForce = 50;

    public ElectricObject(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
    }

    public void setUnderElectricity(boolean underElectricity) {
        isUnderElectricity = underElectricity;
    }

    public boolean isUnderElectricity() {
        return isUnderElectricity;
    }

    public void setPlayerReaction(@NotNull IPlayerReaction playerReaction) {
        this.playerReaction = playerReaction;
    }

    public void executePlayerReaction(Sides collisionSide) {
        if (isUnderElectricity && playerCollisionSideForReaction == collisionSide) {
            this.playerReaction.execute();
        }
    }

    public Sides getPlayerCollisionSideForReaction() {
        return playerCollisionSideForReaction;
    }

    public double getPlayerPushForce() {
        return playerPushForce;
    }

    public void setPlayerPushForce(double playerPushForce) {
        this.playerPushForce = playerPushForce;
    }
}
