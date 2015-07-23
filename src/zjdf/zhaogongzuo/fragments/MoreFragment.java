/**
 * Copyright © 2014-3-15 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.fragments;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.more.AboutActivity;
import zjdf.zhaogongzuo.activity.more.FeedbackActivity;
import zjdf.zhaogongzuo.activity.more.GuideActivity;
import zjdf.zhaogongzuo.activity.more.PushActivity;
import zjdf.zhaogongzuo.activity.more.SubscribeActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.controllers.MoreController;
import zjdf.zhaogongzuo.entity.Versions;
import zjdf.zhaogongzuo.service.DownloadService;
import zjdf.zhaogongzuo.ui.ShareCustomDialog;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import zjdf.zhaogongzuo.utils.ThirdParty;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * <h2>更多<h2>
 * 
 * <pre> </pre>
 * 
 * @author 东方网升Eilin.Yang
 * @since 2014-3-15
 * @version
 * @modify ""
 */
public class MoreFragment extends Fragment {
	private Context context;// 上下文
	private RelativeLayout set_job_subscription;// 职位订阅
	private RelativeLayout set_job_push;// 推送设置
	private RelativeLayout set_job_share;// 应用分享
	private RelativeLayout set_job_feedback;// 意见反馈
	private RelativeLayout set_job_guide;// 新手指引
	private RelativeLayout set_job_About;// 关于我们
	private RelativeLayout set_job_update;// 检查更新

	private ShareCustomDialog shareDialog;// 分享

	private MoreController moreController;// 控制器
	private ApplicationConfig applicationConfig;// 全局控制

