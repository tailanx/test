package com.yidejia.app.mall.ctrl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.ActivityIntentUtil;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.yirihui.ParseYiRiHui;
import com.yidejia.app.mall.yirihui.YiRiHuiData;
import com.yidejia.app.mall.yirihui.YirihuiActivity;

public class HomeYRHView {
	
	private Activity activity;
	private CountDownTimer timer;	//倒计时
	
	private CountDownTimer updateTimer;	//更新的倒计时
	
	private TextView tvHour;// 小时
	private TextView tvMin;// 分钟
	private TextView tvSecond;// 秒
//	private TextView tvBuyNow;// 马上购买
	private TextView tvGoodsName;//商品名称
	private TextView tvPrice;// 价格
	private TextView tvCount;// 数量
//	private TextView tvLong;	//距离开始或者结束
	private ImageView ivGoodsBig;	//商品图片
	private ImageView ivGoodsSmall;	//搭配商品图片
	
	private RelativeLayout rlHomeYRH;	//首页伊日惠的大布局
	
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	
	private int type = 0;	//type -1 过去 0现在 1 将来  
	
	private String ruleId = "";
	
	public HomeYRHView(Activity activity){
		this.activity = activity;
		findIds();
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
	}
	
	private void findIds(){
//		tvLong = (TextView) activity.findViewById(R.id.tv_yirihui_long);
		tvHour = (TextView) activity.findViewById(R.id.tv_main_hot_hour);
		tvMin = (TextView) activity.findViewById(R.id.tv_main_hot_min);
		tvSecond = (TextView) activity.findViewById(R.id.tv_main_hot_second);
//		tvBuyNow = (TextView) activity.findViewById(R.id.tv_buy_now);
		tvGoodsName = (TextView) activity.findViewById(R.id.tv_main_hot_yirihui_detail);
		tvPrice = (TextView) activity.findViewById(R.id.tv_main_hot_yirihui_price);
		tvCount = (TextView) activity.findViewById(R.id.tv_main_hot_yirihui_count);
		ivGoodsBig = (ImageView) activity.findViewById(R.id.iv_yrh_img1);
		ivGoodsSmall = (ImageView) activity.findViewById(R.id.iv_yrh_img2);
		rlHomeYRH = (RelativeLayout) activity.findViewById(R.id.rl_home_yrh);
		
	}
	
	/**获取伊日惠数据**/
	public void getYRHData(){
		String url = new JNICallBack().getHttp4GetYRHHome();
//		String param = new JNICallBack().getHttp4GetYiRiHui("" + type, "0", "1", "");
//		Log.e("system.out", url);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		
		httpClientUtil.getHttpResp(url, new IHttpResp() {
			
			@Override
			public void onSuccess(String content) {
//				Log.e("system.out", content);
//				content = "{\"code\":1,\"msg\":\"成功\",\"response\":[{\"the_id\":\"1\",\"rule_name\":\"伊日惠测试活动一\",\"begin_time\":\"2014-02-17 11:03:07\",\"end_time\":\"2014-02-18 00:00:00\",\"goods_id\":\"1590\",\"quantity\":\"1\",\"can_buy_quantity\":\"1\",\"overtime\":\"900\",\"img_1\":\"5/2014/02/17/a0acc98642b.jpg\",\"img_2\":\"8/2014/02/17/a0ac9bce227.jpg\",\"valid_flag\":\"y\",\"shell_flag\":\"y\",\"goods_name\":\"【马年活动】脱盐海泉精华\",\"goods_price\":\"50.00\"}],\"ts\":1392609753}";
				ParseYiRiHui parseYiRiHui = new ParseYiRiHui();
				boolean isSuccess = parseYiRiHui.parseMainYRH(content);
				if(isSuccess){
					ArrayList<YiRiHuiData> tempYiRiHuiDatas = parseYiRiHui.getYiRiHuiDatas();
					if(null != tempYiRiHuiDatas && tempYiRiHuiDatas.size() != 0) {
						showView(tempYiRiHuiDatas.get(0));
					} else {
//						if(0 == type){
//							type = -1;
//							getYRHData();
//						}
					}
				} 
			}

			@Override
			public void onFinish() {
				super.onFinish();
				updateYRH();
			}
			
		});
	}
	
