package com.testing.swagger_spring_security.util;

import java.util.List;

public class CommonUtil {

	public static boolean validList(List<?> dataList) {
		if(dataList != null && !dataList.isEmpty() && dataList.size() > 0)
			return true;
		return false;
	}

}
