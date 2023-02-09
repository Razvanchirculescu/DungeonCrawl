package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ActorModel;

import java.util.List;

public interface ActorDao {
    void add(ActorModel actor);
    void update(ActorModel actor);
    List<ActorModel> getAll();
}
