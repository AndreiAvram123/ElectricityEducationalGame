package com.company.models;

import com.company.interfaces.IElectricityReaction;
import com.company.interfaces.NoMovingReaction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class ReactiveObject extends ObjectOnScreen {
    private IElectricityReaction electricityReaction;
    private boolean isUnderElectricity = false;
    protected Color color = Color.GREY;

    public ReactiveObject(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        electricityReaction = new NoMovingReaction();
    }

    public void setUnderElectricity(boolean underElectricity) {
        isUnderElectricity = underElectricity;
    }

    public boolean isUnderElectricity() {
        return isUnderElectricity;
    }

    public void setElectricityReaction(@NotNull IElectricityReaction electricityReaction) {
        this.electricityReaction = electricityReaction;
    }

    public void executeElectricityReaction() {
        if (isUnderElectricity) {
            this.electricityReaction.execute();
        }
    }


}
