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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.view.LoginActivity;

public class CartUtil1 {
	private Context context;
	private LayoutInflater inflater;
	private LinearLayout linearLayout;
	public static List<HashMap<String, Float>> list;// ������Ŀ��ֵ�Ǽ۸�
	private TextView mTextView;// ����������Ŀ
	private TextView sumTextView;// �ܵ�Ǯ��
	private CheckBox mBox;
	private CartsDataManage dataManage;
	private List<Object> mList;
	public static List<HashMap<String, Object>> list1;

	// private MyApplication myApplication;
	// private InnerReceiver receiver;

	private String items[] = { "删除", "查看商品详情", "收藏" };

	private TextView number;

	public CartUtil1() {

	}

	/**
	 * 
	 * @param context
	 */
	public CartUtil1(Context context, LinearLayout linearLayout,
			TextView mTextView, TextView sumTextView, CheckBox box) {// ,View
																		// view
		this.inflater = LayoutInflater.from(context); // ,UserCommentDataManage
		// myApplication = (MyApplication) context.getApplicationContext();
		this.linearLayout = linearLayout;
		this.context = context;
		this.mTextView = mTextView;
		this.sumTextView = sumTextView;
		this.mBox = box;
		initDisplayImageOption();
		// receiver = new InnerReceiver();
		// IntentFilter filter = new IntentFilter();
		// this.context.registerReceiver(receiver, filter);
		// filter.addAction(Consts.UPDATE_CHANGE);
	}

	

	private void initDisplayImageOption() {
		options = MyApplication.getInstance().initBannerImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
	}

	private ImageLoadingListener animateFirstListener;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();// ����ͼƬ

	/**
	 * ��ʾȫ�������
	 */

	public void AllComment() {

		ArrayList<Cart> userList;
		ArrayList<Cart> orderCarts = new ArrayList<Cart>();
		try {

			mList = new ArrayList<Object>();

			dataManage = new CartsDataManage();
			userList = dataManage.getCartsArray();

			list = new ArrayList<HashMap<String, Float>>();
			list1 = new ArrayList<HashMap<String, Object>>();
			int a = 0;
			float b = 0;
			for (int i = 0; i < userList.size(); i++) {
				Log.e("info", userList.size() + "  +userList");
				// checkmMap = new HashMap<String, Boolean>();

				final Cart cart = userList.get(i);
				mBox.setChecked(true);
				Log.i("mBox", mBox.isChecked() + "    mBox");
				final View view = LayoutInflater.from(context).inflate(
						R.layout.shopping_cart_item, null);
				view.setTag(i);
				final RelativeLayout layout = (RelativeLayout) view
						.findViewById(R.id.rela);

				ImageView headImageView = (ImageView) view
						.findViewById(R.id.iv_shopping_cart_item__imageview1);
				TextView detailTextView = (TextView) view
						.findViewById(R.id.tv_shopping_cart_item_text);
				final TextView priceTextView = (TextView) view
						.findViewById(R.id.tv_shopping_cart_item_money);

				final CheckBox checkBox = (CheckBox) view
						.findViewById(R.id.ck_shopping_cart_item_checkbox);

				checkBox.setChecked(true);

				final ImageView subtract = (ImageView) view
						.findViewById(R.id.iv_shopping_cart_item_subtract);// ��
				final ImageView addImageView = (ImageView) view
						.findViewById(R.id.iv_shopping_cart_item_add);// ��

				final TextView number = (TextView) view
						.findViewById(R.id.tv_shopping_cart_item_edit_number);// ����ĸ���
				number.setText(cart.getAmount() + "");
				a += cart.getAmount();
				b += cart.getPrice() * cart.getAmount();

				mTextView.setText(a + "");
				sumTextView.setText(b + "");
				String path = cart.getImgUrl();
				
				ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
				imageLoader.displayImage(path, headImageView, options,
						animateFirstListener);

				// Bitmap bm = BitmapFactory.decodeFile(path);
				// if (bm != null) {
				// headImageView.setImageBitmap(bm);
				// } else {
				// headImageView.setImageResource(R.drawable.ic_launcher);
				// }
				//
				detailTextView.setText(cart.getProductText());// ��Ʒ�Ľ���
				layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent(
								context,
								com.yidejia.app.mall.goodinfo.GoodsInfoActivity.class);

						Bundle bundle = new Bundle();
						bundle.putString("goodsId", cart.getUId());
						intent.putExtras(bundle);
						context.startActivity(intent);

						// context.startActivity(intent);
					}
				});

