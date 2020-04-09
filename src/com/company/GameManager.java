package com.company;

import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;

import java.util.Observable;
import java.util.Observer;

public class GameManager implements Observer {
    private TextPanel textPanel;
    private final int numberOfLevels;
    private LevelController levelController;
    private LevelView levelView;
    private LevelDataReader levelDataReader;
    private LevelModel levelModel;

    public GameManager(@NotNull Pane root) {
        textPanel = new TextPanel(root);
        AudioManager.getInstance().playBackgroundMusic();
        attachListenerToPanel();

        levelView = new LevelView(root);
        levelDataReader = new LevelDataReader(levelView.getGraphicsContext());
        this.numberOfLevels = levelDataReader.getNumberOfLevels();
        startFirstLevel();
    }

    /**
     * This method is used to start the first level
     * and display the hint before start on the screen
     */
    public void startFirstLevel() {
        getLevelModel(1);
        levelView.setLevelModel(levelModel);
        levelController = new LevelController(levelView, levelModel);
        displayHintBeforeLevel();
    }


    private void getLevelModel(int level) {
        levelModel = new LevelModel(
                levelDataReader.getObjectsArrayFromJsonFile(level, "objectsOnScreen"),
                levelDataReader.getObjectsArrayFromJsonFile(level, "selectorPaneObjects"),
                levelDataReader.getHintAfterFinish(level),
                levelDataReader.getHintBeforeStart(level),
                level
        );
        levelModel.createInitialState();
    }


    private void attachListenerToPanel() {
        textPanel.getNextButton().setOnMouseClicked(event -> {
            levelController.startLevel();
            levelController.addObserver(this);
            textPanel.hidePanel();
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LevelController) {
            AudioManager.getInstance().playLevelFinishedSound();
            displayLevelFinishedHint();
            int nextLevel = (int) arg + 1;
            if (numberOfLevels < nextLevel) {
                textPanel.getNextButton().setText("Play again");

            } else {
                //increase the level number
                textPanel.getNextButton().setText("Next");
                getLevelModel((int) arg + 1);
                levelController.setLevelModel(levelModel);
                levelView.setLevelModel(levelModel);
            }
        }

    }

    private void displayLevelFinishedHint() {
        levelController.hideLevel();
        textPanel.showPanel(levelModel.getHintAfterFinish());
    }

    private void displayHintBeforeLevel() {
        textPanel.showPanel(levelModel.getHintBeforeStart());
    }
}
