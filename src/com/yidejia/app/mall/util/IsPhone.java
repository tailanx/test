package com.yidejia.app.mall.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class IsPhone {
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])\\d{8}$");
		Matcher m = p.matcher(mobiles);
		Log.e("info", m.matches() + "");
		return m.matches();
	}
}
