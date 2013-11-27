package com.yidejia.app.mall.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.model.Skin;
import com.yidejia.app.mall.model.SkinQOption;
import com.yidejia.app.mall.net.skin.Question;

public class SkinQuesActivity extends Activity {
	private Question question;
	private TextView name;
	private RadioGroup group;
	private TextView textview;
	private ImageView pre;
	private ImageView next;
	private ArrayList<Skin> skinQuestions;
	private LinearLayout linearout;
	private HashMap<Integer, String> haspMap;
//	private Skin skin;
	private ScrollView scrollView;
	private RelativeLayout view;
	private RelativeLayout nextRelative;
	private ImageView back;

	private void setupShow() {
		back = (ImageView) findViewById(R.id.skin_test_question_back);
		name = (TextView) findViewById(R.id.skin_test_question_frist);
		group = (RadioGroup) findViewById(R.id.skin_test_question_radiogroup);
		textview = (TextView) findViewById(R.id.skin_test_number);
		pre = (ImageView) findViewById(R.id.skin_test_pre);
		next = (ImageView) findViewById(R.id.skin_test_next);
		linearout = (LinearLayout) findViewById(R.id.skin_test_lineartlayout);
		scrollView = (ScrollView) findViewById(R.id.skin_test_question_scrollview);
		view = (RelativeLayout) findViewById(R.id.skin_test_question_relative);
		nextRelative = (RelativeLayout) findViewById(R.id.skin_test_question_next_relative);
	}

	
	private String cps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.skin_test_qusetion);
		
		cps = getIntent().getExtras().getString("cps");

		haspMap = new HashMap<Integer, String>();
		setupShow();

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SkinQuesActivity.this.finish();
			}
		});

