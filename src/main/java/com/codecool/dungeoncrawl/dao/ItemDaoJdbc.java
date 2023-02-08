package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

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

    }

    @Override
    public ItemModel get(int id) {
        return null;
    }

    @Override
    public List<ItemModel> getAll() {
        return null;
    }
}
