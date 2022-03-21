package utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import Userarea.Librarian;
import Userarea.Member;
import Userarea.Owner;
import dao.UserDao;
import pojo.User;

public class UserUtil {
static Scanner scan = new Scanner(System.in);
public final static String OWNEREMAIL = "owner@gmail.com";
	
	public static void signUp() {
		try (UserDao user = new UserDao();) {
			User u = UserUtil.acceptUser();
			int res = user.insert(u);
			System.out.println(res + " inserted");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public static void signIn() {
		String email, password;
		System.out.print("Enter Email : ");
		email = scan.nextLine();
		System.out.print("Enter Password : ");
		password = scan.nextLine();
		String role = null;
		if((role = userFind(email, password)) != null) {
			if ((email.equals(OWNEREMAIL)))
				Owner.ownerArea(email);
			else if (role.equals("Member"))
				Member.memberArea(email);
			else if (role.equals("Librarian"))
				Librarian.librarianArea(email);
		}else
			System.out.println("User not Found.");
		
	}
	
	public static User acceptUser() {
		System.out.print("Enter Name : ");
		String name = scan.nextLine();
		System.out.print("Enter Email ID : ");
		String email = scan.nextLine();
		System.out.print("Enter Phone Number : ");
		String phone = scan.nextLine();
		System.out.print("Enter Password : ");
		String password = scan.nextLine();
		if(email.compareTo(OWNEREMAIL) == 0)
			return new User(name, email, password, phone, "Owner");
		else
			return new User(name, email, password, phone, "Member");
		
	}

	public static String userFind(String email, String password) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement stmtFind = connection.prepareStatement("Select * from users where email = ?");){
			String role = null;
			stmtFind.setString(1, email);
			ResultSet rs = stmtFind.executeQuery();
			String passwordToMatch = null;
			while(rs.next()) {
				passwordToMatch = rs.getString(4);
				if(password.equals(passwordToMatch)) {
					role = rs.getString(6);
	
				}
			}
			return role;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static User editUserFind(String email) {
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement stmtFind = connection.prepareStatement("Select * from users where email = ?");) {
			stmtFind.setString(1, email);
			ResultSet rs = stmtFind.executeQuery();
			//String passwordToMatch = null;
			User user = null;
			while (rs.next()) {
					user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6));		
			}
			rs.close();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
