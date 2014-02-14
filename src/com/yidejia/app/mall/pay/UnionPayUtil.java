package com.yidejia.app.mall.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.yidejia.app.mall.order.AllOrderActivity;
import com.yidejia.app.mall.order.WaitPayActivity;
import com.yidejia.app.mall.util.ActivityIntentUtil;

public class UnionPayUtil {

	private String respCode;
	private String upayTn;
	
	private Activity activity;
	
	public UnionPayUtil(Activity activity, String respCode, String upayTn){
		this.activity = activity;
		this.respCode = respCode;
		this.upayTn = upayTn;
	}
	
	public void unionPayOrder(){
		if (null != respCode && "00".equals(respCode) && null != upayTn && !"".equals(upayTn)) {
			
			UPPayAssistEx.startPayByJAR(activity,
					PayActivity.class, null, null, upayTn, "00");
			
		} else {
			Toast.makeText(activity, "支付失败", Toast.LENGTH_LONG).show();
		}
	}
	
	public void onActivityResult(Intent data) {
		/************************************************* 
         * 
         *  步骤3：处理银联手机支付控件返回的支付结果 
         *  
         ************************************************/
        if (data == null) {
            return;
        }
        
        boolean isSuccess = false;
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
        	StatService.onEventDuration(activity, "unionpay success", "unionpay success", 100);
            msg = "支付成功！";
            isSuccess = true;
        } else if (str.equalsIgnoreCase("fail")) {
        	StatService.onEventDuration(activity, "unionpay fail", "unionpay fail", 100);
            msg = "支付失败，是否重试？";
            isSuccess = false;
        } else if (str.equalsIgnoreCase("cancel")) {
        	StatService.onEventDuration(activity, "unionpay cancel", "unionpay cancel", 100);
            
            ActivityIntentUtil.intentActivityAndFinish(activity, WaitPayActivity.class);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        //builder.setCustomTitle();
		if (isSuccess) {//支付成功
			builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ActivityIntentUtil.intentActivityAndFinish(activity, AllOrderActivity.class);
				}
			});
		} else {
			builder.setPositiveButton("是",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					StatService.onEventDuration(activity, "unionpay again", "unionpay again", 100);
					unionPayOrder();
				}
			});
        	builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                   ActivityIntentUtil.intentActivityAndFinish(activity, WaitPayActivity.class);
                }
            });
        }
        builder.create().show();
	}
}
