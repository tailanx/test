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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.datamanage.FavoriteDataManage;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.view.GoCartActivity;
import com.yidejia.app.mall.view.LoginActivity;
import com.yidejia.app.mall.view.PayActivity;

public class GoodsView {
	private View view;
	private int width;
	private CartsDataManage manage;
	private int cart_num = 0;// ���ﳵ����Ʒ����
	private Activity activity;

	private String productId;// ����Ʒid
	private String userid;// ���û�id
	private boolean isLogin;// �û��Ƿ��¼
	private AlertDialog builder;

	public GoodsView(Activity activity, View view, int width) {
		this.view = view;
		this.width = width;
		this.activity = activity;
		initDisplayImageOption();
		Log.i("width", this.width + "");
		MyApplication myApplication = new MyApplication();
		userid = myApplication.getUserId();
		isLogin = myApplication.getIsLogin();
	}

	/**
	 * ��Ʒ��Ϣҳ
	 * 
	 * @param info
	 */
	public void initGoodsView(ProductBaseInfo info) {
		try {
			if (info == null)
				return;
			manage = new CartsDataManage();
			cart_num = manage.getCartAmount();
			final Cart cart = new Cart();
			cart.setSalledAmmount(1);
			productId = info.getUId();
			cart.setUId(productId);
			cart.setImgUrl(info.getImgUrl());
			// ��Ʒ����
			TextView base_info_content_text = (TextView) view
					.findViewById(R.id.base_info_content_text);
			String name = info.getName();
			base_info_content_text.setText(name);
			cart.setProductText(name);
			// �۸�
			TextView price = (TextView) view.findViewById(R.id.price);
			final String priceString = info.getPrice();
			try {
				float priceNum = Float.parseFloat(priceString);
				cart.setPrice(priceNum);
				price.setText(priceString + "Ԫ");
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(activity, "�۸��������ϵ���ǵĿͷ������޸ģ�", Toast.LENGTH_SHORT)
						.show();
				price.setText("");
			}
			ImageView buy_now = (ImageView) view.findViewById(R.id.buy_now);
			ImageView add_to_cart = (ImageView) view.findViewById(R.id.add_to_cart);
			// ���۶�
			TextView selled_num_text = (TextView) view
					.findViewById(R.id.selled_num_text);
			selled_num_text.setText(info.getSalledAmmount());
			// ������
			TextView emulate_num_text = (TextView) view
					.findViewById(R.id.emulate_num_text);
			emulate_num_text.setText(info.getCommentAmount());
			// ɹ����
			TextView show_num_text = (TextView) view
					.findViewById(R.id.show_num_text);
			show_num_text.setText(info.getShowListAmount());
			// Ʒ��
			TextView brand_name_text = (TextView) view
					.findViewById(R.id.brand_name_text);
			
			brand_name_text.setText(info.getBrands());
			// ��Ʒ���
			TextView product_id_num_text = (TextView) view
					.findViewById(R.id.product_id_num_text);
			if(info.getProductNumber()==null||"".equals(info.getProductNumber())){
				product_id_num_text.setText("����");
			}else{
			product_id_num_text.setText(info.getProductNumber());
			}
			// ���
			TextView standard_content_text = (TextView) view
					.findViewById(R.id.standard_content_text);
			standard_content_text.setText(info.getProductSpecifications());
			bannerArray = info.getBannerArray();
			addBaseImage(view, bannerArray);
			recommendArray = info.getRecommendArray();
			addMatchImage(view, recommendArray);
			// ���ﳵ����
			shopping_cart_button = (Button) view
					.findViewById(R.id.shopping_cart_button);
			// CartsDataManage cartsDataManage = new CartsDataManage();
			cart_num = manage.getCartAmount();
			if (cart_num == 0) {
				shopping_cart_button.setVisibility(View.GONE);
			} else {
				setCartNum(cart_num);
			}
			// ���빺�ﳵ��ť����¼�
			add_to_cart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cart_num++;
					setCartNum(cart_num);
					Intent intent = new Intent(Consts.UPDATE_CHANGE);
					activity.sendBroadcast(intent);
					if(cart.getPrice()>0){
					boolean istrue = manage.addCart(cart);
					}else{
						Toast.makeText(activity, "������Ʒ�����ܹ�����", Toast.LENGTH_LONG).show();
					}
					// Log.i("info", istrue+"   cart_num");
//				if (istrue) {
//					builder.show();
//				}
				}
			});
			// ��������ť
			buy_now.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(activity, PayActivity.class);
					try{
						float sum = Float.parseFloat(priceString);
						if(sum <= 0) return;//�۸����
						Bundle bundle = new Bundle();
						bundle.putSerializable("Cart", cart);
						bundle.putString("price", priceString);
						intent.putExtras(bundle);
						activity.startActivity(intent);
//						activity.finish();
					} catch (NumberFormatException e){
						//�۸����
					}
				}
			});
			// ���ﳵ��ť
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

			// �����ղذ�ť
			add_favorites = (ImageView) view.findViewById(R.id.add_favorites);
			add_favorites.setOnClickListener(addFavoriteListener);
			// ����Ƿ��ղز��������ղذ�ť��ͼƬ
			FavoriteDataManage favoriteManage = new FavoriteDataManage(activity);
			if (isLogin && !"".equals(userid)) {
				if (favoriteManage.checkExists(userid, productId)) {
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
			Toast.makeText(activity,"���粻����", Toast.LENGTH_LONG);
		}
	}

	private ImageView add_favorites;// �����ղصİ�ť

	// ��ת���ﳵ�İ�ť
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(activity, "���粻����", Toast.LENGTH_LONG).show();
		}
	}

	private void addMatchImage(View view,
			final ArrayList<MainProduct> bannerArray) {
		try {
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
				matchGoodsImageLayout.setPadding(80, 0, 80, 0);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(activity, "���粻����", Toast.LENGTH_LONG).show();
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
				.setTitle("��½")
				.setMessage("�Ƿ�ȥ��½")
				.setPositiveButton("ȥ��½",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(activity,LoginActivity.class);
								activity.startActivity(intent);
							}
						}).setNegativeButton("����", null).create();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.hot_sell_right_top_image)
				.showImageOnFail(R.drawable.hot_sell_right_top_image)
				.showImageForEmptyUri(R.drawable.hot_sell_right_top_image)
				.cacheInMemory(true).cacheOnDisc(true).build();
	}

	private boolean flag = false;
	/**
	 * �����ղذ�ť
	 */
	private OnClickListener addFavoriteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FavoriteDataManage manage = new FavoriteDataManage(activity);
			if(!isLogin){
				builder.show();
				
				}
			else if (isLogin && !"".equals(userid)) {
				// ��¼״̬��
				if (!manage.checkExists(userid, productId)) {
					// δ�ղأ���������ղ�
					if (manage.addFavourite(userid, productId)) {
						// �ղسɹ�
						 Toast.makeText(activity, "�����ղسɹ�!",
						 Toast.LENGTH_SHORT)
						 .show();
						add_favorites
								.setImageResource(R.drawable.add_favorites2);
					} else {
						 Toast.makeText(activity, "��Ǹ�������ղ�ʧ�ܡ�",
						 Toast.LENGTH_SHORT).show();
						add_favorites
								.setImageResource(R.drawable.add_favorites1);
					}
				} else {
					// ���ղأ�����ɾ���ղ�
					if (manage.deleteFavourite(userid, productId)) {
						// ɾ���ɹ�
						add_favorites
								.setImageResource(R.drawable.add_favorites1);
					} else {
						// ɾ��ʧ��
						add_favorites
								.setImageResource(R.drawable.add_favorites2);
					}
				}
			} else {
				// δ��¼״̬�£��ղص�����
				// �ı�ͼƬ
				flag = !flag;
				changeFravoriteBg();
			}
		}
	};

	/**
	 * �ı��ղص�ͼƬ
	 */
	private void changeFravoriteBg() {
		// �ı�ͼƬ
		if (flag) {
			add_favorites.setImageResource(R.drawable.add_favorites2);
		} else {
			add_favorites.setImageResource(R.drawable.add_favorites1);
		}

	}

}
