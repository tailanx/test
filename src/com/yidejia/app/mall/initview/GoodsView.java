package com.yidejia.app.mall.initview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.BrowseHistoryDataManage;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.FavoriteDataManage;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.CstmPayActivity;
import com.yidejia.app.mall.view.GoCartActivity;
import com.yidejia.app.mall.view.LoginActivity;

public class GoodsView {
	private View view;
	private int width;
	private CartsDataManage manage;
	private int cart_num = 0;// 购物车内商品个数
	private Activity activity;

	private String productId;// 本商品id
	private String userid;// 本用户id
	private boolean isLogin;// 用户是否登录
	private AlertDialog builder;
	private MyApplication myApplication;
	private BrowseHistoryDataManage historyDataManage;

	public GoodsView(Activity activity, View view, int width) {
		this.view = view;
		this.width = width;
		this.activity = activity;
		initDisplayImageOption();
		Log.i("width", this.width + "");
		myApplication = (MyApplication)activity.getApplication();
		userid = myApplication.getUserId();
		isLogin = myApplication.getIsLogin();
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
			manage = new CartsDataManage();
			historyDataManage = new BrowseHistoryDataManage();
			cart_num = manage.getCartAmount();
			final Cart cart = new Cart();
			cart.setSalledAmmount(1);
			productId = info.getUId();
			cart.setUId(productId);
			cart.setImgUrl(info.getImgUrl());
			// 商品名称
			TextView base_info_content_text = (TextView) view
					.findViewById(R.id.base_info_content_text);
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
				price.setText(priceString + activity.getResources().getString(R.string.unit));
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(activity, activity.getResources().getString(R.string.price_error), Toast.LENGTH_SHORT)
						.show();
				price.setText("");
			}
			//添加到浏览历史中去
			
			historyDataManage.addHistory(info);
			
			ImageView buy_now = (ImageView) view.findViewById(R.id.buy_now);
			ImageView add_to_cart = (ImageView) view.findViewById(R.id.add_to_cart);
			// 销售额
			TextView selled_num_text = (TextView) view
					.findViewById(R.id.selled_num_text);
			selled_num_text.setText(info.getSalledAmmount());
			// 评论数
			TextView emulate_num_text = (TextView) view
					.findViewById(R.id.emulate_num_text);
			emulate_num_text.setText(info.getCommentAmount());
			// 晒单数
			TextView show_num_text = (TextView) view
					.findViewById(R.id.show_num_text);
			show_num_text.setText(info.getShowListAmount());
			// 品牌
			TextView brand_name_text = (TextView) view
					.findViewById(R.id.brand_name_text);
			brand_name_text.setText(info.getBrands());
			// 商品编号
			TextView product_id_num_text = (TextView) view
					.findViewById(R.id.product_id_num_text);
			if(info.getProductNumber()==null||"".equals(info.getProductNumber())){
				product_id_num_text.setText(activity.getResources().getString(R.string.none_code));
			}else{
				product_id_num_text.setText(info.getProductNumber());
			}
			// 规格
			TextView standard_content_text = (TextView) view
					.findViewById(R.id.standard_content_text);
			standard_content_text.setText(info.getProductSpecifications());
			//推荐购物view group
			matchGoodsImageLayout = (LinearLayout) view
					.findViewById(R.id.match_goods_image);
			bannerArray = info.getBannerArray();
			addBaseImage(view, bannerArray);
			recommendArray = info.getRecommendArray();
			if(recommendArray.isEmpty()){
				//推荐搭配不可见
				matchGoodsImageLayout.setVisibility(ViewGroup.GONE);
				TextView match_goods_tip = (TextView) view.findViewById(R.id.match_goods);
				match_goods_tip.setVisibility(View.GONE);
			} else{
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
			if (priceNum > 0) {
				add_to_cart.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cart_num++;
						setCartNum(cart_num);
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
				add_to_cart.setImageResource(R.drawable.add_to_cart_hover);
				add_to_cart.setClickable(false);
				buy_now.setImageResource(R.drawable.buy_now_hover);
				buy_now.setClickable(false);
			}
			// 立即购买按钮
			buy_now.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(activity, CstmPayActivity.class);
					try{
						float sum = Float.parseFloat(priceString);
						if(sum <= 0) return;////价格出错
						if(!myApplication.getIsLogin()){
							Toast.makeText(activity,
									activity.getResources().getString(R.string.please_login),
									Toast.LENGTH_LONG).show();
							Intent intent1 = new Intent(activity, LoginActivity.class);
							activity.startActivity(intent1);
						}else{
						Bundle bundle = new Bundle();
						ArrayList<Cart> carts = new ArrayList<Cart>();
						carts.add(cart);
//						bundle.putSerializable("Cart", cart);
						bundle.putString("cartActivity","N");
						intent.putExtra("carts", carts);
						bundle.putString("price", priceString);
						intent.putExtras(bundle);
						activity.startActivity(intent);
						}
//						activity.finish();
					} catch (NumberFormatException e){
						//价格出错
					}
				}
			});
			// 购物车按钮
			RelativeLayout shopping_cart_in_goodsinfo = (RelativeLayout) view
					.findViewById(R.id.shopping_cart_in_goodsinfo);
			shopping_cart_in_goodsinfo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(activity, GoCartActivity.class);
					// Bundle bundle = new Bundle();
					// intent.putExtras(bundle);
					activity.startActivity(intent);
