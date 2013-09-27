package com.yidejia.app.mall.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;
//import com.test.R;
//import com.yidejia.app.mall.adapter.MyAdapter;
//import com.yidejia.app.mall.dal.ProduceDao;
//import com.yidejia.app.mall.entity.Produce;

public class PersonActivity extends SherlockActivity {
//	private Button go_back;
//	private ArrayList<Produce> mArrayList;
//	MyAdapter myAdapter;
//	 ListView mListView;
	public void setupShow(){
		//实例化控件
//		go_back = (Button)findViewById(R.id.main1_button);
//		mArrayList = (ArrayList<Produce>) ProduceDao.produceList;
//		for(int i=0;i<mArrayList.size();i++){
//			Produce p= mArrayList.get(i);
//			Log.i("info", p.toString());
//		}
	
//		mListView = (ListView) findViewById(R.id.lv_group_list_list);
//		mListView.setAdapter(myAdapter);
		Log.i("info", "你好");
	}
//	//添加响应事件
//	public void doClick(View v){
//		Intent intent = new Intent(this,MyMallActivity.class);
//		switch (v.getId()) {
//		case R.id.main1_button://点解返回按钮事件响应
//			startActivity(intent);
//			//结束当前Activity；
//			this.finish();
//			break;
//
//			
//			}
//		}
//	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		 TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
//		myAdapter = new MyAdapter(this,ProduceDao.produceList);
		setContentView(R.layout.message_center);
		LinearLayout layout = (LinearLayout) findViewById(R.id.message_scrollView_linearlayout1);
		
		View person = getLayoutInflater().inflate(R.layout.message_item, null);
//		TextView textView = (TextView) person.findViewById(R.id.person_textview1);
//		TextView textView1 = (TextView) person.findViewById(R.id.person_textview3);
//		TextView textView2 = (TextView) person.findViewById(R.id.person_textview4);
//		textView.setText("affas");
//		textView1.setText("aafas");
//		textView2.setText("bcxb");
		layout.addView(person);
		setupShow();
		
//		Log.i("info", "你好");
	}
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
				PersonActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("消息中心");
	}
}
