package comp421;


import java.sql.* ;


public class SQL {
	
	 
	
	Statement statement;
		
		public SQL() throws SQLException
		{
		
		try {
			    DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
			} catch (Exception cnfe){
			    System.out.println("Class not found");
			    }	 
	
		String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
		Connection con = DriverManager.getConnection (url, "cs421g27", "Mcgill1234") ;
	    statement = con.createStatement ( ) ;

		
	 }
		
		public void WriteExcute(String sqlCode)
		{
			try {
				statement.executeUpdate ( sqlCode ) ;
			} catch (SQLException e)
			    {
				int errorCode = e.getErrorCode(); // Get SQLCODE
				String sqlState = e.getSQLState(); // Get SQLSTATE
		                
				System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			    }
			
		}
		
		
		public java.sql.ResultSet QueryExchte(String sqlCode)
		{ 
			java.sql.ResultSet rs = null;
			try {
			    rs = statement.executeQuery (sqlCode ) ;		   
			} catch (SQLException e)
			    {
				int errorCode = e.getErrorCode(); // Get SQLCODE
				String sqlState = e.getSQLState(); // Get SQLSTATE
		                
				System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			    }
			
			return rs;
		}
		
}
