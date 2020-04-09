package com.company;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    private Pane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root, AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.requestFocus();

        GameManager gameManager = new GameManager(root);
        gameManager.startFirstLevel();

    }
}
