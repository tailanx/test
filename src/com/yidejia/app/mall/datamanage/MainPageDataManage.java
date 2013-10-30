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
import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.initview.HotSellView;
import com.yidejia.app.mall.model.BaseProduct;
import com.yidejia.app.mall.model.MainProduct;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.homepage.GetHomePage;
import com.yidejia.app.mall.util.UnicodeToString;
/**
 * Native首页接口 ， 实例化后先调用{@link #getMainPageData()}
 * @author long bin
 * 
 */
public class MainPageDataManage {
	private ArrayList<BaseProduct> bannerArray; //轮播商品 （个数不固定，<=6）
	private ArrayList<MainProduct> hotSellArray; //热卖商品 （个数暂定3个）
	private ArrayList<MainProduct> acymerArray;    //美容护肤  （个数暂定3个）
	private ArrayList<MainProduct> inerbtyArray;    //内调养护  （个数暂定6个）
	private Context context;
	private String TAG = MainPageDataManage.class.getName();
	private UnicodeToString unicode;
	
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private View view;
	private boolean isHasPTR = false;
	
	/**
	 * Native首页接口 ， 实例化后先调用{@link #getMainPageData()}
	 * @author long bin
	 * 
	 */
	public MainPageDataManage(Context context, PullToRefreshScrollView mPullToRefreshScrollView){//, View view
		this.context = context;
		bannerArray = new ArrayList<BaseProduct>();
		hotSellArray = new ArrayList<MainProduct>();
		acymerArray = new ArrayList<MainProduct>();
		inerbtyArray = new ArrayList<MainProduct>();
		ggTitle = new ArrayList<String>();
		unicode = new UnicodeToString();
		this.mPullToRefreshScrollView = mPullToRefreshScrollView;
		if(mPullToRefreshScrollView == null)
			isHasPTR = false;
		else isHasPTR = true;
//		this.view = view;
	}
	/**
	 * 这个方法是获取首页所有数据的函数，之后调用
	 * <p>{@link #getBannerArray()}获取首页轮播商品数据
	 * <p>{@link #getHotSellArray()}获取首页热卖商品数据
	 * <p>{@link #getAcymerArray()}获取首页美容护肤商品数据
	 * <p>{@link #getInerbtyArray()}获取首页内调养护商品数据
	 * 
	 */
	public void getMainPageData(){
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return;
		}
		TaskMainPage taskMainPage = new TaskMainPage();
		boolean state = false ;
		try {
			state = taskMainPage.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Log.e(TAG, "TaskMainPage() InterruptedException");
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "TaskMainPage() ExecutionException");
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
		if(!state){
			Toast.makeText(context, context.getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
		}
	}
	
//	/**
//	 * �����ֲ���Ʒ�б�
//	 * @param bannerArray �ֲ���Ʒ�б�
//	 */
//	public void setBannerArray(ArrayList<BaseProduct> bannerArray){
//		this.bannerArray = bannerArray;
//	}
	/**
	 * 
	 * @return 轮播商品bannerArray ：包含BaseProduct对象的ArrayList类型
	 */
	public ArrayList<BaseProduct> getBannerArray(){
		return bannerArray;
	}
	/**
	 * 
	 * @return 热卖商品hotSellArray:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getHotSellArray(){
		
		return hotSellArray;
	}
//	/**
//	 * ����������Ʒ�б�
//	 * @param hotSellArray ������Ʒ�б�:��MainProduct�����ArrayList����
//	 */
//	public void setHotSellArray(ArrayList<MainProduct> hotSellArray){
//		this.hotSellArray = hotSellArray;
//	}
	/**
	 * 
	 * @return acymerArray 美容护肤商品列表:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getAcymerArray(){
		return acymerArray;
	}
//	/**
//	 * �������ݻ�����Ʒ�б�
//	 * @param acymerArray ���ݻ�����Ʒ�б�:��MainProduct�����ArrayList����
//	 */
//	public void setAcymerArray(ArrayList<MainProduct> acymerArray){
//		this.acymerArray = acymerArray;
//	}
	/**
	 * 
	 * @return inerbtyArray 内调养护商品列表:包含MainProduct对象的ArrayList类型
	 */
	public ArrayList<MainProduct> getInerbtyArray(){
		return inerbtyArray;
	}
//	/**
//	 * �����ڵ�����Ʒ�б�
//	 * @param inerbtyArray �ڵ�����Ʒ�б�:��MainProduct�����ArrayList����
//	 */
//	public void setInerbtyArray(ArrayList<MainProduct> inerbtyArray){
//		this.inerbtyArray = inerbtyArray;
//	}
	/**
	 * 
	 * @return 商城公告
	 */
	public ArrayList<String> getGGTitle(){
		return ggTitle;
	}
	
	
	private class TaskMainPage extends AsyncTask<Void, Void, Boolean>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (!isHasPTR) {
				bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				bar.setMessage(context.getResources().getString(R.string.searching));
				bar.show();
			} else {
				mPullToRefreshScrollView.setRefreshing();
				
			}
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetHomePage getHomePage = new GetHomePage(context);
			try {
				String httpResultString = getHomePage.getHomePageJsonString();
				analysisGetHomeJson(httpResultString);
//				Thread.sleep(3000);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "task get home ioex");
				return false;
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, "task get home other ex");
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if(!isHasPTR)
				bar.dismiss();
			else mPullToRefreshScrollView.onRefreshComplete();
//			HotSellView hotSellView = new HotSellView(view);
//			hotSellView.initHotSellView(hotSellArray);
//			hotSellView.initAcymerView(acymerArray);
//			hotSellView.initInerbtyView(inerbtyArray);
//			if(result)
//				Toast.makeText(context, "�ɹ�", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
		private ProgressDialog bar = new ProgressDialog(context);
		
	}
	
	/**
	 * 解析首页所有数据
	 * @param httpResultString
	 * @return
	 */
	private void analysisGetHomeJson(String httpResultString){
		JSONObject httpResultObject;
		try {
			httpResultObject = new JSONObject(httpResultString);
			int code = httpResultObject.getInt("code");
			Log.i(TAG, "code"+code);
			if(code == 1){
				String responseString = httpResultObject.getString("response");
				JSONObject itemObject = new JSONObject(responseString);
				String bannerString = itemObject.getString("lb");
				String hotsellString = itemObject.getString("rm");
				String acymerString = itemObject.getString("mrhf");
				String inerbtyString = itemObject.getString("ntyh");
				String ggString = itemObject.getString("gg");
				
				analysisGGJson(ggString);
				analysisBannerJson(bannerString);
				analysisJson(hotsellString, 0);
				analysisJson(acymerString, 1);
				analysisJson(inerbtyString, 2);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "get home page json error");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "get home page json other error");
			e.printStackTrace();
		}
