/*
********************************************************************************
File name  : ClobTest.java
********************************************************************************
*/
import java.sql.*;
import oracle.jdbc.*;
import oracle.sql.*;
import java.io.Writer;
class ClobTest {
  public static void main(String args[]) throws SQLException {
    // ==================================================
    String serverName = "test";
    int port          = 1521;
    String user       = "test";
    String password   = "test";
    String SID        = "ora11g";
    // ==================================================
    // generate a huge string of approximately 150,000 characters
    int clobSize = 150000;
    int tokenSize = 4000;
    StringBuffer str = pad(new StringBuffer("a"),tokenSize,'x');
    while (str.length() < clobSize) {
      StringBuffer token = pad(new StringBuffer("p"),tokenSize,'q');
      str.append(","+token);
    }
    // database interaction over here
    try {
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      String URL = "jdbc:oracle:thin:@" + serverName + ":" + port + ":" + SID;
      Connection conn = DriverManager.getConnection(URL, user, password);
      // Create the CLOB object from the getCLOB method
      CLOB clob = getCLOB(conn,str.toString());
      System.out.println("Length of clob = "+clob.length());
      String strPrCall = "{ call pr_clob_test(?) }";
      OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall(strPrCall);
      cstmt.setObject(1,clob);
      cstmt.execute();
      cstmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // method to keep on padding the character "padChar" to the string "sb"
  // till its size is "size".
  public static StringBuffer pad(StringBuffer sb, int size, char padChar) {
    while (sb.length() < size) { sb.append(padChar); }
    return sb;
  }

  //method to create the CLOB object using temporary clob
  public static CLOB getCLOB (Connection conn, String clobData) throws Exception {
    CLOB tempClob = null;
    try {
      //  create a new temporary CLOB
      tempClob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);
      // Open the temporary CLOB in readwrite mode to enable writing
      tempClob.open(CLOB.MODE_READWRITE);
      // Get the output stream to write
      Writer tempClobWriter = tempClob.setCharacterStream(0L);
      // Write the data into the temporary CLOB
      tempClobWriter.write(clobData);
      // Flush and close the stream
      tempClobWriter.flush();
      tempClobWriter.close();
      // Close the temporary CLOB
      tempClob.close();
    } catch (Exception exp) {
      // Free CLOB object and do something
      tempClob.freeTemporary();
    }
    return tempClob;
  }
}
