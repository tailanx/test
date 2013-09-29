package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.interfacecallback.CallBack;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.model.ProductBaseInfo;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.goodsinfo.GetProductAddress;
import com.yidejia.app.mall.util.UnicodeToString;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
/**
 * ��ȡ��Ʒ����
 * @author long bin
 *
 */
public class ProductDataManage{  //implements CallBack
	private String TAG = ProductDataManage.class.getName();
	private Context context;
	private UnicodeToString unicode;
	
	private ProductBaseInfo productBaseInfo;
	private ArrayList<BaseProduct> bannerArray;			//�ֲ���Ʒ
	private ArrayList<MainProduct> recommendArray;    //�Ƽ����� 
	
	public ProductDataManage(Context context){
		this.context = context;
		unicode = new UnicodeToString();
		productBaseInfo = new ProductBaseInfo();
		bannerArray = new ArrayList<BaseProduct>();
		recommendArray = new ArrayList<MainProduct>();
	}
	/**
	 * ������Ʒid�����Ʒ����
	 * @param goodsId
	 * @return {@link ProductBaseInfo} 
	 */
	public ProductBaseInfo getProductData(String goodsId){
		TaskProduct taskProduct = new TaskProduct(goodsId);
		boolean state = false ;
		try {
			state = taskProduct.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "getProductData() InterruptedException");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "getProductData() ExecutionException");
			e.printStackTrace();
		}
		if(!state){
			Toast.makeText(context, "���粻������", Toast.LENGTH_SHORT).show();
		}
		return productBaseInfo;
	}
	
	private class TaskProduct extends AsyncTask<Void, Void, Boolean>{
		
		private String id;
		public TaskProduct(String id){
			this.id = id;
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
			GetProductAddress address = new GetProductAddress(context);
			try {
				String resultString = address.getProductJsonString(id);
				try {
					JSONObject object = new JSONObject(resultString);
					String responseString = object.getString("response");
					Log.i(TAG, responseString);
					analysisProductJson(responseString);
					Thread.sleep(3000);
					return true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "task json ex");
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "task io ex");
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task ex");
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
	 * ������Ʒ��json����
	 * @param jsonString
	 * @throws JSONException
	 */
	private void analysisProductJson(String jsonString) throws JSONException{
		JSONObject responseObject = new JSONObject(jsonString);
		String uid = responseObject.getString("goods_id");
		productBaseInfo.setUId(uid);
//		Log.i(TAG, uid);
		String goodName = responseObject.getString("goods_name");
		productBaseInfo.setName(unicode.revert(goodName));
		Log.i(TAG, goodName);
		String price = responseObject.getString("price");
		productBaseInfo.setPrice(price);
//		Log.i(TAG, price);
		String sells = responseObject.getString("sells");
		productBaseInfo.setSalledAmount(sells);
//		Log.i(TAG, sells);
		String remarks = responseObject.getString("remarks");
		productBaseInfo.setCommentAmount(remarks);
//		Log.i(TAG, remarks);
		String brands = responseObject.getString("brand");
		productBaseInfo.setBrands(brands);
//		Log.i(TAG, brands);
		String theCode = responseObject.getString("the_code");
		productBaseInfo.setProductNumber(theCode);
//		Log.i(TAG, theCode);
		String sepc = responseObject.getString("sepc");
//		productBaseInfo.setBrief(unicode.revert(sepc));
		productBaseInfo.setProductSpecifications(sepc);
//		Log.i(TAG, sepc);
		String info = responseObject.getString("info");
		productBaseInfo.setProductDetailUrl(info);
//		Log.i(TAG, info);
		String shaidan = responseObject.getString("shaidan");
		productBaseInfo.setShowListAmount(shaidan);
//		Log.i(TAG,shaidan);
		String pics = responseObject.getString("pics");
//		analysisPicJson(pics, bannerArray);
		String imgString = analysisPicJson(pics, bannerArray);
		productBaseInfo.setImgUrl(ImageUrl.IMAGEURL+imgString);
		productBaseInfo.setBannerArray(bannerArray);
//		Log.i(TAG,pics);
		String collects = responseObject.getString("collects");
		Log.i(TAG, collects);
		analysisRecmJson(collects, recommendArray);
		productBaseInfo.setRecommendArray(recommendArray);
	}
	/**
	 * ������Ʒ�ֲ�ͼƬ
	 * @param picString
	 * @throws JSONException 
	 */
	private void analysisRecmJson(String picString, ArrayList<MainProduct> picList) throws JSONException{
		JSONArray responseArray = new JSONArray(picString);
		MainProduct baseProduct;
		JSONObject itemObject;
		int length = responseArray.length();
		for (int i = 0; i < length; i++) {
			baseProduct = new MainProduct();
			itemObject = responseArray.getJSONObject(i);
			String goodsId = itemObject.getString("goods_id");
			baseProduct.setUId(goodsId);
			String imgUrlTemp = itemObject.getString("imgname");
			String imgUrl = ImageUrl.IMAGEURL + imgUrlTemp;
			baseProduct.setImgUrl(imgUrl);
			baseProduct.setPrice(itemObject.getString("price"));
			baseProduct.setTitle(itemObject.getString("goods_name"));
			picList.add(baseProduct);
		}
		Log.i(TAG, picList.getClass().getName());
	}
	
	private String analysisPicJson(String picString, ArrayList<BaseProduct> picList) throws JSONException{
		JSONObject responseArray = new JSONObject(picString);
		String imageUrlString = responseArray.getString("imgname");
		Log.i(TAG, imageUrlString);
		BaseProduct baseProduct;
		int length = responseArray.length();
		for (int i = 0; i < length - 1; i++) {
			baseProduct = new BaseProduct();
			String itemsString = responseArray.getString(String.valueOf(i));
			JSONObject itemObject = new JSONObject(itemsString);
			String id = itemObject.getString("goods_id");
			baseProduct.setUId(id);
			String imgUrlTemp = ImageUrl.IMAGEURL + itemObject.getString("imgname");
			baseProduct.setImgUrl(imgUrlTemp);
			picList.add(baseProduct);
		}
		return imageUrlString;
	}
	
	
//	@Override
//	public void callback(String id) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
	private CallBack callBack;
	public ProductBaseInfo setCallBackListener(CallBack callBack){
//		TaskProduct taskProduct = new TaskProduct();
//		taskProduct.execute();
		this.callBack = callBack;
		return getInfo();
	}
	
	public void getTask(String id){
		TaskProduct taskProduct = new TaskProduct(id);
		taskProduct.execute();
	}
	
	public ProductBaseInfo getInfo(){
		return productBaseInfo;
	}
}
