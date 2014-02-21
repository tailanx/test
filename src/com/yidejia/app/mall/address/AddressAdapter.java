package com.yidejia.app.mall.address;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.pay.CstmPayActivity;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AddressAdapter extends BaseAdapter {
	public ArrayList<ModelAddresses> mAddresses;
	private Activity activity;
	private LayoutInflater inflater;
	private MyApplication myApplication;
	private int temp = -1;
	// private boolean isSelect;
	private SharedPreferences sp;
//	private RequestParams requestParams;
//	private String contentType;
	private String hosturl;
	

	public AddressAdapter(Activity context, ArrayList<ModelAddresses> mAddresses) {
		this.mAddresses = mAddresses;
		myApplication = MyApplication.getInstance();
		// dataManage = new AddressDataManage(context);
		this.activity = context;
		this.inflater = LayoutInflater.from(context);
//		setDefAddr = new SetDefAddr();
		// bar = new ProgressDialog(context);

//		delAddress = new DeleteUserAddress(context);

		sp = myApplication.getSharedPreferences("StateCBId",
				Activity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("stateCBId", temp);
		editor.commit();

//		requestParams = new RequestParams();
		hosturl = new JNICallBack().HTTPURL;
//		contentType = "application/x-www-form-urlencoded;charset=UTF-8";
	}

	@Override
	public int getCount() {
		if (null == mAddresses)
			return 0;
		return mAddresses.size();
	}

	@Override
	public ModelAddresses getItem(int arg0) {
		if (null == mAddresses)
			return null;
		return mAddresses.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final int positionId = position;
		temp = sp.getInt("stateCBId", -1);
//		Log.e("info", temp + "  get  temp");
		final ViewHolder holder;

//		if (null == convertView) {
			convertView = inflater.inflate(R.layout.address_management_item,
					null);
			holder = new ViewHolder();
			holder.ctTextView = (TextView) convertView
					.findViewById(R.id.tv_address_management_item_address1);
			holder.detailTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_address2);
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_textview3);
			holder.phoneTextView = (TextView) convertView
					.findViewById(R.id.address_management_item_textview6);
			holder.cb = (CheckBox) convertView
					.findViewById(R.id.address_management_item_relative1_checkBox1);
			holder.deteleImageView = (TextView) convertView
					.findViewById(R.id.address_management_item_relative1_textview1);
			holder.editImageView = (TextView) convertView
					.findViewById(R.id.address_management_item_relative1_textview2);
			
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}

		final ModelAddresses addresses = mAddresses.get(position);
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

//								addressId = addresses.getAddressId();
								deleDate(addresses);
							}
						})
				.setNegativeButton(
						activity.getResources().getString(R.string.cancel),
						null).create();

		StringBuffer sb = new StringBuffer();
		sb.append(addresses.getProvice());
		sb.append(addresses.getCity());
		sb.append(addresses.getArea());
		holder.ctTextView.setText(activity.getResources().getString(
				R.string.addresss_default_address)
				+ sb.toString());
		holder.detailTextView.setText(addresses.getAddress());
		holder.nameTextView.setText(addresses.getName());
		holder.phoneTextView.setText(addresses.getHandset());
		holder.editImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(activity,
						EditNewAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("editaddress", addresses);
				intent.putExtras(bundle);
				((Activity) activity).startActivityForResult(intent,
						Consts.AddressRequestCode);
			}
		});
		holder.deteleImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.i("info", addresses.getAddressId());
				dialog.show();
			}
		});

		// final int nowPosition = position;

		if (temp != position)
			holder.cb.setChecked(false);

		holder.cb.setId(position);// 对checkbox的id进行重新设置为当前的position
		if (addresses.getDefaultAddress()) {
//			position = temp;
			temp = position;
			Editor editor = sp.edit();
			editor.putInt("stateCBId", temp);
			editor.commit();
			holder.cb.setChecked(true);
		} else {
			holder.cb.setChecked(false);
		}
//		if(!holder.cb.isChecked())
		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			// 把上次被选中的checkbox设为false
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {// 实现checkbox的单选功能,同样适用于radiobutton
					Log.e("info", temp + ":temp:" + holder.cb.getId());
					if (!ConnectionDetector.isConnectingToInternet(activity)) {
						Toast.makeText(
								activity,
								activity.getResources().getString(
										R.string.no_network), Toast.LENGTH_LONG)
								.show();
						return;
					}

//					 addressId = addresses.getAddressId();
//					 closeTask();
//					 task = new Task();
//					 task.setButtonView(buttonView);
//					 task.execute();
					 // }
					setDefAddress(buttonView, addresses);
				} else {
					holder.cb.setChecked(true);
				}
			}
		});

