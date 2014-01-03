package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.HistoryAdapter;
import com.yidejia.app.mall.datamanage.BrowseHistoryDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.ProductBaseInfo;

public class HistoryActivity extends SherlockFragmentActivity {
	private BrowseHistoryDataManage historyDataManage;
	private HistoryAdapter adapter;
	private PullToRefreshListView listview;
	private ArrayList<ProductBaseInfo> mCarts;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		dialog = new Builder(HistoryActivity.this)
				.setMessage(
						getResources().getString(R.string.sure_delete_history))
				.setTitle(getResources().getString(R.string.delete_history))
				.setPositiveButton(getResources().getString(R.string.sure),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if (mCarts.isEmpty()) {
									Toast.makeText(
											HistoryActivity.this,
											getResources().getString(
													R.string.zhanwulishishuju),
											Toast.LENGTH_SHORT).show();
								} else {
									historyDataManage.cleanHistory();
									mCarts.clear();
									adapter.notifyDataSetChanged();
								}
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancel),
						null).create();
		historyDataManage = new BrowseHistoryDataManage();
		setActionBarConfig();
		mCarts = historyDataManage.getHistoryArray();
		// Log.i("info", mCarts.size()+"");
		// if(mCarts.size() ==0){
		//
		// }
		if (mCarts.isEmpty()) {
			Toast.makeText(this,
					getResources().getString(R.string.zhanwulishishuju),
					Toast.LENGTH_SHORT).show();
		}
		setContentView(R.layout.activity_search_result_list_layout);
		listview = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		adapter = new HistoryAdapter(HistoryActivity.this, mCarts);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listener);
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			ProductBaseInfo cart = mCarts.get(arg2 - 1);
			Intent intent = new Intent(HistoryActivity.this,
					GoodsInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("goodsId", cart.getUId());
			intent.putExtras(bundle);
			HistoryActivity.this.startActivity(intent);
		}
	};

	private void setActionBarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_common);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		TextView title = (TextView) findViewById(R.id.actionbar_title);
		title.setText(getResources().getString(R.string.history_watch));
		ImageView left = (ImageView) findViewById(R.id.actionbar_left);
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HistoryActivity.this.finish();

			}
		});
		Button right = (Button) findViewById(R.id.actionbar_right);
		right.setText(getResources().getString(R.string.clean));
		right.setOnClickListener(onClickListener);
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog.show();
		}
	};
}
