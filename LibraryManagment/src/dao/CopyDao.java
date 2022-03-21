package dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pojo.Copy;
import utils.DBUtil;

public class CopyDao implements Closeable {
	private Connection connection;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtSelect;
	private PreparedStatement stmt1;
	private PreparedStatement stmt2;
	private PreparedStatement stmt3;
	private PreparedStatement stmt4;
	private PreparedStatement stmt5;
	
	
	public CopyDao() throws Exception {
		this.connection = DBUtil.getConnection();
		this.stmtInsert = connection.prepareStatement("INSERT INTO copies VALUES(?,?,?,?,?)");
		this.stmtSelect = connection.prepareStatement("SELECT * FROM copies WHERE copy_id = ?");
		this.stmt1 = connection.prepareStatement("SELECT copy_id,rack FROM copies WHERE book_id = ? and status ='available' ");
		this.stmt2 = connection.prepareStatement("UPDATE copies SET rack=? WHERE copy_id=?");
		this.stmt3 = connection.prepareStatement("UPDATE copies SET status = 'available' WHERE copy_id = ?");
		this.stmt4 = connection.prepareStatement("UPDATE copies SET status = 'issued' WHERE copy_id = ?");
		this.stmt5 = connection.prepareStatement("select book_id, if(status='available','available','issued') status,if(status='available',count(book_id),count(book_id)) 'count' from copies group by book_id,status");
	}

	
	//Insert copy
	public int insert(Copy copy) throws Exception{
		String query = "Select max(copy_id) as max from copies ";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		int num = 0;
		while (rs.next())
			num = rs.getInt("max") + 1;
		copy.setCopy_id(num);
		this.stmtInsert.setInt(1, copy.getCopy_id());
		this.stmtInsert.setInt(2, copy.getBook_id());
		this.stmtInsert.setString(3,copy.getRack());
		this.stmtInsert.setString(4, copy.getStatus());
		this.stmtInsert.setString(5, copy.getSubject());
		
		return stmtInsert.executeUpdate();
				
	}	
	
	public int checkBook(int num) {
		int status=0;
		try {
			stmt1.setInt(1, num);
			ResultSet rs = stmt1.executeQuery();	
			System.out.println();
				while(rs.next()) {
					System.out.printf("Copy ID: %-4d  Rack :%-10s\n",rs.getInt(1),rs.getString(2));
						++status;
			}
			System.out.println();
			rs.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
		
	}
	
	public Copy getBook(int num) {
		Copy copy=new Copy();
		try {
			stmtSelect.setInt(1, num);
			ResultSet rs = stmtSelect.executeQuery();
			while(rs.next()) {
				copy.setCopy_id(1);
				copy.setBook_id(rs.getInt(1));
				copy.setRack(rs.getString(3));
				copy.setStatus(rs.getString(4));				
			}
			rs.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return copy;
	}
	
		
	@Override
	public void close() throws IOException {
		try {
			connection.close();
			stmtInsert.close();
			stmtSelect.close();
			stmt1.close();
			stmt2.close();
			stmt3.close();
			stmt4.close();
			stmt5.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void changeRack(int num,String rack) {
		try {				
			 stmt2.setString(1,rack);
			 stmt2.setInt(2, num);
			 stmt2.executeUpdate();
			 System.out.println("Rack changed !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void changeStatusAvail(int copyId) {
		try {								
			stmt3.setInt(1, copyId);
			stmt3.executeUpdate();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
		
	}
	
	public void changeStatusIssued(int copyId) {
		try {								
			stmt4.setInt(1, copyId);
			stmt4.executeUpdate();
		} catch (SQLException e) {				
			e.printStackTrace();
		}
		
	}


	public void bookRecord() {
		try {
			ResultSet rs = stmt5.executeQuery();	
			System.out.println();
				while(rs.next()) {
					System.out.printf("Book ID: %-4d  status :%-10s   count : %-4d\n",rs.getInt(1),rs.getString(2),rs.getInt(3));
						
			}
			System.out.println();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



}
