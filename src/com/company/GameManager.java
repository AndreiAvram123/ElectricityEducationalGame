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


    public GameManager(@NotNull Pane root) {
        this.root = root;
        textPanel = new TextPanel(root);
        AudioManager.getInstance().playBackgroundMusic();
        attachListenerToPanel();
    }

    public void startFirstLevel() {
        currentLevel = new Level(root, 1);
        displayHintBeforeLevel();
    }


    private void attachListenerToPanel() {
        textPanel.getNextButton().setOnMouseClicked(event -> {

            currentLevel.showLevel();
            currentLevel.addObserver(this);
            textPanel.hidePanel();
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Level) {
//            AudioManager.getInstance().playLevelFinishedSound();
//            displayLevelFinishedHint();
//            if (numberOfLevels < (int) arg + 1) {
//                textPanel.getNextButton().setText("Restart");
//                currentLevel = new Level(root, 1);
//            } else {
//                //increase the level number
//                textPanel.getNextButton().setText("Next");
//                currentLevel = new Level(root, (int) arg + 1);
//            }
        }

    }

    private void displayLevelFinishedHint() {
        currentLevel.hide();
        textPanel.showPanel(currentLevel.getHintAfterFinish());
    }

    private void displayHintBeforeLevel() {

        textPanel.showPanel(currentLevel.getHintBeforeStart());
    }
}
