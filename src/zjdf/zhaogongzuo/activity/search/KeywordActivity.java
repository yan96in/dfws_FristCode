/**
 * Copyright © 2014-3-18 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.search;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.adapter.KeywordHistoryAdapter;
import zjdf.zhaogongzuo.controllers.KeywordsController;
import zjdf.zhaogongzuo.databases.handlers.SearchKeywordsHandler;
import zjdf.zhaogongzuo.entity.Keywords;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.utils.DateTimeUtils;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;

/**<h2> 输入关键字<h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2014-3-18
 * @version 
 * @modify ""
 */
public class KeywordActivity extends BaseActivity {

	private Context context;
	/**关键字输入框*/
	private EditText edt_keyword;
	/**提交按钮*/
	private Button btn_keyword_submit;
	/**关键字搜索选项集合*/
	private RadioGroup rgroup_keyword;
	/**全文搜索*/
	private RadioButton rbtn_keyword_full;
	/**职位名*/
	private RadioButton rbtn_keyword_position_name;
	/**企业名*/
	private RadioButton rbtn_keyword_enterprise_name;
	/**搜索记录列表*/
	private ListView lv_keyword_history;
	/**清除历史记录*/
	private LinearLayout linear_clear_history;
	/**历史记录适配器*/
	private KeywordHistoryAdapter adapter;
	/**关键字集合*/
	private List<Keywords> keywords;
	/**搜索范围 默认为1。1为全文匹配，2为职位明匹配，3为企业名匹配。*/
	private int scope=1;
	/**关键字*/
	private String kw="";
	/**历史记录*/
	private List<Keywords> historys;
	/**推荐记录*/
	private SearchKeywordsHandler historyHandler;
	/**关键字搜索范围*/
	public static final String SCOPE="scope";
	/**关键字*/
	public static final String KEYWORD="keyword";
	/**关键字控制器*/
	private KeywordsController controller;
	/**获取关键字成功*/
	private static final int GET_KEYWORDS_SUCCESS=11;
	/**获取关键字失败*/
	private static final int GET_KEYWORDS_FAIL=10;
	
	/**是否选中item项*/
	private boolean isitem=false;
	
	/**关键字*/
	private String key="";
	/**操作*/
	private FunctionEnum mFunction;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_keyword);
		context=this;
		mFunction=FunctionEnum.CANCEL;
		scope=getIntent().getIntExtra("scope", 1);
		initView();
		historyHandler=new SearchKeywordsHandler(context);
		initDatas();
		controller=new KeywordsController(context);
	}

	/**
	 * 
	 *<pre>方法  </pre>
	 */
	private void initView(){
		edt_keyword=(EditText)findViewById(R.id.edt_keyword);
		btn_keyword_submit=(Button)findViewById(R.id.btn_keyword_submit);
		rgroup_keyword=(RadioGroup)findViewById(R.id.rgroup_keyword);
		rbtn_keyword_full=(RadioButton)findViewById(R.id.rbtn_keyword_full);
		rbtn_keyword_position_name=(RadioButton)findViewById(R.id.rbtn_keyword_position_name);
		rbtn_keyword_enterprise_name=(RadioButton)findViewById(R.id.rbtn_keyword_enterprise_name);
		lv_keyword_history=(ListView)findViewById(R.id.lv_keyword_history);
		linear_clear_history=(LinearLayout)findViewById(R.id.linear_clear_history);
		linear_clear_history.setVisibility(View.GONE);
		initRadioStates();
		rgroup_keyword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				initRadioButtonStatus();
				switch (checkedId) {
				case R.id.rbtn_keyword_full:
					rbtn_keyword_full.setTextColor(Color.WHITE);
					scope=1;
					break;
				case R.id.rbtn_keyword_position_name:
					rbtn_keyword_position_name.setTextColor(Color.WHITE);
					scope=2;
					break;
				case R.id.rbtn_keyword_enterprise_name:
					rbtn_keyword_enterprise_name.setTextColor(Color.WHITE);
					scope=3;
					break;
				
				}
				String txt=edt_keyword.getText().toString().trim();
				int nn=(txt==null? 0 : txt.length());
				if (adapter!=null&&nn>0) {
					adapter.clear();
					loadDatas(nn);
				}
			}
		});
		edt_keyword.addTextChangedListener(watcher);
		linear_clear_history.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				historyHandler.deleteAllKeywords();
				adapter.clear();
				linear_clear_history.setVisibility(View.GONE);
			}
		});
		btn_keyword_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				kw=edt_keyword.getText().toString().trim();
				if (mFunction==FunctionEnum.FINISH) {
					boolean flag=historyHandler.isKeywordExist(kw);
					String datetime=DateTimeUtils.getLongDateTime(true);
					if (flag) {
						historyHandler.updateKeywordCount(kw, datetime);
					}else {
						historyHandler.insertKeyword("", "", kw, datetime);
					}
					startIntent();
					finish();
				}else {
					finish();
				}
			}
		});
		lv_keyword_history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String txt=adapter.getContext(position);
				if (txt!=null) {
					isitem=true;
					edt_keyword.removeTextChangedListener(watcher);
					edt_keyword.setText(txt);
					int n=txt.length();
					edt_keyword.setSelection(n);
					switchFunction();
				}
			}
		});
		edt_keyword.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edt_keyword.addTextChangedListener(watcher);
				return false;
			}
		});
		isitem=false;
	}
	
	/**
	 * 初始化选中状态
	 */
	private void initRadioStates(){
		initRadioButtonStatus();
		switch (scope) {
		case 1:
			rbtn_keyword_full.setTextColor(Color.WHITE);
			rbtn_keyword_full.setChecked(true);
			break;
		case 2:
			rbtn_keyword_position_name.setTextColor(Color.WHITE);
			rbtn_keyword_position_name.setChecked(true);
			break;
		case 3:
			rbtn_keyword_enterprise_name.setTextColor(Color.WHITE);
			rbtn_keyword_enterprise_name.setChecked(true);
			break;
		
		}
	}
	
	/**
	 *<pre>方法  </pre>
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
		
		historys=historyHandler.getKeywords(10);
		if (historys!=null&&historys.size()>0) {
			lv_keyword_history.setVisibility(View.VISIBLE);
			linear_clear_history.setVisibility(View.VISIBLE);
		}else {
			linear_clear_history.setVisibility(View.GONE);
		}
		adapter=new KeywordHistoryAdapter(context, historys,null,false);
		lv_keyword_history.setAdapter(adapter);
	}
	
	/***
	 * 初始化item状态
	 *<pre>方法  </pre>
	 */
	private void initRadioButtonStatus(){
		rbtn_keyword_full.setTextColor(Color.BLACK);
		rbtn_keyword_position_name.setTextColor(Color.BLACK);
		rbtn_keyword_enterprise_name.setTextColor(Color.BLACK);
	}			
	
	/**
	 * 加载数据
	 *<pre>方法  </pre>
	 * @param count
	 */
	private void loadDatas(int count){
		if (count>0&&NetWorkUtils.checkNetWork(context)) {
			linear_clear_history.setVisibility(View.GONE);
			String k=edt_keyword.getText().toString().trim();
			executorService.submit(new LoadingKeywordsRunnable(k));
		}
	}
	
	private TextWatcher watcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			loadDatas(count);			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
