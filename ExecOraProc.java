import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

/*
 * Name : ExecOraProc.java
 * Desc : A Java program to test if Oracle rolls back uncommitted changes in a
 *        stored procedure upon encountering an exception.
 *        The database schema was prepared as follows:
 *        -----------------------------------------------------------------------
 *        --
 *        drop procedure pr_load_data;
 *        drop table t1;
 *        drop table t2;
 *        drop table t3;
 *        --
 *        create table t1 (x int, y varchar2(1));
 *        create table t2 (x int, y varchar2(1));
 *        create table t3 (x int, y varchar2(1));
 *        alter table t3 add constraint ck1_t3 check (x < 2);
 *        --
 *        create or replace procedure pr_load_data
 *        as
 *        begin
 *            insert into t1(x, y) values (1, 'a');
 *            insert into t1(x, y) values (2, 'b');
 *            insert into t1(x, y) values (3, 'c');
 *            --
 *            insert into t2(x, y) values (1, 'a');
 *            insert into t2(x, y) values (2, 'b');
 *            insert into t2(x, y) values (3, 'c');
 *            --
 *            insert into t3(x, y) values (1, 'a');
 *            -- The next INSERT will throw an exception and the one after it will never be reached.
 *            -- Remove or comment the next two INSERTs if you want a clean, exception-free run.
 *            insert into t3(x, y) values (2, 'b');
 *            insert into t3(x, y) values (3, 'c');
 *        end;
 *        /
 *        show errors
 *        -----------------------------------------------------------------------
 * Usage: java ExecOraProc <schema> <password> <database>
 */

public class ExecOraProc {
    public static void main(String args[]) throws Exception {
        // Prompt the user for connect information
        if (args.length != 3){
            System.out.println("Usage: java ExecOraProc <schema> <password> <database>");
            System.exit(1);
        }
        String user = args[0], password = args[1], database = args[2];
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        CallableStatement cs = null;
        try {
            // Open an OracleDataSource, get a connection and set AutoCommit to false
            OracleDataSource ods = new OracleDataSource();
            ods.setURL("jdbc:oracle:oci:@" + database);
            ods.setUser(user);
            ods.setPassword(password);
            conn = ods.getConnection();
            conn.setAutoCommit(false);
            cs = conn.prepareCall("{call PR_LOAD_DATA}");
            cs.executeQuery();
        } catch (Exception e) {
            System.out.println("Exception during proc execution: " + e.getMessage());
            // It doesn't matter if we commit here. The data loaded in T1,T2,T3 is already
            // lost because the database rolled it back when the exception was encountered.
            conn.commit();
        }

        // At this point, I would like to check the data in tables T1, T2, T3
        // The commit above was ineffective. It did not make any data persistent, because the
        // data was already lost forever by the time the control came back to this JDBC client.
        try {
            System.out.println("Now fetching from table T1");
            int x;
            String y;
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select x, y from t1");
            while (rset.next()){
                x = rset.getInt("X");
                y = rset.getString("Y");
                System.out.println(x + " " + y);
            }
            System.out.println("Now fetching from table T2");
            rset = stmt.executeQuery("select x, y from t2");
            while (rset.next()){
                x = rset.getInt("X");
                y = rset.getString("Y");
                System.out.println(x + " " + y);
            }
            System.out.println("Now fetching from table T3");
            rset = stmt.executeQuery("select x, y from t3");
            while (rset.next()){
                x = rset.getInt("X");
                y = rset.getString("Y");
                System.out.println(x + " " + y);
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Exception during table fetch: " + e.getMessage());
        } finally {
            // Perform house-keeping
            if (rset != null){ rset.close(); }
            if (stmt != null){ stmt.close(); }
            if (cs != null){ cs.close(); }
            if (conn != null){ conn.close(); }
        }
    }
}

