/**
 * Copyright © 2014年4月1日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.resume;

import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.adapter.EducationsAdapter;
import zjdf.zhaogongzuo.controllers.ResumeInformationControllers;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.ResumeEducation;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;

/**
 * 教育经历
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年4月1日
 * @version
 * @modify
 * 
 */
public class EducationsActivity extends BaseActivity {

	private ResumeInformationControllers resumeInformationControllers;
	private Context context;// 上下文
	private List<ResumeEducation> listeducation;// 返回对象
	private ListView list_information;// list
	private List<ResumeEducation> positions;// 数据
	private EducationsAdapter adapter;// 适配器

	private ImageButton ibtn_back;// 返回
	private Button btn_add_delete;// 显示添加按钮
	private Button btn_manage;
	private boolean isManage;
	private String add_certificate = "";
	private String delete_certificate = "";
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
		setContentView(R.layout.layout_myresume_educations);
		context = this;
		resumeInformationControllers = new ResumeInformationControllers(context);
		initView();

	}

	// 初始化
	private void initView() {
		add_certificate = getString(R.string.add_edu);
		delete_certificate = getString(R.string.delete_edu);
		// TODO Auto-generated method stub
		list_information = (ListView) findViewById(R.id.list_information);
		ibtn_back = (ImageButton) findViewById(R.id.ibtn_back);
		btn_manage = (Button) findViewById(R.id.txt_posit);
		btn_add_delete = (Button) findViewById(R.id.view_button);
		btn_add_delete.setText(add_certificate);
		ibtn_back.setOnClickListener(listener);
		btn_manage.setOnClickListener(listener);
		btn_add_delete.setOnClickListener(listener);

		adapter = new EducationsAdapter(context, positions, list_information);
		list_information.setAdapter(adapter);

	}

	// 改变选中状态
	private void switchBtnLayout() {
		if (isManage) {
			btn_manage.setText(R.string.cancel);
			adapter.isManage(true);
			btn_add_delete.setText(delete_certificate);
		} else {
			btn_manage.setText(R.string.mycenter_apply_manage);
			adapter.isManage(false);
			btn_add_delete.setText(add_certificate);
		}
	}

	// 刷新界面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (positions == null || positions.size() == 0) {
					Toast.makeText(context, "你还没有添加过教育经历", Toast.LENGTH_LONG)
							.show();
					isManage = false;
					switchBtnLayout();
					adapter.clearItems();
					adapter.notifyDataSetChanged();
				} else {
					adapter.clearItems();
					adapter.addItems(positions);
				}

				break;

			case 21:
				CustomMessage.showToast(context, "删除成功!", Gravity.CENTER, 0);
				adapter.clearStates();
				getData();
				break;
			case 20:
				CustomMessage.showToast(context,
						(deleteEorrerBean == null ? "删除失败!"
								: deleteEorrerBean.errMsg), Gravity.CENTER, 0);
				break;
			}
		};
	};

	// 获取数据
	private void getData() {

		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				positions = resumeInformationControllers.getEducations();
				handler.sendEmptyMessage(1);
			}
		});
	};

	// 监听器
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == btn_manage) {
				if (positions == null || positions.size() == 0) {
					return;
				}
				if (isManage) {
					isManage = false;
				} else {
					isManage = true;
				}
				switchBtnLayout();
			}
			if (v == ibtn_back) {
				finish();
			}
			if (v == btn_add_delete) {
				if (!isManage) {
					Intent intent_Disclosure = new Intent(context,
							AddEducatitonsActivity.class);
					startActivityForResult(intent_Disclosure, 1);
					overridePendingTransition(anim.slide_in_right,
							anim.slide_out_left);
				} else {
					doDelete();
				}
			}

		}
	};

	// 处理按钮点击事件
	private void doDelete() {

		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		final String idss = adapter.getSelectedJobIdsString();
		if (StringUtils.isEmpty(idss)) {
			CustomMessage.showToast(context, "请选择一项删除！", Gravity.CENTER, 0);
			return;
		}
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				deleteEorrerBean = resumeInformationControllers.deleteJobEducation(idss);
				handler.sendEmptyMessage(2);
				if (deleteEorrerBean != null && deleteEorrerBean.status == 1) {
					handler.sendEmptyMessage(21);
				} else {
					handler.sendEmptyMessage(20);
				}
			}
		});

	};

	/*
	 * (non-Javadoc)
	 * 
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
		getData();
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
