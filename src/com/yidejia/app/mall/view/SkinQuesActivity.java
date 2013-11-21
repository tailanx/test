package com.yidejia.app.mall.view;

import java.util.ArrayList;

import android.R.layout;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Skin;
import com.yidejia.app.mall.model.SkinQOption;
import com.yidejia.app.mall.net.skin.Question;

public class SkinQuesActivity extends Activity {
	private Question question;
	private TextView name;
	private RadioGroup group;
	private TextView textview;
	private Button pre;
	private Button next;
	private ArrayList<Skin> skinQuestions;
	private LinearLayout linearout;

	private void setupShow() {
		name = (TextView) findViewById(R.id.skin_test_question_frist);
		group = (RadioGroup) findViewById(R.id.skin_test_question_radiogroup);
		textview = (TextView) findViewById(R.id.skin_test_number);
		pre = (Button) findViewById(R.id.skin_test_pre);
		next = (Button) findViewById(R.id.skin_test_next);
		linearout = (LinearLayout) findViewById(R.id.skin_test_lineartlayout);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skin_test_qusetion);
		setupShow();

		Task task = new Task();
		task.execute();

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				index++;
				setQuestionLayout();

			}
		});
		pre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				index--;

				setQuestionLayout();
			}
		});

	}

	private void setQuestionLayout() {
		// index++;
		if (index < 0 || index >= maxLength) {
			return;
		} else {
			b = index + 1;
			Skin skin = skinQuestions.get(index);
			textview.setText("题目："+b+"/"+maxLength);
			if (skin.isNeed()) {
				name.setText(b + "." + skin.getQuestion() + "(必选)");
				ArrayList<SkinQOption> option = (ArrayList<SkinQOption>) skin
						.getOptions();
				group.removeAllViews();

				for (int i = 0; i < option.size(); i++) {

					RadioButton button = new RadioButton(SkinQuesActivity.this);

					button.setText(option.get(i).getValue());
					group.addView(button);

				}
			} else {
				name.setText(b + "." + skin.getQuestion() + "(可选填写)");
				group.removeAllViews();
				EditText editTextView = new EditText(SkinQuesActivity.this);
				editTextView.setLayoutParams(new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				editTextView.setBackgroundResource(R.drawable.comment);
				editTextView.setCursorVisible(true);
				group.addView(editTextView);
			}

		}
	}

	private int index = 0;
	private int b;
	private int maxLength = 0;

	private class Task extends AsyncTask<Void, Void, Boolean> {
		private ProgressDialog bar;

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if (result) {
				setQuestionLayout();
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bar = new ProgressDialog(SkinQuesActivity.this);
			bar.setCancelable(true);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.show();

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean isSucess = false;
			try {
				question = new Question();
				String httpresp = question.getHttpResp();
				if (httpresp != null) {
					isSucess = question.analysis(httpresp);
					skinQuestions = question.getSkinQs();
					maxLength = skinQuestions.size();
					

				}

				return isSucess;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return false;
		}

	}
}