//		return favoriteArray;
	}
	
	private void analysisBannerJson(String bannerJsonString) throws JSONException{
		JSONArray bannerJsonArray = new JSONArray(bannerJsonString);
		int length = bannerJsonArray.length();
		JSONObject itemObject;
		BaseProduct baseProduct ;
		for (int i = 0; i < length; i++) {
			baseProduct = new BaseProduct();
			itemObject = bannerJsonArray.getJSONObject(i);
			String goodsId = itemObject.getString("goods_id");
			baseProduct.setUId(goodsId);
			String imgUrl = itemObject.getString("img_name");
			Log.i(TAG, imgUrl);
			baseProduct.setImgUrl(ImageUrl.IMAGEURL+imgUrl);
			bannerArray.add(baseProduct);
		}
	}
	
	private void analysisJson(String jsonString, int index) throws JSONException{
		JSONArray hotsellJsonArray = new JSONArray(jsonString);
		int length = hotsellJsonArray.length();
		JSONObject itemObject;
		MainProduct baseProduct;
		for (int i = 0; i < length; i++) {
			baseProduct = new MainProduct();
			itemObject = hotsellJsonArray.getJSONObject(i);
			String goodsId = itemObject.getString("goods_id");
			baseProduct.setUId(goodsId);
			String imgUrl;
			if(i==0 && index != 2)imgUrl = ImageUrl.IMAGEURL + itemObject.getString("img_name");
			else imgUrl = ImageUrl.IMAGEURL + itemObject.getString("img_name");
			baseProduct.setImgUrl(imgUrl);
			String title = itemObject.getString("name");
			baseProduct.setTitle(unicode.revert(title));
			Log.i(TAG, title);
//			String subTitle = itemObject.getString(name)
			String price = itemObject.getString("price");
			baseProduct.setPrice(price);
//			mainArray.add(baseProduct);
			switch (index) {
			case 0:
				hotSellArray.add(i, baseProduct);
				Log.i(TAG, hotSellArray.get(i).getTitle());
				break;
			case 1:
				acymerArray.add(i, baseProduct);
				break;
			case 2:
				inerbtyArray.add(i, baseProduct);
				break;
			default:
				break;
			}
//			Log.i(TAG, mainArray.get(i).getTitle());
		}
		if(index == 0){
			for (int i = 0; i < hotSellArray.size(); i++) {
				Log.i(TAG, hotSellArray.get(i).getTitle());
			}
		}
	}
	
	private ArrayList<String> ggTitle;
	private void analysisGGJson(String ggString) throws JSONException{
		JSONArray ggJsonArray = new JSONArray(ggString);
		int length = ggJsonArray.length();
		
		JSONObject jsonObject;
		for (int i = 0; i < length; i++) {
			jsonObject = ggJsonArray.getJSONObject(i);
			ggTitle.add(jsonObject.getString("title"));
		}
	}
	
//	private void analysisAcymerJson(String hotsellJsonString) throws JSONException{
//		JSONArray hotsellJsonArray = new JSONArray(hotsellJsonString);
//		int length = hotsellJsonArray.length();
//		JSONObject itemObject;
//		MainProduct baseProduct = new MainProduct();
//		for (int i = 0; i < length; i++) {
//			itemObject = hotsellJsonArray.getJSONObject(i);
//			String goodsId = itemObject.getString("goods_id");
//			baseProduct.setUId(goodsId);
//			String imgUrl = itemObject.getString("img_name");
//			baseProduct.setImgUrl(imgUrl);
//			String title = itemObject.getString("name");
//			baseProduct.setTitle(title);
////			String subTitle = itemObject.getString(name)
//			String price = itemObject.getString("price");
//			baseProduct.setPrice(price);
//			
//			acymerArray.add(baseProduct);
//		}
//	}
	
}
