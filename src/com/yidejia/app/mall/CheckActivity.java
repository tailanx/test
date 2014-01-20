package com.yidejia.app.mall;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.datamanage.TaskGetShipLog;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 快递查询activity
 * @author LongBin
 *
 */
public class CheckActivity extends BaseActivity {

	private String shipCode;//快递单号
	private String shipCompany;//快递公司
	
	private TextView shipCompanyTextView;//
	private TextView shipCodeTextView;
	private LinearLayout logistics_details_layout;
	private ImageView check_logistics_image;
	
	private TaskGetShipLog task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setActionbar();
		setActionbarConfig();
		setTitle(R.string.check_logistics);
		//这里父布局要用scrollview
		setContentView(R.layout.check_logistics);
		
		Bundle bundle = getIntent().getExtras();
		shipCode = bundle.getString("shipCode");
		shipCompany = bundle.getString("shipCompany");
		
		findIds();
		shipCompanyTextView.setText(shipCompany);
		shipCodeTextView.setText(shipCode);
		int id = getImageIdByShipCom(shipCompany);
		if(id != -1)
			check_logistics_image.setImageResource(id);
		
		task = new TaskGetShipLog(CheckActivity.this, logistics_details_layout);
		task.getShipLogs(shipCode);
	}
	
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(task != null) task.closeTask();
	}




	private void findIds(){
		shipCompanyTextView = (TextView) findViewById(R.id.check_logistics_name);
		shipCodeTextView = (TextView) findViewById(R.id.check_logistics_number);
		logistics_details_layout = (LinearLayout) findViewById(R.id.logistics_details_layout);
		check_logistics_image = (ImageView) findViewById(R.id.check_logistics_image);
	}
	
	
	/**
	 * 根据公司名称 获取公司图片的id
	 * @param shipCompany
	 * @return
	 */
	private int getImageIdByShipCom(String shipCompany){
		int id = -1;
		Resources rs = getResources();
		if(shipCompany.contains(rs.getString(R.string.zto))){
			id = R.drawable.zto;
		} else if(shipCompany.contains(rs.getString(R.string.sto))){
			id = R.drawable.sto;
		} else if(shipCompany.contains(rs.getString(R.string.yto))){
			id = R.drawable.yto;
		} else if(shipCompany.contains(rs.getString(R.string.ems_ex))){
			id = R.drawable.ems;
		} else if(shipCompany.contains(rs.getString(R.string.yunda))){
			id = R.drawable.yunda;
		} else if(shipCompany.contains(rs.getString(R.string.uc_ex))){
			id = R.drawable.ucexpress;
		} else if(shipCompany.contains(rs.getString(R.string.lbex))){
			id = R.drawable.lbex;
		} else if(shipCompany.contains(rs.getString(R.string.ttkdex))){
			id = R.drawable.ttkdex;
		} else if(shipCompany.contains(rs.getString(R.string.qfkd))){
			id = R.drawable.qfkd;
		} else if(shipCompany.contains(rs.getString(R.string.best_ex))){
			id = R.drawable.best_express;
		}
		return id;
	}
	
}
