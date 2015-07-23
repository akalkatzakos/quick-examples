package fixtures.global;

import java.math.BigDecimal;
import java.util.Date;

public class InitializeSystem {
	public InitializeSystem() {
		fitnesse.slim.converters.ConverterRegistry.addConverter(
				BigDecimal.class, new BigDecimalConverter());

		fitnesse.slim.converters.ConverterRegistry.addConverter(Date.class,
				new MyDateConverter());

		Repository.refresh();
	}

}
