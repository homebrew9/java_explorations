/*
 * This program has been pulled straight from this URL:
 *   http://theblasfrompas.blogspot.com/2008/04/jdbc-thin-connection-using-tnsnamesora.html
 *
 * Option 1 : System property as command line option
 * -Doracle.net.tns_admin=<ORACLE_CLIENT_NETWORK\ADMIN>
 *
 * Option 2 : System propety within code
 * System.setProperty("oracle.net.tns_admin",
 *                    "<ORACLE_CLIENT_\\NETWORK\\ADMIN>");
 */
import java.sql.*;
import oracle.jdbc.OracleDriver;

public class JdbcThinTnsNamesTest
{ 
  public JdbcThinTnsNamesTest()
  {
    System.setProperty("oracle.net.tns_admin", "path_to_tnsnames.ora");
  }

  public static Connection getConnection() throws SQLException
  {
    String username = "test";
    String password = "test";
    String thinConn = "jdbc:oracle:thin:@test";
    DriverManager.registerDriver(new OracleDriver());
    Connection conn = DriverManager.getConnection(thinConn,username,password);
    conn.setAutoCommit(false);
    return conn;
  }
 
  public void run () throws SQLException
  {
    Connection conn = getConnection();
    System.out.println("Auto Commit = " + conn.getAutoCommit());
    conn.close();
  }
 
  public static void main(String[] args)
  {
    JdbcThinTnsNamesTest test = new JdbcThinTnsNamesTest();
    try
    {
      test.run();
      System.out.println("all done..");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
 
}

