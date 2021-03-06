package com.yidejia.app.mall;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.cache.CleanImageCache;
import com.yidejia.app.mall.ctrl.Check4Update;
import com.yidejia.app.mall.datamanage.DataCleanManager;
import com.yidejia.app.mall.util.Consts;

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

	int width;
	int height;
	private TextView phoneNumber;
	private TextView versionNameTextView;

	private Check4Update check4Update;
	private String currVersion = "";
	private int isCleanFinish = 1;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication = MyApplication.getInstance();
		setActionbarConfig();
		setTitle(getResources().getString(R.string.order_set));
		setContentView(R.layout.editor);
		phoneNumber = (TextView) findViewById(R.id.main2_main2_linearlayout1_imageview22);
		editor_cache_size = (TextView) findViewById(R.id.editor_cache_size);
		editor_cache_size.setText(DataCleanManager.getTotalSize(this));

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
		// 检查更新
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
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		// 退出登录
		Button editor_exit = (Button) findViewById(R.id.editor_exit);

		if (!myApplication.getIsLogin()) {
			editor_exit.setVisibility(View.GONE);
		}

		editor_exit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				exit.show();
				sp.edit().remove("CHECK").commit();
			}
		});
		// 帮助
		help = (RelativeLayout) findViewById(R.id.editor_linearLayout1);
		help.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogHelp.show();
			}

		});
		// 清除缓存
		clear = (RelativeLayout) findViewById(R.id.editor_linearLayout3);
		clear.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogClear.show();
			}

		});
		// 关于
		about = (RelativeLayout) findViewById(R.id.editor_linearLayout4);
		about.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(EditorActivity.this,
						AboutActivity.class);
				EditorActivity.this.startActivity(intent);
			}

		});
		// 联系客服
		phone = (RelativeLayout) findViewById(R.id.editor_linearLayout5);
		phone.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String number = phoneNumber.getText().toString();
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
						+ number));
				startActivity(intent);

			}

		});

		setupShow();

	}

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
								cleanData();
							}
						}).setNegativeButton("取消", null).create();
		dialogHelp = helpbuilder.setTitle("伊的家服务条款")
				.setIcon(android.R.drawable.menu_frame).setView(mLayout1)
				.setPositiveButton("确定", null).setNegativeButton("取消", null)
				.create();

		exit = exitbuilder
				.setTitle("伊的家")
				.setIcon(android.R.drawable.divider_horizontal_dim_dark)
				.setMessage(getResources().getString(R.string.retrun_back))
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								myApplication.setIsLogin(false);
								myApplication.setUserId("");
								myApplication.setToken("");
								myApplication.setNick("");
								myApplication.setPassword("");
								myApplication.setAidou("");
								myApplication.setUserHeadImg("");
								myApplication.setVip("");

								Intent intent = new Intent(EditorActivity.this,
										HomeLoginActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("exit", Consts.EXIT_LOGIN);
								intent.putExtras(bundle);
								EditorActivity.this.startActivity(intent);
								EditorActivity.this.finish();
							}

						}).setNegativeButton("取消", null).create();

	}

	/** 清除数据 **/
	private void cleanData() {
		if(thread.getState() == Thread.State.NEW)
			thread.start();
		else thread.run();
	}

	private Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {
			// 清除数据
			DataCleanManager.cleanApplicationData(EditorActivity.this);
			new CleanImageCache().clearAllCache();

			Message msg = Message.obtain();
			msg.what = isCleanFinish;
			handler.sendMessage(msg);
		}
	});

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == isCleanFinish) {
				WebView webView = new WebView(EditorActivity.this);
				webView.clearCache(true);
				webView.clearHistory();
				webView.clearFormData();
				webView = null;
				Toast.makeText(EditorActivity.this, "清除成功", Toast.LENGTH_LONG)
						.show();
				editor_cache_size.setText("0.00B");
				
			}
		}

	};

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, getString(R.string.order_set));
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, getString(R.string.order_set));
	}

}
