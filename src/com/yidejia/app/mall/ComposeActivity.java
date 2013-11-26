package com.yidejia.app.mall;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.yidejia.app.mall.adapter.ComposeExListAdapter;
/**
 * 这个类是分类的类，现在用不到了
 * @author long bin 
 *
 */
public class ComposeActivity extends SherlockActivity{

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
//		menu.add(Menu.NONE, 0, Menu.NONE, "��ҳ")
//		.setIcon(R.drawable.home_normal)
//		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(ComposeActivity.this, MainActivity.class);
//				startActivity(intent);
//				ComposeActivity.this.finish();
//				return true;
//			}
//		})
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 1, Menu.NONE, "���֡���")
//		.setIcon(R.drawable.down_guang_normal)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 2, Menu.NONE, "����")
//		.setIcon(R.drawable.down_search_normal)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 3, Menu.NONE, "���ﳵ")
//		.setIcon(R.drawable.down_shopping_normal)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		menu.add(Menu.NONE, 4, Menu.NONE, "�ҵ��̳�")
//		.setIcon(R.drawable.down_my_normal)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//		getSupportMenuInflater().inflate(R.menu.down_menu, menu);
//		MenuItem homeItem = (MenuItem) menu.findItem(R.id.down_home_layout);
//		homeItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(ComposeActivity.this, MainActivity.class);
//				startActivity(intent);
//				return false;
//			}
//		});
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		Toast.makeText(this, "Got click: " + item.toString(), Toast.LENGTH_SHORT).show();
		switch (item.getItemId()) {
		case 0:
			
			break;

		default:
			break;
		}
		return true;
	}

	private ExpandableListView mExpandableListView;
	private ComposeExListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_layout);
		setActionbar();
		
		mExpandableListView = (ExpandableListView) findViewById(R.id.compose_exlist);
		adapter = new ComposeExListAdapter(ComposeActivity.this);
		mExpandableListView.setAdapter(adapter);
		mExpandableListView.setGroupIndicator(null);//��ȥ�Դ�ļ�ͷ
	}
	
	
	private void setActionbar(){
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
//		getSupportActionBar().setLogo(R.drawable.back);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.actionbar_compose);
//		startActionMode(new AnActionModeOfEpicProportions(ComposeActivity.this));
		ImageView button = (ImageView) findViewById(R.id.compose_back);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ComposeActivity.this, "button", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
