package zjdf.zhaogongzuo.activity.options;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.JobInfoJobView;
import zjdf.zhaogongzuo.ui.ShareCustomDialog;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import zjdf.zhaogongzuo.utils.ThirdParty;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 单独 职位
 * 
 * @author Administrator
 * 
 */
public class PositionInfoActivity extends BaseActivity {
	private Context context;
	private JobInfoJobView ui_position;
	private String position_id;
	private Position position;//职位对象
	private PositionController controller;
    private ImageButton but_return;//返回
    /**收藏*/
    private ImageButton ibtn_favorite;
    /**申请*/
    private Button btn_apply;
    /**分享*/
    private ImageButton ibtn_share;
    /**加载对话框*/
    private Dialog dialog;
    private ShareCustomDialog shareDialog;// 分享
	/**职位申请状态信息*/
	private EorrerBean applyEorrerBean;
	/**职位收藏状态信息*/
	private EorrerBean favoriteEorrerBean;
	/**是否已经收藏*/
	private int isFavorited;
	/**是否已经申请*/
	private int isApplied;
	/**取消收储*/
	private EorrerBean deleteFavoriteEorrerBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_positinoinfo);
		context=this;
		position_id=getIntent().getStringExtra("ids");
		controller=new PositionController(this);
		initView();
		initDialog();
		loadData();
	}

	//初始化
	private void initView() {
		// TODO Auto-generated method stub
		ui_position = (JobInfoJobView) findViewById(R.id.ui_position);
		but_return=(ImageButton) findViewById(R.id.but_return);
		ibtn_favorite=(ImageButton)findViewById(R.id.ibtn_favorite);
		btn_apply=(Button)findViewById(R.id.btn_apply);
		ibtn_share=(ImageButton)findViewById(R.id.ibtn_share);
		but_return.setOnClickListener(listener);
		ibtn_favorite.setOnClickListener(listener);
		btn_apply.setOnClickListener(listener);
		ibtn_share.setOnClickListener(listener);
	}
	
	
	private void initDialog(){
		shareDialog = new ShareCustomDialog(context);
		dialog=CustomMessage.createProgressDialog(context, null, false);
	}
		
	private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==but_return) {
				finish();
			}
			if (v==ibtn_favorite) {
				MobclickAgent.onEvent(context, "position_search_position_info_favorite");
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
				if (!StringUtils.isEmpty(position_id)) {
					if (isFavorited==1) {
						executorService.submit(new DeleteFavoriteJobRunnable());
					}else
						executorService.submit(new FavoriteJobRunnable());
				}
			}
			if(v==ibtn_share){
				MobclickAgent.onEvent(context, "position_search_position_info_share");
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "请检查网络！", Gravity.CENTER, 0);
					return;
				}
				if (position==null) {
					return;
				}
				shareDialog.initView();
				shareDialog.setOnclickListener(new shareListener());
				shareDialog.show();
			}
			if (v==btn_apply) {
				MobclickAgent.onEvent(context, "position_search_position_info_apply");
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
				if (!StringUtils.isEmpty(position_id)) {
					executorService.submit(new ApplyJobsRunnable());
				}			
			}
		}
	};

	private void loadData() {
		// TODO Auto-generated method stub
		if (dialog!=null&&!dialog.isShowing()) {
			dialog.show();
		}
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				position = controller.getPosition(position_id);
				handler.sendEmptyMessage(1);

			}
		});
	}
	
	/**
	 * 申请职位
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月28日
	 * @version v1.0.0
	 * @modify
	 */
	private class ApplyJobsRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			applyEorrerBean=controller.applyJobs(position_id);
			if (applyEorrerBean!=null&&applyEorrerBean.status==1) {
				sendMessage(221);
			}else {
				sendMessage(220);
			}
		}
		
	}
	
	/**
	 * 收藏职位
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月28日
	 * @version v1.0.0
	 * @modify
	 */
	private class FavoriteJobRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			favoriteEorrerBean=controller.favoriteJobs(position_id);
			if (favoriteEorrerBean!=null&&favoriteEorrerBean.status==1) {
				sendMessage(441);
			}else {
				sendMessage(440);
			}
		}
		
	}
	
	/**
	 * 取消收藏
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月29日
	 * @version v1.0.0
	 * @modify
	 */
	private class DeleteFavoriteJobRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			deleteFavoriteEorrerBean=controller.delete_favorite_job(position_id);
			if (deleteFavoriteEorrerBean!=null&&deleteFavoriteEorrerBean.status==1) {
				sendMessage(444);
			}else {
				sendMessage(445);
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

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				ui_position.setDatas(position);
				if (position!=null) {
					isApplied=position.getIs_applied();
					isFavorited=position.getIs_favorited();
					setApplyViewState();
					setFavoriteViewState();
				}
				break;
			case 221:
				CustomMessage.showToast(context, "申请职位成功！", 0);
				isApplied=1;
				setApplyViewState();
				break;
			case 220:
				CustomMessage.showToast(context, (applyEorrerBean==null?"职位申请失败!":applyEorrerBean.errMsg), 0);
				break;
				
			case 441:
				CustomMessage.showToast(context, "收藏成功！", 0);
				isFavorited=1;
				setFavoriteViewState();
				break;
			case 440:
				CustomMessage.showToast(context, (favoriteEorrerBean==null?"收藏失败！":favoriteEorrerBean.errMsg), 0);
				break;
			case 444:
				CustomMessage.showToast(context, "取消收藏成功！", 0);
				isFavorited=0;
				setFavoriteViewState();
				break;
			case 445:
				CustomMessage.showToast(context, (deleteFavoriteEorrerBean==null?"收藏失败！":deleteFavoriteEorrerBean.errMsg), 0);
			}
			if (dialog!=null&&dialog.isShowing()) {
				dialog.dismiss();
			}
		};
	};
	
	/**
	 * 切换收藏状态
	 */
	private void setFavoriteViewState(){
		if (ibtn_favorite!=null) {
			if (isFavorited==1) {
				ibtn_favorite.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_star_over_48_36));
			}else {
				ibtn_favorite.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_jobinfo_favorite));
			}
		}
	}
	
	/**
	 * 切换申请状态
	 */
	private void setApplyViewState(){
		if (btn_apply!=null) {
			if (isApplied==1) {
				btn_apply.setText(R.string.search_list_applied);
			}else {
				btn_apply.setText(R.string.search_list_apply);
			}
		}
	}
		
	// 分享监听
		private class shareListener implements View.OnClickListener {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener(v);
			}

			private void listener(View v) {
				String content = "这是我在#最佳东方#找工作时看到的职位，应该适合你，不妨看看。“"+position.getName()+"”,查看详情";
				String content_wei = "推荐你一个好职位-"+position.getName()+"-"+position.getCompanyName();
				String tag_url = "http://m.veryeast.cn/android/" + position.getIdStr() + ".html";
				String title = "分享“最佳东方掌上求职客户端”";
				switch (v.getId()) {
				case R.id.txt_share_sina:
					if (!NetWorkUtils.checkNetWork(context)) {
						CustomMessage.showToast(context, "请检查网络连接！", Gravity.CENTER, 0);
						break;
					}
					ThirdParty.share_Sina(context, content+tag_url, null);

					break;

				case R.id.txt_share_tencent:
					if (!NetWorkUtils.checkNetWork(context)) {
						CustomMessage.showToast(context, "请检查网络连接！", Gravity.CENTER, 0);
						break;
					}
					ThirdParty.share_Tencent(context, content+tag_url, null);
					break;

				case R.id.txt_share_qqzone:
					if (!NetWorkUtils.checkNetWork(context)) {
						CustomMessage.showToast(context, "请检查网络连接！", Gravity.CENTER, 0);
						break;
					}
					ThirdParty.share_Qzone(context, content+tag_url, null);
					break;

				case R.id.txt_share_weixin:
					if (!NetWorkUtils.checkNetWork(context)) {
						CustomMessage.showToast(context, "请检查网络连接！", Gravity.CENTER, 0);
						break;
					}
					ThirdParty.share_Weixin(context, tag_url, content_wei, content_wei,
							null, false);
					break;

				case R.id.txt_share_circle:
					if (!NetWorkUtils.checkNetWork(context)) {
						CustomMessage.showToast(context, "请检查网络连接！", Gravity.CENTER, 0);
						break;
					}
					ThirdParty
							.share_Weixin(context, tag_url, title, "", null, true);
					break;

				case R.id.txt_share_emile:
					if (!NetWorkUtils.checkNetWork(context)) {
						CustomMessage.showToast(context, "请检查网络连接！", Gravity.CENTER, 0);
						break;
					}
					ThirdParty
							.share_Emile(
									context,
									content+tag_url,
									null);
					break;

				case R.id.txt_share_sms:
					if (!NetWorkUtils.checkNetWork(context)) {
						CustomMessage.showToast(context, "请检查网络连接！", Gravity.CENTER, 0);
						break;
					}
					ThirdParty.share_SMS(context, content+tag_url);
					break;
				case R.id.txt_share__cancel:

					break;
				default:
					break;
				}
				shareDialog.dismiss();
			}
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
