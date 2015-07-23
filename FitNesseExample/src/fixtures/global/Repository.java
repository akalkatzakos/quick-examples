package fixtures.global;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.Transaction;

public class Repository {

	private static Repository instance;
	private List<Transaction> transactions = new ArrayList<>();
	private Map<String, Map<Object, BigDecimal>> totalPerPersonPerMonth;
	private Map<String, Map<Object, BigDecimal>> totalPerPersonPerCategory;
	private Map<String, BigDecimal> totalAmountPerPerson;
	
	public static void refresh() {
		instance = new Repository();
	}
	
	public static Repository getInstance() {
		return instance;
	}
	
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public Map<String, Map<Object, BigDecimal>> getTotalPerPersonPerMonth() {
		return totalPerPersonPerMonth;
	}

	public void setTotalPerPersonPerMonth(
			Map<String, Map<Object, BigDecimal>> totalPerPersonPerMonth) {
		this.totalPerPersonPerMonth = totalPerPersonPerMonth;
	}

	public Map<String, Map<Object, BigDecimal>> getTotalPerPersonPerCategory() {
		return totalPerPersonPerCategory;
	}

	public void setTotalPerPersonPerCategory(
			Map<String, Map<Object, BigDecimal>> totalPerPersonPerCategory) {
		this.totalPerPersonPerCategory = totalPerPersonPerCategory;
	}

	public Map<String, BigDecimal> getTotalAmountPerPerson() {
		return totalAmountPerPerson;
	}

	public void setTotalAmountPerPerson(Map<String, BigDecimal> totalAmountPerPerson) {
		this.totalAmountPerPerson = totalAmountPerPerson;
	}
	
	
	
}
