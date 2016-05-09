package books;

import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.mysql.jdbc.Connection;

public class DB {

	
	/**
	 * Creates database named bookDB.
	 * @return Whether the database is created succesfully or not.
	 */
	public static int createDatabase(){
		java.sql.Connection Conn = null;
		try {
			Conn = DriverManager.getConnection
					("jdbc:mysql://localhost/?user=root&password=");
		} catch (SQLException e) { 
			return 0;
			// TODO Auto-generated catch block
		} 
				java.sql.Statement s = null;
				try {
					s = Conn.createStatement();
				   int Result=s.executeUpdate("CREATE DATABASE bookDB");
				} catch (Exception e) {
					return 0;
					// TODO Auto-generated catch block
				}
		return 1;
	}
	/**
	 * Connect to database.
	 * @return Connection to database.
	 */
	public static  java.sql.Connection connect(){
		
		// JDBC driver name and database URL
		   final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   final String DB_URL="jdbc:mysql://localhost/bookDB";
;

		   //  Database credentials
		   final String USER = "root";
		   final String PASS = "";
		   
		   
		   java.sql.Connection conn = null;
		  
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		   }catch(Exception e){System.out.println("No connection");}
		      return conn;
		      
		
	}
	
	/**
	 * Add new table to db.
	 * @param table Table name.
	 * @return  Whether the table is created succesfully.
	 */
	public static java.sql.Connection addTable(String table){
		 java.sql.Statement stmt = null;
		   java.sql.Connection conn = connect();
		   int ss=-1;
		try{
		//STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "CREATE TABLE IF NOT EXISTS book("+
	             "user_name VARCHAR(100),\n"+
	      		 "book_name VARCHAR(100),\n"+
	    		  "genre VARCHAR(100),\n"+
	    		  "author VARCHAR(100),\n"+
	      		  "date VARCHAR(100),\n"+
	      		  "series VARCHAR(100))";
	    		 
	    System.out.println(sql);
	      ss= stmt.executeUpdate(sql);
		}catch(Exception e){}
	      return conn;

	}
	/**
	 * Delete all records of a given user.
	 * @param user_name User Name.
	 * @return Whether delete is successful or not.
	 */
	public static int deleteAll(String user_name){
		
		//createDatabase();
		   java.sql.Connection conn = connect();

		 java.sql.Statement stmt = null;
		int ss=0;
		   try{
		//STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "DELETE FROM book WHERE user_name='"+user_name+"'";
	      		 
	    		 
	    System.out.println(sql);
	       ss= stmt.executeUpdate(sql);
		}catch(Exception e){ return 0;}
	  
	
		return 1;
	}
		
		
	/**
	 * Return previous data of a user as a html string.
	 * @param user_name User Name.
	 * @return Saved data in html format.
	 */
	public static String printAll(String user_name){
		java.sql.Statement stmt = null;
		  java.sql.Connection conn = connect();
		  ResultSet rs=null;
	      String result="";
		  try{
				//STEP 4: Execute a query
			      System.out.println("Creating statement...");
			      stmt = conn.createStatement();
				     String  sql = "SELECT * FROM book WHERE user_name='"+user_name+"'";

			      rs= stmt.executeQuery(sql);
			
	
	      
			      
		        	result+="<table>";
		        	String blank="<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"; 
		        	
		        	while(rs.next()){
		        		 String book_name  = rs.getString("book_name");
		        		 String genre  = rs.getString("genre");
		      	         String author = rs.getString("author");
		      	         String series = rs.getString("series");
		      	         String date = rs.getString("date");
		      	         result+="<tr>";
		        		result+="<td> "+user_name+"</td>"+blank+"<td>"+book_name+"</td>"+blank+"<td>"+
		    		        	author+"</td>"+blank+"<td>"+genre+"</td>"+blank+"<td>"+date+"</td>"+
		        				blank+"<td>"+series+"</td>";
		        		result+="</tr>";
		        			
		        	}
			      
			      result+="</table>";
			      
			      
	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
		    
	         String name  = rs.getString("book_name");
	         String author = rs.getString("author");
	         String series = rs.getString("series");
	         String date = rs.getString("date");

	         //Display values
	         System.out.print("book_name: " + name);
	         System.out.print(", author: " + author);
	         System.out.print(", series: " + series);
	         System.out.println(", date: " + date);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
		
		return result;
	}
	/**
	 * Add new record to db.
	 * @param user_name User Name
	 * @param book_name Book Name
	 * @param author   Author
	 * @param series Series 
	 * @param date Date
	 * @param genre Genre
	 * @return Whether add is successful or not.
	 */
	public static int add(String user_name,String book_name,String author,String series,String date,String genre ){
		createDatabase();
		//createDatabase();
		   java.sql.Connection conn = addTable("book");

		 java.sql.Statement stmt = null;
		int ss=0;
		   try{
		//STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "INSERT INTO book VALUES('"+user_name+"', '"+book_name+"', '"+genre+"', '"+author+"', '"+series+"', '"+date+"')";
	      		 
	    		 
	    System.out.println(sql);
	       ss= stmt.executeUpdate(sql);
		}catch(Exception e){ return 0;}
	  
	
		return 1;
	}
}
