package com.company;

import javafx.scene.canvas.GraphicsContext;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends GameObject implements Movable {
    private static Player instance;
    private double moveOnX;
    private double moveOnY;

    public static synchronized Player createInstance(double x, double y, GraphicsContext gc) {
        if (instance == null) {
            instance = new Player(x, y, gc);
        }
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

                if (moveOnX != 0) {
                    if (moveOnX < 0) {
                        x -= 1;
                        moveOnX++;
                    } else {
                        x += 1;
                        moveOnX--;
                    }
                }
                if (moveOnY != 0) {
                    if (moveOnY < 0) {
                        y -= 1;
                        moveOnY++;
                    } else {
                        y += 1;
                        moveOnY--;
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 20);
    }

    @Override
    public void update() {
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
}
