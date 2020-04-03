package com.company;

import com.company.interfaces.IElectricityReaction;
import com.company.interfaces.NoMovingReaction;
import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

public class ReactiveObject extends GameObject {
    private IElectricityReaction electricityReaction;
    protected boolean isUnderElectricity = false;

    public ReactiveObject(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        electricityReaction = new NoMovingReaction();
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
