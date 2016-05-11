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
		
		buff += "<form method=\"post\" action =\"MainServlet\"> ";
		
		buff += "<table>";
		buff += "<tr>";
		
		buff += "<td width=\"10%\"> ---- </td>";
		buff += "<td width=\"30%\"> field1 </td>";
		buff += "<td width=\"30%\"> field2 </td>";
		buff += "<td width=\"30%\"> field3 </td>";

		buff += "</tr>";

		// ----------------------------------------------------
		
		MainServlet.query = "SELECT * FROM db ORDER BY matchno DESC;";
		runnerQuery();
		
		while(MainServlet.result.next() && num > 0){

			if(MainServlet.result.getInt("matchno") != 0){
				
				buff += "<tr>";
				
				buff += "<td> "
						+ "<input type=\"checkbox\" name=\"box"+ (20-num+1) +"\" value=\"" + MainServlet.result.getString("id") + "\">"
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
	
	public static String insertuser(String name) throws SQLException{
		MainServlet.query = "SELECT * FROM users WHERE name='"+name+"';";
		runnerQuery();
		
		if(!MainServlet.result.next()){
			MainServlet.query = "INSERT INTO users "
					+ " VALUES "
					+ "('" + name + "');";
			
			runnerUpdate();
			return "Welcome traveler.";
		}else{
			return "Welcome old friend.";
		}

	}

	public static void insertrecordforsaving(String name, String id, String keyword) throws SQLException{
		
		MainServlet.query = "SELECT * FROM saved WHERE name='"+name+"' and id='"+id+"' and keyword='"+keyword+"';";
		runnerQuery();
		
		if(!MainServlet.result.next()){
			MainServlet.query = "INSERT INTO saved "
					+ " VALUES "
					+ "('" + name + "' , '"+ id + "' , '" + keyword +"');";
			
			runnerUpdate();

		}
	}

	
}
