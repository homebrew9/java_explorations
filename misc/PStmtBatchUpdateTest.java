/*
 * File name  : PStmtBatchUpdateTest.java
 */
import java.sql.*;
class PStmtBatchUpdateTest {
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
  PreparedStatement pstmt = conn.prepareStatement("INSERT INTO t VALUES (?, ?)");
  pstmt.setInt(1, 4);
  pstmt.setString(2, "4 from pstmt");
  pstmt.addBatch();
  pstmt.setInt(1, 5);
  pstmt.setString(2, "5 from pstmt");
  pstmt.addBatch();
  pstmt.setInt(1, 6);
  pstmt.setString(2, "6 from pstmt");
  pstmt.addBatch();
  // submit the batch for execution
  int[] updateCounts = pstmt.executeBatch();
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
  pstmt.close();
  conn.close();
  }
}
