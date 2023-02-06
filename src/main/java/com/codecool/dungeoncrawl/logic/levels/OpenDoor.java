package com.codecool.dungeoncrawl.logic.levels;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

public class OpenDoor extends Item {

    public OpenDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "openDoor";
    }
}