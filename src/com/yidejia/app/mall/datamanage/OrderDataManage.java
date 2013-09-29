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

import com.yidejia.app.mall.model.Cart;
import com.yidejia.app.mall.model.Order;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.order.GetOrderList;
import com.yidejia.app.mall.net.order.PayOutOrder;
import com.yidejia.app.mall.net.order.SaveOrder;
import com.yidejia.app.mall.util.UnicodeToString;

/**
 * ��ȡ�����б����ݣ��ύ�������޸Ķ���֧��״̬
 * 
 * @author long bin
 * 
 */
public class OrderDataManage {

	private ArrayList<Order> orders;
	private Context context;
	private String TAG = OrderDataManage.class.getName();
	private UnicodeToString unicode;

	public OrderDataManage(Context context) {
		this.context = context;
		orders = new ArrayList<Order>();
		cartsArray = new ArrayList<Cart>();
		unicode = new UnicodeToString();
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param userId ��"514492"
	 *            �ͻ�Id
	 * @param code
	 *            �������,֧��ģ������,��Ϊ�գ���""
	 * @param the_day ""
	 *            1.��һ�ܣ� 2.��һ�£� 3.��һ��
	 * @param status "¼��"������ǩ�ա�������ȡ���������Ѹ�������ѷ�����
	 *            ������¼�루��������Ѹ�������������ѷ���������ɣ������ڿ�ʱ�����ǡ�ȫ������������˼����
	 * @param offset
	 *            ������ʼ
	 * @param limit
	 *            ��������
	 * @return orders �����б�
	 */
	public ArrayList<Order> getOrderArray(String userId, String code,
			String the_day, String status, String offset, String limit) {
		TaskGetList taskGetList = new TaskGetList(userId, code, the_day,
				status, offset, limit);
		boolean state = false;
		try {
			state = taskGetList.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "TaskGetList() ExecutionException");
			e.printStackTrace();
		}
		if (!state) {
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
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

		public TaskGetList(String userId, String code, String the_day,
				String status, String offset, String limit) {
			this.userId = userId;
			this.offset = offset;
			this.limit = limit;
			this.code = code;
			this.the_day = the_day;
			this.status = status;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage("���ڲ�ѯ");
			bar.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetOrderList getList = new GetOrderList(context);
			try {
				String httpResultString = getList.getListJsonString(userId,
						code, the_day, status, offset, limit);
				analysisGetListJson(httpResultString);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task getlist ioex");
				return false;
			} catch (NumberFormatException e) {
				// TODO: handle exception
				// Toast.makeText(context, "��������", Toast.LENGTH_SHORT).show();
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
		}
		return orders;
	}

	private ArrayList<Cart> cartsArray;

	private ArrayList<Cart> analysisCart(String lines) throws JSONException {
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
	 * �����ύ�����ɹ����,boolean
	 * 
	 * @param customer_id
	 *            �ͻ�id
	 * @param ticket_id
	 *            �Ż�ȯid��0��ʾû���Ż�ȯ
	 * @param recipient_id
	 *            �ռ���id
	 * @param pingou_id
	 *            ƴ����Ʒ��id
	 * @param goods_ascore
	 *            �ͻ�֧���Ļ���
	 * @param ship_fee
	 *            �˷�
	 * @param ship_type
	 *            EMS����
	 * @param ship_entity_name
	 *            ��������
	 * @param goods_qty_scr
	 *            ������ַ�������ʽ����Ʒid,����,�Ƿ���ֶһ�;�����硰11,5n��
	 * @param comments
	 *            ������ע��Ϣ
	 * @return boolean �ύ�����ɹ����
	 */
	public boolean saveOrder(String customer_id, String ticket_id,
			String recipient_id, String pingou_id, String goods_ascore,
			String ship_fee, String ship_type, String ship_entity_name,
			String goods_qty_scr, String comments) {
		TaskSave taskSave = new TaskSave(customer_id, ticket_id, recipient_id,
				pingou_id, goods_ascore, ship_fee, ship_type, ship_entity_name,
				goods_qty_scr, comments);
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
		}
		if (!state) {
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
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

		public TaskSave(String customer_id, String ticket_id,
				String recipient_id, String pingou_id, String goods_ascore,
				String ship_fee, String ship_type, String ship_entity_name,
				String goods_qty_scr, String comments) {
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
		}

		private ProgressDialog bar = new ProgressDialog(context);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.setMessage("���ڲ�ѯ");
			bar.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SaveOrder saveOrderList = new SaveOrder();
			try {
				String httpResponse = saveOrderList.getListJsonString(customer_id, ticket_id,
						recipient_id, pingou_id, goods_ascore, ship_fee, ship_type,
						ship_entity_name, goods_qty_scr, comments);
				JSONObject jsonObject = new JSONObject(httpResponse);
				int code = jsonObject.getInt("code");
				if(code == 1){
					String response = jsonObject.getString("response");
					JSONObject responseObject = new JSONObject(response);
					String result = responseObject.getString("@p_result");
					if(unicode.revert(result).equals("success¼��ɹ�")){
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
	
	private String orderCode = "";//�ύ�����ɹ��󷵻صĶ�����
	/**
	 * 
	 * @return orderCode �ύ�����ɹ��󷵻صĶ�����
	 */
	public String getOrderCode(){
		return orderCode;
	}
	
	public boolean changeOrder(String customer_id, String code) {
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
		}
		if (!state) {
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
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
			bar.setMessage("���ڲ�ѯ");
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
					if(unicode.revert(result).equals("success����ɹ�")){
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

}
