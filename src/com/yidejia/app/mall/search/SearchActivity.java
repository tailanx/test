package com.yidejia.app.mall.search;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.SchHistoryDataManage;

public class SearchActivity extends SherlockFragmentActivity {

	private String TAG = getClass().getName();
	private ListView searchHistoryList;
	private Button clearHistoryBtn;
	private Button searchBtn;
	private EditText autoCompleteTextView;

	private ArrayList<String> historyArrayList = new ArrayList<String>();
	private SchHistoryDataManage historyDataManage;
	private InputMethodManager inputMethodManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_search_history);
		Log.e(TAG, "on create");
		historyDataManage = new SchHistoryDataManage();
		historyArrayList = historyDataManage.getHistorysArray();
		Log.i("SchHistory", "size" + historyArrayList.size());
		setActionBarConfig();
		searchHistoryList = (ListView) findViewById(R.id.search_history_list);
		clearHistoryBtn = (Button) findViewById(R.id.search_history_btn);
		if (historyArrayList.isEmpty())
			clearHistoryBtn.setVisibility(ViewGroup.GONE);
		else {
			clearHistoryBtn.setVisibility(ViewGroup.VISIBLE);
		}
		searchHistoryList.setAdapter(adapter);

		autoCompleteTextView.requestFocus();

		inputMethodManager = (InputMethodManager) this.getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		autoCompleteTextView
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							String name = autoCompleteTextView.getText()
									.toString();
							if (name.trim() == null || "".equals(name.trim()))
								return false;
							// 未包含该记录，添加
							if (!historyArrayList.contains(name)) {
								boolean state = historyDataManage
										.addHistory(name);
								historyArrayList.add(name);
								Log.i("SchHistory", "state:" + state);
							}
							inputMethodManager.hideSoftInputFromWindow(
									autoCompleteTextView.getWindowToken(), 0);
							adapter.notifyDataSetChanged();
							goToSchRst(name);
						}
						return true;
					}
				});
		searchHistoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String name = historyArrayList.get(arg2);
				inputMethodManager.hideSoftInputFromWindow(
						autoCompleteTextView.getWindowToken(), 0);
				goToSchRst(name);
			}
		});

		clearHistoryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				historyDataManage.cleanHistory();
				historyArrayList.clear();
				adapter.notifyDataSetChanged();
				clearHistoryBtn.setVisibility(ViewGroup.GONE);
			}
		});

	}

	private void goToSchRst(String name) {
		Intent intent = new Intent(SearchActivity.this,
				SearchResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("name", name);
		bundle.putString("title", name);
		bundle.putString("price", "");
		bundle.putString("brand", "");
		bundle.putString("fun", "");
		intent.putExtras(bundle);
		startActivity(intent);
		SearchActivity.this.finish();
	}

	private void setActionBarConfig() {
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_search_view);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		autoCompleteTextView = (EditText) findViewById(R.id.searchActivity_autoComplete);
		autoCompleteTextView.setCursorVisible(true);

		adapter = new ArrayAdapter<String>(this, // 定义匹配源的adapter
				android.R.layout.simple_dropdown_item_1line, historyArrayList);
		searchBtn = (Button) findViewById(R.id.search_btn);
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = autoCompleteTextView.getText().toString();
				if (name.trim() == null || "".equals(name.trim()))
					return;
				// 未包含该记录，添加
				if (!historyArrayList.contains(name)) {
					boolean state = historyDataManage.addHistory(name);
					historyArrayList.add(name);
					Log.i("SchHistory", "state:" + state);
				}
				inputMethodManager.hideSoftInputFromWindow(
						autoCompleteTextView.getWindowToken(), 0);
				adapter.notifyDataSetChanged();
				goToSchRst(name);
			}
		});

		ImageView backBtn = (ImageView) findViewById(R.id.search_back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e(SearchActivity.this.getClass().getName(),
						"finish activity");
				inputMethodManager.hideSoftInputFromWindow(
						autoCompleteTextView.getWindowToken(), 0);
				SearchActivity.this.finish();
			}
		});
	}

	private ArrayAdapter<String> adapter;

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