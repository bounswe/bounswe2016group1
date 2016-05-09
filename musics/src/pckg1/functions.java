package pckg1;

import java.sql.DriverManager;
import java.sql.SQLException;

public class functions {
	
	public static boolean connect() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		MainServlet.conn = DriverManager.getConnection(MainServlet.url, MainServlet.user, MainServlet.pass);
		
		if(MainServlet.conn != null){
			return true;
		}else{
			return false;
		}
	}

	public static void createtable(String tablename, String field1, String field2) throws SQLException{
		MainServlet.query = "CREATE TABLE " + tablename
				+ "("
				+ field1 + "varchar(255)"
				+ field2 + "varchar(255)"
				+ ");";
		
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
		
	}
	public static void deletetable(String tablename) throws SQLException{
		MainServlet.query = "DROP TABLE " + tablename;
		
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}

	public static void insertrecord(String tablename, String field1, String field2) throws SQLException{
		MainServlet.query = "INSERT INTO " + tablename
				+ "VALUES"
				+ "(" + field1 + field2 + ");";
		
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}
	public static void deleterecord(String tablename,String targetfield,String recordfield) throws SQLException{
		MainServlet.query = "DELETE FROM " + tablename
				+ "WHERE "
				+ targetfield
				+ "="
				+ recordfield
				+ ";";
		
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}
	
	public static void search(String fieldlist, String tablename) throws SQLException{
		MainServlet.query = "SEARCH " + fieldlist
				+ "FROM" + tablename +  ";";
		
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}
	public static void search(String fieldlist, String tablename, String condition) throws SQLException{
		MainServlet.query = "SEARCH " + fieldlist
				+ "FROM" + tablename
				+ "WHERE " + condition +  ";";
		
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}
	
}
