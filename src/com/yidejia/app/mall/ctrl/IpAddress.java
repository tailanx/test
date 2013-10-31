package com.yidejia.app.mall.ctrl;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.util.Log;

public class IpAddress {

	private String getLocalIpAddress() {
	     try {
	         for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	             NetworkInterface intf = en.nextElement();
	             for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                 InetAddress inetAddress = enumIpAddr.nextElement();
	                 if (!inetAddress.isLoopbackAddress()) {
	                     return inetAddress.getHostAddress().toString();
	                 }
	             }
	         }
	     } catch (SocketException ex) {
	         Log.e("testAndroid1", ex.toString());
	     }
	     return null;
	} 
	
	
	private String getLocalIPV4Address(){ 
		try {
			
			for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();en.hasMoreElements();){ 
				NetworkInterface intf = en.nextElement(); 
				for(Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){ 
					InetAddress inetAddress = enumIpAddr.nextElement(); 
					if(!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)){ 
						return inetAddress.getHostAddress().toString(); 
					} 
				} 
			} 
		} catch (SocketException e) {
			// TODO: handle exception
		}
	    return "null"; 
	} 
	
	public String getIpAddress(){
		String ip = "";
		int currentapiVersion=android.os.Build.VERSION.SDK_INT;
		if(currentapiVersion >= 14){
			ip = getLocalIPV4Address();
		} else{
			ip = getLocalIpAddress();
		}
		return ip;
	}
}
