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


    public LevelModel(@NotNull ArrayList<ObjectOnScreen> objectOnScreens,
                      @NotNull ArrayList<ObjectOnScreen> objectsOnSelectorPane,
                      @NotNull Player player,
                      @NotNull String hintAfterFinish,
                      @NotNull String hintBeforeStart,
                      int levelNumber) {
        this.objectsOnGameScreen = objectOnScreens;
        this.objectsOnSelectorPane = objectsOnSelectorPane;
        this.hintAfterFinish = hintAfterFinish;
        this.hintBeforeStart = hintBeforeStart;
        this.levelNumber = levelNumber;
        this.player = player;
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

    public void addObjectToGameScreen(@NotNull ObjectOnScreen object) {
        objectsOnGameScreen.add(object);
        //remove the object from the selector pane after adding
        //it to the game screen objects
        //it is unnecessary to check whether the objectsOnSelectorPane
        //arraylist contains the added object because
        //the method remove() can accept objects that are not in the array
        //if the object is not in the array it simply ignores it
        objectsOnSelectorPane.remove(object);
    }

    public ArrayList<ObjectOnScreen> getObjectsOnGameScreen() {
        return objectsOnGameScreen;
    }

    public ArrayList<ObjectOnScreen> getObjectsOnSelectorPane() {
        return objectsOnSelectorPane;
    }
}
