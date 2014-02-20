package com.yidejia.app.mall.yirihui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class YirihuiFragment extends Fragment {
	private int index = 0;	
	private PullToRefreshListView refreshListView;
	private ListView lvYiRiHui;
	private YiRiHuiAdapter adapter;
	private ArrayList<YiRiHuiData> yiRiHuiDatas;
	private int type = -2;	//type -1 过去 0现在 1 将来  
	private int offset = 0;	//offset 起始位置
	private int limit = 10;	//limit 偏移量 必须
	private String sort = "";	//sort 排序 默认按结束时间倒序(end_time desc)


	public static YirihuiFragment newInstance(int num) {
		YirihuiFragment now = new YirihuiFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("index", num);
		now.setArguments(bundle);
		return now;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		index = getArguments() != null ? getArguments().getInt("index") : -1;
		switch (index) {
		case 0:	//正在进行
			type = 0;
			break;
		case 1:	//即将开始
			type = 1;
			break;
		case 2:	//已经结束
			type = -1;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common_listview, null);
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.lv_common);
		
		refreshListView.setOnRefreshListener(listener);
		
		lvYiRiHui = refreshListView.getRefreshableView();
		yiRiHuiDatas = new ArrayList<YiRiHuiData>();
		adapter = new YiRiHuiAdapter(getActivity(), yiRiHuiDatas);
		if(type == 0) adapter.setCanBuy(true);
		adapter.setType(type);
		lvYiRiHui.setAdapter(adapter);
		
		getYiriHuiData();
		
//		updateYRH("1");
		
		return view;
	}
	
	/**获取伊日惠数据**/
	private void getYiriHuiData(){
		String url = new JNICallBack().HTTPURL;
		String param = new JNICallBack().getHttp4GetYiRiHui(type + "", offset + "", limit + "", sort);
//		Log.e("system.out", url + "?" + param);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		
		httpClientUtil.setPullToRefreshView(refreshListView);
		
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {
			
			@Override
			public void success(String content) {
//				Log.e("system.out", content);
//				content = "{\"code\":1,\"msg\":\"成功\",\"response\":[{\"the_id\":\"1\",\"rule_name\":\"伊日惠测试活动一\",\"begin_time\":\"2014-02-17 11:03:07\",\"end_time\":\"2014-02-18 00:00:00\",\"goods_id\":\"1590\",\"quantity\":\"1\",\"can_buy_quantity\":\"1\",\"overtime\":\"900\",\"img_1\":\"5/2014/02/17/a0acc98642b.jpg\",\"img_2\":\"8/2014/02/17/a0ac9bce227.jpg\",\"valid_flag\":\"y\",\"shell_flag\":\"y\",\"goods_name\":\"【马年活动】脱盐海泉精华\",\"goods_price\":\"50.00\"}],\"ts\":1392609753}";
				if(!isAdded()) return;
				ParseYiRiHui parseYiRiHui = new ParseYiRiHui();
				boolean isSuccess = parseYiRiHui.parseYiRiHui(content);
				if(isSuccess){
					ArrayList<YiRiHuiData> tempYiRiHuiDatas = parseYiRiHui.getYiRiHuiDatas();
					if(null != tempYiRiHuiDatas) {
						
						if(0 == offset && null != yiRiHuiDatas) {
							yiRiHuiDatas.clear();
						}
						if(null != yiRiHuiDatas)
							yiRiHuiDatas.addAll(tempYiRiHuiDatas);
						if(null != adapter)
							adapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), getString(R.string.nomore), Toast.LENGTH_SHORT).show();
					}
					String label = getResources().getString(R.string.update_time)
							+ DateUtils.formatDateTime(getActivity()
									.getApplicationContext(), System
									.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
					if (null != refreshListView) {
						refreshListView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);
					}
				} else {
					offset -= limit;
				}
			}
		});
	}
	
	private OnRefreshListener2<ListView> listener = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			offset = 0;
			getYiriHuiData();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			offset += limit;
			getYiriHuiData();
		}
	}; 

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (null != yiRiHuiDatas) {
			yiRiHuiDatas.clear();
			yiRiHuiDatas = null;
		} 
		if(null != adapter){
			adapter = null;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (null != yiRiHuiDatas) {
			yiRiHuiDatas.clear();
			yiRiHuiDatas = null;
		}
		if(null != adapter){
			adapter = null;
		}
	}
	
	
}
