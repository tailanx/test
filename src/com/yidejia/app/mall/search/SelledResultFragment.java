package com.yidejia.app.mall.search;

import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.goodinfo.GoodsInfoActivity;
import com.yidejia.app.mall.initview.SRViewWithImage;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.model.SearchItem;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class SelledResultFragment extends SherlockFragment {

	public static SelledResultFragment newInstance(Bundle searchBundle,
			boolean title) {

		SelledResultFragment fragment = new SelledResultFragment();
//		Bundle bundle = new Bundle();
		searchBundle.putBoolean("isShowWithList", title);
//		bundle.putBundle("bundle", searchBundle);
		fragment.setArguments(searchBundle);
//		bundle = null;
		return fragment;
	}

	private boolean isShowWithList;
	private boolean defaultInt = true;
	private String TAG = "SelledResultFragment";

	private ListView selledListView;
	private SelledResultListAdapter searchListAdapter;
	private LinearLayout searchResultLayout;
	private PullToRefreshListView mPullToRefreshListView;
	private PullToRefreshScrollView mPullToRefreshScrollView;

	private ArrayList<SearchItem> searchItemsArray;

	private int fromIndex = 0;// 开始
	private int amount = 10;// 个数
	private String name = "";// 名称
	private String fun = "";// 功效
	private String price = "";// 价格区间
	private String brand = "";// 品牌
	private String order = "";// 排序

	private boolean isFirstIn = true;
	private boolean isHasResult = true;// 搜索是否有结果
//	private boolean isNoMore = false;// 判断是否还有更多数据,true为没有更多了

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle searchBundle = getArguments();
		isShowWithList = (searchBundle != null) ? searchBundle.getBoolean("isShowWithList")
				: defaultInt;
//		Bundle searchBundle = bundle.getBundle("bundle");
		name = searchBundle.getString("name");
		fun = searchBundle.getString("fun");
		price = searchBundle.getString("price");
		brand = searchBundle.getString("brand");
		order = searchBundle.getString("order");
		
	}

	private View view = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (isShowWithList) {
			view = inflater.inflate(
					R.layout.activity_search_result_list_layout, container,
					false);
			mPullToRefreshListView = (PullToRefreshListView) view
					.findViewById(R.id.pull_refresh_list);
			mPullToRefreshListView
					.setOnRefreshListener(listviewRefreshListener);
			emptyLayout = (RelativeLayout) view.findViewById(R.id.search_empty);
			selledListView = mPullToRefreshListView.getRefreshableView();
		} else {
			view = inflater.inflate(R.layout.activity_search_image_layout,
					container, false);
			emptyLayout = (RelativeLayout) view.findViewById(R.id.search_empty);
			mPullToRefreshScrollView = (PullToRefreshScrollView) view
					.findViewById(R.id.pull_refresh_scrollview);
			searchResultLayout = (LinearLayout) view
					.findViewById(R.id.search_result_layout);
			mPullToRefreshScrollView
					.setOnRefreshListener(scrollviewRefreshListener);
		}
		return view;
	}

	private RelativeLayout emptyLayout;

	private void initWithListView() {
		if (isFirstIn) {
			searchListAdapter = new SelledResultListAdapter(getActivity(),
					searchItemsArray);
			selledListView.setAdapter(searchListAdapter);
		} else {
			searchListAdapter.notifyDataSetChanged();
		}
		selledListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						GoodsInfoActivity.class);
				intent.putExtra("goodsId", searchItemsArray.get(arg2 - 1)
						.getUId());
				startActivity(intent);
			}
		});
	}

	private void initWithImageView() {
		int count = searchItemsArray.size();
		SRViewWithImage srViewWithImage = null;
		boolean isOdd = (count % 2 == 0) ? false : true;
		for (int i = 0; i < count; i++) {
			View child = null;
			if (isOdd && i == count - 1) {
				child = getActivity().getLayoutInflater().inflate(
						R.layout.item_search_result_image_odd, null);
				srViewWithImage = new SRViewWithImage(getActivity(),
						child);
				srViewWithImage.initView(searchItemsArray.get(i));
			} else {
				child = getActivity().getLayoutInflater().inflate(
						R.layout.item_search_result_image_even, null);
				srViewWithImage = new SRViewWithImage(getActivity(),
						child);
				srViewWithImage.initView(searchItemsArray.get(i),
						searchItemsArray.get(i + 1));
				i++;
			}
			searchResultLayout.addView(child);

		}
	}

	private OnRefreshListener<ListView> listviewRefreshListener = new OnRefreshListener<ListView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			fromIndex += amount;
