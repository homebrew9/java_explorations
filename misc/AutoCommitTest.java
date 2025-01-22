/******************************************************************************
File: AutoCommitTest.java
Desc: Oracle table T is empty. Run the following DDL command before trying out
      this Java program.
      CREATE TABLE T (X NUMBER, Y VARCHAR2(1), Z DATE);
      What happens you write to a database and exit without committing? Of
      course, if autocommit is set to TRUE, then the changes are committed.
      What if autocommit is set to FALSE? Does the client commit or rollback
      the changes?
By:   prat
On:   16-Jul-2010
******************************************************************************/
import java.sql.*;
class AutoCommitTest {
  public static void main(String args[]) throws SQLException {
    // Connection information
    Connection conn = null;
    PreparedStatement pstmt = null;
    String user = "test";
    String password = "test";
    String URL = "test";
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    conn = DriverManager.getConnection(URL, user, password);
    conn.setAutoCommit(false);   // set autocommit to FALSE; it is TRUE by default
    pstmt = conn.prepareStatement("INSERT INTO T (X, Y, Z) VALUES (?, ?, ?)");
    // 1st record
    pstmt.setInt(1, 1);
    pstmt.setString(2, "a");
    pstmt.setDate(3, Date.valueOf("2009-12-20"));
    pstmt.executeUpdate();
    // 2nd record
    pstmt.setInt(1, 2);
    pstmt.setString(2, "b");
    pstmt.setDate(3, Date.valueOf("2009-12-21"));
    pstmt.executeUpdate();
    pstmt.close();
    conn.close();
  }
}
