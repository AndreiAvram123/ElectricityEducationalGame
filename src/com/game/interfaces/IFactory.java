package com.game.interfaces;

import com.game.models.ObjectOnScreen;

public interface IFactory {

  ObjectOnScreen createObject(String type, double x, double y);
}
