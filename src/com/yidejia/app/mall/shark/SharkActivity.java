package com.yidejia.app.mall.shark;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class SharkActivity extends Activity implements OnClickListener,
		Callback {

	private SharkUtil sharkUtil;

	private RelativeLayout noProcue;// 无商品
	private RelativeLayout produce;// 无商品
	private RelativeLayout youhuiquan;// 优惠券

	private ImageView quanImageView;

	private Animation animation;
	private ImageView backImageView;
	private Animation quanAnimation;
	private MediaPlayer mediaPlayer;
	private ProductBaseInfo info;// 获取到的摇到的商品
	private TextView guize;// 规则

	private TextView yaocishu;// 剩下的要的次数

	private TextView youhuicount;// 优惠券的价格
	private TextView youhuixianzhi;// 优惠券的限制
	private TextView youhuiTime;// 优惠劵的时间
	private TextView youhuichakan;// 优惠券的查看

	private ImageView produceImage;// 商品的图片
	private TextView produceTitle;// 商品的名称
	private TextView producePrice;// 商品的价格
	private TextView produceChakan;// 商品查看
	private int count;
	private DisplayImageOptions options;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		options = MyApplication.getInstance().initGoodsImageOption();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shark_activity_main);
		initView();
		sharkUtil = new SharkUtil(this);
		backImageView.setOnClickListener(this);
		mediaPlayer = MediaPlayer.create(this, R.raw.yao);
		quanAnimation = AnimationUtils.loadAnimation(this,
				R.anim.my_translate_action);
		quanImageView.startAnimation(quanAnimation);
		guize = (TextView) findViewById(R.id.tv_shark_activity_guize);
		guize.setOnClickListener(this);
		produceChakan.setOnClickListener(this);
		handler = new Handler(this);
		youhuichakan.setOnClickListener(this);
	}

	private void initView() {
		noProcue = (RelativeLayout) findViewById(R.id.Re_show_produce);
		produce = (RelativeLayout) findViewById(R.id.Re_show_produce_add);
		youhuiquan = (RelativeLayout) findViewById(R.id.Re_show_produce_youhuiquan);

		yaocishu = (TextView) findViewById(R.id.tv_shared_sum_add);

		quanImageView = (ImageView) findViewById(R.id.iv_yaoyiyao_shou);
		backImageView = (ImageView) findViewById(R.id.iv_shark_back);
		youhuichakan = (TextView) findViewById(R.id.tv_shark_no_produce);
		youhuicount = (TextView) findViewById(R.id.tv_youhuiquan_price);
		youhuixianzhi = (TextView) findViewById(R.id.tv_youhuiquan_title);
		youhuiTime = (TextView) findViewById(R.id.tv_youhuiquan_time_add);

		produceImage = (ImageView) findViewById(R.id.iv_shared_produce_image);
		produceTitle = (TextView) findViewById(R.id.tv_shared_produce_title);
		producePrice = (TextView) findViewById(R.id.tv_shared_produce_price);
		produceChakan = (TextView) findViewById(R.id.tv_shark_no_produce_add);

	}

	@Override
	protected void onResume() {
		super.onResume();

		StatService.onPageStart(this, "摇一摇页面");

		sharkUtil.registerListener(new IShark() {

			@Override
			public void onStart() {
				quanImageView.clearAnimation();
				quanAnimation.setRepeatCount(0);
				// relativeLayout.setVisibility(View.VISIBLE);
				quanImageView.setVisibility(View.GONE);
				mediaPlayer.start();
				// startAnimaton();
				noProcue.setVisibility(View.GONE);
				produce.setVisibility(View.GONE);
				getSharkData();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			}

		});
	}

	private void getSharkData() {
		String url = new JNICallBack().getHttp4GetShark(MyApplication
				.getInstance().getUserId(), MyApplication.getInstance()
				.getToken());

		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, new IHttpResp() {

			@Override
			public void success(String content) {
				ParseShark parseShark = new ParseShark();
				boolean isSuccess = parseShark.parseShark(content);
				count = parseShark.getCount();

				// if (count == 0) {
				// sharkUtil.unregisterListener();
				// Toast.makeText(SharkActivity.this,
				// getResources().getString(R.string.no_count),
				// Toast.LENGTH_SHORT).show();
				// }
				if (isSuccess) {
					Message msg = new Message();
					msg.what = 1;
					msg.arg1 = count;
					handler.sendMessage(msg);
					// yaocishu.setText(count);
					int theType = parseShark.getTheType();
					switch (theType) {
					case 1: // 什么都没摇到
						noProcue.setVisibility(View.VISIBLE);
						startAnimaton(noProcue);
						break;
					case 2: // 摇到商品

						produce.setVisibility(View.VISIBLE);
						startAnimaton(produce);

						info = parseShark.getProductBaseInfo();
						if (null != info && !"".equals(info)) {
							ImageLoader.getInstance().init(
									MyApplication.getInstance().initConfig());
							ImageLoader.getInstance().displayImage(
									info.getImgUrl(),
									produceImage,
									options,
									MyApplication.getInstance()
											.getImageLoadingListener());
							produceTitle.setText(info.getName());
							producePrice.setText("￥" + info.getPrice());
						}
						break;
					// case 3: // 摇到礼品券
					// youhuiquan.setVisibility(View.VISIBLE);
					// startAnimaton(youhuiquan);
					// break;
					// case 4: // 摇到现金券
					// youhuiquan.setVisibility(View.VISIBLE);
					// startAnimaton(youhuiquan);
					// break;
					}
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		sharkUtil.unregisterListener();
		StatService.onPageEnd(this, "摇一摇页面");

	}

	@Override
	protected void onStop() {
		super.onStop();
		sharkUtil.unregisterListener();
	}

	private void startAnimaton(RelativeLayout rela) {
		animation = AnimationUtils.loadAnimation(this, R.anim.myown_design);
		rela.startAnimation(animation);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_shark_back:
			this.finish();
			break;

		case R.id.tv_shark_activity_guize:// 规则
			LinearLayout root = new LinearLayout(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			WebView webView = new WebView(this);
			webView.setLayoutParams(lp);
			root.addView(webView);
			webView.loadUrl("http://m.yidejia.com/shakerules.html");
			setContentView(root);
			break;
		case R.id.tv_shark_no_produce_add:
			Intent intent = new Intent(this, GoodsInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("goodsId", info.getUId());
			intent.putExtras(bundle);
			SharkActivity.this.startActivity(intent);
			break;
		case R.id.tv_shark_no_produce:// 再摇一次】
			noProcue.setVisibility(View.GONE);
			produce.setVisibility(View.GONE);
			youhuiquan.setVisibility(View.GONE);
			quanImageView.setVisibility(View.VISIBLE);
			quanImageView.startAnimation(quanAnimation);
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			yaocishu.setText(msg.arg1 + "");
			break;

		}
		return false;
	}
}