package com.yidejia.app.mall.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.yidejia.app.mall.R;

public class EditorActivity extends SherlockActivity {
	private RelativeLayout help;
	private RelativeLayout option;
	private RelativeLayout clear;
	private RelativeLayout about;
	private RelativeLayout phone;
	private RelativeLayout recommended;

	private AlertDialog dialogHelp;
	private AlertDialog dialogAbout;
	private AlertDialog dialogphone;
	private AlertDialog dialogClear;
	private AlertDialog exit;

	private void setupShow() {
		LinearLayout mLayout1 = (LinearLayout) getLayoutInflater().inflate(
				R.layout.help, null);
		LinearLayout mLayout2 = (LinearLayout) getLayoutInflater().inflate(
				R.layout.about, null);
		LinearLayout mLayout3 = (LinearLayout) getLayoutInflater().inflate(
				R.layout.phone_number, null);

		Builder builder = new Builder(this);
		dialogClear = builder
				.setTitle("提示")
				.setMessage("您确认清除缓存图片吗？")
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Toast.makeText(EditorActivity.this, "清除成功",
										Toast.LENGTH_LONG).show();

							}
						}).setNegativeButton("取消", null).create();
		dialogHelp = builder.setTitle("艾蒂妲服务条款")
				.setIcon(android.R.drawable.menu_frame).setView(mLayout1)
				.setPositiveButton("确定", null).create();
		dialogAbout = builder.setTitle("关于").setIcon(R.drawable.ic_launcher)
				.setView(mLayout2).setPositiveButton("确定", null).create();
		dialogphone = builder.setTitle("艾蒂妲")
				.setIcon(android.R.drawable.divider_horizontal_dim_dark)
				.setView(mLayout3).setPositiveButton("确定", null).create();
		exit = builder
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								System.exit(0);

							}

						}).setNegativeButton("取消", null).create();
		// WindowManager.LayoutParams layoutParams =
		// dialog.getWindow().getAttributes();
		// layoutParams.width = 200;
		// layoutParams.height = LayoutParams.WRAP_CONTENT;
		// dialog.getWindow().setAttributes(layoutParams);

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.editor_exit:
			exit.show();

			break;
		}
	}

	int width;
	int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight() + 300;
		setActionbar();
		setContentView(R.layout.editor);
		help = (RelativeLayout) findViewById(R.id.editor_linearLayout1);
		option = (RelativeLayout) findViewById(R.id.editor_linearLayout2);
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
		option.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(EditorActivity.this,
						OptionActivity.class);
				startActivity(intent);

			}

		});
		help.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogHelp.show();

				dialogHelp.getWindow().setLayout(width, height / 2);
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
				dialogAbout.show();
				dialogHelp.getWindow().setLayout(width, height / 2);
			}

		});

		phone.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogphone.show();

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
