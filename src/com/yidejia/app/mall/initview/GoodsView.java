package com.yidejia.app.mall.initview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.BrowseHistoryDataManage;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.FavoriteDataManage;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.favorite.CheckExistsFavorite;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.CstmPayActivity;
import com.yidejia.app.mall.view.GoCartActivity;
import com.yidejia.app.mall.view.ImagePagerActivity;
import com.yidejia.app.mall.view.LoginActivity;

public class GoodsView {
	private View view;
	private int width;
	private CartsDataManage manage;
	private int cart_num = 0;// 购物车内商品个数
	private Activity activity;
	private ViewGroup anim_mask_layout;
	private ImageView imgIcon;
	private DisplayMetrics displayMetrics;

	private String productId;// 本商品id
	private String userid;// 本用户id
	private boolean isLogin;// 用户是否登录
	private AlertDialog builder;
	private MyApplication myApplication;
	private BrowseHistoryDataManage historyDataManage;
	private HorizontalScrollView horizontalScrollView;

	public GoodsView(Activity activity, View view, int width) {
		this.view = view;
		this.width = width;
		this.activity = activity;
		initDisplayImageOption();
		Log.i("width", this.width + "");
		myApplication = (MyApplication) activity.getApplication();
		userid = myApplication.getUserId();
		isLogin = myApplication.getIsLogin();
		anim_mask_layout = createAnimLayout();
		displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
//			add_to_cart_press = (ImageView) activity.findViewById(R.id.add_to_cart_press);
			
			manage = new CartsDataManage();
			
			historyDataManage = new BrowseHistoryDataManage();
			cart_num = manage.getCartAmount();
			final Cart cart = new Cart();
			cart.setSalledAmmount(1);
			productId = info.getUId();
			cart.setUId(productId);
			cart.setImgUrl(info.getImgUrl());
			//
//			imgIcon = (ImageView) view.findViewById(R.id.iv_item_goods_detail);
			// 商品名称
			TextView base_info_content_text = (TextView) view
					.findViewById(R.id.tv_item_goods_base_produce_detail);
			String name = info.getName();
			base_info_content_text.setText(name);
			cart.setProductText(name);
			// 价格
			TextView price = (TextView) view.findViewById(R.id.price);
			final String priceString = info.getPrice();
			float priceNum = 0.0f;
			try {
				priceNum = Float.parseFloat(priceString);
				cart.setPrice(priceNum);
				price.setText(priceString
						+ activity.getResources().getString(R.string.unit));
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(
						activity,
						activity.getResources().getString(R.string.price_error),
						Toast.LENGTH_SHORT).show();
				price.setText("");
			}
			// 添加到浏览历史中去

			historyDataManage.addHistory(info);

			ImageView add_to_cart = (ImageView) view
					.findViewById(R.id.iv_add_to_cart);
			// 销售额
			TextView selled_num_text = (TextView) view
					.findViewById(R.id.tv_selled_num_text);
			selled_num_text.setText(info.getSalledAmmount());
			// 评论数
			TextView emulate_num_text = (TextView) view
					.findViewById(R.id.tv_emulate_num_text);
			emulate_num_text.setText(info.getCommentAmount());
			// 晒单数
			TextView show_num_text = (TextView) view
					.findViewById(R.id.tv_show_num_text);
			show_num_text.setText(info.getShowListAmount());
			// 品牌
			TextView brand_name_text = (TextView) view
					.findViewById(R.id.tv_brand_name_text);
			brand_name_text.setText(info.getBrands());
			// 商品编号
			TextView product_id_num_text = (TextView) view
					.findViewById(R.id.tv_product_id_num_text);
			if (info.getProductNumber() == null
					|| "".equals(info.getProductNumber())) {
				product_id_num_text.setText(activity.getResources().getString(
						R.string.none_code));
			} else {
				product_id_num_text.setText(info.getProductNumber());
			}
			// 规格
			TextView standard_content_text = (TextView) view
					.findViewById(R.id.standard_content_text);
			standard_content_text.setText(info.getProductSpecifications());
			// 推荐购物view group
			matchGoodsImageLayout = (LinearLayout) view
					.findViewById(R.id.ll_match_goods_image);
			bannerArray = info.getBannerArray();
			recommendArray = info.getRecommendArray();
			if (recommendArray.isEmpty()) {
				// 推荐搭配不可见
				matchGoodsImageLayout.setVisibility(ViewGroup.GONE);
				TextView match_goods_tip = (TextView) view
						.findViewById(R.id.match_goods);
				match_goods_tip.setVisibility(View.GONE);
			} else {
				addMatchImage(view, recommendArray);
			}
			// 购物车个数
			shopping_cart_button = (Button) view
					.findViewById(R.id.shopping_cart_button);
			// CartsDataManage cartsDataManage = new CartsDataManage();
			cart_num = manage.getCartAmount();
			if (cart_num == 0) {
				shopping_cart_button.setVisibility(View.GONE);
			} else {
				setCartNum(cart_num);
			}
			// 加入购物车按钮点击事件
			if (priceNum > 0.01) {
				imgIcon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						add_to_cart_press.setVisibility(View.VISIBLE);
//						setAnim(add_to_cart_press);
//						add_to_cart_press.setVisibility(View.GONE);
						cart_num++;
						boolean istrue = manage.addCart(cart);
						Intent intent = new Intent(Consts.UPDATE_CHANGE);
						activity.sendBroadcast(intent);
						if (istrue) {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.add_cart_scs),
									Toast.LENGTH_SHORT).show();
						}
					}
					// Log.i("info", istrue+"   cart_num");
					// if (istrue) {
					// builder.show();
					// }

				});
			} else {
				Toast.makeText(activity, "这是赠品，不能够购买", Toast.LENGTH_LONG)
						.show();
//				add_to_cart.setImageResource(R.drawable.add_to_cart_hover);
				add_to_cart.setClickable(false);
			
			}
			
			// 购物车按钮
			RelativeLayout shopping_cart_in_goodsinfo = (RelativeLayout) view
					.findViewById(R.id.shopping_cart_in_goodsinfo);
			shopping_cart_in_goodsinfo
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(activity,
									GoCartActivity.class);
							// Bundle bundle = new Bundle();
							// intent.putExtras(bundle);
							activity.startActivity(intent);
							// activity.finish();
						}
					});

			// 加入收藏按钮
			add_favorites = (ImageView) view.findViewById(R.id.add_favorites);
			add_favorites.setOnClickListener(addFavoriteListener);

			// 检查是否收藏并且设置收藏按钮的图片
