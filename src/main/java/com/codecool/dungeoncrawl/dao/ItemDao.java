package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;


public interface ItemDao {
    void add(ItemModel item);
    void update(ItemModel player);
    ItemModel get(int id);
}
