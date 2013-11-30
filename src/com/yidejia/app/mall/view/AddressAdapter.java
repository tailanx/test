package com.yidejia.app.mall.view;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.address.DeleteUserAddress;
import com.yidejia.app.mall.net.address.SetDefAddr;
import com.yidejia.app.mall.util.DefinalDate;
import com.yidejia.app.mall.widget.YLProgressDialog;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AddressAdapter extends BaseAdapter {
	public ArrayList<Addresses> mAddresses;
//	private AddressDataManage dataManage;
	private Activity activity;
	private LayoutInflater inflater;
	private MyApplication myApplication;
	private int temp = -1;
	private boolean isSelect;
	private SharedPreferences sp;
	
	public AddressAdapter(Activity context, ArrayList<Addresses> mAddresses) {
		this.mAddresses = mAddresses;
		myApplication = (MyApplication) context.getApplication();
//		dataManage = new AddressDataManage(context);
		this.activity = context;
		this.inflater = LayoutInflater.from(context);
		setDefAddr = new SetDefAddr();
//		bar = new ProgressDialog(context);
		
		delAddress = new DeleteUserAddress(context);
		
		sp = MainFragmentActivity.MAINACTIVITY.getSharedPreferences("StateCBId", Activity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("stateCBId", temp);
		editor.commit();
	}

	// public ArrayList<Addresses> getmAddresses() {
	// return mAddresses;
	// }
	//
	// public void setmAddresses(ArrayList<Addresses> mAddresses) {
	// if(mAddresses!=null)
	// this.mAddresses = mAddresses;
	// else
	// this.mAddresses = new ArrayList<Addresses>();
	// }
	//
	// public void changeDate(ArrayList<Addresses> mAddresses ){
	// this.setmAddresses(mAddresses);
	// this.notifyDataSetChanged();
	// }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAddresses.size();
	}

	@Override
	public Addresses getItem(int arg0) {
		// TODO Auto-generated method stub
		return mAddresses.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		temp = sp.getInt("stateCBId", -1);
		Log.e("info", temp + "  get  temp");
		final ViewHolder holder;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.address_management_item,
					null);
			holder = new ViewHolder();
			holder.ctTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_address1);
			holder.detailTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_address2);
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_textview3);
			holder.phoneTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_textview6);
			holder.cb = (CheckBox) convertView
					.findViewById(R.id.address_management_item_relative1_checkBox1);
			holder.deteleImageView = (ImageView) convertView
					.findViewById(R.id.address_management_item_relative1_textview1);
			holder.editImageView = (ImageView) convertView
					.findViewById(R.id.address_management_item_relative1_textview2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		final Addresses addresses = mAddresses.get(position);
//		addressId = addresses.getAddressId();
		final AlertDialog dialog = new Builder(activity)
				.setTitle(activity.getResources().getString(R.string.tips))
				.setIcon(R.drawable.ic_launcher)
				.setMessage(
						activity.getResources().getString(
								R.string.delete_address))
				.setPositiveButton(
						activity.getResources().getString(R.string.sure),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								/*dataManage.deleteAddress(
										myApplication.getUserId(),
										addresses.getAddressId(),
										myApplication.getToken());
								mAddresses.remove(addresses);
								notifyDataSetChanged();*/
								addressId = addresses.getAddressId();
								closeTask();
								delTask = new DelTask();
								delTask.setAddresses(addresses);
								delTask.execute();
							}
						})
				.setNegativeButton(
						activity.getResources().getString(R.string.cancel),
						null).create();
		// if(position == 0){
		// temp = Integer.parseInt(addresses.getAddressId());
		// Log.i("info", position+"onResume");
		// holder.cb.setChecked(true);
		// }

		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		holder.ctTextView.setText(sb.toString());
		holder.detailTextView.setText(addresses.getAddress());
		holder.nameTextView.setText(addresses.getName());
		holder.phoneTextView.setText(addresses.getHandset());
		holder.editImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity,
						EditNewAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("editaddress", addresses);
				intent.putExtras(bundle);
				//如果去到编辑的页面却不提交修改，肯定不能先删除该地址的，这样做是不合理的
