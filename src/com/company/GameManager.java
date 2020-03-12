package com.company;

import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameManager {

    private static GameManager instance;
    private Pane root;
    private GraphicsContext graphicsContext;
    private Canvas canvas;


    public static GameManager getInstance(@NotNull Pane root) {
        if (instance == null) {
            instance = new GameManager(root);
        }
        return instance;
    }

    private GameManager(@NotNull Pane root) {
        this.root = root;
        this.canvas = new Canvas(800, 600);
        this.root.getChildren().add(this.canvas);
        this.graphicsContext = canvas.getGraphicsContext2D();
    }


    public void startLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                Level level1 = new Level(canvas);
                level1.start();
                break;
        }
    }


}
