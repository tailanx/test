package com.yidejia.app.mall.task;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
public class TaskGetCode {
	private String reponse;// 返回码信息
	private int code;
	private Activity activity;
//
//	private String name;
//
//	private Task task;
//	private TaskSendCode taskSendCode;
//
	public TaskGetCode(Activity activity) {
		this.activity = activity;
	}
//
//	public void getCode(String name) {
//		this.name = name;
//		if (task != null
//				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
//			task.cancel(true);
//		}
//
//		task = new Task();
//		task.execute();
//	}
//
//	private boolean isTimeout = false;
//
//	private class Task extends AsyncTask<Void, Void, Boolean> {
//
//		private ProgressDialog bar;
//
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			GetCode getCode = new GetCode();
//			try {
//				String httpResp;
//				try {
//					httpResp = getCode.getHttpResp(name);
//					boolean isSuccess = getCode.analysisGetCode(httpResp);
//					regCode = getCode.getCode();
//					return isSuccess;
//				} catch (TimeOutEx e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					isTimeout = true;
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			return false;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			// bar = new ProgressDialog(activity);
//			// bar.setCancelable(true);
//			// bar.setMessage(activity.getResources().getString(R.string.loading));
//			// bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			// bar.show();
//			bar = (ProgressDialog) new YLProgressDialog(activity)
//					.createLoadingDialog(activity, null);
//			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//				@Override
//				public void onCancel(DialogInterface dialog) {
//					// TODO Auto-generated method stub
//					cancel(true);
//				}
//			});
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (result) {
//				TaskSendCode taskSendCode = new TaskSendCode(activity, bar);
//				taskSendCode.sendMsg(name, regCode + "");
//			} else {
//				if (isTimeout) {
//					Toast.makeText(
//							activity,
//							activity.getResources()
//									.getString(R.string.time_out),
//							Toast.LENGTH_SHORT).show();
//					isTimeout = false;
//				}
//				bar.dismiss();
//			}
//		}
//
//	}
//
//	private int regCode;
//
//	public int getRegCode() {
//		return regCode;
//	}
//
//	public void closeTask() {
//		if (task != null
//				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
//			task.cancel(true);
//		}
//
//		if (taskSendCode != null) {
//			taskSendCode.closeTask();
//		}
//	}

	public boolean parse(String content) {
		JSONObject object;
		try {
			object = new JSONObject(content);
			int respCode = object.getInt("code");
			if (respCode == 1) {
				code = object.getInt("response");
			}
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean parseResp(String content) {
		JSONObject object;
		boolean isSucess = false;
		try {
			object = new JSONObject(content);
			int code = object.getInt("code");
			if (code == -1) {
				isSucess = true;
				reponse = object.getString("response");
			} else {
				reponse = object.getString("response");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isSucess;
	}
}
