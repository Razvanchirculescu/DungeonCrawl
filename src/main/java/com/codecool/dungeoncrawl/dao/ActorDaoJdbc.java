package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ActorModel;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE actor SET actor_name = ?, hp = ?, x = ?, y = ? WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, actor.getActorName());
            st.setInt(2, actor.getHp());
            st.setInt(3, actor.getX());
            st.setInt(4, actor.getY());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ActorModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, actor_name, hp, x, y FROM actor";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<ActorModel> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                ActorModel author = new ActorModel(rs.getString(2), rs.getInt(3),
                        rs.getInt(4), rs.getInt(5));
                author.setId(rs.getInt(1));
                result.add(author);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }
}
