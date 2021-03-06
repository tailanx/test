package com.yidejia.app.mall.adapter;

import java.util.ArrayList;

import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.BrowseHistoryDataManage;
import com.yidejia.app.mall.model.ProductBaseInfo;

public class HistoryAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<ProductBaseInfo> mArrayList;
	private LayoutInflater  inflater;
	private BrowseHistoryDataManage historyDataManage;
	public HistoryAdapter(Context context,ArrayList<ProductBaseInfo> mArrayList){
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mArrayList = mArrayList;
		historyDataManage = new BrowseHistoryDataManage();
		initDisplayImageOption();
	}
	public void setmArrayList(ArrayList<ProductBaseInfo> mArrayList) {
		if (mArrayList  != null)
			this.mArrayList = mArrayList;
		else
			this.mArrayList = new ArrayList<ProductBaseInfo>();
	}

	@Override
	public int getCount() {
		if(null == mArrayList) return 0;
		return mArrayList.size();
	}

	@Override
	public ProductBaseInfo getItem(int arg0) {
		return mArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	protected ImageLoader imageLoader = ImageLoader.getInstance();// 加载图片
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	
	private void initDisplayImageOption() {
		options = MyApplication.getInstance().initGoodsImageOption();
		animateFirstListener = MyApplication.getInstance().getImageLoadingListener();
	}

	@Override
	public View getView(int id, View convertView, ViewGroup arg2) {
		
		ViewHolder holder = null ;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.history_my_collect_item, null);
			holder.head = (ImageView) convertView
					.findViewById(R.id.my_collect_item__imageview1);
			holder.content = (TextView) convertView
					.findViewById(R.id.my_collect_item_text);
			holder.price = (TextView) convertView
					.findViewById(R.id.my_collect_item_money);
			holder.sellCount = (TextView) convertView
					.findViewById(R.id.my_collect_item_sum1);
			holder.commentCount = (TextView) convertView
					.findViewById(R.id.my_collect_item_sum2);
			holder.detele = (TextView) convertView
					.findViewById(R.id.my_collect_item_right);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final ProductBaseInfo ProductBaseInfo = mArrayList.get(id);
		final AlertDialog dialog = new Builder(context).setIcon(R.drawable.ic_launcher).setMessage("是否确认删除").setTitle(context.getResources().getString(R.string.delete)).setPositiveButton(context.getResources().getString(R.string.sure), new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				historyDataManage.delHistory(ProductBaseInfo.getUId()+"");
				mArrayList.remove(ProductBaseInfo);
				notifyDataSetChanged();
			}
		}).setNegativeButton(context.getResources().getString(R.string.cancel),null).create();
		
		holder.content.setText(ProductBaseInfo.getBrief());
		holder.price.setText(ProductBaseInfo.getPrice()+"");
		holder.sellCount.setText(ProductBaseInfo.getSalledAmmount());
		holder.commentCount.setText(ProductBaseInfo.getCommentAmount());
		holder.detele.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.show();
			}
				
		});
		ImageLoader.getInstance().init(MyApplication.getInstance().initConfig());
		imageLoader.displayImage(ProductBaseInfo.getImgUrl(), holder.head, options, animateFirstListener);
	
		return convertView;
	}
	class ViewHolder {
		private ImageView head;
		private TextView content;
		private TextView price;
		private TextView sellCount;
		private TextView commentCount;
		private TextView detele;
	}

}
