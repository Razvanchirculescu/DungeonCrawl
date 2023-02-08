package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ActorModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class ActorDaoJdbc implements ActorDao {
    private DataSource dataSource;

    public ActorDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ActorModel actor) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO actor (actor_name, hp, x, y, game_state_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, actor.getActorName());
            statement.setInt(2, actor.getHp());
            statement.setInt(3, actor.getX());
            statement.setInt(4, actor.getY());
            statement.setInt(5, 1);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            actor.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ActorModel actor) {

    }

    @Override
    public ActorModel get(int id) {
        return null;
    }

    @Override
    public List<ActorModel> getAll() {
        return null;
    }
}
