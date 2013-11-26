package com.yidejia.app.mall.view;

import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.SearchActivity;
import com.yidejia.app.mall.adapter.FavoriteAdapter;
import com.yidejia.app.mall.datamanage.FavoriteDataManage;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.favorite.GetFavoriteList;
import com.yidejia.app.mall.task.TaskDelFav;

public class MyCollectActivity extends SherlockActivity {
	private FavoriteAdapter fAdapter;
	private ListView mListView;
	private PullToRefreshListView mPullRefreshListView;
	private RelativeLayout emptyLayout;
	private static int index;//
	private int fromIndex = 0;
	private int amount = 10;
	
	private void setupShow(){
		
		Log.e(TAG, "show setup");
		mListView = mPullRefreshListView.getRefreshableView();
		fAdapter = new FavoriteAdapter(this, favList);
		mListView.setAdapter(fAdapter);
		
		mPullRefreshListView.setOnRefreshListener(listener2);

	
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String pid = favList.get(position - 1).getUId();
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
				Log.i("MyCollectAct", "m" + favList.size());
				final String pid = favList.get(position - 1).getUId();
				Log.e("MyCollectACT", "pid" + pid + "position:" + position);
				new Builder(MyCollectActivity.this).setTitle(R.string.tips)
					.setMessage("确定要删除收藏？").setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							delFav(pid);
//								updateListView(index);
//								mPullRefreshListView.setRefreshing();
//								mPullRefreshListView.onRefreshComplete();
							
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
		setContentView(R.layout.my_collect);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.my_collect_listview);
		emptyLayout = (RelativeLayout) findViewById(R.id.empty_fav);
		Button search = (Button) findViewById(R.id.favorite_empty_button);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentOrder = new Intent(MyCollectActivity.this, SearchActivity.class);
				MyCollectActivity.this.startActivity(intentOrder);
			}
		});
		favList = new ArrayList<SearchItem>();
		setupShow();
		/*dataManage = new FavoriteDataManage(this);
		mList = dataManage.getFavouriteArray(
				((MyApplication) getApplication()).getUserId(), fromIndex, amount);
		// Log.i("info", mList.size()+"");
		if (mList.size() <= 0) {
			setContentView(R.layout.favorite_empty);
			Button search = (Button) findViewById(R.id.favorite_empty_button);
			search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intentOrder = new Intent(MyCollectActivity.this, SearchActivity.class);
					MyCollectActivity.this.startActivity(intentOrder);
				}
			});
		} else {
			setContentView(R.layout.my_collect);
			setupShow();
		}*/
		closeTask();
		task = new Task();
		task.execute();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeTask();
	}

	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
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
	
	private boolean isDownRefresh = false;
	
	private OnRefreshListener2<ListView> listener2 = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
//			String label = MyCollectActivity.this.getResources().getString(R.string.update_time)
//					+ DateUtils.formatDateTime(MyCollectActivity.this,
//							System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
//							| DateUtils.FORMAT_SHOW_DATE
//							| DateUtils.FORMAT_ABBREV_ALL);
//			mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//			FavoriteDataManage manage = new FavoriteDataManage(MyCollectActivity.this);
			fromIndex = 0;
//			ArrayList<SearchItem> favList = manage.getFavouriteArray(((MyApplication)getApplication()).getUserId(), fromIndex, amount);
//			mPullRefreshListView.onRefreshComplete();
//			if(favList.isEmpty()) {
//				if(mList.isEmpty())
//					Toast.makeText(MyCollectActivity.this, "亲，你还没收藏任何商品哦！", Toast.LENGTH_LONG).show();
//				else {
//					Toast.makeText(MyCollectActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
//				}
//			}
//			else{
////				refreshView.removeAllViews();
//				mList.clear();
//				mList.addAll(favList);
//				fAdapter.notifyDataSetChanged();
//			}
//			favList.clear();
			isDownRefresh = true;
			closeTask();
			task = new Task();
			task.execute();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// TODO Auto-generated method stub
