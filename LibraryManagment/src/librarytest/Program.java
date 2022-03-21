package librarytest;

import java.util.Scanner;
import utils.UserUtil;

public class Program {
	static Scanner scan = new Scanner(System.in);

	
	private static int menuList() {
		System.out.println("0. EXIT");
		System.out.println("1. SIGN IN");
		System.out.println("2. SIGN UP");
		System.out.print("Enter Choice : ");
		return scan.nextInt();
	}
	public static void main(String[] args) {
		int choice;
		while((choice = Program.menuList())!= 0) {
			switch(choice) {
			case 1 :		// Sign In
				UserUtil.signIn();
				break;
			case 2 :		//Sign Up
				UserUtil.signUp();
				break;
			}
		}
	}
	
}