//			FavoriteDataManage favoriteManage = new FavoriteDataManage(activity);
//			if (myApplication.getIsLogin() && !"".equals(userid)) {
//				if (favoriteManage.checkExists(userid, productId,
//						myApplication.getToken())) {
//					add_favorites.setImageResource(R.drawable.add_favorites2);
//					// Toast.makeText(activity, "yes",
//					// Toast.LENGTH_LONG).show();
//				} else {
//					add_favorites.setImageResource(R.drawable.add_favorites1);
//					// Toast.makeText(activity, "no", Toast.LENGTH_LONG).show();
//				}
//			} else {
//				add_favorites.setImageResource(R.drawable.add_favorites1);
//			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(activity,
					activity.getResources().getString(R.string.bad_network),
					Toast.LENGTH_LONG).show();
		}
	}

	private ImageView add_favorites;// 加入收藏的按钮
	private Button shopping_cart_button;	// 跳转购物车的按钮
	
//	private ImageView add_to_cart_press;//加入购物车动画图片

	

	private void setCartNum(int crat_num) {
		shopping_cart_button.setVisibility(view.VISIBLE);
		shopping_cart_button.setText("" + cart_num);
	}

	private LinearLayout baseInfoImageLayout;
	private LinearLayout matchGoodsImageLayout;
	private ArrayList<BaseProduct> bannerArray;
	private ArrayList<MainProduct> recommendArray;
	private int index;
	private void addMatchImage(View view,
			final ArrayList<MainProduct> bannerArray) {
		try {
			int lenght = bannerArray.size();
			Resources r = activity.getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					90, r.getDisplayMetrics());
			for (int i = 0; i < lenght; i++) {
				LinearLayout.LayoutParams lp_base = new LinearLayout.LayoutParams(
						(new Float(px)).intValue(), (new Float(px)).intValue());// LayoutParams.WRAP_CONTENT);
				// View imageViewLayout =
				// LayoutInflater.from(view.getContext()).inflate(R.layout.goods_banner_imageview,
				// null);
				// ImageView bannerImageView = (ImageView)
				// imageViewLayout.findViewById(R.id.banner_imageview);
				lp_base.gravity = Gravity.CENTER;
				ImageView bannerImageView = new ImageView(activity);
				bannerImageView.setLayoutParams(lp_base);
				imageLoader.displayImage(bannerArray.get(i).getImgUrl(),
						bannerImageView, options, animateFirstListener);
				matchGoodsImageLayout.setPadding(80, 0, 80, 0);
				// imageViewLayout.setPadding(10, 0, 10, 0);
				bannerImageView.setPadding(20, 0, 20, 0);
				final int index = i;
				bannerImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(activity,
								GoodsInfoActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("goodsId", bannerArray.get(index)
								.getUId());
						intent.putExtras(bundle);
						// activity.notifyAll();
						activity.startActivity(intent);
						activity.finish();
					}
				});
				// TextView nameTextView = (TextView)
				// imageViewLayout.findViewById(R.id.banner_name);
				// nameTextView.setText(bannerArray.get(i).getTitle());
				// TextView priceTextView = (TextView)
				// imageViewLayout.findViewById(R.id.banner_price);
				// priceTextView.setText(bannerArray.get(i).getPrice());
				matchGoodsImageLayout.addView(bannerImageView, lp_base);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(activity,
					activity.getResources().getString(R.string.bad_network),
					Toast.LENGTH_LONG).show();
		}
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

	private DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private void initDisplayImageOption() {
		builder = new AlertDialog.Builder(activity)
				.setTitle(
						activity.getResources().getString(R.string.login_title))
				.setMessage(
						activity.getResources()
								.getString(R.string.login_acount))
				.setPositiveButton(
						activity.getResources().getString(R.string.go2cart),
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(activity,
										LoginActivity.class);
								activity.startActivity(intent);
								// activity.finish();
							}
						})
				.setNegativeButton(
						activity.getResources().getString(R.string.guang_again),
						null).create();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
	}

	private boolean flag = false;
	/**
	 * 加入收藏按钮
	 */
	private OnClickListener addFavoriteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FavoriteDataManage manage = new FavoriteDataManage(activity);
			if (!myApplication.getIsLogin()) {

				builder.show();

			} else if (myApplication.getIsLogin() && !"".equals(userid)) {

//				// 登录状态下
//				if (!manage.checkExists(userid, productId,
//						myApplication.getToken())) {
//					
//				} else {
//
//					
//				}
				if(!ConnectionDetector.isConnectingToInternet(activity)){
					Toast.makeText(activity, activity.getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
					return;
				}
				closeCheckFavTask();
				checkFavTask = new CheckFavTask();
				checkFavTask.execute();
			} else {

				// 未登录状态下，收藏到本地
				// 改变图片
				// flag = !flag;
				// changeFravoriteBg();
			}
		}
	};
	
	
	private CheckFavTask checkFavTask;
	
	private void closeCheckFavTask(){
		if(null != checkFavTask && AsyncTask.Status.RUNNING == checkFavTask.getStatus().RUNNING) {
			checkFavTask.cancel(true);
		}
	}
	
	private class CheckFavTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			CheckExistsFavorite check = new CheckExistsFavorite(activity);
			try {
				String httpResponse = check.httpResponse(userid, productId, myApplication.getToken());
				Log.i(TAG, httpResponse);
				JSONObject httpResponseoObject = new JSONObject(httpResponse);
				int code = httpResponseoObject.getInt("code");
				Log.i(TAG, "check exists code" + code);
				if(code == 1) return true;
				else {
					return false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "check favorite exists task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "check favorite exists task ex");
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			FavoriteDataManage manage = new FavoriteDataManage(activity);
			if(!result) {
				// 未收藏，现在添加收藏
				if (manage.addFavourite(myApplication.getUserId(),
						productId, myApplication.getToken())) {
					// 收藏成功
					Toast.makeText(
							activity,
							activity.getResources().getString(
									R.string.add_fav_scs),
							Toast.LENGTH_SHORT).show();
//					add_favorites
//							.setImageResource(R.drawable.add_favorites2);
				} else {
					Toast.makeText(
							activity,
							activity.getResources().getString(
									R.string.add_fav_fail),
							Toast.LENGTH_SHORT).show();
//					add_favorites
//							.setImageResource(R.drawable.add_favorites1);
				}
			} else {
				// 已收藏，现在删除收藏
				if (manage.deleteFavourite(userid, productId,
						myApplication.getToken())) {
					// 删除成功
//					add_favorites
//							.setImageResource(R.drawable.add_favorites1);
					Toast.makeText(
							activity,
							activity.getResources().getString(
									R.string.del_fav_ok), Toast.LENGTH_LONG)
							.show();
				} else {
					// 删除失败
//					add_favorites
//							.setImageResource(R.drawable.add_favorites2);
				}
			}
		}
		
	}
	
	private String TAG = GoodsView.class.getName();

	/**
	 * 改变收藏的图片
	 */
	private void changeFravoriteBg() {
		// 改变图片
//		if (flag) {
//			add_favorites.setImageResource(R.drawable.add_favorites2);
//		} else {
//			add_favorites.setImageResource(R.drawable.add_favorites1);
//		}

	}

	/**
	 * @Description: 创建动画层
	 * @param
	 * @return void
	 * @throws
	 */
	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		animLayout.setLayoutParams(lp);
