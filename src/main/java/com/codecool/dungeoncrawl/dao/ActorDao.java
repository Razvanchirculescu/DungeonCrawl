package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ActorModel;

public interface ActorDao {
    void add(ActorModel player);
    void update(ActorModel player);
    ActorModel get(int id);
}