	/**
	 * 检查更新
	 */
	private Versions version;// 应用程序版本
	private DownloadService.DownloadBinder binder;
	private String url;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		version = new Versions();
		moreController = new MoreController(context);
		applicationConfig = (ApplicationConfig) context.getApplicationContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_fragment_more, container,
				false);
		set_job_subscription = (RelativeLayout) view
				.findViewById(R.id.set_job_subscription);
		set_job_push = (RelativeLayout) view.findViewById(R.id.set_job_push);
		set_job_share = (RelativeLayout) view.findViewById(R.id.set_job_share);
		set_job_feedback = (RelativeLayout) view
				.findViewById(R.id.set_job_feedback);
		set_job_guide = (RelativeLayout) view.findViewById(R.id.set_job_guide);
		set_job_About = (RelativeLayout) view.findViewById(R.id.set_job_About);
		set_job_update = (RelativeLayout) view
				.findViewById(R.id.set_job_update);
		shareDialog = new ShareCustomDialog(context);
		addListener();
		return view;
	}

	// 刷新界面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String currentVersion = StringUtils.getAppVersionName(context);
				String netVersion = version.getLatest_version();
				// String netVersion = "2.0";
				int a = StringUtils.getAppVersionNameInt(context,"zjdf.zhaogongzuo");
				int b = StringUtils.parseArrayToInt(netVersion);
				if (!StringUtils.isEmpty(netVersion)) {
					if (currentVersion.equalsIgnoreCase(netVersion)) {
						Toast.makeText(context, "暂无新版本，当前已是最新版本!",Toast.LENGTH_LONG).show();
					} else if (b > a) {
						dialog();
					} else {
						Toast.makeText(context, "暂无新版本，当前已是最新版本!",Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(context, "获取版本信息失败，请重试！", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case 1:
				Toast.makeText(context, "暂无新版本，当前已是最新版本", Toast.LENGTH_LONG)
						.show();
				break;
			}
		};
	};

	// 显示更新下载的对话框
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(version.getLatest_version());
		builder.setTitle(version.getUpdate_log());
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doUpdate();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("稍后更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	/**
	 * 添加监听器
	 */
	private void addListener() {
		set_job_subscription.setOnClickListener(setListtenter);
		set_job_push.setOnClickListener(setListtenter);
		set_job_share.setOnClickListener(setListtenter);
		set_job_feedback.setOnClickListener(setListtenter);
		set_job_guide.setOnClickListener(setListtenter);
		set_job_About.setOnClickListener(setListtenter);
		set_job_update.setOnClickListener(setListtenter);

	}

	/**
	 * 
	 * 注册监听器
	 */
	private View.OnClickListener setListtenter = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 职位订阅
			case R.id.set_job_subscription:
/*				if (applicationConfig.user_ticket != null&& applicationConfig.user.getName() != null) {
					Intent intent = new Intent(context, SubscribeActivity.class);
					startActivityForResult(intent, 1);
					((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				} else {
					Toast.makeText(context, "请先登录哦！", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
				}*/
				Intent intents = new Intent(context, SubscribeActivity.class);
				startActivityForResult(intents, 1);
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 推送设置
			case R.id.set_job_push:
				if (applicationConfig.user.getName() != null&&applicationConfig.user_ticket!=null) {
					Intent intent_push = new Intent(context, PushActivity.class);
					startActivity(intent_push);
					((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				} else {
					Toast.makeText(context, "请先登录", 0).show();
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
				}

				break;
			// 应用分享
			case R.id.set_job_share:
				MobclickAgent.onEvent(context, "more_app_share");
				shareDialog.initView();
				shareDialog.setOnclickListener(new shareListener());
				shareDialog.show();
				// ThirdParty.controller.openShare(getActivity(), false);
				break;

			// 意见反馈
			case R.id.set_job_feedback:
				Intent intent_feedback = new Intent(context,
						FeedbackActivity.class);
				startActivity(intent_feedback);
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 新手指引
			case R.id.set_job_guide:
				Intent intent_guide = new Intent(context, GuideActivity.class);
				startActivity(intent_guide);
				break;
			// 关于我们
			case R.id.set_job_About:
				Intent intent_about = new Intent(context, AboutActivity.class);
				startActivity(intent_about);
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 检查更新
			case R.id.set_job_update:
				checkUpdate();
				break;
			default:
				break;
			}
		}
	};

	// 检查更新
	public void checkUpdate() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG).show();
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				version = moreController.get_checkVersion();
				if (version != null) {
					handler.sendEmptyMessage(0);
				} else {
					handler.sendEmptyMessage(1);
				}

			}
		}).start();
	}

	// 立即更新
	public void doUpdate() {
		url = version.getApp_url();
		Intent intent = new Intent(context, DownloadService.class);
		intent.putExtra("url", url);
		intent.putExtra("version", version.getLatest_version());
		context.startService(intent); // 如果先调用startService,则在多个服务绑定对象调用unbindService后服务仍不会被销毁
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (DownloadService.DownloadBinder) service;
			binder.start();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	public void cancel() {
		if (null != binder && !binder.isCancelled()) {
			binder.cancel();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != binder) {
			context.unbindService(conn);
		}
		cancel();
	}

	// 分享监听
	private class shareListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			share(v);
		}

		private void share(View v) {
			String content = "#最佳东方掌上求职#移动客户端，感觉不错，快来体验一下吧！下载地址：http://m.veryeast.cn/Azgz";
			String content_wei = "#我在使用最佳东方掌上求职#，感觉不错，快来体验吧！下载：http://m.veryeast.cn/Azgz";
			String tag_url = "http://m.veryeast.cn/Azgz";
			String title = "分享“最佳东方掌上求职客户端”";
			switch (v.getId()) {
			case R.id.txt_share_sina:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Sina(context, content, null);

				break;

			case R.id.txt_share_tencent:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Tencent(context, content, null);
				break;

			case R.id.txt_share_qqzone:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Qzone(context, content, null);
				break;

			case R.id.txt_share_weixin:
				MobclickAgent.onEvent(context, "more_app_share_weixin");
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_Weixin(context, tag_url, title, content_wei,
						null, false);
				break;

			case R.id.txt_share_circle:
				MobclickAgent.onEvent(context, "more_app_share_weixin_circle");
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty
						.share_Weixin(context, tag_url, title, "", null, true);
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
								content,
								null);
				break;

			case R.id.txt_share_sms:
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG)
							.show();
					break;
				}
				ThirdParty.share_SMS(context, content_wei);
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
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 MobclickAgent.onPageStart("MoreFragment"); 
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MoreFragment");
	}
}