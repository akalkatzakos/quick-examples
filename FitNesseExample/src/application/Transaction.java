package application;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
	private String name;
	private BigDecimal amount;
	private String category;
	private Date date;
	
	public Transaction() {
	}
	
	public Transaction(String name, BigDecimal amount, String category,
			Date date) {
		super();
		this.name = name;
		this.amount = amount;
		this.category = category;
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Transaction [name=" + name + ", amount=" + amount
				+ ", category=" + category + ", date=" + date + "]";
	}
	
	

}
