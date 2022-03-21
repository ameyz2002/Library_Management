package dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pojo.User;
import utils.DBUtil;

public class UserDao implements Closeable {
	private Connection connection;
	private PreparedStatement stmtInsert;
	private PreparedStatement stmtSelect;
	private PreparedStatement stmtUpdate;

	public UserDao() throws Exception {
		this.connection = DBUtil.getConnection();
		this.stmtInsert = this.connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?)");
		this.stmtSelect = this.connection.prepareStatement("SELECT * FROM users");
		this.stmtUpdate = this.connection.prepareStatement("UPDATE users SET name=?, password=?, phone=? WHERE user_id=?");	
	}

	// insert
	public int insert(User u) throws Exception {

		String query = "Select max(user_id) as max from users ";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		int num = 0;
		while (rs.next())
			num = rs.getInt("max") + 1;
		u.setUser_id(num);
		this.stmtInsert.setInt(1, u.getUser_id());
		this.stmtInsert.setString(2, u.getName());
		this.stmtInsert.setString(3, u.getEmail());
		this.stmtInsert.setString(5, u.getPassword());
		this.stmtInsert.setString(4, u.getPhone());
		this.stmtInsert.setString(6, u.getRole());
		return this.stmtInsert.executeUpdate();
	}

	// getUsers
	public List<User> getUsers() throws Exception {
		List<User> userList = new ArrayList<>();
		try (ResultSet rs = this.stmtSelect.executeQuery();) {
			while (rs.next()) 
				userList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6)));
			System.out.printf("%-6s%-20s%-25s%-12s%-10s\n","ID","NAME","EMAIL","PHONE","ROLE");
			userList.forEach(System.out::println);
			
		}
		return userList;
	}

	@Override
	public void close() throws IOException {
		try {
			this.stmtInsert.close();
			this.stmtUpdate.close();
			this.stmtSelect.close();
			this.connection.close();
		} catch (SQLException cause) {
			throw new IOException(cause); // Exception Chaining
		}
	}

	public void update(User u) throws SQLException {
				this.stmtUpdate.setString(1, u.getName());
				this.stmtUpdate.setString(2, u.getPassword());
				this.stmtUpdate.setString(3, u.getPhone());
				this.stmtUpdate.setInt(4, u.getUser_id());
				int res = this.stmtUpdate.executeUpdate();
				if( res == 1)
					System.out.println("Profile Updated ");
	}

}
