package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;

public class ItemModel extends BaseModel{

    private String itemName;
    private int x;
    private int y;


    public ItemModel(String itemName, int x, int y) {
        this.itemName = itemName;
        this.x = x;
        this.y = y;
    }

    public ItemModel(Item item) {
        this.itemName = item.getTileName();
        this.x = item.getX();
        this.y = item.getY();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
