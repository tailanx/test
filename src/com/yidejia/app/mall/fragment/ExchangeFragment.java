package com.yidejia.app.mall.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.PreferentialDataManage;
import com.yidejia.app.mall.fragment.ExchangeAdapter.ViewHolder;
import com.yidejia.app.mall.model.Specials;

public class ExchangeFragment extends SherlockFragment {
//	private TextView titleTextView;//������״̬
//	private TextView numberTextView;//�����ı��
//	private TextView sumPrice;//�������ܼ۸�
//	private TextView countTextView;//����������Ŀ
//	private LinearLayout mLayout;//���Ĳ���
//	private View view;
//	private OrderDataManage orderDataManage ;//������ȡ��������
	
	private String hello;
	private String defaultHello = "default hello";
	private ListView listview;
	private PreferentialDataManage dataManage ;
	private ExchangeAdapter adapter;
	/**
	 * ʵ��������
	 * @param view
	 */
	private void setupShow(View view){

	}


	//ͨ������ģʽ����������
	public static ExchangeFragment newInstance(String s){
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
		//��ȡ�洢�Ĳ���
		Bundle args = getArguments();
		hello = args!=null?args.getString("hello"):defaultHello;
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		dataManage  = new PreferentialDataManage(getSherlockActivity());
		View view= inflater.inflate(R.layout.exchange_produce, null);//��ȡ��ͼ����
		listview = (ListView) view.findViewById(R.id.exchange_shopping_listview);
		ArrayList<Specials> mArrayList = dataManage.getScoreGoods();
		adapter = new ExchangeAdapter(mArrayList, getActivity());
		listview.setAdapter(adapter);
		//		LinearLayout relativeLayout = (LinearLayout)view.findViewById(R.id.shopping_cart_relative2);//��ȡ����

//		AllOrderUtil allOrderUtil = new AllOrderUtil(getSherlockActivity(), relativeLayout);
//		Log.i("info", allOrderUtil+"");
//		allOrderUtil.loadView();
//		setupShow(view);
//		getData();
				
//		View produce = inflater.inflate(R.layout.all_order_item_produce, null);//��Ʒ��ϸ
//		View produce1 = inflater.inflate(R.layout.all_order_item_produce, null);//��Ʒ��ϸ
//		
//		relativeLayout.addView(produce);
//		relativeLayout.addView(produce1);
//		
//		produce1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),OrderDetailActivity.class);
//				
//				startActivity(intent);
//			}
//		});
//		//��Ӽ���
//		produce.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(getSherlockActivity(),OrderDetailActivity.class);
//				
//				startActivity(intent);
		listview.setOnItemClickListener(listItemClickListener);  
//			}
//		});
		return view;
	}
    OnItemClickListener listItemClickListener=new OnItemClickListener() {  

        @Override  
        public void onItemClick(AdapterView<?> parent, View view, int position,  
                long id) {  
            //// ȡ��ViewHolder����������ʡȥ��ͨ������findViewByIdȥʵ����������Ҫ��cbʵ���Ĳ���    
            ViewHolder viewHolder=(ViewHolder)view.getTag();                          
            viewHolder.cb.toggle();// ��CheckBox��ѡ��״̬��Ϊ��ǰ״̬�ķ�,gridviewȷ���ǵ�һѡ��  
           ExchangeAdapter.getIsSelected().put(position, viewHolder.cb.isChecked());//��CheckBox��ѡ��״����¼���� </span>  
        }  
    };
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
//	/**
//	 * ����չʾ���ݵ�
//	 */
//	private void getData(){
//		ArrayList<Order> mList = orderDataManage.getOrderArray(514492+"", "", "", "", 0+"", 10+"");
//		for(int i=0;i<mList.size();i++){
//			Order mOrder = mList.get(i);
//			titleTextView.setText(mOrder.getStatus());
//			numberTextView.setText(mOrder.getOrderCode());
//			mLayout.addView(view);
//		}
//	}
}
