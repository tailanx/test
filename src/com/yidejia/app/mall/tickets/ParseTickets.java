package com.yidejia.app.mall.tickets;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseTickets {
	
	private ArrayList<Ticket> tickets;
	
	public boolean parseTickets(String content){
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			String strResp = httpObject.optString("response");
			if(1 == code) {
				tickets = new ArrayList<Ticket>();
				Ticket tempTicket = null;
				JSONArray respJSONArray = new JSONArray(strResp);
				int length = respJSONArray.length();
				for (int i = 0; i < length; i++) {
					JSONObject tickJsonObject = respJSONArray.optJSONObject(i);
					tempTicket = new Ticket();
					tempTicket.setStatus(tickJsonObject.optString("status"));
					tempTicket.setTicketId(tickJsonObject.optString("ticket_id"));
					tempTicket.setCode(tickJsonObject.optString("code"));
					tempTicket.setId(tickJsonObject.optString("id"));
					tempTicket.setType(tickJsonObject.optString("type"));
					tempTicket.setAppointGoodsId(tickJsonObject.optString("appoint_goods_id"));
					tempTicket.setLowCash(tickJsonObject.optString("low_cash"));
					tempTicket.setName(tickJsonObject.optString("name"));
					tempTicket.setBeginDate(tickJsonObject.optString("begin_date"));
					tempTicket.setEndDate(tickJsonObject.optString("end_date"));
					tempTicket.setMoney(tickJsonObject.optString("money"));
					tempTicket.setGoodsId(tickJsonObject.optString("goods_id"));
					tempTicket.setComments(tickJsonObject.optString("comments"));
					tickets.add(tempTicket);
				}
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}
	
}
