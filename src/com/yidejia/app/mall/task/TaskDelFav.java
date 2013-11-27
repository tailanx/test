package com.yidejia.app.mall.task;

import java.io.IOException;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.favorite.DeleteFavorite;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class TaskDelFav {

	private String userid;
	private String goodsid;
	private String token;

	private Context context;
	private PullToRefreshListView mPullToRefreshListView;
	private ProgressDialog bar;

	public TaskDelFav(Context context) {
		delFavorite = new DeleteFavorite();
		this.context = context;
		bar = new ProgressDialog(context);
	}

	public TaskDelFav(Context context,
			PullToRefreshListView mPullToRefreshListView) {
		this(context);
		this.mPullToRefreshListView = mPullToRefreshListView;
	}

	public void delFav(String userid, String goodsid, String token) {
		this.userid = userid;
		this.goodsid = goodsid;
		this.token = token;

		closeTask();
		task = new Task();
		task.execute();
	}

	private DeleteFavorite delFavorite;
	private Task task;

	private class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp = delFavorite.deleteFavorite(userid, goodsid,
						token);
				boolean issuccess = delFavorite.analysicDeleteJson(httpresp);
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
			bar.setCancelable(true);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage(context.getResources().getString(R.string.loading));
			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if (result) {
				if (mPullToRefreshListView != null) {
					mPullToRefreshListView.setRefreshing();
					mPullToRefreshListView.onRefreshComplete();
				}
				Toast.makeText(context,
						context.getResources().getString(R.string.del_fav_ok),
						Toast.LENGTH_LONG).show();
			}
		}

	}

	public void closeTask() {
		if (task != null
				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
	}
}
