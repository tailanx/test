package com.yidejia.app.mall.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidejia.app.mall.CleanImageCache;
import com.yidejia.app.mall.DataCleanManager;
import com.yidejia.app.mall.MainFragmentActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.Check4Update;
import com.yidejia.app.mall.main.HomeLogActivity;
import com.yidejia.app.mall.main.HomeMallActivity;
import com.yidejia.app.mall.util.Consts;

public class EditorActivity extends SherlockActivity {
	private RelativeLayout help;
//	private RelativeLayout option;
	private RelativeLayout clear;
	private RelativeLayout about;
	private RelativeLayout phone;
	private RelativeLayout recommended;
	private RelativeLayout check4updateLayout;
	private TextView editor_cache_size;

	private AlertDialog dialogHelp;
	private AlertDialog dialogAbout;
	private AlertDialog dialogphone;
	private AlertDialog dialogClear;
	private AlertDialog exit;
	
	private MyApplication myApplication;

	private void setupShow() {
		LinearLayout mLayout1 = (LinearLayout) getLayoutInflater().inflate(
				R.layout.help, null);
//		LinearLayout mLayout2 = (LinearLayout) getLayoutInflater().inflate(
//				R.layout.about, null);
//		LinearLayout mLayout3 = (LinearLayout) getLayoutInflater().inflate(
//				R.layout.phone_number, null);

		Builder builder = new Builder(this);
		Builder helpbuilder = new Builder(this);
		Builder aboutbuilder = new Builder(this);
		Builder exitbuilder = new Builder(this);
		Builder phonebuilder = new Builder(this);
//		dialogAbout.setc
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
//		dialogAbout = aboutbuilder.setTitle("关于").setIcon(R.drawable.ic_launcher)
//				.setView(mLayout2).setPositiveButton("确定", null).setNegativeButton("取消", null).create();
//		dialogphone = phonebuilder.setTitle("伊的家")
//				.setIcon(android.R.drawable.divider_horizontal_dim_dark)
//				.setMessage("确认拨打客服热线").setPositiveButton("确定", new OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						String  number = phoneNumber.getText().toString();
//						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
//						startActivity(intent);
//					}
//				}).setNegativeButton("取消", null).create();
		
		exit = exitbuilder.setTitle("伊的家")
				.setIcon(android.R.drawable.divider_horizontal_dim_dark)
				.setMessage(getResources().getString(R.string.retrun_back))
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								myApplication.setIsLogin(false);
//								Intent intent = new Intent(EditorActivity.this, MainFragmentActivity.class);
								Intent  intent = new Intent(EditorActivity.this,HomeMallActivity.class);
//								intent.setAction(Consts.RETURN_BACk);
//								EditorActivity.this.sendBroadcast(intent);
								EditorActivity.this.startActivity(intent);
								EditorActivity.this.finish();
							}

						}).setNegativeButton("取消", null).create();
		// WindowManager.LayoutParams layoutParams =
		// dialog.getWindow().getAttributes();
		// layoutParams.width = 200;
		// layoutParams.height = LayoutParams.WRAP_CONTENT;
		// dialog.getWindow().setAttributes(layoutParams);

	}

	int width;
	int height;
	private TextView phoneNumber;
	private TextView versionNameTextView;
	
	private Check4Update check4Update;
	private String currVersion = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		WindowManager manager = getWindowManager();
//		Display display = manager.getDefaultDisplay();
//		width = display.getWidth();
//		height = display.getHeight() + 300;\
		myApplication = (MyApplication) getApplication();
		setActionbar();
		setContentView(R.layout.editor);
		phoneNumber =  (TextView) findViewById(R.id.main2_main2_linearlayout1_imageview22);
		editor_cache_size = (TextView) findViewById(R.id.editor_cache_size);
		editor_cache_size.setText(DataCleanManager.getTotalSize(this));
		
		help = (RelativeLayout) findViewById(R.id.editor_linearLayout1);
//		option = (RelativeLayout) findViewById(R.id.editor_linearLayout2);
//		option.setVisibility(View.GONE);
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
				// TODO Auto-generated method stub
				check4Update.checkUpdate();
			}
		});
		
//		option.setOnClickListener(new android.view.View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				Intent intent = new Intent(EditorActivity.this,
//						OptionActivity.class);
//				startActivity(intent);
//
//			}
//
//		});
		Button editor_exit = (Button) findViewById(R.id.editor_exit);
		
		if(!myApplication.getIsLogin()){
			editor_exit.setVisibility(View.GONE);
		}
		
		editor_exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exit.show();
			}
		});

		help.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogHelp.show();
				
//				dialogHelp.getWindow().setLayout(width, height / 2);
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
				//				dialogAbout.show();
//				dialogHelp.getWindow().setLayout(width, height / 2);
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

	private void setActionbar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		// getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
		// startActionMode(new
		// AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);

		button.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ComposeActivity.this, "button",
				// Toast.LENGTH_SHORT).show();
				EditorActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("设置");

	}

}
