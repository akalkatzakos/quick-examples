package fixtures.input;

import java.math.BigDecimal;
import java.util.Date;

import application.Transaction;
import fixtures.global.Repository;

public class AddTransaction {
	private String name;
	private BigDecimal amount;
	private String category;
	private Date date;

	public void execute() {
		Transaction transaction = new Transaction(name, amount, category, date);
		Repository.getInstance().getTransactions().add(transaction);
		System.out.println("added: " + transaction);
	}

	public boolean isAdded() {
		return true;
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
	
	

}
