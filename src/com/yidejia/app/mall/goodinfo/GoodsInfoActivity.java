package com.yidejia.app.mall.goodinfo;

import java.io.IOException;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import com.baidu.mobstat.StatService;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.util.DPIUtil;
import com.yidejia.app.mall.widget.YLViewPager;

/**
 * 商品基本信息activity
 * 
 * @author LongBin
 * 
 */
public class GoodsInfoActivity extends BaseActivity implements OnClickListener {

	private String goodsId; // 商品id
	private YLViewPager vpImage; // 商品图片容器
	private int width; // 屏幕宽
	private ProductBaseInfo productInfo; // 商品信息类实例
	private GoodsInfoViewUtil viewUtil;
	private ImageView shareImageView;// 分享

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(this);//
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			goodsId = bundle.getString("goodsId");
		}
		
		Uri dataUri = getIntent().getData();
		if(null != dataUri){
//			String scheme = dataUri.getScheme();
//			String host = dataUri.getHost();
			List<String> params = dataUri.getPathSegments(); 
			if(null != params && params.size() != 0){
				String temp = params.get(0);
				temp = temp.replace("acymer_", "");
				goodsId = temp.replace(".html", "");
			}
		}
		
		
		setActionbarConfig();
		shareImageView = (ImageView) findViewById(R.id.ab_common_iv_share);
		shareImageView.setVisibility(View.VISIBLE);
		shareImageView.setImageResource(R.drawable.share);
		shareImageView.setOnClickListener(this);// 分享的点击事件
		setTitle(R.string.goods_info);

		setContentView(R.layout.item_goods_base_info);
		vpImage = (YLViewPager) findViewById(R.id.vp_item_goods_detail);

		width = DPIUtil.getWidth();

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				width, width);
		vpImage.setLayoutParams(layoutParams);

		getData();
	}

	private void getData() {
		String url = new JNICallBack().getHttp4GetGoods(goodsId);
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse() {

			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				ParseGoodsJson parseGoodsJson = new ParseGoodsJson();
				boolean issucess = parseGoodsJson.parseGoodsInfo(content);
				if (issucess) {
					productInfo = parseGoodsJson.getProductBaseInfo();
					viewUtil = new GoodsInfoViewUtil(GoodsInfoActivity.this);
					viewUtil.initGoodsView(productInfo);
				} else {
					Toast.makeText(GoodsInfoActivity.this,
							getResources().getString(R.string.no_product),
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				if (error instanceof IOException) {
					Toast.makeText(GoodsInfoActivity.this,
							getResources().getString(R.string.bad_network),
							Toast.LENGTH_LONG).show();
				}
			}

		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, getString(R.string.goods_info));
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, getString(R.string.goods_info));
		if (null != viewUtil)
			viewUtil.setCartNumber();
	}

	@Override
	public void onClick(View v) {
		share();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);// 结束shareSdk的统计功能
	}

	/**
	 * 调用插件的分享
	 */

	public void share() {
		OnekeyShare oks = new OnekeyShare();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ydj_icon,
				getResources().getString(R.string.app_name));
		// address是接收人地址，仅在信息和邮件使用
		oks.setAddress("12345678901");
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getResources().getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://www.yidejia.com/acymer_" + productInfo.getUId()
				+ ".html");
		// text是分享文本，所有平台都需要这个字段
		oks.setText(getResources().getString(R.string.share_content)+productInfo.getName()
				+ "http://www.yidejia.com/acymer_" +productInfo.getUId()
				+ ".html");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath(MainActivity.TEST_IMAGE);
		// imageUrl是图片的网络路径，新浪微博、人人网、QQ空间、
		// 微信的两个平台、Linked-In支持此字段
		oks.setImageUrl(productInfo.getImgUrl());
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://www.yidejia.com/acymer_" +productInfo.getUId()
				+ ".html");
		// appPath是待分享应用程序的本地路劲，仅在微信中使用
		// oks.setAppPath(MainActivity.TEST_IMAGE);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(getResources().getString(R.string.share));
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(GoodsInfoActivity.this.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://app.yidejia.com/android.html");
		// venueName是分享社区名称，仅在Foursquare使用
		oks.setVenueName("Southeast in China");
		// venueDescription是分享社区描述，仅在Foursquare使用
		oks.setVenueDescription("This is a beautiful place!");
		// latitude是维度数据，仅在新浪微博、腾讯微博和Foursquare使用
		oks.setLatitude(23.122619f);
		// longitude是经度数据，仅在新浪微博、腾讯微博和Foursquare使用
		oks.setLongitude(113.372338f);
		// 是否直接分享（true则直接分享）
		oks.setSilent(false);
		// 指定分享平台，和slient一起使用可以直接分享到指定的平台
		// if (platform != null) {
		// oks.setPlatform(platform);
		// }
		// 去除注释可通过OneKeyShareCallback来捕获快捷分享的处理结果
		// oks.setCallback(new OneKeyShareCallback());
		// 通过OneKeyShareCallback来修改不同平台分享的内容
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

			@Override
			public void onShare(Platform platform, ShareParams paramsToShare) {
			}
		});

		oks.show(this);
	}
}
