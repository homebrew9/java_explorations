import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.jdbc.pool.OracleDataSource;

public class NestedTableObj {
    /* We use the STRUCT Class for Oracle Objects */
    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement cstmt = null;
        // ==================================================
        String serverName = "test";
        int port          = 1521;
        String user       = "xxxx";
        String password   = "xxxx";
        String SID        = "test";
        // ==================================================
        try {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL("jdbc:oracle:thin:" + user + "/" + password + "@//" + serverName + ":" + port + "/" + SID);
            conn = ods.getConnection();

            String insertStoreProc = "{call insert_emp(?,?)}";
            cstmt = conn.prepareCall(insertStoreProc);
            final StructDescriptor strDesc = StructDescriptor.createDescriptor("EMP_OBJ", conn);                   
            STRUCT[] str = {
                                new STRUCT(strDesc, conn, new  Object[] {10, "Arkadiy"}),
                                new STRUCT(strDesc, conn, new  Object[] {20, "Boris"}),
                                new STRUCT(strDesc, conn, new  Object[] {30, "Cheslav"}),
                                new STRUCT(strDesc, conn, new  Object[] {40, "Dmitry"}),
                                new STRUCT(strDesc, conn, new  Object[] {50, "Ekaterina"})
                           };
            ArrayDescriptor arrDesc = ArrayDescriptor.createDescriptor("EMP_TAB", conn);  
            ARRAY array_to_pass = new ARRAY(arrDesc, conn, str);
            cstmt.setObject(1, array_to_pass, Types.ARRAY);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.execute();              
            System.out.print(cstmt.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cstmt.close();
                conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}

