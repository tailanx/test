package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.AddressDataManage;
import com.yidejia.app.mall.model.Addresses;
import com.yidejia.app.mall.util.CartUtil;

public class GoCartActivity extends SherlockActivity {// implements
														// OnClickListener

	private TextView sumTextView;// �ܵ�Ǯ��
	private TextView counTextView;// �ܵ�����
	private CheckBox mBox;// ѡ���
	private Button mbutton;// ȥ����
	private CartUtil cartUtil;
	private AddressDataManage addressManage;// ��ַ�������
	private PullToRefreshScrollView mPullToRefreshScrollView;// ����ˢ��
	private ImageView mImageView;// ����
	private TextView mTextView;// title
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);
		myApplication = (MyApplication) getApplication();
		addressManage = new AddressDataManage(GoCartActivity.this);

		mBox = (CheckBox) findViewById(R.id.shopping_cart_checkbox);// ѡ���

		sumTextView = (TextView) findViewById(R.id.shopping_cart_sum_money);// �ܵ�Ǯ��

		counTextView = (TextView) findViewById(R.id.shopping_cart_sum_number);// �ܵ�����

		mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.shopping_cart_item_goods_scrollView);
		String label = "�ϴθ�����"
				+ DateUtils.formatDateTime(GoCartActivity.this,
						System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_SHOW_TIME);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
				label);
		mPullToRefreshScrollView.setOnRefreshListener(listener);

		LinearLayout layout = (LinearLayout) findViewById(R.id.shopping_cart_relative2);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		GoCartActivity.this.getSupportActionBar().setCustomView(
				R.layout.actionbar_common);
		cartUtil = new CartUtil(GoCartActivity.this, layout, counTextView,
				sumTextView, mBox);
		cartUtil.AllComment();
		mbutton = (Button) findViewById(R.id.actionbar_right);
		mTextView = (TextView) findViewById(R.id.actionbar_title);
		mImageView = (ImageView) findViewById(R.id.actionbar_left);
		mTextView.setText("���ﳵ");

		try {
			mImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					GoCartActivity.this.finish();
				}
			});
			mbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!((MyApplication) GoCartActivity.this.getApplication())
							.getIsLogin()) {
						Toast.makeText(GoCartActivity.this, "�㻹δ��½�����ȵ�½",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(GoCartActivity.this,
								LoginActivity.class);
						startActivity(intent);
						return;
					}
					Intent intent = new Intent(GoCartActivity.this,
							CstmPayActivity.class);
					float sum = Float.parseFloat(sumTextView.getText()
							.toString());

					if (sum > 0) {
						Bundle bundle = new Bundle();

						bundle.putString("price", sum + "");
						intent.putExtras(bundle);
						GoCartActivity.this.startActivity(intent);
					} else {
						Toast.makeText(GoCartActivity.this, "�㻹δ�����κ���Ʒ",
								Toast.LENGTH_LONG).show();
					}
				}
//
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(GoCartActivity.this, "���粻������", Toast.LENGTH_SHORT)
					.show();
		}
		//
		//
	}

	private OnRefreshListener<ScrollView> listener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			// TODO Auto-generated method stub
			String label = "�ϴθ�����"
					+ DateUtils.formatDateTime(GoCartActivity.this,
							System.currentTimeMillis(),
							DateUtils.FORMAT_ABBREV_ALL
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_SHOW_TIME);
			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			mPullToRefreshScrollView.onRefreshComplete();
		}
	};
	// try {
	// TODO Auto-generated method stub
	// requestWindowFeature(Window.FEATURE_NO_TITLE);
	// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

	// mbutton = (Button) findViewById(R.id.go_cart_go_pay);
	// mbutton.setOnClickListener(this);
	//
	// mBox = (CheckBox) findViewById(R.id.go_cart_checkbox);// ѡ���
	//
	// sumTextView = (TextView)findViewById(R.id.shopping_cart_sum_money);//
	// �ܵ�Ǯ��
	//
	// counTextView = (TextView)findViewById(R.id.shopping_cart_sum_number);//
	// �ܵ�����
	//
	// LinearLayout layout = (LinearLayout)
	// findViewById(R.id.go_cart_relative1);
	// cartUtil = new CartUtil(this, layout, counTextView, sumTextView, mBox);
	// cartUtil.AllComment();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Toast.makeText(GoCartActivity.this, "���粻������", Toast.LENGTH_SHORT).show();
	//
	// }
	//
	// }
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.go_cart_go_pay:
	// Intent intent = new Intent(GoCartActivity.this,PayActivity.class);
	// GoCartActivity.this.startActivity(intent);
	// break;
	//
	// }
	//
	// }
	//
	// Addresses address = null;

	// /**
	// *
	// * @return ����һ����ַ
	// */
	// private void getAddresses() {
	//
	// String userId = ((MyApplication) getApplication()).getUserId();
	// ArrayList<Addresses> mAddresses = addressManage.getAddressesArray(
	// userId, 0, 5);
	// if (mAddresses.size() == 0) {
	// Intent intent = new Intent(GoCartActivity.this,
	// NewAddressActivity.class);
	// GoCartActivity.this.startActivity(intent);
	// GoCartActivity.this.finish();
	// // Log.i("info", "nihao");
	//
	// } else {
	// address = mAddresses.remove(0);
	// // Log.i("info", address + "address");
	// }
	//
	// }

}
