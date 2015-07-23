/**
 * Copyright © 2014年3月29日 FindJob www.veryeast.com
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

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.MainActivity;
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.activity.resume.CertificateInformationActivity;
import zjdf.zhaogongzuo.activity.resume.DisclosureActivity;
import zjdf.zhaogongzuo.activity.resume.EducationsActivity;
import zjdf.zhaogongzuo.activity.resume.JobFavoriteActivity;
import zjdf.zhaogongzuo.activity.resume.JobStatusActivity;
import zjdf.zhaogongzuo.activity.resume.LanguageActivity;
import zjdf.zhaogongzuo.activity.resume.ResumeBaseInFoActivity;
import zjdf.zhaogongzuo.activity.resume.SkillsActivity;
import zjdf.zhaogongzuo.activity.resume.TrainingActivity;
import zjdf.zhaogongzuo.activity.resume.WorksActivity;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.controllers.ResumeInformationControllers;
import zjdf.zhaogongzuo.entity.Status;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.CustomSeekBar;
import zjdf.zhaogongzuo.utils.BitmapTools;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我的简历
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月29日
 * @version
 * @modify
 * 
 */
public class MyResumeActivity extends BaseActivity {

	private Context context;
	/** 返回 */
	private ImageButton ibtn_back;
	/** 简历完整度 */
	private CustomSeekBar seekBar;
	/** 照片 */
	private RelativeLayout rela_photo;
	/** 头像 */
	private ImageView iv_resume_protrait;
	/** 求职状态 */
	private RelativeLayout rela_jobstatus;
	private TextView txt_jobstatus_notice;
	/** 公开状态 */
	private RelativeLayout rela_openstate;
	private TextView txt_openstate_notice;
	/** 基本信息 */
	private RelativeLayout rela_base_info;
	private TextView txt_base_info;
	/** 求职意向 */
	private RelativeLayout rela_job_favorite;
	private TextView txt_job_favorite;
	/** 教育 */
	private RelativeLayout rela_edu;
	private TextView txt_edu;
	/** 工作经验 */
	private RelativeLayout rela_works;
	private TextView txt_works;
	/** 语言能力 */
	private RelativeLayout rela_language;
	private TextView txt_language;
	/** 技能和特长 */
	private RelativeLayout rela_extra;
	private TextView txt_extra;
	/** 培训经历 */
	private RelativeLayout rela_train;
	private TextView txt_train;
	/** 证书 */
	private RelativeLayout rela_certificate;
	private TextView txt_certificate;

