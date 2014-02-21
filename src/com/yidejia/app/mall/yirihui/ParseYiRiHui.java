package com.yidejia.app.mall.yirihui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.net.ImageUrl;

public class ParseYiRiHui {

	private ArrayList<YiRiHuiData> yiRiHuiDatas;
	
	/**解析伊日惠活动**/
	public boolean parseYiRiHui(String content){
		try {
			JSONObject httpJsonObject = new JSONObject(content);
			int code = httpJsonObject.optInt("code");
			String strResp = httpJsonObject.optString("response");
			long ts = httpJsonObject.optLong("ts");
			if(1 == code){
				
				yiRiHuiDatas = new ArrayList<YiRiHuiData>();
				YiRiHuiData tempYRH = null;
				JSONArray respArray = new JSONArray(strResp);
				int length = respArray.length();
				for (int i = 0; i < length; i++) {
					JSONObject yrhObject = respArray.optJSONObject(i);
					tempYRH = new YiRiHuiData();
					tempYRH.setTheId(yrhObject.optString("the_id"));
					tempYRH.setRuleName(yrhObject.optString("rule_name"));
					tempYRH.setBeginTime(yrhObject.optLong("begin_time"));
					long endTime = yrhObject.optLong("end_time");
//					String strEndTime = yrhObject.optString("end_time");
					tempYRH.setEndTime(endTime);
					if((endTime - ts) < 0) tempYRH.setStartTime(0);
					else tempYRH.setStartTime((endTime - ts));
					
					tempYRH.setGoodsId(yrhObject.optString("goods_id"));
					tempYRH.setQuantity(yrhObject.optString("quantity"));
					tempYRH.setCanBuyQuantity(yrhObject.optString("can_buy_quantity"));
					tempYRH.setOverTime(yrhObject.optString("overtime"));
					tempYRH.setImg1(ImageUrl.IMAGEURL + yrhObject.optString("img_1") + "!150");
					tempYRH.setImg2(ImageUrl.IMAGEURL + yrhObject.optString("img_2") + "!150");
					tempYRH.setValidFlag(yrhObject.optString("valid_flag"));
					tempYRH.setShellFlag(yrhObject.optString("shell_flag"));
					tempYRH.setGoodsName(yrhObject.optString("goods_name"));
					tempYRH.setGoodsPrice(yrhObject.optString("goods_price"));
					tempYRH.setTs(ts);
					yiRiHuiDatas.add(tempYRH);
				}
				
				return true;
			} else if(-1 == code) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**获取伊日惠活动信息**/
	public ArrayList<YiRiHuiData> getYiRiHuiDatas() {
		return yiRiHuiDatas;
	}
	
	public boolean parseMainYRH(String content){
		try {
			JSONObject httpJsonObject = new JSONObject(content);
			int code = httpJsonObject.optInt("code");
			String strResp = httpJsonObject.optString("response");
			long ts = httpJsonObject.optLong("ts");
			if(1 == code){
				
				yiRiHuiDatas = new ArrayList<YiRiHuiData>();
				YiRiHuiData tempYRH = null;
//				JSONArray respArray = new JSONArray(strResp);
//				int length = respArray.length();
//				for (int i = 0; i < length; i++) {
//					JSONObject yrhObject = respArray.optJSONObject(i);
				JSONObject yrhObject = new JSONObject(strResp);
				tempYRH = new YiRiHuiData();
				tempYRH.setTheId(yrhObject.optString("the_id"));
				tempYRH.setRuleName(yrhObject.optString("rule_name"));
				tempYRH.setBeginTime(yrhObject.optLong("begin_time"));
				long endTime = yrhObject.optLong("end_time");
				// String strEndTime = yrhObject.optString("end_time");
				tempYRH.setEndTime(endTime);
				if ((endTime - ts) < 0)
					tempYRH.setStartTime(0);
				else
					tempYRH.setStartTime((endTime - ts));

				tempYRH.setGoodsId(yrhObject.optString("goods_id"));
				tempYRH.setQuantity(yrhObject.optString("quantity"));
				tempYRH.setCanBuyQuantity(yrhObject
						.optString("can_buy_quantity"));
				tempYRH.setOverTime(yrhObject.optString("overtime"));
				tempYRH.setImg1(ImageUrl.IMAGEURL
						+ yrhObject.optString("img_1") + "!150");
				tempYRH.setImg2(ImageUrl.IMAGEURL
						+ yrhObject.optString("img_2") + "!150");
				tempYRH.setValidFlag(yrhObject.optString("valid_flag"));
				tempYRH.setShellFlag(yrhObject.optString("shell_flag"));
				tempYRH.setGoodsName(yrhObject.optString("goods_name"));
				tempYRH.setGoodsPrice(yrhObject.optString("goods_price"));
				yiRiHuiDatas.add(tempYRH);
//				}
				
				return true;
			} else if(-1 == code) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
}
