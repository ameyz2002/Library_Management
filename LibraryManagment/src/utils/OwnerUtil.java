package utils;

import java.util.Scanner;

import dao.UserDao;
import pojo.User;

public class OwnerUtil {
	static Scanner scan = new Scanner(System.in);
	public static void appointLibrarian() {
		try (UserDao dao = new UserDao()) {
			User u = UserUtil.acceptUser();
			u.setRole("Librarian");
			if(dao.insert(u) == 1)
				System.out.println("Librarian is Added.");
			else
				System.out.println("Librarian is Not Added.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void listUsers() {
		try (UserDao dao = new UserDao()) {
			dao.getUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static int editMenuList() {
		System.out.println("0. Exit");
		System.out.println("1. Change Name");
		System.out.println("2. Change Password");
		System.out.println("3. Change Phone");
		System.out.print("Enter choice : ");
		return scan.nextInt();
	}

	public static void editProfile(String email) {

		
		try (UserDao dao = new UserDao()) {
			User u = UserUtil.editUserFind(email);
			int choice = 0;
			while((choice = editMenuList()) != 0 ) {
				switch(choice) {
				case 1:    //name
					scan.nextLine();
					System.out.print("Enter New Name : ");
					u.setName(scan.nextLine());
					dao.update(u);
					break;
					
				case 2: 	//Password
					System.out.print("Enter New Password : ");
					u.setPassword(scan.nextLine());
					dao.update(u);
					break;
					
				case 3: 	//Phone
					System.out.print("Enter New Phone Number : ");
					u.setPhone(scan.nextLine());
					dao.update(u);
					break;
					
				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void changePassword(String email) {
		try (UserDao dao = new UserDao()) {
			User u = UserUtil.editUserFind(email);
			System.out.print("Enter New Password : ");
			u.setPassword(scan.nextLine());
			dao.update(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
