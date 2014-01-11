package com.yidejia.app.mall.util;

import com.yidejia.app.mall.MyApplication;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager; 

public class DPIUtil {
	private static Display defaultDisplay;
	private static float mDensity = 160.0F;

	public static int dip2px(float paramFloat) {
		return (int) (0.5F + paramFloat * mDensity);
	}

	public static Display getDefaultDisplay() {
		 if (defaultDisplay == null)
		 defaultDisplay = ((WindowManager)MyApplication.getInstance().getSystemService("window")).getDefaultDisplay();
		return defaultDisplay;
	}

	public static float getDensity() {
		return mDensity;
	}

	public static int getHeight() {
		return getDefaultDisplay().getHeight();
	}

	public static int getWidth() {
		return getDefaultDisplay().getWidth();
	}

	public static int percentHeight(float paramFloat) {
		return (int) (paramFloat * getHeight());
	}

	public static int percentWidth(float paramFloat) {
		return (int) (paramFloat * getWidth());
	}

	public static int px2dip(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat / mDensity);
	}

	public static int px2sp(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().scaledDensity);
	}

	public static void setDensity(float paramFloat) {
		mDensity = paramFloat;
	}

	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}
}