package com.yidejia.app.mall.shark;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore.Video.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yidejia.app.mall.R;

public class TestSharkActivity extends Activity implements OnClickListener {

	private SharkUtil sharkUtil;

	private RelativeLayout relativeLayout;
	private ImageView quanImageView;
	private Animation animation;
	private ImageView backImageView;
	private Animation quanAnimation;
	private MediaPlayer mediaPlayer;

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
		mediaPlayer = MediaPlayer.create(this, R.raw.a);
		quanAnimation = AnimationUtils.loadAnimation(this,
				R.anim.my_translate_action);
		quanImageView.startAnimation(quanAnimation);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sharkUtil.registerListener(new IShark() {

			@Override
			public void onStart() {
				quanImageView.clearAnimation();
				quanAnimation.setRepeatCount(0);
				relativeLayout.setVisibility(View.VISIBLE);
				quanImageView.setVisibility(View.GONE);
				mediaPlayer.start();
				startAnimaton();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
			}

		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sharkUtil.unregisterListener();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		sharkUtil.unregisterListener();
	}

	private void startAnimaton() {
		animation = AnimationUtils.loadAnimation(this, R.anim.myown_design);
		relativeLayout.startAnimation(animation);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_shark_back:
			this.finish();
			break;

		default:
			break;
		}
	}
}
