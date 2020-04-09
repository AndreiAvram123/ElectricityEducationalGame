package com.company.models;

import com.company.Sides;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class Fan extends ReactiveObject implements Rotating {
    private int currentRotation = 0;

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

}
