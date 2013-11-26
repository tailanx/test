package com.yidejia.app.mall.net.order;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.util.Md5;
import com.yidejia.app.mall.util.UnicodeToString;

public class SaveOrder {
	private String[] keys = new String[15];
	private String[] values = new String[15];
	private String TAG = SaveOrder.class.getName();
	
	/**
	 * 
	 * ֻ�з��� ��success¼��ɹ��� �����ύ�����ɹ���
	 * @param customer_id �ͻ�id
	 * @param ticket_id �Ż�ȯid��0��ʾû���Ż�ȯ
	 * @param recipient_id �ռ���id
	 * @param pingou_id ƴ����Ʒ��id
	 * @param goods_ascore �ͻ�֧���Ļ��
	 * @param ship_fee �˷�
	 * @param ship_type EMS����
	 * @param ship_entity_name ��������
	 * @param goods_qty_scr ������ַ���ʽ����Ʒid,����,�Ƿ��ֶһ�;�����硰11,5n��
	 * @param comments ������ע��Ϣ
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
//		values[11] = "fw_test";
		values[11] = "fw_mobile";
		keys[12] = "format";
		values[12] = "array";
		keys[13] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[13] = ts;
		
		keys[14] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
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
	 * ֻ�з��� ��success¼��ɹ��� �����ύ�����ɹ���
	 * @param customer_id �ͻ�id
	 * @param ticket_id �Ż�ȯid��0��ʾû���Ż�ȯ
	 * @param recipient_id �ռ���id
	 * @param pingou_id ƴ����Ʒ��id
	 * @param goods_ascore �ͻ�֧���Ļ��
	 * @param ship_fee �˷�
	 * @param ship_type EMS����
	 * @param ship_entity_name ��������
	 * @param goods_qty_scr ������ַ���ʽ����Ʒid,����,�Ƿ��ֶһ�;�����硰11,5n��
	 * @param comments ������ע��Ϣ
	 */
	public String getListJsonString(String customer_id, String ticket_id,
			String recipient_id, String pingou_id, String goods_ascore,
			String ship_fee, String ship_type, String ship_entity_name,
			String goods_qty_scr, String comments) throws IOException {
		HttpGetConn conn = new HttpGetConn(getHttpAddress(customer_id,
				ticket_id, recipient_id, pingou_id, goods_ascore, ship_fee,
				ship_type, ship_entity_name, goods_qty_scr, comments));
		result = conn.getJsonResult();
		return result;
	}
	
	public String getHttpResponse(String customer_id, String ticket_id,
			String recipient_id, String pingou_id, String goods_ascore,
			String ship_fee, String ship_type, String ship_entity_name,
			String goods_qty_scr, String comments, String pay_type, String token)
			throws IOException {
		String url = new JNICallBack().getHttp4SaveOrder(customer_id, ticket_id,
				recipient_id, pingou_id, goods_ascore, ship_fee, new String(
						ship_type.getBytes("UTF-8"), "UTF-8"), new String(
						ship_entity_name.getBytes("UTF-8"), "UTF-8"),
				goods_qty_scr, new String(comments.getBytes("UTF-8"), "UTF-8"),
				pay_type, token);
		
		HttpPostConn conn = new HttpPostConn(url);
//		HttpGetConn conn = new HttpGetConn(url, true);
		return result = conn.getHttpResponse();
//		return conn.getJsonResult();
	}
	
	public boolean analysisHttpResp(String httpResponse){
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(httpResponse);
			int code = jsonObject.getInt("code");
			if(code == 1){
				String response = jsonObject.getString("response");
				JSONObject responseObject = new JSONObject(response);
				String result = responseObject.getString("@p_result");
				if(unicode.revert(result).equals(context.getResources().getString(R.string.success_save_order))){
					orderCode = responseObject.getString("@p_order_code");
					resp_code = responseObject.getString("@p_resp_code");
					tn = responseObject.getString("@p_tn");
					return true;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	private String orderCode;
	private String resp_code;
	private String tn;
	private UnicodeToString unicode;
	private Context context;
	
	public SaveOrder(){
		
	}
	
	public SaveOrder(Context context){
		this();
		this.context = context;
		unicode = new UnicodeToString();
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getResp_code() {
		return resp_code;
	}

	public String getTn() {
		return tn;
	}
}
