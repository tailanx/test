package com.yidejia.app.mall.goodinfo;

import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.BrowseHistoryDataManage;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.DPIUtil;
import com.yidejia.app.mall.view.GoCartActivity;
import com.yidejia.app.mall.widget.YLViewPager;

public class GoodsInfoViewUtil {

	private CartsDataManage manage;
	private BrowseHistoryDataManage historyDataManage;
	private int cart_num = 0; // 购物车内商品个数
	private String productId; // 本商品id
	private String userid; // 本用户id
	private String token;
	private Activity activity;
	// private ImageView imgIcon;
	private ImageView add_favorites; // 加入收藏的按钮
	private TextView shopping_cart_button; // 跳转购物车的按钮
	private LinearLayout matchGoodsImageLayout;
	private ArrayList<BaseProduct> bannerArray;
	private ArrayList<MainProduct> recommendArray;
	private DisplayImageOptions options;

	private YLViewPager vpImage; // 商品图片容器
	
	private boolean isFavClick = false;	//收藏按钮是否被点击

	/**
	 * 只能是商品详情页的activity调用{@link GoodsInfoActivity}
	 * 
	 * @param activity
	 *            {@link GoodsInfoActivity}
	 */
	public GoodsInfoViewUtil(Activity activity) {
		this.activity = activity;
		vpImage = (YLViewPager) activity
				.findViewById(R.id.vp_item_goods_detail);
		initOptions();
	}

