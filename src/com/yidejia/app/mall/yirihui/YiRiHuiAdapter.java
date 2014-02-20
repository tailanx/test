package com.yidejia.app.mall.yirihui;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.pay.CstmPayActivity;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class YiRiHuiAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<YiRiHuiData> yiRiHuiDatas;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	private Activity activity;
	
//	private boolean isCanBuy = false;
	private int type = 0;	//type -1 过去 0现在 1 将来  

	public YiRiHuiAdapter(Activity activity, ArrayList<YiRiHuiData> yiRiHuiDatas){
		this.activity = activity;
		inflater = activity.getLayoutInflater();
		this.yiRiHuiDatas = yiRiHuiDatas;
		
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
		ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
	}
	
	/**设置是否为正在进行的活动**/
	public void setCanBuy(boolean isCanBuy) {
//		this.isCanBuy = isCanBuy;
	}
	
	/**type -1 过去 0现在 1 将来  **/
	public void setType(int type){
		this.type = type;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public int getCount() {
		if(null != yiRiHuiDatas) return yiRiHuiDatas.size();
		return 0;
	}

	@Override
	public YiRiHuiData getItem(int position) {
		if(null != yiRiHuiDatas) return yiRiHuiDatas.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.yirihui_item, null);
			holder = new ViewHolder();
			holder.tvLong = (TextView) convertView.findViewById(R.id.tv_yirihui_long);
			holder.tvHour = (TextView) convertView.findViewById(R.id.tv_hour);
			holder.tvMin = (TextView) convertView.findViewById(R.id.tv_min);
			holder.tvSecond = (TextView) convertView.findViewById(R.id.tv_second);
			holder.tvBuyNow = (TextView) convertView.findViewById(R.id.tv_buy_now);
			holder.tvGoodsName = (TextView) convertView.findViewById(R.id.tv_yirihui_detail);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_yirihui_price);
			holder.tvCount = (TextView) convertView.findViewById(R.id.tv_yirihui_count);
			holder.ivGoodsBig = (ImageView) convertView.findViewById(R.id.iv_yrh_big);
			holder.ivGoodsSmall = (ImageView) convertView.findViewById(R.id.iv_yrh_small);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
//		boolean isCanBuy = false;
//		if(0 == type) isCanBuy = true;
		if(0 == type) holder.tvBuyNow.setSelected(true);
		
		setLongText(holder);
		
		if(null == yiRiHuiDatas) return convertView;
		
		final long overTime = yiRiHuiDatas.get(position).getStartTime();
		setTime(holder, overTime);
		
//		String count = yiRiHuiDatas.get(position).getQuantity();
		String totalCount = yiRiHuiDatas.get(position).getCanBuyQuantity();
		holder.tvCount.setText(totalCount);
		
		final String goodsId = yiRiHuiDatas.get(position).getGoodsId();
		final String ruleId = yiRiHuiDatas.get(position).getTheId();
		
		final String goodsName = yiRiHuiDatas.get(position).getRuleName();
		holder.tvGoodsName.setText(goodsName);
		
		final String goodsPrice = yiRiHuiDatas.get(position).getGoodsPrice();
		holder.tvPrice.setText(activity.getString(R.string.unit) + goodsPrice);
		
		final String imgUrlBig = yiRiHuiDatas.get(position).getImg1();
		ImageLoader.getInstance().displayImage(imgUrlBig, holder.ivGoodsBig, options, animateFirstListener);
		
		String imgUrlSmall = yiRiHuiDatas.get(position).getImg2();
		ImageLoader.getInstance().displayImage(imgUrlSmall, holder.ivGoodsSmall, options, animateFirstListener);
		
		//已结束 马上购买文字变为已售罄
		if(-1 == type) {
			holder.tvBuyNow.setText("已售罄");
			holder.tvCount.setText("00");
		} else {
			holder.tvCount.setSelected(true);
		}
		
		if(-1 != type){
			new CountDownTimer(overTime * 1000, 1000) {
				
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					setTime(holder, millisUntilFinished / 1000);
				}
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					
				}
			}.start();
		}
		
		try {
			final int canBuyCount = Integer.parseInt(totalCount);
			if(canBuyCount <= 0) {	//用户可购买的数量小于0
				holder.tvBuyNow.setSelected(false);
				holder.tvCount.setSelected(false);
			}
			if(canBuyCount < 10) holder.tvCount.setText("0" + canBuyCount);
		} catch (NumberFormatException e) {
		}
		
		if(0 == type) {
			holder.tvBuyNow.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(!holder.tvBuyNow.isSelected()) return;
					//检查是否登录
					if(!isLogin()) return;
					
					Cart cart = new Cart();
					cart.setUId(goodsId);
					cart.setName(goodsName);
					
					float price = 0.0f;
					try {
						price = Float.parseFloat(goodsPrice);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					cart.setImgUrl(imgUrlBig);
					cart.setPrice(price);
					cart.setProductText(goodsName);
					cart.setSalledAmmount(1);
					cart.setScort("0.0f");
					ArrayList<Cart> cartArray = new ArrayList<Cart>();
					cartArray.add(cart);
					go2Pay(price, cartArray, ruleId);
				}
			});
		}
		
		return convertView;
	}
	
	/**设置时间**/
	private void setTime(ViewHolder holder, long overTime){
		if (null == holder) {
			return;
		}
//		int time = 0;
		try {
//			time = Integer.parseInt(overTime);
			int second = (int)overTime % 60;
			if(second < 10) holder.tvSecond.setText("0" + second);
			else holder.tvSecond.setText(second + "");
			int min = (int)(overTime / 60) % 60;
			if(min < 10) holder.tvMin.setText("0" + min);
			else holder.tvMin.setText(min + "");
			int hour = (int)overTime / 3600;
			if(hour < 10) holder.tvHour.setText("0" + hour);
			else holder.tvHour.setText(hour + "");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	private void go2Pay(float price, ArrayList<Cart> carts, String ruleId) {
		Intent intent = new Intent(activity, CstmPayActivity.class);
		Bundle bundle = new Bundle();
		bundle.putFloat("price", price);
		bundle.putBoolean("canHuanGou", false);
		bundle.putBoolean("canTicket", false);
		// bundle.putBoolean("isYRH", true);
		bundle.putString("ruleId", ruleId);
		intent.putExtras(bundle);
		intent.putExtra("carts", carts);
		activity.startActivity(intent);

	}
	
	private void setLongText(ViewHolder holder){
		switch (type) {
		case 0:
			
			break;
		case -1:
			holder.tvLong.setText("已结束");
			break;
		case 1:
			holder.tvLong.setText("距离开始");
			break;
		default:
			break;
		}
	}
	
	/** 检查是否已登录 **/
	private boolean isLogin() {
		if (!MyApplication.getInstance().getIsLogin()) {
			Toast.makeText(activity,
					activity.getResources().getString(R.string.please_login),
					Toast.LENGTH_LONG).show();
			Intent intent1 = new Intent(activity, LoginActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("exit", Consts.DELETE_CART);
			intent1.putExtras(bundle);
			activity.startActivity(intent1);
			return false;
		}
		return true;
	}
	
	static class ViewHolder {
		private TextView tvHour;// 小时
		private TextView tvMin;// 分钟
		private TextView tvSecond;// 秒
		private TextView tvBuyNow;// 马上购买
		private TextView tvGoodsName;//商品名称
		private TextView tvPrice;// 价格
		private TextView tvCount;// 数量
		private TextView tvLong;	//距离开始或者结束
		private ImageView ivGoodsBig;	//商品图片
		private ImageView ivGoodsSmall;	//搭配商品图片
	}

}
