package zjdf.zhaogongzuo.activity.more;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.controllers.MoreController;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.umeng.analytics.MobclickAgent;

/**
 * 更多--意见反馈
 * 
 * @author Administrator
 * 
 */
public class FeedbackActivity extends Activity {
	private ImageButton subscribe_return;// 返回
	private Button txt_more_submit;// 提交
	private EditText more_job_content;// 内容
	private EditText more_job_number;// 电话/邮箱
	private Context context;// 上下文
	private MoreController moreController;// 控制器
	private int value;// 接收返回数据
	private String phoneNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more_job_feedback);
		context = this;
		moreController = new MoreController(context);
		TelephonyManager tm = (TelephonyManager)
		// 与手机建立连接
		getSystemService(Context.TELEPHONY_SERVICE);
		// 获取手机号码
		phoneNo = tm.getLine1Number();
		initView();

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

	// 初始化数据
	private void initView() {
		subscribe_return = (ImageButton) findViewById(R.id.subscribe_return);
		txt_more_submit = (Button) findViewById(R.id.txt_more_submit);
		more_job_content = (EditText) findViewById(R.id.more_job_content);
		more_job_number = (EditText) findViewById(R.id.more_job_number);
		more_job_number.setText(phoneNo==null?"":phoneNo); 
//		more_job_number.setText(ApplicationConfig.email);
		subscribe_return.setOnClickListener(listener);
		txt_more_submit.setOnClickListener(listener);
	}

	// 监听器
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 返回
			case R.id.subscribe_return:
				finish();
				break;
			// 提交
			case R.id.txt_more_submit:
				MobclickAgent.onEvent(context, "more_feedback");
				initRequest();
				break;
			default:
				break;
			}
		}
	};

	// 请求服务器处理
	private void initRequest() {
		final String str_content;// 内容
		final String str_number;// 电话邮箱
		str_content = more_job_content.getText().toString();
		str_number = more_job_number.getText().toString();
		// 判断内容
		if (StringUtils.isEmpty(str_content)) {
			CustomMessage.showToast(context, "内容不能为空，请重新输入。", Gravity.CENTER,0);
			return;
		}
		if (StringUtils.isEmpty(str_number)) {
			CustomMessage.showToast(context, "邮箱或手机号不能为空，请重新输入。", Gravity.CENTER,0);
			return;
		}
		if (str_number.contains("@")) {
			if (!StringUtils.checkEmail(str_number)) {
				CustomMessage.showToast(context, "邮箱格式不正确！", Gravity.CENTER,0);
				return;
			}
		}else if(!str_number.contains("@")){
			if (!StringUtils.checkPhone(str_number)) {
				CustomMessage.showToast(context, "手机号码格式不正确！", Gravity.CENTER,0);
				return;
			}
		}
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "连接网络失败，请检查网络！", Gravity.CENTER,0);
			return;
		}
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				value = moreController
						.set_FeedbackMore(str_number, str_content);
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	// 刷新界面
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				if (value == 1) {
					CustomMessage.showToast(context, "感谢您的反馈，我们会积极改进的！", Gravity.CENTER,0);
				} else {
					CustomMessage.showToast(context, "失败了！", Gravity.CENTER,0);
				}
				finish();
				break;
			}

		}
	};

}
