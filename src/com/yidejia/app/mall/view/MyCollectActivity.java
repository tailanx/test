package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.FavoriteAdapter;
import com.yidejia.app.mall.datamanage.FavoriteDataManage;
import com.yidejia.app.mall.model.SearchItem;

public class MyCollectActivity extends SherlockActivity {
	private FavoriteAdapter fAdapter;
	private FavoriteDataManage dataManage;
	private ListView mListView;
	private ArrayList<SearchItem> mList;
//	private View mycollectView;
//	private LinearLayout layout;
//	private ImageView headImage;//头像
//	private TextView content;//内容
//	private TextView price;//价格
//	private TextView sellCount;//卖出的总数
//	private TextView compment;//评论
	private void setupShow(){
		mListView = (ListView) findViewById(R.id.my_collect_listview);
		fAdapter = new FavoriteAdapter(this, mList);
		mListView.setAdapter(fAdapter);
//		layout = (LinearLayout) findViewById(R.id.wmy_collect_scrollView_linearlayout1);
//		mycollectView = getLayoutInflater().inflate(R.layout.my_collect_item, null);
//		headImage = (ImageView) mycollectView.findViewById(R.id.my_collect_item__imageview1);
//		content = (TextView)mycollectView.findViewById(R.id.my_collect_item_text);
//		price = (TextView)mycollectView.findViewById(R.id.my_collect_item_money);
//		sellCount = (TextView)mycollectView.findViewById(R.id.my_collect_item_sum1);
//		compment = (TextView)mycollectView.findViewById(R.id.my_collect_item_sum2);
	
	
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();
		dataManage = new FavoriteDataManage(this);
		mList = dataManage.getFavouriteArray(((MyApplication)getApplication()).getUserId()
, 0, 10);
//		Log.i("info", mList.size()+"");
		if(mList.size()<=0){
			setContentView(R.layout.favorite_empty);
		}else{
		setContentView(R.layout.my_collect);
		setupShow();
		}
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);

		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				MyCollectActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("我的收藏");
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("详情");
		menu.add("删除");
		menu.add("取消");
	}
}
