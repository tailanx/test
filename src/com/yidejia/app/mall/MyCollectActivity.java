package com.yidejia.app.mall;

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
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.FavoriteAdapter;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.net.favorite.GetFavoriteList;
import com.yidejia.app.mall.search.SearchResultActivity;
import com.yidejia.app.mall.task.TaskDelFav;
import com.yidejia.app.mall.widget.YLProgressDialog;

/**
 * 我的收藏
 * @author LongBin
 *
 */
public class MyCollectActivity extends SherlockActivity {
	private FavoriteAdapter fAdapter;
	private ListView mListView;
	private PullToRefreshListView mPullRefreshListView;
	private RelativeLayout emptyLayout;
	private int fromIndex = 0;
	private int amount = 10;

	private void setupShow() {

		Log.e(TAG, "show setup");
		mListView = mPullRefreshListView.getRefreshableView();
		fAdapter = new FavoriteAdapter(this, favList);
		mListView.setAdapter(fAdapter);

		mPullRefreshListView.setOnRefreshListener(listener2);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String pid = favList.get(position - 1).getUId();
				Intent intent = new Intent(MyCollectActivity.this,
						GoodsInfoActivity.class);
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
				final String pid = favList.get(position - 1).getUId();
				new Builder(MyCollectActivity.this)
						.setTitle(R.string.tips)
						.setMessage("确定要删除收藏？")
						.setPositiveButton(R.string.sure,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										delFav(pid);
									}
								})
						.setNegativeButton(R.string.searchCancel, null)
						.create().show();
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
				Intent intent = new Intent(MyCollectActivity.this,
						SearchResultActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", "全部");
				bundle.putString("name", "");
				bundle.putString("price", "");
				bundle.putString("brand", "");
				bundle.putString("fun", "");
				intent.putExtras(bundle);
				MyCollectActivity.this.startActivity(intent);
			}
		});
		favList = new ArrayList<SearchItem>();
		setupShow();
		closeTask();
		task = new Task();
		task.execute();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeTask();
	}

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_common);

		TextView button = (TextView) findViewById(R.id.ab_common_back);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				MyCollectActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
		titleTextView.setText("我的收藏");
	}

	private boolean isDownRefresh = false;

	private OnRefreshListener2<ListView> listener2 = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex = 0;
			isDownRefresh = true;
			closeTask();
			task = new Task();
			task.execute();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex += amount;
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
			try {
				String httpresp;
				try {
					httpresp = getFavoriteList.getHttpResp(
							((MyApplication) getApplication()).getUserId(),
							fromIndex + "", amount + "");
					boolean issuccess = getFavoriteList
							.analysisGetListJson(httpresp);
					isNoMore = getFavoriteList.getIsNoMore();
					mList = getFavoriteList.getFavList();
					return issuccess;
				} catch (TimeOutEx e) {
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return false;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (isFirstIn) {
				bar = (ProgressDialog) new YLProgressDialog(
						MyCollectActivity.this).createLoadingDialog(
						MyCollectActivity.this, null);
				bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						cancel(true);
					}
				});
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				if (mList != null && !mList.isEmpty()) {
					mPullRefreshListView.setVisibility(ViewGroup.VISIBLE);
					emptyLayout.setVisibility(ViewGroup.GONE);
					String label = getResources().getString(
							R.string.update_time)
							+ DateUtils.formatDateTime(MyCollectActivity.this,
									System.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
					mPullRefreshListView.getLoadingLayoutProxy()
							.setLastUpdatedLabel(label);

					if (isDownRefresh) {
						favList.clear();
						isDownRefresh = false;
					}
					favList.addAll(mList);
					fAdapter.notifyDataSetChanged();
				} else {
					if (favList != null && !favList.isEmpty() && isNoMore) {
						Toast.makeText(MyCollectActivity.this,
								getResources().getString(R.string.nomore),
								Toast.LENGTH_LONG).show();
						if (!isFirstIn)
							mPullRefreshListView.onRefreshComplete();
						else
							bar.dismiss();
						return;
					}
					mPullRefreshListView.setVisibility(ViewGroup.GONE);
					emptyLayout.setVisibility(ViewGroup.VISIBLE);
				}
			} else {
				if (fromIndex != 0)
					fromIndex -= amount;
				if (isTimeout) {
					Toast.makeText(
							MyCollectActivity.this,
							MyCollectActivity.this.getResources().getString(
									R.string.time_out), Toast.LENGTH_SHORT)
							.show();
					isTimeout = false;

				}
			}
			if (isFirstIn) {
				bar.dismiss();
				isFirstIn = false;
			} else
				mPullRefreshListView.onRefreshComplete();
		}

	}

	private boolean isTimeout = false;

	private void closeTask() {
		if (task != null
				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
		if (taskDelFav != null) {
			taskDelFav.closeTask();
		}
	}

	private TaskDelFav taskDelFav;

	private void delFav(String productId) {
		taskDelFav = new TaskDelFav(MyCollectActivity.this,
				mPullRefreshListView);
		taskDelFav.delFav(((MyApplication) getApplication()).getUserId(),
				productId, ((MyApplication) getApplication()).getToken());
	}
}
