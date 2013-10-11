package com.yidejia.app.mall.util;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.UserComment;

import com.yidejia.app.mall.view.GoCartActivity;

public class CartUtil {
	private Context context;
	private LayoutInflater inflater;
	private LinearLayout linearLayout;
	public static List<HashMap<String, Float>> list;// 键是数目，值是价格
	private TextView mTextView;// 传过来的数目
	private TextView sumTextView;// 总的钱数
	private CheckBox mBox;
	private CartsDataManage dataManage;
	private List<Object> mList;

	private String items[] = { "删除", "查看商品详情", "收藏" };

	
	private TextView number;

//	private void setupShow() {
//		Builder builder = new Builder(context);
//		dialog = builder
//				.setIcon(android.R.drawable.alert_dark_frame)
//				.setTitle("购物车操作")
//				.setSingleChoiceItems(items, 0,
//						new android.content.DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								switch (which) {
//								case 0:
//									dataManage = new CartsDataManage();
//									boolean isDel = dataManage.delCart(cart
//											.getUId());
//
//									dialog.dismiss();
//									break;
//
//								case 1:
//									Intent intent = new Intent(context,
//											GoodsInfoActivity.class);
//									Bundle bundle = new Bundle();
//									bundle.putSerializable("goodsId",
//											cart.getUId());
//									intent.putExtras(bundle);
//									context.startActivity(intent);
//									dialog.dismiss();
//									break;
//								case 2:
//									Toast.makeText(context, "收藏成功",
//											Toast.LENGTH_LONG).show();
//									dialog.dismiss();
//									break;
//								}
//
//							}
//						}).create();
//	}

	// private HashMap<String, Boolean> checkmMap;

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
		initDisplayImageOption();
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
	private void initDisplayImageOption() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// 加载图片
	/**
	 * 显示全部的数据
	 */

	public void AllComment() {

		try {

			mList = new ArrayList<Object>();

			dataManage = new CartsDataManage();

			ArrayList<Cart> userList = dataManage.getCartsArray();

			list = new ArrayList<HashMap<String, Float>>();
			int a = 0;
			float b = 0;
			for (int i = 0; i < userList.size(); i++) {
				// Log.i("info", userList.size()+"  +userList");
				// checkmMap = new HashMap<String, Boolean>();
				
				final Cart cart = userList.get(i);
				mBox.setChecked(true);

				final HashMap<String, Float> map = new HashMap<String, Float>();
				 final View view = LayoutInflater.from(context).inflate(
						R.layout.shopping_cart_item, null);
				view.setTag(i);
				final RelativeLayout layout = (RelativeLayout) view
						.findViewById(R.id.rela);
			
				ImageView headImageView = (ImageView) view
						.findViewById(R.id.shopping_cart_item__imageview1);
				TextView detailTextView = (TextView) view
						.findViewById(R.id.shopping_cart_item_text);
				final TextView priceTextView = (TextView) view
						.findViewById(R.id.shopping_cart_item_money);

				final CheckBox checkBox = (CheckBox) view
						.findViewById(R.id.shopping_cart_item_checkbox);

				checkBox.setChecked(true);

				final ImageView subtract = (ImageView) view
						.findViewById(R.id.shopping_cart_item_subtract);// 减
				final ImageView addImageView = (ImageView) view
						.findViewById(R.id.shopping_cart_item_add);// 加

				final TextView number = (TextView) view
						.findViewById(R.id.shopping_cart_item_edit_number);// 输入的个数
				number.setText(cart.getAmount()+"");
				a += cart.getAmount();
				b += cart.getPrice()*cart.getAmount();
				
				mTextView.setText(a+"");
				sumTextView.setText(b+"");
				String path = cart.getImgUrl();
				imageLoader.displayImage(path, headImageView, options,
						animateFirstListener);
//				Bitmap bm = BitmapFactory.decodeFile(path);
//				if (bm != null) {
//					headImageView.setImageBitmap(bm);
//				} else {
//					headImageView.setImageResource(R.drawable.ic_launcher);
//				}
//				
				detailTextView.setText(cart.getProductText());// 商品的介绍
				layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent(context,
								GoodsInfoActivity.class);

						Bundle bundle = new Bundle();
						bundle.putString("goodsId", cart.getUId());
						intent.putExtras(bundle);
						context.startActivity(intent);

						// context.startActivity(intent);
					}
				});

