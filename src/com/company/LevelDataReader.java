package com.company;

import com.company.interfaces.IFactory;
import com.company.models.ObjectOnScreen;
import com.company.models.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
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
    public LevelDataReader(@NotNull IFactory factory) {
        this.fileData = getFileData();
        this.factory = factory;
    }

    private JsonObject getFileData() {
        JsonObject data = null;
        try {
            InputStream initialStream = getClass().getResourceAsStream("levelsData.json");
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);
            File targetFile = new File("targetFile.tmp");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            FileReader fileReader = new FileReader(targetFile);
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
     * @param arrayName   - the name of the array that should be read
     * @return
     */
    @NotNull
    public ArrayList<ObjectOnScreen> getObjectsArrayFromJsonFile(int levelNumber, @NotNull String arrayName) {
        ArrayList<ObjectOnScreen> dataToReturn = new ArrayList<>();
        if (fileData != null) {
            JsonObject levelData = fileData.getAsJsonObject(Integer.toString(levelNumber));
            JsonArray objectsOnGameScreen = levelData.getAsJsonArray(arrayName);
            for (int i = 0; i < objectsOnGameScreen.size(); i++) {
                dataToReturn.add(mapToGameObject(objectsOnGameScreen.get(i).getAsJsonObject()));
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
    @NotNull
    private ObjectOnScreen mapToGameObject(@NotNull JsonObject jsonElement) {
        String className = jsonElement.get("class").getAsString();
        double x = jsonElement.get("x").getAsDouble();
        double y = jsonElement.get("y").getAsDouble();
        return factory.createObject(className, x, y);
    }

    /**
     * Get the String text representing the hint before the start
     *
     * @param levelNumber
     * @return
     */
    @NotNull
    public String getHintBeforeStart(int levelNumber) {
        JsonObject levelData = fileData.get(Integer.toString(levelNumber)).getAsJsonObject();
        return levelData.get("hintBefore").getAsString();
    }

    /**
     * Get the String text representing the hint after finish
     *
     * @param levelNumber
     * @return
     */
    @NotNull
    public String getHintAfterFinish(int levelNumber) {
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
    public Player getPlayerFromJsonFile(int levelNumber) {
        JsonObject levelData = fileData.get(Integer.toString(levelNumber)).getAsJsonObject();
        return (Player) mapToGameObject(levelData.get("player").getAsJsonObject());
    }
}
