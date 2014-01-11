package com.yidejia.app.mall.goodinfo;

import java.io.IOException;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.DPIUtil;
import com.yidejia.app.mall.widget.YLViewPager;

/**
 * 商品基本信息activity
 * @author LongBin
 *
 */
public class GoodsInfoActivity extends BaseActivity {

	private String goodsId; // 商品id
	private YLViewPager vpImage;	//商品图片容器
	private int width;	//屏幕宽
	private ProductBaseInfo productInfo;	//商品信息类实例
	private Button btnCartNum;	//商品信息页购物车按钮的数字

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			goodsId = bundle.getString("goodsId");
		}
		setActionbarConfig();
		setTitle(R.string.goods_info);

		setContentView(R.layout.item_goods_base_info);
		vpImage = (YLViewPager) findViewById(R.id.vp_item_goods_detail);

		width = DPIUtil.getWidth();

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				width, width);
		vpImage.setLayoutParams(layoutParams);
		
		getData();
	}

	private void getData() {
		String url = new JNICallBack().getHttp4GetGoods(goodsId);
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse() {

			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				ParseGoodsJson parseGoodsJson = new ParseGoodsJson();
				boolean issucess = parseGoodsJson.parseGoodsInfo(content);
				if(issucess) {
					productInfo = parseGoodsJson.getProductBaseInfo();
					GoodsInfoViewUtil viewUtil = new GoodsInfoViewUtil(GoodsInfoActivity.this);
					viewUtil.initGoodsView(productInfo);
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				if (error instanceof IOException) {
					Toast.makeText(GoodsInfoActivity.this,
							getResources().getString(R.string.bad_network),
							Toast.LENGTH_LONG).show();
				}
			}

		});
	}

//	/** 设置界面的头部 **/
//	private void setActionbarConfig() {
//		getSupportActionBar().setCustomView(R.layout.actionbar_common);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//		getSupportActionBar().setDisplayShowCustomEnabled(true);
//		getSupportActionBar().setDisplayShowTitleEnabled(false);
//		getSupportActionBar().setDisplayShowHomeEnabled(false);
//		((TextView) findViewById(R.id.ab_common_title)).setText("商品展示");
//
//		TextView tvBack = (TextView) findViewById(R.id.ab_common_back);
//
//		tvBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				GoodsInfoActivity.this.finish();
//			}
//		});
//	}

}
