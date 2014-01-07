package com.yidejia.app.mall.task;

import java.io.IOException;
import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.broadcast.MallAction;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.homepage.GetHomePage;
import com.yidejia.app.mall.widget.BannerView;
import com.yidejia.app.mall.widget.YLProgressDialog;

/**
 * 异步任务用来获取首页面的数据
 * 
 * @author Administrator
 * 
 */
public class MallTask {
	private Task task;
	private boolean IsFristin = true;
	private ProgressDialog bar;
	private SherlockFragmentActivity context;
	private ArrayList<BaseProduct> bannerArray = new ArrayList<BaseProduct>();
	private ArrayList<MainProduct> acymerArray = new ArrayList<MainProduct>();
	private ArrayList<MainProduct> inerbtyArray = new ArrayList<MainProduct>();
	private ArrayList<MainProduct> hotsellArray = new ArrayList<MainProduct>();
	private ArrayList<String> ggTitleArray = new ArrayList<String>();
	private boolean isTimeOut = false;// 链接超时
	private FrameLayout layout;
	private ViewGroup viewGroup;
	private View view;
	private TextView title;// 商城的公告
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private FrameLayout layoutView;
	private BannerView mallSlip;

	public MallTask(SherlockFragmentActivity context, View view,
			FrameLayout layout, TextView title,
			PullToRefreshScrollView mPullToRefreshScrollView) {
		this.context = context;
		this.title = title;
		this.layout = layout;
		this.view = view;
		layoutView = (FrameLayout) view.findViewById(R.id.layout);
		this.mPullToRefreshScrollView = mPullToRefreshScrollView;
		
		closeTask();
		task = new Task();
		task.execute();
		
	}

	public MallTask() {

	}

	public Task getTask() {
		return task;
	}

	public ArrayList<MainProduct> getInerbtyArray() {
		return inerbtyArray;
	}

	public ArrayList<MainProduct> getAcymerArray() {
		return acymerArray;
	}

	public ArrayList<MainProduct> getHotsellArray() {
		return hotsellArray;
	}

	public class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			bar.dismiss();
			GetHomePage homePage = new GetHomePage();
			String httpString;
			try {
				httpString = homePage.getHomePageJsonString();
				boolean isSucess = homePage.analysisGetHomeJson(httpString);
				bannerArray = homePage.getBannerArray();
				inerbtyArray = homePage.getInerbtyArray();
				hotsellArray = homePage.getHotSellArray();
				acymerArray = homePage.getAcymerArray();
				ggTitleArray = homePage.getGGTitle();
				return isSucess;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeOutEx e) {
				e.printStackTrace();
				isTimeOut = true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				layoutView.removeAllViews();
				
				mallSlip = new BannerView(bannerArray, context);
				viewGroup = mallSlip.getMainListFirstItem();
				layoutView.addView(viewGroup);
				
				
//				HotSellView hotSellView = new HotSellView(view, context);
//				hotSellView.initHotSellView(hotsellArray);
//				hotSellView.initAcymerView(acymerArray);
//				hotSellView.initInerbtyView(inerbtyArray);
//				MallLoadData mallLoadData = new MallLoadData(context, view,acymerArray,inerbtyArray,hotsellArray);// 进行各种界面的跳转和界面的数据加载
//				mallLoadData.intentToView(view);
				title.setText(ggTitleArray.get(0));
				
//					timer.schedule(timetask, DELAY, DELAY);
				mallSlip.startTimer();
			} else {
				if (isTimeOut) {
					Toast.makeText(
							context,
							context.getResources().getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeOut = false;

				} else
					Toast.makeText(
							context,
							context.getResources().getString(
									R.string.bad_network), Toast.LENGTH_SHORT)
							.show();
			}
			if (MallAction.isFirstIn) {
				bar.dismiss();
				MallAction.isFirstIn = false;
			} else {

				mPullToRefreshScrollView.onRefreshComplete();
				String label = context.getResources().getString(
						R.string.update_time)
						+ DateUtils.formatDateTime(
								context.getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
				mPullToRefreshScrollView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);
			}
		}

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (IsFristin) {
				// 1.创建bar
				bar = new YLProgressDialog(context).createLoadingDialog(
						context, null);
				// 2.bar上的点击事件
				bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						closeTask();
						IsFristin = false;
						layout.removeAllViews();
					}
				});
				// 3.清空数据
				if (!bannerArray.isEmpty())
					bannerArray.clear();
				if (!acymerArray.isEmpty())
					acymerArray.clear();
				if (!inerbtyArray.isEmpty())
					inerbtyArray.clear();
				if (!hotsellArray.isEmpty())
					hotsellArray.clear();
			}
		}
	}

	/**
	 * 关闭异步任务
	 */
	@SuppressWarnings("static-access")
	public void closeTask() {
		if (task != null
				&& task.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
	}
	
	public void onStart() {
		mallSlip.startTimer();
	}

	public void onPause() {
		closeTask();
		mallSlip.stopTimer();
	}

}
