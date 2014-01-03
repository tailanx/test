/**
 * 摇一摇功能的控制类
 */
package com.yidejia.app.mall.shark;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;

/**
 * 
 * 摇一摇功能的控制类
 * @version 1.0 2013/1/3
 * @author Long Bin
 *
 */
public class SharkUtil {

//	private Activity activity;
	private SensorManager sensorManager;
	private Vibrator vibrator;
	private IShark iShark;
	private String TAG = SharkUtil.class.getName();
	
	public SharkUtil(Activity activity) {
//		this.activity = activity;
		
		sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	
	public void registerListener(IShark iShark) {
		this.iShark = iShark;
		if (sensorManager != null) {// 注册监听器
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_NORMAL);
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		}
	}
	
	public void unregisterListener() {
		sensorManager.unregisterListener(sensorEventListener);
	}
	
	/**
	* 重力感应监听
	*/
	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			// 传感器信息改变时执行该方法
			float[] values = event.values;
			float x = values[0]; // x轴方向的重力加速度，向右为正
			float y = values[1]; // y轴方向的重力加速度，向前为正
			float z = values[2]; // z轴方向的重力加速度，向上为正
			Log.i(TAG, "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
			// 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
			int medumValue = 18;
			if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
//				vibrator.vibrate(200);
//				handler = new Handler();
				iShark.onStart();
				vibrator.vibrate(new long[]{10, 200, 500, 200}, -1);
				handler.postDelayed(runnable, 3000);
//				handler.postDelayed(runnable, 800);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			iShark.onFinish();
		}
	};
}
