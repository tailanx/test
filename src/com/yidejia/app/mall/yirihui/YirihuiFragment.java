package com.yidejia.app.mall.yirihui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class YirihuiFragment extends Fragment {
	private int index;	
//	private PullToRefreshListView refreshListView;
	private int type = 0;	//type -1 过去 0现在 1 将来  
	private int offset = 0;	//offset 起始位置
	private int limit = 10;	//limit 偏移量 必须
	private String sort;	//sort 排序 默认按结束时间倒序(end_time desc)


	public static YirihuiFragment newInstance(int num) {
		YirihuiFragment now = new YirihuiFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("index", num);
		now.setArguments(bundle);
		return now;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		index = getArguments() != null ? getArguments().getInt("index") : -1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.yirihui_item, null);
//		refreshListView = (PullToRefreshListView) view
//				.findViewById(R.id.pull_refresh_list);
		getYiriHuiData();
		return view;
	}
	
	/**获取伊日惠数据**/
	private void getYiriHuiData(){
		String url = new JNICallBack().HTTPURL;
		String param = new JNICallBack().getHttp4GetYiRiHui(type + "", offset + "", limit + "", sort);
		Log.e("system.out", url + "?" + param);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {
			
			@Override
			public void success(String content) {
				Log.e("system.out", content);
				content = "{\"code\":1,\"msg\":\"成功\",\"response\":[{\"the_id\":\"1\",\"rule_name\":\"伊日惠测试活动一\",\"begin_time\":\"2014-02-17 11:03:07\",\"end_time\":\"2014-02-18 00:00:00\",\"goods_id\":\"1590\",\"quantity\":\"1\",\"can_buy_quantity\":\"1\",\"overtime\":\"900\",\"img_1\":\"5/2014/02/17/a0acc98642b.jpg\",\"img_2\":\"8/2014/02/17/a0ac9bce227.jpg\",\"valid_flag\":\"y\",\"shell_flag\":\"y\",\"goods_name\":\"【马年活动】脱盐海泉精华\",\"goods_price\":\"50.00\"}],\"ts\":1392609753}";
				//TODO 解析数据显示数据
				ParseYiRiHui parseYiRiHui = new ParseYiRiHui();
				boolean isSuccess = parseYiRiHui.parseYiRiHui(content);
				if(isSuccess){
					ArrayList<YiRiHuiData> yiRiHuiDatas = parseYiRiHui.getYiRiHuiDatas();
					if(null != yiRiHuiDatas) {
						//TODO 显示数据
						
					}
				}
			}
		});
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
