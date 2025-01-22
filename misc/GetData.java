/*********************************************************************
* File name  : SimpleOraJava.java
*********************************************************************/

import java.sql.*;

class GetData {
  public static void main(String args[]) throws SQLException {
    DriverManager.registerDriver(
      new oracle.jdbc.driver.OracleDriver()
    );
// ===================================================================================
    String serverName = "test";
    int port = 1521;
    String user = "test";
    String password = "test";
    String SID = "ora10g";
//  =================================================================================== */
    String URL = "jdbc:oracle:thin:@" + serverName + ":" + port + ":" + SID;
    Connection conn = DriverManager.getConnection(URL, user, password);
    String strQuery = "SELECT x FROM t";
    Statement stat = conn.createStatement();
    ResultSet rs = stat.executeQuery(strQuery);
    while (rs.next()) {
      System.out.println(rs.getString("x"));
    }
    stat.close();
    conn.close();
  }
}