//				dataManage.deleteAddress(
//						myApplication.getUserId(),
//						addresses.getAddressId(),
//						myApplication.getToken());
//				mAddresses.remove(addresses);
//				notifyDataSetChanged();
				((Activity) activity).startActivityForResult(intent,
						DefinalDate.requestcode);
			}
		});
		holder.deteleImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Log.i("info", addresses.getAddressId());
				dialog.show();
			}
		});
		
//		final int nowPosition = position;
		
		if(temp != position) holder.cb.setChecked(false);

		holder.cb.setId(position);// 对checkbox的id进行重新设置为当前的position
		if (addresses.getDefaultAddress()) {
			position = temp;
//			temp = holder.cb.getId();
			Log.e("info", "def index :" + position + " and temp:" + temp);
//			temp = position;
//			Editor editor = sp.edit();
//			editor.putInt("stateCBId", position);
//			editor.commit();
//			holder.cb.setChecked(true);
//			holder.cb.setClickable(false);
		} else {
//			holder.cb.setChecked(false);
//			holder.cb.setClickable(true);
		} 
		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
  
			// 把上次被选中的checkbox设为false
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {// 实现checkbox的单选功能,同样适用于radiobutton
					Log.e("info", temp + ":temp:" + holder.cb.getId());
					if(!ConnectionDetector.isConnectingToInternet(activity)) {
						Toast.makeText(activity, activity.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
						return;
					}
//					boolean isSuceess = dataManage.setDefaultAddress(
//							myApplication.getUserId(),
//							addresses.getAddressId(), myApplication.getToken());
//					if (holder.cb.getId() != temp) {
					addressId = addresses.getAddressId();
						closeTask();
						task = new Task();
						task.setButtonView(buttonView);
						task.execute();
//					}
				} else {
//					if(temp == nowPosition){
//						buttonView.setClickable(false);
//						return;
//					} else {
//						holder.cb.setClickable(true);
//					}
				}
			}
		});

		// System.out.println("temp:"+temp);
		// System.out.println("position:"+position);
		if (position == temp)// 比对position和当前的temp是否一致
			holder.cb.setChecked(true);
		else
			holder.cb.setChecked(false);

		return convertView;
	}

	static class ViewHolder {
		private TextView ctTextView;// 省市区
		private TextView detailTextView;// 详细地址
		private TextView nameTextView;// 收货人姓名
		private TextView phoneTextView;// 收货人电话
		private ImageView deteleImageView;// 删除
		private ImageView editImageView;// 编辑
		public CheckBox cb;

	}
	
	
	private SetDefAddr setDefAddr;
	private ProgressDialog bar;
	private String addressId;
	private Task task;
	
	private class Task extends AsyncTask<Void, Void, Boolean> {
		
		private CompoundButton buttonView;
		public void setButtonView(CompoundButton button){
			this.buttonView = button;
		}
		

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp = setDefAddr.getHttpResponse(myApplication.getUserId(),
						addressId, myApplication.getToken());
				return setDefAddr.analysicSetDefJson(httpresp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgress(ProgressDialog.STYLE_SPINNER);
//			bar.setCancelable(true);
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
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result){
				if (temp != -1) {
					// 找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
					CheckBox tempCheckBox = (CheckBox) activity
							.findViewById(temp);
					if (tempCheckBox != null)
						tempCheckBox.setChecked(false);
				}
				if(buttonView != null){
					temp = buttonView.getId();// 保存当前选中的checkbox的id值
//					Editor editor = sp.edit();
//					editor.putInt("stateCBId", temp);
//					editor.commit();
//					buttonView.setClickable(false);
				}
			}
		}
		
	}
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		if(delTask != null && delTask.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			delTask.cancel(true);
		}
	}
	
	
	private DelTask delTask;
	private DeleteUserAddress delAddress;
	private class DelTask extends AsyncTask<Void, Void, Boolean> {
		
		private Addresses addresses;
		public void setAddresses(Addresses addresses){
			this.addresses = addresses;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar.setProgress(ProgressDialog.STYLE_SPINNER);
//			bar.setCancelable(true);
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
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result && addresses != null && !mAddresses.isEmpty()){
				mAddresses.remove(addresses);
				notifyDataSetChanged();
			} else {
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
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp;
				try {
					httpresp = delAddress.deleteAddress(myApplication.getUserId(),
								addressId, myApplication.getToken());
					return delAddress.analysicDeleteJson(httpresp);
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	
	private boolean isTimeout = false;
}
