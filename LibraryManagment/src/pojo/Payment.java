package pojo;

import java.sql.Date;

public class Payment {
	private int paymentId;
	private int userId;
	private double amount;
	private String type;
	private Date transaction_time;
	private Date nextPaymentDueDate;
	
	public Payment() {
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTransaction_time() {
		return transaction_time;
	}

	public void setTransaction_time(Date transaction_time) {
		this.transaction_time = transaction_time;
	}

	public Date getNextPaymentDueDate() {
		return nextPaymentDueDate;
	}

	public void setNextPaymentDueDate(Date nextPaymentDueDate) {
		this.nextPaymentDueDate = nextPaymentDueDate;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", userId=" + userId + ", amount=" + amount + ", type=" + type
				+ ", transaction_time=" + transaction_time + ", nextPaymentDueDate=" + nextPaymentDueDate + "]";
	}
	
	
	
}
