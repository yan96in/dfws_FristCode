/**
 * Copyright © 2014年4月29日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import zjdf.zhaogongzuo.utils.ThirdParty;

/**
 * 职位详细信息
 *<h2> JobInfoItemView</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月29日
 * @version 
 * @modify 
 * 
 */
public class JobInfoItemView extends LinearLayout {

	private Context context;
	/**选项*/
	private RadioGroup rdo_group_tabs;
	/**选项--职位详情*/
	private RadioButton rdo_job_info;
	/**选项--企业介绍*/
	private RadioButton rdo_company_info;
	/**收藏*/
	private ImageButton ibtn_favorite;
	/**申请或加关注*/
	private Button btn_apply_or_attention;
	/**分享*/
	private ImageButton ibtn_share;
	/**职位详情页面*/
	private JobInfoJobView jobinfo_job;
	/**企业介绍页面*/
	private JobInfoCompanyView jobinfo_company;
	/***线程池*/
	public static ExecutorService executorService;
	/**显示状态.默认显示职位详情,1:职位详情，*/
	private ShowPage showpage=ShowPage.JOB;
	/**职位详情*/
	private Position mPosition;
	/**企业详情*/
	private Company mCompany;
	/**职位详情控制器*/
	private PositionController controller;
	/**加载数据对话框*/
	private Dialog mDialog;
	/**职位id*/
	private String jobId;
	/**企业id*/
	private String companyId;
	
	private ShareCustomDialog shareDialog;// 分享
	/**职位申请状态信息*/
	private EorrerBean applyEorrerBean;
	/**职位收藏状态信息*/
	private EorrerBean favoriteEorrerBean;
	/**职位申请状态信息*/
	private EorrerBean attentionEorrerBean;
	/**取消收储*/
	private EorrerBean deleteFavoriteEorrerBean;
	/**取消关注*/
	private EorrerBean deleteAttentionEorrerBean;
	
	private ApplicationConfig mApplication;
	/**是否已经收藏*/
	private int isFavorited;
	/**是否已经申请*/
	private int isApplied;
	/**是否已经关注企业*/
	private int isAttention;
	
