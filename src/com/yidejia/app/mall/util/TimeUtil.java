package com.yidejia.app.mall.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	@SuppressLint("SimpleDateFormat")
	private static String convert(long mill, String format) {
		Date date = new Date(mill * 1000L);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	public static String converDate(long mill){
		return convert(mill, "yyyy-MM-dd");
	}
	
	public static String convertMill(long mill){
		return convert(mill, "yyyy-MM-dd HH:mm:ss");
	}
}
