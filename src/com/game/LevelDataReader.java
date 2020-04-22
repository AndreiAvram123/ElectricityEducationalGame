package com.game;

import com.game.interfaces.IFactory;
import com.game.models.ElectricObject;
import com.game.models.LevelModel;
import com.game.models.Player;
import com.game.models.ScreenObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Class used for reading data related to level
 */
public class LevelDataReader {

    private IFactory factory;
    private JsonObject fileData;

    /**
     * Main constructor for the class
     *
     * @param factory - a factory needed in order to create the
     *                objects
     */
    public LevelDataReader(IFactory factory) {
        this.fileData = getDataFromFile();
        this.factory = factory;
    }

    private JsonObject getDataFromFile() {
        JsonObject data = null;
        try {
            String path = getClass().getResource("levelsData.json").getFile();
            FileReader fileReader = new FileReader(new File(path));
            data = JsonParser.parseReader(fileReader).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * Method used in order to read an array from the levels Data file
     *
     * @param levelNumber - the level number
     * @param listName    - the name of the array that should be read
     * @return
     */

    private ArrayList<ScreenObject> getObjectsListForLevel(int levelNumber, String listName) {
        ArrayList<ScreenObject> dataToReturn = new ArrayList<>();
        if (fileData != null) {
            JsonObject levelData = fileData.getAsJsonObject(Integer.toString(levelNumber));
            JsonArray objectsOnGameScreen = levelData.getAsJsonArray(listName);
            for (int i = 0; i < objectsOnGameScreen.size(); i++) {
                dataToReturn.add(mapToScreen(objectsOnGameScreen.get(i).getAsJsonObject()));
            }
        }
        return dataToReturn;
    }


    /**
     * Method used to map a Json Object to a game object
     *
     * @param jsonElement - the JsonObject representing the data
     * @return
     */

    private ScreenObject mapToScreen(JsonObject jsonElement) {
        String className = jsonElement.get("class").getAsString();
        double x = jsonElement.get("x").getAsDouble();
        double y = jsonElement.get("y").getAsDouble();
        ScreenObject screenObject = factory.createObject(className, x, y);
        if (jsonElement.has("customPush")) {
            ((ElectricObject) screenObject).setPlayerPushForce(jsonElement.get("customPush").getAsDouble());
        }
        return screenObject;
    }

    /**
     * Get the String text representing the hint before the start
     *
     * @param levelNumber
     * @return
     */

    private String getHintBeforeLevel(int levelNumber) {
        JsonObject levelData = fileData.get(Integer.toString(levelNumber)).getAsJsonObject();
        return levelData.get("hintBefore").getAsString();
    }

    /**
     * Get the String text representing the hint after finish
     *
     * @param levelNumber
     * @return
     */

    private String getHintAfterLevel(int levelNumber) {
        JsonObject levelData = fileData.get(Integer.toString(levelNumber)).getAsJsonObject();
        return levelData.get("hintAfter").getAsString();

    }

    /**
     * Static method that returns the number of available levels
     * Method usually used in the GameManager class
     *
     * @return
     */
    public int getNumberOfLevels() {
        if (fileData != null) {
            return fileData.get("numberOfLevels").getAsInt();
        }
        return 0;
    }

    /**
     * Return a player Object from teh
     *
     * @param levelNumber
     * @return
     */
    public Player getPlayerForLevel(int levelNumber) {
        JsonObject levelData = fileData.get(Integer.toString(levelNumber)).getAsJsonObject();
        return (Player) mapToScreen(levelData.get("player").getAsJsonObject());
    }


    public LevelModel getLevelModel(int level) {
        ArrayList<ScreenObject> staticObjects = getObjectsListForLevel(level, "staticObjects");
        ArrayList<ScreenObject> draggableObjects = getObjectsListForLevel(level, "draggableObjects");
        draggableObjects.forEach(objectOnScreen -> objectOnScreen.setHasDragEnabled(true));
        ArrayList<ScreenObject> allObjects = new ArrayList<>();
        allObjects.addAll(staticObjects);
        allObjects.addAll(draggableObjects);


        return new LevelModel(
                allObjects,
                getPlayerForLevel(level),
                getHintAfterLevel(level),
                getHintBeforeLevel(level),
                level
        );
    }
}
