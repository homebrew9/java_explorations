/*
 * File name  : StmtBatchUpdateTest.java
 */
import java.sql.*;
class StmtBatchUpdateTest {
  public static void main(String args[]) throws SQLException {
  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
  // ===================================================================================
  String serverName = "test";
  int port = 1521;
  String user = "test";
  String password = "test";
  String SID = "ora11g";
  // ===================================================================================
  String URL = "jdbc:oracle:thin:@" + serverName + ":" + port + ":" + SID;
  Connection conn = DriverManager.getConnection(URL, user, password);
  // turn off autocommit
  conn.setAutoCommit(false);
  Statement stmt = conn.createStatement();
  stmt.addBatch("INSERT INTO t (x, y) VALUES (1, '1 from stmt')");
  stmt.addBatch("INSERT INTO t (x, y) VALUES (2, '2 from stmt')");
  stmt.addBatch("INSERT INTO t (x, y) VALUES (3, '3 from stmt')");
  // submit a batch of update commands for execution
  int[] updateCounts = stmt.executeBatch();
  // commit
  conn.commit();
  // check the values of updateCounts of each statement of the batch
  // According to JDBC 2.1 Specification at http://java.sun.com/products/jdbc/download.html#corespec21
  // if return value = RV, then
  // RV >= 0 means success and value of RV = number of rows affected
  // RV = -2 means success but number of affected rows is unknown
  // RV = -3 means failure in execution or batch element could not be processed
  for (int i=0; i<=updateCounts.length-1; i++) {
    System.out.println("Return updateCount from statement # "+(i+1)+" of the batch = "+updateCounts[i]);
  }
  stmt.close();
  conn.close();
  }
}
