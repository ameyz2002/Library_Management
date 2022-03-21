package pojo;

public class User {
	private int user_id;
	private String name;
	private String email;
	private String password;
	private String phone;
	private String role;

	
	public User() {
	}

	public User(String name, String email, String password, String phone, String role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
	}

	public User(int user_id, String name, String email, String password, String phone, String role) {
		this.user_id = user_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		
		return String.format("%-6d%-20s%-25s%-12s%-10s", this.user_id, this.name, this.email, this.phone, this.role);
	}

	
	
	
}
