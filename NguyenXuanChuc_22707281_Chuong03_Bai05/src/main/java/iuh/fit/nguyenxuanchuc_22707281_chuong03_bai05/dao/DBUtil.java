package iuh.fit.nguyenxuanchuc_22707281_chuong03_bai05.dao;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DBUtil {
    private static DataSource dataSource;

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/companydb");
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy DataSource", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
