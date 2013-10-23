package com.yidejia.app.mall.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.VoucherDataManage;

public class IntegeralFragment extends Fragment {
	private String hello;
	private String defaultHello = "default hello";
	private TextView jiFen;
	private VoucherDataManage voucherDataManage;//����
	private MyApplication myApplication;
	//ͨ������ģʽ����������
	public static IntegeralFragment newInstance(String s){
		IntegeralFragment waitFragment = new IntegeralFragment();
		Bundle bundle = new Bundle();
		bundle.putString("hello", s);
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
		voucherDataManage = new VoucherDataManage(getActivity());
		myApplication = (MyApplication) getActivity().getApplication();
		View viewCoupons = inflater.inflate(R.layout.my_card_voucher1_item, null);//�Ż�ȯ��ͼ
		View viewIntegeral =inflater.inflate(R.layout.coupons, null);//������ͼ
		jiFen = (TextView) viewIntegeral.findViewById(R.id.jiefen);
		jiFen.setText(voucherDataManage.getUserVoucher(myApplication.getUserId(), myApplication.getToken()));
		if(hello.equals("jifenquan"))
			return viewCoupons;
		else 
			return viewIntegeral;
			
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
