package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;
import javax.sql.DataSource;
import java.sql.*;

public class ItemDaoJdbc implements ItemDao{

    private DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (item_name, x, y, game_state_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getItemName());
            statement.setInt(2, item.getX());
            statement.setInt(3, item.getY());
            statement.setInt(4, 1);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ItemModel item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE item SET item_name = ?, x = ?, y = ? WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, item.getItemName());
            st.setInt(2, item.getX());
            st.setInt(3, item.getY());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT item_name, x, y FROM item WHERE game_state_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            ItemModel item = new ItemModel(rs.getString(1), rs.getInt(2), rs.getInt(3));
            item.setId(id);
            return item;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading author with id: " + id, e);
        }
    }
}
