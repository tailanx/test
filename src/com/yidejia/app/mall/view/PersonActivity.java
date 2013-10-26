package com.yidejia.app.mall.view;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.util.MsgUtil;
//import com.test.R;
//import com.yidejia.app.mall.adapter.MyAdapter;
//import com.yidejia.app.mall.dal.ProduceDao;
//import com.yidejia.app.mall.entity.Produce;

public class PersonActivity extends SherlockActivity {
	// private Button go_back;
	// private ArrayList<Produce> mArrayList;
	// MyAdapter myAdapter;
	// ListView mListView;
	// private MessageDataManage messaeDataManage;//????????????
	// private MyApplication myApplication;
	// private
	// public void setupShow(){
	// //?????
	// // go_back = (Button)findViewById(R.id.main1_button);
	// // mArrayList = (ArrayList<Produce>) ProduceDao.produceList;
	// // for(int i=0;i<mArrayList.size();i++){
	// // Produce p= mArrayList.get(i);
	// // Log.i("info", p.toString());
	// // }
	//
	// // mListView = (ListView) findViewById(R.id.lv_group_list_list);
	// // mListView.setAdapter(myAdapter);
	// Log.i("info", "???");
	// }
	// //?????????
	// public void doClick(View v){
	// Intent intent = new Intent(this,MyMallActivity.class);
	// switch (v.getId()) {
	// case R.id.main1_button://?????????????
	// startActivity(intent);
	// //?????Activity??
	// this.finish();
	// break;
	//
	//
	// }
	// }
	//
	private void setupShow(){
		new MsgUtil(PersonActivity.this, layout,fromIndex,amount);
	}
	private int fromIndex = 0;
	private int amount = 5;
	PullToRefreshScrollView mPullToRefreshScrollView;
	LinearLayout layout;
	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
					PersonActivity.this.getApplicationContext(),
					System.currentTimeMillis(),
					DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			fromIndex += amount;
			setupShow();
			mPullToRefreshScrollView.onRefreshComplete();
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// messaeDataManage = new MessageDataManage(PersonActivity.this);
		// myApplication = (MyApplication) getApplication();
		//
		// boolean isSucess =
		// messaeDataManage.getMsgData(myApplication.getUserId(),
		// myApplication.getToken(), offset+"", limit+"");
		// if(isSucess){
		// ArrayList<MsgCenter> mArrayList = messaeDataManage.getMsg();
		// for(int i=0; i<mArrayList.size();i++){
		// MsgCenter ms = mArrayList.get(i);
		//
		// }
		// }

		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionbar();
		// myAdapter = new MyAdapter(this,ProduceDao.produceList);
		setContentView(R.layout.message_center);
		layout = (LinearLayout) findViewById(R.id.message_scrollView_linearlayout1);
		setupShow();//
	    mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.message_scrollView);
		
	    mPullToRefreshScrollView.setOnRefreshListener(listener);
		String label = getResources().getString(R.string.update_time)	+ DateUtils.formatDateTime(
				PersonActivity.this.getApplicationContext(),
				System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME
					| DateUtils.FORMAT_SHOW_DATE
					| DateUtils.FORMAT_ABBREV_ALL);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(label);;
		// View person = getLayoutInflater().inflate(R.layout.message_item,
		// null);
		// TextView textView = (TextView)
		// person.findViewById(R.id.person_textview1);
		// TextView textView1 = (TextView)
		// person.findViewById(R.id.person_textview3);
		// TextView textView2 = (TextView)
		// person.findViewById(R.id.person_textview4);
		// textView.setText("affas");
		// textView1.setText("aafas");
		// textView2.setText("bcxb");
		// layout.addView(person);
		// setupShow();

		// Log.i("info", "???");
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
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(ComposeActivity.this, "button",
				// Toast.LENGTH_SHORT).show();
				PersonActivity.this.finish();
			}
		});

		TextView titleTextView = (TextView) findViewById(R.id.compose_title);
		titleTextView.setText(getResources().getString(R.string.person_message));
	}
}
