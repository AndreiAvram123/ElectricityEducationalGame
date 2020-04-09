package com.company;

import com.company.models.ObjectOnScreen;
import com.company.models.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.logging.Level;

public class LevelModel {
    private ArrayList<ObjectOnScreen> objectsOnGameScreen;
    private ArrayList<ObjectOnScreen> objectsOnSelectorPane;
    private String hintBeforeStart;
    private String hintAfterFinish;
    private int levelNumber;
    private Player player;
    private LevelModel initialState;


    public LevelModel(@NotNull ArrayList<ObjectOnScreen> objectOnScreens,
                      @NotNull ArrayList<ObjectOnScreen> objectsOnSelectorPane,
                      @NotNull String hintAfterFinish,
                      @NotNull String hintBeforeStart,
                      int levelNumber) {
        this.objectsOnGameScreen = objectOnScreens;
        this.objectsOnSelectorPane = objectsOnSelectorPane;
        this.hintAfterFinish = hintAfterFinish;
        this.hintBeforeStart = hintBeforeStart;
        this.levelNumber = levelNumber;
        this.player = Player.getInstance();
    }

    public void createInitialState() {
        this.initialState = new LevelModel(
                objectsOnGameScreen,
                objectsOnSelectorPane,
                hintAfterFinish,
                hintBeforeStart,
                levelNumber
        );
    }

    public void restoreInitialState() {
        this.objectsOnGameScreen = initialState.objectsOnGameScreen;
        this.objectsOnSelectorPane = initialState.objectsOnSelectorPane;
    }

    public void setPlayer(@NotNull Player player) {
        this.player = player;
    }

    public String getHintBeforeStart() {
        return hintBeforeStart;
    }

    public String getHintAfterFinish() {
        return hintAfterFinish;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public void addObject(@NotNull ObjectOnScreen object) {
        objectsOnGameScreen.add(object);
    }

    public ArrayList<ObjectOnScreen> getObjectsOnGameScreen() {
        return objectsOnGameScreen;
    }

    public ArrayList<ObjectOnScreen> getObjectsOnSelectorPane() {
        return objectsOnSelectorPane;
    }
}