//			isitem=false;

		}
		
		@Override
		public void afterTextChanged(Editable s) {
	
			switchFunction();
		}
	};
	
	/**
	 * 改变功能
	 */
	private void switchFunction(){
		if (edt_keyword.getText().toString().length()>0) {
			mFunction=FunctionEnum.FINISH;
			btn_keyword_submit.setText("完成");
		}else {
			mFunction=FunctionEnum.CANCEL;
			btn_keyword_submit.setText("取消");
		}
	}
	
	private class LoadingKeywordsRunnable implements Runnable{

		private String kwss;
		public LoadingKeywordsRunnable(String kws){
			kwss=kws;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!StringUtils.isEmpty(kwss)) {				
				keywords=controller.getKeywords(null, kwss, scope);
				if (keywords==null) {
					sendMessage(GET_KEYWORDS_FAIL);
				}else {
					sendMessage(GET_KEYWORDS_SUCCESS);
				}
			}
		}
		
	}
	
	
	private Handler handler=new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_KEYWORDS_SUCCESS:
				if (keywords!=null&&keywords.size()>0) {
					lv_keyword_history.setVisibility(View.VISIBLE);
					key=edt_keyword.getText().toString().trim();
					if (adapter!=null) {
						adapter.setKeyword(key);
						adapter.setDatas(keywords,true);
					}else {
						adapter=new KeywordHistoryAdapter(context, keywords,key,true);
						lv_keyword_history.setAdapter(adapter);
					}
				}
				break;

			case GET_KEYWORDS_FAIL:
				
				break;
			}
		}
		
	};
	
	
	/**
	 * 发送消息
	 *<pre>方法  </pre>
	 * @param what 消息标志
	 */
	private void sendMessage(int what){
		Message msg=handler.obtainMessage();
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	/**
	 * 完成天传
	 *<pre>方法  </pre>
	 * @param keyword 关键字
	 */
	private void startIntent(){
		Intent intent = new Intent();
		intent.putExtra(KEYWORD, kw);
		intent.putExtra(SCOPE, scope);
		this.setResult(SearchFragment.REQUESTCODE_KEYWORD, intent);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (historyHandler!=null) {
			historyHandler.close();
		}
	}
	
	private enum FunctionEnum{
		/**取消*/
		CANCEL,
		/**完成*/
		FINISH
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}
}
