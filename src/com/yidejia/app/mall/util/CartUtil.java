package com.yidejia.app.mall.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.UserComment;
import com.yidejia.app.mall.view.GoCartActivity;

public class CartUtil{
	private Context context;
	private LayoutInflater inflater;
	private LinearLayout linearLayout;
	public static List<HashMap<String, Float>> list;// ������Ŀ��ֵ�Ǽ۸�
	private TextView mTextView;// ����������Ŀ
	private TextView sumTextView;// �ܵ�Ǯ��
	private CheckBox mBox;
	private CartsDataManage dataManage;
	private List<Object> mList;

	
//	private HashMap<String, Boolean> checkmMap;

	/**
	 * 
	 */
	public CartUtil() {

	}

	/**
	 * 
	 * @param context
	 */
	public CartUtil(Context context, LinearLayout linearLayout,
			TextView mTextView, TextView sumTextView, CheckBox box) {// ,View
																		// view
		this.inflater = LayoutInflater.from(context); // ,UserCommentDataManage

		this.linearLayout = linearLayout;
		this.context = context;
		this.mTextView = mTextView;
		this.sumTextView = sumTextView;
		this.mBox = box;
	}

	/**
	 * ��ʾȫ��������
	 */

	public void AllComment() {
		
		try {
			mList = new ArrayList<Object>();
			
			dataManage = new CartsDataManage();

			ArrayList<Cart> userList = dataManage.getCartsArray();

			list = new ArrayList<HashMap<String, Float>>();

			for (int i = 0; i < userList.size(); i++) {
				Log.i("info", userList.size()+"  +userList");
//			checkmMap = new HashMap<String, Boolean>();
				mBox.setChecked(true);
				
				final HashMap<String, Float> map = new HashMap<String, Float>();
				View view = LayoutInflater.from(context).inflate(
						R.layout.shopping_cart_item, null);
				view.setTag(i);
				ImageView headImageView = (ImageView) view
						.findViewById(R.id.shopping_cart_item__imageview1);
				TextView detailTextView = (TextView) view
						.findViewById(R.id.shopping_cart_item_text);
				final TextView priceTextView = (TextView) view
						.findViewById(R.id.shopping_cart_item_money);
				
				final CheckBox checkBox = (CheckBox) view
						.findViewById(R.id.shopping_cart_item_checkbox);
				
				checkBox.setChecked(true);
			
				
				ImageView subtract = (ImageView) view
						.findViewById(R.id.shopping_cart_item_subtract);// ��
				ImageView addImageView = (ImageView) view
						.findViewById(R.id.shopping_cart_item_add);// ��

				final TextView number = (TextView) view
						.findViewById(R.id.shopping_cart_item_edit_number);// ����ĸ���

				final Cart cart = userList.get(i);
				String path = cart.getImgUrl();
				Bitmap bm = BitmapFactory.decodeFile(path);
				if (bm != null) {
					headImageView.setImageBitmap(bm);
				} else {
					headImageView.setImageResource(R.drawable.ic_launcher);
				}
				detailTextView.setText(cart.getProductText());//��Ʒ�Ľ���
				detailTextView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(context,GoodsInfoActivity.class);
						
						Bundle bundle = new Bundle();
						bundle.putString("goodsId", cart.getUId());
						intent.putExtras(bundle);
						context.startActivity(intent);
						
						
						context.startActivity(intent);
					}
				});
				
				priceTextView.setText(cart.getPrice()+"");
			

				final Handler handler = new Handler() {
					public void handleMessage(Message msg) {
						if (msg.what == 123) {
							
//						map.put("check",checkBox.isChecked()==false?0:1);
							
							// Log.i("info", 123+"");
							map.put("count",
									Float.parseFloat(number.getText().toString()));
							// map.put(Integer.parseInt(number.getText().toString()),Integer.parseInt(priceTextView.getText().toString()));
							int count = 0;
							double sum = 0;
							float price = 0;
							int j = 0;
							for (int i = 0; i < list.size(); i++) {
								HashMap<String, Float> map = list.get(i);
								float ischeck = map.get("check");
								j +=ischeck;
								if(ischeck == 1){
								float count1 = map.get("count");
								count += map.get("count");
								price = map.get("price");
								sum += count1 * price;

								
								}
								else{
									mBox.setChecked(false);
								}
								
							}
//						Log.i("info", j+"+j");
							if(j == list.size()){
								
//							Log.i("info", list.size()+"+list.size");
								mBox.setChecked(true);
//							Log.i("info",mBox.isChecked()+"");
							}
							sumTextView.setText(sum + "");
							mTextView.setText(count + "");
//						 Log.i("info", list.toString());
						}
						else if(msg.what == 124){
							checkBox.setChecked(true);
							int count = 0;
							double sum = 0;
							float price = 0;
							for (int i = 0; i < list.size(); i++) {
								HashMap<String, Float> map = list.get(i);
								
//							map.put("check",1);
								Log.i("info", map+"");
								float ischeck = map.get("check");
								if(ischeck == 1){
									checkBox.setChecked(true);
								}else{
									checkBox.setChecked(false);
								}
								
			
								Log.i("info",checkBox.isChecked()+"+j");
								float count1 = map.get("count");
								count += map.get("count");
								price = map.get("price");
								sum += count1 * price;

							}
							sumTextView.setText(sum + "");
							mTextView.setText(count + "");
						}
					}

				};

				subtract.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int sum = Integer.parseInt(number.getText().toString());
						if (sum <= 0) {
							Toast.makeText(context, "�Ѿ�����С����ֵ��", Toast.LENGTH_LONG)
									.show();
						} else {
							sum--;
							number.setText(sum + "");
							mTextView.setText(Integer.parseInt(number.getText()
									.toString()) + "");
						}
						Message ms = new Message();
						ms.what = 123;
						handler.sendMessage(ms);
					}

				});
				addImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int sum = Integer.parseInt(number.getText().toString());
						if (sum >= 9999) {
							Toast.makeText(context, "����Ҫ�������Ĳ�Ʒ������ͷ���ϵ",
									Toast.LENGTH_LONG).show();
						} else {
							sum++;
							number.setText(sum + "");
							// mTextView.setText(Integer.parseInt(number.getText()
							// .toString())+"");
						}
						Message ms = new Message();
						ms.what = 123;
						handler.sendMessage(ms);
					}
				});
				// mTextView.setText(Integer.parseInt(number.getText()
				// .toString())+"");
				// Log.i("info", view.getTag()+"");
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(checkBox.isChecked()){
							map.put("check", (float) 1.0);
						}else{
							map.put("check", (float) 0.0);
						}
						Message ms = new Message();
						ms.what = 123;
						handler.sendMessage(ms);
					}
				});
				mList.add(checkBox);
				map.put("check",(float) (checkBox.isChecked() == false?0:1));
				map.put("count", (float) Integer.parseInt(number.getText().toString()));
				map.put("price",
						Float.parseFloat(priceTextView.getText().toString()));
				list.add(map);
				//���ü���
				mBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							Log.i("info",mBox.isChecked()+"");
							Message msg = new Message();
							msg.what = 124;
							handler.sendMessage(msg);
							for(int i=0;i<mList.size();i++){
								CheckBox checkBox = (CheckBox) mList.get(i);
								checkBox.setChecked(true);
								
							}
						}
						
					}
				});
				linearLayout.addView(view);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();

		}
		
 	}

	

	
}
