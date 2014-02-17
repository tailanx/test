package com.yidejia.app.mall.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
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
	
	@SuppressLint("SimpleDateFormat")
	public static long stringToTimestamp(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {

			d = sdf.parse(strDate);
			long l = d.getTime();
//			String str = String.valueOf(l);
//			String re_time = str.substring(0, 10);
			return l / 1000;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
