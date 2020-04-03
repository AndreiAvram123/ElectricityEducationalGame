package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
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
        Scene scene = new Scene(root, 800, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        GameManager gameManager = GameManager.getInstance(root);
        gameManager.startLevel(1);

    }
}