	private ResumeInformationControllers controllers;// 简历增删改查
	private PersonalController personalControllers;// 个人中心控制器
	private Status status;// 返回数据
	private Bitmap bitmap = null;// 图片
	private int numbers;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycenter_myresume);
		context = this;
		controllers = new ResumeInformationControllers(context);
		personalControllers = new PersonalController(context);
		initView();
		setListener();
	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 赋值初始化数据
			case 1:
				if (status != null) {
					int number = (int) ((status.getCompletion()) * 100);
					if (number == 0) {
						seekBar.setProgress(0);
					} else {
						seekBar.setProgress(number);
					}
					// getBitamp();
					txt_jobstatus_notice.setText(status.getJob_status_text());
					txt_base_info.setText(status.getInfo_text());
					txt_job_favorite.setText(status.getIntention_text());
					txt_edu.setText(status.getEducation_text());
					txt_works.setText(status.getExperience_text());
					txt_language.setText(status.getLanguage_text());
					txt_extra.setText(status.getSkill_text());
					txt_train.setText(status.getTraining_text());
					txt_certificate.setText(status.getCertificate_text());
					String str="";
					 numbers=status.getPrivacy();
					if (numbers==1) {
						str="对所有公开";
					}else if(numbers==2){
						str="只公开Email";
					}else if(numbers==3){
						str="完全保密";
					}
					txt_openstate_notice.setText(str);
				}
				break;

			// 设置图片
			case 2:
				// iv_resume_protrait.setImageBitmap(bitmap);
				break;
			}
		};
	};

	/** 初始化控件 */
	private void initView() {
		ibtn_back = (ImageButton) findViewById(R.id.ibtn_back);
		seekBar = (CustomSeekBar) findViewById(R.id.skb_progress);
		rela_photo = (RelativeLayout) findViewById(R.id.rela_photo);
		iv_resume_protrait = (ImageView) findViewById(R.id.iv_resume_protrait);
		rela_jobstatus = (RelativeLayout) findViewById(R.id.rela_jobstatus);
		txt_jobstatus_notice = (TextView) findViewById(R.id.txt_jobstatus_notice);
		rela_base_info = (RelativeLayout) findViewById(R.id.rela_base_info);
		txt_base_info = (TextView) findViewById(R.id.txt_base_info);
		rela_job_favorite = (RelativeLayout) findViewById(R.id.rela_job_favorite);
		txt_job_favorite = (TextView) findViewById(R.id.txt_job_favorite);
		rela_edu = (RelativeLayout) findViewById(R.id.rela_edu);
		txt_edu = (TextView) findViewById(R.id.txt_edu);
		rela_works = (RelativeLayout) findViewById(R.id.rela_works);
		txt_works = (TextView) findViewById(R.id.txt_works);
		rela_language = (RelativeLayout) findViewById(R.id.rela_language);
		txt_language = (TextView) findViewById(R.id.txt_language);
		rela_extra = (RelativeLayout) findViewById(R.id.rela_extra);
		txt_extra = (TextView) findViewById(R.id.txt_extra);
		rela_train = (RelativeLayout) findViewById(R.id.rela_train);
		txt_train = (TextView) findViewById(R.id.txt_train);
		rela_certificate = (RelativeLayout) findViewById(R.id.rela_certificate);
		txt_certificate = (TextView) findViewById(R.id.txt_certificate);
		rela_openstate = (RelativeLayout) findViewById(R.id.rela_openstate);
		txt_openstate_notice = (TextView) findViewById(R.id.txt_openstate_notice);
	}

	// 添加监听器
	private void setListener() {
		ibtn_back.setOnClickListener(listener);
		rela_photo.setOnClickListener(listener);
		rela_jobstatus.setOnClickListener(listener);
		rela_base_info.setOnClickListener(listener);
		rela_job_favorite.setOnClickListener(listener);
		rela_edu.setOnClickListener(listener);
		rela_works.setOnClickListener(listener);
		rela_language.setOnClickListener(listener);
		rela_extra.setOnClickListener(listener);
		rela_train.setOnClickListener(listener);
		rela_certificate.setOnClickListener(listener);
		rela_openstate.setOnClickListener(listener);

	}

	/** 初始化数据 */
	private void iniDatas() {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 0);
			return;
		}
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				status = controllers.getStatus();
				handler.sendEmptyMessage(1);
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
		iniDatas();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}

	/** 获取图片 */
	private void getBitamp() {
		final String url = status.getAvatar();
		if (StringUtils.isEmpty(url)) {
			return;
		}
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				bitmap = BitmapTools.loadImageAndStore(
						FrameConfigures.FOLDER_PRA, url, "usericon.png");
				handler.sendEmptyMessage(2);
			}
		});
	}

	// 监听器
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 返回
			case R.id.ibtn_back:
				MainActivity.mainActivity.setTabSelection(2);
				finish();
				break;
			// 照片
			case R.id.rela_photo:

				break;
			// 求职状态
			case R.id.rela_jobstatus:
				Intent intent_JobStatus = new Intent(context,JobStatusActivity.class);
				intent_JobStatus.putExtra("JobStatus", status==null?"正在找工作":status.getJob_status_text());
				startActivityForResult(intent_JobStatus, 1);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				//finish();
				break;
			// 简历公开状态
			case R.id.rela_openstate:
				Intent intent_Disclosure = new Intent(context,DisclosureActivity.class);
				intent_Disclosure.putExtra("Disclosure",numbers );
				startActivityForResult(intent_Disclosure, 2);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				//finish();
				break;
			// 基本信息
			case R.id.rela_base_info:
				startActivity(new Intent(context, ResumeBaseInFoActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 求职意向
			case R.id.rela_job_favorite:
				startActivity(new Intent(context, JobFavoriteActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				//finish();
				break;
			// 教育经历
			case R.id.rela_edu:
				startActivity(new Intent(context, EducationsActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				//finish();
				break;
			// 工作经验
			case R.id.rela_works:
				startActivity(new Intent(context, WorksActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				//finish();
				break;
			// 语言能力
			case R.id.rela_language:
				startActivity(new Intent(context, LanguageActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			//	finish();
				break;
			// 技能和特长
			case R.id.rela_extra:
				startActivity(new Intent(context, SkillsActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				//finish();
				break;
			// 培训和经历
			case R.id.rela_train:
				startActivity(new Intent(context, TrainingActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			//	finish();
				break;
			// 证书
			case R.id.rela_certificate:
				startActivity(new Intent(context,
						CertificateInformationActivity.class));
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				//finish();
				break;
			}
		}
	};

	// 处理返回值
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 求职状态
		case 1:
			if (data == null || "".equals(data)) {
				return;
			} else {

				txt_jobstatus_notice.setText(data.getStringExtra("JobStatus"));
			}
			break;
		// 公开状态
		case 2:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_openstate_notice.setText(data.getStringExtra("Disclosure"));
			}
			break;
		// 基本信息
		case 3:

			break;
		// 求职意向
		case 4:

			break;
		// 教育经历
		case 5:

			break;
		// 工作经验
		case 6:

			break;
		// 语言能力
		case 7:

			break;
		// 技能和特长
		case 8:

			break;
		// 培训经历
		case 9:

			break;
		// 证书
		case 10:

			break;
		}
	};

}