				// 设置子控件优先获取焦点，详细看setDescendantFocusability
				layout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
				priceTextView.setText(cart.getPrice() + "");

				final Handler handler = new Handler() {
					public void handleMessage(Message msg) {
						if (msg.what == 123 ) {
							
							// map.put("check",checkBox.isChecked()==false?0:1);

							// Log.i("info", 123+"");
							map.put("count", Float.parseFloat(number.getText()
									.toString()));
							// map.put(Integer.parseInt(number.getText().toString()),Integer.parseInt(priceTextView.getText().toString()));
							dataManage.mdfCartAmount(cart.getUId(),Integer.parseInt(number.getText()
									.toString()));
							int count = 0;
							double sum = 0;
							float price = 0;
							int j = 0;
							for (int i = 0; i < list.size(); i++) {
								HashMap<String, Float> map = list.get(i);
								float ischeck = map.get("check");
								j += ischeck;
								if (ischeck == 1) {
									float count1 = map.get("count");
									count += map.get("count");
									price = map.get("price");
									sum += count1 * price;
									

								} else {
									mBox.setChecked(false);
								}

							}
							// Log.i("info", j+"+j");
							if (j == list.size()) {

								// Log.i("info", list.size()+"+list.size");
								mBox.setChecked(true);
								// Log.i("info",mBox.isChecked()+"");
							}
							sumTextView.setText(sum + "");
							Intent intent = new Intent(Consts.UPDATE_CHANGE);
							context.sendBroadcast(intent);
							mTextView.setText(count + "");
							// Log.i("info", list.toString());
						} else if (msg.what == 124) {
							checkBox.setChecked(true);
							int count = 0;
							double sum = 0;
							float price = 0;
							for (int i = 0; i < list.size(); i++) {
								HashMap<String, Float> map = list.get(i);

								// map.put("check",1);
								Log.i("info", map + "");
								float ischeck = map.get("check");
								if (ischeck == 1) {
									checkBox.setChecked(true);
								} else {
									checkBox.setChecked(false);
								}

								Log.i("info", checkBox.isChecked() + "+j");
								float count1 = map.get("count");
								count += map.get("count");
								price = map.get("price");
								sum += count1 * price;

							}
							sumTextView.setText(sum + "");
							Intent intent = new Intent(Consts.UPDATE_CHANGE);
							context.sendBroadcast(intent);
							mTextView.setText(count + "");
						} 
						else if (msg.what == 125) {
							linearLayout.removeView(layout);
							view.invalidate();
							map.put("count", (float) 0.0);
							dataManage.mdfCartAmount(cart.getUId(),Integer.parseInt(number.getText()
									.toString()));
							int count = 0;
							double sum = 0;
							float price = 0;
							int j = 0;
							for (int i = 0; i < list.size(); i++) {
								HashMap<String, Float> map = list.get(i);
								float ischeck = map.get("check");
								j += ischeck;
								if (ischeck == 1) {
									float count1 = map.get("count");
									count += map.get("count");
									price = map.get("price");
									sum += count1 * price;
									

								} else {
									mBox.setChecked(false);
								}

							}
							// Log.i("info", j+"+j");
							if (j == list.size()) {

								// Log.i("info", list.size()+"+list.size");
								mBox.setChecked(true);
								// Log.i("info",mBox.isChecked()+"");
							}
							sumTextView.setText(sum + "");
							Intent intent = new Intent(Consts.UPDATE_CHANGE);
							context.sendBroadcast(intent);
							mTextView.setText(count + "");

						}
					}

				};
				Builder builder = new Builder(context);
				final AlertDialog dialog = builder
						.setIcon(android.R.drawable.alert_dark_frame)
						.setTitle("购物车操作")
						.setSingleChoiceItems(items, 0,
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											dataManage = new CartsDataManage();
											boolean isDel = dataManage.delCart(cart
													.getUId());
											dialog.dismiss();
											Message ms = new Message();
											handler.sendEmptyMessage(125);
											
										Intent intent1 = new Intent("com.yidejia.UPDATE");
											context.sendBroadcast(intent1);
			
											break;

										case 1:
											Intent intent = new Intent(context,
													GoodsInfoActivity.class);
											Bundle bundle = new Bundle();
											bundle.putSerializable("goodsId",
													cart.getUId());
											intent.putExtras(bundle);
											context.startActivity(intent);
											dialog.dismiss();
											break;
										case 2:
											Toast.makeText(context, "收藏成功",
													Toast.LENGTH_LONG).show();
											dialog.dismiss();
											break;
										}

									}
								}).create();
				subtract.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
					
						int sum = Integer.parseInt(number.getText().toString());
						if (sum <= 0) {
							Toast.makeText(context, "已经是最小的数值了",
									Toast.LENGTH_LONG).show();
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
							Toast.makeText(context, "您想要购买更多的产品，请与客服联系",
									Toast.LENGTH_LONG).show();
						} else {
							sum++;
							number.setText(sum + "");
							// mTextView.setText(Integer.parseInt(number.getText()
							// .toString())+"");
						}
