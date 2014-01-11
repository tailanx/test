package com.yidejia.app.mall.goodinfo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.BrowseHistoryDataManage;
import com.yidejia.app.mall.datamanage.CartsDataManage;
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
	private Activity activity;
	// private ImageView imgIcon;
	private ImageView add_favorites; // 加入收藏的按钮
	private Button shopping_cart_button; // 跳转购物车的按钮
	private LinearLayout matchGoodsImageLayout;
	private ArrayList<BaseProduct> bannerArray;
	private ArrayList<MainProduct> recommendArray;
	private DisplayImageOptions options;

	private YLViewPager vpImage; // 商品图片容器

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
			shopping_cart_button = (Button) activity
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
				add_to_cart.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						cart_num++;
						boolean istrue = manage.addCart(cart);
						if (istrue) {
							Toast.makeText(
									activity,
									activity.getResources().getString(
											R.string.add_cart_scs),
									Toast.LENGTH_SHORT).show();
						}
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
			// TODO
			// 加入收藏按钮点击事件add_favorites.setOnClickListener(addFavoriteListener);

			// // 检查是否收藏并且设置收藏按钮的图片
			// FavoriteDataManage favoriteManage = new
			// FavoriteDataManage(activity);
			// if (MyApplication.getInstance().getIsLogin() &&
			// !"".equals(userid)) {
			// if (favoriteManage.checkExists(userid, productId,
			// MyApplication.getInstance().getToken())) {
			// add_favorites.setImageResource(R.drawable.add_favorites2);
			// } else {
			// add_favorites.setImageResource(R.drawable.add_favorites1);
			// }
			// } else {
			// add_favorites.setImageResource(R.drawable.add_favorites1);
			// }

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(activity,
					activity.getResources().getString(R.string.bad_network),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 设置购物车的个数
	 * 
	 * @param crat_num
	 *            个数
	 */
	private void setCartNum(int crat_num) {
		shopping_cart_button.setVisibility(View.VISIBLE);
		shopping_cart_button.setText("" + cart_num);
	}

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
						(int) DPIUtil.convertDpToPixel(dp, activity),
						(int) DPIUtil.convertDpToPixel(dp, activity));// LayoutParams.WRAP_CONTENT);
				lp_base.gravity = Gravity.CENTER;
				ImageView bannerImageView = new ImageView(activity);
				bannerImageView.setLayoutParams(lp_base);
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
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_bg)
				.showImageOnFail(R.drawable.image_bg)
				.showImageForEmptyUri(R.drawable.image_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
	}
}
