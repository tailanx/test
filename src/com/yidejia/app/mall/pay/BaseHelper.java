package com.yidejia.app.mall.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;

public class BaseHelper {
	
	/**
	 * 流转字符串方法
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	/**
	 * 显示dialog
	 * 
	 * @param context
	 *            环境
	 * @param strTitle
	 *            标题
	 * @param strText
	 *            内容
	 * @param icon
	 *            图标
	 */
	public static void showDialog(Activity context, String strTitle,
			String strText, int icon) {
		showDialog(context, strTitle, strText, icon, android.R.string.ok, null, -1, null);
	}
	
	/**
	 * 显示dialog
	 * 
	 * @param context
	 *            环境
	 * @param strTitle
	 *            标题
	 * @param strText
	 *            内容
	 * @param icon
	 *            图标
	 * @param strOk
	 *            确定按钮文字，null便是不显示
	 * @param okListener
	 *            确定按钮点击事件
	 * @param strCancel
	 *            取消按钮文字，null表示不显示
	 * @param cancelListener
	 *            取消按钮点击事件
	 * 
	 */
	public static void showDialog(Activity context, String strTitle,
			String strText, int icon, String strOk,
			DialogInterface.OnClickListener okListener, String strCancel,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
		tDialog.setIcon(icon);
		tDialog.setTitle(strTitle);
		tDialog.setMessage(strText);
		if(!TextUtils.isEmpty(strOk))
			tDialog.setPositiveButton(strOk, okListener);
		if(!TextUtils.isEmpty(strCancel))
			tDialog.setNegativeButton(strCancel, cancelListener);
		tDialog.show();
	}
	/**
	 * 显示dialog
	 * 
	 * @param context
	 *            环境
	 * @param strTitle
	 *            标题
	 * @param strText
	 *            内容
	 * @param icon
	 *            图标
	 * @param idOk
	 *            确定按钮文字资源id,-1表示不显示
	 * @param okListener
	 *            确定按钮点击事件
	 * @param idCancel
	 *            取消按钮文字资源id,-1表示不显示
	 * @param cancelListener
	 *            取消按钮点击事件
	 * 
	 */
	public static void showDialog(Activity context, String strTitle,
			String strText, int icon, int idOk,
			DialogInterface.OnClickListener okListener, int idCancel,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
		tDialog.setIcon(icon);
		tDialog.setTitle(strTitle);
		tDialog.setMessage(strText);
		if(-1 != idOk)
			tDialog.setPositiveButton(idOk, okListener);
		if(-1 != idCancel)
			tDialog.setNegativeButton(idCancel, cancelListener);
		tDialog.show();
	}

	/**
	 * 显示进度条
	 * 
	 * @param context
	 *            环境
	 * @param title
	 *            标题
	 * @param message
	 *            信息
	 * @param indeterminate
	 *            确定性
	 * @param cancelable
	 *            可撤销
	 * @return
	 */
	public static ProgressDialog showProgress(Context context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(false);
		// dialog.setDefaultButton(false);
		dialog.setOnCancelListener(new AlixOnCancelListener((Activity) context));

		dialog.show();
		return dialog;
	}

	static class AlixOnCancelListener implements
			DialogInterface.OnCancelListener {
		Activity mcontext;

		AlixOnCancelListener(Activity context) {
			mcontext = context;
		}

		public void onCancel(DialogInterface dialog) {
			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}
	
	
	/**
	 * 获取权限
	 * 
	 * @param permission
	 *            权限
	 * @param path
	 *            路径
	 */
	public static void chmod(String permission, String path) {
		try {
			String command = "chmod " + permission + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 字符串转json对象
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static JSONObject string2JSON(String str, String split) {
		JSONObject json = new JSONObject();
		try {
			String[] arrStr = str.split(split);
			for (int i = 0; i < arrStr.length; i++) {
				String[] arrKeyValue = arrStr[i].split("=");
				json.put(arrKeyValue[0],
						arrStr[i].substring(arrKeyValue[0].length() + 1));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}
}