	/**
	 * 商品信息页
	 * 
	 * @param info
	 */
	public void initGoodsView(ProductBaseInfo info) {
		try {
			if (info == null)
				return;

			userid = MyApplication.getInstance().getUserId();
			token = MyApplication.getInstance().getToken();

			manage = new CartsDataManage();

			historyDataManage = new BrowseHistoryDataManage();
			// 添加到浏览历史中去
			historyDataManage.addHistory(info);

			cart_num = manage.getCartAmount();
			final Cart cart = new Cart();
			cart.setSalledAmmount(1);
			productId = info.getUId();
			cart.setUId(productId);
			cart.setImgUrl(info.getImgUrl());

			String name = info.getName(); // 商品名称
			cart.setProductText(name);
			final String priceString = info.getPrice(); // 价格
			float priceNum = 0.0f;

			// 商品名称
			TextView base_info_content_text = (TextView) activity
					.findViewById(R.id.tv_item_goods_base_produce_detail);
			base_info_content_text.setText(name);

			// 价格
			TextView price = (TextView) activity.findViewById(R.id.price);
			try {
				priceNum = Float.parseFloat(priceString);
				cart.setPrice(priceNum);
				price.setText(activity.getResources().getString(R.string.unit)
						+ priceString);
			} catch (Exception e) {
				Toast.makeText(
						activity,
						activity.getResources().getString(R.string.price_error),
						Toast.LENGTH_SHORT).show();
				price.setText("");
			}

			// 加入购物车按钮
			// imgIcon = (ImageView) activity.findViewById(R.id.iv_add_to_cart);
			// 加入购物车
			ImageView add_to_cart = (ImageView) activity
					.findViewById(R.id.iv_add_to_cart);
			final boolean isShowFlag = info.isShow_flag();
			if(!isShowFlag){
				add_to_cart.setClickable(false);
				add_to_cart.setImageResource(R.drawable.pause_sales);
				add_to_cart.setFocusable(false);
			}
			
			// 销售额
			TextView selled_num_text = (TextView) activity
					.findViewById(R.id.tv_selled_num_text);
			selled_num_text.setText(info.getSalledAmmount());
			// 评论数
			TextView emulate_num_text = (TextView) activity
					.findViewById(R.id.tv_emulate_num_text);
			emulate_num_text.setText(info.getCommentAmount());
			// 晒单数
			TextView show_num_text = (TextView) activity
					.findViewById(R.id.tv_show_num_text);
			show_num_text.setText(info.getShowListAmount());
			// 品牌
			TextView brand_name_text = (TextView) activity
					.findViewById(R.id.tv_brand_name_text);
			brand_name_text.setText(info.getBrands());
			// 商品编号
			TextView product_id_num_text = (TextView) activity
					.findViewById(R.id.tv_product_id_num_text);
			if (info.getProductNumber() == null
					|| "".equals(info.getProductNumber())) {
				product_id_num_text.setText(activity.getResources().getString(
						R.string.none_code));
			} else {
				product_id_num_text.setText(info.getProductNumber());
			}
			// 规格
			TextView standard_content_text = (TextView) activity
					.findViewById(R.id.standard_content_text);
			standard_content_text.setText(info.getProductSpecifications());
			// 推荐购物view group
			matchGoodsImageLayout = (LinearLayout) activity
					.findViewById(R.id.ll_match_goods_image);
			recommendArray = info.getRecommendArray();

			if (recommendArray.isEmpty()) {
				// 推荐搭配不可见
				matchGoodsImageLayout.setVisibility(ViewGroup.GONE);
				((TextView) activity.findViewById(R.id.match_goods))
						.setVisibility(View.GONE);
			} else {
				addMatchImage(recommendArray);
			}

			bannerArray = info.getBannerArray();
			setPicImage();
			// 购物车个数
			shopping_cart_button = (TextView) activity
					.findViewById(R.id.btn_cart);
			// CartsDataManage cartsDataManage = new CartsDataManage();
			cart_num = manage.getCartAmount();
			if (cart_num == 0) {
				shopping_cart_button.setVisibility(View.GONE);
			} else {
				setCartNum();
			}
			// 加入购物车按钮点击事件
			if (priceNum > 0.01) {
				add_to_cart.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(!isShowFlag){
							return;
						}
						cart_num++;
						boolean istrue = manage.addCart(cart);
						if (istrue) {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.add_cart_scs),
									Toast.LENGTH_SHORT).show();
						}
						setCartNum();
					}

				});
			} else {
				Toast.makeText(activity, "这是赠品，不能够购买", Toast.LENGTH_LONG)
						.show();
				add_to_cart.setImageResource(R.drawable.unfavorites);
				add_to_cart.setClickable(false);

			}

			// 购物车按钮
			RelativeLayout shopping_cart_in_goodsinfo = (RelativeLayout) activity
					.findViewById(R.id.shopping_cart_in_goodsinfo);
			shopping_cart_in_goodsinfo
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(activity,
									GoCartActivity.class);
							activity.startActivity(intent);
						}
					});

			// 加入收藏按钮
			add_favorites = (ImageView) activity
					.findViewById(R.id.add_favorites);
			
			// 加入收藏按钮点击事件
			add_favorites.setOnClickListener(addFavoriteListener);

			// 检查是否收藏并且设置收藏按钮的图片
			checkFavIsExsist();
			
			//点击评论跳转到评论页面
			go2Comments();
			//点击详情跳转到商品图文详情页面
			go2Details(info.getProductDetailUrl());
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(activity,
					activity.getResources().getString(R.string.bad_network),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 跳转到评论页面
	 */
	private void go2Comments() {
		RelativeLayout re_produce_comments = (RelativeLayout) activity
				.findViewById(R.id.re_produce_comments);
		re_produce_comments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, CommentActivity.class);
				intent.putExtra("goodsId", productId);
				activity.startActivity(intent);
			}
		});
	}

	/** 跳转到商品图文详情 **/
	private void go2Details(final String url) {
		RelativeLayout re_produce_details = (RelativeLayout) activity
				.findViewById(R.id.re_produce_image_detail);
		re_produce_details.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, GoodsDetailActivity.class);
				intent.putExtra("url", url);
				activity.startActivity(intent);
			}
		});
	}
	
	/**
	 * 设置购物车个数<br>
	 * 每次重新进入页面时设置购物车个数,在onResume中调用
	 * 
	 */
	public void setCartNumber(){
		cart_num = manage.getCartAmount();
		setCartNum();
	}

	/**
	 * 设置购物车的个数
	 * 
	 * @param crat_num
	 *            个数
	 */
	private void setCartNum() {
		if (cart_num == 0) {
			shopping_cart_button.setVisibility(View.GONE);
			return;
		} 
		shopping_cart_button.setVisibility(View.VISIBLE);
		shopping_cart_button.setText("" + cart_num);
	}

	/**添加到收藏夹点击事件**/
	private OnClickListener addFavoriteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(!MyApplication.getInstance().getIsLogin()){
//				Toast.makeText(activity, activity.getString(R.string.please_login), Toast.LENGTH_SHORT).show();
				Toast.makeText(activity, "请先登录！", Toast.LENGTH_SHORT).show();
				return;
			}
			isFavClick = true;
			checkFavIsExsist();
		}
	};

	/**检查是否已收藏**/
	private void checkFavIsExsist() {
		if(!MyApplication.getInstance().getIsLogin()){
			return;
		}
		String param = new JNICallBack().getHttp4CheckFav(userid, productId,
				token);
		String url = new JNICallBack().HTTPURL;

		RequestParams requestParams = new RequestParams();
		requestParams.put(param);

		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, requestParams, new AsyncHttpResponse() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				JSONObject httpResponseoObject;
				try {
					httpResponseoObject = new JSONObject(content);
					int code = httpResponseoObject.optInt("code");
					setFavBg(1 == code);
					if(!isFavClick) return;
					if(-1 == code){
						saveFav();
					} else if(1 == code){
						delFav();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
			}

		});
	}
	
	/**加入收藏**/
	private void saveFav(){
		String param = new JNICallBack().getHttp4SaveFav(userid, productId, token);
		String url = new JNICallBack().HTTPURL;
		
		RequestParams requestParams = new RequestParams();
		requestParams.put(param);
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, requestParams, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				isFavClick = false;
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode){
					JSONObject httpResultObject;
					try {
						httpResultObject = new JSONObject(content);
						int code = httpResultObject.optInt("code");
						setFavBg(1 == code);
						if (1 == code) {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.add_fav_scs),
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.add_fav_fail),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(
						activity,
						activity.getResources().getString(
								R.string.add_fav_fail),
						Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	/**删除收藏**/
	private void delFav(){
		String param = new JNICallBack().getHttp4DelFav(userid, productId, token);
		String url = new JNICallBack().HTTPURL;
		
		RequestParams requestParams = new RequestParams();
		requestParams.put(param);
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.post(url, requestParams, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				isFavClick = false;
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode){
					JSONObject httpResultObject;
					try {
						httpResultObject = new JSONObject(content);
						int code = httpResultObject.optInt("code");
						setFavBg(1 != code);
						if (1 == code) {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.del_fav_ok),
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.del_fav_fail),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(
						activity,
						activity.getResources().getString(
								R.string.del_fav_fail),
						Toast.LENGTH_SHORT).show();
			}
			
		});
	}

	/**设置收藏按钮背景**/
	private void setFavBg(boolean isExsist) {
		if (isExsist) {
			add_favorites.setImageResource(R.drawable.favorites);
		} else {
			add_favorites.setImageResource(R.drawable.unfavorites);
		}
	}

	/** 设置商品图片 **/
	private void setPicImage() {
		vpImage.setAdapter(new PicImageAdapter(activity, bannerArray));
		vpImage.setCurrentItem(0);
		vpImage.setOffscreenPageLimit(2);
	}

	/**
	 * 设置推荐产品
	 * 
	 * @param bannerArray
	 */
	private void addMatchImage(final ArrayList<MainProduct> bannerArray) {
		try {
			int lenght = bannerArray.size();
			float dp = 120f;
			for (int i = 0; i < lenght; i++) {
				LinearLayout.LayoutParams lp_base = new LinearLayout.LayoutParams(
						(int) DPIUtil.dpToPixel(dp, activity),
						(int) DPIUtil.dpToPixel(dp, activity));// LayoutParams.WRAP_CONTENT);
				lp_base.gravity = Gravity.CENTER;
				ImageView bannerImageView = new ImageView(activity);
				bannerImageView.setLayoutParams(lp_base);
				
				ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
				ImageLoader.getInstance().displayImage(
						bannerArray.get(i).getImgUrl(), bannerImageView,
						options,
						MyApplication.getInstance().getImageLoadingListener());
				matchGoodsImageLayout.setPadding(20, 0, 80, 0);
				bannerImageView.setPadding(20, 0, 20, 0);
				final int index = i;
				bannerImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(activity,
								GoodsInfoActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("goodsId", bannerArray.get(index)
								.getUId());
						intent.putExtras(bundle);
						activity.startActivity(intent);
						activity.finish();
					}
				});
				matchGoodsImageLayout.addView(bannerImageView, lp_base);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(activity,
					activity.getResources().getString(R.string.bad_network),
					Toast.LENGTH_LONG).show();
		}
	}

	private void initOptions() {
		options = MyApplication.getInstance().initGoodsImageOption();
	}
}
