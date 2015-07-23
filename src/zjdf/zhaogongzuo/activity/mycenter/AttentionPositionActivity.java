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

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.adapter.FocusAdapter;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
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

import com.umeng.analytics.MobclickAgent;

/**
 * <h2>AttentionPositionActivity</h2>
 * 
 * <pre> </pre>
 * 
 * 关注的企业
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月27日
 * @version
 * @modify
 * 
 */
public class AttentionPositionActivity extends BaseActivity {
	
	private ImageButton ibtn_return; // 返回
	private MyResumeConttroller myResumeConttroller; // 控制器
	private Context context; // 上下文
	private ListView listview; // list显示
	private FocusAdapter adapter; // 适配器
	private List<Company> companies; // 接收数据
	private Dialog dialog;
	private Button btn_manage;
	private boolean isManage;
	private Button btn_delete;
	private LinearLayout not_txt; // 当数据为空的时候
	private EorrerBean deleteEorrerBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycenter_attention_enterprise);
		context = this;
		myResumeConttroller = new MyResumeConttroller(context);
		isManage=false;
		initView();
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
		getData(true);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}

	

	// 初始化数据
	private void initView() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.listview_focus);
		not_txt=(LinearLayout) findViewById(R.id.not_txt);
		ibtn_return = (ImageButton) findViewById(R.id.image_but_return);
		btn_manage = (Button) findViewById(R.id.btn_manage);
		btn_delete=(Button)findViewById(R.id.btn_delete);
		btn_delete.setVisibility(View.GONE);
		btn_manage.setOnClickListener(listener);
		ibtn_return.setOnClickListener(listener);
		btn_delete.setOnClickListener(listener);
		adapter = new FocusAdapter(context, companies);
		listview.setAdapter(adapter);
		dialog = CustomMessage.createProgressDialog(context, null, false);
	}
	
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


	// 加载 list 数据
	private void getData(boolean showDialog) {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "请检查网络！", Gravity.CENTER, 0);
			return;
		}
		if (showDialog&&dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				companies = myResumeConttroller.followed_companies();
				handler.sendEmptyMessage(1);
			}
		});
	}

	// 监听器
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==btn_delete) {
				doDelete();
			}
			if (v==ibtn_return) {
				finish();
			}
			if (v==btn_manage) {
				if (companies==null||companies.size()==0) {
					return;
				}
				if (isManage) {
					isManage=false;
				}else {
					isManage=true;
				}
				switchBtnLayout();
			}
		}
	};
	
	// 刷新UI 显示服务器返回的数据
		private Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (companies == null || companies.size() == 0) {
						not_txt.setVisibility(View.VISIBLE);
						listview.setVisibility(View.GONE);
						isManage=false;
						switchBtnLayout();
					} else {
						adapter.clearItems();
						adapter.addItems(companies);
					}
					break;
					
				case 21:
					CustomMessage.showToast(context, "删除成功!", Gravity.CENTER, 0);
					adapter.clearStates();
					getData(false);
					break;
				case 20:
					CustomMessage.showToast(context, (deleteEorrerBean==null?"删除失败!":deleteEorrerBean.errMsg), Gravity.CENTER, 0);
					break;

				}
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		};

	// 处理按钮点击事件
	private void doDelete() {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "请检查网络！", Gravity.CENTER, 0);
			return;
		}
		final String idss=adapter.getSelectedJobIdsString();
		if (StringUtils.isEmpty(idss)) {
			CustomMessage.showToast(context, "请至少选择一项!", Gravity.CENTER, 0);
			return;
		}
			executorService.submit(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					deleteEorrerBean = myResumeConttroller.unfollow_company(idss);
					if (deleteEorrerBean!=null&&deleteEorrerBean.status==1) {
						handler.sendEmptyMessage(21);
					}else {
						handler.sendEmptyMessage(20);
					}
				}
			});
	}
}
