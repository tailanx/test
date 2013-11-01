package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.unionpay.mpay.utils.m;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.FavoriteAdapter;
import com.yidejia.app.mall.datamanage.FavoriteDataManage;
import com.yidejia.app.mall.model.SearchItem;

public class MyCollectActivity extends SherlockActivity {
	private FavoriteAdapter fAdapter;
	private FavoriteDataManage dataManage;
	private ListView mListView;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayList<SearchItem> mList;
	private static int index;//
	private int fromIndex = 0;
	private int amount = 10;
//	private View mycollectView;
//	private LinearLayout layout;
//	private ImageView headImage;//头像
//	private TextView content;//内容
//	private TextView price;//价格
//	private TextView sellCount;//卖出的总数
//	private TextView compment;//评论
	private void setupShow(){
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.my_collect_listview);
		String label = getResources().getString(R.string.update_time)
				+ DateUtils.formatDateTime(this,
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		mListView = mPullRefreshListView.getRefreshableView();
		fAdapter = new FavoriteAdapter(this, mList);
		mListView.setAdapter(fAdapter);
//		mPullRefreshListView.setOnRefreshListener2(listener2);
		mPullRefreshListView.setOnRefreshListener(listener2);
//		layout = (LinearLayout) findViewById(R.id.wmy_collect_scrollView_linearlayout1);
//		mycollectView = getLayoutInflater().inflate(R.layout.my_collect_item, null);
//		headImage = (ImageView) mycollectView.findViewById(R.id.my_collect_item__imageview1);
//		content = (TextView)mycollectView.findViewById(R.id.my_collect_item_text);
//		price = (TextView)mycollectView.findViewById(R.id.my_collect_item_money);
//		sellCount = (TextView)mycollectView.findViewById(R.id.my_collect_item_sum1);
//		compment = (TextView)mycollectView.findViewById(R.id.my_collect_item_sum2);
	
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String pid = mList.get(position).getUId();
				Intent intent = new Intent(MyCollectActivity.this, GoodsInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("goodsId", pid);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//未完成的删掉收藏
				index = position;
				final String pid = mList.get(position).getUId();
				new Builder(MyCollectActivity.this).setTitle(R.string.tips)
					.setMessage("确定要删除收藏？").setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if(delFav(pid)){
//								updateListView(index);
								mPullRefreshListView.setRefreshing();
								mPullRefreshListView.onRefreshComplete();
							}
						}
					}).setNegativeButton(R.string.searchCancel, null).create().show();
				return true;
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionbar();
		dataManage = new FavoriteDataManage(this);
		mList = dataManage.getFavouriteArray(
				((MyApplication) getApplication()).getUserId(), fromIndex, amount);
		// Log.i("info", mList.size()+"");
		if (mList.size() <= 0) {
			setContentView(R.layout.favorite_empty);
		} else {
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
	
	private OnRefreshListener2<ListView> listener2 = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			String label = MyCollectActivity.this.getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(MyCollectActivity.this,
							System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			FavoriteDataManage manage = new FavoriteDataManage(MyCollectActivity.this);
			fromIndex = 0;
			ArrayList<SearchItem> favList = manage.getFavouriteArray(((MyApplication)getApplication()).getUserId(), fromIndex, amount);
			mPullRefreshListView.onRefreshComplete();
			if(favList.isEmpty()) {
				if(mList.isEmpty())
					Toast.makeText(MyCollectActivity.this, "亲，你还没收藏任何商品哦！", Toast.LENGTH_LONG).show();
				else {
					Toast.makeText(MyCollectActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
				}
			}
			else{
//				refreshView.removeAllViews();
				mList.clear();
				mList.addAll(favList);
				fAdapter.notifyDataSetChanged();
			}
			
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			String label = MyCollectActivity.this.getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(MyCollectActivity.this,
							System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			FavoriteDataManage manage = new FavoriteDataManage(MyCollectActivity.this);
			fromIndex += amount;
			ArrayList<SearchItem> favList = manage.getFavouriteArray(((MyApplication)getApplication()).getUserId(), fromIndex, amount);
			mPullRefreshListView.onRefreshComplete();
			if(favList.isEmpty() && fromIndex != 0) {
				
				fromIndex -= amount;
				if(mList.isEmpty()){
					Toast.makeText(MyCollectActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
				}
			}
			else{
				mList.addAll(favList);
				fAdapter.notifyDataSetChanged();
			}
			
		}
		
	};
	
	private OnRefreshListener<ListView> listener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
			String label = MyCollectActivity.this.getResources().getString(R.string.update_time)
					+ DateUtils.formatDateTime(MyCollectActivity.this,
							System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);
			mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			FavoriteDataManage manage = new FavoriteDataManage(MyCollectActivity.this);
			fromIndex += amount;
			ArrayList<SearchItem> favList = manage.getFavouriteArray(((MyApplication)getApplication()).getUserId(), fromIndex, amount);
			if(favList.isEmpty() && fromIndex != 0) fromIndex -= amount;
			else{
				mList.addAll(favList);
				fAdapter.notifyDataSetChanged();
			}
			mPullRefreshListView.onRefreshComplete();
		}
	};
	
	
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		super.onCreateContextMenu(menu, v, menuInfo);
//		menu.add("详情");
//		menu.add("删除");
//		menu.add("取消");
//	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
//		mList = dataManage.getFavouriteArray(
//				((MyApplication) getApplication()).getUserId(), fromIndex, amount);
//		if(mList.size() > 0)
//			updateListView(index);
		
	}
	//更新listview
	private void updateListView(int index){
		int visiblePosition = mListView.getFirstVisiblePosition();  
		View v = mListView.getChildAt(index - visiblePosition);
		mList.remove(index);
		mListView.removeView(v);
	}
	
	private boolean delFav(String productId){
		FavoriteDataManage manage = new FavoriteDataManage(MyCollectActivity.this);
		return manage.deleteFavourite(((MyApplication)getApplication()).getUserId(), productId, ((MyApplication)getApplication()).getToken());
	}
}
