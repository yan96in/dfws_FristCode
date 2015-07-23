/**
 * Copyright © 2014-3-15 FindJob www.veryeast.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package zjdf.zhaogongzuo.fragments;

import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.search.AddressActivity;
import zjdf.zhaogongzuo.activity.search.KeywordActivity;
import zjdf.zhaogongzuo.activity.search.PositionClassActivity;
import zjdf.zhaogongzuo.activity.search.PositionListActivity;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.databases.handlers.SearchHistoryHandler;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.entity.SearchHistory;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.PositionItemView;
import zjdf.zhaogongzuo.utils.DateTimeUtils;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * <h2>职位搜索模块<h2>
 * 
 * <pre> </pre>
 * 
 * @author 东方网升Eilin.Yang
 * @since 2014-3-15
 * @version
 * @modify ""
 */
public class SearchFragment extends Fragment implements OnClickListener {

	private Context context;
	/** 关键字 */
	private TextView txt_search_keyword;
	/** 选择工作地点 */
	private TextView txt_search_address;
	/** 选择职位类别 */
	private TextView txt_search_position_class;
	/** 搜索 */
	private Button btn_search;
	/** 历史记录 */
	private ImageButton ibtn_search_history;
	/** 历史记录模块 */
	private LinearLayout linear_history;
	/** 历史记录容器 */
	private LinearLayout linear_history_container;
	/** 清除历史记录 */
	private TextView txt_clear_history;
	/** 推荐的职位列表 */
	private LinearLayout linear_positions_container;
	/** 加载页面 */
	private LinearLayout linear_loading;
	/** 推荐的职位列表 */
	private List<Position> positions;
	/** 推荐职位控制器 */
	private PositionController controller;
	/** 搜索记录 */
	private List<SearchHistory> keyValues;
	/** 搜索记录，数据库操作 */
	private SearchHistoryHandler historyHandler;

	/** 关键字编码 */
	public static final int REQUESTCODE_KEYWORD = 0x0000100;
	/** 选择地址编码 */
	public static final int REQUESTCODE_ADDRESS = 0x0000101;
	/** 选择职位分类编码 */
	public static final int REQUESTCODE_POSITIONCLASS = 0x0000102;
	protected static final int REQUEST_OK = 101;
	protected static final int REQUEST_FAIL = 100;

	/** 关键字 */
	public static String keyword;
	/** 关键字范围 */
	public static int scope = 1;
	/** 地址编码 */
	public static String areaCode;
	/** 地址 */
	public static String areaValue;
	/** 职位分类编码 */
	public static String positionsClassCode;
	/** 职位分类 */
	public static String positionsClassValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		controller = new PositionController(context);
		historyHandler = new SearchHistoryHandler(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View search = inflater.inflate(R.layout.layout_fragment_search,
				container, false);
		initView(search);
		return search;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		loadDatas();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if (historyHandler != null) {
			historyHandler.close();
		}
	}

