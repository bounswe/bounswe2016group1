package pckg1;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is for useful methods that simplify coding in MainServlet.java
 * @author sahin_batmaz
 */
public class functions {
	
	/**
	 * This method makes the connection to database.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void connect() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		MainServlet.conn = DriverManager.getConnection(MainServlet.url, MainServlet.user, MainServlet.pass);
		
	}
	
	/**
	 * There are two kind of database operations, 
	 * one is asking query and taking desired list and
	 * the other is updating the database.
	 * This method makes the executions for the former when query is already set.
	 * @throws SQLException
	 */
	public static void runnerQuery() throws SQLException{
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.result = MainServlet.statement.executeQuery();
	}
	/**
	 * There are two kind of database operations, 
	 * one is asking query and taking desired list and
	 * the other is updating the database.
	 * This method makes the executions for the latter when query is already set.
	 * @throws SQLException
	 */
	public static void runnerUpdate() throws SQLException{
		MainServlet.statement = MainServlet.conn.prepareStatement(MainServlet.query);
		MainServlet.statement.executeUpdate();
	}

	/**
	 * There is a rank value field in database for 
	 * how much a record is related with the searching keyword set.
	 * This method set this field zero for all records.
	 * Note that this method is always used just before a search operation starts.  
	 * @throws SQLException
	 */
	public static void resetmark() throws SQLException {
		MainServlet.query = "UPDATE db SET matchno=0";
		runnerUpdate();
	}
	/**
	 * There is a rank value field in database for 
	 * how much a record is related with the searching keyword set.
	 * This method finds the corresponding rank value for the keyword set.
	 * @param keyword // It is one of words that user has entered.
	 * @throws SQLException
	 */
	public static void marker(String keyword) throws SQLException {
		select("*","db");
		while(MainServlet.result.next()){
			if(MainServlet.result.getString("f1").toLowerCase().contains(keyword.toLowerCase())){
				MainServlet.query = "UPDATE db SET matchno = matchno+1 WHERE id="+MainServlet.result.getString("id")+";";
				runnerUpdate();
			}
			if(MainServlet.result.getString("f2").toLowerCase().contains(keyword.toLowerCase())){
				MainServlet.query = "UPDATE db SET matchno = matchno+1 WHERE id="+MainServlet.result.getString("id")+";";
				runnerUpdate();
			}
			if(MainServlet.result.getString("f3").toLowerCase().contains(keyword.toLowerCase())){
				MainServlet.query = "UPDATE db SET matchno = matchno+1 WHERE id="+MainServlet.result.getString("id")+";";
				runnerUpdate();
			}
		}		
	}
	
	/**
	 * Before this method is called, all rank values in database are set to what they should be.
	 * This method sort the records according to rank values and
	 * create html code with information of records in it.
	 * @param num // It is for the maximum number of records that will be showed
	 * @return // This method returns the appropriate html code as a String.
	 * @throws SQLException
	 */
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
	
	/**
	 * This is a basic query for database.
	 * By this method, it is easy to use 'select - from table' query.
	 * Output values are automatically put into Result variable of MainServlet.java
	 * @param fieldlist // desired fields of records that are being searched
	 * @param tablename // the table name of records that they belong to
	 * @throws SQLException
	 */
	public static void select(String fieldlist, String tablename) throws SQLException{
		MainServlet.query = "SELECT " + fieldlist
				+ " FROM " + tablename +  ";";
		
		runnerQuery();
	}
	/**
	 * This is a basic query for database.
	 * By this method, it is easy to use 'select - from table where -' query.
	 * Output values are automatically put into Result variable of MainServlet.java
	 * @param fieldlist // desired fields of records that are being searched
	 * @param tablename // the table name of records that they belong to
	 * @param condition // it is the part of the query that comes after where.
	 * @throws SQLException
	 */
	public static void select(String fieldlist, String tablename, String condition) throws SQLException{
		MainServlet.query = "SELECT " + fieldlist
				+ " FROM " + tablename
				+ " WHERE " + condition +  ";";
		
		runnerQuery();
	}

	
	// ----------------------------------------------------
	
	/**
	 * In my project, I have a table for users who are using the project.
	 * As a first processes of my project, it asks for a username.
	 * And this method adds the name to the database.
	 * @param name // name of the user.
	 * @return	// If the already exists in the database it returns 'Welcome old friend' as a String,
	 * 				else it returns 'Welcome traveler' as a String.
	 * @throws SQLException
	 */
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

	/**
	 * When a user wants to save some records that s/he searched,
	 * this method runs in the project.
	 * It saves the id of the records with the username and the keyword that the results come from.
	 * @param name	// name of the user
	 * @param id	// id of the record that will be saved
	 * @param keyword // the keyword set which gave the output set.
	 * @throws SQLException
	 */
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
