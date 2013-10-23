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
	private ImageView userIcon;// �û�ͷ��
	private TextView userName;// �û�����
	private TextView userLevel;// �û��ȼ�
	private TextView userContent;// �û�
	private TextView userGrade;// �û�����
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
	}

	/**
	 * ��ʾȫ��������
	 */

	public void AllComment(String goodsId, int fromIndex, int amount) {
		if (ConnectionDetector.isConnectingToInternet(context)) {
			ProgressDialog bar = new ProgressDialog(context);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage("���ڲ�ѯ");
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
				Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
				return;
			}
			ArrayList<UserComment> userList = dataManage.getUserCommentsArray(
					goodsId, fromIndex, amount, false);
			for (int i = 0; i < userList.size(); i++) {
				setupShow();// ʵ�����ؼ�
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
	 * ʵ�����ؼ�
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
