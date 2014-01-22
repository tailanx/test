package com.yidejia.app.mall.order;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.RetViewCtrl;
import com.yidejia.app.mall.jni.JNICallBack;

/**
 * 退换货
 * @author LongBin
 *
 */
public class ExchangeActivity extends BaseActivity{

	private PullToRefreshScrollView mPullToRefreshScrollView;
	private LinearLayout allOrderLayout;//全部退换货的layout

	private int fromIndex = 0;
	private int amount = 10;
	private String userId;
	private String token;
	private boolean isRefresh = false;
//	private ArrayList<RetOrderInfo> retOrders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.exchanged));
		setContentView(R.layout.all_order_item_main);

		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.all_order_item_main_refresh_scrollview11);
		allOrderLayout = (LinearLayout) findViewById(R.id.all_order_item_main_scrollView_linearlayout1);

		userId = MyApplication.getInstance().getUserId();
		token = MyApplication.getInstance().getToken();
		
//		retOrders = new ArrayList<RetOrderInfo>();

		getData();
		
		mPullToRefreshScrollView.setOnRefreshListener(listener2);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private OnRefreshListener2<ScrollView> listener2 = new OnRefreshListener2<ScrollView>() {

		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ScrollView> refreshView) {
			fromIndex = 0;// 刷新的index要改为0
			getData();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			fromIndex += amount;
			getData();
		}
	};
	
	private void getData(){
		String url = new JNICallBack().getHttp4GetReturnList(userId, fromIndex + "", amount + "", token);
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				String label = getResources().getString(R.string.update_time)
						+ DateUtils.formatDateTime(ExchangeActivity.this,
								System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_SHOW_TIME);
				mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
						label);
				if (null != mPullToRefreshScrollView
						&& mPullToRefreshScrollView.isRefreshing()) {
					mPullToRefreshScrollView.onRefreshComplete();
				}
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode) {
					ParseReturnJson parseReturnJson = new ParseReturnJson();
					boolean isSuccess = parseReturnJson.parseReturn(content);
					ArrayList<RetOrderInfo> retOrdersTemp = parseReturnJson.getRetOrderInfos();
					if(isSuccess && null != retOrdersTemp){
						if(isRefresh) {
//							retOrders.clear();
							allOrderLayout.removeAllViews();
						}
//						retOrders.addAll(retOrdersTemp);
						int length = retOrdersTemp.size();
						for (int i = 0; i < length; i++) {
							add2view(retOrdersTemp.get(i));
						}
					} else if(isSuccess) {
						Toast.makeText(ExchangeActivity.this,
								getResources().getString(R.string.nomore),
								Toast.LENGTH_SHORT).show();
					}
					
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				fromIndex -= amount;
				if(fromIndex < 0) fromIndex = 0;
			}
			
		});
	}
	
	/**加载到界面**/
	private void add2view(final RetOrderInfo info){
		RetViewCtrl ctrl = new RetViewCtrl(this,info);
		View view = ctrl.getRetView();
		ctrl.setText(info.getStatus(), info.getOrderCode(), info.getTheDate());
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ExchangeActivity.this, ReturnActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", info);
				intent.putExtras(bundle);
				ExchangeActivity.this.startActivity(intent);
			}
		});
		allOrderLayout.addView(view);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "退换货页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "退换货页面");
	}
}
