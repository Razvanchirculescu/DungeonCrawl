package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ActorModel;
import javax.sql.DataSource;
import java.sql.*;

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
    public ActorModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT actor_name, hp, x, y FROM actor WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ActorModel actor = new ActorModel(rs.getString(1), rs.getInt(2),
                    rs.getInt(3), rs.getInt(4));
            actor.setId(id);
            return actor;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading author with id: " + id, e);
        }
    }
}
