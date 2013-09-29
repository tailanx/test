package com.yidejia.app.mall;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.yidejia.app.mall.adapter.SearchListAdapter;
import com.yidejia.app.mall.search.SuggestionsAdapter;

public class SearchActivity extends SherlockFragmentActivity {//implements SearchView.OnQueryTextListener,SearchView.OnSuggestionListener 
	
	
	private SuggestionsAdapter mSuggestionsAdapter;
	private static final String[] COLUMNS = { BaseColumns._ID,
			SearchManager.SUGGEST_COLUMN_TEXT_1, };
	private ListView searchHistoryList;
	private Button clearHistoryBtn;
	private Button searchBtn;
	private AutoCompleteTextView searchTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_search_history);
		setActionBarConfig();
		searchHistoryList = (ListView) findViewById(R.id.search_history_list);
		clearHistoryBtn = (Button) findViewById(R.id.search_history_btn);
//		SearchListAdapter searchListAdapter = new SearchListAdapter(SearchActivity.this);
		searchHistoryList.setAdapter(adapter);
		/*
		ImageView downSearchImage = (ImageView) findViewById(R.id.down_search_icon);
		downSearchImage.setImageResource(R.drawable.down_search_hover);
		RelativeLayout down_home_layout = (RelativeLayout) findViewById(R.id.down_home_layout);
		down_home_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchActivity.this, MainActivity.class);
				startActivity(intent);
//				SearchActivity.this.finish();
			}
		});
		*/
//		searchListView.setVisibility(View.GONE);
		searchHistoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
//				intent.putExtra("position", arg2);
				Bundle bundle = new Bundle();
				
				startActivity(intent);
			}
		});
		handleIntent(getIntent());  
	}
	@Override  
	protected void onNewIntent(Intent intent) {  
	    setIntent(intent);  
	    handleIntent(intent);  
	} 
	private void handleIntent(Intent intent) {  
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {  
	      String query = intent.getStringExtra(SearchManager.QUERY);  
	      doMySearch(query);  
	    }  
	}  
	
	private void doMySearch(String query){
		Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
	}
	/*
	@Override
	public boolean onSuggestionSelect(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		// TODO Auto-generated method stub
		Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
        String query = c.getString(c.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
        Toast.makeText(this, "Suggestion clicked: " + query, Toast.LENGTH_LONG).show();
        return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "You searched for: " + query, Toast.LENGTH_LONG).show();
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}
	*/
	private void setActionBarConfig(){
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_search_view);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		searchTextView = (AutoCompleteTextView) findViewById(R.id.searchActivity_autoComplete);
		adapter = new ArrayAdapter<String>(this, //定义匹配源的adapter
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//		searchTextView.setAdapter(adapter);
		searchBtn = (Button) findViewById(R.id.search_btn);
	}
	
	private ArrayAdapter<String> adapter;
	static final String[] COUNTRIES = new String[] {  //这里用一个字符串数组来当数据匹配源
	     "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra"};
}
