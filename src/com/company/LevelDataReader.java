package com.company;

import com.company.models.GameObject;
import com.company.models.ObjectOnScreen;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LevelDataReader {

    private GameObjectsFactory factory;
    private GraphicsContext graphicsContext;
    private JsonObject fileData;
    private static final String RELATIVE_PATH_LEVELS_DATA = "/src/res/levelsData.json";

    public LevelDataReader(@NotNull GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.factory = new GameObjectsFactory(graphicsContext);
        this.fileData = getFileData();
    }

    private JsonObject getFileData() {
        JsonObject data = null;
        try {

            String path = new File("").getAbsolutePath();
            String newPath = path.concat(RELATIVE_PATH_LEVELS_DATA);
            FileReader fileReader = new FileReader(newPath);
            data = JsonParser.parseReader(fileReader).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public ArrayList<ObjectOnScreen> getObjectsArrayFromJsonFile(int levelNumber, String arrayName) {
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


    private ObjectOnScreen mapToGameObject(JsonObject jsonElement) {
        String className = jsonElement.get("class").getAsString();
        double x = jsonElement.get("x").getAsDouble();
        double y = jsonElement.get("y").getAsDouble();
        boolean hasDragEnabled = jsonElement.get("hasDragEnabled").getAsBoolean();
        return factory.createObject(className, x, y, hasDragEnabled);
    }

    public String getHintBeforeStart(int levelNumber) {
        JsonObject levelData = fileData.get(Integer.toString(levelNumber)).getAsJsonObject();
        return levelData.get("hintBefore").getAsString();
    }

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
    public static int getNumberOfLevels() {
        JsonObject data = null;
        try {
            String path = new File("").getAbsolutePath();
            String newPath = path.concat(RELATIVE_PATH_LEVELS_DATA);
            FileReader fileReader = new FileReader(newPath);
            data = JsonParser.parseReader(fileReader).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null) {
            return data.get("numberOfLevels").getAsInt();
        }
        return 0;
    }
}
