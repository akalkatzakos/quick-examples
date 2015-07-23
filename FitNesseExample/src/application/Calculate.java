package application;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

public class Calculate {
	public static SimpleDateFormat DATEFORMAT = new SimpleDateFormat("dd/MM/yy");

	private List<Transaction> transactions;
	private Map<String, Map<Object, BigDecimal>> totalPerPersonPerMonth;
	private Map<String, Map<Object, BigDecimal>> totalPerPersonPerCategory;
	private Map<String, BigDecimal> totalAmountPerPerson;

	public Calculate(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void run() {
		totalAmountPerPerson = transactions.stream().collect(
				groupingBy(Transaction::getName, sumUpAmount()));

		System.out.println("totalAmountPerPerson: " + totalAmountPerPerson);

		totalPerPersonPerMonth = transactions.stream().collect(
				groupingBy(Transaction::getName, groupingBy(trans -> {
					return getMonth(trans.getDate());
				}, sumUpAmount())));

		System.out.println("totalPerPersonPerMonth: " + totalPerPersonPerMonth);

		totalPerPersonPerCategory = transactions.stream().collect(
				groupingBy(Transaction::getName, groupingBy(trans -> {
					return trans.getCategory().toLowerCase();
				}, sumUpAmount())));

		System.out.println("totalPerPersonPerCategory: "
				+ totalPerPersonPerCategory);
	}

	private Collector<Transaction, ?, BigDecimal> sumUpAmount() {
		return mapping(Transaction::getAmount,
				reducing(BigDecimal.ZERO, (a, b) -> a.add(b)));
	}

	public Month getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mon = cal.get(Calendar.MONTH);
		return Month.of(mon + 1);
	}

	public static void main(String[] args) throws ParseException {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("alex", new BigDecimal("100"),
				"clothes", DATEFORMAT.parse("13/04/14")));
		transactions.add(new Transaction("alex", new BigDecimal("31"), "food",
				DATEFORMAT.parse("15/05/14")));
		transactions.add(new Transaction("alex", new BigDecimal("110"), "food",
				DATEFORMAT.parse("19/04/14")));
		transactions.add(new Transaction("alex", new BigDecimal("50"), "food",
				DATEFORMAT.parse("19/08/14")));
		transactions.add(new Transaction("maria", new BigDecimal("22"),
				"clothes", DATEFORMAT.parse("12/04/14")));
		transactions.add(new Transaction("maria", new BigDecimal("150"),
				"food", DATEFORMAT.parse("12/04/14")));
		transactions.add(new Transaction("maria", new BigDecimal("200"),
				"clothes", DATEFORMAT.parse("18/04/14")));
		transactions.add(new Transaction("chris", new BigDecimal("18.56"),
				"clothes", DATEFORMAT.parse("12/09/14")));
		transactions.add(new Transaction("chris", new BigDecimal("18.5980"),
				"food", DATEFORMAT.parse("12/09/14")));
		transactions.add(new Transaction("chris", new BigDecimal("25.600"),
				"clothes", DATEFORMAT.parse("18/09/14")));
		new Calculate(transactions).run();
	}

	public Map<String, BigDecimal> getTotalAmountPerPerson() {
		return totalAmountPerPerson;
	}

	public Map<String, Map<Object, BigDecimal>> getTotalPerPersonPerMonth() {
		return totalPerPersonPerMonth;
	}

	public Map<String, Map<Object, BigDecimal>> getTotalPerPersonPerCategory() {
		return totalPerPersonPerCategory;
	}
}
