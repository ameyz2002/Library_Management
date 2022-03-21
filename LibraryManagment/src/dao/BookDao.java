package dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pojo.Book;
import utils.DBUtil;

public class BookDao implements Closeable{
	private Connection connection;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtSelect;
	private PreparedStatement stmtSearch;
	private PreparedStatement stmtFind;
	public BookDao() throws Exception {
		this.connection = DBUtil.getConnection();
		this.stmtInsert = this.connection.prepareStatement("INSERT INTO books VALUES(?,?,?,?,?,?)");
		this.stmtUpdate = this.connection.prepareStatement("update books set name=?,author=?,subject=?,price=? where book_id=?");
		this.stmtSelect = this.connection.prepareStatement("SELECT * FROM books");
		this.stmtSearch = this.connection.prepareStatement("SELECT * FROM books WHERE LOCATE(?,name) != 0");
		this.stmtFind = this.connection.prepareStatement("select * from books where book_id=?");
	}
	//insert
	public int insert(Book book) throws Exception{
		String query = "Select max(book_id) as max from books ";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		int num = 0;
		while (rs.next())
			num = rs.getInt("max") + 1;
		book.setBookId(num);
		this.stmtInsert.setInt(1, book.getBookId()); 
		this.stmtInsert.setString(2, book.getName());
		this.stmtInsert.setString(3, book.getAuthor());
		this.stmtInsert.setString(4, book.getSubject());
		this.stmtInsert.setDouble(5, book.getPrice());
		this.stmtInsert.setString(6, book.getIsbn());
		
		return this.stmtInsert.executeUpdate();
	}
	
	//update
	public void updateBooks(Book book) {		
		try {
			stmtUpdate.setString(1, book.getName());
			stmtUpdate.setString(2, book.getAuthor());
			stmtUpdate.setString(3, book.getSubject());
			stmtUpdate.setDouble(4, book.getPrice());
			stmtUpdate.setInt(5,book.getBookId());
			stmtUpdate.executeUpdate();
			System.out.println("record updated ");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	//getBooks
	public List<Book> getBooks( )throws Exception{
		List<Book> bookList = new ArrayList<>();
		String sql = "SELECT * FROM books"; 
		try (ResultSet rs = this.stmtSelect.executeQuery();) {
			while (rs.next()) 
				bookList.add(  new Book( rs.getInt("book_id"), rs.getString("subject_name"), rs.getString("book_name"), rs.getString("author_name"),rs.getDouble("price"),rs.getString("isbn")) );
			bookList.forEach(System.out::println);
		}
		return bookList;
	}
	
	//getbook_byID
	public Book getBookByID(int num) {
		try {
			stmtFind.setInt(1, num);
			ResultSet rs=stmtFind.executeQuery( );
			Book book=new Book();
			while(rs.next()) {
				book.setBookId(rs.getInt(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setSubject(rs.getString(4));
				book.setPrice(rs.getDouble(5));
				book.setIsbn(rs.getString(6));				
			}
			rs.close();
			return book;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//Search
	public List<Book> search(String bookName) throws Exception {
		this.stmtSearch.setString(1, bookName);
		List<Book> bookFind = new ArrayList<>();
		try(ResultSet rs = this.stmtSearch.executeQuery();) {
			while (rs.next())
				bookFind.add(new Book(rs.getInt("book_id"), rs.getString("name"), rs.getString("author"),
						rs.getString("subject"), rs.getDouble("price"), rs.getString("isbn")));
			bookFind.forEach(System.out::println);
		}
		return bookFind;
		}
	
	@Override
	public void close() throws IOException {
		try {
			this.stmtInsert.close();
			this.stmtUpdate.close();
			this.stmtSelect.close();
			this.connection.close();
		} catch (SQLException cause) {
			throw new IOException(cause);	//Exception Chaining
		}
	}
	
}
