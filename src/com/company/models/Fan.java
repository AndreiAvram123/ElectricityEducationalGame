package com.company.models;

import com.company.Sides;
import com.company.interfaces.MovePlayerLeft;
import com.company.interfaces.MovePlayerRight;
import com.company.interfaces.MovePlayerUp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fan extends ReactiveObject implements Rotating {
    private int currentRotation = 0;

    public Fan(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        img = new Image("fan_right.png");
        setElectricityReaction(new MovePlayerRight(Player.getInstance()));
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
                setElectricityReaction(new MovePlayerRight(Player.getInstance()));
                playerCollisionSideForReaction = Sides.LEFT;
                break;
            case 90:
                setElectricityReaction(new MovePlayerUp(Player.getInstance()));
                img = new Image("res/fan_up.png");
                playerCollisionSideForReaction = Sides.BOTTOM;
                break;
            case 180:
                setElectricityReaction(new MovePlayerLeft(Player.getInstance()));
                playerCollisionSideForReaction = Sides.RIGHT;
                img = new Image("res/fan_left.png");
                break;
        }
    }
}
