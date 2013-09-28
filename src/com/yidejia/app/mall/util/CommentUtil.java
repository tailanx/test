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
	private ImageView userIcon;// �û�ͷ��
	private TextView userName;// �û�����
	private TextView userLevel;// �û��ȼ�
	private TextView userContent;// �û�
	private LinearLayout userGrade;// �û�����
	private TextView commentTime;// ����ʱ��

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
	 * ��ʾȫ��������
	 */

	public void AllComment(String goodsId, int fromIndex, int amount) {
		if (ConnectionDetector.isConnectingToInternet(context)) {
			dataManage = new UserCommentDataManage(context);
			if("".equals(goodsId) || goodsId == null) {
				Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
				return;
			}
			ArrayList<UserComment> userList = dataManage.getUserCommentsArray(
					goodsId, fromIndex, amount, false);
			for (int i = 0; i < userList.size(); i++) {
				setupShow();// ʵ�����ؼ�
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
	 * ����û����ֵ�����
	 * @param rate ������
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
	 * ʵ�����ؼ�
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
	 * �첽����ͷ��
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
