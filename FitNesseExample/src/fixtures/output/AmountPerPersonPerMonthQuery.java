package fixtures.output;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import fixtures.global.Repository;

public class AmountPerPersonPerMonthQuery {

	public List<Object> query() {

		LinkedList<Object> allResults = new LinkedList<Object>();

		Map<String, Map<Object, BigDecimal>> totalPerPersonPerMonth = Repository.getInstance()
				.getTotalPerPersonPerMonth();

		if (totalPerPersonPerMonth != null) {
			for (String person : totalPerPersonPerMonth.keySet()) {
				Map<Object, BigDecimal> amountPerMonth = totalPerPersonPerMonth.get(person);
				for (Object month: amountPerMonth.keySet()) {					
					allResults.add(asList(
							asList("name", person),
							asList("month", month),
							asList("amount", amountPerMonth.get(month))));
				}
			}

		}

		return allResults;
	}
}
