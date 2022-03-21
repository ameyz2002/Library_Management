package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import dao.CopyDao;
import dao.IssueDao;
import dao.PaymentDao;
import pojo.IssueRecord;

public class IssueUtil {
	private static Scanner scan = new Scanner(System.in);
	private static IssueRecord issue = new IssueRecord();
	
	public static void issueBook() {
		 try (IssueDao dao = new IssueDao(); CopyDao cdao = new CopyDao();) {
			 PaymentDao pdao = new PaymentDao();
			System.out.print("Enter copy id      : ");
			issue.setCopyId(scan.nextInt());		
			System.out.print("Enter user id      : ");
			issue.setUserId(scan.nextInt());
			
			if(pdao.paidMember(issue.getUserId())) {
				System.out.println("Enter issue date : ");
				System.out.print("day   : ");
				int a = scan.nextInt();
				System.out.print("month : ");
				int b = scan.nextInt();
				System.out.print("year  : ");
				int c = scan.nextInt();
				LocalDate date = LocalDate.of(c, b, a);
				issue.setIssueDate(Date.valueOf(date));
				issue.setReturnDuedate(Date.valueOf(date.plusDays(15)));	
				dao.addRecord(issue);
				cdao.changeStatusIssued(issue.getCopyId());
				
			}
			else System.out.println("User is not paid user !!! ");
			pdao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
   
	
	public static void returnBookCopy() {
		 try (IssueDao dao = new IssueDao()) {
			System.out.print("Enter user ID      : ");
			   int id = scan.nextInt();
			   System.out.println("Enter return date : ");
			   System.out.print("day   : ");
				int a = scan.nextInt();
				System.out.print("month : ");
				int b = scan.nextInt();
				System.out.print("year  : ");
				int c = scan.nextInt();			
				 dao.returnBook(id,Date.valueOf(LocalDate.of(c, b, a)));
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			
		   
	   }

}