//			closeTask();
//			task = new Task();
//			task.execute();
			getSearchListData();
		}
	};

	private OnRefreshListener<ScrollView> scrollviewRefreshListener = new OnRefreshListener<ScrollView>() {

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			fromIndex += amount;
			searchItemsArray.clear();
			isFirstIn = false;
			getSearchListData();
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "TestFragment-----onActivityCreated");
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setProgressStyle(R.style.StyleProgressDialog);
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getSearchListData();
	}
	
	private ProgressDialog progressDialog;
	
	private HttpClientUtil client;
	
	private void getSearchListData() {
		String url = "";
		try {
//			url = new JNICallBack().getHttp4GetSearch(
//					URLEncoder.encode(name, "UTF-8"),
//					URLEncoder.encode(fun, "UTF-8"),
//					URLEncoder.encode(brand, "UTF-8"),
//					URLEncoder.encode(price, "UTF-8"),
//					URLEncoder.encode(order, "UTF-8"),
//					URLEncoder.encode(fromIndex + "", "UTF-8"),
//					URLEncoder.encode(amount + "", "UTF-8"));
			
			url = new JNICallBack().getHttp4GetSearch(URLEncoder.encode(name, "UTF-8"), fun, brand, price, order, fromIndex + "", amount + "");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		client = new HttpClientUtil();
		client.getHttpResp(url, new IHttpResp() {

			@Override
			public void onStart() {
				super.onStart();
				if (isFirstIn && isAdded() && null != progressDialog) {
//					bar = (ProgressDialog) new YLProgressDialog(
//							getActivity()).createLoadingDialog(
//							getActivity(), null);
					progressDialog.show();
					progressDialog.setContentView(R.layout.progress_dialog);
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
//				if (null != bar) {
//					bar.dismiss();
//				}
				
				if(!isAdded())return;
				
				if(null != progressDialog && progressDialog.isShowing()) progressDialog.dismiss();
				
				if (null != mPullToRefreshListView) {
					mPullToRefreshListView.onRefreshComplete();
				}
				if (null != mPullToRefreshScrollView) {
					mPullToRefreshScrollView.onRefreshComplete();
				}
				isFirstIn = false;
			}

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
//				Log.e(TAG, content);
				if(!isAdded()) return;
				if(isShowWithList){
					mPullToRefreshListView.setVisibility(View.VISIBLE);
				} else {
					mPullToRefreshScrollView.setVisibility(View.VISIBLE);
				}
				emptyLayout.setVisibility(View.GONE);
				ParseSearchJson parseSearchJson = new ParseSearchJson();
				isHasResult = parseSearchJson.parseSearchJson(content);
				if(!isHasResult) {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.nomore),
							Toast.LENGTH_SHORT).show();
					return;
				}
				if(null == searchItemsArray) {
					searchItemsArray = new ArrayList<SearchItem>();
				}
				ArrayList<SearchItem> tmpItems = parseSearchJson.getSearchResults();
				if(null == tmpItems || tmpItems.isEmpty()) {
					Toast.makeText(getActivity(), getResources().getString(R.string.nomore), Toast.LENGTH_SHORT).show();
					return;
				}
				searchItemsArray.addAll(tmpItems);
				
				if (null != searchItemsArray) {
					if(isShowWithList) {
						initWithListView();
					} else {
						initWithImageView();
					}
				}
				//显示更新时间
				String label = getResources().getString(R.string.update_time)
						+ DateUtils.formatDateTime(getActivity()
								.getApplicationContext(), System
								.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
				if (null != mPullToRefreshListView) {
					mPullToRefreshListView.getLoadingLayoutProxy()
					.setLastUpdatedLabel(label);
				}
				if (null != mPullToRefreshScrollView) {
					mPullToRefreshScrollView.getLoadingLayoutProxy()
					.setLastUpdatedLabel(label);
				}

			}

			@Override
			public void onError() {
				super.onError();
				if(!isAdded()) return;
				fromIndex -= amount;
				if(fromIndex < 0) {
					fromIndex = 0;
				}
				
				Toast.makeText(getActivity(), getResources().getString(R.string.bad_network), Toast.LENGTH_SHORT).show();
				
				if(isFirstIn){
					emptyLayout.setVisibility(View.VISIBLE);
					if(isShowWithList){
						mPullToRefreshListView.setVisibility(View.GONE);
					} else {
						mPullToRefreshScrollView.setVisibility(View.GONE);
					}
					
					Button guang_button = (Button) emptyLayout.findViewById(R.id.guang_button);
					guang_button.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent (getActivity(),SearchResultActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("title", "全部");
							bundle.putString("name", "");
							bundle.putString("price", "");
							bundle.putString("brand", "");
							bundle.putString("fun", "");
							bundle.putBoolean("isShowWithList", isShowWithList);
							intent.putExtras(bundle);
							getActivity().startActivity(intent);
							getActivity().finish();
						}
					});
				}
			}

		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "TestFragment-----onStart");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (null != searchItemsArray) {
			searchItemsArray.clear();
			searchItemsArray = null;
		}
		if(null != client)
		client.closeConn();
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
}