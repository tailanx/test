package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import com.yidejia.app.mall.ctrl.ShipLogViewCtrl;
import com.yidejia.app.mall.model.ShipLog;
import com.yidejia.app.mall.net.express.GetShipLog;

public class TaskGetShipLog {
	
	private Activity activity;
	private LinearLayout layout;
	private String shipCode;
	private ArrayList<ShipLog> shipLogs;
	
	private Task task;
	
	public TaskGetShipLog(Activity activity, LinearLayout layout){
		this.activity = activity;
		this.layout = layout;
	}
	
	public ArrayList<ShipLog> getShipLogs(String shipCode){
		this.shipCode = shipCode;
		
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		task = new Task();
		task.execute();
		
		
		return shipLogs;
	}
	
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		
		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				if(shipLogs != null && !shipLogs.isEmpty()){
					int length = shipLogs.size();
					for (int i = 0; i < length; i++) {
						ShipLogViewCtrl viewCtrl = new ShipLogViewCtrl(activity);
						View view = viewCtrl.addView();
						viewCtrl.setText(shipLogs.get(length - 1- i).getContext(), shipLogs.get(length - 1- i).getTime());
						if(i == 0){
							viewCtrl.setTextColor("#ed217c");
						} else {
							viewCtrl.setTextColor("#000000");
						}
						layout.addView(view);
					}
				}
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetShipLog getShipLog = new GetShipLog();
			String httpResp;
			boolean isSuccess = false;
			try {
				httpResp = getShipLog.getHttpResp(shipCode);
				isSuccess = getShipLog.analysisHttpResp(httpResp);
				shipLogs = getShipLog.getShipLogs();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return isSuccess;
		}
		
	}
	
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}
}
