package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.unionpay.mpay.widgets.p;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.commments.WaitingComment;
import com.yidejia.app.mall.net.goodsinfo.GetProductAddress;
import com.yidejia.app.mall.view.PersonEvaluationActivity;

public class TaskNoEva {
	
//	private int fromIndex = 0;
//	private int amount = 10;
	
	private Activity activity;
//	private View view;
	private LinearLayout layout;
	private LayoutInflater inflater;
	private TaskWaitComm taskWaitComm;
	
	public TaskNoEva(Activity activity, LinearLayout layout){
		this.activity = activity;
//		this.view = view;
		this.layout = layout;
		this.inflater = LayoutInflater.from(activity);
	}
	
	public void getWaitingComment(String userid, boolean isFirstIn){
		waitCommGoods = new ArrayList<Cart>();
		if(!ConnectionDetector.isConnectingToInternet(activity)) {
			Toast.makeText(activity, activity.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return;
		}
		if (taskWaitComm != null
				&& taskWaitComm.getStatus() == AsyncTask.Status.RUNNING) {
			taskWaitComm.cancel(true); // 如果Task还在运行，则先取消它
		}
		taskWaitComm = new TaskWaitComm(userid, isFirstIn);
		taskWaitComm.execute();
	}
	
	private ArrayList<Cart> waitCommGoods;

	private class TaskWaitComm extends AsyncTask<Void, Void, Boolean> {

		private String userid;
		private boolean isFirstIn;
		private ProgressDialog bar;

		public TaskWaitComm(String userid, boolean isFirstIn) {
			this.userid = userid;
			this.isFirstIn = isFirstIn;
			if(isFirstIn)
				bar = new ProgressDialog(activity);
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (isFirstIn) {
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//				bar.setMessage(activity.getResources().getString(
//						R.string.searching));
				bar.show();
			}
		}



		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(isCancelled()) return null;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			WaitingComment waitingComment = new WaitingComment();
			try {
				String httpResp = waitingComment.getHttpResp(userid);
				try {
					JSONObject httpObject = new JSONObject(httpResp);
					int httpCode = httpObject.getInt("code");
					if (httpCode == 1) {
						String resp = httpObject.getString("response");
						try {
							JSONArray respArray = new JSONArray(resp);
							int length = respArray.length();
							Cart mCart;
							JSONObject arrayObject;
							for (int i = 0; i < length; i++) {
								mCart = new Cart();
								arrayObject = respArray.getJSONObject(i);
								String goodsid = arrayObject
										.getString("goods_id");
								String price = arrayObject.getString("price");
//								if("".equals(price) || null == price || "0.0".equals(price))
//									break;
								String quantity = arrayObject
										.getString("quantity");
								String evaluate_status = arrayObject
										.getString("evaluate_status");
								if("y".equals(evaluate_status)) continue;
								String dry_status = arrayObject
										.getString("dry_status");
								String imageUrl = ImageUrl.IMAGEURL
										+ arrayObject.getString("imgname") + "!100";
								String goodsname = "";
								GetProductAddress address = new GetProductAddress(
										activity);
								try {
									String productString = address
											.getProductJsonString(goodsid);
									JSONObject productObject = new JSONObject(
											productString);
									int productHttpCode = productObject
											.getInt("code");
									if (productHttpCode == 1) {
										String productResp = productObject
												.getString("response");
										JSONObject productRespObject = new JSONObject(
												productResp);
										goodsname = productRespObject
												.getString("goods_name");
									}
								} catch (IOException e) {

								} catch (JSONException e) {
									// TODO: handle exception
								} catch (Exception e) {
									// TODO: handle exception
								}
								mCart.setImgUrl(imageUrl);
								mCart.setProductText(goodsname);
								mCart.setUId(goodsid);
								try {
									mCart.setSalledAmmount(Integer
											.parseInt(quantity));
									mCart.setPrice(Float.parseFloat(price));
								} catch (NumberFormatException e) {
									// TODO: handle exception
								}
								waitCommGoods.add(mCart);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(isFirstIn)
				bar.dismiss();
			if(result){
				initDisplayImageOption();
				if (!waitCommGoods.isEmpty()) {
					int length = waitCommGoods.size();
					Cart mCart;
					for (int i = 0; i < length; i++) {
						mCart = waitCommGoods.get(i);
						View view = inflater.inflate(R.layout.evaluation_item, null);
						TextView evaluation_item_text = (TextView) view.findViewById(R.id.evaluation_item_text);
						TextView evaluation_item_sum = (TextView) view.findViewById(R.id.evaluation_item_sum);
						TextView evaluation_item_count = (TextView) view.findViewById(R.id.evaluation_item_count);
						TextView evaluation_sum_money = (TextView) view.findViewById(R.id.evaluation_sum_money);
						TextView evaluation_item_total_num = (TextView) view.findViewById(R.id.evaluation_item_total_num);
						
						ImageView evaluation_item_image = (ImageView) view.findViewById(R.id.evaluation_item_image);
						imageLoader.displayImage(mCart.getImgUrl(), evaluation_item_image, options,
								animateFirstListener);
						Button evaluation_item_evaluation = (Button) view.findViewById(R.id.evaluation_item_evaluation);
						evaluation_item_text.setText(mCart.getProductText());
						evaluation_item_sum.setText(mCart.getPrice() + activity.getResources().getString(R.string.unit));
						evaluation_item_count.setText("数量:" + mCart.getAmount());
						evaluation_sum_money.setText((mCart.getPrice() * mCart.getAmount()) + "");
						evaluation_item_total_num.setText("数量:" + mCart.getAmount() + "");
						
						final String goodsId = mCart.getUId();
						
						evaluation_item_evaluation.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								// 评价
								intent.setClass(activity, PersonEvaluationActivity.class);
								intent.putExtra("goodsId", goodsId);
								int requestCode = 4001;
								activity.startActivityForResult(intent, requestCode);
								// 结束当前Activity；
//								activity.finish();
							}
						});
						layout.addView(view);
					}
				}
			} else {
				Toast.makeText(activity, "未搜索到数据", Toast.LENGTH_LONG).show();
			}
		}

		
		
	}
	
	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {


		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
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
	
	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private void initDisplayImageOption(){
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.image_bg)
			.showImageOnFail(R.drawable.image_bg)
			.showImageForEmptyUri(R.drawable.image_bg)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
	}
}
