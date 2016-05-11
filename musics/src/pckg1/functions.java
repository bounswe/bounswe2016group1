package pckg1;

import java.sql.DriverManager;
import java.sql.SQLException;

public class functions {
	
	public static void connect() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		MainServlet.conn = DriverManager.getConnection(MainServlet.url, MainServlet.user, MainServlet.pass);
		
	}
	
	public static void runner() throws SQLException{
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}

	public static void marker(String keyword) {
		MainServlet.query = "UPDATE db SET matchno=matchno+1 WHERE ----------------";
		
	}
	
	public static void resetmark() throws SQLException {
		MainServlet.query = "UPDATE db SET matchno=0";
		runner();
	}
	
	public static String display(int num) {

		return null;
	}
	
	
	
	
	
	
	public static void createtableforsaving(String tablename) throws SQLException{
		MainServlet.query = "CREATE TABLE " + tablename
				+ "("
				+ "f1 varchar(255) , "
				+ "f2 varchar(255)"
				+ ");";
		
		runner();
		
	}
	public static void deletetable(String tablename) throws SQLException{
		MainServlet.query = "DROP TABLE " + tablename;
		
		runner();
	}

	public static void insertrecordforsaving(String tablename, String field1, String field2) throws SQLException{
		MainServlet.query = "INSERT INTO " + tablename
				+ " VALUES "
				+ "(" + field1 + " , "+ field2 + ");";
		
		runner();
	}
	public static void deleterecord(String tablename,String fieldname,String recordvalue) throws SQLException{
		MainServlet.query = "DELETE FROM " + tablename
				+ " WHERE "
				+ fieldname
				+ "="
				+ recordvalue
				+ ";";
		
		runner();
	}
	
	public static void select(String fieldlist, String tablename) throws SQLException{
		MainServlet.query = "SELECT " + fieldlist
				+ " FROM " + tablename +  ";";
		
		runner();
	}
	public static void select(String fieldlist, String tablename, String condition) throws SQLException{
		MainServlet.query = "SELECT " + fieldlist
				+ " FROM " + tablename
				+ " WHERE " + condition +  ";";
		
		runner();
	}

	
}
