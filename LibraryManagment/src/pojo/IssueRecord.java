package pojo;

import java.sql.Date;

public class IssueRecord {
	private int issueId;
	private int copyId;
	private int userId;
	private Date issueDate;
	private Date returnDuedate;
	private Date returnDate;
	private double fine;
	
	public IssueRecord() {
	}

	public IssueRecord(int issueId, int copyId, int userId, Date issueDate, Date returnDuedate, Date returnDate,
			double fine) {
		this.issueId = issueId;
		this.copyId = copyId;
		this.userId = userId;
		this.issueDate = issueDate;
		this.returnDuedate = returnDuedate;
		this.returnDate = returnDate;
		this.fine = fine;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDuedate() {
		return returnDuedate;
	}

	public void setReturnDuedate(Date returnDuedate) {
		this.returnDuedate = returnDuedate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	@Override
	public String toString() {
		return "IssueRecord [issueId=" + issueId + ", copyId=" + copyId + ", userId=" + userId + ", issueDate="
				+ issueDate + ", returnDuedate=" + returnDuedate + ", returnDate=" + returnDate + ", fine=" + fine
				+ "]";
	}

	
	
}
