package com.company;

import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;

import java.util.Observable;
import java.util.Observer;

public class GameManager implements Observer {
    private final TextPanel textPanel;
    private final LevelController levelController;


    public GameManager(@NotNull Pane root) {
        textPanel = new TextPanel(root);
        AudioManager.getInstance().playBackgroundMusic();
        levelController = new LevelController(new LevelView(root));
        levelController.addObserver(this);
        attachListenerToPanel();

    }

    public void start(){
        displayHintBeforeLevel();
    }

    private void attachListenerToPanel() {
        textPanel.getNextButton().setOnMouseClicked(event -> {
            levelController.startLevel();
            textPanel.hidePanel();
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LevelController) {
            AudioManager.getInstance().playLevelFinishedSound();
            displayLevelFinishedHint();
            int nextLevel = (int) arg + 1;
            if (levelController.getNumberOfLevels() < nextLevel) {
                textPanel.getNextButton().setText("Play again");

            } else {
                //increase the level number
                textPanel.getNextButton().setText("Next");
                levelController.getNextLevel();
            }
        }

    }

    private void displayLevelFinishedHint() {
        levelController.hideLevel();
        textPanel.showPanel(levelController.getControllerLevelModel().getHintAfterFinish());
    }

    public void displayHintBeforeLevel() {
        textPanel.showPanel(levelController.getControllerLevelModel().getHintBeforeStart());
    }
}
