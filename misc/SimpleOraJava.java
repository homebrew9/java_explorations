/*********************************************************************
* File name  : SimpleOraJava.java
*********************************************************************/

import java.sql.*;

class SimpleOraJava {
  public static void main(String args[]) throws SQLException {
    DriverManager.registerDriver(
      new oracle.jdbc.driver.OracleDriver()
    );
/*  String user = "test";
    String password = "test";
    String URL = "test"; */
/*  String user = "test";
    String password = "test";
    String URL = "test"; */
//    String URL = "jdbc:oracle:thin:@" + serverName + ":" + port + ":" + SID;

    String user = "test";
    String password = "test";
    String URL = "test";
    Connection conn = DriverManager.getConnection(URL, user, password);
    String SQL = "SELECT 1 as x, to_char(sysdate,'mm/dd/yyyy hh24:mi:ss') as y FROM dual";
    Statement stat = conn.createStatement();
    ResultSet rs = stat.executeQuery(SQL);
    while (rs.next()) {
      System.out.println(
        rs.getInt(1) +
        "\t" +
        rs.getString(2)
      );
    }
    stat.close();
    conn.close();
  }
}
