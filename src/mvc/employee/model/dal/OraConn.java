package mvc.employee.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OraConn {
	static private String url;
	static private String userName;
	static private String userPassword;
	static private Connection connection = null;

	private OraConn() {
	}

	static Connection getConnection() {
		return connection;
	}
	
	private static int err = 0;

	public static int getErr() { 
	    return err;
	  }

	private static String errMsg = "";

	public static String getErrMsg() { 
	    return errMsg; 
	  }

	public static int open(String url, String usr, String pass) {
		try {
			OraConn.url = url;
			OraConn.userName = usr;
			OraConn.userPassword = pass;
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
		} 
		catch (ClassNotFoundException ex) { 
			errMsg = ex.getMessage();
			return err = 1; 
		}
		try {
	    connection = DriverManager.getConnection(
	    		OraConn.url, OraConn.userName, OraConn.userPassword);
	    	errMsg = "";
	    	return err = 0; 
	    } 
		catch (SQLException ex) {
			connection = null;
			errMsg = ex.getMessage();
			return err = 2; 
		}
	  }

	public static int close() {
	  try {
		  if (connection != null) 
			  connection.close();
		  errMsg = "";
		  return err = 0; 
	  } 
	  catch (SQLException ex) {
		  connection = null;
		  errMsg = ex.getMessage();
		  return err = 1; 
	  }
}

	public static boolean setAutoCommit(boolean isAutoCommit) {
	  try { 
		  connection.setAutoCommit(isAutoCommit); 
	  } 
	  catch (SQLException e) { 
		  return false; 
	  }
	  return true;
  }
}
