package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao;

import iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.model.TinTuc;
import java.sql.*;
import java.util.*;

public class DanhSachTinTucQuanLy {
    public List<TinTuc> getAll() {
        List<TinTuc> list = new ArrayList<>();
        String sql = "SELECT * FROM TINTUC ORDER BY MATT";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public List<TinTuc> getByDanhMuc(int madm) {
        List<TinTuc> list = new ArrayList<>();
        String sql = "SELECT * FROM TINTUC WHERE MADM=? ORDER BY MATT";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, madm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public void insert(TinTuc t) {
        String sql = "INSERT INTO TINTUC (TIEUDE, NOIDUNGTT, LIENKET, MADM) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getTieude());
            ps.setString(2, t.getNoidungtt());
            ps.setString(3, t.getLienket());
            if (t.getMadm() > 0) ps.setInt(4, t.getMadm()); else ps.setNull(4, Types.INTEGER);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void delete(int matt) {
        String sql = "DELETE FROM TINTUC WHERE MATT=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, matt);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public TinTuc findById(int matt) {
        String sql = "SELECT * FROM TINTUC WHERE MATT=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, matt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return null;
    }

    private TinTuc mapRow(ResultSet rs) throws SQLException {
        return new TinTuc(rs.getInt("MATT"), rs.getString("TIEUDE"), rs.getString("NOIDUNGTT"), rs.getString("LIENKET"), rs.getInt("MADM"));
    }
}
