package fixtures.global;

import java.text.ParseException;

import static application.Calculate.DATEFORMAT;

import java.util.Date;

import fitnesse.slim.Converter;

public class MyDateConverter implements Converter<Date> {

	@Override
	public Date fromString(String arg0) {
		if (arg0 != null) {
			try {
				return DATEFORMAT.parse(arg0);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String toString(Date arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
