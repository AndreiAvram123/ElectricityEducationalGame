package com.company;

import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import java.util.Observable;
import java.util.Observer;

public class GameManager implements Observer {

    private static GameManager instance;
    private final Pane root;


    public static GameManager getInstance(@NotNull Pane root) {
        if (instance == null) {
            instance = new GameManager(root);
        }
        return instance;
    }

    private GameManager(@NotNull Pane root) {
        this.root = root;
    }


    public void startLevel(int levelNumber) {
        Level level = new Level(root, levelNumber);
        level.addObserver(this);
        level.start();

    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Level) {
            displayLevelFinished();
        }
    }

    private void displayLevelFinished() {
        AudioClip audioClip = new AudioClip(this.getClass().getResource("guta.mp3").toString());
        audioClip.play();
    }
}
