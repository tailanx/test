package com.yidejia.app.mall.pay;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.express.ParseExpressJson;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Express;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.view.CstmPayActivity;

public class DeliveryActivity extends BaseActivity{

	private PullToRefreshListView mPullToRefreshListView;
	private ListView listView;
	private DeliveryAdapter adapter;
	
	private ArrayList<Express> distributions;	//配送中心列表
	private String preId = "-1";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setActionbarConfig();
		setTitle("修改发货地点");
		
		preId = getIntent().getStringExtra("preId");
		
		setContentView(R.layout.activity_search_result_list_layout);
		
		initLayout();
		getData();
	}
	
	private void initLayout(){
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullToRefreshListView.setPullToRefreshEnabled(false);
		listView = mPullToRefreshListView.getRefreshableView();
		
		distributions = new ArrayList<Express>();
		
		adapter = new DeliveryAdapter();
		listView.setAdapter(adapter);
		
		/*listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectDelivery(position);
			}
		});*/
	}
	
	private void getData(){
		String url = new JNICallBack().getHttp4GetDistribute("flag%3D%27y%27", "0", "100", "", "", "%2A");
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, new IHttpResp() {
			
			@Override
			public void success(String content) {
				ParseExpressJson parseExpressJson = new ParseExpressJson();
				boolean isSuccess = parseExpressJson.parseDist(content);
				if (isSuccess) {
					distributions = parseExpressJson.getDistributions();
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private void selectDelivery(int position){
		Express delivery = distributions.get(position);
		
		Intent intent = new Intent(this, CstmPayActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("delivery", delivery);
		intent.putExtras(bundle);
		setResult(Consts.DELIVERY_RESULT, intent);
		this.finish();
	}
	
	private class DeliveryAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if(null == distributions) return 0;
			return distributions.size();
		}

		@Override
		public Object getItem(int position) {
			if(null == distributions) return null;
			return distributions.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			final int positionId = position;
			if(null == convertView) {
				convertView = getLayoutInflater().inflate(R.layout.item_postition_select, null);
				holder = new ViewHolder();
				holder.cb_position = (CheckBox) convertView.findViewById(R.id.cb_position);
				holder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			String preidTemp = "";
			preidTemp = distributions.get(position).getPreId();
			if(!TextUtils.isEmpty(preidTemp) && preidTemp.equals(preId)) holder.cb_position.setChecked(true);
			
			holder.tv_position.setText(distributions.get(position).getDisName());
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					selectDelivery(positionId);
				}
			});
			return convertView;
		}
		
		private class ViewHolder{
			private TextView tv_position;
			private CheckBox cb_position;
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "配送中心");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "配送中心");
	}
	
}
