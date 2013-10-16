package com.yidejia.app.mall.view;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


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
	private RelativeLayout recommended 
;
	
	
	private AlertDialog dialogHelp;
	private AlertDialog dialogAbout;
	private AlertDialog dialogphone;
	private AlertDialog dialogClear;
	private AlertDialog exit;
	
	private void setupShow(){
		 LinearLayout mLayout1 =(LinearLayout) getLayoutInflater().inflate(R.layout.help, null);
		 LinearLayout mLayout2 =(LinearLayout) getLayoutInflater().inflate(R.layout.about, null);
		 LinearLayout mLayout3 =(LinearLayout) getLayoutInflater().inflate(R.layout.phone_number, null);

		 Builder builder = new Builder(this);
		 dialogClear = builder
					.setTitle("��ʾ")
					.setMessage("��ȷ���������ͼƬ��")
					.setPositiveButton("ȷ��",new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							Toast.makeText(EditorActivity.this, "����ɹ�", Toast.LENGTH_LONG).show();
							
						}
					})
					.setNegativeButton("ȡ��", null)
					.create();
		 dialogHelp = builder
				.setTitle("����槷�������")
				.setIcon(android.R.drawable.menu_frame)
				.setView(mLayout1)
				.setPositiveButton("ȷ��", null).create();
		 dialogAbout = builder
					.setTitle("����")
					.setIcon(R.drawable.ic_launcher)
					.setView(mLayout2)
					.setPositiveButton("ȷ��", null).create();
		 dialogphone = builder
					.setTitle("�����")
					.setIcon(android.R.drawable.divider_horizontal_dim_dark)
					.setView(mLayout3)
					.setPositiveButton("ȷ��", null).create();
		 exit = builder
					.setPositiveButton("ȷ��",new android.content.DialogInterface.OnClickListener(){
						
						public void onClick(DialogInterface arg0, int arg1) {
							EditorActivity.this.finish();
							
						}

					
						
					})
					.setNegativeButton("ȡ��", null)
					.create()
					;
		   	
	}
	public void doClick(View v){
		switch (v.getId()) {	
		case R.id.editor_exit:
			exit.show();


 
			break;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setActionbar();
		setContentView(R.layout.editor);
		help =(RelativeLayout) findViewById(R.id.editor_linearLayout1);
		option =(RelativeLayout) findViewById(R.id.editor_linearLayout2);
		clear =(RelativeLayout) findViewById(R.id.editor_linearLayout3);
		
		about =(RelativeLayout) findViewById(R.id.editor_linearLayout4);
		phone =(RelativeLayout) findViewById(R.id.editor_linearLayout5);
		recommended =(RelativeLayout) findViewById(R.id.editor_linearLayout6);
		recommended.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(EditorActivity.this,RecommendActivity.class);
				startActivity(intent);
			}
			
		});
		option.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(EditorActivity.this,OptionActivity.class);
				startActivity(intent);
			}
			
		});
		help.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialogHelp.show();
				
			}
			
		});
		clear.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialogClear.show();
				
			}
			
		});
		about.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialogAbout.show();
				
			}
			
		});
		
		phone.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialogphone.show();
				
			}
			
		});
		
		
		
		setupShow();
		
	}
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		
		button.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
				EditorActivity.this.finish();
			}
		});
		
		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText("����");
		
	}
	
	
	
}
