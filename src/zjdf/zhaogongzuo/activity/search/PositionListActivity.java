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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zjdf.zhaogongzuo.R;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.adapter.PositionAdapter;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 *<h2> 搜索模块职位列表Activity</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月17日
 * @version 
 * @modify 
 * 
 */
public class PositionListActivity extends BaseActivity {

	public static final String TAG="PositionListActivity";
	private Context context;
	/**数据集*/
	private List<Position> positions;
	/**适配器*/
	public static PositionAdapter adapter;
	/**返回*/
	private ImageButton goback;
	/**标题*/
	private TextView txt_search_title;
	/**提交*/
	private Button btn_fiter;
	/**可以下拉的ListView*/
	private PullToRefreshListView pullListView;
	/**职位列表*/
	private ListView lv_position_list;
	/**申请职位*/
	private Button btn_apply;
	/**职位控制器*/
	private static PositionController controller;	
	/**页码*/
	private static int pageIndex=1;
	/**每页大小*/
	private static int pageSize=10;
	/**更新时间*/
	public static int updateTime=-1;
	/**工作经验*/
	public static int works=0;
	/**教育经历*/
	public static int educations=0;
	/**薪资范围*/
	public static int salary=0;
	/**是否食宿*/
	public static int room_board=0;
	/**工作性质*/
	private static int work_mode=0;
	/**获取数据成功*/
	private static final int REQUEST_OK=200;
	/**获取数据失败*/
	private static final int REQUEST_FAIL=400;
	
	/**申请职位成功*/
	private static final int REQUEST_JOBS_OK=211;
	/**申请职位失败*/
	private static final int REQUEST_JOBS_FAIL=210;
	
	/**加载对话框*/
	private Dialog loaDialog;
	
	/**标题*/
	private String title="";
	private Mode mode;
	private ApplicationConfig application;
	/**职位申请状态信息*/
	private EorrerBean applyEorrerBean;
	private LinearLayout linear_notice;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_position_list);
		context=this;
		MobclickAgent.onEvent(context, "position_search_list");
		application=(ApplicationConfig)getApplicationContext();
		controller=new PositionController(context);
		initView();
		initDialog();
		initValue();
		initDatas();
		PositionFiterActivity.initValue();
		executorService.submit(new LoadDatasRunnable(1));
		loaDialog.show();
		
	}
	
	
	/**
	 * 初始化控件
	 *<pre>方法  </pre>
	 */
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		txt_search_title=(TextView)findViewById(R.id.txt_search_title);
		btn_fiter=(Button)findViewById(R.id.btn_fiter);
		pullListView=(PullToRefreshListView)findViewById(R.id.lv_position_list);
		linear_notice=(LinearLayout)findViewById(R.id.linear_notice);
