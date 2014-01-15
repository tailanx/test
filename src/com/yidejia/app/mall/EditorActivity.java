package com.yidejia.app.mall;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.cache.CleanImageCache;
import com.yidejia.app.mall.ctrl.Check4Update;
import com.yidejia.app.mall.datamanage.DataCleanManager;

public class EditorActivity extends BaseActivity {
	private RelativeLayout help;
	private RelativeLayout clear;
	private RelativeLayout about;
	private RelativeLayout phone;
	private RelativeLayout recommended;
	private RelativeLayout check4updateLayout;
	private TextView editor_cache_size;

	private AlertDialog dialogHelp;
	private AlertDialog dialogClear;
	private AlertDialog exit;
	
	private MyApplication myApplication;

	private void setupShow() {
		LinearLayout mLayout1 = (LinearLayout) getLayoutInflater().inflate(
				R.layout.help, null);

		Builder builder = new Builder(this);
		Builder helpbuilder = new Builder(this);
		Builder exitbuilder = new Builder(this);
		dialogClear = builder
				.setTitle("提示")
				.setMessage("您确认清除缓存图片吗？")
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								DataCleanManager.cleanApplicationData(EditorActivity.this);
								WebView webView = new WebView(EditorActivity.this);
								webView.clearCache(true);
								webView.clearHistory();
								webView.clearFormData();
								new CleanImageCache().clearAllCache();
								Toast.makeText(EditorActivity.this, "清除成功",
										Toast.LENGTH_LONG).show();
								editor_cache_size.setText("0.00B");
							}
						}).setNegativeButton("取消", null).create();
		dialogHelp = helpbuilder.setTitle("伊的家服务条款")
				.setIcon(android.R.drawable.menu_frame).setView(mLayout1)
				.setPositiveButton("确定", null).setNegativeButton("取消", null).create();
		
		exit = exitbuilder.setTitle("伊的家")
				.setIcon(android.R.drawable.divider_horizontal_dim_dark)
				.setMessage(getResources().getString(R.string.retrun_back))
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								myApplication.setIsLogin(false);
								Intent  intent = new Intent(EditorActivity.this,HomeLogActivity.class);
								EditorActivity.this.startActivity(intent);
								EditorActivity.this.finish();
							}

						}).setNegativeButton("取消", null).create();

	}

	int width;
	int height;
	private TextView phoneNumber;
	private TextView versionNameTextView;
	
	private Check4Update check4Update;
	private String currVersion = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication = (MyApplication) getApplication();
//		setActionbar();
		setActionbarConfig();
		setTitle(getResources().getString(R.string.order_set));
		setContentView(R.layout.editor);
		phoneNumber =  (TextView) findViewById(R.id.main2_main2_linearlayout1_imageview22);
		editor_cache_size = (TextView) findViewById(R.id.editor_cache_size);
		editor_cache_size.setText(DataCleanManager.getTotalSize(this));
		
		help = (RelativeLayout) findViewById(R.id.editor_linearLayout1);
		clear = (RelativeLayout) findViewById(R.id.editor_linearLayout3);
		
		about = (RelativeLayout) findViewById(R.id.editor_linearLayout4);
		phone = (RelativeLayout) findViewById(R.id.editor_linearLayout5);
		recommended = (RelativeLayout) findViewById(R.id.editor_linearLayout6);
		recommended.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(EditorActivity.this,
						RecommendActivity.class);
				startActivity(intent);
			}

		});
		
		versionNameTextView = (TextView) findViewById(R.id.editor_current_version);
		
		check4Update = new Check4Update(EditorActivity.this);
		currVersion = check4Update.getVersionName();
		
		versionNameTextView.setText(currVersion);
		
		check4updateLayout = (RelativeLayout) findViewById(R.id.editor_linearLayout7);
		check4updateLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 事件id（"registered id"）的事件pass，其时长持续100毫秒
				StatService.onEventDuration(EditorActivity.this,
						"check for update", "check for update", 100);
				check4Update.checkUpdate();
			}
		});
		
		Button editor_exit = (Button) findViewById(R.id.editor_exit);
		
		if(!myApplication.getIsLogin()){
			editor_exit.setVisibility(View.GONE);
		}
		
		editor_exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exit.show();
			}
		});

		help.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogHelp.show();
			}

		});
		clear.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogClear.show();

			}

		});
		about.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(EditorActivity.this,AboutActivity.class);
				EditorActivity.this.startActivity(intent);
			}

		});

		phone.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String  number = phoneNumber.getText().toString();
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
				startActivity(intent);
				
			}

		});

		setupShow();

	}

//	private void setActionbar() {
//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//		getSupportActionBar().setDisplayShowHomeEnabled(false);
//		getSupportActionBar().setDisplayShowTitleEnabled(false);
//		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setIcon(R.drawable.back);
//		getSupportActionBar().setDisplayShowCustomEnabled(true);
//		getSupportActionBar().setCustomView(R.layout.actionbar_common);
//		TextView button = (TextView) findViewById(R.id.ab_common_back);
//
//		button.setOnClickListener(new android.view.View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				EditorActivity.this.finish();
//			}
//		});
//
//		TextView titleTextView = (TextView) findViewById(R.id.ab_common_title);
//		titleTextView.setText("设置");
//
//	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
	
}
