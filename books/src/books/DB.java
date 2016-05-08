package books;

import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
		createDatabase();
		//addTable("book");
		//add("Test1","Test2","Test3","Test4");
		//printAll();
	}

	
	public static void createDatabase(){
		java.sql.Connection Conn = null;
		try {
			Conn = DriverManager.getConnection
					("jdbc:mysql://localhost/?user=root&password=");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
				java.sql.Statement s = null;
				try {
					s = Conn.createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					int Result=s.executeUpdate("CREATE DATABASE books");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
	public static  java.sql.Connection connect(){
		
		// JDBC driver name and database URL
		   final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   final String DB_URL="jdbc:mysql://localhost/books";
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
	
	
	public static int addTable(String table){
		 java.sql.Statement stmt = null;
		   java.sql.Connection conn = connect();
		   int ss=-1;
		try{
		//STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "CREATE TABLE IF NOT EXISTS book("+
	      		 "book_name VARCHAR(100),\n"+
	    		  "author VARCHAR(100),\n"+
	      		  "date VARCHAR(100),\n"+
	      		  "series VARCHAR(100))";
	    		 
	    System.out.println(sql);
	      ss= stmt.executeUpdate(sql);
		}catch(Exception e){}
	      return ss;

	}
	
	public static void printAll(){
		java.sql.Statement stmt = null;
		  java.sql.Connection conn = connect();
		  ResultSet rs=null;
		  try{
				//STEP 4: Execute a query
			      System.out.println("Creating statement...");
			      stmt = conn.createStatement();
				     String  sql = "SELECT * FROM book";

			      rs= stmt.executeQuery(sql);
			
	
	      
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
		
		
		
	}
	public static int add(String book_name,String author,String series,String date ){
		 addTable("book");

		 java.sql.Statement stmt = null;
		   java.sql.Connection conn = connect();
		   int ss=-1;
		try{
		//STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "INSERT INTO book VALUES('"+book_name+"', '"+author+"', '"+series+"', '"+date+"')";
	      		 
	    		 
	    System.out.println(sql);
	      ss= stmt.executeUpdate(sql);
		}catch(Exception e){}
	      return ss;
	  
	
		
		
	}
}
