package pckg1;

import java.sql.DriverManager;
import java.sql.SQLException;

public class functions {
	
	public static void connect() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		MainServlet.conn = DriverManager.getConnection(MainServlet.url, MainServlet.user, MainServlet.pass);
		
	}
	
	public static void runnerQuery() throws SQLException{
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}
	public static void runnerUpdate() throws SQLException{
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.statement.executeUpdate();
	}


	public static void resetmark() throws SQLException {
		MainServlet.query = "UPDATE db SET matchno=0";
		runnerUpdate();
	}
	
	public static void marker(String keyword) throws SQLException {
		select("*","db");
		while(MainServlet.result.next()){
			if(MainServlet.result.getString("f1").contains(keyword)){
				MainServlet.query = "UPDATE db SET matchno = matchno+1 WHERE id="+MainServlet.result.getString("id")+";";
				runnerUpdate();
			}
			if(MainServlet.result.getString("f2").contains(keyword)){
				MainServlet.query = "UPDATE db SET matchno = matchno+1 WHERE id="+MainServlet.result.getString("id")+";";
				runnerUpdate();
			}
			if(MainServlet.result.getString("f3").contains(keyword)){
				MainServlet.query = "UPDATE db SET matchno = matchno+1 WHERE id="+MainServlet.result.getString("id")+";";
				runnerUpdate();
			}
		}		
	}
	
	
	public static String display(int num) throws SQLException {
		String buff ="";
		
		buff += "<form method=\"post\" action =\"SaveCheckedServlet\"> ";
		
		buff += "<table>";
		buff += "<tr>";
		
		buff += "<td> ---------- </td>";
		buff += "<td> f1editname </td>";
		buff += "<td> f2editname </td>";
		buff += "<td> f3editname </td>";

		buff += "</tr>";

		// ----------------------------------------------------
		
		MainServlet.query = "SELECT * FROM db ORDER BY matchno DESC;";
		runnerQuery();
		
		while(MainServlet.result.next() && num > 0){

			if(MainServlet.result.getInt("matchno") != 0){
				
				buff += "<tr>";
				
				buff += "<td> "
						+ "<input type=\"checkbox\" name=\"box\" value=\"mavera\">"
						+ "</td>";
				buff += "<td> " + MainServlet.result.getString("f1") + " </td>";
				buff += "<td> " + MainServlet.result.getString("f2") + " </td>";
				buff += "<td> " + MainServlet.result.getString("f3") + " </td>";

				buff += "</tr>";
				
				num--;
			}else{
				break;
			}
		}
		MainServlet.listlength = 20 - num;
		

		// ----------------------------------------------------
		
		buff += "</table>";
		buff += "<input  type=\"submit\" value=\"Save selected results\">";
		buff += "</form> ";
		
		return buff;
	}

	
	
	// ----------------------------------------------------
	
	
	public static void select(String fieldlist, String tablename) throws SQLException{
		MainServlet.query = "SELECT " + fieldlist
				+ " FROM " + tablename +  ";";
		
		runnerQuery();
	}
	public static void select(String fieldlist, String tablename, String condition) throws SQLException{
		MainServlet.query = "SELECT " + fieldlist
				+ " FROM " + tablename
				+ " WHERE " + condition +  ";";
		
		runnerQuery();
	}

	
	// ----------------------------------------------------
	
	
	
	public static void createtableforsaving(String tablename) throws SQLException{
		MainServlet.query = "CREATE TABLE " + tablename
				+ "("
				+ "f1 varchar(255) , "
				+ "f2 varchar(255)"
				+ ");";
		
		runnerUpdate();
		
	}
	public static void deletetable(String tablename) throws SQLException{
		MainServlet.query = "DROP TABLE " + tablename;
		
		runnerUpdate();
	}

	public static void insertrecordforsaving(String tablename, String field1, String field2) throws SQLException{
		MainServlet.query = "INSERT INTO " + tablename
				+ " VALUES "
				+ "(" + field1 + " , "+ field2 + ");";
		
		runnerUpdate();
	}
	public static void deleterecord(String tablename,String fieldname,String recordvalue) throws SQLException{
		MainServlet.query = "DELETE FROM " + tablename
				+ " WHERE "
				+ fieldname
				+ "="
				+ recordvalue
				+ ";";
		
		runnerUpdate();
	}
	
	
	
}
