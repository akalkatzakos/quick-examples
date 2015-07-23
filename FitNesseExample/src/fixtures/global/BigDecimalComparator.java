package fixtures.global;

import java.math.BigDecimal;
import java.math.RoundingMode;

import fitnesse.testsystems.slim.CustomComparator;

public class BigDecimalComparator implements CustomComparator {
	private static final int SCALE_BY = 2;

	@Override
	public boolean matches(String arg0, String arg1) {		
		return round(arg0).equals(round(arg1));
	}

	public BigDecimal round(BigDecimal value) {
		return value.setScale(SCALE_BY, RoundingMode.HALF_EVEN);
	}

	public BigDecimal round(String value) {
		return round(new BigDecimal(value));
	}
	
	public static void main(String[] args) {
		BigDecimalComparator obj = new BigDecimalComparator();
		System.out.println(obj.round (new BigDecimal("22990.817965")));
		System.out.println(obj.round (new BigDecimal("22990.81797")));
		System.out.println(obj.matches("22990.819", "22990.818"));
	}

}
