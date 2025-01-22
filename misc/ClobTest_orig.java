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
    String SID        = "ora10g";
    // ==================================================
    int clobSize = 150000;
    StringBuffer str = pad(new StringBuffer("a"),4000,'x');
    while (str.length() < clobSize) {
      StringBuffer token = pad(new StringBuffer("p"),4000,'q');
      str.append(","+token);
    }
    System.out.println("Length of str = "+str.length());
//    System.out.println("str           = "+str);
    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    String URL = "jdbc:oracle:thin:@" + serverName + ":" + port + ":" + SID;
    Connection conn = DriverManager.getConnection(URL, user, password);
    CLOB clob = getCLOB(conn,str.toString());
/*
    String strFnCall = "{ call pr_clob_test(?) }";
    OracleCallableStatement cstmt = (OracleCallableStatement)conn.prepareCall(strFnCall);
    cstmt.registerOutParameter(1, OracleTypes.ARRAY, "EMP_TBL");
    cstmt.execute();
    ARRAY arr = cstmt.getARRAY(1);
    Datum[] empData = arr.getOracleArray();
    System.out.println("\nEMPNO\tENAME");
    System.out.println("======\t==========");
    for (int i = 0; i < empData.length; i++) {
      STRUCT os = (STRUCT)empData[i];
      Object[] a = os.getAttributes();
      System.out.println(a[0]+"\t"+a[1]);
    }
    cstmt.close();
*/
    conn.close();
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
