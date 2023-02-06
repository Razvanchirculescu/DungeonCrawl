package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class CreepyBug extends Actor {

    public CreepyBug(Cell cell) {
        super(cell);
        this.setHealth(20);
        this.setDamage(5);
    }

    @Override
    public String getTileName() {
        return "creepyBug";
    }
}
