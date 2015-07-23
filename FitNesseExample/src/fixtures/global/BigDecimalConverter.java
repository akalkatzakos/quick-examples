package fixtures.global;

import java.math.BigDecimal;

import fitnesse.slim.Converter;

public class BigDecimalConverter implements Converter<BigDecimal> {

	@Override
	public BigDecimal fromString(String arg0) {
		if (arg0 != null && !arg0.isEmpty())
			return new BigDecimal(arg0);
		return null;
	}

	@Override
	public String toString(BigDecimal arg0) {
		return arg0.toString();
	}

}
