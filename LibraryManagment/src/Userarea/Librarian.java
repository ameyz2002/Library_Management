package Userarea;

import java.util.Scanner;

import utils.BookUtil;
import utils.CopyUtil;
import utils.IssueUtil;
import utils.OwnerUtil;
import utils.PaymentUtil;
import utils.UserUtil;

public class Librarian {
	static Scanner scan = new Scanner(System.in);
	private static int menuList() {
		System.out.println("0. Sign Out");
		System.out.println("1. Add Member");
		System.out.println("2. Edit Profile");
		System.out.println("3. Change Password");
		System.out.println("4. Add Book");
		System.out.println("5. Find Book");
		System.out.println("6. Edit Book");
		System.out.println("7. Book Availability");
		System.out.println("8. Add Copy");
		System.out.println("9. Change Rack");
		System.out.println("10. Issue Copy");
		System.out.println("11. Return Copy");
		System.out.println("12. Take Payment");
		System.out.println("13. Payment History");
		System.out.println("14. List All Users");
		System.out.print("Enter Choice : ");
		return scan.nextInt();
	}
	
	public static void librarianArea(String email) {
		System.out.println("LIBRARIAN AREA");
		int choice;
		while ((choice = Librarian.menuList()) != 0) {
			switch (choice) {
			case 1: 		//Add Member
				UserUtil.signUp();
				break;
			case 2:			//Edit profile
				OwnerUtil.editProfile(email);
				break;
			case 3:			//Change Password
				OwnerUtil.changePassword(email);
				break;
			case 4:  		//Add Book
				BookUtil.addBook();
				break;
			case 5:			//Find Book
				BookUtil.findBook();
				break;
			case 6:	 		//EDIT BOOK
				BookUtil.editBook();
				break;
			case 7:			//Book availability
				CopyUtil.checkAvail();
				break;
			case 8:			//Add copy
				CopyUtil.addCopy();
				break;
			case 9:			//Change Rack
				CopyUtil.chengeRack();
				break;
			case 10:		//Issue Copy
				IssueUtil.issueBook();
				break;
			case 11:		// Return copy
				IssueUtil.returnBookCopy();
				break;
			case 12:		//Take Payment
				PaymentUtil.acceptPayment();
				break;				
			case 13:		//Payment History
				PaymentUtil.paymentHistory();
				break;
			case 14:		//List all users
				OwnerUtil.listUsers();
				break;
				
			}
		}
	}

}
