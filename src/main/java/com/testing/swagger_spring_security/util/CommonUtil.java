package com.testing.swagger_spring_security.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtil {

	public static boolean validList(List<?> dataList) {
		if (dataList != null && !dataList.isEmpty() && dataList.size() > 0)
			return true;
		return false;
	}

	public static boolean validString(String data) {
		if (data != null && !data.isEmpty() && !data.equals(""))
			return true;
		return false;
	}

	public static boolean validLong(Long data) {
		if (data != null && data > 0)
			return true;
		return false;
	}

	public static String convertDate(Date data) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(data);
		return date;
	}

	public static boolean validInteger(Integer data) {
		if(data != null && data > 0)
			return true;
		return false;
	}

	public static boolean validGreaterCurrent(Date data) {
		if(data != null && (data.equals(new Date()) || data.after(new Date())))
			return true;
		return false;
	}

}
