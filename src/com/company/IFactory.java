package com.company;

public interface IFactory {

  GameObject createObject(String type,double x,double y,boolean hasDragEnabled);
}
