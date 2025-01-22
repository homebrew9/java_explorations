import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

public class FetchUnicodeData {
    public static void main(String args[]) throws SQLException {
        String user       = "test";
        String password   = "test";
        String database   = "test";

        // Open an OracleDataSource and get a connection
        OracleDataSource ods = new OracleDataSource();
        ods.setURL("jdbc:oracle:oci:@" + database);
        ods.setUser(user);
        ods.setPassword(password);
        Connection conn = ods.getConnection();

        String strQuery = "SELECT id, buyer_name FROM buyer";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(strQuery);
        while (rs.next()) {
            System.out.println(rs.getInt(1) + ":" + rs.getString(2));
        }
        stat.close();
        conn.close();
    }
}

