package com.company;

import javafx.scene.canvas.GraphicsContext;

public interface IFactory {


  GameObject createObject(String type,double x,double y);
}
