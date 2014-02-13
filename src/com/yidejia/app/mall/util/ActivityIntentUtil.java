package com.yidejia.app.mall.util;

import android.app.Activity;
import android.content.Intent;

public class ActivityIntentUtil {
	
	
	/**
	 * 两个页面跳转,原页面finish
	 * @param fromActivity
	 * @param intentActivity
	 */
	public static void intentActivityAndFinish(Activity fromActivity, Class<?> intentActivity) {
//		Intent intent = new Intent(fromActivity, intentActivity.getClass());
//		fromActivity.startActivity(intent);
		intentActivity(fromActivity, intentActivity);
		fromActivity.finish();
	}
	/**
	 * 两个页面跳转,原页面不finish
	 * @param fromActivity
	 * @param intentActivity
	 */
	public static void intentActivity(Activity fromActivity, Class<?> intentActivity) {
		Intent intent = new Intent(fromActivity, intentActivity);
		fromActivity.startActivity(intent);
	}
	/**
	 * 两个页面跳转,原页面不finish
	 * @param fromActivity
	 * @param intentActivity
	 * @param requestCode
	 */
	public static void intentActivityForResult(Activity fromActivity, Class<?> intentActivity, int requestCode) {
		Intent intent = new Intent(fromActivity, intentActivity);
		fromActivity.startActivityForResult(intent, requestCode);
	}
}
