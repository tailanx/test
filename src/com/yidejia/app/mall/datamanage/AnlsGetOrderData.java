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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.order.GetOrderList;
import com.yidejia.app.mall.util.AllOrderUtil;
import com.yidejia.app.mall.util.OrdersUtil;
import com.yidejia.app.mall.util.UnicodeToString;

public class AnlsGetOrderData {
	
	private ArrayList<Order> orders;
	private Context context;
	private LinearLayout mLinearLayoutLayout;
	private String TAG = AnlsGetOrderData.class.getName();
	private UnicodeToString unicode;
	
	private boolean isNoMore = false;///判断是否还有更多数据,true为没有更多了
	private int orderType;
	
	public AnlsGetOrderData(Context context, LinearLayout mLayout, int orderType){
		this.context = context;
		orders = new ArrayList<Order>();
//		cartsArray = new ArrayList<Cart>();
		unicode = new UnicodeToString();
//		order = new Order();
		this.mLinearLayoutLayout = mLayout;
		this.orderType = orderType;
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
	public void getOrderArray(String userId, String code,
				String the_day, String status, String offset, String limit, String token) {
			if(!ConnectionDetector.isConnectingToInternet(context)) {
				Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
//				return orders;
				return;
			}
			TaskGetList taskGetList = new TaskGetList(userId, code, the_day,
					status, offset, limit, token);
			taskGetList.execute();
//			boolean state = false;
//			try {
//				state = taskGetList.execute().get();
//				if(isNoMore){
//					Toast.makeText(context, context.getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
//					isNoMore = false;
//					state = true;
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				Log.e(TAG, "TaskGetList() InterruptedException");
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				Log.e(TAG, "TaskGetList() ExecutionException");
//				e.printStackTrace();
//			} catch(Exception e){
//				
//			}
//			if (!state) {
//				Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
//			}
//			return orders;
			return;
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
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.searching));
//			bar.show();
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
			 if(result){
				 OrdersUtil allOrderUtil = new OrdersUtil(context, mLinearLayoutLayout);
				 allOrderUtil.viewCtrl(orders, orderType);
			 }
			// Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
//			bar.dismiss();
		}

//		private ProgressDialog bar = new ProgressDialog(context);
	}

	/**
	 * 解析http返回的信息
	 * @param httpResultString
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<Order> analysisGetListJson(String httpResultString)
			throws JSONException {
		ArrayList<Order> orders = new ArrayList<Order>();
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
				try {
					itemOrder.setOrderSummary(Float.parseFloat(goods_acash));
				} catch (Exception e) {
					// TODO: handle exception
					itemOrder.setOrderSummary(0.0F);
				}
				String ship_fee = itemObject.getString("ship_fee");
				itemOrder.setShipFee(ship_fee);
				itemOrder.setShipCode(itemObject.getString("ship_code"));
				itemOrder.setShipCompany(itemObject.getString("ship_company"));
				String lines = itemObject.getString("lines");
				itemOrder.setCartsArray(analysisCart(lines));
//				itemOrder.setTn(itemObject.getString("tn"));
				orders.add(itemOrder);
			}
		} else if(code == -1){
			isNoMore = true;
		}
		return orders;
	}

//	private ArrayList<Cart> cartsArray;
	
	/**
	 * 解析商品串数据
	 * @param lines 需要解析的原数据
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
			jObject = jsonArray.getJSONObject(i);
			cart = new Cart();
			String goodsId = jObject.getString("goods_id");
			cart.setUId(goodsId);
			String quantity = jObject.getString("quantity");
			cart.setSalledAmmount(Integer.parseInt(quantity));
			String goods_name = jObject.getString("goods_name");
			cart.setProductText(unicode.revert(goods_name));
			String img_name = jObject.getString("img_name");
			cart.setImgUrl(ImageUrl.IMAGEURL + img_name + "!100");
			cart.setPrice(Float.parseFloat(jObject.getString("price")));
			cartsArray.add(cart);
		}

		return cartsArray;
	}
}
