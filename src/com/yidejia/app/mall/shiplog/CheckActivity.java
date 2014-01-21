package com.yidejia.app.mall.shiplog;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import com.baidu.mobstat.StatService;
import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.ShipLog;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		
		getShipLog();
	}
	
	/**获取快递信息**/
	private void getShipLog(){
		String url = new JNICallBack().getHttp4GetShipLog(shipCode);
		
		AsyncOkHttpClient client = new AsyncOkHttpClient();
		client.get(url, new AsyncHttpResponse(){

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if(HttpStatus.SC_OK == statusCode){
					ParseLogJson parseLogJson = new ParseLogJson();
					boolean isSuccess = parseLogJson.parseShipLog(content);
					ArrayList<ShipLog> shipLogs = parseLogJson.getShipLogs();
					if(isSuccess && null != shipLogs) {
						int length = shipLogs.size();
						for (int i = 0; i < length; i++) {
							ShipLogViewCtrl viewCtrl = new ShipLogViewCtrl(CheckActivity.this);
							View view = viewCtrl.addView();
							viewCtrl.setText(shipLogs.get(length - 1- i).getContext(), shipLogs.get(length - 1- i).getTime());
							if(i == 0){
								viewCtrl.setTextColor("#ed217c");
							} else {
								viewCtrl.setTextColor("#000000");
							}
							logistics_details_layout.addView(view);
						}
					} else {
						Toast.makeText(CheckActivity.this, "暂无快递信息", Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onError(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onError(error, content);
				Toast.makeText(CheckActivity.this, getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	protected void onResume() {
		super.onResume();
//		StatService.onResume(this);
		StatService.onPageStart(this, "查看物流页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
//		StatService.onPause(this);
		StatService.onPageEnd(this, "查看物流页面");
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
