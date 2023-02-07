package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;


import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import javax.sql.DataSource;
import java.sql.SQLException;



public class GameDatabaseManager {
    private PlayerDao playerDao;

    public void setup() throws SQLException, FileNotFoundException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
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
}
