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
	public static native String getHttp4SaveAddress(String customer_id, String customer_name, String handset, String province,
			String city, String district, String address, String recipient_id, String token);
	
	//删除商品评论
	public static native String getHttp4DelComment(String id);
	
	//获取商品评论
	public static native String getHttp4GetComment(String where, String offset, String limit, String group, String order, String fields);
	
	// 保存商品评论
	public static native String getHttp4SaveComment(String goods_id,
			String user_id, String user_name, String title, String experience,
			String commentDate);
	
	//获取配送中心
	public static native String getHttp4GetDistribute(String where, String offset, String limit, String group, String order, String fields);
	
	//获取配送中心
	public static native String getHttp4GetExpress(String where, String offset, String limit, String group, String order, String fields);
	
	//获取包邮信息
	public static native String getHttp4GetFree(String where, String offset, String limit, String group, String order, String fields);
	
	//检查收藏是否存在
	public static native String getHttp4CheckFav(String userid, String goodsid, String token);
	
	//删除收藏
	public static native String getHttp4DelFav(String userid, String goodsid, String token);
	
	//获取收藏列表
	public static native String getHttp4GetFav(String where, String offset, String limit, String group, String order, String fields);
	
	//保存收藏
	public static native String getHttp4SaveFav(String userid, String goodsid, String token);
	
	//获取商品信息
	public static native String getHttp4GetGoods(String id);
	
	//获取首页
	public static native String getHttp4GetHome();
	
	//获取订单数据
	public static native String getHttp4GetOrder(String user_id, String code, String date, String status, String offset1, String limit1, String token);
	
	//获取待评价商品列表
	public static native String getHttp4GetNoEvaluate(String user_id);
	
	//根据订单编号获取订单信息
	public static native String getHttp4GetOrderByCode(String code);
	
	//修改支付状态
	public static native String getHttp4PayOut(String customer_id, String code);
	
	//取消订单
	public static native String getHttp4CancelOrder(String id, String code, String token);
	
	//删除订单
	public static native String getHttp4DelOrder(String id, String code, String token);
	
	//签收订单
	public static native String getHttp4SignOrder(String id, String code, String token);
	
	// 递交订单
	public static native String getHttp4SaveOrder(String customer_id,
			String ticket_id, String recipient_id, String pingou_id,
			String goods_ascore, String ship_fee, String ship_type,
			String ship_entity_name, String goods_qty_scr, String comments,
			String pay_type, String token);

	//获取品牌
	public static native String getHttp4GetBrand();
	
	//获取功效
	public static native String getHttp4GetEffect(String where, String offset, String limit, String group, String order, String fields);
	
	//获取搜索结果
	public static native String getHttp4GetSearch(String name, String fun, String brand,String price, String order1, String offset1, String limit1);
	
	//获取价格区间
	public static native String getHttp4GetPrice();
	
	//获取积分
	public static native String getHttp4GetVoucher(String id, String token);
	
	//登录
	public static native String getHttp4Login(String username, String password, String login_ip);
	
	//注册
	public static native String getHttp4Register(String username, String password, String cps, String ip);
	
	//设置默认地址
	public static native String getHttp4SetDefAddr(String cid, String aid, String token);
	
	//获取免费送和积分换购列表
	public static native String getHttp4GetVerify(String goods, String userid);
	
	//获取用户积分，收藏，消息， 订单数量
	public static native String getHttp4GetCount(String userId, String token);
	
	//获取消息中心用户消息列表
	public static native String getHttp4GetMessage(String userId, String token, String offset1, String limit1);
	
	//标记消息未已读状态
	public static native String getHttp4ChangeRead(String userId, String msgId, String token);
	
	//申请退换货
	public static native String getHttp4GetReturn(String user_id, String order_code, String the_date, String contact, String phone, String cause, String desc, String token);
	
	//查看退换货
	public static native String getHttp4GetReturnList(String userid, String offset, String limit, String token);
	
	//查看物流
	public static native String getHttp4GetShipLog(String code);
	
	//获取tn
	public static native String getHttp4GetTn(String userid, String order_code, String token);
	
	
}	
	