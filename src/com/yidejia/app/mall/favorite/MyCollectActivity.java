package com.yidejia.app.mall.favorite;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.R.string;
import com.yidejia.app.mall.adapter.FavoriteAdapter;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.search.SearchResultActivity;

/**
 * 我的收藏
 * @author LongBin
 *
 */
public class MyCollectActivity extends BaseActivity {
	private FavoriteAdapter fAdapter;
	private ListView mListView;
	private PullToRefreshListView mPullRefreshListView;
	private RelativeLayout emptyLayout;
	private int fromIndex = 0;
	private int amount = 10;
	private String userId;
	private String token;

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
				final View delView = view;
				final String pid = favList.get(position - 1).getUId();
				new Builder(MyCollectActivity.this)
						.setTitle(R.string.tips)
						.setMessage("确定要删除收藏？")
						.setPositiveButton(R.string.sure,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										delFav(pid, mListView, delView);
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
		setContentView(R.layout.my_collect);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.my_collect_listview);
		emptyLayout = (RelativeLayout) findViewById(R.id.empty_fav);
		Button search = (Button) findViewById(R.id.favorite_empty_button);
		setActionbarConfig();
		setTitle(getResources().getString(R.string.my_collect));
		
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
//		closeTask();
//		task = new Task();
//		task.execute();
		
		userId = MyApplication.getInstance().getUserId();
		token = MyApplication.getInstance().getToken();
		
		getCollectsData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(null != favList) {
			favList.clear();
			favList = null;
		}
		if(null != fAdapter) {
			fAdapter = null;
		}
//		closeTask();
	}

	private boolean isDownRefresh = false;

	private OnRefreshListener2<ListView> listener2 = new OnRefreshListener2<ListView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex = 0;
			isDownRefresh = true;
//			closeTask();
//			task = new Task();
//			task.execute();
			getCollectsData();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex += amount;
			isDownRefresh = false;
//			closeTask();
//			task = new Task();
//			task.execute();
			getCollectsData();
		}

	};
	
	private void getCollectsData() {
		String url = new JNICallBack().getHttp4GetFav("userid=" + userId, fromIndex + "",
				+amount + "", "", "created+desc", "%2A");
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				super.onFinish();
				if(isFirstIn) {
					// TODO 
					
				} else if(null != mPullRefreshListView) {
					mPullRefreshListView.onRefreshComplete();
				}
				isFirstIn = false;
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(statusCode == HttpStatus.SC_OK) {
					ParseFavJson parseFavJson = new ParseFavJson();
					boolean isSuccess = parseFavJson.parseGetListJson(content);
					if(isSuccess) {
						ArrayList<SearchItem> tempList = parseFavJson.getFavList();
						if(null != tempList){
							if(isDownRefresh) {
								favList.clear();
							}
							favList.addAll(tempList);
							fAdapter.notifyDataSetChanged();
						} else {
							Toast.makeText(MyCollectActivity.this,
									getResources().getString(R.string.nomore),
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				fromIndex -= amount;
				if(fromIndex < 0) {
					fromIndex  = 0;
				}
				Toast.makeText(MyCollectActivity.this,
						getResources().getString(R.string.bad_network),
						Toast.LENGTH_SHORT).show();
			}
			
		});
	}

	private String TAG = MyCollectActivity.class.getName();

	private boolean isFirstIn = true;
//	private boolean isNoMore = false;
	private ArrayList<SearchItem> favList;
//	private ArrayList<SearchItem> mList;
//	private ProgressDialog bar;
//	private Task task;
//	private GetFavoriteList getFavoriteList = new GetFavoriteList();

	/*private class Task extends AsyncTask<Void, Void, Boolean> {

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
	}*/

//	private TaskDelFav taskDelFav;

	/**删除收藏**/
	private void delFav(String productId, final ListView parent, final View view) {
//		taskDelFav = new TaskDelFav(MyCollectActivity.this,
//				mPullRefreshListView);
//		taskDelFav.delFav(((MyApplication) getApplication()).getUserId(),
//				productId, ((MyApplication) getApplication()).getToken());
		
		String url = new JNICallBack().HTTPURL;
		String param = new JNICallBack().getHttp4DelFav(userId, productId, token);
		
		RequestParams requestParams = new RequestParams();
		requestParams.put(param);
		String contentType = "application/x-www-form-urlencoded;charset=UTF-8";
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		
		client.post(url, contentType, requestParams, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode) {
					ParseFavJson parseFavJson = new ParseFavJson();
					boolean issuccess = parseFavJson.parseDeleteJson(content);
					if (issuccess) {
						Toast.makeText(MyCollectActivity.this,
								getResources().getString(R.string.del_fav_ok),
								Toast.LENGTH_SHORT).show();
						if(null != mPullRefreshListView) {
							fromIndex = 0;
							mPullRefreshListView.setRefreshing();
						}
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
			}
			
		});
	}
	
}