				// �����ӿؼ����Ȼ�ȡ���㣬��ϸ��setDescendantFocusability
				layout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
				priceTextView.setText(cart.getPrice() + "");

				final HashMap<String, Float> map = new HashMap<String, Float>();
				final HashMap<String, Object> map1 = new HashMap<String, Object>();
				final Handler handler = new Handler() {
					public void handleMessage(Message msg) {

						if (msg.what == 123) {

							// map.put("check",checkBox.isChecked()==false?0:1);

							// Log.i("info", 123+"");
							map.put("count", Float.parseFloat(number.getText()
									.toString()));
							// map.put(Integer.parseInt(number.getText().toString()),Integer.parseInt(priceTextView.getText().toString()));
							dataManage.mdfCartAmount(cart.getUId(), Integer
									.parseInt(number.getText().toString()));
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
						} else if (msg.what == 113) {

							// map.put("check",checkBox.isChecked()==false?0:1);

							// Log.i("info", 123+"");
							map.put("count", Float.parseFloat(number.getText()
									.toString()));
							// map.put(Integer.parseInt(number.getText().toString()),Integer.parseInt(priceTextView.getText().toString()));
							dataManage.mdfCartAmount(cart.getUId(), Integer
									.parseInt(number.getText().toString()));
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

								}
								// else {
								// mBox.setChecked(false);
								// }

							}
							// Log.i("info", j+"+j");
							if (j == list.size()) {

								// Log.i("info", list.size()+"+list.size");
								mBox.setChecked(true);
								Log.i("info", mBox.isChecked() + "   mBox6");
								// Log.i("info",mBox.isChecked()+"");
							} else {
								mBox.setChecked(false);
								Log.i("info", mBox.isChecked() + "   mBox7");
							}
							Log.i("info", count + "        afafafas");
							Log.i("info", sum + "        afafafas");
							sumTextView.setText(sum + "");
							mTextView.setText(count + "");
							// Log.i("info", list.toString());
						}

