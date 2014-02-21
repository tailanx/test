package com.yidejia.app.mall.msg;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.MsgCenter;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

/**
 * 前消息中心
 * 
 * @author Administrator
 * 
 */
public class MsgActivity extends BaseActivity {

	private int fromIndex = 0;
	private int amount = 10;
	private String userId;
	private String token;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private LinearLayout layout;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.person_message));
		setContentView(R.layout.message_center);
		layout = (LinearLayout) findViewById(R.id.message_scrollView_linearlayout1);
		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.message_scrollView);
		
		mPullToRefreshScrollView.setOnRefreshListener(listener);
		
		userId = MyApplication.getInstance().getUserId();
		token = MyApplication.getInstance().getToken();
		getMsg();
		
	}


	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			fromIndex += amount;
			getMsg();
		}

	};
	
	private void getMsg(){
		String url = new JNICallBack().getHttp4GetMessage(userId, token, fromIndex + "", amount + "");
		
		HttpClientUtil httpClientUtil = new HttpClientUtil(this);
		if(fromIndex == 0)httpClientUtil.setIsShowLoading(true);
		else httpClientUtil.setIsShowLoading(false);
		httpClientUtil.setPullToRefreshView(mPullToRefreshScrollView);
		httpClientUtil.getHttpResp(url, new IHttpResp() {
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				if(null == getResources()) return;
				ParseMsg parseMsg = new ParseMsg();
				boolean isSuccess = parseMsg.parseMsg(content);
				if (isSuccess) {
					ArrayList<MsgCenter> msgCenters = parseMsg.getMsgs();
					if (null != msgCenters && msgCenters.size() != 0) {
						loadView(msgCenters);
					} else {
						Toast.makeText(MsgActivity.this,
								getString(R.string.nomore), Toast.LENGTH_SHORT)
								.show();
					}
					
					String label = getResources().getString(
							R.string.update_time)
							+ DateUtils.formatDateTime(
									MsgActivity.this.getApplicationContext(),
									System.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
					if (null != mPullToRefreshScrollView)
						mPullToRefreshScrollView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
				} else {
					String errMsg = (null != parseMsg.getErrMsg() && !""
							.equals(parseMsg.getErrMsg())) ? parseMsg
							.getErrMsg() : getString(R.string.bad_network);
					Toast.makeText(MsgActivity.this, errMsg, Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		
		/*AsyncOkHttpClient client = new AsyncOkHttpClient();
		
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
				if(null != mPullToRefreshScrollView)
					mPullToRefreshScrollView.onRefreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				
				if(HttpStatus.SC_OK == statusCode){
					
					ParseMsg parseMsg = new ParseMsg();
					boolean isSuccess = parseMsg.parseMsg(content);
					if(isSuccess){
						ArrayList<MsgCenter> msgCenters = parseMsg.getMsgs();
						if(null != msgCenters && msgCenters.size() != 0) {
							loadView(msgCenters);
						} else {
							Toast.makeText(MsgActivity.this, getString(R.string.nomore), Toast.LENGTH_SHORT).show();
						}
						
						String label = getResources().getString(R.string.update_time)
								+ DateUtils.formatDateTime(
										MsgActivity.this.getApplicationContext(),
										System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						if(null != mPullToRefreshScrollView)
						mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
								label);
					} else {
						String errMsg = (null != parseMsg.getErrMsg() && !""
								.equals(parseMsg.getErrMsg())) ? parseMsg
								.getErrMsg() : getString(R.string.bad_network);
						Toast.makeText(MsgActivity.this, errMsg, Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				Toast.makeText(MsgActivity.this, getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			}
			
		});*/
	}
	
	public void loadView(ArrayList<MsgCenter> msgCenters) {
		for (int i = 0; i < msgCenters.size(); i++) {
			View view = getLayoutInflater().inflate(R.layout.message_item, null);
			TextView tvTitle = (TextView) view
					.findViewById(R.id.message_item_textview1);
			TextView tvTime = (TextView) view
					.findViewById(R.id.message_textview2);
			TextView tvContent = (TextView) view
					.findViewById(R.id.message_textview4);
			MsgCenter msg = msgCenters.get(i);
			tvTitle.setText(msg.getTitle());
			tvContent.setText(msg.getSubject());
			tvTime.setText(msg.getTime());
			final String id = msg.getMsgid();
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MsgActivity.this, MsgDetailsActivity.class);
					intent.putExtra("msgid", id);
					startActivity(intent);
				}
			});
			layout.addView(view);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "消息中心页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "消息中心页面");
	}
}
