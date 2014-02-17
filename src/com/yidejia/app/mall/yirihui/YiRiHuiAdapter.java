package com.yidejia.app.mall.yirihui;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class YiRiHuiAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<YiRiHuiData> yiRiHuiDatas;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;

	public YiRiHuiAdapter(Activity activity, ArrayList<YiRiHuiData> yiRiHuiDatas){
		inflater = activity.getLayoutInflater();
		this.yiRiHuiDatas = yiRiHuiDatas;
		
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
		ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
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
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.yirihui_item, null);
			holder = new ViewHolder();
			holder.tvLong = (TextView) convertView.findViewById(R.id.tv_yirihui_long);
			holder.tvHour = (TextView) convertView.findViewById(R.id.tv_hour);
			holder.tvMin = (TextView) convertView.findViewById(R.id.tv_min);
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
		
		if(null == yiRiHuiDatas) return convertView;
		
		String overTime = yiRiHuiDatas.get(position).getStartTime();
		setTime(holder, overTime);
		
		String count = yiRiHuiDatas.get(position).getQuantity();
		
		String goodsName = yiRiHuiDatas.get(position).getGoodsName();
		holder.tvGoodsName.setText(goodsName);
		
		String goodsPrice = yiRiHuiDatas.get(position).getGoodsPrice();
		holder.tvPrice.setText(goodsPrice);
		
		String imgUrlBig = yiRiHuiDatas.get(position).getImg1();
		ImageLoader.getInstance().displayImage(imgUrlBig, holder.ivGoodsBig, options, animateFirstListener);
		
		String imgUrlSmall = yiRiHuiDatas.get(position).getImg2();
		ImageLoader.getInstance().displayImage(imgUrlSmall, holder.ivGoodsSmall, options, animateFirstListener);
		
		
		return convertView;
	}
	
	private void setTime(ViewHolder holder, String overTime){
		if (null == holder) {
			return;
		}
		int time = 0;
		try {
			time = Integer.parseInt(overTime);
			
			int second = time % 60;
			holder.tvSecond.setText(second + "");
			int min = (time / 60) % 60;
			holder.tvMin.setText(min + "");
			int hour = time / 3600;
			holder.tvHour.setText(hour + "");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
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
