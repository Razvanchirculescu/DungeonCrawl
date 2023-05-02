package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class GameMap {
    private int mapWidth;

    private int mapHeight;
    private int displayWidth;
    private int displayHeight;
    private Cell[][] cells;

    private Player player;


    public GameMap(int width, int height, int displayWidth, int displayHeight, CellType defaultCellType) {
        this.mapWidth = width;
        this.mapHeight = height;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }


}
