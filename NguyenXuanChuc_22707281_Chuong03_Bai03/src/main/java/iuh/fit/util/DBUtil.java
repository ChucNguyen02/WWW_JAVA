package iuh.fit.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private DataSource dataSource;

    public DBUtil(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
