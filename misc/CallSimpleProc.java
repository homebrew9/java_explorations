/******************************************************************************
File name : CallSimpleProc.java
Desc      : A Java program to call a simple Oracle packaged procedure. This
            procedure takes in a (string) table name, truncates it, and returns
            a (number) sqlcode and (string) sqlerrmessage.
            The Oracle package is as follows -
-- ============================================================================
CREATE OR REPLACE PACKAGE pkg_util
AS
PROCEDURE pr_trunc_table (
  p_table_name    IN       VARCHAR2,
  p_status_code   OUT      NUMBER,
  p_status_mesg   OUT      VARCHAR2
);
END pkg_util;
/
show errors

CREATE OR REPLACE PACKAGE BODY pkg_util
AS
-- Procedure to truncate table passed as input parameter
PROCEDURE pr_trunc_table (
  p_table_name    IN       VARCHAR2,
  p_status_code   OUT      NUMBER,
  p_status_mesg   OUT      VARCHAR2
)
IS
BEGIN
  -- Call the procedure to trucnate table LOSS_DETAIL_QS_SAS_BKUP
  EXECUTE IMMEDIATE 'TRUNCATE TABLE ' || p_table_name;
  p_status_code := 0;
  p_status_mesg := 'TABLE - ' || p_table_name || ' TRUNCATED SUCCESSFULLY';
EXCEPTION
  WHEN OTHERS
  THEN
	p_status_code := SQLCODE;
	p_status_mesg := SUBSTR (SQLERRM, 1, 200);
END;
END pkg_util;
/
show errors
-- ============================================================================
Usage     : java CallSimpleProc <table_name>
Revision  :
27-Apr-10  prat    Created
******************************************************************************/

import java.sql.*;
import oracle.jdbc.*;
import oracle.sql.*;

class CallSimpleProc {
  public static void main(String args[]) throws SQLException {
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
    Connection conn = null;
    CallableStatement cs = null;
    String sqlCode = "";
    String sqlMesg = "";

    // The table to be truncated should be passed from the command line
    // Basic error checking here.
    if (args.length == 0) {
      System.out.println("Usage: java CallSimpleProc <table_name>");
      return;
    } else if (args.length > 1) {
      System.out.println("Correct Usage: java CallSimpleProc <table_name>");
      System.out.println("Only the first table will be passed as input parameter");
    }
    String tableName = args[0];

    try {
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      String URL = "jdbc:oracle:thin:@" + serverName + ":" + port + ":" + SID;
      conn = DriverManager.getConnection(URL, user, password);
      cs = conn.prepareCall ("{ call pkg_util.pr_trunc_table(?, ?, ?) }");

      // Set the IN parameter
      cs.setString(1, tableName);
      // Register the OUT parameters
      cs.registerOutParameter(2, Types.INTEGER);
      cs.registerOutParameter(3, Types.CHAR);
      // Now execute it
      cs.executeUpdate();

      // Capture and print the results
      sqlCode = cs.getString(2);
      sqlMesg = cs.getString(3);
      System.out.println("SQLCODE = "+sqlCode);
      System.out.println("SQLMESG = "+sqlMesg);

      // Close CallableStatement and Connection
      cs.close();
      conn.close();
    } catch (SQLException e) {
      // e.printStackTrace();
      System.out.println("Oracle exception encountered; check logs and investigate => " + e.getMessage());
      System.out.println("SQLCODE = "+sqlCode);
      System.out.println("SQLMESG = "+sqlMesg);
    } finally {
      if(cs != null) cs.close();
      if(conn != null) conn.close();
    }
  }
}
