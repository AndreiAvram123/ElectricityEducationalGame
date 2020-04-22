package com.game;

import com.game.UI.LevelView;
import com.game.UI.TextPanel;
import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;

import java.util.Observable;
import java.util.Observer;

/**
 * Game manager is an observer responsible for switching between two layers : the level layer ( the visual
 * part that represents the actual game ) and the textPanel ( the visual part that represents hints before and after the
 * level)
 * This observer gets notified when each level is completed by the  LevelController
 */
public class GameManager implements Observer {
    private final TextPanel textPanel;
    private final LevelController levelController;
    private boolean shouldDisplayHintBeforeLevel = true;

    public GameManager(@NotNull Pane root) {
        textPanel = new TextPanel(root);
        AudioManager.getInstance().playBackgroundMusic();
        levelController = new LevelController(new LevelView(root));
        levelController.addObserver(this);
        attachListenerToPanel();

    }

    /**
     * Call this method to start the game
     */
    public void startGame() {
        displayHintBeforeLevel();

    }

    private void attachListenerToPanel() {
        textPanel.getNextButton().setOnMouseClicked(event -> {
            if(shouldDisplayHintBeforeLevel) {
              displayHintBeforeLevel();
            }else {
                levelController.showLevelView();
                textPanel.hidePanel();
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LevelController) {
            displayLevelFinishedHint();
            int nextLevel = (int) arg + 1;
            levelController.goToNextLevel();
            if (levelController.getNumberOfLevels() < nextLevel) {
                textPanel.getNextButton().setText("Play again");
            } else {
                //increase the level number
                textPanel.getNextButton().setText("Next");
            }
        }

    }

    /**
     * This method is used to display a hint to the user
     * after each level finishes
     */
    private void displayLevelFinishedHint() {
        levelController.hideLevelView();
        textPanel.showPanel(levelController.getControllerLevelModel().getHintAfterFinish());
        shouldDisplayHintBeforeLevel = true;
    }

    /**
     * This method is used to display a hint to the user
     * before each level begins
     */
    public void displayHintBeforeLevel() {
        textPanel.showPanel(levelController.getControllerLevelModel().getHintBeforeStart());
        shouldDisplayHintBeforeLevel = false;
    }
}
