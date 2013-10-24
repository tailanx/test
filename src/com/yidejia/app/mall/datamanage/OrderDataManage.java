package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.order.CancelOrder;
import com.yidejia.app.mall.net.order.GetOrderList;
import com.yidejia.app.mall.net.order.PayOutOrder;
import com.yidejia.app.mall.net.order.SaveOrder;
import com.yidejia.app.mall.net.order.SignOrder;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * 获取订单列表数据，提交订单，修改订单支付状态
 * 
 * @author long bin
 * 
 */
public class OrderDataManage {

	private ArrayList<Order> orders;
	private Context context;
	private String TAG = OrderDataManage.class.getName();
	private UnicodeToString unicode;
	
	private boolean isNoMore = false;///判断是否还有更多数据,true为没有更多了
	
	public OrderDataManage(Context context) {
		this.context = context;
		orders = new ArrayList<Order>();
		cartsArray = new ArrayList<Cart>();
		unicode = new UnicodeToString();
	}

	/**
	 * 获取订单列表
	 * 
	 * @param userId 如"514492"
	 *            客户Id
	 * @param code
	 *            订单编号,支持模糊查找,如为空，用""
	 * @param the_day ""
	 *            1.近一周； 2.近一月； 3.近一年
	 * @param status "录入"，“已签收”，“已取消”，“已付款”，“已发货”
	 *            包括（录入（待付款），已付款（待发货），已发货和已完成，，等于空时，就是“全部订单“的意思）；
	 * @param offset
	 *            订单开始
	 * @param limit
	 *            订单个数
	 * @param token
	 * @return orders 订单列表
	 */
	public ArrayList<Order> getOrderArray(String userId, String code,
			String the_day, String status, String offset, String limit, String token) {
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return orders;
		}
		TaskGetList taskGetList = new TaskGetList(userId, code, the_day,
				status, offset, limit, token);
		boolean state = false;
		try {
			state = taskGetList.execute().get();
			if(isNoMore){
				Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
				isNoMore = false;
				state = true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() ExecutionException");
			e.printStackTrace();
		} catch(Exception e){
			
		}
		if (!state) {
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return orders;
	}

	private class TaskGetList extends AsyncTask<Void, Void, Boolean> {
		private String userId;
		private String code;
		private String the_day;
		private String status;
		private String offset;
		private String limit;
		private String token;
		public TaskGetList(String userId, String code, String the_day,
				String status, String offset, String limit, String token) {
			this.userId = userId;
			this.offset = offset;
			this.limit = limit;
			this.code = code;
			this.the_day = the_day;
			this.status = status;
			this.token = token;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage(context.getResources().getString(R.string.searching));
			bar.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetOrderList getList = new GetOrderList(context);
			try {
				String httpResultString = getList.getHttpResponse(userId,
						code, the_day, status, offset, limit, token);
				analysisGetListJson(httpResultString);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task getlist ioex");
				return false;
			} catch (NumberFormatException e) {
				// TODO: handle exception
				// Toast.makeText(context, "�������", Toast.LENGTH_SHORT).show();
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task getlist other ex");
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			// if(result)
			// Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
		}

		private ProgressDialog bar = new ProgressDialog(context);
	}

	private ArrayList<Order> analysisGetListJson(String httpResultString)
			throws JSONException {
		JSONObject httpJsonObject = new JSONObject(httpResultString);
		int code = httpJsonObject.getInt("code");
		if (code == 1) {
			String responseString = httpJsonObject.getString("response");
			JSONArray jsonResponseArray = new JSONArray(responseString);
			int length = jsonResponseArray.length();
			Order itemOrder;
			JSONObject itemObject;
			for (int i = 0; i < length; i++) {
				itemOrder = new Order();
				itemObject = jsonResponseArray.getJSONObject(i);
				String orderId = itemObject.getString("order_id");
				itemOrder.setId(orderId);
				String order_code = itemObject.getString("order_code");
				itemOrder.setOrderCode(order_code);
				String the_date = itemObject.getString("the_date");
				itemOrder.setDate(the_date);
				String status = itemObject.getString("status_ex");
				itemOrder.setStatus(status);
				String goods_ascore = itemObject.getString("goods_ascore");
				itemOrder.setCore(goods_ascore);
				String goods_acash = itemObject.getString("goods_acash");
				itemOrder.setOrderSummary(Float.parseFloat(goods_acash));
				String ship_fee = itemObject.getString("ship_fee");
				itemOrder.setShipFee(ship_fee);
				String lines = itemObject.getString("lines");
				itemOrder.setCartsArray(analysisCart(lines));
				orders.add(itemOrder);
			}
		} else if(code == -1){
			isNoMore = true;
		}
		return orders;
	}

	private ArrayList<Cart> cartsArray;

	private ArrayList<Cart> analysisCart(String lines) throws JSONException {
		ArrayList<Cart> cartsArray = new ArrayList<Cart>();
		JSONArray jsonArray = new JSONArray(lines);
		int length = jsonArray.length();
		Cart cart;
		JSONObject jObject;
		for (int i = 0; i < length; i++) {
			jObject = jsonArray.getJSONObject(i);
			cart = new Cart();
			String goodsId = jObject.getString("goods_id");
			cart.setUId(goodsId);
			String quantity = jObject.getString("quantity");
			cart.setSalledAmmount(Integer.parseInt(quantity));
			String goods_name = jObject.getString("goods_name");
			cart.setProductText(unicode.revert(goods_name));
			String img_name = jObject.getString("img_name");
			cart.setImgUrl(ImageUrl.IMAGEURL + img_name);
			cart.setPrice(Float.parseFloat(jObject.getString("price")));
			cartsArray.add(cart);
		}

		return cartsArray;
	}

	/**
	 * 
	 * 返回提交订单成功与否,boolean
	 * 
	 * @param customer_id
	 *            客户id
	 * @param ticket_id
	 *            优惠券id，0表示没用优惠券
	 * @param recipient_id
	 *            收件人id
	 * @param pingou_id
	 *            拼购商品的id
	 * @param goods_ascore
	 *            客户支付的积分
	 * @param ship_fee
	 *            运费
	 * @param ship_type
	 *            EMS或快递
	 * @param ship_entity_name
	 *            配送中心
	 * @param goods_qty_scr
	 *            购买的字符串，格式“商品id,数量,是否积分兑换;”，如“11,5n;”
	 * @param comments
	 *            订单备注信息
	 * @param token
	 * @return boolean 提交订单成功与否
	 */
	public boolean saveOrder(String customer_id, String ticket_id,
			String recipient_id, String pingou_id, String goods_ascore,
			String ship_fee, String ship_type, String ship_entity_name,
			String goods_qty_scr, String comments, String token) {
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		}
		TaskSave taskSave = new TaskSave(customer_id, ticket_id, recipient_id,
				pingou_id, goods_ascore, ship_fee, ship_type, ship_entity_name,
				goods_qty_scr, comments, token);
		boolean state = false;
		try {
			state = taskSave.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Tasksave() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Tasksave() ExecutionException");
			e.printStackTrace();
		} catch(Exception e){
			
		}
		if (!state) {
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return state;
	}

	private class TaskSave extends AsyncTask<Void, Void, Boolean> {
		private String customer_id;
		private String ticket_id;
		private String recipient_id;
		private String pingou_id;
		private String goods_ascore;
		private String ship_fee;
		private String ship_type;
		private String ship_entity_name;
		private String goods_qty_scr;
		private String comments;
		private String token;
		public TaskSave(String customer_id, String ticket_id,
				String recipient_id, String pingou_id, String goods_ascore,
				String ship_fee, String ship_type, String ship_entity_name,
				String goods_qty_scr, String comments, String token) {
			this.customer_id = customer_id;
			this.ticket_id = ticket_id;
			this.recipient_id = recipient_id;
			this.pingou_id = pingou_id;
			this.goods_ascore = goods_ascore;
			this.ship_fee = ship_fee;
			this.ship_type = ship_type;
			this.ship_entity_name = ship_entity_name;
			this.goods_qty_scr = goods_qty_scr;
			this.comments = comments;
			this.token = token;
		}

		private ProgressDialog bar = new ProgressDialog(context);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage(context.getResources().getString(R.string.searching));
			bar.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SaveOrder saveOrderList = new SaveOrder();
			try {
				String httpResponse = saveOrderList.getHttpResponse(customer_id, ticket_id,
						recipient_id, pingou_id, goods_ascore, ship_fee, ship_type,
						ship_entity_name, goods_qty_scr, comments, token);
				JSONObject jsonObject = new JSONObject(httpResponse);
				int code = jsonObject.getInt("code");
				if(code == 1){
					String response = jsonObject.getString("response");
					JSONObject responseObject = new JSONObject(response);
					String result = responseObject.getString("@p_result");
					if(unicode.revert(result).equals(context.getResources().getString(R.string.success_save_order))){
						orderCode = responseObject.getString("@p_order_code");
						return true;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "save order task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "save order task other ex");
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
		}
	}
	
	private String orderCode = "";//提交订单成功后返回的订单号
	/**
	 * 
	 * @return orderCode 提交订单成功后返回的订单号
	 */
	public String getOrderCode(){
		return orderCode;
	}
	/**
	 * 修改订单支付状态
	 * @param customer_id
	 * @param code
	 * @return
	 */
	public boolean changeOrder(String customer_id, String code) {
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		}
		TaskChange taskChange = new TaskChange(customer_id, code);
		boolean state = false;
		try {
			state = taskChange.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() ExecutionException");
			e.printStackTrace();
		} catch(Exception e){
			
		}
		if (!state) {
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return state;
	}
	
	private class TaskChange extends AsyncTask<Void, Void, Boolean>{
		private String customer_id;
		private String code;
		public TaskChange(String customer_id, String code){
			this.customer_id = customer_id;
			this.code = code;
		}
		
		private ProgressDialog bar = new ProgressDialog(context);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage(context.getResources().getString(R.string.searching));
			bar.show();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			PayOutOrder payOutOrder = new PayOutOrder();
			try {
				String httpResponse = payOutOrder.getHttpResponseString(customer_id, code);
				JSONObject jsonObject = new JSONObject(httpResponse);
				int responseCode = jsonObject.getInt("code");
				if(responseCode == 1){
					String response = jsonObject.getString("response");
					JSONObject responseObject = new JSONObject(response);
					String result = responseObject.getString("@p_result");
					if(unicode.revert(result).equals(context.getResources().getString(R.string.success_pay_order))){
						return true;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "payout order task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "payout order task other ex");
				e.printStackTrace();
			}
			return false;
		}
		

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
		}
	}
	
	/**
	 * ȡ��
	 * @param customer_id
	 * @param code
	 * @param token
	 * @return
	 */
	public boolean cancelOrder(String customer_id, String code, String token) {
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		}
		TaskCancel taskCancel = new TaskCancel(customer_id, code, token);
		boolean state = false;
		try {
			state = taskCancel.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Task cancel InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Task cancel ExecutionException");
			e.printStackTrace();
		} catch(Exception e){
			
		}
		if (!state) {
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return state;
	}
	
	private class TaskCancel extends AsyncTask<Void, Void, Boolean>{
		private String customer_id;
		private String code;
		private String token;
		public TaskCancel(String customer_id, String code, String token){
			this.customer_id = customer_id;
			this.code = code;
			this.token = token;
		}
		
		private ProgressDialog bar = new ProgressDialog(context);
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage(context.getResources().getString(R.string.searching));
			bar.show();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			CancelOrder cancelOrder = new CancelOrder();
			try {
				String httpResponse = cancelOrder.getHttpResponseString(customer_id, code, token);
				JSONObject jsonObject = new JSONObject(httpResponse);
				int responseCode = jsonObject.getInt("code");
				if(responseCode == 1){
					String response = jsonObject.getString("response");
					JSONObject responseObject = new JSONObject(response);
					String result = responseObject.getString("@p_result");
					if(unicode.revert(result).equals(context.getResources().getString(R.string.success_cancel_order))){
						return true;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "cancel order task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "cancel order task other ex");
				e.printStackTrace();
			}
			return false;
		}
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
		}
	}
	
	/**
	 * ǩ�ն���
	 * @param customer_id
	 * @param code
	 * @param token
	 * @return
	 */
	public boolean signOrder(String customer_id, String code, String token) {
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return false;
		}
		TaskSign taskSign = new TaskSign(customer_id, code, token);
		boolean state = false;
		try {
			state = taskSign.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Tasksign InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Task sign ExecutionException");
			e.printStackTrace();
		} catch(Exception e){
			
		}
		if (!state) {
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		return state;
	}
	
	private class TaskSign extends AsyncTask<Void, Void, Boolean>{
		private String customer_id;
		private String code;
		private String token;
		public TaskSign(String customer_id, String code, String token){
			this.customer_id = customer_id;
			this.code = code;
			this.token = token;
		}
		
		private ProgressDialog bar = new ProgressDialog(context);
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage(context.getResources().getString(R.string.searching));
			bar.show();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SignOrder signOrder = new SignOrder();
			try {
				String httpResponse = signOrder.getHttpResponseString(customer_id, code, token);
				JSONObject jsonObject = new JSONObject(httpResponse);
				int responseCode = jsonObject.getInt("code");
				if(responseCode == 1){
					String response = jsonObject.getString("response");
					JSONObject responseObject = new JSONObject(response);
					String result = responseObject.getString("@p_result");
					if(unicode.revert(result).equals(context.getResources().getString(R.string.success_sign_order))){
						return true;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "sign order task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "sign order task other ex");
				e.printStackTrace();
			}
			return false;
		}
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
		}
	}


}
