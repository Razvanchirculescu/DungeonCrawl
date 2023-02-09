package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.ActorModel;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;


import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

import javax.sql.DataSource;
import java.sql.*;



public class GameDatabaseManager {
    private ActorDaoJdbc actorDao;
    private ItemDaoJdbc itemDao;
    private ActorModel actorModel;
    private GameStateDao gameStateDao;



    public void setup() throws SQLException, FileNotFoundException {
        DataSource dataSource = connect();
        actorDao = new ActorDaoJdbc(dataSource);
        itemDao = new ItemDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Actor player, int gameStateId) {
        ActorModel model = new ActorModel(player);
        actorDao.add(model, gameStateId);
    }

    public void saveItems(Item item, int gameStateId) {
        ItemModel model = new ItemModel(item);
        itemDao.add(model, gameStateId);
    }

    public void saveGameState(String map, String name) {
        GameStateModel model = new GameStateModel(map, name, new Date(System.currentTimeMillis()));
        gameStateDao.add(model);
    }

    public List<ActorModel> listAllActors(String name) {
        return  actorDao.getAll(getGameStateId(name));
    }

    private DataSource connect() throws SQLException, FileNotFoundException {

        String dbName = null;
        String user = null;
        String password = null;


        try {
            File myObj = new File("src/main/java/com/codecool/dungeoncrawl/.env");
            Scanner myReader = new Scanner(myObj);
            for (int i=0; i<3; i++) {
                String data = myReader.nextLine();
                if (i == 0) {
                    dbName = data;
                } else if (i == 1) {
                    user = data;
                } else {
                    password = data;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found!");
            e.printStackTrace();
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }


        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


    public int getGameStateId(String name) {
        return (int) gameStateDao.getId(name);
    }
}