//		if (position == temp)// 比对position和当前的temp是否一致
//			holder.cb.setChecked(true);
//		else
//			holder.cb.setChecked(false);
		if(requestCode == Consts.AddressRequestCode) {
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					selectPayAdds(positionId);
				}
			});
		}

		return convertView;
	}

	static class ViewHolder {
		private TextView ctTextView;// 省市区
		private TextView detailTextView;// 详细地址
		private TextView nameTextView;// 收货人姓名
		private TextView phoneTextView;// 收货人电话
		private TextView deteleImageView;// 删除
		private TextView editImageView;// 编辑
		public CheckBox cb;
//		private LinearLayout llAddress;	//该地址的大布局
	}
	
	/**
	 * 确认订单跳转过来时，选择地址的事件
	 * @param position
	 */
	private void selectPayAdds(int position){
		ModelAddresses addresses = mAddresses.get(position);
		Intent intent = new Intent(activity,
				CstmPayActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("addresses1", addresses);
		intent.putExtras(bundle);
		activity.setResult(Consts.AddressResponseCode,
				intent);
		activity.finish();
	}
	
	/**
	 * 默认为-1，如果是从确认订单页跳转过来的值为Consts.AddressRequestCode
	 * @param requestCode
	 */
	public void setRequestCode(int requestCode){
		this.requestCode = requestCode;
	}
	
	private int requestCode = -1;
	
//	private SetDefAddr setDefAddr;
//	private ProgressDialog bar;
//	private String addressId;
	/*private Task task;

	private class Task extends AsyncTask<Void, Void, Boolean> {

		private CompoundButton buttonView;

		public void setButtonView(CompoundButton button) {
			this.buttonView = button;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				String httpresp = setDefAddr.getHttpResponse(
						myApplication.getUserId(), addressId,
						myApplication.getToken());
				return setDefAddr.analysicSetDefJson(httpresp);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar = (ProgressDialog) new YLProgressDialog(activity)
					.createLoadingDialog(activity, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			bar.dismiss();
			if (result) {
				if (temp != -1) {
					// 找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
					CheckBox tempCheckBox = (CheckBox) activity
							.findViewById(temp);
					if (tempCheckBox != null)
						tempCheckBox.setChecked(false);
				}
				if (buttonView != null) {
					temp = buttonView.getId();// 保存当前选中的checkbox的id值
				}
			}
		}

	}*/

	/*public void closeTask() {
		if (task != null
				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
//		if (delTask != null
//				&& delTask.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
//			delTask.cancel(true);
//		}
	}*/

//	private DelTask delTask;
//	private DeleteUserAddress delAddress;

	/*private class DelTask extends AsyncTask<Void, Void, Boolean> {

		private ModelAddresses addresses;

		public void setAddresses(ModelAddresses addresses) {
			this.addresses = addresses;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			bar = (ProgressDialog) new YLProgressDialog(activity)
					.createLoadingDialog(activity, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			bar.dismiss();
			if (result && addresses != null && !mAddresses.isEmpty()) {
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
			try {
				String httpresp;
				try {
					httpresp = delAddress.deleteAddress(
							myApplication.getUserId(), addressId,
							myApplication.getToken());
					return delAddress.analysicDeleteJson(httpresp);
				} catch (TimeOutEx e) {
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
*/
//	private boolean isTimeout = false;

	private void deleDate(final ModelAddresses addresses) {

		String url = new JNICallBack().getHttp4DelAddress(
				myApplication.getUserId(), addresses.getAddressId(),
				myApplication.getToken());
//		requestParams.put(url);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil(activity);
		httpClientUtil.setIsShowLoading(true);
		httpClientUtil.setShowErrMessage(true);
		httpClientUtil.getHttpResp(hosturl, url, new IHttpResp(){

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				ParseAddressJson parseAddressJson = new ParseAddressJson();
				if (!parseAddressJson.parseSetDefault(content)) {
					return;
				}
				mAddresses.remove(addresses);
				notifyDataSetChanged();
			}
			
		});

//		client.post(hosturl, contentType, requestParams,
//				new AsyncHttpResponse() {
//					@Override
//					public void onStart() {
//						super.onStart();
//
//						new YLProgressDialog(activity);
//						bar = YLProgressDialog.createLoadingDialog(activity,
//								null);
//						bar.setOnCancelListener(new OnCancelListener() {
//
//							@Override
//							public void onCancel(DialogInterface dialog) {
//								bar.dismiss();
//							}
//
//						});
//					}
//
//					@Override
//					public void onFinish() {
//						super.onFinish();
//						bar.dismiss();
//						if (isTimeout) {
//							Toast.makeText(
//									activity,
//									activity.getResources().getString(
//											R.string.no_network),
//									Toast.LENGTH_SHORT).show();
//						}
//					}
//
//					@Override
//					public void onSuccess(int statusCode, String content) {
//						super.onSuccess(statusCode, content);
//						
//						if(HttpStatus.SC_OK == statusCode) {
//							ParseAddressJson parseAddressJson = new ParseAddressJson();
//							if(!parseAddressJson.parseSetDefault(content)){
//								return;
//							} 
//							mAddresses.remove(addresses);
//							notifyDataSetChanged();
//						}
//						
//						/*JSONObject httpResultObject;
//						if (content != null && !"".equals(content)) {
//							try {
//								httpResultObject = new JSONObject(content);
//								int deleResult = httpResultObject
//										.getInt("code");
//								Log.e("info", "-------->onStart" + deleResult);
//								if (deleResult == 1) {
//
//									mAddresses.remove(addresses);
//									notifyDataSetChanged();
//									isTimeout = false;
//								} else {
//									isTimeout = true;
//								}
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								isTimeout = true;
//							}
//
//						} else {
//							return;
//						}*/
//
//					}
//
//					@Override
//					public void onError(Throwable error, String content) {
//						super.onError(error, content);
//						isTimeout = true;
//					}
//
//				});
	}

	private void setDefAddress(final CompoundButton buttonView,
			ModelAddresses addresses) {
		String param = new JNICallBack().getHttp4SetDefAddr(
				myApplication.getUserId(), addresses.getAddressId(),
				myApplication.getToken());
//		String url = new JNICallBack().HTTPURL;
//		requestParams.put(param);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil(activity);
		httpClientUtil.setIsShowLoading(true);
		httpClientUtil.setShowErrMessage(true);
		
		httpClientUtil.getHttpResp(hosturl, param, new IHttpResp(){

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				ParseAddressJson parseAddressJson = new ParseAddressJson();
				if(!parseAddressJson.parseSetDefault(content)) {
					temp = buttonView.getId();
					CheckBox tempCheckBox = (CheckBox) activity
							.findViewById(temp);
					if (tempCheckBox != null)
						tempCheckBox.setChecked(false);
					return;
				}
				
				temp = sp.getInt("stateCBId", -1);
				if (temp != -1) {
					// 找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
					Log.e("info", temp+"temp");
					CheckBox tempCheckBox = (CheckBox) activity
							.findViewById(temp);
					if (tempCheckBox != null)
						tempCheckBox.setChecked(false);
					else Log.e("info", "null");
					mAddresses.get(temp).setDefaultAddress(false);
				}
				if (buttonView != null) {
					temp = buttonView.getId();// 保存当前选中的checkbox的id值
					mAddresses.get(temp).setDefaultAddress(true);
					Editor editor = sp.edit();
					editor.putInt("stateCBId", temp);
					editor.commit();
				}
				notifyDataSetChanged();
			}

			@Override
			public void onError() {
				super.onError();
				temp = buttonView.getId();
				CheckBox tempCheckBox = (CheckBox) activity
						.findViewById(temp);
				if (tempCheckBox != null)
					tempCheckBox.setChecked(false);
			}
			
		});
		