//		animLayout.setId(R.id.age);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}
	/**
	 * 
	 * @param v
	 */
	private void setAnim(View v){
		Animation mScaleAnimation = new ScaleAnimation(0.7f, 0.1f, 0.7f, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f);
		mScaleAnimation.setDuration(AnimationDuration);
		mScaleAnimation.setFillAfter(true);

		int[] start_location = new int[2];
		v.getLocationInWindow(start_location);
		int x = v.getWidth();
		defaultWidth = displayMetrics.widthPixels;
		marginRight = defaultWidth - x - start_location[0];
		ViewGroup vg = (ViewGroup) v.getParent();
		vg.removeView(v);
		// 将组件添加到我们的动画层上
		View view = addViewToAnimLayout(anim_mask_layout, v,start_location);
		int[] end_location = new int[2];
		shopping_cart_button.getLocationInWindow(end_location);
		// 计算位移
		int endX = end_location[0] - start_location[0];
		int endY = end_location[1] - start_location[1];


		Animation mTranslateAnimation = new TranslateAnimation(0,endX,0,endY);// 移动
		mTranslateAnimation.setDuration(AnimationDuration);
		
		AnimationSet mAnimationSet = new AnimationSet(false);
		// 这块要注意，必须设为false,不然组件动画结束后，不会归位。
		mAnimationSet.setFillAfter(false);
		mAnimationSet.addAnimation(mScaleAnimation);
		mAnimationSet.addAnimation(mTranslateAnimation);
		view.startAnimation(mAnimationSet);
		
		mTranslateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {

				setCartNum(cart_num);
				
			}
		});
			
		
	}
	/**
	 * @Description: 添加视图到动画层
	 * @param @param vg
	 * @param @param view
	 * @param @param location
	 * @param @return
	 * @return View
	 * @throws
	 */
	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		vg.addView(view);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.rightMargin = marginRight;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}
	
	/**
	 * 动画时间
	 */
	private int AnimationDuration = 500;
	/**
	 * 手机屏幕的宽度
	 */
	private int defaultWidth = 0;
	/**
	 * 加入购物车按钮离右边的距离
	 */
	private int marginRight = 0;
}