						else if (msg.what == 124) {
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
							Log.i("info", count + "        afafafas");
							Log.i("info", sum + "        afafafas");
							sumTextView.setText(sum + "");
							// Intent intent = new
							// Intent(Consts.CHECK_UPDATE_CHANGE);
							// context.sendBroadcast(intent);
							mTextView.setText(count + "");
						} else if (msg.what == 125) {
							linearLayout.removeView(layout);
							view.invalidate();
							Log.i("info", "        afafafas");
							dataManage.delCart(cart.getUId());
							map.put("count", (float) 0.0);
							// dataManage.mdfCartAmount(cart.getUId(),0);//
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
							// Intent intent = new Intent(Consts.UPDATE_CHANGE);
							// context.sendBroadcast(intent);
							mTextView.setText(count + "");
						}
					}

				};
				Builder builder = new Builder(context);
				final AlertDialog dialog = builder
						.setIcon(android.R.drawable.alert_dark_frame)
						.setTitle(
								context.getResources().getString(
										R.string.cart_contorl))
						.setSingleChoiceItems(
								items,
								0,
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0:
											dataManage = new CartsDataManage();
											map.put("count", (float) 0);
											boolean isDel = dataManage
													.delCart(cart.getUId());
											dialog.dismiss();
											Message ms1 = new Message();
											ms1.what = 125;
											mList.remove(checkBox);
											handler.sendMessage(ms1);
											Intent intent1 = null;
											if (dataManage.getCartAmount() == 0) {
												intent1 = new Intent(
														Consts.BROAD_UPDATE_CHANGE);
											} else {
												intent1 = new Intent(
														Consts.UPDATE_CHANGE);
											}
											context.sendBroadcast(intent1);

											break;

										case 1:
											Intent intent = new Intent(context,
													GoodsInfoActivity.class);
											Bundle bundle = new Bundle();
											bundle.putString("goodsId",
													cart.getUId());
											intent.putExtras(bundle);
											dialog.dismiss();
											context.startActivity(intent);
											break;
										case 2:
											Toast.makeText(
													context,
													context.getResources()
															.getString(
																	R.string.alreay_collect),
													Toast.LENGTH_LONG).show();
											dialog.dismiss();
											MyApplication myApplication = (MyApplication) context
													.getApplicationContext();
											if (myApplication.getIsLogin()) {
												// 已经登录
												//TODO 添加收藏
												

											} else {
												new Builder(context)
														.setTitle(
																context.getResources()
																		.getString(
																				R.string.tips))
														.setMessage(
																R.string.please_login)
														.setPositiveButton(
																R.string.sure,
																new DialogInterface.OnClickListener() {

																	@Override
																	public void onClick(
																			DialogInterface dialog,
																			int which) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub
																		Intent loginIntent = new Intent(
																				context,
																				LoginActivity.class);
																		context.startActivity(loginIntent);
																	}

																})
														.setNegativeButton(
																R.string.searchCancel,
																new DialogInterface.OnClickListener() {

																	@Override
																	public void onClick(
																			DialogInterface dialog,
																			int which) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub

																	}

																}).create()
														.show();
											}
											break;
										}

									}
								}).create();
				subtract.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						int sum = Integer.parseInt(number.getText().toString());
						if (sum <= 1) {
							Toast.makeText(
									context,
									context.getResources().getString(
											R.string.mix), Toast.LENGTH_LONG)
									.show();
						} else {
							sum--;
							number.setText(sum + "");
							// mTextView.setText(Integer.parseInt(number.getText()
							// .toString()) + "");
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
							Toast.makeText(
									context,
									context.getResources().getString(
											R.string.price_error),
									Toast.LENGTH_LONG).show();
						} else {
							sum++;
							number.setText(sum + "");
							// mTextView.setText(Integer.parseInt(number.getText()
							// .toString())+"");
						}
						// dataManage.mdfCartAmount(cart.getUId(),sum);
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
							Log.i("info", mBox.isChecked() + "   mBox2");
							map.put("check", (float) 1.0);
							map1.put("check", (float) 1.0);
						} else {
							Log.i("info", mBox.isChecked() + "   mBox3");
							map.put("check", (float) 0.0);
							map1.put("check", (float) 0.0);
							// mBox.setChecked(false);
						}
						Message ms = new Message();
						ms.what = 113;
						handler.sendMessage(ms);
					}
				});

				mList.add(checkBox);
				map.put("check",
						(float) (checkBox.isChecked() == false ? 0 : 1));
				map1.put("check", (float) (checkBox.isChecked() == false ? 0
						: 1));
				map1.put("cart", cart);
				map.put("count",
						(float) Integer.parseInt(number.getText().toString()));
				map.put("price",
						Float.parseFloat(priceTextView.getText().toString()));
				map.put("produce", Float.parseFloat(cart.getUId()));
				list.add(map);
				list1.add(map1);
				mBox.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.i("info", mBox.isChecked() + "   mBox");
						if (mBox.isChecked()) {
							mBox.setChecked(true);
							Log.i("info", mBox.isChecked() + "   mBox5");
							Message msg = new Message();
							msg.what = 124;
							handler.sendMessage(msg);
							for (int i = 0; i < mList.size(); i++) {
								CheckBox checkBox = (CheckBox) mList.get(i);
								checkBox.setChecked(true);
								Log.i("info", checkBox.isChecked() + "+j");
							}
						} else {
							mBox.setChecked(false);
							Log.i("info", mBox.isChecked() + "   mBox1");
							for (int i = 0; i < mList.size(); i++) {
								CheckBox checkBox = (CheckBox) mList.get(i);
								checkBox.setChecked(false);
							}

						}
					}
				});
				final AlertDialog dialog1 = new Builder(context)
						.setTitle(
								context.getResources().getString(
										R.string.delete))
						.setIcon(R.drawable.ic_launcher)
						.setMessage(
								context.getResources().getString(
										R.string.sure_delete_produce))
						.setPositiveButton(
								context.getResources().getString(R.string.sure),
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										map.put("count", (float) 0);
										boolean isDel = dataManage.delCart(cart
												.getUId());
										Message ms1 = new Message();
										ms1.what = 125;
										mList.remove(checkBox);
										handler.sendMessage(ms1);
										Intent intent1 = null;
										if (dataManage.getCartAmount() == 0) {
											intent1 = new Intent(
													Consts.BROAD_UPDATE_CHANGE);
										} else {
											intent1 = new Intent(
													Consts.UPDATE_CHANGE);
										}
										context.sendBroadcast(intent1);
									}
								})
						.setNegativeButton(
								context.getResources().getString(
										R.string.cancel), null).create();

				Log.i("info", mBox.isChecked() + "   mBox");
				linearLayout.addView(view);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			Log.e(CartUtil1.class.getName(), "类型转换出错");
			e.printStackTrace();

		}
	}

}
