/**
 * Author : chiziyue
 * Date : 2022年5月24日 下午3:48:44
 * Title : org.scaffold.feign.databind.ScaffoldDataFormat.java
 *
**/
package org.scaffold.feign.databind;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScaffoldDataFormat extends DateFormat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2653422354281757901L;
	private DateFormat dateFormat;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ScaffoldDataFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		return dateFormat.format(date, toAppendTo, fieldPosition);
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		Date date = null;
		try {
			date = simpleDateFormat.parse(source, pos);
		} catch (Exception e) {
			date = dateFormat.parse(source, pos);
		}
		return date;
	}
	
	@Override
	public Date parse(String source) throws ParseException {
		Date date = null;
		try {
			date = simpleDateFormat.parse(source);
		} catch (Exception e) {
			date = dateFormat.parse(source);
		}
		return date;
	}
	
	@Override
	public Object clone() {
		Object object = dateFormat.clone();
		return new ScaffoldDataFormat((DateFormat)object);
	}

}
