package tw.com.ispan.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DatetimeConverter {
	public static String toString(LocalDateTime datetime, String format) {
		String result = "";
		try {
			if (datetime != null) {
				result = new SimpleDateFormat(format).format(datetime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date parse(String datetime, String format) {
		Date result = new Date();
		try {
			result = new SimpleDateFormat(format).parse(datetime);
		} catch (Exception e) {
			result = new Date();
			e.printStackTrace();
		}
		return result;
	}
}