	/**显示伊日惠数据**/
	private void showView(YiRiHuiData yiRiHuiDatas){
		long overTime = yiRiHuiDatas.getStartTime();
		setTime(overTime);
		
//		String count = yiRiHuiDatas.getQuantity();
		String totalCount = yiRiHuiDatas.getCanBuyQuantity();
		tvCount.setText(totalCount);
		
		if(overTime > 0){
			setTextViewSelected(true);
			type = 0;
		} else{
			setTextViewSelected(false);
			totalCount = "0";
			type = -1;
		}
		
		try {
			int count = 0;
			count = Integer.parseInt(totalCount);
			if(count <= 0) {
				tvCount.setText("00");
				tvCount.setSelected(false);
			} else if(count < 10) {
				tvCount.setText("0" + count);
			}
		} catch (NumberFormatException e) {
		}
		
//		final String goodsId = yiRiHuiDatas.getGoodsId();
//		final String ruleId = yiRiHuiDatas.getTheId();
		
		rlHomeYRH.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				ActivityIntentUtil.intentActivity(activity, YirihuiActivity.class);
				Intent intent = new Intent(activity, YirihuiActivity.class);
				intent.putExtra("type", type);
				activity.startActivity(intent);
			}
		});
		
		if(ruleId.equals(yiRiHuiDatas.getTheId())) return;
		
		if (0 == type) {
			startTimer(overTime);
		}
		ruleId = yiRiHuiDatas.getTheId();
		
		final String goodsName = yiRiHuiDatas.getRuleName();
		tvGoodsName.setText(goodsName);
		
		final String goodsPrice = yiRiHuiDatas.getGoodsPrice();
		tvPrice.setText(activity.getString(R.string.unit) + goodsPrice);
		
		final String imgUrlBig = yiRiHuiDatas.getImg1();
		ImageLoader.getInstance().displayImage(imgUrlBig, ivGoodsBig, options, animateFirstListener);
		
		String imgUrlSmall = yiRiHuiDatas.getImg2();
		ImageLoader.getInstance().displayImage(imgUrlSmall, ivGoodsSmall, options, animateFirstListener);
	}
	
	private void startTimer(long overTime){
		if(null != timer) {
			timer.cancel();
			timer = null;
		}
		
		timer = new CountDownTimer(overTime * 1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				setTime(millisUntilFinished / 1000);
			}

			@Override
			public void onFinish() {
				getYRHData();
			}
		}.start();
	}
	
	/**设置时间**/
	private void setTime(long overTime){
//		int time = 0;
		try {
//			time = Integer.parseInt(overTime);
			
			int second = (int)overTime % 60;
			if(second < 10) tvSecond.setText("0" + second);
			else tvSecond.setText(second + "");
			int min = (int)(overTime / 60) % 60;
			if(min < 10) tvMin.setText("0" + min);
			else tvMin.setText(min + "");
			int hour = (int)overTime / 3600;
			if(hour < 10) tvHour.setText("0" + hour);
			else tvHour.setText(hour + "");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	private void setTextViewSelected(boolean isSelected){
		tvHour.setSelected(isSelected);
		tvMin.setSelected(isSelected);
		tvSecond.setSelected(isSelected);
		tvCount.setSelected(isSelected);
	}
	
	private void updateYRH(){
		if(null != updateTimer) {
			updateTimer.cancel();
			updateTimer = null;
		}
		
		updateTimer = new CountDownTimer(8 * 1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				getYRHData();
			}
		}.start();
	}
	
	public void cancelUpdateYRH(){
		if(null != updateTimer) {
			updateTimer.cancel();
			updateTimer = null;
		}
	}
	
}
