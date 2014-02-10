package com.yidejia.app.mall.goodinfo;

import java.io.IOException;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
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
	private GoodsInfoViewUtil viewUtil;

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
					viewUtil = new GoodsInfoViewUtil(GoodsInfoActivity.this);
					viewUtil.initGoodsView(productInfo);
				} else {
					Toast.makeText(GoodsInfoActivity.this, getResources().getString(R.string.no_product), Toast.LENGTH_SHORT).show();
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

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, getString(R.string.goods_info));
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, getString(R.string.goods_info));
		if(null != viewUtil) viewUtil.setCartNumber();
	}



}