//						dataManage.mdfCartAmount(cart.getUId(),sum);
						Message ms = new Message();
						ms.what = 123;
						handler.sendMessage(ms);
					}
				});
				layout.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						dialog.show();				
						return true;
					}
				});
				// mTextView.setText(Integer.parseInt(number.getText()
				// .toString())+"");
				// Log.i("info", view.getTag()+"");
				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (checkBox.isChecked()) {
							map.put("check", (float) 1.0);
						} else {
							map.put("check", (float) 0.0);
						}
						Message ms = new Message();
						ms.what = 123;
						handler.sendMessage(ms);
					}
				});
				mList.add(checkBox);
				map.put("check",
						(float) (checkBox.isChecked() == false ? 0 : 1));
				map.put("count",
						(float) Integer.parseInt(number.getText().toString()));
				map.put("price",
						Float.parseFloat(priceTextView.getText().toString()));
				list.add(map);
				// 设置监听
				mBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Log.i("info", mBox.isChecked() + "");
							Message msg = new Message();
							msg.what = 124;
							handler.sendMessage(msg);
							for (int i = 0; i < mList.size(); i++) {
								CheckBox checkBox = (CheckBox) mList.get(i);
								checkBox.setChecked(true);

							}
						}else{
							
							for (int i = 0; i < mList.size(); i++) {
								CheckBox checkBox = (CheckBox) mList.get(i);
								checkBox.setChecked(false);
						}
						}

					}
				});
				linearLayout.addView(view);
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();

		}

	}

}

