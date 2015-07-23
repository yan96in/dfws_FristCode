/**
 * Copyright © 2014年3月25日 FindJob www.veryeast.com
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
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.adapter.PositionApplyLogAdapter;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 职位申请记录
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月25日
 * @version
 * @modify
 * 
 */
public class PositionApplyLogActivity extends BaseActivity {
	private Context context;// 上下文
	private List<Position> positions;// 数据集合
	private ListView listView;// listview
	private PositionApplyLogAdapter adapter;// 适配器
	private MyResumeConttroller myResumeConttroller;// 控制器
	private ImageButton ibtn_return;// 返回
	private Dialog dialog;
	private LinearLayout not_txt;// 没数据就显示
	private Button btn_manage;
	private boolean isManage;
	private Button btn_delete;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycenter_position_apply_log);
		context = this;
		myResumeConttroller = new MyResumeConttroller(context);
		isManage=false;
		initView();
		// 加载数据
		getData();
	}

	// 初始化数据
	private void initView() {
		not_txt = (LinearLayout) findViewById(R.id.not_txt);
		ibtn_return = (ImageButton) findViewById(R.id.posi_return);
		btn_manage=(Button)findViewById(R.id.btn_manage);
		btn_delete=(Button)findViewById(R.id.btn_delete);
		listView = (ListView) findViewById(R.id.listview_log);
		btn_manage.setOnClickListener(listener);
		ibtn_return.setOnClickListener(listener);
		btn_delete.setVisibility(View.GONE);
		adapter = new PositionApplyLogAdapter(context, positions);
		listView.setAdapter(adapter);
		dialog = CustomMessage.createProgressDialog(context, null, false);
	}

	// 加载listview 数据
	private void getData() {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 0);
			return;
		}
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				positions = myResumeConttroller.applied_jobs();
				handler.sendEmptyMessage(1);
			}
		});
	}

	// 刷新 UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (positions == null || positions.size() == 0) {
					not_txt.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				} else {
					adapter.addDatas(positions);
				}

				break;
			case 2:

				break;
			}
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		};
	};
	
	private void switchBtnLayout(){
		if (isManage) {
			btn_manage.setText(R.string.cancel);
			adapter.isManage(true);
			btn_delete.setVisibility(View.VISIBLE);
		}else {
			btn_manage.setText(R.string.mycenter_apply_manage);
			adapter.isManage(false);
			btn_delete.setVisibility(View.GONE);
		}
	}
	
	// 监听器
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			// 返回
			case R.id.posi_return:
				finish();
				break;
			case R.id.btn_manage:
				if (positions==null||positions.size()==0) {
					return;
				}
				if (isManage) {
					isManage=false;
				}else {
					isManage=true;
				}
				switchBtnLayout();
				break;
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
