package com.game.interfaces;

import com.game.models.Player;

public class MovePlayerCommand implements ICommand {
    private Player player;

    public MovePlayerCommand(Player player) {
        this.player = player;
    }

    @Override
    public void _do() {

    }

    @Override
    public void _undo() {

    }
}
