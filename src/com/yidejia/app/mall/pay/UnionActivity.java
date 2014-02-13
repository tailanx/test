package com.yidejia.app.mall.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.R;

public class UnionActivity extends BaseActivity{
	private String respCode;
	private String upayTn;
	
	private int payMode;
	
	private UnionPayUtil payUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent getIntent = getIntent();
		Bundle getBundle = getIntent.getExtras();
		payMode = getBundle.getInt("mode");
		if(payMode == 1){//银联支付
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			respCode = getBundle.getString("resp_code");
			upayTn = getBundle.getString("tn");
			setContentView(R.layout.activity_userpay);
			payUtil = new UnionPayUtil(this, respCode, upayTn);
			payUtil.unionPayOrder();
		} 
		
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/************************************************* 
         * 
         *  步骤3：处理银联手机支付控件返回的支付结果 
         *  
         ************************************************/
        if(null != payUtil) payUtil.onActivityResult(data);
	}
	
}
