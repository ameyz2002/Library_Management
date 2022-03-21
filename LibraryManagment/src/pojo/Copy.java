package pojo;

public class Copy {
	private int copy_id;
	private int book_id;
	private String rack;
	private String status;
	private String subject;
	
	
	public Copy() {
	}

	public int getCopy_id() {
		return copy_id;
	}

	public void setCopy_id(int copy_id) {
		this.copy_id = copy_id;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getRack() {
		return rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Copy(int book_id, String rack, String status, String subject) {
		this.book_id = book_id;
		this.rack = rack;
		this.status = status;
		this.subject = subject;
	}

	@Override
	public String toString() {
		return String.format("%-6d%-6d%-10s%-10s%-20s", this.copy_id, this.book_id, this.rack, this.status, this.subject);
	}
	
	
	
	
	
	
	
}
