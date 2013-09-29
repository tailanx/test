package com.yidejia.app.mall.net.order;

import java.io.IOException;

import android.util.Log;

import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;

public class SaveOrder {
	private String[] keys = new String[15];
	private String[] values = new String[15];
	private String TAG = SaveOrder.class.getName();
	
	/**
	 * 
	 * 只有返回 “success录入成功” 才算提交订单成功。
	 * @param customer_id 客户id
	 * @param ticket_id 优惠券id，0表示没用优惠券
	 * @param recipient_id 收件人id
	 * @param pingou_id 拼购商品的id
	 * @param goods_ascore 客户支付的积分
	 * @param ship_fee 运费
	 * @param ship_type EMS或快递
	 * @param ship_entity_name 配送中心
	 * @param goods_qty_scr 购买的字符串，格式“商品id,数量,是否积分兑换;”，如“11,5n”
	 * @param comments 订单备注信息
	 */
	private void setKeysAndValues(String customer_id, String ticket_id, String recipient_id, String pingou_id, String goods_ascore, String ship_fee
			, String ship_type, String ship_entity_name, String goods_qty_scr, String comments){
		keys[0] = "api";
		String api = "ucenter.order.save";
		values[0] = api;
		keys[1] = "customer_id";
		values[1] = customer_id;
		keys[2] = "ticket_id";
		values[2] = ticket_id;
		keys[3] = "recipient_id";
		values[3] = recipient_id;
		keys[4] = "pingou_id";
		values[4] = pingou_id;
		keys[5] = "goods_ascore";
		values[5] = goods_ascore;
		keys[6] = "ship_fee";
		values[6] = ship_fee;
		
		keys[7] = "ship_type";
		values[7] = ship_type;
		keys[8] = "ship_entity_name";
		values[8] = ship_entity_name;
		keys[9] = "goods_qty_scr";
		values[9] = goods_qty_scr;
		keys[10] = "comments";
		values[10] = comments;
		
		keys[11] = "key";
		values[11] = "fw_mobile";
		keys[12] = "format";
		values[12] = "array";
		keys[13] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[13] = ts;
		
		keys[14] = "sign";
		StringBuffer strTemp = new StringBuffer();
		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[14] = result;
	}
	
	private String getHttpAddress(String customer_id, String ticket_id, String recipient_id, String pingou_id, String goods_ascore, String ship_fee
			, String ship_type, String ship_entity_name, String goods_qty_scr, String comments){
		StringBuffer result = new StringBuffer();
		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(customer_id, ticket_id, recipient_id, pingou_id, goods_ascore, ship_fee
				, ship_type, ship_entity_name, goods_qty_scr, comments);//"", "0", "1", "", "", "%2A"
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";
	/**
	 * 
	 * 只有返回 “success录入成功” 才算提交订单成功。
	 * @param customer_id 客户id
	 * @param ticket_id 优惠券id，0表示没用优惠券
	 * @param recipient_id 收件人id
	 * @param pingou_id 拼购商品的id
	 * @param goods_ascore 客户支付的积分
	 * @param ship_fee 运费
	 * @param ship_type EMS或快递
	 * @param ship_entity_name 配送中心
	 * @param goods_qty_scr 购买的字符串，格式“商品id,数量,是否积分兑换;”，如“11,5n”
	 * @param comments 订单备注信息
	 */
	public String getListJsonString(String customer_id, String ticket_id, String recipient_id, String pingou_id, String goods_ascore, String ship_fee
			, String ship_type, String ship_entity_name, String goods_qty_scr, String comments)throws IOException{
		HttpGetConn conn = new HttpGetConn(getHttpAddress(customer_id, ticket_id, recipient_id, pingou_id, goods_ascore, ship_fee
				, ship_type, ship_entity_name, goods_qty_scr, comments));
		result = conn.getJsonResult();
		return result;
	}
}
