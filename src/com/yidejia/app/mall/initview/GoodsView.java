package com.yidejia.app.mall.initview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yidejia.app.mall.GoodsInfoActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.fragment.CartActivity;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.view.GoCartActivity;
import com.yidejia.app.mall.view.PayActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class GoodsView {
	private View view;
	private int width;
	private int cart_num = 0;
	private Activity activity;

	public GoodsView(Activity activity, View view, int width) {
		this.view = view;
		this.width = width;
		this.activity = activity;
		initDisplayImageOption();
		Log.i("width", this.width + "");
	}

	public void initGoodsView(ProductBaseInfo info) {
		if (info == null)
			return;
		final Cart cart = new Cart();
		cart.setSalledAmmount(1);
		cart.setUId(info.getUId());
		cart.setImgUrl(info.getImgUrl());
		// 商品名称
		TextView base_info_content_text = (TextView) view
				.findViewById(R.id.base_info_content_text);
		String name = info.getName();
		base_info_content_text.setText(name);
		cart.setProductText(name);
		// 价格
		TextView price = (TextView) view.findViewById(R.id.price);
		String priceString = info.getPrice();
		try {
			float priceNum = Float.parseFloat(priceString);
			cart.setPrice(priceNum);
			price.setText(priceString);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(activity, "价格出错，请联系我们的客服进行修改！", Toast.LENGTH_SHORT)
					.show();
			price.setText("");
		}
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
		product_id_num_text.setText(info.getProductNumber());
		// 规格
		TextView standard_content_text = (TextView) view
				.findViewById(R.id.standard_content_text);
		standard_content_text.setText(info.getProductSpecifications());
		bannerArray = info.getBannerArray();
		addBaseImage(view, bannerArray);
		recommendArray = info.getRecommendArray();
		addMatchImage(view, recommendArray);

		final Button shopping_cart_button = (Button) view
				.findViewById(R.id.shopping_cart_button);

		shopping_cart_button.setVisibility(View.GONE);
		shopping_cart_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				builder.show();
				
			}
		});
		
		add_to_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shopping_cart_button.setVisibility(view.VISIBLE);
				cart_num++;
				shopping_cart_button.setText("" + cart_num);
				CartsDataManage manage = new CartsDataManage();
				manage.addCart(cart);
			}
		});

		buy_now.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, PayActivity.class);
				Bundle bundle = new Bundle();
				intent.putExtras(bundle);
				activity.startActivity(intent);
			}
		});

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
			}
		});
	}

	private LinearLayout baseInfoImageLayout;
	private LinearLayout matchGoodsImageLayout;
	private ArrayList<BaseProduct> bannerArray;
	private ArrayList<MainProduct> recommendArray;

	private void addBaseImage(View view, ArrayList<BaseProduct> bannerArray) {
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
					(new Float(px)).intValue(), LayoutParams.WRAP_CONTENT);
			// View imageViewLayout = LayoutInflater.from(view.getContext())
			// .inflate(R.layout.goods_banner_imageview, null);
			// ImageView bannerImageView = (ImageView) imageViewLayout
			// .findViewById(R.id.banner_imageview);
			ImageView bannerImageView = new ImageView(activity);
			bannerImageView.setLayoutParams(lp_base);
			imageLoader.displayImage(bannerArray.get(i).getImgUrl(),
					bannerImageView, options, animateFirstListener);
			baseInfoImageLayout.setPadding(10, 0, 10, 0);
			// imageViewLayout.setPadding(10, 0, 10, 0);
			bannerImageView.setPadding(10, 0, 10, 0);
			baseInfoImageLayout.addView(bannerImageView, lp_base);
		}
	}

	private void addMatchImage(View view,
			final ArrayList<MainProduct> bannerArray) {
		matchGoodsImageLayout = (LinearLayout) view
				.findViewById(R.id.match_goods_image);
		int lenght = bannerArray.size();
		Resources r = activity.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90,
				r.getDisplayMetrics());
		for (int i = 0; i < lenght; i++) {
			LinearLayout.LayoutParams lp_base = new LinearLayout.LayoutParams(
					(new Float(px)).intValue(), LayoutParams.WRAP_CONTENT);
			// View imageViewLayout =
			// LayoutInflater.from(view.getContext()).inflate(R.layout.goods_banner_imageview,
			// null);
			// ImageView bannerImageView = (ImageView)
			// imageViewLayout.findViewById(R.id.banner_imageview);
			ImageView bannerImageView = new ImageView(activity);
			bannerImageView.setLayoutParams(lp_base);
			imageLoader.displayImage(bannerArray.get(i).getImgUrl(),
					bannerImageView, options, animateFirstListener);
			matchGoodsImageLayout.setPadding(10, 0, 10, 0);
			// imageViewLayout.setPadding(10, 0, 10, 0);
			bannerImageView.setPadding(10, 0, 10, 0);
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
	private AlertDialog builder;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private void initDisplayImageOption() {
		 builder = new AlertDialog.Builder(activity)
				.setTitle("添加成功")
				.setMessage("商品以成功加入购物车")
				.setPositiveButton("去购物车",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(activity,GoCartActivity.class);
								activity.startActivity(intent);
							}
						}).setNegativeButton("再逛逛", null).create();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}

}