//			String label = MyCollectActivity.this.getResources().getString(R.string.update_time)
//					+ DateUtils.formatDateTime(MyCollectActivity.this,
//							System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
//							| DateUtils.FORMAT_SHOW_DATE
//							| DateUtils.FORMAT_ABBREV_ALL);
//			mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//			FavoriteDataManage manage = new FavoriteDataManage(MyCollectActivity.this);
			fromIndex += amount;
//			ArrayList<SearchItem> favList = manage.getFavouriteArray(((MyApplication)getApplication()).getUserId(), fromIndex, amount);
//			mPullRefreshListView.onRefreshComplete();
//			if(favList.isEmpty() && fromIndex != 0) {
//				
//				fromIndex -= amount;
//				if(mList.isEmpty()){
//					Toast.makeText(MyCollectActivity.this, getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
//				}
//			}
//			else{
//				mList.addAll(favList);
//				fAdapter.notifyDataSetChanged();
//			}
			closeTask();
			task = new Task();
			task.execute();
		}
		
	};
	
	private String TAG = MyCollectActivity.class.getName();
	
	private boolean isFirstIn = true;
	private boolean isNoMore = false;
	private ArrayList<SearchItem> favList;
	private ArrayList<SearchItem> mList;
	private ProgressDialog bar;
	private Task task;
	private GetFavoriteList getFavoriteList = new GetFavoriteList();

	private class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp = getFavoriteList.getHttpResp(((MyApplication) getApplication()).getUserId(), fromIndex + "", amount + "");
				boolean issuccess = getFavoriteList.analysisGetListJson(httpresp);
				isNoMore = getFavoriteList.getIsNoMore();
//				if(mList != null && !mList.isEmpty())mList.clear();
				mList = getFavoriteList.getFavList();
				return issuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (isFirstIn) {
				bar = new ProgressDialog(MyCollectActivity.this);
				bar.setCancelable(false);
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				bar.show();
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				if(mList != null && !mList.isEmpty()){
					mPullRefreshListView.setVisibility(ViewGroup.VISIBLE);
					emptyLayout.setVisibility(ViewGroup.GONE);
					String label = getResources().getString(R.string.update_time)
							+ DateUtils.formatDateTime(MyCollectActivity.this,
									System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);
					mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					
//					if(!isFirstIn) {
					if(isDownRefresh) {
						favList.clear();
						isDownRefresh = false;
					}
						favList.addAll(mList);
						fAdapter.notifyDataSetChanged();
//						mListView.setAdapter(fAdapter);
//					}
//					else {
//						favList.addAll(mList);
//						setupShow();
//					}
				} else {
					if (favList != null && !favList.isEmpty() && isNoMore) {
						Toast.makeText(MyCollectActivity.this,
								getResources().getString(R.string.nomore),
								Toast.LENGTH_LONG).show();
						if(!isFirstIn) mPullRefreshListView.onRefreshComplete();
						else bar.dismiss();
						return;
					} 
					mPullRefreshListView.setVisibility(ViewGroup.GONE);
					emptyLayout.setVisibility(ViewGroup.VISIBLE);
				}
			} else {
				if(fromIndex != 0)fromIndex -= amount;
			}
			if(isFirstIn){
				bar.dismiss();
				isFirstIn = false;
//				setupShow();
			} else 
				mPullRefreshListView.onRefreshComplete();
		}
		
	}
	
	private void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		if(taskDelFav != null){
			taskDelFav.closeTask();
		}
	}
	
	private TaskDelFav taskDelFav;
	
	private void delFav(String productId){
//		FavoriteDataManage manage = new FavoriteDataManage(MyCollectActivity.this);
//		return manage.deleteFavourite(((MyApplication)getApplication()).getUserId(), productId, ((MyApplication)getApplication()).getToken());
		taskDelFav = new TaskDelFav(MyCollectActivity.this, mPullRefreshListView);
		taskDelFav.delFav(((MyApplication)getApplication()).getUserId(), productId, ((MyApplication)getApplication()).getToken());
	}
}