	/**
	 * <pre></pre>
	 * 
	 */
	private void initView(View view) {
		// TODO Auto-generated method stub
		txt_search_keyword = (TextView) view
				.findViewById(R.id.txt_search_keyword);
		txt_search_address = (TextView) view
				.findViewById(R.id.txt_search_address);
		txt_search_position_class = (TextView) view
				.findViewById(R.id.txt_search_position_class);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		ibtn_search_history = (ImageButton) view
				.findViewById(R.id.ibtn_search_history);
		linear_history = (LinearLayout) view.findViewById(R.id.linear_history);
		linear_history_container = (LinearLayout) view
				.findViewById(R.id.linear_history_container);
		linear_positions_container = (LinearLayout) view
				.findViewById(R.id.linear_positions_container);
		linear_loading = (LinearLayout) view.findViewById(R.id.linear_loading);
		txt_clear_history = (TextView) view
				.findViewById(R.id.txt_clear_history);
		txt_search_keyword.setOnClickListener(this);
		txt_search_address.setOnClickListener(this);
		txt_search_position_class.setOnClickListener(this);
		txt_clear_history.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		ibtn_search_history.setOnClickListener(this);
		/** 关键字 */
		keyword = "";
		/** 关键字范围 */
		scope = 1;
		/** 地址编码 */
		areaCode = "";
		/** 地址 */
		areaValue = "";
		/** 职位分类编码 */
		positionsClassCode = "";
		/** 职位分类 */
		positionsClassValue = "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_search_keyword:
			Intent keywordIntent = new Intent(context, KeywordActivity.class);
			keywordIntent.putExtra("scope", scope);
			startActivityForResult(keywordIntent, REQUESTCODE_KEYWORD);
			((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			break;

		case R.id.txt_search_address:
			Intent addressIntent = new Intent(context, AddressActivity.class);
			startActivityForResult(addressIntent, REQUESTCODE_ADDRESS);
			((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			break;

		case R.id.txt_search_position_class:
			Intent classIntent = new Intent(context,
					PositionClassActivity.class);
			startActivityForResult(classIntent, REQUESTCODE_POSITIONCLASS);
			((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			break;

		case R.id.btn_search:
			MobclickAgent.onEvent(context, "position_search");
			if (NetWorkUtils.checkNetWork(context)) {
				if (StringUtils.isEmpty(keyword)
						&& StringUtils.isEmpty(areaCode)
						&& StringUtils.isEmpty(positionsClassCode)) {
					CustomMessage.showToast(context, "关键字，地址，职位分类不能同时为空!", 0);
					return;
				} else {
					if (historyHandler.isValueExist(keyword, scope, areaCode,
							positionsClassCode)) {
						historyHandler.updateHistory(keyword, scope, areaCode,
								positionsClassCode,
								DateTimeUtils.getLongDateTime(true));
					} else
						historyHandler.insertHistory("", keyword, scope,
								areaValue, areaCode, positionsClassValue,
								positionsClassCode,
								DateTimeUtils.getLongDateTime(true));
					startPositionListActivity();
				}
			} else {
				CustomMessage.showToast(context, "请检查网络连接!", 0);
				return;
			}
			break;
		case R.id.ibtn_search_history:
			MobclickAgent.onEvent(context, "position_search_history");
			int sta = linear_history.getVisibility();
			if (sta == View.GONE) {
				keyValues = historyHandler.getKeywords(10);
				if (keyValues == null || keyValues.size() == 0) {
					CustomMessage.showToast(context, "你还没有可用的搜索记录哦!",
							Gravity.CENTER, 0);
					return;
				}
				initHistoryView();
				linear_history.setVisibility(View.VISIBLE);
			}
			if (sta == View.VISIBLE) {
				linear_history.setVisibility(View.GONE);
			}

			break;
		case R.id.txt_clear_history:
			int f = historyHandler.deleteAllHistory();
			if (f > 0) {
				if (linear_history_container != null) {
					linear_history_container.removeAllViews();
					linear_history.setVisibility(View.GONE);
				}
			}
			break;
		}
	}

	/**
	 * 开始查询
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void startPositionListActivity() {
		Intent intent = new Intent(context, PositionListActivity.class);
		startActivity(intent);
		((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		case REQUESTCODE_KEYWORD:// 关键字
			keyword = "";
			scope = 1;
			if (data != null) {
				String result = data.getStringExtra(KeywordActivity.KEYWORD);
				keyword = (result == null ? "" : result);
				scope = data.getIntExtra(KeywordActivity.SCOPE, 1);
			}
			txt_search_keyword.setText(keyword);
			break;

		case REQUESTCODE_ADDRESS:// 地址
			areaCode = "";
			areaValue = "";
			if (data != null) {
				String area_code = data
						.getStringExtra(AddressActivity.AREA_CODE);
				String area_value = data
						.getStringExtra(AddressActivity.AREA_VALUE);
				areaCode = area_code == null ? "" : area_code;
				areaValue = area_value == null ? "" : area_value;
			} else {
				if (!StringUtils.isEmpty(AddressActivity.code)
						&& !StringUtils.isEmpty(AddressActivity.value)) {
					areaCode = AddressActivity.code;
					areaValue = AddressActivity.value;
				}
			}
			txt_search_address.setText(areaValue);
			break;
		case REQUESTCODE_POSITIONCLASS:// 职位分类
			positionsClassCode = "";
			positionsClassValue = "";
			if (data != null) {
				String position_code = data
						.getStringExtra(PositionClassActivity.POSITION_CODE);
				String position_value = data
						.getStringExtra(PositionClassActivity.POSITION_VALUE);
				positionsClassCode = position_code == null ? "" : position_code;
				positionsClassValue = position_value == null ? ""
						: position_value;
			} else {
				if (!StringUtils.isEmpty(PositionClassActivity.code)
						&& !StringUtils.isEmpty(PositionClassActivity.value)) {
					positionsClassCode = PositionClassActivity.code;
					positionsClassValue = PositionClassActivity.value;
				}
			}
			txt_search_position_class.setText(positionsClassValue);
			break;
		}
	}

	/** 初始化数据 */
	public void loadDatas() {
		if (true) {
			linear_loading.setVisibility(View.VISIBLE);
			new Thread(new LoadDataRunnable()).start();
		}
	}

	private class LoadDataRunnable implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			positions = controller.getRecommendPositions();
			if (positions == null) {
				sendMessage(REQUEST_FAIL);
			} else {
				sendMessage(REQUEST_OK);
			}
		}

	}

	/**
	 * 发送消息
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 * 
	 * @param what
	 *            消息标识
	 */
	private void sendMessage(int what) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case REQUEST_OK:
				initPositionViews();
				break;

			case REQUEST_FAIL:
				break;
			}
			linear_loading.setVisibility(View.GONE);
		}

	};