//					activity.finish();
				}
			});

			// 加入收藏按钮
			add_favorites = (ImageView) view.findViewById(R.id.add_favorites);
			add_favorites.setOnClickListener(addFavoriteListener);
			// 检查是否收藏并且设置收藏按钮的图片
			FavoriteDataManage favoriteManage = new FavoriteDataManage(activity);
			if (isLogin && !"".equals(userid)) {
				if (favoriteManage.checkExists(userid, productId, myApplication.getToken())) {
					add_favorites.setImageResource(R.drawable.add_favorites2);
//				Toast.makeText(activity, "yes", Toast.LENGTH_LONG).show();
				} else {
					add_favorites.setImageResource(R.drawable.add_favorites1);
//				Toast.makeText(activity, "no", Toast.LENGTH_LONG).show();
				}
			} else {
				add_favorites.setImageResource(R.drawable.add_favorites1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(activity, activity.getResources().getString(R.string.bad_network), Toast.LENGTH_LONG).show();
		}
	}

	private ImageView add_favorites;// 加入收藏的按钮

	// 跳转购物车的按钮
	private Button shopping_cart_button;

	private void setCartNum(int crat_num) {
		shopping_cart_button.setVisibility(view.VISIBLE);
		shopping_cart_button.setText("" + cart_num);
	}

	private LinearLayout baseInfoImageLayout;
	private LinearLayout matchGoodsImageLayout;
	private ArrayList<BaseProduct> bannerArray;
	private ArrayList<MainProduct> recommendArray;

	private void addBaseImage(View view, ArrayList<BaseProduct> bannerArray) {
		try {
			baseInfoImageLayout = (LinearLayout) view
					.findViewById(R.id.base_info_image_linear_layout);

			int lenght = bannerArray.size();
			// child.setId(BASE_IMAGE_ID);
			// Log.e(TAG, TAG+child.getId());
			// Log.e(TAG, TAG+baseInfoImageLayout.getId());
			Resources r = activity.getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150,
					r.getDisplayMetrics());
			for (int i = 0; i < lenght; i++) {
				// ImageView matchchild = (ImageView)
				// getSherlockActivity().getLayoutInflater().inflate(R.layout.item_base_image,
				// null);
				// ImageView child = (ImageView)
				// getSherlockActivity().getLayoutInflater().inflate(R.layout.item_base_image,
				// null);
				// ImageView child = new ImageView();
				// ImageView matchchild = new ImageView(getSherlockActivity());
				// int base_px =
				// getResources().getDimensionPixelSize(R.dimen.base_info_image);

				// int imageDimen = (int)
				// getResources().getDimension(R.dimen.base_info_image);
				// lp_base.setMargins(imageDimen, imageDimen, imageDimen,
				// imageDimen);
				// LinearLayout.LayoutParams lp_match = new
				// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				// LayoutParams.MATCH_PARENT, 1);
				// int matchDimen = (int)
				// getResources().getDimension(R.dimen.base_info_match);
				// lp_match.setMargins(matchDimen, matchDimen, matchDimen,
				// matchDimen);
				// child.setLayoutParams(lp_base);
				// child.setImageResource(R.drawable.product_photo1);
				// matchchild.setLayoutParams(lp_match);
				// matchchild.setImageResource(R.drawable.product_photo2);
				// baseInfoImageLayout.addView(child, lp_base);//
				// matchGoodsImageLayout.addView(matchchild, lp_match);//
				LinearLayout.LayoutParams lp_base = new LinearLayout.LayoutParams(
						(new Float(px)).intValue(), (new Float(px)).intValue());//LayoutParams.WRAP_CONTENT);//(new Float(px)).intValue()
				lp_base.gravity = Gravity.CENTER;
				// View imageViewLayout = LayoutInflater.from(view.getContext())
				// .inflate(R.layout.goods_banner_imageview, null);
				// ImageView bannerImageView = (ImageView) imageViewLayout
				// .findViewById(R.id.banner_imageview);
				ImageView bannerImageView = new ImageView(activity);
				bannerImageView.setLayoutParams(lp_base);
				imageLoader.displayImage(bannerArray.get(i).getImgUrl(),
						bannerImageView, options, animateFirstListener);
				Log.e(GoodsView.class.getName(), bannerArray.get(i).getImgUrl());
				baseInfoImageLayout.setPadding(20, 0, 20, 0);
				
				// imageViewLayout.setPadding(10, 0, 10, 0);
				bannerImageView.setPadding(20, 0, 20, 0);
				baseInfoImageLayout.addView(bannerImageView, lp_base);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(activity, activity.getResources().getString(R.string.bad_network), Toast.LENGTH_LONG).show();
		}
	}

	private void addMatchImage(View view,
			final ArrayList<MainProduct> bannerArray) {
		try {
			int lenght = bannerArray.size();
			Resources r = activity.getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90,
					r.getDisplayMetrics());
			for (int i = 0; i < lenght; i++) {
				LinearLayout.LayoutParams lp_base = new LinearLayout.LayoutParams(
						(new Float(px)).intValue(), (new Float(px)).intValue());//LayoutParams.WRAP_CONTENT);
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
						bundle.putString("goodsId", bannerArray.get(index).getUId());
						intent.putExtras(bundle);
//						activity.notifyAll();
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
			Toast.makeText(activity, activity.getResources().getString(R.string.bad_network), Toast.LENGTH_LONG).show();
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
		.setTitle(activity.getResources().getString(R.string.login_title))
		.setMessage(activity.getResources().getString(R.string.login_acount))
		.setPositiveButton(activity.getResources().getString(R.string.go2cart),
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(activity,
								LoginActivity.class);
						activity.startActivity(intent);
						activity.finish();
					}
				}).setNegativeButton(activity.getResources().getString(R.string.guang_again), null).create();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}

	private boolean flag = false;
	/**
	 *  加入收藏按钮
	 */
	private OnClickListener addFavoriteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FavoriteDataManage manage = new FavoriteDataManage(activity);
			if (!isLogin) {
				builder.show();

			} else if (isLogin && !"".equals(userid)) {
				// 登录状态下
				if (!manage.checkExists(userid, productId,
						myApplication.getToken())) {
					// 未收藏，现在添加收藏
					if (manage.addFavourite(userid, productId,
							myApplication.getToken())) {
						// 收藏成功
						Toast.makeText(
								activity,
								activity.getResources().getString(
										R.string.add_fav_scs),
								Toast.LENGTH_SHORT).show();
						add_favorites
								.setImageResource(R.drawable.add_favorites2);
					} else {
						Toast.makeText(
								activity,
								activity.getResources().getString(
										R.string.add_fav_fail),
								Toast.LENGTH_SHORT).show();
						add_favorites
								.setImageResource(R.drawable.add_favorites1);
					}
				} else {
					// 已收藏，现在删除收藏
					if (manage.deleteFavourite(userid, productId,
							myApplication.getToken())) {
						// 删除成功
						add_favorites
								.setImageResource(R.drawable.add_favorites1);
						Toast.makeText(activity, activity.getResources().getString(R.string.del_fav_ok), Toast.LENGTH_LONG).show();
					} else {
						// 删除失败
						add_favorites
								.setImageResource(R.drawable.add_favorites2);
					}
				}
			} else {
				// 未登录状态下，收藏到本地
				// 改变图片
//				flag = !flag;
//				changeFravoriteBg();
			}
		}
	};

	/**
	 * 改变收藏的图片
	 */
	private void changeFravoriteBg() {
		// 改变图片
		if (flag) {
			add_favorites.setImageResource(R.drawable.add_favorites2);
		} else {
			add_favorites.setImageResource(R.drawable.add_favorites1);
		}

	}

}