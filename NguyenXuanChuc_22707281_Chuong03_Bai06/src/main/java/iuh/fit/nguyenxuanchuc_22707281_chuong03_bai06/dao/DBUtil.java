package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai06.dao;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DBUtil {
    private static DataSource ds;
    static {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/QUANLYDANHMUC");
        } catch (Exception e) {
            throw new RuntimeException("Lỗi lấy DataSource", e);
        }
    }
    public static Connection getConnection() throws Exception {
        return ds.getConnection();
    }
}
