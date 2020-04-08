package com.company.interfaces;

import com.company.models.GameObject;
import com.company.models.ObjectOnScreen;

public interface IFactory {

  ObjectOnScreen createObject(String type, double x, double y, boolean hasDragEnabled);
}