//package com.yidejia.app.mall.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.AlteredCharSequence;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnLongClickListener;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.yidejia.app.mall.GoodsInfoActivity;
//import com.yidejia.app.mall.R;
//import com.yidejia.app.mall.datamanage.CartsDataManage;
//import com.yidejia.app.mall.model.Cart;
//import com.yidejia.app.mall.model.UserComment;
//import com.yidejia.app.mall.view.GoCartActivity;
//
//public class CartUtil {
//	private Context context;
//	private LayoutInflater inflater;
//	private LinearLayout linearLayout;
//	public static List<HashMap<String, Float>> list;// 键是数目，值是价格
//	private TextView mTextView;// 传过来的数目
//	private TextView sumTextView;// 总的钱数
//	private CheckBox mBox;
//	private CartsDataManage dataManage;
//	private List<Object> mList;
//
//	private String items[] = { "删除", "查看商品详情", "收藏" };
//
//	private TextView number;
//
//	// private void setupShow() {
//	// Builder builder = new Builder(context);
//	// dialog = builder
//	// .setIcon(android.R.drawable.alert_dark_frame)
//	// .setTitle("购物车操作")
//	// .setSingleChoiceItems(items, 0,
//	// new android.content.DialogInterface.OnClickListener() {
//	//
//	// @Override
//	// public void onClick(DialogInterface dialog,
//	// int which) {
//	// switch (which) {
//	// case 0:
//	// dataManage = new CartsDataManage();
//	// boolean isDel = dataManage.delCart(cart
//	// .getUId());
//	//
//	// dialog.dismiss();
//	// break;
//	//
//	// case 1:
//	// Intent intent = new Intent(context,
//	// GoodsInfoActivity.class);
//	// Bundle bundle = new Bundle();
//	// bundle.putSerializable("goodsId",
//	// cart.getUId());
//	// intent.putExtras(bundle);
//	// context.startActivity(intent);
//	// dialog.dismiss();
//	// break;
//	// case 2:
//	// Toast.makeText(context, "收藏成功",
//	// Toast.LENGTH_LONG).show();
//	// dialog.dismiss();
//	// break;
//	// }
//	//
//	// }
//	// }).create();
//	// }
//
//	// private HashMap<String, Boolean> checkmMap;
//
//	/**
//	 * 
//	 */
//	public CartUtil() {
//
//	}
//
//	/**
//	 * 
//	 * @param context
//	 */
//	public CartUtil(Context context, LinearLayout linearLayout,
//			TextView mTextView, TextView sumTextView, CheckBox box) {// ,View
//																		// view
//		this.inflater = LayoutInflater.from(context); // ,UserCommentDataManage
//
//		this.linearLayout = linearLayout;
//		this.context = context;
//		this.mTextView = mTextView;
//		this.sumTextView = sumTextView;
//		this.mBox = box;
//
//	}
//
//	/**
//	 * 显示全部的数据
//	 */
//
//	public void AllComment() {
//
//		try {
//
//			mList = new ArrayList<Object>();
//
//			dataManage = new CartsDataManage();
//
//			ArrayList<Cart> userList = dataManage.getCartsArray();
//
//			list = new ArrayList<HashMap<String, Float>>();
//			int a = 0;
//			float b = 0;
//			for (int i = 0; i < userList.size(); i++) {
//				// Log.i("info", userList.size()+"  +userList");
//				// checkmMap = new HashMap<String, Boolean>();
//
//				final Cart cart = userList.get(i);
//				mBox.setChecked(true);
//
//				final HashMap<String, Float> map = new HashMap<String, Float>();
//				final View view = LayoutInflater.from(context).inflate(
//						R.layout.shopping_cart_item, null);
//				view.setTag(i);
//				final RelativeLayout layout = (RelativeLayout) view
//						.findViewById(R.id.rela);
//
//				ImageView headImageView = (ImageView) view
//						.findViewById(R.id.shopping_cart_item__imageview1);
//				TextView detailTextView = (TextView) view
//						.findViewById(R.id.shopping_cart_item_text);
//				final TextView priceTextView = (TextView) view
//						.findViewById(R.id.shopping_cart_item_money);
//
//				final CheckBox checkBox = (CheckBox) view
//						.findViewById(R.id.shopping_cart_item_checkbox);
//
//				checkBox.setChecked(true);
//
//				final ImageView subtract = (ImageView) view
//						.findViewById(R.id.shopping_cart_item_subtract);// 减
//				final ImageView addImageView = (ImageView) view
//						.findViewById(R.id.shopping_cart_item_add);// 加
//
//				final TextView number = (TextView) view
//						.findViewById(R.id.shopping_cart_item_edit_number);// 输入的个数
//				number.setText(cart.getAmount() + "");
//				a += cart.getAmount();
//				b += cart.getPrice() * cart.getAmount();
//
//				mTextView.setText(a + "");
//				sumTextView.setText(b + "");
//
//				String path = cart.getImgUrl();
//				Bitmap bm = BitmapFactory.decodeFile(path);
//				if (bm != null) {
//					headImageView.setImageBitmap(bm);
//				} else {
//					headImageView.setImageResource(R.drawable.ic_launcher);
//				}
//
//				detailTextView.setText(cart.getProductText());// 商品的介绍
//				layout.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//
//						Intent intent = new Intent(context,
//								GoodsInfoActivity.class);
//
//						Bundle bundle = new Bundle();
//						bundle.putString("goodsId", cart.getUId());
//						intent.putExtras(bundle);
//						context.startActivity(intent);
//
//						// context.startActivity(intent);
//					}
//				});
//
//				// 设置子控件优先获取焦点，详细看setDescendantFocusability
//				layout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
//				priceTextView.setText(cart.getPrice() + "");
//
//				final Handler handler = new Handler() {
//					public void handleMessage(Message msg) {
//						if (msg.what == 123) {
//
//							// map.put("check",checkBox.isChecked()==false?0:1);
//
//							// Log.i("info", 123+"");
//							map.put("count", Float.parseFloat(number.getText()
//									.toString()));
//							// map.put(Integer.parseInt(number.getText().toString()),Integer.parseInt(priceTextView.getText().toString()));
//							dataManage.mdfCartAmount(cart.getUId(), Integer
//									.parseInt(number.getText().toString()));
//							int count = 0;
//							double sum = 0;
//							float price = 0;
//							int j = 0;
//							for (int i = 0; i < list.size(); i++) {
//								HashMap<String, Float> map = list.get(i);
//								float ischeck = map.get("check");
//								j += ischeck;
//								if (ischeck == 1) {
//									float count1 = map.get("count");
//									count += map.get("count");
//									price = map.get("price");
//									sum += count1 * price;
//
//								} else {
//									mBox.setChecked(false);
//								}
//
//							}
//							// Log.i("info", j+"+j");
//							if (j == list.size()) {
//
//								// Log.i("info", list.size()+"+list.size");
//								mBox.setChecked(true);
//								// Log.i("info",mBox.isChecked()+"");
//							}
//							sumTextView.setText(sum + "");
//							Intent intent = new Intent(Consts.UPDATE_CHANGE);
//							context.sendBroadcast(intent);
//							mTextView.setText(count + "");
//							// Log.i("info", list.toString());
//						} else if (msg.what == 124) {
//							checkBox.setChecked(true);
//							int count = 0;
//							double sum = 0;
//							float price = 0;
//							for (int i = 0; i < list.size(); i++) {
//								HashMap<String, Float> map = list.get(i);
//
//								// map.put("check",1);
//								Log.i("info", map + "");
//								float ischeck = map.get("check");
//								if (ischeck == 1) {
//									checkBox.setChecked(true);
//								} else {
//									checkBox.setChecked(false);
//								}
//
//								Log.i("info", checkBox.isChecked() + "+j");
//								float count1 = map.get("count");
//								count += map.get("count");
//								price = map.get("price");
//								sum += count1 * price;
//
//							}
//							sumTextView.setText(sum + "");
//							Intent intent = new Intent(Consts.UPDATE_CHANGE);
//							context.sendBroadcast(intent);
//							mTextView.setText(count + "");
//						} else if (msg.what == 125) {
//							linearLayout.removeView(layout);
//							view.invalidate();
//							map.put("count", (float) 0.0);
//							dataManage.mdfCartAmount(cart.getUId(), Integer
//									.parseInt(number.getText().toString()));
//							int count = 0;
//							double sum = 0;
//							float price = 0;
//							int j = 0;
//							for (int i = 0; i < list.size(); i++) {
//								HashMap<String, Float> map = list.get(i);
//								float ischeck = map.get("check");
//								j += ischeck;
//								if (ischeck == 1) {
//									float count1 = map.get("count");
//									count += map.get("count");
//									price = map.get("price");
//									sum += count1 * price;
//
//								} else {
//									mBox.setChecked(false);
//								}
//
//							}
//							// Log.i("info", j+"+j");
//							if (j == list.size()) {
//
//								// Log.i("info", list.size()+"+list.size");
//								mBox.setChecked(true);
//								// Log.i("info",mBox.isChecked()+"");
//							}
//							sumTextView.setText(sum + "");
//							Intent intent = new Intent(Consts.UPDATE_CHANGE);
//							context.sendBroadcast(intent);
//							mTextView.setText(count + "");
//
//						}
//					}
//
//				};
//				Builder builder = new Builder(context);
//				final AlertDialog dialog = builder
//						.setIcon(android.R.drawable.alert_dark_frame)
//						.setTitle("购物车操作")
//						.setSingleChoiceItems(
//								items,
//								0,
//								new android.content.DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										switch (which) {
//										case 0:
//											dataManage = new CartsDataManage();
//											boolean isDel = dataManage
//													.delCart(cart.getUId());
//											dialog.dismiss();
//											Message ms = new Message();
//											handler.sendEmptyMessage(125);
//
//											Intent intent1 = new Intent(
//													"com.yidejia.UPDATE");
//											context.sendBroadcast(intent1);
//
//											break;
//
//										case 1:
//											Intent intent = new Intent(context,
//													GoodsInfoActivity.class);
//											Bundle bundle = new Bundle();
//											bundle.putSerializable("goodsId",
//													cart.getUId());
//											intent.putExtras(bundle);
//											context.startActivity(intent);
//											dialog.dismiss();
//											break;
//										case 2:
//											Toast.makeText(context, "收藏成功",
//													Toast.LENGTH_LONG).show();
//											dialog.dismiss();
//											break;
//										}
//
//									}
//								}).create();
//				subtract.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//
//						int sum = Integer.parseInt(number.getText().toString());
//						if (sum <= 0) {
//							Toast.makeText(context, "已经是最小的数值了",
//									Toast.LENGTH_LONG).show();
//						} else {
//							sum--;
//							number.setText(sum + "");
//							mTextView.setText(Integer.parseInt(number.getText()
//									.toString()) + "");
//						}
//
//						Message ms = new Message();
//						ms.what = 123;
//						handler.sendMessage(ms);
//					}
//
//				});
//				addImageView.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						int sum = Integer.parseInt(number.getText().toString());
//						if (sum >= 9999) {
//							Toast.makeText(context, "您想要购买更多的产品，请与客服联系",
//									Toast.LENGTH_LONG).show();
//						} else {
//							sum++;
//							number.setText(sum + "");
//							// mTextView.setText(Integer.parseInt(number.getText()
//							// .toString())+"");
//						}
//						// dataManage.mdfCartAmount(cart.getUId(),sum);
//						Message ms = new Message();
//						ms.what = 123;
//						handler.sendMessage(ms);
//					}
//				});
//				layout.setOnLongClickListener(new OnLongClickListener() {
//
//					@Override
//					public boolean onLongClick(View v) {
//						// TODO Auto-generated method stub
//						dialog.show();
//						return true;
//					}
//				});
//				// mTextView.setText(Integer.parseInt(number.getText()
//				// .toString())+"");
//				// Log.i("info", view.getTag()+"");
//				checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView,
//							boolean isChecked) {
//						if (checkBox.isChecked()) {
//							map.put("check", (float) 1.0);
//						} else {
//							map.put("check", (float) 0.0);
//						}
//						Message ms = new Message();
//						ms.what = 123;
//						handler.sendMessage(ms);
//					}
//				});
//				mList.add(checkBox);
//				map.put("check",
//						(float) (checkBox.isChecked() == false ? 0 : 1));
//				map.put("count",
//						(float) Integer.parseInt(number.getText().toString()));
//				map.put("price",
//						Float.parseFloat(priceTextView.getText().toString()));
//				list.add(map);
//				// 设置监听
//				mBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView,
//							boolean isChecked) {
//						if (isChecked) {
//							Log.i("info", mBox.isChecked() + "");
//							Message msg = new Message();
//							msg.what = 124;
//							handler.sendMessage(msg);
//							for (int i = 0; i < mList.size(); i++) {
//								CheckBox checkBox = (CheckBox) mList.get(i);
//								checkBox.setChecked(true);
//
//							}
//						}
//
//					}
//				});
//				linearLayout.addView(view);
//
//			}
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			Toast.makeText(context, "网络不给力！", Toast.LENGTH_SHORT).show();
//
//		}
//
//	}
//
//}

