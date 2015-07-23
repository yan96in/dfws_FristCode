/**
 * Copyright © 2014年3月27日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.mycenter;

import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.adapter.JobFairAdapter;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.entity.JobFair;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;

/**
 * <h2>JobFairActivity</h2> 招聘会 列表
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月27日
 * @version
 * @modify
 * 
 */
public class JobFairActivity extends BaseActivity {

	private Context context;
	/** 招聘会列表 */
	private List<JobFair> jobFairs;
	/** 适配器 */
	private JobFairAdapter adapter;
	private ListView listView;
	private MyResumeConttroller conttroller;// 控制器
	private ImageButton but_return;//返回
	private Dialog dialog;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycenter_jobfair);
		context = this;
		conttroller = new MyResumeConttroller(context);
		initView();
		initDatas();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.lv_jobfair);
		but_return=(ImageButton) findViewById(R.id.but_return);
		but_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		dialog=CustomMessage.createProgressDialog(context, null, false);
		adapter = new JobFairAdapter(context, jobFairs, listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int index = (int) id;
				int meet_id = adapter.getItemObjectId(index);
				Intent intent = new Intent(context,JobFiarParticulars.class);
				intent.putExtra("meet_id", meet_id);
				startActivity(intent);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			}

		});
	}

	// 初始化招聘会列表
	private void initDatas() {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "连接网络失败，请检查网络！", Gravity.CENTER, 0);
			return;
		}
		if (dialog!=null&&!dialog.isShowing()) {
			dialog.show();
		}
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				jobFairs = conttroller.getJobFair();
				if (jobFairs!=null) {
					handler.sendEmptyMessage(1);
				}else {
					handler.sendEmptyMessage(0);
				}
			}
		});
	}

	// 刷新界面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				adapter.addDatas(jobFairs);
				break;
			case 0:
				CustomMessage.showToast(context, "加载失败，或者没有相关的招聘会！", Gravity.CENTER, 0);
				break;
			}
			if (dialog!=null&&dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	};
	
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
