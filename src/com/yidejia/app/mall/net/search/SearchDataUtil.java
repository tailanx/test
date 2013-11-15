package com.yidejia.app.mall.net.search;

import java.io.IOException;



import android.content.Context;
import android.util.Log;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpAddressParam;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.util.Md5;
/**
 * ��ȡ��������б�
 * @author long bin
 *
 */
public class SearchDataUtil {
	private String[] keys = new String[12];
	private String[] values = new String[12];
	private String TAG = SearchDataUtil.class.getName();
//	private Context context;
	
	public SearchDataUtil(){//Context context
//		this.context = context;
	}
	
	private void setKeysAndValues(String name, String fun, String brand,String price, String order1, String offset1, String limit1){
		keys[0] = "api";
		String api = "product.mallgood.search";
		values[0] = api;
		keys[1] = "name";
		values[1] = name;
		keys[2] = "fun";
		values[2] = fun;
		keys[3] = "brand";
		values[3] = brand;
		keys[4] = "price";
		values[4] = price;
		keys[5] = "order1";
		values[5] = order1;
		keys[6] = "offset1";
		values[6] = offset1;
		keys[7] = "limit1";
		values[7] = limit1;
		keys[8] = "key";
//		values[8] = "fw_test";
		values[8] = "fw_mobile";
		keys[9] = "format";
		values[9] = "array";
		keys[10] = "ts";
		long time = System.currentTimeMillis();
		String ts = String.valueOf(time/1000);
		values[10] = ts;
		
		keys[11] = "sign";
		StringBuffer strTemp = new StringBuffer();
//		strTemp.append("ChunTianfw_mobile123456");
		strTemp.append("ChunTianfw_mobile@SDF!TD#DF#*CB$GER@");
		strTemp.append(api);
		strTemp.append(ts);
		Md5 md = new Md5();
		String result = md.getMD5Str(strTemp.toString());
		md = null;
        strTemp = null;
		values[11] = result;
	}
	
	private String getHttpAddress(String name, String fun, String brand, String price, String order1, String offset1, String limit1){
		StringBuffer result = new StringBuffer();
//		result.append("http://192.168.1.254:802/?");
		setKeysAndValues(name, fun, brand, price, order1, offset1, limit1);//"", "0", "1", "", "", "%2A"
		result.append(HttpAddressParam.getHttpAddress(keys, values));
		Log.i(TAG, result.toString());
		return result.toString();
	}
	
	private String result = "";
	/**
	 * 
	 * @param name ������Ʒ����ƣ�֧��ģ�����
	 * @param fun ��Ч
	 * @param brand Ʒ��
	 * @param price �۸�
	 * @param order1 ����
	 * @param offset1 ��ʼ��
	 * @param limit1 ������
	 * @return
	 * @throws IOException
	 */
	public String getHttpResponseString(String name, String fun, String brand, String price, String order1, String offset1, String limit1)throws IOException{
//		HttpGetConn conn = new HttpGetConn(getHttpAddress(name, fun, brand, price, order1, offset1, limit1));
		String url = new JNICallBack().getHttp4GetSearch(name, fun, brand, price, order1, offset1, limit1);
		Log.e(TAG, "before conn"+url);
		HttpGetConn conn = new HttpGetConn(url, true);
		result = conn.getJsonResult();
		return result;
	}
//	public String getFavoriteListJson(){
//		AsyncHttpClient client = new AsyncHttpClient();
//		client.setTimeout(5000);
//		client.get(getHttpAddress(), new AsyncHttpResponseHandler(){
//
//			@Override
//			public void onSuccess(String content) {
//				// TODO Auto-generated method stub
//				super.onSuccess(content);
//				Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
//				Log.i(TAG, "1111"+content);
//				bar.dismiss();
//				result = content;
//			}
//
//			@Override
//			public void onFailure(Throwable error, String content) {
//				// TODO Auto-generated method stub
//				super.onFailure(error, content);
//				Log.i(TAG, "2222"+content);
//				if (error.getCause() instanceof ConnectTimeoutException) {
//					System.out.println("Connection timeout !");
//					Log.i(TAG, "Connection timeout ");
//				}
//				bar.dismiss();
//			}
//
//			@Override
//			public void onStart() {
//				// TODO Auto-generated method stub
//				super.onStart();
//				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//				bar.setMessage("���ڲ�ѯ");
//				bar.show();
//			}
//			
//			private ProgressDialog bar = new ProgressDialog(context);
//		});
//		return result;
//	}
//	
	
}
