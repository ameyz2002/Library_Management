package dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import pojo.IssueRecord;
import pojo.Payment;
import utils.DBUtil;

public class PaymentDao implements Closeable{
	
	private Connection connection;
	private Statement stm11;
	private Statement stm2;
	private PreparedStatement stm;
	private PreparedStatement stm3;
	private PreparedStatement stm4;
	private PreparedStatement stm5;
	private PreparedStatement stm6;
	private PreparedStatement stm7;

	 public PaymentDao() throws Exception {
		 connection = DBUtil.getConnection();
			stm=connection.prepareStatement("INSERT INTO payments VALUES (?,?,?,?,?,?)");
		    stm11=connection.createStatement();
		    stm2=connection.createStatement();
		    stm3=connection.prepareStatement("INSERT INTO payments VALUES(?,?,?,?,?,?)");
		    stm4=connection.prepareStatement("SELECT max(nextpayement_duedate)FROM payments WHERE user_id=? and type='fees' ");
		    stm5=connection.prepareStatement("SELECT * FROM payments ORDER BY user_id");
		    stm6=connection.prepareStatement("SELECT if(type='fine','Total Fine','Total Fees') 'Type',  if(type='fine',sum(amount)  ,sum(amount) ) as 'Amount' FROM payments where transanction_time BETWEEN ? and ? GROUP BY type");
		    stm7=connection.prepareStatement("SELECT max(nextpayement_duedate) FROM payments WHERE user_id=?");
	
	}
	 
	 public void fineAccept(IssueRecord issue) {
	    	try {
				ResultSet rs=stm11.executeQuery("SELECT max(payment_id) FROM payments");
				int num = 0;
				while(rs.next())
					num = rs.getInt(1) + 1;
				rs.close();
				stm.setInt(1, num);
				stm.setInt(2,issue.getUserId());
				stm.setDouble(3, issue.getFine());
				stm.setString(4,"fine");
				stm.setDate(5,issue.getReturnDate());
				stm.setDate(6,null);
				stm.executeUpdate();
				System.out.println("Fine Received !!! ");
			} catch (SQLException e) {			
				e.printStackTrace();
			}	
			
		}
	 
	 public void acceptFees(Payment payment) {
	    	Date lastdate=null;
	    	try {
				ResultSet rs = stm2.executeQuery("SELECT max(payment_id) max FROM payments");
				int num = 0;
				while(rs.next())
					num = rs.getInt(1) + 1;
				
				stm4.setInt(1,payment.getUserId());
				ResultSet rs1 = stm4.executeQuery();			     
				while(rs1.next())
					lastdate = rs1.getDate(1);
				
				if(lastdate==null)
					lastdate = Date.valueOf(LocalDate.now());
				
				LocalDate nextdate = lastdate.toLocalDate().plusDays(30);
				
				
				stm3.setInt(1, num);
				stm3.setInt(2, payment.getUserId());
				stm3.setDouble(3, payment.getAmount());
				stm3.setString(4,payment.getType());
				stm3.setDate(5,lastdate);
				stm3.setDate(6, Date.valueOf(nextdate));
				stm3.executeUpdate();
				System.out.println("Fees Received");			
				rs.close();
				rs1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	
	@Override
	public void close() throws IOException {
		try {
			connection.close();
			stm.close();
			stm2.close();
			stm3.close();
			stm4.close();
			stm5.close();
			stm6.close();
			stm7.close();
			stm11.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void paymentHistory() {
		 try {
			ResultSet rs = stm5.executeQuery();
			System.out.println();
			while(rs.next()) {
				System.out.printf("paymentID: %-4d userID: %-5d amount:%-8.2f type:%-7s time:%-10s nextdate: %-10s\n",rs.getInt(1),rs.getInt(2),rs.getDouble(3),rs.getString(4),rs.getDate(5).toString(),rs.getDate(6));
			}
			System.out.println();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	public void getPaymentReport(Date sdate,Date edate) {
		 try {
			    stm6.setDate(1, sdate);
			    stm6.setDate(2, edate);
				ResultSet rs = stm6.executeQuery();
				System.out.println();
				while(rs.next()) {
					System.out.printf("%-15s  %-8.2f \n",rs.getString(1),rs.getDouble(2));
				}
				System.out.println();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}

	   public boolean paidMember(int userid) {
	   	try {
	   		stm7.setInt(1, userid);
				ResultSet rs = stm7.executeQuery();
				Date date = null;
				while(rs.next())
					date = rs.getDate(1);
				rs.close();
				if(date!=null) {
					LocalDate rdate = date.toLocalDate();
					LocalDate cdate = LocalDate.now();
					if((rdate.compareTo(cdate))>=0) 
					 return true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	return false;
	   	
	   }


}


