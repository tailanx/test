package com.yidejia.app.mall;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.adapter.HistoryAdapter;
import com.yidejia.app.mall.datamanage.BrowseHistoryDataManage;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.model.ProductBaseInfo;

public class HistoryActivity extends BaseActivity {
	private BrowseHistoryDataManage historyDataManage;
	private HistoryAdapter adapter;
	private PullToRefreshListView listview;
	private ArrayList<ProductBaseInfo> mCarts;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
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
		setActionBar();
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
			ProductBaseInfo cart = mCarts.get(arg2 - 1);
			Intent intent = new Intent(HistoryActivity.this,
					GoodsInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("goodsId", cart.getUId());
			intent.putExtras(bundle);
			HistoryActivity.this.startActivity(intent);
		}
	};

	private void setActionBar() {
		
		setActionbarConfig();
		setTitle(R.string.history_watch);
		
		TextView tv_right = (TextView) findViewById(R.id.ab_common_tv_right);
		tv_right.setVisibility(View.VISIBLE);
		tv_right.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			dialog.show();
		}
	};
}
