package com.company.models;

import com.company.Sides;
import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class Fan extends ElectricObject implements Rotating, HintOnHover {
    private int currentRotation = 0;
    public static final String HINT = "Use key A to rotate it and push the player. Remember it needs electricity to function";
    public Fan(double x, double y, @NotNull GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("fan_right.png");
        playerCollisionSideForReaction = Sides.LEFT;

    }

    @Override
    public void rotate() {
        currentRotation += 90;
        if (currentRotation == 360) {
            currentRotation = 0;
        }
        /**
         * Java fx does not allow individual image rotation on
         * canvas so I decided to provide an image for each 90 degree
         * rotation
         */
        switch (currentRotation) {
            case 0:
                img = new Image("res/fan_right.png");
                playerCollisionSideForReaction = Sides.LEFT;
                break;
            case 90:
                img = new Image("res/fan_up.png");
                playerCollisionSideForReaction = Sides.BOTTOM;
                break;
            case 180:
                playerCollisionSideForReaction = Sides.RIGHT;
                img = new Image("res/fan_left.png");
                break;
        }
    }

    @Override
    public String getHint() {
        return HINT;
    }
}
