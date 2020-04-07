package com.company;

import com.company.models.Level;
import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;

import java.util.Observable;
import java.util.Observer;

public class GameManager implements Observer {

    private static GameManager instance;
    private final Pane root;
    private TextPanel textPanel;
    private Level currentLevel;
    private int numberOfLevels = LevelDataReader.getNumberOfLevels();

    public static GameManager getInstance(@NotNull Pane root) {
        if (instance == null) {
            instance = new GameManager(root);
        }
        return instance;
    }

    private GameManager(@NotNull Pane root) {
        this.root = root;
        textPanel = new TextPanel(root);
        AudioManager.getInstance().playBackgroundMusic();
    }


    public void startLevel(int levelNumber) {

        if (numberOfLevels >= levelNumber) {
            currentLevel = new Level(root, levelNumber);
            currentLevel.addObserver(this);
            textPanel.showPanel(currentLevel.getHintBeforeStart());
            textPanel.getNextButton().setOnMouseClicked(event -> {
                currentLevel.showLevel();
                currentLevel.addObserver(this);
                textPanel.hidePanel();
            });
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Level) {
            displayLevelFinishedHint();
            AudioManager.getInstance().playLevelFinishedSound();

        }
    }

    private void displayLevelFinishedHint() {
        currentLevel.hide();
        textPanel.showPanel(currentLevel.getHintAfterFinish());
    }
}
