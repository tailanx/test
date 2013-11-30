package com.yidejia.app.mall.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * ����ʱ��ѡ��ؼ�
 * 
 * @author ��Į
 */
public class DateTimePickerDialog implements OnDateChangedListener,
		OnTimeChangedListener {
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private Activity activity;

	/**
	 * ����ʱ�䵯��ѡ���
	 * 
	 * @param activity
	 *            �����õĸ�activity
	 */
	public DateTimePickerDialog(Activity activity) {
		this.activity = activity;
	}

	public void init(DatePicker datePicker, TimePicker timePicker) {
		Calendar calendar = Calendar.getInstance();
		initDateTime = calendar.get(Calendar.YEAR) + "-"
				+ calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + " "
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND);
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), this);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
	}

//	/**
//	 * ��������ʱ��ѡ���
//	 * 
//	 * @param dateTimeTextEdite
//	 *            ��Ҫ���õ�����ʱ���ı��༭��
//	 * @param type
//	 *            : 0Ϊ����ʱ������:yyyy-MM-dd HH:mm:ss 1Ϊ��������:yyyy-MM-dd
//	 *            2Ϊʱ������:HH:mm:ss
//	 * @return
//	 */
//	public AlertDialog dateTimePicKDialog(final EditText dateTimeTextEdite,
//			int type) {
//		Calendar c = Calendar.getInstance();
//		switch (type) {
//		case 1:
//			new DatePickerDialog(activity,
//					new DatePickerDialog.OnDateSetListener() {
//						public void onDateSet(DatePicker datePicker, int year,
//								int monthOfYear, int dayOfMonth) {
//							Calendar calendar = Calendar.getInstance();
//							calendar.set(datePicker.getYear(),
//									datePicker.getMonth(),
//									datePicker.getDayOfMonth());
//							SimpleDateFormat sdf = new SimpleDateFormat(
//									"yyyy-MM-dd");
//							dateTime = sdf.format(calendar.getTime());
//							dateTimeTextEdite.setText(dateTime);
//						}
//					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
//					c.get(Calendar.DATE)).show();
//
//		}
//		return null;
////		return ad;
//	}
	
	/**
	 * ��������ʱ��ѡ���
	 * 
	 * @param dateTimeTextEdite
	 *            ��Ҫ���õ�����ʱ���ı��༭��
	 * @param type
	 *            : 0Ϊ����ʱ������:yyyy-MM-dd HH:mm:ss 1Ϊ��������:yyyy-MM-dd
	 *            2Ϊʱ������:HH:mm:ss
	 * @return
	 */
	public AlertDialog dateTimePicKDialog(final TextView dateTimeTextEdite,int type) {
		Calendar c = Calendar.getInstance();
		switch (type) {
		case 1:
			new DatePickerDialog(activity,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker datePicker, int year,
								int monthOfYear, int dayOfMonth) {
							Calendar calendar = Calendar.getInstance();
							calendar.set(datePicker.getYear(),
									datePicker.getMonth(),
									datePicker.getDayOfMonth());
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							dateTime = sdf.format(calendar.getTime());
							dateTimeTextEdite.setText(dateTime);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DATE)).show();

		}
		return null;
//		return ad;
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		onDateChanged(null, 0, 0, 0);
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
				timePicker.getCurrentMinute());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateTime = sdf.format(calendar.getTime());
		ad.setTitle(dateTime);
	}

}