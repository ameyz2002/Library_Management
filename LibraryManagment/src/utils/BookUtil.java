package utils;

import java.util.Scanner;

import dao.BookDao;
import pojo.Book;

public class BookUtil {
	static Scanner scan = new Scanner(System.in);
	public static void addBook() {
		try(BookDao dao= new BookDao();){
			Book book = acceptBook();
			int res = dao.insert(book);
			System.out.println(res + " Book inserted");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private static Book acceptBook() {

		System.out.print("Enter Book Name : ");
		String name = scan.nextLine();
		System.out.print("Enter Author Name : ");
		String author = scan.nextLine();
		System.out.print("Enter Subject Name : ");
		String subject = scan.nextLine();
		System.out.print("Enter Price : ");
		Double price = scan.nextDouble();
		scan.nextLine();
		System.out.print("Enter ISBN : ");
		String isbn = scan.nextLine();
		
	return new Book(name, author, subject, price, isbn);

	}
	
	public static void findBook() {
		try (BookDao dao = new BookDao();) {
			System.out.print("Enter Book Name : ");
			String bookName =scan.nextLine();
			dao.search(bookName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static int editBookMenu() {
		System.out.println("0. Exit");	
		System.out.println("1. Update Name  ");
		System.out.println("2. Update Author  ");
		System.out.println("3. Update Subject ");
		System.out.print("4. Update Price ");
		System.out.print("Enter Choice : ");
		return scan.nextInt();
	} 
	public static void editBook() {
		try(BookDao dao = new BookDao()){
		System.out.print("Enter book_id : ");
		Book book= dao.getBookByID(scan.nextInt());	
		int choice;
		while((choice = editBookMenu())!=0) {			
			 switch(choice) { 
		        
		        case 1:
		        	scan.nextLine();
		        	System.out.print("Enter new name : ");
		            book.setName(scan.nextLine());
		        	break;
		        case 2:
		        	scan.nextLine();
		        	System.out.print("Enter new author : ");
		        	book.setAuthor(scan.nextLine());		        	
		        	break;
		        	
		        case 3:
		        	scan.nextLine();
		        	System.out.print("Enter new subject : ");
		        	book.setSubject(scan.nextLine());		        	
		        	break;
		        	
		        case 4:
		        	System.out.print("Enter new price : ");
		        	book.setPrice(scan.nextDouble());		             	
		        	break;
			
			}
		}
		dao.updateBooks(book);
		
	}  catch (Exception e) {
		e.printStackTrace();
	}
	}
	

}
