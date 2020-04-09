package com.company.models;

import com.company.interfaces.Movable;
import com.company.interfaces.HintOnHover;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends ObjectOnScreen implements Movable, HintOnHover, Gravity {
    private static Player instance;
    private double moveOnX;
    private double moveOnY;
    private static final String HINT = "This is the player dummy";
    private boolean gravityEnabled = false;

    //todo
    //refactor player
    //the position depends on the level so it is not a singleton
    public static synchronized Player createInstance(double x, double y, GraphicsContext gc) {
        instance = new Player(x, y, gc);
        return instance;
    }

    public static Player getInstance() {
        return instance;
    }

    public Player(double x, double y, GraphicsContext gc) {
        super(x, y, gc);
        initializeMovingAnimation();
    }

    private void initializeMovingAnimation() {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                applyGravity();

                if (moveOnX < 0) {
                    x--;
                    moveOnX++;
                } else {
                    if (moveOnX > 0) {
                        x++;
                        moveOnX--;
                    }
                }

                if (moveOnY < 0) {
                    y--;
                    moveOnY++;
                } else {
                    if (moveOnY > 0) {
                        y++;
                        moveOnY--;
                    }
                }
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 20);
    }

    @Override
    public void update() {
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, 50, 50);
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
