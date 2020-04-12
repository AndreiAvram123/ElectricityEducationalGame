package com.game;

import com.game.UI.StartScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene startScene = new Scene(root, AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT);
        primaryStage.setScene(startScene);
        primaryStage.show();
        primaryStage.setResizable(false);

        StartScreen startScreen = new StartScreen(root);
        startScreen.getStartButton().setOnMouseClicked(event -> {
            Pane gameSceneRoot = new Pane();
            Scene gameScene = new Scene(gameSceneRoot, AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT);
            primaryStage.setScene(gameScene);
            primaryStage.show();
            GameManager gameManager = new GameManager(gameSceneRoot);
            gameManager.startGame();
        });


        primaryStage.setOnCloseRequest(t -> System.exit(0));
    }
}
