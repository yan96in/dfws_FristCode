package zjdf.zhaogongzuo.activity.more;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.controllers.MoreController;
import zjdf.zhaogongzuo.entity.SetPush;
import zjdf.zhaogongzuo.ui.CustomMessage;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

/**
 * 更多---- 推送
 * 
 * @author Administrator
 * 
 */
public class PushActivity extends Activity {
	private ImageButton push_return;// 返回
	private Button txt_more_push_submite;// 提交

	private ImageButton tb_sets_push_mode;// 推送开关
	private ImageButton tb_sets_job_mode;// ְ职位订阅
	private ImageButton tb_sets_message_mode;// 我的消息
	private ImageButton tb_sets_why_mode;// ˭谁看过我
	private ImageButton tb_sets_recruitment_mode;// 招聘会



	private Context context;// 上下文
	private MoreController moreController;// 控制器
	private SetPush setPush;// 返回推送状态
	private int s1, s2, s3, s4; // 0 为关闭 or 1开启
	private int stuats;// 0 为关闭 or 1开启 推送设置 总开关
	private int Return_status;// 设置推送状态

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more_job_push);
		context = this;
		moreController = new MoreController(context);
		setPush = new SetPush();
		initView();

	}

	// 刷新界面
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			// 初始化 服务器获取的状态
			case 8:
				if (setPush != null) {
					if (setPush.getPush_enable() == 1) {
						tb_sets_push_mode
								.setBackgroundResource(R.drawable.ic_button_open);
						// 职位订阅
						if (setPush.getPush_job_enable() == 1) {
							tb_sets_job_mode
									.setBackgroundResource(R.drawable.ic_button_open);
						} else {
							tb_sets_job_mode
									.setBackgroundResource(R.drawable.ic_button_cloes);
						}
						// 我的消息
						if (setPush.getPush_msg_enable() == 1) {
							tb_sets_message_mode
									.setBackgroundResource(R.drawable.ic_button_open);
						} else {
							tb_sets_message_mode
									.setBackgroundResource(R.drawable.ic_button_cloes);
						}
						// 谁看过我
						if (setPush.getPush_viewed_enable() == 1) {
							tb_sets_why_mode
									.setBackgroundResource(R.drawable.ic_button_open);
						} else {
							tb_sets_why_mode
									.setBackgroundResource(R.drawable.ic_button_cloes);
						}
						// 招聘会
						if (setPush.getPush_meets_enable() == 1) {
							tb_sets_recruitment_mode
									.setBackgroundResource(R.drawable.ic_button_open);
						} else {
							tb_sets_recruitment_mode
									.setBackgroundResource(R.drawable.ic_button_cloes);
						}
					} else {
						// 所有都是0 那么当前状态都为关闭
						tb_sets_push_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
						tb_sets_job_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
						tb_sets_why_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
						tb_sets_recruitment_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
						tb_sets_message_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
					}
				}
				break;
			// 成功设置过 推送返回的状态
			case 9:
				if (Return_status == 0) {
					CustomMessage.showToast(context, "设置成功！", Gravity.CENTER, 0);

				} else {
					CustomMessage.showToast(context, "设置失败！", Gravity.CENTER, 0);
				}
				break;

			}
		}
	};

	// 初始化数据
	private void initView() {
		push_return = (ImageButton) findViewById(R.id.push_return);
		txt_more_push_submite = (Button) findViewById(R.id.txt_more_push_submite);

		tb_sets_push_mode = (ImageButton) findViewById(R.id.tb_sets_push_mode);
		tb_sets_job_mode = (ImageButton) findViewById(R.id.tb_sets_job_mode);
		tb_sets_message_mode = (ImageButton) findViewById(R.id.tb_sets_message_mode);
		tb_sets_why_mode = (ImageButton) findViewById(R.id.tb_sets_why_mode);
		tb_sets_recruitment_mode = (ImageButton) findViewById(R.id.tb_sets_recruitment_mode);


		// linear_product_info = (LinearLayout)
		// findViewById(R.id.linear_product_info);
		// 头部两个按钮监听
		push_return.setOnClickListener(listener);
		txt_more_push_submite.setOnClickListener(listener);
		// imagebutton 监听
		tb_sets_push_mode.setOnClickListener(listener);
		tb_sets_job_mode.setOnClickListener(listener);
		tb_sets_message_mode.setOnClickListener(listener);
		tb_sets_why_mode.setOnClickListener(listener);
		tb_sets_recruitment_mode.setOnClickListener(listener);

		getpushState();

		stuats = setPush.getPush_enable();// 推送设置
		s1 = setPush.getPush_job_enable();// 职位订阅
		s2 = setPush.getPush_meets_enable();// 招聘会
		s3 = setPush.getPush_msg_enable();// 我的消息
		s4 = setPush.getPush_viewed_enable();// 谁看过我
	}

	// 获取推送设置
	private void getpushState() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				setPush = moreController.get_PushNotice();
				handler.sendEmptyMessage(8);

			}
		}.start();
	}

	// 设置推送
	private void setPush() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				Return_status = moreController.Set_PushNotice(
						setPush.getPush_enable(), setPush.getPush_job_enable(),
						setPush.getPush_msg_enable(),
						setPush.getPush_viewed_enable(),
						setPush.getPush_meets_enable());
				handler.sendEmptyMessage(9);
			}
		}.start();
	}

	// 设置按钮 全部关闭状态
	private void setState() {
		setPush.setPush_enable(0);
		tb_sets_job_mode.setBackgroundResource(R.drawable.ic_button_cloes);
		setPush.setPush_job_enable(0);
		tb_sets_message_mode.setBackgroundResource(R.drawable.ic_button_cloes);
		setPush.setPush_msg_enable(0);
		tb_sets_why_mode.setBackgroundResource(R.drawable.ic_button_cloes);
		setPush.setPush_viewed_enable(0);
		tb_sets_recruitment_mode
				.setBackgroundResource(R.drawable.ic_button_cloes);
		setPush.setPush_meets_enable(0);
		// stuats = 1;
	}

	// 设置全部开启状态
	private void setOpen() {
		tb_sets_job_mode.setBackgroundResource(R.drawable.ic_button_open);
		tb_sets_message_mode.setBackgroundResource(R.drawable.ic_button_open);
		tb_sets_why_mode.setBackgroundResource(R.drawable.ic_button_open);
		tb_sets_recruitment_mode
				.setBackgroundResource(R.drawable.ic_button_open);
		setPush.setPush_enable(1);
		setPush.setPush_job_enable(1);
		setPush.setPush_msg_enable(1);
		setPush.setPush_viewed_enable(1);
		setPush.setPush_meets_enable(1);

	}

	// 监听器
	private View.OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			listener(v);
		}

		private void listener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 返回
			case R.id.push_return:
				finish();
				break;
			// 确定
			case R.id.txt_more_push_submite:
				setPush();
				finish();
				break;

			// ְ推送设置 ImageButton
			case R.id.tb_sets_push_mode:
				if (stuats == 0) {
					setState();
					// linear_product_info.setVisibility(View.GONE);
					tb_sets_push_mode
							.setBackgroundResource(R.drawable.ic_button_cloes);
					setPush.setPush_enable(0);
					stuats = 1;
				} else {
					// linear_product_info.setVisibility(View.VISIBLE);
					tb_sets_push_mode
							.setBackgroundResource(R.drawable.ic_button_open);
					setOpen();
					stuats = 0;
					setPush.setPush_enable(1);
				}
				break;
			// 职位订阅 ImageButton
			case R.id.tb_sets_job_mode:
				if (stuats == 0) {
					if (s1 == 0) {
						tb_sets_job_mode.setBackgroundResource(R.drawable.ic_button_cloes);
						setPush.setPush_job_enable(0);
						s1 = 1;
					} else {
						tb_sets_job_mode
								.setBackgroundResource(R.drawable.ic_button_open);
						setPush.setPush_job_enable(1);
						s1 = 0;
					}
				}
				break;
			// ˭我的消息 ImageButton
			case R.id.tb_sets_message_mode:
				if (stuats==0) {
					if (s3 == 0) {
						tb_sets_message_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
						setPush.setPush_msg_enable(0);
						s3 = 1;
					} else {
						tb_sets_message_mode
								.setBackgroundResource(R.drawable.ic_button_open);
						setPush.setPush_msg_enable(1);
						s3 = 0;
					}
				}
				break;
			// 谁看过我 ImageButton
			case R.id.tb_sets_why_mode:
				if (stuats==0) {
					if (s4 == 0) {
						tb_sets_why_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
						setPush.setPush_viewed_enable(0);
						s4 = 1;
					} else {
						tb_sets_why_mode
								.setBackgroundResource(R.drawable.ic_button_open);
						setPush.setPush_viewed_enable(1);
						s4 = 0;
					}
				}
				break;
			// 招聘会
			case R.id.tb_sets_recruitment_mode:
				if (stuats==0) {
					if (s2 == 0) {
						tb_sets_recruitment_mode
								.setBackgroundResource(R.drawable.ic_button_cloes);
						setPush.setPush_meets_enable(0);
						s2 = 1;
					} else {
						tb_sets_recruitment_mode
								.setBackgroundResource(R.drawable.ic_button_open);
						setPush.setPush_meets_enable(1);
						s2 = 0;
					}
				}
				break;
			}
		}
	};

	/*
	 * protected void onResume() { super.onResume(); getpushState(); };
	 */
	
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
