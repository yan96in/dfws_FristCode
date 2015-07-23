/**
 * Copyright © 2014年4月17日 FindJob www.veryeast.com
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

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.adapter.PositionSingleListAdapter;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 *<h2> 搜索模块职位列表Activity</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月17日
 * @version 
 * @modify 
 * 
 */
public class PositionSingleListActivity extends BaseActivity {

	public String TAG="PositionSingleListActivity";
	private Context context;
	/**数据集*/
	private List<Position> positions;
	/**适配器*/
	public PositionSingleListAdapter adapter;
	/**返回*/
	private ImageButton goback;
	private LinearLayout linear_notice;
	/**标题*/
	private TextView txt_search_title;
	/**职位列表*/
	private ListView lv_position_list;
	/**申请职位*/
	private Button btn_apply;
	/**简历控制器*/
	private MyResumeConttroller resumeConttroller;
	/**职位控制器*/
	private PositionController pConttroller;
	/**申请职位成功*/
	private static final int REQUEST_JOBS_OK=211;
	/**申请职位失败*/
	private static final int REQUEST_JOBS_FAIL=210;
	
	/**加载对话框*/
	private Dialog loaDialog;
	
	/**标题*/
	private String title="";
	private ApplicationConfig application;
	private String companyid;
	private String companyname;
	/**职位申请状态信息*/
	private EorrerBean applyEorrerBean;
	

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_position_single_list);
		context=this;
		MobclickAgent.onEvent(context, "position_search_list");
		application=(ApplicationConfig)getApplicationContext();
		resumeConttroller=new MyResumeConttroller(context);
		pConttroller=new PositionController(context);
		companyid=getIntent().getStringExtra("cid");
		companyname=getIntent().getStringExtra("cname");
		initView();
		initDialog();
		initDatas();
		executorService.submit(new LoadDatasRunnable());
		loaDialog.show();
		
	}
	
	/**
	 * 初始化控件
	 *<pre>方法  </pre>
	 */
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		txt_search_title=(TextView)findViewById(R.id.txt_search_title);
		lv_position_list=(ListView)findViewById(R.id.lv_position_list);
		linear_notice=(LinearLayout)findViewById(R.id.linear_notice);
		linear_notice.setVisibility(View.GONE);
		btn_apply=(Button)findViewById(R.id.btn_apply);
		txt_search_title.setText("企业相关职位");
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_apply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MobclickAgent.onEvent(context, "position_search_list_apply");
				if (positions==null||positions.size()==0) {
					return;
				}
				if (StringUtils.isEmpty(application.user_ticket)) {
					Intent intent=new Intent(context, LoginActivity.class);
					context.startActivity(intent);
					((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
					return;
				}
				String ids=adapter.getSelectedJobIdsString();
				if (StringUtils.isEmpty(ids)) {
					CustomMessage.showToast(context, "请先选择至少1个职位!", 0);
					return;
				}
				executorService.submit(new ApplyJobsRunnable());
			}
		});
	}
	
	/**
	 * 初始化对话框
	 *<pre>方法  </pre>
	 */
	private void initDialog(){
		loaDialog=CustomMessage.createProgressDialog(context, "正在加载数据...", false);
	}
	
	private void initDatas(){
		adapter=new PositionSingleListAdapter(context, null);
		lv_position_list.setAdapter(adapter);
	}
	
	/**
	 * 
	 *<h2> LoadDatasRunnable 加载数据</h2>
	 *<pre>  </pre>
	 * @author Eilin.Yang VeryEast
	 * @since 2014年4月18日
	 * @version 
	 * @modify 
	 *
	 */
	private class LoadDatasRunnable implements Runnable{

		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			positions=resumeConttroller.company_recruit_jobs(companyid);
			if (positions!=null) {
				sendMessage(REQUEST_OK);
			}else {
				sendMessage(REQUEST_FAIL);
			}
		}
	}
	
	
	private class ApplyJobsRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String ids=adapter.getSelectedJobIdsString();
			applyEorrerBean=pConttroller.applyJobs(ids);
			if (applyEorrerBean!=null&&applyEorrerBean.status==1) {
				sendMessage(REQUEST_JOBS_OK);
			}else {
				sendMessage(REQUEST_JOBS_FAIL);
			}
		}
		
	}

	/**
	 * 发送消息
	 *<pre>方法  </pre>
	 * @param what 消息标识
	 */
	private void sendMessage(int what){
		Message msg=handler.obtainMessage();
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	private Handler handler=new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what) {
			case REQUEST_OK:
				linear_notice.setVisibility(View.GONE);
				adapter.clear();
				adapter.addDatas(positions);
				
				break;

			case REQUEST_FAIL:
				linear_notice.setVisibility(View.VISIBLE);
				break;
				
			case REQUEST_JOBS_OK:
					CustomMessage.showToast(context, "职位申请成功!", 0);
					break;
			case REQUEST_JOBS_FAIL:
				CustomMessage.showToast(context, (applyEorrerBean==null?"职位申请失败!":applyEorrerBean.errMsg), 0);
					break;
			}
			if (loaDialog.isShowing()) {
				loaDialog.dismiss();
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
