package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ActorModel;
import com.codecool.dungeoncrawl.model.ItemModel;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoJdbc implements ItemDao{

    private DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel item, int gameStateId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (item_name, x, y, game_state_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getItemName());
            statement.setInt(2, item.getX());
            statement.setInt(3, item.getY());
            statement.setInt(4, gameStateId);
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
    public List<ItemModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, item_name, x, y FROM item";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<ItemModel> result = new ArrayList<>();
            while (rs.next()) { // while result set pointer is positioned before or on last row read authors
                ItemModel item = new ItemModel(rs.getString(2), rs.getInt(3),
                        rs.getInt(4));
                item.setId(rs.getInt(1));
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all items", e);
        }
    }
}