//		lv_position_list=(ListView)findViewById(R.id.lv_position_list);
		linear_notice.setVisibility(View.GONE);
		btn_apply=(Button)findViewById(R.id.btn_apply);
		if (!StringUtils.isEmpty(SearchFragment.keyword)) {
			title=SearchFragment.keyword;
		}
		if (!StringUtils.isEmpty(SearchFragment.areaValue)) {
			if (StringUtils.isEmpty(title)) {
				title=SearchFragment.areaValue;
			}else
				title=title+"+"+SearchFragment.areaValue;
		}
		if (!StringUtils.isEmpty(SearchFragment.positionsClassCode)) {
			if (StringUtils.isEmpty(title)) {
				title=SearchFragment.positionsClassValue;
			}else
				title=title+"+"+SearchFragment.positionsClassValue;
		}
		title=StringUtils.isEmpty(title)?"职位列表":(title.length()>10?(title.substring(0, 8)+"..."):title);
		txt_search_title.setText(title);
		btn_fiter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MobclickAgent.onEvent(context, "position_search_position_list_fiter");
				Intent intent=new Intent(context, PositionFiterActivity.class);
				startActivityForResult(intent, 108);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			}
		});
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		if(StringUtils.getApiVersion()>8)
			pullListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		pullListView.setMode(Mode.BOTH);
		pullListView.getLoadingLayoutProxy(true, true).setTitleLayoutVisibility(View.GONE);
		pullListView.setOnRefreshListener(new com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!NetWorkUtils.checkNetWork(context)) {
					pullListView.onRefreshComplete();
					return;
				}
				pageSize=adapter.getCount();
				executorService.submit(new LoadDatasRunnable(1));
				mode=Mode.PULL_FROM_START;
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (controller.jobCounts==adapter.getCount()) {
					pullListView.onRefreshComplete();
					return;
				}
				if (NetWorkUtils.checkNetWork(context)&&controller.jobCounts>adapter.getCount()) {
					pageSize=10;
					executorService.submit(new LoadDatasRunnable(++pageIndex));
					mode=Mode.PULL_FROM_END;
				}else {
					pullListView.onRefreshComplete();
					return;
				}
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
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "请检查网络！", Gravity.CENTER, 0);
					return;
				}
				String ids=adapter.getSelectedJobIdsString();
				if (StringUtils.isEmpty(ids)) {
					CustomMessage.showToast(context, "请先选择至少1个职位!", 0);
					return;
				}
				executorService.submit(new ApplyJobsRunnable(ids));
			}
		});
	}
	
	private void initValue(){
		/**页码*/
		pageIndex=1;
		/**每页大小*/
		pageSize=10;
		/**更新时间*/
		updateTime=-1;
		/**工作经验*/
		works=0;
		/**教育经历*/
		educations=0;
		/**薪资范围*/
		salary=0;
		/**是否食宿*/
		room_board=0;
		/**工作性质*/
		work_mode=0;
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==108&&data!=null) {
			pageIndex=1;
			pageSize=10;
			updateTime=PositionFiterActivity.updateTime_value;
			works=PositionFiterActivity.works_value;
			educations=PositionFiterActivity.educations_value;
			salary=PositionFiterActivity.salary_value;
			room_board=PositionFiterActivity.room_board_value;
			work_mode=PositionFiterActivity.works_mode_value;
			adapter.clear();
			linear_notice.setVisibility(View.GONE);
			executorService.submit(new LoadDatasRunnable(pageIndex));
			if(!loaDialog.isShowing())
				loaDialog.show();
		}
	}
	
	/**
	 * 初始化对话框
	 *<pre>方法  </pre>
	 */
	private void initDialog(){
		loaDialog=CustomMessage.createProgressDialog(context, "正在加载数据...", false);
	}
	
	private void initDatas(){
		adapter=new PositionAdapter(context, null);
		lv_position_list=pullListView.getRefreshableView();
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

		/**索引*/
		private int index=1;
		public LoadDatasRunnable(int idx){
			index=idx;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			/**
			 * url 请求地址 ---必填
				keyword 关键字。为必须字段,可以为"" ---必填
				scope 关键字搜索范围。1为全文匹配，2为职位明匹配，3为企业名匹配。---必填
				page 页码，默认为1。
				size 每页大小 。默认为10。
				area_code 地区编码 .例：杭州 050100
				position_code 职位编码.例：前厅部员工1115
				updateTime 更新时间.-1:不限，0：今日最新，3：最近3天，5：最近5天，7：最近一周，14：最近两周，30：最近一个月，60：最近两个月，
				works 工作经验。 0:不限，1：一年以上，2：两年以上，3： 三年以上，4：五年以上，5：八年以上，6：十年以上
				educations 教育经验.0：不限，1：初中，2：高中：3：中技，4：中专，5：大专，6：本科，7：硕士，8：博士，9：博士后
				salary 薪资要求.0：不限，1:1001-2000,2:2001-3000,3:3001-5000,4：4500-6000,5:6001-8000,6:8001-10000
				room_board 是否提供食宿.0：不限，1：提供食宿，2：不提供，3：提供吃，4：提供住。
				work_mode 工作性质.0：不限，1：全职，2：兼职，3：实习，4：临时

			 */
			positions=controller.getPositions( SearchFragment.keyword, SearchFragment.scope, index, pageSize, SearchFragment.areaCode, SearchFragment.positionsClassCode, updateTime, works, educations, salary+"", room_board+"", work_mode+"");
			if (positions!=null) {
				sendMessage(REQUEST_OK);
			}else {
				sendMessage(REQUEST_FAIL);
			}
		}
	}
	
	
	private class ApplyJobsRunnable implements Runnable{

		private String idss;
		public ApplyJobsRunnable(String ids){
			idss=ids;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			applyEorrerBean=controller.applyJobs(idss);
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
				pullListView.onRefreshComplete();
				if (mode==Mode.PULL_FROM_END) {
					adapter.addDatas(positions);
				}
				if (mode==Mode.PULL_FROM_START) {
					adapter.clear();
					adapter.addDatas(positions);
				}
				if (mode==null) {
					if (positions.size()>0) {
						adapter.addDatas(positions);
					}else{
//						CustomMessage.showToast(context, "没有符合条件的职位！", Gravity.CENTER, 1);
						linear_notice.setVisibility(View.VISIBLE);
					}
					
				}
				
				break;

			case REQUEST_FAIL:
				pullListView.onRefreshComplete();
				if (mode==Mode.PULL_FROM_END) {
					--pageIndex;
				}
				if (mode==null) {
					if (positions==null||positions.size()==0) {
//						CustomMessage.showToast(context, "没有符合条件的职位！", Gravity.CENTER, 1);
						linear_notice.setVisibility(View.VISIBLE);
					}
				}
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
	private static List<Position> positions_temp;
	public interface LoadNextPageListener {
		void loadFinish();
		void loadEorror();
	}
	public static LoadNextPageListener nextPageLoadListener;
	
	/**
	 * <h2>自动加载下一页 <h2>
	 * <pre> </pre>
	 * @author 东方网升Eilin.Yang
	 * @since 2013-11-1
	 * @version 
	 * @modify ""
	 */
	private static class LoadNextPageRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			++pageIndex;
			pageSize=10;
			positions_temp=controller.getPositions( SearchFragment.keyword, SearchFragment.scope, pageIndex, pageSize, SearchFragment.areaCode, SearchFragment.positionsClassCode, updateTime, works, educations, salary+"", room_board+"", work_mode+"");
			if (positions_temp!=null) {
				pageHandler.sendEmptyMessage(1001);
			}else {
				pageHandler.sendEmptyMessage(1000);
			}
		}
		
	}
	
	private static Handler pageHandler=new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1000:
				--pageIndex;
				nextPageLoadListener.loadEorror();
				break;

			case 1001:
				adapter.addDatas(positions_temp);
				nextPageLoadListener.loadFinish();
				break;
			}
		}
		
	};
	
	
	public static void loadNextPage(LoadNextPageListener loadNextPageListener) {
		nextPageLoadListener=loadNextPageListener;
		executorService.submit(new LoadNextPageRunnable());
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
