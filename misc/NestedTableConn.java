/*
********************************************************************************
File name  : NestedTableConn.java
********************************************************************************
*/
import java.sql.*;
import oracle.jdbc.*;
import oracle.sql.*;
import oracle.jdbc.pool.OracleDataSource;

class NestedTableConn {
  public static void main(String args[]) throws SQLException {
    // ==================================================
    String serverName = "test";
    int port          = 1521;
    String user       = "test";
    String password   = "test";
    String SID        = "test";
    // ==================================================
    try {
      OracleDataSource ods = new OracleDataSource();
      ods.setURL("jdbc:oracle:thin:" + user + "/" + password + "@//" + serverName + ":" + port + "/" + SID);
      Connection conn = ods.getConnection();
      String[] elements = new String[20];
      // Create an array of 20 string elements
      for (int i=0; i<20; i++){
        elements[i] = "String # "+(i+1);
      }
      // Create an ArrayDescriptor - an objecct of type oracle.sql.ArrayDescriptor
      // that describes the ARRAY object. Only one array descriptor is necessary
      // for a particular collection type.
      // IMPORTANT - in "AMKJPF.TAB_STR", the Oracle schema name is "AMKJPF" and the
      //             nested table type is "TAB_STR", and both of them must be in uppercase.
      ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("AMKJPF.TAB_STR", conn);
      // Create an ARRAY object that we want to pass to our procedure.
      ARRAY myArray = new ARRAY(descriptor, conn, elements);
      CallableStatement cs = conn.prepareCall("begin pr_ntable_test(?); end;");
      // Use the method setArray to pass the array value to the procedure.
      cs.setArray(1, myArray);
      cs.execute();
      conn.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

