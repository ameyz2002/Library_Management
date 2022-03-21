package Userarea;

import java.util.Scanner;

import dao.IssueDao;
import utils.BookUtil;
import utils.CopyUtil;
import utils.OwnerUtil;

public class Member {
	static Scanner scan = new Scanner(System.in);
	
	private static int menuList() {
		System.out.println("0. Sign Out");
		System.out.println("1. Edit Profile");
		System.out.println("2. Change Password");
		System.out.println("3. Book Availability");
		System.out.println("4. Find Book");
		System.out.print("Enter Choice : ");
		return scan.nextInt();
	}


	public static void memberArea(String email) {
		System.out.println("MEMBER AREA");
		try (IssueDao dao = new IssueDao()) {
			int choice;
			while((choice = Member.menuList()) != 0) {
				switch(choice) {
				case 1:		// Edit Profile
					OwnerUtil.editProfile(email);
					break;
				case 2:		// Change Password
					OwnerUtil.changePassword(email);
					break;
				case 3:		// Book Availability
					CopyUtil.checkAvail();
					break;
				case 4:		// Find Book
					BookUtil.findBook();
					break;
				
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}