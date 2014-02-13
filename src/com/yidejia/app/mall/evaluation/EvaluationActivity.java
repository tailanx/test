package com.yidejia.app.mall.evaluation;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.id;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.R.string;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.Cart;

public class EvaluationActivity extends BaseActivity {

	private LinearLayout layout;
	private MyApplication myApplication;
	private String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation);
		myApplication = MyApplication.getInstance();
		userid = myApplication.getUserId();
		// setActionbar();
		setActionbarConfig();
		setTitle(getResources().getString(R.string.evaluation));
		layout = (LinearLayout) findViewById(R.id.evaluation_scrollView_linearlayout1);
		// TaskNoEva taskNoEva = new TaskNoEva(EvaluationActivity.this, layout);
		// taskNoEva.getWaitingComment(myApplication.getUserId(), true);
		getData();
	}

	/** 获取带评论数据 **/
	private void getData() {
		String url = new JNICallBack().getHttp4GetNoEvaluate(userid);

		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse() {

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
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if (HttpStatus.SC_OK == statusCode) {
					ParseEvaJson parseEvaJson = new ParseEvaJson();
					boolean isSuccess = parseEvaJson.parseNoEvaJson(content);
					ArrayList<Cart> noEvaCarts = parseEvaJson
							.getWaitCommGoods();
					if (isSuccess && null != noEvaCarts) {
						showView(noEvaCarts);
					} else {
						Toast.makeText(EvaluationActivity.this, "暂无数据",
								Toast.LENGTH_SHORT).show();
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

	/** 显示到view **/
	private void showView(ArrayList<Cart> waitCommGoods) {

		ImageLoader imageLoader = ImageLoader.getInstance();

		int length = waitCommGoods.size();
		Cart mCart;
		for (int i = 0; i < length; i++) {
			mCart = waitCommGoods.get(i);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
			lp.setMargins(0, 10, 0, 0);
			View view = LayoutInflater.from(this).inflate(
					R.layout.evaluation_item, null);
			TextView evaluation_item_text = (TextView) view
					.findViewById(R.id.evaluation_item_text);// 商品名称
			TextView evaluation_item_sum = (TextView) view
					.findViewById(R.id.evaluation_item_sum);
			TextView evaluation_item_count = (TextView) view
					.findViewById(R.id.evaluation_item_count);
			TextView evaluation_sum_money = (TextView) view
					.findViewById(R.id.evaluation_sum_money);
			TextView evaluation_item_total_num = (TextView) view
					.findViewById(R.id.evaluation_item_total_num);

			ImageView evaluation_item_image = (ImageView) view
					.findViewById(R.id.evaluation_item_image);

			ImageLoader.getInstance().init(
					MyApplication.getInstance().initConfig());
			imageLoader.displayImage(mCart.getImgUrl(), evaluation_item_image,
					myApplication.initGoodsImageOption(),
					myApplication.getImageLoadingListener());
			Button evaluation_item_evaluation = (Button) view
					.findViewById(R.id.evaluation_item_evaluation);
			evaluation_item_text.setText(mCart.getProductText());
			evaluation_item_sum.setText(getResources().getString(R.string.unit)
					+ mCart.getPrice());
			evaluation_item_count.setText("数量:" + mCart.getAmount());
			evaluation_sum_money.setText((mCart.getPrice() * mCart.getAmount())
					+ "");
			evaluation_item_total_num.setText("数量:" + mCart.getAmount() + "");

			final String goodsId = mCart.getUId();

			evaluation_item_evaluation
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							// 评价
							intent.setClass(EvaluationActivity.this,
									PersonEvaluationActivity.class);
							intent.putExtra("goodsId", goodsId);
							int requestCode = 4001;
							startActivityForResult(intent, requestCode);
							// 结束当前Activity；
							// activity.finish();
						}
					});
			view.setLayoutParams(lp);
			layout.addView(view);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 4001 && resultCode == 4002) {
			layout.removeAllViews();
			getData();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// StatService.onResume(this);
		StatService.onPageStart(this, "待评价晒单页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// StatService.onPause(this);
		StatService.onPageEnd(this, "待评价晒单页面");
	}
}