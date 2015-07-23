package zjdf.zhaogongzuo.activity.options;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.JobInfoCompanyView;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
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

import com.umeng.analytics.MobclickAgent;

/**
 * 单独 企业
 * 
 * @author Administrator
 * 
 */
public class CompanyInfoActivity extends BaseActivity {
	//
	private Context context;
	private JobInfoCompanyView ui_company;
	private String company_id;
	private Company company;
	private PositionController controller;
	private ImageButton imagereturn;
	/**申请或加关注*/
	private Button btn_attention;
	/**职位申请状态信息*/
	private EorrerBean attentionEorrerBean;
	/**取消关注*/
	private EorrerBean deleteAttentionEorrerBean;
	/**是否已经关注企业*/
	private int isAttention;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_companyinfo);
		context=this;
		company_id = getIntent().getStringExtra("company_id");
		controller=new PositionController(this);
		initView();
		loadData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		ui_company = (JobInfoCompanyView) findViewById(R.id.ui_company);
		imagereturn=(ImageButton) findViewById(R.id.image_but_return);
		btn_attention=(Button) findViewById(R.id.btn_attention);
		btn_attention.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MobclickAgent.onEvent(context, "position_search_position_info_follow");
				if (StringUtils.isEmpty(mApplication.user_ticket)||StringUtils.isEmpty(mApplication.user.getName())) {
					Intent intent=new Intent(context, LoginActivity.class);
					context.startActivity(intent);
					((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
					return;
				}
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "请检查网络！", Gravity.CENTER, 0);
					return;
				}
				if (!StringUtils.isEmpty(company_id)) {
					if (isAttention==1) {
						executorService.submit(new DeleteAttentionCompanyRunnable());
					}else
						executorService.submit(new AttentionCompanyRunnable());
				}
			}
		});
		imagereturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		setAttentionViewState();
	}
	/**
	 * 切换申请状态
	 */
	private void setAttentionViewState(){
		if (btn_attention!=null) {
			if (isAttention==1) {
				btn_attention.setText(R.string.search_company_followed);
			}else {
				btn_attention.setText(R.string.search_company_follow);
			}
		}
	}

	private void loadData() {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				company = controller.getCompany(company_id+"");
				sendMessage(1);
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (company!=null){
					ui_company.setDatas(company,null);
				}else {
					CustomMessage.showToast(context, "获取数据失败！", 0);
				}
				if (company!=null) {
					isAttention=company.getIsFollowed();
					setAttentionViewState();
				}
				break;
				
			case 331:
				CustomMessage.showToast(context, "关注成功！", 0);
				isAttention=1;
				setAttentionViewState();
				break;
			case 330:
				CustomMessage.showToast(context, (attentionEorrerBean==null?"关注失败！":attentionEorrerBean.errMsg), 0);
				break;
			case 333:
				CustomMessage.showToast(context, "取消关注成功！", 0);
				isAttention=0;
				setAttentionViewState();
				break;
			case 334:
				CustomMessage.showToast(context, (deleteAttentionEorrerBean==null?"关注失败！":deleteAttentionEorrerBean.errMsg), 0);
				break;

			}
		};
	};

	/**
	 * 关注企业
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月15日
	 * @version v1.0.0
	 * @modify
	 */
	private class AttentionCompanyRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			attentionEorrerBean=controller.follow_company(company_id);
			if (attentionEorrerBean!=null&&attentionEorrerBean.status==1) {
				sendMessage(331);
			}else {
				sendMessage(330);
			}
		}
		
	}
	
	/**
	 * 取消关注企业
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月15日
	 * @version v1.0.0
	 * @modify
	 */
	private class DeleteAttentionCompanyRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			deleteAttentionEorrerBean=controller.unfollow_company(company_id);
			if (deleteAttentionEorrerBean!=null&&deleteAttentionEorrerBean.status==1) {
				sendMessage(333);
			}else {
				sendMessage(334);
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
