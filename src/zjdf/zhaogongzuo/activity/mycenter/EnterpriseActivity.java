package zjdf.zhaogongzuo.activity.mycenter;

import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.adapter.PositionAdapters;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 企业 招聘职位展示
 * 
 * @author Administrator
 * 
 */
public class EnterpriseActivity extends BaseActivity {
	private Context context;// 上下文
	private ImageButton subscribe_return;// 返回
	private TextView txt_datails_title;// 显示企业名字
	private int enterprise_id;// 接收listview 传递过来的ID
	private String enterprise_name;
	private MyResumeConttroller myResumeConttroller;// 控制器
	private ListView listview_enterpers;// listview
	private PositionAdapters positionAdapter;// 职位数据适配器
	private List<Position> mPositions;// 数据
	private TextView works_txtview;//理解申请
	private boolean flag;
	private PositionController controller;//控制器
	/**职位申请状态信息*/
	private EorrerBean applyEorrerBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycenter_enterpers);
		context = this;
		myResumeConttroller = new MyResumeConttroller(context);
		controller=new PositionController(context);
		enterprise_id = getIntent().getIntExtra("enterprise_id", 0);
		enterprise_name = getIntent().getStringExtra("enterprise_name");
		initVew();
	}

	// 初始化数据
	private void initVew() {
		// TODO Auto-generated method stub
		listview_enterpers = (ListView) findViewById(R.id.listview_enterpers);
		works_txtview=(TextView) findViewById(R.id.works_txtview);
		//返回
		subscribe_return=(ImageButton) findViewById(R.id.subscribe_return);
		subscribe_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//理解申请
		works_txtview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			       doSet();
			}
		});
		//标题
		txt_datails_title=(TextView) findViewById(R.id.txt_datails_title);
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 0);
			return;
		}
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mPositions=myResumeConttroller.company_recruit_jobs(enterprise_id+"");
				handler.sendEmptyMessage(1);
			}
		});
		

	}
	// 刷新 ui
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				positionAdapter = new PositionAdapters(context, mPositions,listview_enterpers);
				listview_enterpers.setAdapter(positionAdapter);
				txt_datails_title.setText(enterprise_name);
				break;
			case 2:
				CustomMessage.showToast(context, "申请成功", 0);
				break;
				
			case 20:
				CustomMessage.showToast(context, (applyEorrerBean==null?"职位申请失败!":applyEorrerBean.errMsg), 0);
				break;
			}
		
		};
	};
	
	// 处理按钮点击事件
		private void doSet() {
			
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "连接网络失败，请检查网络！", Gravity.CENTER, 0);
					return;
				}
				final String idss = positionAdapter.getSelectedJobIdsString();
				if (StringUtils.isEmpty(idss)) {
					CustomMessage.showToast(context, "请至少选择一个职位！", Gravity.CENTER, 0);
					return;
				}
				if (StringUtils.isEmpty(mApplication.user_ticket)||mApplication.user==null||StringUtils.isEmpty(mApplication.user.getName())) {
					CustomMessage.showToast(context, "请先登录！", Gravity.CENTER, 0);
					return;
				}
				executorService.submit(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						applyEorrerBean=controller.applyJobs(idss);
						if (applyEorrerBean!=null&&applyEorrerBean.status==1) {
							handler.sendEmptyMessage(2);
						}else {
							handler.sendEmptyMessage(20);
						}
					}
				});
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
