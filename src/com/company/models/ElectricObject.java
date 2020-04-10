package com.company.models;

import com.company.Sides;
import com.company.interfaces.IPlayerReaction;
import com.company.interfaces.NoMovingReaction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class ElectricObject extends ObjectOnScreen {
    private IPlayerReaction electricityReaction;
    private boolean isUnderElectricity = false;
    protected Color color = Color.GREY;
    protected Sides playerCollisionSideForReaction = Sides.BOTTOM;

    public ElectricObject(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        electricityReaction = new NoMovingReaction();
    }

    public void setUnderElectricity(boolean underElectricity) {
        isUnderElectricity = underElectricity;
    }

    public boolean isUnderElectricity() {
        return isUnderElectricity;
    }

    public void setElectricityReaction(@NotNull IPlayerReaction electricityReaction) {
        this.electricityReaction = electricityReaction;
    }

    public void executeElectricityReaction(Sides collisionSide) {
        if (isUnderElectricity && playerCollisionSideForReaction == collisionSide) {
            this.electricityReaction.execute();
        }
    }

    public Sides getPlayerCollisionSideForReaction() {
        return playerCollisionSideForReaction;
    }
}
