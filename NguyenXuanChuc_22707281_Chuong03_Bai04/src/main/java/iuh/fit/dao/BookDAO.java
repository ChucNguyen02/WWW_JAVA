package iuh.fit.dao;

import iuh.fit.model.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final DataSource dataSource;

    public BookDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book mapResultSet(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                rs.getString("imgURL"),
                rs.getString("description")
        );
    }
}
