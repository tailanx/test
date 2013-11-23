package com.yidejia.app.mall.util;

import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.commments.GetProductCommentList;

public class CommentUtil {
	private Context context;
	private LinearLayout linearLayout;
	private View view;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private RelativeLayout refresh_view;


	/**
	 *   
	 */
	public CommentUtil() {

	}

	/**
	 * 
	 * @param context
	 */
	public CommentUtil(Context context, LinearLayout linearLayout) {// ,View
		this.linearLayout = linearLayout;
		this.context = context;
		// this.view = view;
		// this.dataManage = dataManage;
		getCommentList = new GetProductCommentList();
		bar = new ProgressDialog(context);
		bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}
	
	public void setRefreshView(PullToRefreshScrollView mPullToRefreshScrollView, RelativeLayout refresh_view){
		this.mPullToRefreshScrollView = mPullToRefreshScrollView;
		this.refresh_view = refresh_view;
	}
	
	private String goodsId;
	private int fromIndex;
	private int amount ;
	private boolean isNoMore = false;
	private boolean isHasRet = false;
	private ArrayList<UserComment> comments = new ArrayList<UserComment>();
	private Task task;
	private boolean isFirstIn = true;//第一次进入
	private boolean issuccess = false;
	private GetProductCommentList getCommentList;

	/**
	 * ???????????
	 */

	public void AllComment(String goodsId, int fromIndex, int amount) {
		this.goodsId = goodsId;
		this.fromIndex = fromIndex;
		this.amount = amount;
		if (ConnectionDetector.isConnectingToInternet(context)) {
			if("".equals(goodsId) || goodsId == null) {
				Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
//				return;
				refresh_view.setVisibility(view.VISIBLE);
				mPullToRefreshScrollView.setVisibility(view.GONE);
			} else {
				closeTask();
				task = new Task();
				task.execute();
			}
		} else {
			refresh_view.setVisibility(view.VISIBLE);
			mPullToRefreshScrollView.setVisibility(view.GONE);
		}
		bar.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
//					isFirstIn = false;
				if(!isHasRet){
					refresh_view.setVisibility(view.VISIBLE);
					mPullToRefreshScrollView.setVisibility(view.GONE);
				}
				closeTask();
			}
		});
	}
	
	private ProgressDialog bar;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (isFirstIn) {
				
				// bar.setCancelable(false);
				bar.show();
			}
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String httpresp = getCommentList.getCommentsListJsonString("goods_id=" + goodsId, fromIndex + "", amount + "", "", "commentDate+desc", "%2A");
				issuccess = getCommentList.analysisGetListJson(httpresp);
				comments = getCommentList.getComments();
				isNoMore = getCommentList.getIsNoMore();
				isHasRet = getCommentList.getIsHasRet();
				return issuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(isFirstIn){
				bar.dismiss();
				isFirstIn = false;
			}
			issuccess = result;
			Log.e(CommentUtil.class.getName(), "ishasret:"+isHasRet + "and isnomore"+isNoMore);
			if(comments.isEmpty() && !isHasRet){
				linearLayout.removeAllViews();
				view = LayoutInflater.from(context).inflate(
						R.layout.act_guang, null);
				TextView emptyTextView = (TextView) view.findViewById(R.id.empty_text);
				emptyTextView.setText(R.string.none_comment);
				Log.e(CommentUtil.class.getName(), "ishasret:"+isHasRet + "and isnomore"+isNoMore);
				linearLayout.addView(view);
				return;
			} else if(isNoMore){
				Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_LONG).show();
				return;
			}
			
			refresh_view.setVisibility(view.GONE);
			mPullToRefreshScrollView.setVisibility(view.VISIBLE);
			
			int length = comments.size();
			Log.e(CommentUtil.class.getName(), "length:"+length);
			
			for (int i = 0; i < length; i++) {
				try {
					UserComment userComment = comments.get(i);
					View view = LayoutInflater.from(context).inflate(
							R.layout.item_goods_emulate_item, null);
					TextView userName = (TextView) view.findViewById(R.id.user_name);
					TextView userLevel = (TextView) view.findViewById(R.id.user_level);
					TextView userContent = (TextView) view.findViewById(R.id.emulate_text);
					TextView commentTime = (TextView) view.findViewById(R.id.emulate_user_time);
					LinearLayout starLayout = (LinearLayout) view.findViewById(R.id.emulate_grade_star);
					Log.e(CommentUtil.class.getName(), "view"+view + "and layout"+linearLayout);
					userName.setText(userComment.getUserName());
					userLevel.setText(userComment.getVipLevel());
					userContent.setText(userComment.getUserCommentText());
//					 userGrade.setText(userComment.getRate()+"");
					int rate = userComment.getRate();
					if(context != null) setCommStar(rate, starLayout);
					commentTime.setText(userComment.getCommentTime());
					linearLayout.addView(view);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}

	private void setCommStar(int rate, LinearLayout starLayout){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
				context.getResources().getDisplayMetrics());
		LinearLayout.LayoutParams lp_base = new LinearLayout.LayoutParams(
				(new Float(px)).intValue(), (new Float(px)).intValue());
		lp_base.gravity = Gravity.CENTER;
		for (int i = 0; i < rate; i++) {
			ImageView starImageView = new ImageView(context);
			starImageView.setLayoutParams(lp_base);
			starImageView.setPadding(5, 0, 5, 0);
			starImageView.setImageResource(R.drawable.score1);
			starLayout.addView(starImageView);
		}
	}

	/**
	 * 
	 * ?????
	 */
	private void setupShow(UserComment userComment) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_goods_emulate_item, null);
		ImageView userIcon = (ImageView) view.findViewById(R.id.user_icon);
		TextView userName = (TextView) view.findViewById(R.id.user_name);
		TextView userLevel = (TextView) view.findViewById(R.id.user_level);
		TextView userContent = (TextView) view.findViewById(R.id.emulate_text);
		TextView commentTime = (TextView) view.findViewById(R.id.emulate_user_time);
		userName.setText(userComment.getUserName());
		userLevel.setText(userComment.getVipLevel());
		userContent.setText(userComment.getUserCommentText());
		// userGrade.setText(userComment.getRate()+"");
		commentTime.setText(userComment.getCommentTime());
		Log.e(CommentUtil.class.getName(), userComment.getUserName());
		linearLayout.addView(view);
	}

}
