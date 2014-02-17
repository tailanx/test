package com.yidejia.app.mall.shark;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class SharkActivity extends Activity implements OnClickListener {

	private SharkUtil sharkUtil;

	private RelativeLayout relativeLayout;
	private ImageView quanImageView;
	private Animation animation;
	private ImageView backImageView;
	private Animation quanAnimation;
	private MediaPlayer mediaPlayer;
	
	private TextView guize;// 规则

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shark_activity_main);
		sharkUtil = new SharkUtil(this);
		relativeLayout = (RelativeLayout) findViewById(R.id.Re_show_produce);
		quanImageView = (ImageView) findViewById(R.id.iv_yaoyiyao_shou);
		backImageView = (ImageView) findViewById(R.id.iv_shark_back);
		backImageView.setOnClickListener(this);
		mediaPlayer = MediaPlayer.create(this, R.raw.yao);
		quanAnimation = AnimationUtils.loadAnimation(this,
				R.anim.my_translate_action);
		quanImageView.startAnimation(quanAnimation);
		
		guize = (TextView) findViewById(R.id.tv_shark_activity_guize);
		guize.setOnClickListener(this);
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
				relativeLayout.setVisibility(View.VISIBLE);
				quanImageView.setVisibility(View.GONE);
				mediaPlayer.start();
				startAnimaton();
				getSharkData();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			}

		});
	}
	
	private void getSharkData(){
		String url = new JNICallBack().getHttp4GetShark(MyApplication.getInstance().getUserId(), MyApplication.getInstance().getToken());
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, new IHttpResp() {
			
			@Override
			public void success(String content) {
				ParseShark parseShark = new ParseShark();
				boolean isSuccess = parseShark.parseShark(content);
				if (isSuccess) {
					int theType = parseShark.getTheType();
					switch (theType) {
					case 1:	//什么都没摇到
						Toast.makeText(SharkActivity.this, parseShark.getData(), Toast.LENGTH_SHORT).show();
						break;
					case 2:	//摇到商品
						ProductBaseInfo productInfo = parseShark.getProductBaseInfo();
						if(null != productInfo) {
							//TODO 显示商品
						}
						break;
					case 3:	//摇到礼品券
						break;
					case 4:	//摇到现金券
						break;
					default:
						break;
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

	private void startAnimaton() {
		animation = AnimationUtils.loadAnimation(this, R.anim.myown_design);
		relativeLayout.startAnimation(animation);
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
		}
	}
}
