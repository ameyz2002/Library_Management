package Userarea;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import dao.CopyDao;
import dao.PaymentDao;
import utils.OwnerUtil;

public class Owner {
	static Scanner scan = new Scanner(System.in);
	
	private static int menuList() {
		System.out.println("0. Sign Out");
		System.out.println("1. Appoint Librarian");
		System.out.println("2. Edit Profile");
		System.out.println("3. Change Password");
		System.out.println("4. Fees/Fine Report");
		System.out.println("5. Book Availability");
		System.out.println("6. List All Users");
		System.out.print("Enter Choice : ");
		return scan.nextInt();
	}


	public static void ownerArea(String email) {
		System.out.println("OWNER AREA");
		int choice;
		 try (PaymentDao pdao = new PaymentDao()) {
			while((choice = Owner.menuList()) != 0) {
				switch(choice) {
				case 1:		// Appoint Librarian
					OwnerUtil.appointLibrarian();
					break;
				case 2:		// Edit Profile
					OwnerUtil.editProfile(email);
					break;
				case 3:		// Change Password
					OwnerUtil.changePassword(email);;
					break;
				case 4:		// Fees/Fine Report
					System.out.print("\nEnter starting date : (yyyy:MM:dd)\n");
					LocalDate sdate = LocalDate.of(scan.nextInt(), scan.nextInt(), scan.nextInt());
					System.out.print("\nEnter end date : (yyyy:MM:dd)\n");
					LocalDate edate=LocalDate.of(scan.nextInt(), scan.nextInt(), scan.nextInt());
					pdao.getPaymentReport(Date.valueOf(sdate),Date.valueOf(edate));
					break;
				case 5:		// Book Availability
					CopyDao cdao = new CopyDao();
					cdao.bookRecord();
					break;
				case 6:		// List All Users
					OwnerUtil.listUsers();
					break;
				
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
