import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

class JDBCVersion
{
  public static void main (String args[]) throws SQLException
  {
    OracleDataSource ods = new OracleDataSource();
    // The following also works as of 3/12/2018
    // Unfortunately "SQLcl" does not work despite using the same Java thin driver and the URL below!!
    // Ok, I was able to fix the SQLcl problem! - prat, 3/13/2018
    ods.setUser("test");
    ods.setPassword("test");  // As of today - prat, 3/20/2019
    ods.setURL("jdbc:oracle:thin:@test");

    // Note that the following "setURL" does not work with JDBC Thin Driver.
    // It cannot use the SID to lookup the tnsnames.ora!! More discussion of this problem is at:
    //     https://coderanch.com/t/444830/databases/read-values-tnsnames-ora-file
    // ================================================================================================

    Connection conn = ods.getConnection();

    // Create Oracle DatabaseMetaData object
    DatabaseMetaData meta = conn.getMetaData();

    // gets driver info:
    System.out.println("================================================================================");
    System.out.println("DATABASE METADATA");
    System.out.println("================================================================================");
    System.out.println("Major JDBC version number for this driver  = " + meta.getJDBCMajorVersion());
    System.out.println("Minor JDBC version number for this driver  = " + meta.getJDBCMinorVersion());
    System.out.println("JDBC driver name                           = " + meta.getDriverName());
    System.out.println("JDBC driver major version                  = " + meta.getDriverMajorVersion());
    System.out.println("JDBC driver minor version                  = " + meta.getDriverMinorVersion());
    System.out.println("JDBC driver version                        = " + meta.getDriverVersion());
    System.out.println("Database Major version                     = " + meta.getDatabaseMajorVersion());
    System.out.println("Database Minor version                     = " + meta.getDatabaseMinorVersion());
    System.out.println("Database Product Name                      = " + meta.getDatabaseProductName());
    System.out.println("Database Product Version                   = ");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println(meta.getDatabaseProductVersion());
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println("Default Transaction Isolation Level        = " + meta.getDefaultTransactionIsolation());
    System.out.println("Max no. of chars in SQL stmt allowed by db = " + meta.getMaxStatementLength());
    System.out.println("Max no. of concurrent connections to db    = " + meta.getMaxConnections());
    System.out.println("Database supports batch updates            = " + meta.supportsBatchUpdates());
    System.out.println("Db allows mult open trx open on diff conn  = " + meta.supportsMultipleTransactions());
    System.out.println("Db stores tables in a local file           = " + meta.usesLocalFiles());
    System.out.println("URL for this DBMS                          = ");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println(meta.getURL());
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println("================================================================================");

    // Create a statement
    Statement stmt = conn.createStatement();

    // Do the SQL "Hello World" thing
    //ResultSet rset = stmt.executeQuery("select 'Hello World' || from dual");
    //ResultSet rset = stmt.executeQuery("select 'Hello World! from ' || user ||'@'|| global_name || ' at ' || to_char(sysdate, 'DD-Mon-YYYY HH:MI:SS AM') from global_name");
    // Changed to a more informative query. - prat, 1/30/2017
    ResultSet rset = stmt.executeQuery(
       "select 'Hello World! from ' || user ||'@'|| global_name as blurb from global_name union all " +
       "select rpad('=',80,'=') from dual union all " +
       "select 'Timestamp on server = ' || systimestamp from dual union all " +
       "select 'Timestamp on client = ' || localtimestamp from dual union all " +
       "select rpad('=',80,'=') from dual union all " +
       "select 'Oracle version on server:' from dual union all " +
       "select rpad('=',80,'=') from dual union all " +
       "select banner from v$version union all " +
       "select rpad('=',80,'=') from dual");
    while (rset.next())
      System.out.println(rset.getString(1));
    // close the result set, the statement and the connection
    rset.close();
    stmt.close();
    conn.close();
    System.out.println("Your JDBC installation is correct.");

  }
}

