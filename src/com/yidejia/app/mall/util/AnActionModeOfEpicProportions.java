package com.yidejia.app.mall.util;

import android.content.Context;
import android.widget.Toast;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.yidejia.app.mall.R;

public class AnActionModeOfEpicProportions implements ActionMode.Callback {

	private Context mContext;
	
	public AnActionModeOfEpicProportions(Context mContext){
		this.mContext = mContext; 
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, Menu.NONE, "��ҳ")
		.setIcon(R.drawable.home_normal)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, 1, Menu.NONE, "���֡���")
		.setIcon(R.drawable.down_guang_normal)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, 2, Menu.NONE, "����")
		.setIcon(R.drawable.down_search_normal)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, 3, Menu.NONE, "���ﳵ")
		.setIcon(R.drawable.down_shopping_normal)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add(0, 4, Menu.NONE, "�ҵ��̳�")
		.setIcon(R.drawable.down_my_normal)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "Got click: " + item, Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}

}
