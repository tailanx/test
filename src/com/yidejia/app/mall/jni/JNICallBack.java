package com.yidejia.app.mall.jni;

/**
 * jni数据接口
 * @author long bin
 *
 */
public class JNICallBack {
	static {
		System.loadLibrary("Mall");
	}
	
	public static String HTTPURL = getHttp4PostUrl();
	
	//获取host Url
	public static native String getHttp4PostUrl();
	
	//图片地址
	public static native String getHttp4ImageUrlPrefix();
	
	//删除用户收货地址
	public static native String getHttp4DelAddress(String cid, String aid, String token);
	
	//获取用户收货地址
	public static native String getHttp4GetAddress(String where, String offset, String limit, String group, String order, String fields);
	
	//保存用户收货地址
	public static native String getHttp4SaveAddress();
	
	//删除商品评论
	public static native String getHttp4DelComment();
	
	//获取商品评论
	public static native String getHttp4GetComment();
	
	//保存商品评论
	public static native String getHttp4SaveComment();
	
	//获取配送中心
	public static native String getHttp4GetDistribute();
	
	//获取包邮信息
	public static native String getHttp4GetFree();
	
	//检查收藏是否存在
	public static native String getHttp4CheckFav(String userid, String goodsid);
	
	//删除收藏
	public static native String getHttp4DelFav();
	
	//获取收藏列表
	public static native String getHttp4GetFav();
	
	//保存收藏
	public static native String getHttp4SaveFav();
	
	//获取商品信息
	public static native String getHttp4GetGoods();
	
	//获取首页
	public static native String getHttp4GetHome();
	
	//获取订单数据
	public static native String getHttp4GetOrder(String user_id, String code, String date, String status, String offset1, String limit1, String token);
	
	//修改支付状态
	public static native String getHttp4PayOut();
	
	//递交订单
	public static native String getHttp4SaveOrder();
	
	//获取品牌
	public static native String getHttp4GetBrand();
	
	//获取功效
	public static native String getHttp4GetEffect();
	
	//获取搜索结果
	public static native String getHttp4GetSearch();
	
	//获取价格区间
	public static native String getHttp4GetPrice();
	
	//获取积分
	public static native String getHttp4GetVoucher();
}
