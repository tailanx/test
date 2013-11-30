package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.voucher.Voucher;
import com.yidejia.app.mall.widget.YLProgressDialog;

/**
 * 获取用户积分
 * @author long bin
 *
 */
public class VoucherDataManage {
	
	private String id;
	private String token;
	
	private String voucherNum = "";
	private String TAG = VoucherDataManage.class.getName();
	private Activity activity;
	private TaskVoucher taskVoucher;
	
	public VoucherDataManage(Activity activity){
		this.activity = activity;
	}
	
	/**
	 * 获取用户积分
	 * @param userid 用户id
	 * @return
	 */
	public String getUserVoucher(String userid, String token){
		this.id = userid;
		this.token = token;
		
		if(!ConnectionDetector.isConnectingToInternet(activity)) {
			Toast.makeText(activity, activity.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return voucherNum;
		}
//		boolean state = false;
		if(taskVoucher != null && AsyncTask.Status.RUNNING == taskVoucher.getStatus()){
			taskVoucher.cancel(true);
		}
		taskVoucher = new TaskVoucher();
		taskVoucher.execute();
		
//		try {
//			state = taskVoucher.execute().get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			Log.e(TAG, "task voucher InterruptedException");
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			Log.e(TAG, "task voucher ExecutionException");
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO: handle exception
//			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
//		}
//		if(!state){
//			
//		}
		return voucherNum;
	};
	
	private boolean isFromPay = false;
	
	/**
	 * 获取用户积分
	 * @param userid 用户id
	 * @return
	 */
	public String getUserVoucherForPay(String userid, String token, boolean isFromPay){
		this.id = userid;
		this.token = token;
		this.isFromPay = isFromPay;
		
		if(!ConnectionDetector.isConnectingToInternet(activity)) {
			Toast.makeText(activity, activity.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return voucherNum;
		}
//		if(taskVoucher != null && AsyncTask.Status.RUNNING == taskVoucher.getStatus()){
//			taskVoucher.cancel(true);
//		}
		taskVoucher = new TaskVoucher();
//		taskVoucher.execute();
		
		boolean state = false;
		try {
			state = taskVoucher.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "task voucher InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "task voucher ExecutionException");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(activity, activity.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		if(!state){
			
		}
		return voucherNum;
	};
	
	private boolean isTimeout = false;
	
	private class TaskVoucher extends AsyncTask<Void, Void, Boolean>{
		
		private ProgressDialog bar;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar = new ProgressDialog(activity);
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(activity.getResources().getString(R.string.loading));
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(activity)
					.createLoadingDialog(activity, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Voucher voucher = new Voucher();
			try {
				String httpResponse;
				try {
					httpResponse = voucher.getHttpResponse(id, token);
					try {
						JSONObject httpObject = new JSONObject(httpResponse);
						int code = httpObject.getInt("code");
						if(code == 1){
							String response = httpObject.getString("response");
							JSONObject resObject = new JSONObject(response);
							voucherNum = resObject.getString("can_use_score");
							return true;
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (TimeOutEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					isTimeout = true;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result && !isFromPay){
				TextView jiFen = (TextView) activity.findViewById(R.id.jiefen);
				if(voucherNum==null||"".equals(voucherNum)){
					jiFen.setText("0");
				}else{
					jiFen.setText(voucherNum);
				}
			} else{
				if (isTimeout) {
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
				}
			}
				
		}
		
	}
	
	public void cancelTask(){
		if(taskVoucher != null && AsyncTask.Status.RUNNING == taskVoucher.getStatus()){
			taskVoucher.cancel(true);
		}
	}
}