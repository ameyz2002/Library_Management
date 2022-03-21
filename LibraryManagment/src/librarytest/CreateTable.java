package librarytest;

import java.sql.*;
import utils.DBUtil;

public class CreateTable {
	
	public static void main(String[] args) {
		try(Connection con = DBUtil.getConnection(); Statement stmt = con.createStatement();) {
			
			String sql = "CREATE TABLE users " +
	                   "(user_id INT not NULL, " +
	                   " name VARCHAR(255), " + 
	                   " email VARCHAR(255), " + 
	                   " password VARCHAR(255), " + 
	                   " phone VARCHAR(255), " +
	                   "role VARCHAR(255)," +
	                   " PRIMARY KEY ( user_id ))";
			stmt.executeUpdate(sql);
			System.out.println("Table USERS Created");
			
			
			String sql2 = "CREATE TABLE books" +
						"(book_id INT," +
						"name VARCHAR(255)," +
						"author VARCHAR(255)," +
						"subject VARCHAR(255)," +
						"price DOUBLE," +
						"isbn VARCHAR(255)," +
						"PRIMARY KEY (book_id))";
			
			
			stmt.executeUpdate(sql2);
			System.out.println("Table BOOKS Created");
			
			String sql3 = "CREATE TABLE copies" +
						"(copy_id INT," +
						"book_id INT," +
						"rack VARCHAR(255)," +
						"status VARCHAR(255)," +
						"subject VARCHAR(255)," +
						"PRIMARY KEY (copy_id)," +
						"FOREIGN KEY (book_id) REFERENCES books(book_id))";
			
		
			stmt.executeUpdate(sql3);
			System.out.println("Table COPIES Created");
		
		
			String sql4 = "CREATE TABLE issuerecords" +
						"(issue_id INT," +
						"copy_id INT," +
						"user_id INT," +
						"issue_date DATETIME," +
						"return_duedate DATETIME," +
						"return_date DATETIME," +
						"fine DOUBLE," +
						"PRIMARY KEY (issue_id),"+
						"FOREIGN KEY (user_id) REFERENCES users(user_id),"+
						"FOREIGN KEY (copy_id) REFERENCES copies(copy_id))";
			
	
			stmt.executeUpdate(sql4);
			System.out.println("Table ISSUERECORDS Created");
	
	
			String sql5 = "CREATE TABLE payments" +
						  "(payment_id INT, "+
						  "user_id INT,"+
						  "amount DOUBLE,"+
						  "type VARCHAR(20),"+
						  "transanction_time DATETIME,"+
						  "nextpayement_duedate DATETIME,"+
						  "PRIMARY KEY (payment_id),"+
						  "FOREIGN KEY (user_id) REFERENCES users(user_id))";


			stmt.executeUpdate(sql5);
			System.out.println("Table PAYMENTS Created");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
