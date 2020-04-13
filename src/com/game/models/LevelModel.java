package com.game.models;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * The model representing the data for a given level
 *
 */
public class LevelModel {
    private final ArrayList<ScreenObject> objectsOnGameScreen;
    private final String hintBeforeStart;
    private final String hintAfterFinish;
    private final int levelNumber;
    private Player player;


    public LevelModel(@NotNull ArrayList<ScreenObject> screenObject,
                      @NotNull Player player,
                      @NotNull String hintAfterFinish,
                      @NotNull String hintBeforeStart,
                      int levelNumber) {
        this.objectsOnGameScreen = screenObject;
        this.hintAfterFinish = hintAfterFinish;
        this.hintBeforeStart = hintBeforeStart;
        this.levelNumber = levelNumber;
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

    public ArrayList<ScreenObject> getObjectsOnGameScreen() {
        return objectsOnGameScreen;
    }

}
