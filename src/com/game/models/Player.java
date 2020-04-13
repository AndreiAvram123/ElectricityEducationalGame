package com.game.models;

import com.game.interfaces.Gravity;
import com.game.interfaces.Movable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends ScreenObject implements Movable, Gravity {
    private double moveOnX;
    private double moveOnY;
    private static final String HINT = "This is the player dummy";
    private boolean gravityEnabled = false;

    public Player(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        setHasDragEnabled(false);
    }

    private void movePlayer() {
        applyGravity();

        double speedPerFrame = 1;
        if (moveOnX < 0) {
            x -= speedPerFrame;
            moveOnX += speedPerFrame;
        } else {
            if (moveOnX > 0) {
                x += speedPerFrame;
                moveOnX -= speedPerFrame;
            }
        }

        if (moveOnY < 0) {
            y -= speedPerFrame;
            moveOnY += speedPerFrame;
        } else {
            if (moveOnY > 0) {
                y += speedPerFrame;
                moveOnY -= speedPerFrame;
            }
        }
    }

    @Override
    public void update() {
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, 50, 50);
        movePlayer();
    }

    @Override
    public void moveOnY(double y) {
        this.moveOnY = y;
    }

    @Override
    public void moveOnX(double x) {
        this.moveOnX = x;
    }

    @Override
    public String getHint() {
        return HINT;
    }


    @Override
    public void setGravityEnabled(boolean enabled) {
        gravityEnabled = enabled;
    }

    @Override
    public void applyGravity() {
        if (moveOnY == 0 && moveOnX == 0 && gravityEnabled) {
            moveOnY++;
        }
    }

    public boolean isMoving() {
        return moveOnX != 0 || moveOnY != 0;

    }


}
