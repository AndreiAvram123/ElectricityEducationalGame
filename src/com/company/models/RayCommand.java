package com.company.models;

import com.company.GameObject;
import com.company.interfaces.ICommand;
import org.jetbrains.annotations.NotNull;

public class RayCommand implements ICommand {

    private GameObject gameObject;

    //todo
    //every object of such type should have an interface of some type
    public RayCommand(@NotNull GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void _do() {

    }

    @Override
    public void _undo() {

    }
}
