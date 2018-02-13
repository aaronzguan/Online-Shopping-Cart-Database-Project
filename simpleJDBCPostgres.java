/**
 * Make sure the Postgresql JDBC driver is in your classpath.
 * You can download the JDBC 4 driver from here if required.
 * https://jdbc.postgresql.org/download.html
 *
 * take care of the variables usernamestring and passwordstring to use 
 * appropriate database credentials before you compile !
 *
**/

import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;



class simpleJDBC
{
    public static void main ( String [ ] args ) throws SQLException
    {
	// Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
	String tableName = "";
        int sqlCode=0;      // Variable to hold SQLCODE
        String sqlState="00000";  // Variable to hold SQLSTATE

	if ( args.length > 0 ){
	    tableName += args [ 0 ] ;
	}
	else {
	    tableName += "example3.tbl";
	}
	
	
	// Register the driver.  You must register the driver before you can use it.
    try {
    DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
} catch (Exception cnfe){
    System.out.println("Class not found");
    }

// This is the url you must use for Postgresql.
//Note: This url may not valid now !
String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
Connection con = DriverManager.getConnection (url,usernamestring, passwordstring) ;
Statement statement = con.createStatement ( ) ;

	// Creating a table
	try {
	    String createSQL = "CREATE TABLE " + tableName + " (id INTEGER, name VARCHAR (25)) ";
	    System.out.println (createSQL ) ;
	    statement.executeUpdate (createSQL ) ;
	    System.out.println ("DONE");
	}catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                
                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }

	// Inserting Data into the table
	try {
	    String insertSQL = "INSERT INTO " + tableName + " VALUES ( 1 , \'Vicki\' ) " ;
	    System.out.println ( insertSQL ) ;
	    statement.executeUpdate ( insertSQL ) ;
	    System.out.println ( "DONE" ) ;

	    insertSQL = "INSERT INTO " + tableName + " VALUES ( 2 , \'Vera\' ) " ;
	    System.out.println ( insertSQL ) ;
	    statement.executeUpdate ( insertSQL ) ;
	    System.out.println ( "DONE" ) ;
	    insertSQL = "INSERT INTO " + tableName + " VALUES ( 3 , \'Franca\' ) " ;
	    System.out.println ( insertSQL ) ;
	    statement.executeUpdate ( insertSQL ) ;
	    System.out.println ( "DONE" ) ;

	} catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                
                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }

	// Querying a table
	try {
	    String querySQL = "SELECT id, name from " + tableName + " WHERE NAME = \'Vicki\'";
	    System.out.println (querySQL) ;
	    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
	    while ( rs.next ( ) ) {
		int id = rs.getInt ( 1 ) ;
		String name = rs.getString (2);
		System.out.println ("id:  " + id);
		System.out.println ("name:  " + name);
	    }
	    System.out.println ("DONE");
	} catch (SQLException e)
	    {
		sqlCode = e.getErrorCode(); // Get SQLCODE
		sqlState = e.getSQLState(); // Get SQLSTATE
                
		// Your code to handle errors comes here;
		// something more meaningful than a print would be good
		System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	    }

	//Updating a table
    	try {
	    String updateSQL = "UPDATE " + tableName + " SET NAME = \'Mimi\' WHERE id = 3";
	    System.out.println(updateSQL);
	    statement.executeUpdate(updateSQL);
	    System.out.println("DONE");

	    // Dropping a table
	    String dropSQL = "DROP TABLE " + tableName;
	    System.out.println ( dropSQL ) ;
	    statement.executeUpdate ( dropSQL ) ;
	    System.out.println ("DONE");
	} catch (SQLException e)
	    {
		sqlCode = e.getErrorCode(); // Get SQLCODE
		sqlState = e.getSQLState(); // Get SQLSTATE
                
		// Your code to handle errors comes here;
		// something more meaningful than a print would be good
		System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	    }


	// Finally but importantly close the statement and connection
	statement.close ( ) ;
	con.close ( ) ;
    }
}

