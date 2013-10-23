package com.yidejia.app.mall.util;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.UserCommentDataManage;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.net.ConnectionDetector;

public class CommentUtil {
	private Context context;
	private LayoutInflater inflater;
	private LinearLayout linearLayout;
	private View view;
	private ImageView userIcon;// 用户头像
	private TextView userName;// 用户名字
	private TextView userLevel;// 用户等级
	private TextView userContent;// 用户
	private TextView userGrade;// 用户评分
	private TextView commentTime;// 评论时间

	private UserCommentDataManage dataManage;

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
																	// view
		this.inflater = LayoutInflater.from(context); // ,UserCommentDataManage
														// dataManage
		this.linearLayout = linearLayout;
		this.context = context;
		// this.view = view;
		// this.dataManage = dataManage;
	}

	/**
	 * 显示全部的数据
	 */

	public void AllComment(String goodsId, int fromIndex, int amount) {
		if (ConnectionDetector.isConnectingToInternet(context)) {
			ProgressDialog bar = new ProgressDialog(context);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage("正在查询");
			bar.show();
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			dataManage = new UserCommentDataManage(context);
			bar.dismiss();
			if("".equals(goodsId) || goodsId == null) {
				Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();
				return;
			}
			ArrayList<UserComment> userList = dataManage.getUserCommentsArray(
					goodsId, fromIndex, amount, false);
			for (int i = 0; i < userList.size(); i++) {
				setupShow();// 实例化控件
				UserComment userComment = userList.get(i);
				String path = userComment.getUserPictureUrl();
				Bitmap bm = BitmapFactory.decodeFile(path);
				if (bm != null) {
					userIcon.setImageBitmap(bm);
				} else {
					userIcon.setImageResource(R.drawable.ic_launcher);
				}
				userName.setText(userComment.getUserName());
				userLevel.setText(userComment.getVipLevel());
				userContent.setText(userComment.getUserCommentText());
				// userGrade.setText(userComment.getRate()+"");
				commentTime.setText(userComment.getCommentTime());
				linearLayout.addView(view);
			}
		}
	}

	/**
	 * 
	 * 实例化控件
	 */
	private void setupShow() {
		view = LayoutInflater.from(context).inflate(
				R.layout.item_goods_emulate_item, null);
		userIcon = (ImageView) view.findViewById(R.id.user_icon);
		userName = (TextView) view.findViewById(R.id.user_name);
		userLevel = (TextView) view.findViewById(R.id.user_level);
		userContent = (TextView) view.findViewById(R.id.emulate_text);
		userGrade = (TextView) view.findViewById(R.id.emulate_grade);
		commentTime = (TextView) view.findViewById(R.id.emulate_user_time);
	}

}
