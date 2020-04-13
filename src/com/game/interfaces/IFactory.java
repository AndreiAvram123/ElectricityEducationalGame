package com.game.interfaces;

import com.game.models.ScreenObject;

public interface IFactory {

  ScreenObject createObject(String type, double x, double y);
}
