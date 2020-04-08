package com.company.interfaces;

import com.company.models.GameObject;

public interface IFactory {

  GameObject createObject(String type, double x, double y, boolean hasDragEnabled);
}
