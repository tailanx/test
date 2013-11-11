package com.yidejia.app.mall.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.VoucherDataManage;
import com.yidejia.app.mall.model.Specials;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.CstmPayActivity;

public class ExchangeAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Specials> mlist;
	private static HashMap<Integer, Boolean> isSelected;// =״̬
	public static List<HashMap<String, Float>> mlist1;
	public static List<HashMap<String, Object>> mlist2;
	private LayoutInflater inflater;
	private VoucherDataManage voucherDataManage;// 积分的信息
	private double userVoucher;// 用户积分
	private MyApplication myApplication;
	private InnerReciver receiver;

	public ExchangeAdapter(ArrayList<Specials> mList, Activity context) {
		
		this.activity = context;
		this.mlist = mList;
		this.inflater = LayoutInflater.from(context);
		receiver = new InnerReciver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Consts.EXCHANG_FREE);
		context.registerReceiver(receiver, filter);
		
		voucherDataManage = new VoucherDataManage(context);
		myApplication = (MyApplication) activity.getApplication();
		
		Log.e("voucher",CstmPayActivity.voucherString1+"");
		userVoucher = Double.parseDouble(CstmPayActivity.voucherString1);//用户的积分
		
//		userVoucher = Double.parseDouble(voucherDataManage.getUserVoucher(
//				myApplication.getUserId(), myApplication.getToken()));
		
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
		isSelected = new HashMap<Integer, Boolean>();
		mlist1 = new ArrayList<HashMap<String, Float>>();
		mlist2 = new ArrayList<HashMap<String, Object>>();
		initData();
		
		
	}

	public ExchangeAdapter() {
		// TODO Auto-generated constructor stub
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		ExchangeAdapter.isSelected = isSelected;
	}

	/**
	 * ��ʼ��checkbox��ѡ��״̬
	 */
	private void initData() {
		for (int i = 0; i < mlist.size(); i++) {
			// Log.i("info", mlist.size() + "size");
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Specials getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return Long.parseLong(mlist.get(arg0).getUId());
	}

	static final List<String> displayedImages = Collections
			.synchronizedList(new LinkedList<String>());

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	/**
	 * ������ͼ
	 */
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ����ͼƬ
	int i = 0;
	private  Handler handler;
	@Override
	public View getView(final int postion, View covertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final HashMap<String, Float> map = new HashMap<String, Float>();
		final HashMap<String, Object> map1 = new HashMap<String, Object>();
		final ViewHolder holder;
		if (covertView == null) {
			covertView = inflater.inflate(R.layout.exchange_produce_item, null);
			holder = new ViewHolder();
			holder.iv = (ImageView) covertView
					.findViewById(R.id.exchange_produce_item__imageview1);
			holder.tvContent = (TextView) covertView
					.findViewById(R.id.exchange_produce_item_text);
			holder.tvPrice = (TextView) covertView
					.findViewById(R.id.exchange_produce_item_money);
			holder.subtract = (ImageView) covertView
					.findViewById(R.id.exchange_produce_item_subtract);
			holder.add = (ImageView) covertView
					.findViewById(R.id.exchange_produce_item_add);
			holder.count = (TextView) covertView
					.findViewById(R.id.exchange_produce_item_edit_number);
			holder.cb = (CheckBox) covertView
					.findViewById(R.id.exchange_produce_item_checkbox);
			covertView.setTag(holder);
		} else {
			holder = (ViewHolder) covertView.getTag();
		}
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 113) {
					map.put("count",
							Float.parseFloat(holder.count.getText().toString()));
					map1.put("count1",
							Float.parseFloat(holder.count.getText().toString()));
				}
				if(msg.what == 114){
					Log.i("info", "    sum1");
					holder.cb.setChecked(false);
				}
				
			};
		};
		Specials s = mlist.get(postion);
		imageLoader.displayImage(s.getImgUrl(), holder.iv, options,
				animateFirstListener);
		holder.tvContent.setText(s.getBrief());
		holder.tvPrice.setText(s.getScores());
		holder.cb.setChecked(getIsSelected().get(postion));
		holder.count.setText(1 + "");

		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					isSelected.put(postion, true);
					map.put("isCheck", (float) 0);
					map1.put("isCheck1", (float) 0);
					// map.put("isCheck", (float) 0 );
				} else {
					isSelected.put(postion, false);
					map.put("isCheck", (float) 1);
					map1.put("isCheck1", (float) 1);
					holder.cb.setChecked(false);
					
					map1.put("price", 0);
				}
//				Message ms = new Message();
//				ms.what = 115;
//				handler.sendMessage(ms);
			}

		});
		holder.add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int sum = Integer.parseInt(holder.count.getText().toString());
				if (sum >= 9999) {
					Toast.makeText(
							activity,
							activity.getResources().getString(
									R.string.price_error), Toast.LENGTH_LONG)
							.show();

				} else {
					sum++;
					holder.count.setText(sum + "");
				}
				Message ms = new Message();
				ms.what = 113;
				handler.sendMessage(ms);

			}
		});
		holder.subtract.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int sum = Integer.parseInt(holder.count.getText().toString());
				if (sum <= 1) {
					Toast.makeText(activity,
							activity.getResources().getString(R.string.mix),

							Toast.LENGTH_LONG).show();
				} else {
					sum--;
					holder.count.setText(sum + "");

				}
				Message ms = new Message();
				ms.what = 113;
				handler.sendMessage(ms);
			}
		});
		map1.put("isCheck1", (float) (holder.cb.isChecked() == true ? 0 : 1));
		map1.put("cart", s);
		map1.put("price1", Float.parseFloat(s.getScores()));
		map1.put("count1", Float.parseFloat(holder.count.getText().toString()));

		map.put("price", Float.parseFloat(s.getScores()));
		map.put("count", Float.parseFloat(holder.count.getText().toString()));
		map.put("isCheck", (float) (holder.cb.isChecked() == true ? 0 : 1));

		i++;
		mlist1.add(map);
		mlist2.add(map1);
		return covertView;
	}

	static class ViewHolder {
		CheckBox cb;
		private ImageView iv;
		private TextView tvContent;
		private TextView tvPrice;
		private ImageView subtract;
		private ImageView add;
		private TextView count;

	}

	public class InnerReciver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String  action = intent.getAction();
			if(Consts.EXCHANG_FREE.equals(action)){
//				 Log.i("info", action+"    sum1");
				 Message ms = new Message();
				 ms.what = 114;
				 handler.sendMessage(ms);
			}
		}
		
	}
	
}
