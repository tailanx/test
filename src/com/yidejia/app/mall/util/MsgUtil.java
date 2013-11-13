package com.yidejia.app.mall.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.MessageDataManage;
import com.yidejia.app.mall.model.MsgCenter;

public class MsgUtil {
	private Context context;
	private LinearLayout mLayout;
	private LayoutInflater inflater;
	private MyApplication myApplication;
	private MessageDataManage msgDataManage;
	private int offset;
	private int limit;

	public MsgUtil(Context context, LinearLayout linearLayout, int fromIndex,
			int amount) {
		this.context = context;
		this.mLayout = linearLayout;
		this.inflater = LayoutInflater.from(context);
		myApplication = (MyApplication) context.getApplicationContext();
		msgDataManage = new MessageDataManage(context);
		this.offset = fromIndex;
		this.limit = amount;
	}

	public void loadView() {
		boolean isSucess = msgDataManage.getMsgData(myApplication.getUserId(),
				myApplication.getToken(), offset + "", limit + "");
		if (isSucess) {
			ArrayList<MsgCenter> msgCenters = msgDataManage.getMsg();
			Log.i("info", msgCenters.size()+"       msgCenters.size()");
			for (int i = 0; i < msgCenters.size(); i++) {
				View view = inflater.inflate(R.layout.message_item, null);
				TextView title = (TextView) view
						.findViewById(R.id.message_item_textview1);
				TextView time = (TextView) view.findViewById(R.id.message_textview2);
				TextView content = (TextView) view.findViewById(R.id.message_textview4);
				MsgCenter msg = msgCenters.get(i);
				title.setText(msg.getTitle());
				content.setText(msg.getMsg());
				time.setText(DateUtils.formatDateTime(context,
						Long.parseLong(msg.getTime()), DateUtils.FORMAT_ABBREV_ALL
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_SHOW_TIME));
				mLayout.addView(view);
			}
		}
	}
	

}
