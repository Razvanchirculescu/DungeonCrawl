package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import javax.sql.DataSource;
import java.sql.*;

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
}