	/**
	 * 初始化历史记录
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void initHistoryView() {
		if (keyValues != null && keyValues.size() > 0) {
			if (linear_history_container != null) {
				linear_history_container.removeAllViews();
			}
			int hn = keyValues.size();
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.topMargin = 8;
			SearchHistory history = null;
			for (int i = 0; i < hn; i++) {
				history = keyValues.get(i);
				linear_history_container.addView(createTextView(history),
						params);
			}
		}
	}

	private TextView createTextView(final SearchHistory history) {
		if (history == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		if (!StringUtils.isEmpty(history.keyword)) {
			buffer.append(history.keyword);
		}
		if (!StringUtils.isEmpty(history.address)) {
			if (StringUtils.isEmpty(buffer)) {
				buffer.append(history.address);
			} else
				buffer.append("+" + history.address);
		}
		if (!StringUtils.isEmpty(history.positionclass)) {
			if (StringUtils.isEmpty(buffer)) {
				buffer.append(history.positionclass);
			} else
				buffer.append("+" + history.positionclass);
		}
		if (StringUtils.isEmpty(buffer)) {
			return null;
		}
		TextView txt = new TextView(context);
		txt.setText(buffer.toString());
		txt.setTextSize(14);
		txt.setTextColor(Color.BLACK);
		txt.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		txt.setPadding(5, 5, 5, 5);
		txt.setBackgroundResource(R.drawable.selector_item_bg);
		txt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				keyword = history.keyword;
				scope = history.scope;
				areaCode = history.address_code;
				areaValue = history.address;
				positionsClassCode = history.positionclass_code;
				positionsClassValue = history.positionclass;
				historyHandler.updateHistory(history,
						DateTimeUtils.getLongDateTime(true));
				startPositionListActivity();
			}
		});
		return txt;
	}

	/**
	 * 初始化职位控件
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	private void initPositionViews() {
		if (positions == null) {
			return;
		}
		if (linear_positions_container != null) {
			linear_positions_container.removeAllViews();
		}
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 1);
		params.leftMargin = 8;
		int nn = positions.size();
		nn = (nn > 5 ? 5 : nn);
		PositionItemView itemView = null;
		for (int i = 0; i < nn; i++) {
			itemView = new PositionItemView(context);
			itemView.setDatas(positions.get(i));
			linear_positions_container.addView(itemView);
			View line = LayoutInflater.from(context).inflate(
					R.layout.layout_line, null);
			linear_positions_container.addView(line, params);
		}
	}

	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 MobclickAgent.onPageStart("SearchFragment"); 
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("SearchFragment");
	}
}
