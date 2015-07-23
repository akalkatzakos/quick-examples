package fixtures.output;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static java.util.Arrays.asList;

import fixtures.global.Repository;

public class AmountPerPersonQuery {

	public List<Object> query() {

		LinkedList<Object> allResults = new LinkedList<Object>();

		Map<String, BigDecimal> totalPerPerson = Repository.getInstance()
				.getTotalAmountPerPerson();

		if (totalPerPerson != null) {
			for (String person : totalPerPerson.keySet()) {
				allResults.add(asList(
									asList("name", person),
									asList("total", totalPerPerson.get(person))));
			}

		}

		return allResults;
	}
}
