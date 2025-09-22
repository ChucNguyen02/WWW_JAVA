package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.dao;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.model.Employee;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class EmployeeDAO {

    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public List<Employee> getByDepartment(int deptId) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE department_id=? ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public Employee findById(int id) {
        String sql = "SELECT * FROM employee WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) { throw new RuntimeException(e); }
        return null;
    }

    public void insert(Employee e) {
        String sql = "INSERT INTO employee(name,email,phone,position,salary,department_id) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, e, false);
            ps.executeUpdate();
        } catch (Exception ex) { throw new RuntimeException(ex); }
    }

    public void update(Employee e) {
        String sql = "UPDATE employee SET name=?,email=?,phone=?,position=?,salary=?,department_id=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, e, true);
            ps.executeUpdate();
        } catch (Exception ex) { throw new RuntimeException(ex); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("position"),
                rs.getBigDecimal("salary"),
                rs.getInt("department_id")
        );
    }

    private void setParams(PreparedStatement ps, Employee e, boolean includeId) throws SQLException {
        ps.setString(1, e.getName());
        ps.setString(2, e.getEmail());
        ps.setString(3, e.getPhone());
        ps.setString(4, e.getPosition());
        ps.setBigDecimal(5, e.getSalary());
        if (e.getDepartmentId() > 0)
            ps.setInt(6, e.getDepartmentId());
        else
            ps.setNull(6, Types.INTEGER);
        if (includeId) ps.setInt(7, e.getId());
    }
}