//		skin = new Skin();

		Task task = new Task();
		task.execute();

		final List<Integer> userAns = new ArrayList<Integer>();
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Log.e("info", index+"  next111");
				// TODO Auto-generated method stub
				// 获取用户选择的答案
				// List<Integer> m = getUserAnswers();
				// 保存用户的选择
				// for (RadioButton c : mList) {
				// if (c.isChecked()) {
				// userAns.add(c.getId());
				// }
				// }
				// 保存用户的选择到当前试题中
				// skin.saveUserAnswers(m);

				group.clearCheck();
				// if (index > maxLength - 1) {
				// // Toast.makeText(SkinQuesActivity.this,
				// // getResources().getString(R.string.skin_test_last),
				// // Toast.LENGTH_SHORT).show();
				// // return;
				// Intent intent = new Intent(SkinQuesActivity.this,
				// SkinAnswerActivity.class);
				// startActivity(intent);
				// }
				// else

				if (index == maxLength - 1) {
					Skin skin1 = skinQuestions.get(index);
					if (skin1.isIs_multiple()) {
						ArrayList<SkinQOption> options = skin1.getOptions();
						 boolean istrue = false;
						for (int op = 0; op < options.size(); op++) {
							SkinQOption skinQuestions = options.get(op);

							if (skinQuestions.isSelected()) {
								// mList.clear();
								istrue = true;
								index++;
								setQuestionLayout();
							}
						}
						if (!istrue) {
							Toast.makeText(SkinQuesActivity.this,
									"亲，请先选择一个或多个选项", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					if (!skin1.isIs_multiple() && skin1.isNeed()) {
						boolean istrue1 = false;
						ArrayList<SkinQOption> options = skin1.getOptions();
						for (int op = 0; op < options.size(); op++) {
							SkinQOption skinQuestions = options.get(op);
							Log.i("info", skinQuestions.isSelected()
									+ "  skinQuestions.isSelected()");

							if (skinQuestions.isSelected()) {
								istrue1 = true;
								b = maxLength + 1;
								index++;
								textview.setText(b + "/" + b);
								nextRelative.setVisibility(View.GONE);
								scrollView.setVisibility(View.GONE);
								view.setVisibility(View.VISIBLE);
								showView(view, SkinQuesActivity.this,cps);
							}
						}
						if (!istrue1) {
							Toast.makeText(SkinQuesActivity.this,
									getResources().getString(R.string.skin_check), Toast.LENGTH_SHORT)
									.show();
							return;
						}
					}

					// b = maxLength + 1;
					// // index++;
					// textview.setText(b + "/" + b);
					// nextRelative.setVisibility(View.GONE);
					// scrollView.setVisibility(View.GONE);
					// view.setVisibility(View.VISIBLE);
					//

				} else if (index < maxLength - 1) {

					Skin skin1 = skinQuestions.get(index);
					
					if (skin1.isIs_multiple()) {
						ArrayList<SkinQOption> options = skin1.getOptions();
						
						boolean istrue = false;
						
						for (int op = 0; op < options.size(); op++) {
							SkinQOption skinQuestions = options.get(op);

							if (skinQuestions.isSelected()) {
								
								// mList.clear();
								// keys.set(index, mList.toString());
								istrue = true;

							}
						}
						if (!istrue) {
							Toast.makeText(SkinQuesActivity.this,
									getResources().getString(R.string.skin_check), Toast.LENGTH_SHORT).show();
							return;
						}
						index++;

						setQuestionLayout();

						keys.set(index - 1, mList.toString());

					}
					if (!skin1.isIs_multiple() && skin1.isNeed()) {
						
//						Log.e("info", index+"  next");
						boolean istrue1 = false;
						ArrayList<SkinQOption> options = skin1.getOptions();
						for (int op = 0; op < options.size(); op++) {
							SkinQOption skinQuestions = options.get(op);
							// Log.i("info",
							// skinQuestions.isSelected()+"  skinQuestions.isSelected()");

							if (skinQuestions.isSelected()) {
								istrue1 = true;
								
							}
						}
						if (!istrue1) {
							Toast.makeText(SkinQuesActivity.this,
									"亲，请先选择一个或多个选项", Toast.LENGTH_SHORT)
									.show();
							return;
						}
						index++;
						setQuestionLayout();
//						Log.e("info", index+"  next2");
						
					} else if (!skin1.isNeed()) {
						index++;
						Skin skin2 = skinQuestions.get(index);
						if (skin2.isIs_multiple()) {
							mList.clear();
						}
						setQuestionLayout();
						// Skin skin = skinQuestions.get(index);
						// if (skin.isIs_multiple()) {
						//
						// }
						// if (index == maxLength - 1) {
						// //
						// next.setText(getResources().getString(R.string.commit));
						// }
						// scrollView.setVisibility(View.VISIBLE);
						// view.setVisibility(View.GONE);
					}
					Log.i(SkinQuesActivity.class.getName(), keys.toString()
							+ "   keys");
				}
			}
		});
		pre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (index <= 0) {
					Toast.makeText(SkinQuesActivity.this,
							getResources().getString(R.string.skin_test_frist),
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					group.clearCheck();
					index--;
					Skin skin1 = skinQuestions.get(index);
					if (skin1.isIs_multiple()) {
						mList.clear();

					}
					nextRelative.setVisibility(View.VISIBLE);
					scrollView.setVisibility(View.VISIBLE);
					view.setVisibility(View.GONE);
					// next.setText(getResources().getString(R.string.pre));
					setQuestionLayout();

				}
				Log.i(SkinQuesActivity.class.getName(), keys.toString()
						+ "   keys");
//				Log.e("info", index+"  pre");
			}
		});

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {//

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (group.getCheckedRadioButtonId() == -1) {// ||group.getCheckedRadioButtonId()
															
					return;
				} else {
					int lastBtnId = 0;
					if (!btnId.isEmpty() && btnId.size() - 1 >= index) {//
						// 获取上次保存的id
						lastBtnId = btnId.get(index);
					}
					if (lastBtnId == checkedId) {
						return;
					}
//					// RadioButton btn = (RadioButton) findViewById(checkedId);
//					Log.e("info", group.getCheckedRadioButtonId() + "");

					RadioButton btn = (RadioButton) findViewById(group
							.getCheckedRadioButtonId());
					String value = btn.getText().toString();

					Skin skin = skinQuestions.get(index);
					ArrayList<SkinQOption> options = skin.getOptions();
					int valueLength = options.size();
					for (int i = 0; i < valueLength; i++) {
						if (options.get(i).getValue().equals(value)) {

							SkinQOption skinQuestions = options.get(i);

							skinQuestions.setSelected(true);

							String key = options.get(i).getKey();

							keys.set(index, key);
							btnId.set(index, checkedId);

							// Log.i(SkinQuesActivity.class.getName(),
							// keys.toString() + "   keys");
						}
					}

				}
				// //
				// // TODO Auto-generated method stub
				// // if(checkedId == group.getCheckedRadioButtonId()){
				// Toast.makeText(SkinQuesActivity.this,
				// group.getCheckedRadioButtonId() + "",
				// Toast.LENGTH_SHORT).show();
				// // }
//				Log.e("info", index+"group");
			}
			
		});

	}

	private List<Integer> btnId = new ArrayList<Integer>();
	public static List<String> keys = new ArrayList<String>();

	private List<String> keys1 = new ArrayList<String>();
	private List<Integer> mList = new ArrayList<Integer>();

	int a = 0;

	private void setQuestionLayout() {
		// index++;
		b = index + 1;
		Skin skin = skinQuestions.get(index);
		textview.setText(b + "/" + maxLength);
		if (skin.isIs_multiple()) {
			mList.clear();
			name.setText(b + "." + skin.getQuestion() + "(必选)");
			ArrayList<SkinQOption> options = (ArrayList<SkinQOption>) skin
					.getOptions();
			group.removeAllViews();

			for (int j = 0; j < options.size(); j++) {
				final SkinQOption option = options.get(j);
				final CheckBox cb = new CheckBox(SkinQuesActivity.this);

				cb.setButtonDrawable(R.drawable.checkbox_style);
				cb.setPadding(150, 0, 0, 0);

				if (option.isSelected()) {
					// Log.i("info", option.isSelected() + " ");
					cb.setChecked(true);
				}
//				Log.i("info", option.isSelected() + " ");
				cb.setId(j);
				if (option.isSelected()) {
					a = cb.getId() + 1;
					mList.add(a);
				}
				cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						option.setSelected(isChecked);
						if (isChecked) {

							a = cb.getId() + 1;
							mList.add(a);

						}
					}
				});

				cb.setText(options.get(j).getValue());
				cb.setTextColor(getResources().getColor(R.color.skin_test));
				group.addView(cb);
			}
			keys.set(index, mList.toString());

		} else {
			if (skin.isNeed()) {
				name.setText(b + "." + skin.getQuestion() + "(必选)");
				ArrayList<SkinQOption> option = (ArrayList<SkinQOption>) skin
						.getOptions();
				group.removeAllViews();

				for (int i = 0; i < option.size(); i++) {

					// if (!m.isEmpty()) {
					// return;
					// } else {
					// // int a = m.get(0);
					// Log.e("info", "   a");
					// }
					final RadioButton button = new RadioButton(
							SkinQuesActivity.this);
					// 选中的按钮
					int checkId = btnId.get(index);
//					Log.i("info", checkId + "checkId");
					if (i == checkId) {
						button.setChecked(true);
					}
					// Drawable end =
					// SkinQuesActivity.this.getResources().getDrawable(R.drawable.checkbox_style);
					// button.setCompoundDrawablesRelative(null, null, end,
					// null);
					// button.setCompoundDrawables (null, null, end, null);
					// button.
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							80);

					button.setPadding(80, 0, 0, 0);

					// button.setLayoutParams(lp);
					// button.setCameraDistance(10.0f);
					button.setGravity(Gravity.CENTER);
					button.setBackgroundResource(R.drawable.listbg);
					button.setButtonDrawable(R.drawable.checkbox_style);
					button.setId(i);
					button.setText(option.get(i).getValue());
					button.setTextColor(getResources().getColor(
							R.color.skin_test));

					group.addView(button);
					//
					// group.setPadding(0, 50, 0, 50);
					// group.setVerticalFadingEdgeEnabled(true);
					// group.setFadingEdgeLength(50);

				}
//				Log.e("info", index+"  sssss");

			} else {
				name.setText(b + "." + skin.getQuestion()
						+ "(可选填写,多个品牌之间用逗号分开)");
				group.removeAllViews();
				EditText editTextView = new EditText(SkinQuesActivity.this);
				editTextView.setLayoutParams(new LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				editTextView.setBackgroundResource(R.drawable.listbg);
				editTextView.setPadding(20, 0, 20, 0);
				editTextView.setSingleLine();
				editTextView.setTextColor(getResources().getColor(
						R.color.price_color));
				editTextView.setCursorVisible(true);

				group.addView(editTextView);
				// keys.set(index, editTextView.getText().toString());

			}
		}

	}

	private void save(String object) {// 填写第四题的品牌时
		btnId.set(index, -1);
		keys.set(index, object);
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
					for (int i = 0; i < maxLength; i++) {
						keys.add(i + "");
						btnId.add(-1);
					}
				}

				return isSucess;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return false;
		}

	}

	private void showView(RelativeLayout view, SkinQuesActivity activity,String cps) {
		SkinAnswerActivity sa = new SkinAnswerActivity(view, activity,cps);

	}
}
