/*********************************************************************
  File name  : GetEmpData.java
*********************************************************************/

import java.sql.*;
import oracle.jdbc.*;
import oracle.sql.*;

class GetEmpData {
  public static void main(String args[]) throws SQLException {
    DriverManager.registerDriver(
      new oracle.jdbc.driver.OracleDriver()
    );
/* ==================================================
    String serverName = "test";
    int port          = 1521;
    String user       = "test";
    String password   = "test";
    String SID        = "ora10g";
// ================================================*/
// ==================================================
    String serverName = "test";
    int port          = 1521;
    String user       = "test";
    String password   = "test";
    String SID        = "ora11g";
// ================================================*/
    String URL = "jdbc:oracle:thin:@" + serverName + ":" + port + ":" + SID;
    Connection conn = DriverManager.getConnection(URL, user, password);
    String strFnCall = "{ call pr_get_emp_array(?) }";
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
    conn.close();
  }
}

