package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.ActorModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class GameDatabaseManager {
    private ActorDaoJdbc actorDao;
    private ActorModel actorModel;



    public void setup() throws SQLException {
        DataSource dataSource = connect();
        actorDao = new ActorDaoJdbc(dataSource);
    }

    public void savePlayer(Actor player) {
        ActorModel model = new ActorModel(player);
        actorDao.add(model);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "dungeonCrawl";
        String user = "postgres";
        String password = "Geogra1.";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


}
