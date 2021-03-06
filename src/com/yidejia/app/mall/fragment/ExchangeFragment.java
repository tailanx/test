package com.yidejia.app.mall.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.PreferentialDataManage;
import com.yidejia.app.mall.pay.CstmPayActivity;
import com.yidejia.app.mall.util.Consts;

public class ExchangeFragment extends SherlockFragment {
	// private TextView titleTextView;//订单的状态
	// private TextView numberTextView;//订单的编号
	// private TextView sumPrice;//订单的总价格
	// private TextView countTextView;//订单的总数目
	// private LinearLayout mLayout;//外层的布局
	// private View view;
	// private OrderDataManage orderDataManage ;//用来获取订单数据

	private String hello;
	private String defaultHello = "default hello";
	private ListView listview;
	private PreferentialDataManage dataManage;
	private ExchangeAdapter adapter;

	/**
	 * 实例化对象
	 * 
	 * @param view
	 */
	private void setupShow(View view) {

	}

	// 通过单例模式，构建对象
	public static ExchangeFragment newInstance(String s) {
		ExchangeFragment waitFragment = new ExchangeFragment();
		Bundle bundle = new Bundle();
		bundle.putString("Hello", s);
		waitFragment.setArguments(bundle);
		return waitFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取存储的参数
		Bundle args = getArguments();
		hello = args != null ? args.getString("hello") : defaultHello;
	}

	
	private InnerReciver receiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		receiver = new InnerReciver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.EXCHANG_FREE);
		getSherlockActivity().registerReceiver(receiver, filter);
		
		
		dataManage = new PreferentialDataManage(getSherlockActivity());
		View view = inflater.inflate(R.layout.exchange_produce, null);// 获取视图对象
		listview = (ListView) view
				.findViewById(R.id.exchange_shopping_listview);
		adapter = new ExchangeAdapter(CstmPayActivity.arrayListExchange,
				getActivity());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listItemClickListener);
		// LinearLayout relativeLayout =
		// (LinearLayout)view.findViewById(R.id.shopping_cart_relative2);//获取布局

		// AllOrderUtil allOrderUtil = new AllOrderUtil(getSherlockActivity(),
		// relativeLayout);
		// Log.i("info", allOrderUtil+"");
		// allOrderUtil.loadView();
		// setupShow(view);
		// getData();

		// View produce = inflater.inflate(R.layout.all_order_item_produce,
		// null);//产品详细
		// View produce1 = inflater.inflate(R.layout.all_order_item_produce,
		// null);//产品详细
		//
		// relativeLayout.addView(produce);
		// relativeLayout.addView(produce1);
		//
		// produce1.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent intent = new
		// Intent(getSherlockActivity(),OrderDetailActivity.class);
		//
		// startActivity(intent);
		// }
		// });
		// //添加监听
		// produce.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent intent = new
		// Intent(getSherlockActivity(),OrderDetailActivity.class);
		//
		// startActivity(intent);
		listview.setOnItemClickListener(listItemClickListener);
		// }
		// });
		return view;
	}

	OnItemClickListener listItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//			ViewHolder viewHolder = (ViewHolder) view.getTag();
//			viewHolder.cb.toggle();// 把CheckBox的选中状态改为当前状态的反,gridview确保是单一选中
//			ExchangeAdapter.getIsSelected().put(position,
//					viewHolder.cb.isChecked());// 将CheckBox的选中状况记录下来
			
//			Intent  intent = new Intent(getSherlockActivity(), GoodsInfoActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putString("goodsId", CstmPayActivity.arrayListExchange.get(position).getUId());
//			intent.putExtras(bundle);
//			getSherlockActivity().startActivity(intent);
		}
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getSherlockActivity().unregisterReceiver(receiver);
	}
	
	
	public class InnerReciver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String  action = intent.getAction();
			if(Consts.EXCHANG_FREE.equals(action)){
				 adapter.initData();
				 adapter.notifyDataSetChanged();
			}
			
		}
		
	}
	// /**
	// * 用来展示数据的
	// */
	// private void getData(){
	// ArrayList<Order> mList = orderDataManage.getOrderArray(514492+"", "", "",
	// "", 0+"", 10+"");
	// for(int i=0;i<mList.size();i++){
	// Order mOrder = mList.get(i);
	// titleTextView.setText(mOrder.getStatus());
	// numberTextView.setText(mOrder.getOrderCode());
	// mLayout.addView(view);
	// }
	// }
}