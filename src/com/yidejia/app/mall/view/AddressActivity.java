package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DefinalDate;

public class AddressActivity extends SherlockActivity {

	private AddressDataManage mangerAddressDataManage;

	// private TextView areaTextView;//地区地址
	// private TextView addressTextView;//具体地址
	// private TextView nameTextView;//收货人姓名
	// private TextView numberTextView;//收货人电话
	// private ArrayList<Addresses> addressesArray;//收货人地址栏
	private String TAG = "AddressDataManage";// log
	// private ImageView deleteImageView;//删除
	// private ImageView editImageView;//编辑
	// private CheckBox checkBox;//单选框
//	private LinearLayout layout;
	// private View view;
	private AddressAdapter adapter;
	private AddressDataManage addressDataManage;
	private ListView listView;
	private ArrayList<Addresses> mAddresses;
	private MyApplication myApplication;
	private PullToRefreshListView pullToRefreshListView;
	// private void setupShow(){
	//
	// try {
	// layout = (LinearLayout) findViewById(R.id.address_management_relative2);
	//
	//
	// // deleteImageView =
	// (ImageView)view.findViewById(R.id.address_management_item_relative1_textview1);
	// // editImageView =
	// (ImageView)view.findViewById(R.id.address_management_item_relative1_textview2);
	// // checkBox =
	// (CheckBox)view.findViewById(R.id.address_management_item_relative1_checkBox1);
	//
	// // deleteImageView.setOnClickListener(this);
	//
	//
	//
	// // new AddressUtil(AddressActivity.this,layout).AllAddresses();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Log.e(TAG, "addAddress() ExecutionException");
	// }
	// }

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setActionbar();
	// //���︸����Ҫ��scrollview
	// setContentView(R.layout.address_management);
	// // mangerAddressDataManage = new AddressDataManage(this);
	// // addressesArray = mangerAddressDataManage.getAddressesArray(654321, 0,
	// 10);
	// // addView =
	// getLayoutInflater().inflate(R.layout.address_management_item, null);
	// setupShow();
	// new AddressUtil(AddressActivity.this,layout).AllAddresses();
	//
	//
	// }
	// public void updateView(ArrayList<Addresses> mAddresses) {
	// adapter.changeDate(mAddresses);
	// }

//	int fromIndex = 0;
//	int acount = 50;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.address_management);
		
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.address_item_main_refresh_scrollview111);
		
		pullToRefreshListView.setOnRefreshListener(listener);
		
		String label = getResources().getString(R.string.update_time)+DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_SHOW_TIME);
		pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		
		
		listView = pullToRefreshListView.getRefreshableView();
//		layout = (LinearLayout) findViewById(R.id.address_management_relative2);
		addressDataManage = new AddressDataManage(AddressActivity.this);
		
		myApplication = (MyApplication) getApplication();
		mAddresses = addressDataManage.getAddressesArray(
				myApplication.getUserId(), fromIndex, acount);
		adapter = new AddressAdapter(AddressActivity.this, mAddresses);
		listView.setAdapter(adapter);
//		Utility.setListViewHeightBasedOnChildren(listView);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.i("info",   "   OnItemClickListener");
			Addresses addresses = mAddresses.get(position);
			Intent intent = new Intent(AddressActivity.this, CstmPayActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("addresses1", addresses);
			intent.putExtras(bundle);
			AddressActivity.this.setResult(Consts.AddressResponseCode, intent);
			AddressActivity.this.finish();
//
//			// // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//			ViewHolder viewHolder = (ViewHolder) view.getTag();
////			if (viewHolder.cb.isChecked()) {
////				Log.i("info", viewHolder.cb.isChecked()+"    viewHolder");
////				return;
////			} else {
////				Log.i("info", viewHolder.cb.isChecked()+"    viewHolder11");
//				viewHolder.cb.toggle();// 把CheckBox的选中状态改为当前状态的反,gridview确保是单一选中
		}
		});
	}
	private void setupShow(){
		adapter.notifyDataSetChanged();
	}
	private int fromIndex = 0;
	private int acount = 10;
	private OnRefreshListener<ListView> listener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time)+DateUtils.formatDateTime(AddressActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_ABBREV_ALL);
			pullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex = 0;
			AddressDataManage dataManage = new AddressDataManage(AddressActivity.this);
			ArrayList<Addresses> addressesList = dataManage.getAddressesArray(myApplication.getUserId(), fromIndex, acount);
			pullToRefreshListView.onRefreshComplete();
			if(addressesList.isEmpty()){
				if(mAddresses.isEmpty()){
					Toast.makeText(AddressActivity.this, getResources().getString(R.string.load_addresses), Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(AddressActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				}
//				Toast.makeText(AddressActivity.this, t, duration).show();
			}else{
				mAddresses.clear();
				mAddresses.addAll(addressesList);
				adapter.notifyDataSetChanged();
			}
//			setupShow();
		}
	};

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		// getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		// startActionMode(new
		// AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView leftButton = (ImageView) findViewById(R.id.actionbar_left);
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AddressActivity.this.finish();
			}
		});
		Button rightButton = (Button) findViewById(R.id.actionbar_right);
		rightButton.setText(getResources().getString(R.string.new_address));
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(AddressActivity.this,
						NewAddressActivity.class);
				startActivityForResult(intent2, DefinalDate.requestcode);// 发送Intent,并设置请求码
				// AddressActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.actionbar_title);
		titleTextView
				.setText(getResources().getString(R.string.manage_address));
		Log.e(TAG, titleTextView.getText().toString());
	}

//	public void updateView(ArrayList<Addresses> musics) {
//		adapter.changeData(musics);
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == DefinalDate.requestcode
					&& resultCode == DefinalDate.responcode) {
				try {
					Bundle bundle = data.getExtras();
					Addresses addresses = (Addresses) bundle
							.getSerializable("newaddress");
					// Log.i(TAG, TAG+"onResume");
					mAddresses.add(addresses);
					// adapter.mAddresses.clear();
					// AddressDataManage addressDataManage1= new
					// AddressDataManage(AddressActivity.this);
					// adapter.mAddresses =
					// addressDataManage1.getAddressesArray(myApplication.getUserId(),
					// fromIndex, acount);
					adapter.notifyDataSetChanged();
//										Utility.setListViewHeightBasedOnChildren(listView);
//					listView.setAdapter(adapter);
					// new AddressUtil(AddressActivity.this,
					// layout).addAddresses(data);
					// layout.invalidate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(AddressActivity.this,
							getResources().getString(R.string.wrong_input),
							Toast.LENGTH_SHORT).show();
				}

			} else if (requestCode == DefinalDate.requestcode
					&& resultCode == DefinalDate.responcode1) {
//				listView.removeAllViews();
//				ArrayList<Addresses> addresses =  mAddresses = addressDataManage.getAddressesArray(
//						myApplication.getUserId(), fromIndex, acount);
//				adapter.changeData(addresses);
//				adapter.notifyDataSetChanged();
//				new AddressUtil(AddressActivity.this, layout)
//						.updateAddresses(data);
//				layout.invalidate();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(AddressActivity.this,
					getResources().getString(R.string.bad_network),
					Toast.LENGTH_SHORT).show();

		}
	}

}