//		Log.e("system.out", )
		/*client.post(url, contentType, requestParams, new AsyncHttpResponse() {
			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				isTimeout = true;
				temp = buttonView.getId();
				CheckBox tempCheckBox = (CheckBox) activity
						.findViewById(temp);
				if (tempCheckBox != null)
					tempCheckBox.setChecked(false);
			}

			@Override
			public void onFinish() {
				super.onFinish();
				bar.dismiss();
			}

			@Override
			public void onStart() {
				super.onStart();
				bar = new YLProgressDialog(activity).createLoadingDialog(
						activity, null);
				bar.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						bar.dismiss();
					}
				});
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (statusCode == HttpStatus.SC_OK) {
					
					ParseAddressJson parseAddressJson = new ParseAddressJson();
					if(!parseAddressJson.parseSetDefault(content)) {
						temp = buttonView.getId();
						CheckBox tempCheckBox = (CheckBox) activity
								.findViewById(temp);
						if (tempCheckBox != null)
							tempCheckBox.setChecked(false);
						return;
					}
					
					temp = sp.getInt("stateCBId", -1);
					if (temp != -1) {
						// 找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
						Log.e("info", temp+"temp");
						CheckBox tempCheckBox = (CheckBox) activity
								.findViewById(temp);
						if (tempCheckBox != null)
							tempCheckBox.setChecked(false);
						else Log.e("info", "null");
						mAddresses.get(temp).setDefaultAddress(false);
					}
					if (buttonView != null) {
						temp = buttonView.getId();// 保存当前选中的checkbox的id值
						mAddresses.get(temp).setDefaultAddress(true);
						Editor editor = sp.edit();
						editor.putInt("stateCBId", temp);
						editor.commit();
					}
					notifyDataSetChanged();
				}
			}
		});*/
	}
	
}
