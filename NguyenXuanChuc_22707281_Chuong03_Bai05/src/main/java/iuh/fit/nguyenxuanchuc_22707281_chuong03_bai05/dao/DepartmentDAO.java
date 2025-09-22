package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.dao;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.model.Department;

import java.sql.*;
import java.util.*;

public class DepartmentDAO {

    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM department ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Department(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("note")));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public Department findById(int id) {
        String sql = "SELECT * FROM department WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Department(rs.getInt("id"), rs.getString("name"), rs.getString("note"));
        } catch (Exception e) { throw new RuntimeException(e); }
        return null;
    }

    public void insert(Department d) {
        String sql = "INSERT INTO department(name, note) VALUES (?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getNote());
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void update(Department d) {
        String sql = "UPDATE department SET name=?, note=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getNote());
            ps.setInt(3, d.getId());
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM department WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public List<Department> search(String keyword) {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM department WHERE name LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Department(rs.getInt("id"), rs.getString("name"), rs.getString("note")));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }
}
