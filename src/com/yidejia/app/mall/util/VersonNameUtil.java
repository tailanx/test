package com.yidejia.app.mall.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.yidejia.app.mall.MyApplication;

/**获取版本号**/
public class VersonNameUtil {

	
	/**获取版本号**/
	public static String getVersionName() {
		String versionName = "";
		try {
			PackageInfo packInfo = MyApplication.getInstance()
					.getPackageManager()
					.getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

}
