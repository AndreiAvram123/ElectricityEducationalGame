package com.company;

import com.company.models.Finish;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public LevelDataReader(@NotNull GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.factory = new GameObjectsFactory(graphicsContext);
    }

    private FileReader getLevelsDataFileReader() {
        FileReader fileReader = null;
        try {
            String path = new File("").getAbsolutePath();
            String newPath = path.concat("/src/res/levelsData.json");
            fileReader = new FileReader(newPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileReader;
    }


    public ArrayList<GameObject> getObjectsArrayFromJsonFile(String levelNumber,String arrayName) {
        ArrayList<GameObject> dataToReturn = new ArrayList<>();
        FileReader fileReader = getLevelsDataFileReader();
        if (fileReader != null) {
            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonObject levelData = jsonObject.getAsJsonObject(levelNumber);
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
}
