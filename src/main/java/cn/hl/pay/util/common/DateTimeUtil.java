package cn.hl.pay.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
}
