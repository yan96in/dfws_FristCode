package zjdf.zhaogongzuo.activity.personal;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.id;
import zjdf.zhaogongzuo.R.string;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 账号注册
 * 
 * @author Administrator
 * 
 */
public class RegisteredActivity extends BaseActivity {
	private EditText reg_userid_etxt;// 用户名
	private EditText reg_password_etxt;// 密码
	private EditText reg_email_etxt;// 邮箱
	private EditText reg_password_after_etxt;// 重复密码
	private ImageButton reg_return;// 返回
	private Button reg_button;// 注册
	private Context context;// 上下文
	private PersonalController personal;// 个人中心控制器
	private EorrerBean state;// 返回状态
	private Drawable c = null;// 删除图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_registered);
		context = this;
		personal = new PersonalController(context);
		initView();

	}

	//刷新UI
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (state!=null&&state.status==1) {
					CustomMessage.showToast(context, "注册成功！", Gravity.CENTER, 0);
					startActivity(new Intent(context, MicroresumeActivity.class));
					finish();
				} else {
					CustomMessage.showToast(context, (state==null?"注册失败！":state.errMsg), Gravity.CENTER, 0);
				}
				break;
			}
		};
	};

	// 初始化
	private void initView() {
		reg_userid_etxt = (EditText) findViewById(R.id.reg_userid_etxt);
		reg_password_etxt = (EditText) findViewById(R.id.reg_password_etxt);
		reg_email_etxt = (EditText) findViewById(R.id.reg_email_etxt);
		reg_password_after_etxt = (EditText) findViewById(R.id.reg_password_after_etxt);
		c = getResources().getDrawable(R.drawable.ic_close_gary);
		reg_return = (ImageButton) findViewById(R.id.reg_return);
		reg_button = (Button) findViewById(R.id.reg_button);
		// 点击事件
		reg_return.setOnClickListener(listener);
		reg_button.setOnClickListener(listener);
		reg_userid_etxt.setOnClickListener(listener);
		reg_password_etxt.setOnClickListener(listener);
		reg_email_etxt.setOnClickListener(listener);
		reg_password_after_etxt.setOnClickListener(listener);
		// 触屏点击删除事件
		reg_userid_etxt.addTextChangedListener(username_delect);
		reg_userid_etxt.setOnTouchListener(username_ontouch);
		reg_password_etxt.addTextChangedListener(userpass_delect);
		reg_password_etxt.setOnTouchListener(userpass_ontouch);
		reg_email_etxt.addTextChangedListener(useremail_delect);
		reg_email_etxt.setOnTouchListener(useremail_ontouch);
		reg_password_after_etxt.addTextChangedListener(userpass1_delect);
		reg_password_after_etxt.setOnTouchListener(userpass1_ontouch);

	}

	// 注册请求网络
	private void doReg() {
		MobclickAgent.onEvent(context, "mycenter_register");
		final String username = reg_userid_etxt.getText().toString().trim();
		final String userpass = reg_password_etxt.getText().toString().trim();
		final String useremail = reg_email_etxt.getText().toString().trim();
		final String userpass1 = reg_password_after_etxt.getText().toString().trim();
		if (StringUtils.isEmpty(username)) {
			CustomMessage.showToast(context, "请输入用户名！", Gravity.CENTER, 0);
			return;
		}
		if (username.trim().length()<3) {
			CustomMessage.showToast(context, "用户名必须是三位以上！", Gravity.CENTER, 0);
			return;
		}
		if (userpass.trim().length()<6) {
			CustomMessage.showToast(context, "密码必须是6位以上！", Gravity.CENTER, 0);
			return;
		}
		if (userpass1.trim().length()<6) {
			CustomMessage.showToast(context, "密码必须是6位以上！", Gravity.CENTER, 0);
			return;
		}
		if (StringUtils.isEmpty(useremail)||!StringUtils.checkEmail(useremail)) {
			CustomMessage.showToast(context, "请输入正确的邮箱！", Gravity.CENTER, 0);
			reg_email_etxt.setError(this.getResources().getString(R.string.reg_email1));
			return;
		}
		
		if (StringUtils.isEmpty(userpass)) {
			CustomMessage.showToast(context, "请输入密码！", Gravity.CENTER, 0);
			return;
		}
		if (StringUtils.isEmpty(userpass1)) {
			CustomMessage.showToast(context, "请确认密码！", Gravity.CENTER, 0);
			return;
		}
		if (!StringUtils.equals(userpass, userpass1)) {
			CustomMessage.showToast(context, "两次密码不一致！", Gravity.CENTER, 0);
			return;
		}
		
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 0);
			return;
		}
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				state = personal.registered(username, userpass, useremail);
				handler.sendEmptyMessage(1);
			}
		});
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
			case R.id.reg_return:
				finish();
				break;
			// 注册
			case R.id.reg_button:
				doReg();
				break;
			// 以下 四个用于监听文本框是否显示删除图片的
			case R.id.reg_userid_etxt:
				if (reg_userid_etxt.length() > 1) {
					reg_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return;
				} else {
					reg_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
				}
				break;
			case R.id.reg_password_etxt:
				if (reg_password_etxt.length() > 1) {
					reg_password_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return;
				} else {
					reg_password_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
				}
				break;
			case R.id.reg_email_etxt:
				if (reg_email_etxt.length() > 1) {
					reg_email_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return;
				} else {
					reg_email_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
				}
				break;
			case R.id.reg_password_after_etxt:
				if (reg_password_after_etxt.length() > 1) {
					reg_password_after_etxt
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, c, null);
					return;
				} else {
					reg_password_after_etxt
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, null, null);
				}
				break;

			default:
				break;
			}
		}
	};

	// 监听账号输入框 显示 X图片
	private TextWatcher username_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				reg_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, c, null);
				return;
			} else {
				reg_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, null, null);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};
	// 触屏 删除账号
	private OnTouchListener username_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(reg_userid_etxt.getText())) {
					reg_userid_etxt.setText("");
					// login_password_etxt.setText("");
					reg_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					int cacheInputType = reg_userid_etxt.getInputType();
					reg_userid_etxt.setInputType(InputType.TYPE_NULL);
					reg_userid_etxt.onTouchEvent(event);
					reg_userid_etxt.setInputType(cacheInputType);
					reg_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听密码输入框 显示 X图片
	private TextWatcher userpass_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				reg_password_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, c, null);
				return;
			} else {
				reg_password_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, null, null);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};
	// 触屏 删除密码
	private OnTouchListener userpass_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(reg_password_etxt.getText())) {
					reg_password_etxt.setText("");
					// login_password_etxt.setText("");
					reg_password_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					int cacheInputType = reg_password_etxt.getInputType();
					reg_password_etxt.setInputType(InputType.TYPE_NULL);
					reg_password_etxt.onTouchEvent(event);
					reg_password_etxt.setInputType(cacheInputType);
					reg_password_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听邮箱输入框 显示 X图片
	private TextWatcher useremail_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				reg_email_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, c, null);
				return;
			} else {
				reg_email_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, null, null);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};
	// 触屏 删除账号
	private OnTouchListener useremail_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(reg_email_etxt.getText())) {
					reg_email_etxt.setText("");
					// login_password_etxt.setText("");
					reg_email_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					int cacheInputType = reg_email_etxt.getInputType();
					reg_email_etxt.setInputType(InputType.TYPE_NULL);
					reg_email_etxt.onTouchEvent(event);
					reg_email_etxt.setInputType(cacheInputType);
					reg_email_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听账号输入框 显示 X图片
	private TextWatcher userpass1_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				reg_password_after_etxt
						.setCompoundDrawablesWithIntrinsicBounds(null, null, c,
								null);
				return;
			} else {
				reg_password_after_etxt
						.setCompoundDrawablesWithIntrinsicBounds(null, null,
								null, null);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};
	// 触屏 删除密码
	private OnTouchListener userpass1_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils
								.isEmpty(reg_password_after_etxt.getText())) {
					reg_password_after_etxt.setText("");
					// login_password_etxt.setText("");
					reg_password_after_etxt
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, c, null);
					int cacheInputType = reg_password_after_etxt.getInputType();
					reg_password_after_etxt.setInputType(InputType.TYPE_NULL);
					reg_password_after_etxt.onTouchEvent(event);
					reg_password_after_etxt.setInputType(cacheInputType);
					reg_password_after_etxt
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, c, null);
					return true;
				}
				break;
			}
			return false;
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