	public JobInfoItemView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public JobInfoItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		MobclickAgent.onEvent(context, "position_search_position_info");
		mApplication=(ApplicationConfig)context.getApplicationContext();
		LayoutInflater.from(context).inflate(R.layout.layout_jobinfo_item,
				this, true);
		executorService=Executors.newCachedThreadPool();
		controller=new PositionController(context);
		shareDialog = new ShareCustomDialog(context);
		initView();
		setListener();
		switchPage();
	}
	
	/**
	 *<pre>方法  </pre>
	 */
	private void initView() {
		// TODO Auto-generated method stub
		rdo_group_tabs=(RadioGroup)findViewById(R.id.rdo_group_tabs);
		rdo_job_info=(RadioButton)findViewById(R.id.rdo_job_info);
		rdo_company_info=(RadioButton)findViewById(R.id.rdo_company_info);
		ibtn_favorite=(ImageButton)findViewById(R.id.ibtn_favorite);
		ibtn_share=(ImageButton)findViewById(R.id.ibtn_share);
		btn_apply_or_attention=(Button)findViewById(R.id.btn_apply_or_attention);
		jobinfo_job=(JobInfoJobView)findViewById(R.id.jobinfo_job);
		jobinfo_company=(JobInfoCompanyView)findViewById(R.id.jobinfo_company);
		mDialog= CustomMessage.createProgressDialog(context, "加载中...", false);
	}
	
	/**
	 * 切换显示界面
	 *<pre>方法  </pre>
	 */
	private void switchPage(){
		if (showpage==ShowPage.JOB) {
			jobinfo_company.setVisibility(View.GONE);
			jobinfo_job.setVisibility(View.VISIBLE);
			
			ibtn_favorite.setVisibility(View.VISIBLE);
			ibtn_share.setVisibility(View.VISIBLE);
//			btn_apply_or_attention.setText("立即申请");
			setApplyViewState();
			setFavoriteViewState();
		}
		if (showpage==ShowPage.COMPANY) {
			jobinfo_job.setVisibility(View.GONE);
			jobinfo_company.setVisibility(View.VISIBLE);
			
			ibtn_favorite.setVisibility(View.INVISIBLE);
			ibtn_share.setVisibility(View.INVISIBLE);
			setAttentionViewState();
//			btn_apply_or_attention.setText("加关注");
		}
	}
	
	/**
	 * 设置监听
	 *<pre>方法  </pre>
	 */
	private void setListener(){
		rdo_group_tabs.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId==rdo_job_info.getId()) {
					showpage=ShowPage.JOB;
				}
				
				if (checkedId==rdo_company_info.getId()) {
					showpage=ShowPage.COMPANY;
				}
				switchPage();
			}
		});
		
		ibtn_favorite.setOnClickListener(listener);
		ibtn_share.setOnClickListener(listener);
		btn_apply_or_attention.setOnClickListener(listener);
	}
	
	private View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
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
				if (!StringUtils.isEmpty(jobId)) {
					if (isFavorited==1) {
						executorService.submit(new DeleteFavoriteJobRunnable());
					}else
						executorService.submit(new FavoriteJobRunnable());
				}
			}
			//分享
			if (v==ibtn_share) {
				MobclickAgent.onEvent(context, "position_search_position_info_share");
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "请检查网络！", Gravity.CENTER, 0);
					return;
				}
				if (mPosition==null) {
					return;
				}
				shareDialog.initView();
				shareDialog.setOnclickListener(new shareListener());
				shareDialog.show();
			}
			if (v==btn_apply_or_attention) {
				if (StringUtils.isEmpty(mApplication.user_ticket)||StringUtils.isEmpty(mApplication.user.getName())) {
//					CustomMessage.showToast(context, "请先登录！", Gravity.CENTER, 0);
					Intent intent=new Intent(context, LoginActivity.class);
					context.startActivity(intent);
					((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
					return;
				}
				if (!NetWorkUtils.checkNetWork(context)) {
					CustomMessage.showToast(context, "请检查网络！", Gravity.CENTER, 0);
					return;
				}
				if (showpage==ShowPage.JOB) {
					MobclickAgent.onEvent(context, "position_search_position_info_apply");
					if (!StringUtils.isEmpty(jobId)) {
						executorService.submit(new ApplyJobsRunnable());
					}
				}
				if (showpage==ShowPage.COMPANY) {
					MobclickAgent.onEvent(context, "position_search_position_info_follow");
					if (!StringUtils.isEmpty(companyId)) {
						if (isAttention==1) {
							executorService.submit(new DeleteAttentionCompanyRunnable());
						}else
							executorService.submit(new AttentionCompanyRunnable());
					}
				}
			}
		}
	};
	
	/**
	 * 开始页面
	 */
	public void start(){
		if (mDialog!=null&&!mDialog.isShowing()) {
			mDialog.isShowing();
		}
		executorService.submit(new LoadDatasRunnable());
	}
	
	/**
	 * 职位详情id
	 * @param jobid 职位id
	 */
	public void setJobInfoId(String jobid) {
		jobId=jobid;
	}
	
	/**
	 * 释放资源
	 */
	public void release() {
//		if () {
//			
//		}
	}
		
	private class LoadDatasRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mPosition=controller.getPosition(jobId);
			if (mPosition!=null&&!StringUtils.isEmpty(mPosition.getIdStr())) {
				companyId=mPosition.getCompanyId();
				mCompany=controller.getCompany(companyId);
			}
			sendMessage(101);
		}
		
	}
	
	private class ApplyJobsRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			applyEorrerBean=controller.applyJobs(jobId);
			if (applyEorrerBean!=null&&applyEorrerBean.status==1) {
				sendMessage(221);
			}else {
				sendMessage(220);
			}
		}
		
	}
	
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
			attentionEorrerBean=controller.follow_company(companyId);
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
			deleteAttentionEorrerBean=controller.unfollow_company(companyId);
			if (deleteAttentionEorrerBean!=null&&deleteAttentionEorrerBean.status==1) {
				sendMessage(333);
			}else {
				sendMessage(334);
			}
		}
		
	}
	
	/**
	 * 收藏职位
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月15日
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
			favoriteEorrerBean=controller.favoriteJobs(jobId);
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
			deleteFavoriteEorrerBean=controller.delete_favorite_job(jobId);
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
	
	private Handler handler=new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 100:
				CustomMessage.showToast(context, "加载数据失败！", 0);
				break;

			case 101:
				if (mPosition!=null) {
					jobinfo_job.setDatas(mPosition);
					if (mPosition!=null) {
						isApplied=mPosition.getIs_applied();
						isFavorited=mPosition.getIs_favorited();
						setApplyViewState();
						setFavoriteViewState();
					}
				}
				if (mCompany!=null) {
					jobinfo_company.setDatas(mCompany,null);
					if (mCompany!=null) {
						isAttention=mCompany.getIsFollowed();
						setAttentionViewState();
					}
				}
				if (mDialog!=null&&mDialog.isShowing()) {
					mDialog.dismiss();
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
				break;
				
			}
		}
		
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
		if (btn_apply_or_attention!=null) {
			if (showpage==ShowPage.JOB) {
				if (isApplied==1) {
					btn_apply_or_attention.setText(R.string.search_list_applied);
				}else {
					btn_apply_or_attention.setText(R.string.search_list_apply);
				}
			}
		}
	}
	
	/**
	 * 切换申请状态
	 */
	private void setAttentionViewState(){
		if (btn_apply_or_attention!=null) {
			if (showpage==ShowPage.COMPANY) {
				if (isAttention==1) {
					btn_apply_or_attention.setText(R.string.search_company_followed);
				}else {
					btn_apply_or_attention.setText(R.string.search_company_follow);
				}
			}
		}
	}
	
	/**
	 * 
	 *<h2> ShowPage</h2>
	 *<pre> 显示状态 </pre>
	 * @author Eilin.Yang VeryEast
	 * @since 2014年4月29日
	 * @version 
	 * @modify 
	 *
	 */
	private enum ShowPage{
		/**职位详情*/
		JOB,
		/**企业介绍*/
		COMPANY
	}
	
	

	// 分享监听
	private class shareListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			// "http://m.veryeast.cn/android/" + jobId + ".html"
			listener(v);
		}

		private void listener(View v) {
			String content = "这是我在#最佳东方#找工作时看到的职位，应该适合你，不妨看看。“"+mPosition.getName()+"”,查看详情";
			String content_wei = "推荐你一个好职位-"+mPosition.getName()+"-"+mPosition.getCompanyName();
			String tag_url = "http://m.veryeast.cn/android/" + mPosition.getIdStr() + ".html";
			String title = "";
			switch (v.getId()) {
			case R.id.txt_share_sina:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Sina(context, content+tag_url, null);

				break;

			case R.id.txt_share_tencent:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Tencent(context, content+tag_url, null);
				break;

			case R.id.txt_share_qqzone:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Qzone(context, content+tag_url, null);
				break;

			case R.id.txt_share_weixin:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Weixin(context, tag_url, content_wei, content_wei,
						null, false);
				break;

			case R.id.txt_share_circle:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty
						.share_Weixin(context, tag_url, content_wei, "", null, true);
				break;

			case R.id.txt_share_emile:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
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
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
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
	
	/**
	 * 获取当前职位
	 * @return
	 */
	public Position getPosition() {
		return mPosition;
	}
}
