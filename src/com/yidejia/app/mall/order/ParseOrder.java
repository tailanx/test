package com.yidejia.app.mall.order;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.util.UnicodeToString;

public class ParseOrder {

	private Context context;
	private String orderCode;	//订单号
	private String resp_code;	//银联支付状态码， 00为正式支付码
	private String tn;	//银联流水号
	private UnicodeToString unicode;
	private ArrayList<Order> orders;	//订单列表
	
	private Order orderDetail;	//订单详细的具体订单信息
	private String recipient_id;	//收件人id
	
	private String errResult;

	public ParseOrder(Context context) {
		this.context = context;
		unicode = new UnicodeToString();
	}
	
//	public ParseOrder(){
//		unicode = new UnicodeToString();
//	}

	/**
	 * 解析提交订单数据
	 * 
	 * @param content
	 * @return
	 */
	public boolean parseSaveOrder(String content) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(content);
			int code = jsonObject.optInt("code");
			if (code == 1) {
				String response = jsonObject.optString("response");
				JSONObject responseObject = new JSONObject(response);
				String result = responseObject.optString("@p_result");
				if (unicode.revert(result).equals(
						context.getResources().getString(
								R.string.success_save_order))) {
					orderCode = responseObject.optString("@p_order_code");
					resp_code = responseObject.optString("@p_resp_code");
					tn = responseObject.optString("@p_tn");
					return true;
				} else {
					errResult = result;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @return 提交订单成功返回的订单号
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * 
	 * @return 银联返回的订单状态
	 */
	public String getResp_code() {
		return resp_code;
	}

	/**
	 * 
	 * @return 银联返回的流水号
	 */
	public String getTn() {
		return tn;
	}
	
	/**提交订单出错是返回的错误信息**/
	public String getErrResult() {
		return errResult;
	}

	/**
	 * 解析获取的订单数据
	 * @param content
	 * @return
	 */
	public boolean parseGetOrders(String content) {
		orders = new ArrayList<Order>();
		JSONObject httpJsonObject;
		try {
			httpJsonObject = new JSONObject(content);
			int code = httpJsonObject.optInt("code");
			if (code == 1) {
				String responseString = httpJsonObject.optString("response");
				JSONArray jsonResponseArray = new JSONArray(responseString);
				int length = jsonResponseArray.length();
				Order itemOrder;
				JSONObject itemObject;
				for (int i = 0; i < length; i++) {
					itemOrder = new Order();
					itemObject = jsonResponseArray.optJSONObject(i);
					String orderId = itemObject.optString("order_id");
					itemOrder.setId(orderId);
					String order_code = itemObject.optString("order_code");
					itemOrder.setOrderCode(order_code);
					String the_date = itemObject.optString("the_date");
					itemOrder.setDate(the_date);
					String status = itemObject.optString("status_ex");
					itemOrder.setStatus(status);
					String goods_ascore = itemObject.optString("goods_ascore");
					itemOrder.setCore(goods_ascore);
					String goods_acash = itemObject.optString("goods_acash");
					try {
						itemOrder
								.setOrderSummary(Float.parseFloat(goods_acash));
					} catch (Exception e) {
						itemOrder.setOrderSummary(0.0F);
					}
					String ship_fee = itemObject.optString("ship_fee");
					itemOrder.setShipFee(ship_fee);
					itemOrder.setShipCode(itemObject.optString("ship_code"));
					itemOrder.setShipCompany(itemObject
							.optString("ship_company"));
					String lines = itemObject.optString("lines");
					itemOrder.setCartsArray(analysisCart(lines));
					orders.add(itemOrder);
				}
				return true;
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @return 返回订单数据
	 */
	public ArrayList<Order> getOrders(){
		return orders;
	}

	/**
	 * 解析商品串数据
	 * 
	 * @param lines
	 *            需要解析的原数据
	 * @return
	 * @throws JSONException
	 */
	private ArrayList<Cart> analysisCart(String lines) throws JSONException {
		ArrayList<Cart> cartsArray = new ArrayList<Cart>();
		JSONArray jsonArray = new JSONArray(lines);
		int length = jsonArray.length();
		Cart cart;
		JSONObject jObject;
		for (int i = 0; i < length; i++) {
			jObject = jsonArray.optJSONObject(i);
			cart = new Cart();
			String goodsId = jObject.optString("goods_id");
			cart.setUId(goodsId);
			String quantity = jObject.optString("quantity");
			cart.setSalledAmmount(Integer.parseInt(quantity));
			String goods_name = jObject.optString("goods_name");
			cart.setProductText(unicode.revert(goods_name));
			String img_name = jObject.optString("img_name");
			cart.setImgUrl(ImageUrl.IMAGEURL + img_name + "!100");
			String prcie = jObject.optString("price");
			if (!TextUtils.isEmpty(prcie)) {
				try {
					cart.setPrice(Float.parseFloat(prcie));
				} catch (NumberFormatException e) {
					cart.setPrice(0.0f);
				}
				cartsArray.add(cart);
			} else {
				continue;
			}
		}

		return cartsArray;
	}
	
	public boolean parseOrderDetail(String content){
		JSONObject httpJsonObject;
		try {
			httpJsonObject = new JSONObject(content);
			int respCode = httpJsonObject.optInt("code");
			if(respCode == 1){
				orderDetail = new Order();
				String respString = httpJsonObject.optString("response");
				JSONObject respObject = new JSONObject(respString);
				orderDetail.setId(respObject.optString("order_id"));
				orderDetail.setOrderCode(respObject.optString("order_code"));
				orderDetail.setDate(respObject.optString("the_date"));
				orderDetail.setStatus(respObject.optString("status_ex"));
				orderDetail.setCore(respObject.optString("goods_ascore"));
				orderDetail.setShipFee(respObject.optString("ship_fee"));
				recipient_id = (respObject.optString("recipient_id"));
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**获取订单详细信息**/
	public Order getOrderDetail() {
		return orderDetail;
	}

	/**收件人id**/
	public String getRecipient_id() {
		return recipient_id;
	}
	
	/**
	 * 解析服务器返回删除订单数据
	 * @param httpResp 待解析的字符串
	 * @return 只有返回"success删除成功"才算删除成功
	 */
	public boolean parseDelOrder(String httpResp){
		try {
			JSONObject httpObject = new JSONObject(httpResp);
			int code = httpObject.optInt("code");
			String response = httpObject.optString("response");
			if(code == 1){
				JSONObject respObject = new JSONObject(response);
				String result = respObject.optString("@p_result");
				if("success删除成功".equals(result)){
					return true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 解析服务器返回取消订单数据
	 * @param httpResp 待解析的字符串
	 * @return 只有返回"success取消成功"才算取消成功
	 */
	public boolean parseCancelOrder(String content){
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(content);
			int responseCode = jsonObject.getInt("code");
			if (responseCode == 1) {
				String response = jsonObject.getString("response");
				JSONObject responseObject = new JSONObject(response);
				String result = responseObject.getString("@p_result");
				if (unicode.revert(result).equals("success取消成功")) {
					return true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
}
