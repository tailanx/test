package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.IntegerAdapter;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.tickets.ParseTickets;
import com.yidejia.app.mall.tickets.Ticket;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class YouhuiActivity extends BaseActivity implements OnClickListener {
	private TextView back;// 返回
	private ListView listview;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.youhuiquan);
		listview = (ListView) findViewById(R.id.youhuiquan_listview);
		setActionbarConfig();
		setTitle(getString(R.string.youhuiquan_zekou));
		back = (TextView) findViewById(R.id.ab_common_back);
		back.setOnClickListener(this);
		getTicket();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ab_common_back:// 返回
			this.finish();
			break;
		}
	}
	/**
	 * 获取优惠券
	 */
	private void getTicket(){
		String url = new JNICallBack().getHttp4GetTicket(MyApplication.getInstance().getUserId(), MyApplication.getInstance().getToken());
		HttpClientUtil client = new HttpClientUtil();
		client.getHttpResp(url, new IHttpResp() {
			
			@Override
			public void success(String content) {
				ParseTickets parseTickets = new ParseTickets();
				boolean isSuccess = parseTickets.parseTickets(content);
				if(isSuccess){
					ArrayList<Ticket> tickets = parseTickets.getTickets();
					IntegerAdapter adapter = new IntegerAdapter(YouhuiActivity.this, tickets,"Youhuiquan");
					listview.setAdapter(adapter);
				}
			}
		});
	}
}
