package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.model.DanhMuc;
import java.sql.*;
import java.util.*;

public class DanhMucDAO {
    public List<DanhMuc> getAll() {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM DANHMUC ORDER BY MADM";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DanhMuc(rs.getInt("MADM"), rs.getString("TENDANHMUC"), rs.getString("NGUOIQUANLY"), rs.getString("GHICHU")));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public DanhMuc findById(int madm) {
        String sql = "SELECT * FROM DANHMUC WHERE MADM=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, madm);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new DanhMuc(rs.getInt("MADM"), rs.getString("TENDANHMUC"), rs.getString("NGUOIQUANLY"), rs.getString("GHICHU"));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return null;
    }
}
