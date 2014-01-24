package com.yidejia.app.mall.order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AllOrderUtil {

	/**
	 * 根据订单的状态（orderType）获取请求订单数据的字符串字段值
	 * @param orderType（0-4分别表示全部订单，待付款订单，待发货订单，已发货订单，已完成订单）
	 * @return
	 */
	public String getOrderTpye(int orderType){
		String orderTypeStr = "";
		switch (orderType) {
		case 0:
			orderTypeStr = "";
			break;
		case 1:
			orderTypeStr = "录入";
			break;
		case 2:
			orderTypeStr = "已付款";
			break;
		case 3:
			orderTypeStr = "已发货";
			break;
		case 4:
			orderTypeStr = "已签收";
			break;

		default:
			break;
		}
		try {
			return URLEncoder.encode(orderTypeStr, "UTF-8").toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 根据订单时间状态（orderTimeType）获取订单的时间戳
	 * @param orderTimeType 0-2 分别表示近一周，近一月，近一年
	 * @return 近一周，近一月，近一年数据查询的时间戳
	 */
	public long getOrderTime(int orderTimeType){
		long orderTime = System.currentTimeMillis()/1000;
		switch (orderTimeType) {
		case 0:
			orderTime -= 7L * 24L * 60L * 60L;
			break;
		case 1:
			orderTime -= 30L * 24L * 60L * 60L;
			break;
		case 2:
			orderTime -= 365L * 24L * 60L * 60L;
			break;

		default:
			break;
		}
		return orderTime;
	}
	
	
}

