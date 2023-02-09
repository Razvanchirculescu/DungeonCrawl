package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import java.util.List;


public interface ItemDao {
    void add(ItemModel item);
    void update(ItemModel item);
    List<ItemModel> getAll();
}
