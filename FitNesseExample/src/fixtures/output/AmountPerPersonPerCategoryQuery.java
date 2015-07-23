package fixtures.output;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import fixtures.global.Repository;

public class AmountPerPersonPerCategoryQuery {

	public List<Object> query() {

		LinkedList<Object> allResults = new LinkedList<Object>();

		Map<String, Map<Object, BigDecimal>> totalPerPersonPerCategory = Repository.getInstance()
				.getTotalPerPersonPerCategory();

		if (totalPerPersonPerCategory != null) {
			for (String person : totalPerPersonPerCategory.keySet()) {
				Map<Object, BigDecimal> amountPerCategory = totalPerPersonPerCategory.get(person);
				for (Object category: amountPerCategory.keySet()) {					
					allResults.add(asList(
							asList("name", person),
							asList("category", category),
							asList("amount", amountPerCategory.get(category))));
				}
			}

		}

		return allResults;
	}
}
