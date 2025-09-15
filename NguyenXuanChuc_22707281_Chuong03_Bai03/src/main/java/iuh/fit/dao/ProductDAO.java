package iuh.fit.dao;

import iuh.fit.model.Product;
import iuh.fit.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {
    private final DBUtil dbUtil;

    public ProductDAO(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource cannot be null");
        }
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("ID"),
                        rs.getString("MODEL"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("QUANTITY"),
                        rs.getDouble("PRICE"),
                        rs.getString("IMGURL")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving products", e);
        }
        return list;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE ID=?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("ID"),
                            rs.getString("MODEL"),
                            rs.getString("DESCRIPTION"),
                            rs.getInt("QUANTITY"),
                            rs.getDouble("PRICE"),
                            rs.getString("IMGURL"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}