package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.pay.CstmPayActivity;
import com.yidejia.app.mall.tickets.Ticket;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.TimeUtil;

public class IntegerAdapter extends BaseAdapter {
	private Activity context;
	private ArrayList<Ticket> list;
	private String type;
	
	private float totalPrice = 0.0f;

	public IntegerAdapter(Activity context, ArrayList<Ticket> tickets) {
		this.context = context;
		this.list = tickets;
	}

	public IntegerAdapter(Activity context, ArrayList<Ticket> tickets,
			String name) {
		this.context = context;
		this.list = tickets;
		this.type = name;
	}
	
	/**设置总价格，只有确认订单页跳转过来时的totalprice才会大于0**/
	public void setTotalPrice(float totalPrice){
		this.totalPrice = totalPrice;
	}


	@Override
	public int getCount() {
		if (list.isEmpty()) {
			list = new ArrayList<Ticket>();
			return 0;
		} else {
			return list.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.youhuiquan_item, null);
			holder = new ViewHolder();
			holder.layout = (RelativeLayout) convertView
					.findViewById(R.id.iv_youhuiquan_image);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_youhuiquan_price);
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_youhuiquan_price_user);
			holder.kind = (TextView) convertView
					.findViewById(R.id.tv_youhuiquan_price_way);
			holder.startTime = (TextView) convertView
					.findViewById(R.id.tv_youhuiquan_start_time);
			holder.endTime = (TextView) convertView
					.findViewById(R.id.tv_youhuiquan_end_time);
			holder.mustUser = (TextView) convertView
					.findViewById(R.id.tv_youhuiquan_must);
			if (null != type && !"".equals(type) && "Youhuiquan".equals(type)) {
				holder.mustUser = (TextView) convertView
						.findViewById(R.id.tv_youhuiquan_must);
				holder.mustUser.setVisibility(View.VISIBLE);

				convertView.setTag(holder);
			} else{
				holder.mustUser.setVisibility(View.GONE);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Ticket ticket = list.get(position);
		//商品总价格大于优惠券的最小使用金额才会显示可以使用的按钮
		String strLowCash = ticket.getLowCash();
		try {
			float lowCash = Float.parseFloat(strLowCash);
			if(totalPrice > lowCash) {
				holder.mustUser.setVisibility(View.VISIBLE);
			} else {
				holder.mustUser.setVisibility(View.GONE);
			}
		} catch (NumberFormatException e) {
			holder.mustUser.setVisibility(View.GONE);
		}

		
		if ("10".equals(ticket.getMoney())) {
			holder.layout.setBackgroundColor(context.getResources().getColor(
					R.color.integer_green));
		} else if ("20".equals(ticket.getMoney())) {
			holder.layout.setBackgroundColor(context.getResources().getColor(
					R.color.integer_bule));
		} else if ("30".equals(ticket.getMoney())) {
			holder.layout.setBackgroundColor(context.getResources().getColor(
					R.color.integer_orange));
		} else {
			holder.layout.setBackgroundColor(context.getResources().getColor(
					R.color.integer_other));
		}
		holder.price.setText("￥" + ticket.getMoney());
		holder.content.setText(ticket.getComments());
		holder.kind.setText(ticket.getName());
		holder.startTime.setText(TimeUtil.converDate(Long.parseLong(ticket
				.getBeginDate())));
		holder.endTime.setText(TimeUtil.converDate(Long.parseLong(ticket
				.getEndDate())));
		holder.mustUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CstmPayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("youhuiquan", ticket.getMoney());			
				bundle.putString("tickId", ticket.getTicketId());
				bundle.putString("tickName", ticket.getName());
				intent.putExtras(bundle);
				context.setResult(Consts.YOUHUIQUAN_RESPONSE, intent);
				context.finish();
			}
		});

		return convertView;
	}

	class ViewHolder {

		private TextView price;// 优惠券价格
		private TextView content;// 优惠券内容
		private TextView kind;// 获取的方式
		private TextView startTime;// 开始时间
		private TextView endTime;// 结束时间
		private TextView mustUser;// 立即使用
		private RelativeLayout layout;
	}

}
