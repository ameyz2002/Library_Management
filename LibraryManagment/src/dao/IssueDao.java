package dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import pojo.Copy;
import pojo.IssueRecord;
import utils.DBUtil;
import utils.PaymentUtil;

public class IssueDao implements Closeable  {
	private static Scanner scan = new Scanner(System.in);
	private Connection connection;
	private PreparedStatement stmt;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtSelect;
	private PreparedStatement stmt1;

	
	public IssueDao()throws Exception {
		connection = DBUtil.getConnection();
		stmtInsert=connection.prepareStatement("INSERT INTO issuerecords VALUES (?,?,?,?,?,?,?)");
		stmtUpdate=connection.prepareStatement("UPDATE issuerecords SET return_date = ? WHERE issue_id = ?");
		stmtSelect=connection.prepareStatement("SELECT * FROM issuerecords WHERE user_id = ?");
		stmt1=connection.prepareStatement("SELECT * FROM issuerecords WHERE issue_id = ?");
	}
	
	public void addRecord(IssueRecord issue) {
		try (CopyDao dao = new CopyDao()) {
			Copy copy = new Copy();
			copy = dao.getBook(issue.getCopyId());
			if(copy.getStatus().contentEquals("available")) {
				String query = "SELECT max(issue_id) FROM issuerecords";
				stmt = connection.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				int num = 0;
				while(rs.next())
					num = rs.getInt(1) + 1;
				stmtInsert.setInt(1, num);
				stmtInsert.setInt(2, issue.getCopyId());
				stmtInsert.setInt(3, issue.getUserId());
				stmtInsert.setDate(4, issue.getIssueDate());
				stmtInsert.setDate(5, issue.getReturnDuedate());
				stmtInsert.setDate(6, issue.getReturnDate());
				stmtInsert.setDouble(7, issue.getFine());
				stmtInsert.executeUpdate();
				System.out.println("Record added !!! ");
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
	public void returnBook(int userid, Date dt) {
		try(CopyDao cdao = new CopyDao();) {
			stmtSelect.setInt(1, userid);
			ResultSet res = stmtSelect.executeQuery();
			System.out.println();
			ArrayList<IssueRecord> records = new ArrayList<IssueRecord>();
			while(res.next()) {
				   records.add(new IssueRecord(res.getInt(1),res.getInt(2),res.getInt(3),res.getDate(4),res.getDate(5),res.getDate(6),res.getDouble(7)));				  
			}res.close();
			System.out.println();
			for(IssueRecord ref: records) {
				System.out.println(ref.toString());
			}
			System.out.print("Enter issue Id to be returned : ");
			int num =scan.nextInt();
						stmtUpdate.setDate(1, dt);
						stmtUpdate.setInt(2,num);
						stmtUpdate.executeUpdate();
						int issueId = 0;
						int copyID = 0;
						for(IssueRecord ref: records) {
							if(ref.getIssueId()==num) {
								issueId = ref.getIssueId();
								copyID = ref.getCopyId();
								PaymentUtil.fineReceive(userid,issueId,dt);
							}
								
						}
						 cdao.changeStatusAvail(copyID);
						System.out.println("retun book record updated ");
								
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
	
	
	public void checkStatus(int id) {
		try {
			stmtSelect.setInt(1, id);
			ResultSet rs = stmtSelect.executeQuery();
			ArrayList<IssueRecord> records=new ArrayList<IssueRecord>();
			while(rs.next()) {
				   records.add(new IssueRecord(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDate(4),rs.getDate(5),rs.getDate(6),rs.getDouble(7)));				  
			}rs.close();
			System.out.println();
			for(IssueRecord ref: records) {
				System.out.println(ref.toString());
			}	
			System.out.println();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void close() throws IOException {
		try {
			connection.close();
			stmt.close();
			stmtInsert.close();
			stmtSelect.close();
			stmtUpdate.close();
			stmt1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public IssueRecord getRecord(int issueid,Date dt) {
		IssueRecord issue = new IssueRecord();
		try {
			
			stmt1.setInt(1, issueid);
			ResultSet rs = stmt1.executeQuery();
			while(rs.next()) {
				issue.setCopyId(rs.getInt(2));
				issue.setUserId(rs.getInt(3));
				issue.setIssueDate(rs.getDate(4));
				issue.setReturnDuedate(rs.getDate(5));
				issue.setReturnDate(dt);
			}rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return issue;
		
	}

	
}
