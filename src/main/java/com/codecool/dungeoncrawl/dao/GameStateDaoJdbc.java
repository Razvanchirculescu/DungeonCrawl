package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;
import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(GameStateModel state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, save_name) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getCurrentMap());
            statement.setString(2, state.getSavedName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameStateModel state, String saveName) {
    }

    @Override
    public GameStateModel get(int id) {
        return null;
    }

    public Object getId(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM game_state WHERE save_name = " + "'"+name+"'";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return rs.getObject(1);
        } catch (SQLException e) {
            throw new RuntimeException("Does not exist: " + name, e);
        }
    }

    @Override
    public List<String> getAllNames() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT save_name FROM game_state";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<String> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                String name = rs.getString(1);
                result.add(name);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all names" , e);
        }
    }
}
