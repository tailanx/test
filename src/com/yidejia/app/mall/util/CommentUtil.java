package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
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
	private LinearLayout userGrade;// 用户评分
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
		initDisplayImageOption();
	}

	/**
	 * 显示全部的数据
	 */

	public void AllComment(String goodsId, int fromIndex, int amount) {
		if (ConnectionDetector.isConnectingToInternet(context)) {
			dataManage = new UserCommentDataManage(context);
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
//				Bitmap bm = BitmapFactory.decodeFile(path);
//				if (bm != null) {
//					userIcon.setImageBitmap(bm);
//				} else {
//					userIcon.setImageResource(R.drawable.ic_launcher);
//				}
				imageLoader.displayImage(path, userIcon, options, animateFirstListener);
				userName.setText(userComment.getUserName());
				userLevel.setText(userComment.getVipLevel());
				userContent.setText(userComment.getUserCommentText());
				// userGrade.setText(userComment.getRate()+"");
				addRateStar(userComment.getRate());
				commentTime.setText(userComment.getCommentTime());
				
				
				linearLayout.addView(view);
			}
		}
	}
	/**
	 * 添加用户评分的星星
	 * @param rate 好评度
	 */
	private void addRateStar(int rate) {

		for (int i = 0; i < 5; i++) {
			LinearLayout.LayoutParams params = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			ImageView rateImageView = new ImageView(context);
			rateImageView.setLayoutParams(params);
			rateImageView.setPadding(5, 0, 5, 0);
			if(i < rate){
				rateImageView.setImageResource(R.drawable.score1);
			} else {
				rateImageView.setImageResource(R.drawable.score2);
			}
			userGrade.addView(rateImageView);
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
		userGrade = (LinearLayout) view.findViewById(R.id.emulate_grade_star);
		commentTime = (TextView) view.findViewById(R.id.emulate_user_time);
	}
	
	/**
	 * 异步加载头像
	 */
	
	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {


		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private void initDisplayImageOption(){
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.user_head)
			.showImageOnFail(R.drawable.user_head)
			.showImageForEmptyUri(R.drawable.user_head)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
	}
	

}
