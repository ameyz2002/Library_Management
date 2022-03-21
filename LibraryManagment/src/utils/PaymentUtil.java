package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

import dao.IssueDao;
import dao.PaymentDao;
import pojo.IssueRecord;
import pojo.Payment;

public class PaymentUtil {
	private static Scanner scan = new Scanner(System.in); 
	
	public static void acceptPayment() {
		try (PaymentDao pdao = new PaymentDao()) {
			Payment payment = new Payment();
			System.out.print("Enter user id : ");
			payment.setUserId(scan.nextInt());
			payment.setType("fees");
			payment.setAmount(500.00);
			pdao.acceptFees(payment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void fineReceive(int userid,int issueid,Date dt) {
		try (IssueDao idao = new IssueDao(); PaymentDao pdao = new PaymentDao();) {
			IssueRecord issue = idao.getRecord(issueid,dt);
			LocalDate issueDate = issue.getIssueDate().toLocalDate();
			LocalDate returnDate = dt.toLocalDate();	
			
			int diffdays = Period.between(issueDate, returnDate).getDays();
			System.out.println("diff days :"+diffdays);
			if(diffdays>15) {
				issue.setFine((diffdays-15)*10);
				System.out.println("fine"+issue.getFine());
				pdao.fineAccept(issue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void paymentHistory() {
		try (PaymentDao pdao = new PaymentDao()) {
			pdao.paymentHistory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
