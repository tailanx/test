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
 * 
 * @version 1.0 2013/1/3
 * @author Long Bin
 * 
 */
public class SharkUtil {

	// 速度阈值，当摇晃速度达到这值后产生作用
	private static final int SPEED_SHRESHOLD = 3000;
	// 两次检测的时间间隔
	private static final int UPTATE_INTERVAL_TIME = 70;
	private SensorManager sensorManager;
	private Vibrator vibrator;
	private IShark iShark;
	private String TAG = SharkUtil.class.getName();
	// 手机上一个位置时重力感应坐标
	private float lastX;
	private float lastY;
	private float lastZ;
	// 上次检测时间
	private long lastUpdateTime;

	/**
	 * 封装摇一摇功能的类，需要添加权限<code>android:name="android.permission.VIBRATE" </code>
	 * 
	 * @param activity
	 *            注册摇一摇功能的activity
	 */
	public SharkUtil(Activity activity) {
		// this.activity = activity;

		sensorManager = (SensorManager) activity
				.getSystemService(Context.SENSOR_SERVICE);
		vibrator = (Vibrator) activity
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	/**
	 * 注册摇一摇监听
	 * 
	 * @param iShark
	 *            摇一摇的接口，摇一摇时只需实现onStart() 和 onFinish()方法
	 */
	public void registerListener(IShark iShark) {
		this.iShark = iShark;
		if (sensorManager != null) {// 注册监听器
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_GAME);
			// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
		}
	}

	/**
	 * 取消摇一摇监听
	 */
	public void unregisterListener() {
		sensorManager.unregisterListener(sensorEventListener);
	}

	/**
	 * 重力感应监听
	 */
	private SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {

			// 现在检测时间
			long currentUpdateTime = System.currentTimeMillis();
			// 两次检测的时间间隔
			long timeInterval = currentUpdateTime - lastUpdateTime;
			// 判断是否达到了检测时间间隔
			if (timeInterval < UPTATE_INTERVAL_TIME)
				return;
			// 现在的时间变成last时间
			lastUpdateTime = currentUpdateTime;
			// 传感器信息改变时执行该方法
			float[] values = event.values;
			float x = values[0]; // x轴方向的重力加速度，向右为正
			float y = values[1]; // y轴方向的重力加速度，向前为正
			float z = values[2]; // z轴方向的重力加速度，向上为正
			// Log.i(TAG, "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" +
			// z);
			// // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
			// int medumValue = 19;
			// if (Math.abs(x) > medumValue || Math.abs(y) > medumValue
			// || Math.abs(z) > medumValue) {
			// // vibrator.vibrate(200);
			// // handler = new Handler();
			// iShark.onStart();
			// Log.e("info", "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y +
			// "；z轴方向的重力加速度" + z);

			// 第一个参数是震动的前奏时间，第二个参数是震动到不震动的时间
			// // handler.postDelayed(runnable, 3000);
			// // handler.postDelayed(runnable, 800);
			// }
			// 获得x,y,z的变化值
			float deltaX = x - lastX;
			float deltaY = y - lastY;
			float deltaZ = z - lastZ;

			// 将现在的坐标变成last坐标
			lastX = x;
			lastY = y;
			lastZ = z;

			double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
					* deltaZ)
					/ timeInterval * 10000;
			// 达到速度阀值，发出提示
			if (speed >= SPEED_SHRESHOLD) {
				iShark.onStart();
				vibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);//
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

	};

	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			iShark.onFinish();
		}
	};
}
