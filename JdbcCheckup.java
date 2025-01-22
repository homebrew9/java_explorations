/*
 * This sample can be used to check the JDBC installation.
 * Just run it and provide the connect information. It will select
 * "Hello World" from the database.
 */

// You need to import the java.sql and JDBC packages to use JDBC
import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

// We import java.io to be able to read from the command line
import java.io.*;

class JdbcCheckup {
  public static void main(String args[]) throws SQLException, IOException {

    // Prompt the user for connect information
    System.out.println("Please enter information to test connection to the database");
    String user;
    String password;
    String database;

    user = readEntry("User                      : ");
    int slash_index = user.indexOf('/');
    if (slash_index != -1) {
      password = user.substring(slash_index + 1);
      user = user.substring(0, slash_index);
    } else {
      // password = readEntry("Password                  : ");
      password = readStdInput("Password                  : ", "y");
    }
    database = readEntry("Database (a TNSNAME entry): ");

    System.out.print("Connecting to the database...\n");
    System.out.flush();
    // Open an OracleDataSource and get a connection
    OracleDataSource ods = new OracleDataSource();
    ods.setURL("jdbc:oracle:oci8:@" + database);

    ods.setUser(user);
    ods.setPassword(password);
    Connection conn = ods.getConnection();
    System.out.println("Connected.");

    // Create a statement
    Statement stmt = conn.createStatement();

    // Do the SQL "Hello World" thing
    //ResultSet rset = stmt.executeQuery("select 'Hello World' || from dual");
    ResultSet rset = stmt.executeQuery("select 'Hello World! from ' || user ||'@'|| global_name || ' at ' || to_char(sysdate, 'DD-Mon-YYYY HH:MI:SS AM') from global_name");

    while (rset.next())
      System.out.println(rset.getString(1));
    // close the result set, the statement and the connection
    rset.close();
    stmt.close();
    conn.close();
    System.out.println("Your JDBC installation is correct.");
  }

  // Utility function to read a line from standard input
  static String readEntry(String prompt)
  {
    try
    {
      StringBuffer buffer = new StringBuffer();
      System.out.print(prompt);
      System.out.flush();
      int c = System.in.read();
      while (c != '\n' && c != -1)
      {
        buffer.append((char)c);
        c = System.in.read();
      }
      return buffer.toString().trim();
    }
    catch(IOException e)
    {
      return "";
    }
  }

  // Utility function to read a line from standard input using the java.io.Console class
  // Note that this is available only from Java 6 onwards. - prat, 7/3/2014
  static String readStdInput (String prompt, String isMasked) {
    String strBuffer;
    try {
      Console cons = System.console();
      if (isMasked.equalsIgnoreCase("y")) {
          char buffer[] = cons.readPassword(prompt);
          strBuffer = new String(buffer);
      } else {
          strBuffer = cons.readLine(prompt);
      }
      return strBuffer;
    }
    catch(Exception e) {
      return "";
    }
  }
}

