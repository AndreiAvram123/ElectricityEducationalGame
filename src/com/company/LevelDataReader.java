package com.company;

import com.company.models.Finish;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class LevelDataReader {

    private GameObjectsFactory factory;
    private GraphicsContext graphicsContext;
    private JsonObject fileData;

    public LevelDataReader(@NotNull GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.factory = new GameObjectsFactory(graphicsContext);
        this.fileData = getFileData();
    }

    private JsonObject getFileData() {
        JsonObject data = null;
        try {

            String path = new File("").getAbsolutePath();
            String newPath = path.concat("/src/res/levelsData.json");
            FileReader fileReader = new FileReader(newPath);
            data = JsonParser.parseReader(fileReader).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public ArrayList<GameObject> getObjectsArrayFromJsonFile(int levelNumber, String arrayName) {
        ArrayList<GameObject> dataToReturn = new ArrayList<>();
        if (fileData != null) {
            JsonObject levelData = fileData.getAsJsonObject(Integer.toString(levelNumber));
            JsonArray objectsOnGameScreen = levelData.getAsJsonArray(arrayName);
            for (int i = 0; i < objectsOnGameScreen.size(); i++) {
                dataToReturn.add(mapToGameObject(objectsOnGameScreen.get(i).getAsJsonObject()));
            }
        }
        return dataToReturn;
    }


    private GameObject mapToGameObject(JsonObject jsonElement) {
        String className = jsonElement.get("class").getAsString();
        double x = jsonElement.get("x").getAsDouble();
        double y = jsonElement.get("y").getAsDouble();
        boolean hasDragEnabled = jsonElement.get("hasDragEnabled").getAsBoolean();
        switch (className) {
            case "finish":
                return Finish.getInstance(x, y, graphicsContext);
            case "player":
                return Player.createInstance(x, y, graphicsContext);
            default:
                return factory.createObject(className, x, y, hasDragEnabled);
        }
    }

    public String getHintBeforeStart(int levelNumber) {
        JsonObject levelData = fileData.get(Integer.toString(levelNumber)).getAsJsonObject();
        String hintBeforeStart = levelData.get("hintBefore").getAsString();
        return hintBeforeStart;
    }
}
