package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.voucher.Voucher;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
	private Context context;
	
	public VoucherDataManage(Context context){
		this.context = context;
	}
	
	/**
	 * 获取用户积分
	 * @param userid 用户id
	 * @return
	 */
	public String getUserVoucher(String userid, String token){
		this.id = userid;
		this.token = token;
		
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return voucherNum;
		}
		boolean state = false;
		
		TaskVoucher taskVoucher = new TaskVoucher();
		
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
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		if(!state){
			
		}
		return voucherNum;
	};
	
	private class TaskVoucher extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Voucher voucher = new Voucher();
			try {
				String httpResponse = voucher.getHttpResponse(id, token);
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
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	}
}