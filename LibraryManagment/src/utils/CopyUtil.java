package utils;


import java.util.Scanner;

import dao.CopyDao;
import pojo.Copy;

public class CopyUtil {
	static Scanner scan = new Scanner(System.in);
	private static int res;
	 
	
	public static void addCopy() {
		try (CopyDao dao = new CopyDao()) {
			Copy copy = new Copy();
			System.out.print("Enter book id : ");
			copy.setBook_id(scan.nextInt());
			scan.nextLine();
			System.out.print("Enter rack no : ");
			copy.setRack(scan.nextLine());
			copy.setStatus("available");
			System.out.print("Enter Subject : ");
			copy.setSubject(scan.nextLine());
			dao.insert(copy);
			System.out.println("Copy Added");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static int checkAvail() {
		try (CopyDao dao = new CopyDao()) {
			System.out.print("Enter Book id ");
			 res = dao.checkBook(scan.nextInt());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	
	public static void chengeRack() {
		try (CopyDao dao = new CopyDao()) {
			System.out.print("Enter copy id : ");
			int num = scan.nextInt();
			scan.nextLine();
			System.out.print("Enter new rack : ");
			String rack=scan.nextLine();
			dao.changeRack(num,rack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

}
