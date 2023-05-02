package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class BluePotion extends Item {


    public BluePotion(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "bluePotion";
    }
}
