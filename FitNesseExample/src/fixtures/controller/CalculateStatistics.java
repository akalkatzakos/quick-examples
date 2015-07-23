package fixtures.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import application.Calculate;
import application.Transaction;
import fixtures.global.Repository;

public class CalculateStatistics {

	public void calculate() {
		List<Transaction> transactions = Repository.getInstance()
				.getTransactions();
		Calculate calculator = new Calculate(transactions);
		calculator.run();

		Repository.getInstance().setTotalAmountPerPerson(
				calculator.getTotalAmountPerPerson());
		Repository.getInstance().setTotalPerPersonPerCategory(
				calculator.getTotalPerPersonPerCategory());
		Repository.getInstance().setTotalPerPersonPerMonth(
				calculator.getTotalPerPersonPerMonth());

	}

	public int numberOfPersons() {
		Map<String, BigDecimal> amountPerPerson = Repository.getInstance()
				.getTotalAmountPerPerson();

		return (amountPerPerson != null) ? amountPerPerson.keySet().size() : 0;
	}
}
