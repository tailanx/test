package com.yidejia.app.mall.task;

import java.io.IOException;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.favorite.DeleteFavorite;
import com.yidejia.app.mall.widget.YLProgressDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
//		bar = new ProgressDialog(context);
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
	private boolean isTimeOut = false;

	private class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				String httpresp;
				try {
					httpresp = delFavorite.deleteFavorite(userid, goodsid,
							token);
					boolean issuccess = delFavorite.analysicDeleteJson(httpresp);
					return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeOut = true;
				}
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
//			bar.setCancelable(true);
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.loading));
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(context)
			.createLoadingDialog(context, null);
	bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			cancel(true);
		}
	});
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
			} else {
				if(isTimeOut) {
					Toast.makeText(context,
							context.getResources().getString(R.string.time_out),
							Toast.LENGTH_LONG).show();
					isTimeOut = false;
				}
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
