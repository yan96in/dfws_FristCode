package zjdf.zhaogongzuo.activity.personal;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.entity.EorrerBean;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 账号绑定
 * 
 * @author Administrator
 * 
 */
public class AccountbindingActivity extends Activity {

	private LinearLayout line_not;// 注册并且绑定
	private LinearLayout lin_has;// 只绑定
	private Button login_not_button;// 注册并且绑定
	private Button login_has_button;// 只绑定
	// 接收 第三方登录界面传递过来的数据
	private String connect_cooperate;// 接收参数
	private String connect_code;// 接收参数

	// 只登录
	private EditText login_has_username;// 用户名
	private EditText login_has_password;// 密码
	private Button login_has_submit;// 登录

	// 注册并且绑定
	private EditText login_not_username;// 用户名
	private EditText login_not_email;// 邮箱
	private EditText login_not_password;// 密码
	private EditText login_not_password_after;// 二次密码
	private Button login_not_submit;// 注册并且绑定
	private PersonalController personalControllers;// 控制器
	private ImageButton login_return;// 返回

	private Context context;// 上下文
	private EorrerBean state;// 返回状态

	private Drawable drawable = null;// 删除图片

	private int stauts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_accountbinding);
		context = this;
		personalControllers = new PersonalController(context);
		initView();
	}

	// 刷新 界面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 3:
				if (state.errCode == 0) {
					Toast.makeText(context, "注册成功!", Toast.LENGTH_LONG).show();
					finish();
					return;
				} else {
					stauts = state.errCode;
					getStatus();
				}
				break;

			case 4:
				if (state.errCode == 0) {
					Toast.makeText(context, "绑定成功!", Toast.LENGTH_LONG).show();
					finish();
					return;
				} else {
					stauts = state.errCode;
					getStatus();
				}
				break;
			}
		};
	};

	// 初始化
	private void initView() {
		// 接收参数
		Intent intent = getIntent();
		connect_cooperate = intent.getStringExtra("connect_cooperate");
		connect_code = intent.getStringExtra("connect_code");

		// 两个 不同窗口
		line_not = (LinearLayout) findViewById(R.id.line_not);
		lin_has = (LinearLayout) findViewById(R.id.lin_has);
		// 点击显示不同的 布局
		login_not_button = (Button) findViewById(R.id.login_not_button);
		login_has_button = (Button) findViewById(R.id.login_has_button);

		login_return = (ImageButton) findViewById(R.id.login_return);

		// 只登陆
		login_has_username = (EditText) findViewById(R.id.login_has_username);
		login_has_password = (EditText) findViewById(R.id.login_has_password);
		login_has_submit = (Button) findViewById(R.id.login_has_submit);

		// 注册并且绑定
		login_not_username = (EditText) findViewById(R.id.login_not_username);
		login_not_email = (EditText) findViewById(R.id.login_not_email);
		login_not_password = (EditText) findViewById(R.id.login_not_password);
		login_not_password_after = (EditText) findViewById(R.id.login_not_password_after);
		login_not_submit = (Button) findViewById(R.id.login_not_submit);

		// 点击事件
		login_not_button.setOnClickListener(listener);
		login_has_button.setOnClickListener(listener);
		login_has_submit.setOnClickListener(listener);
		login_not_submit.setOnClickListener(listener);
		login_return.setOnClickListener(listener);

		drawable = getResources().getDrawable(R.drawable.ic_close_gary);
		// 点击触屏删除正行文字
		login_has_username.addTextChangedListener(username_delect);
		login_has_username.setOnTouchListener(username_ontouch);
		login_has_password.addTextChangedListener(userpass_delect);
		login_has_password.setOnTouchListener(userpass_ontouch);

		login_not_username.addTextChangedListener(username_delects);
		login_not_username.setOnTouchListener(username_ontouchs);
		login_not_password.addTextChangedListener(userpass_delects);
		login_not_password.setOnTouchListener(userpass_ontouchs);
		login_not_email.addTextChangedListener(useremail_delect);
		login_not_email.setOnTouchListener(useremail_ontouch);
		login_not_password_after.addTextChangedListener(userpass1_delect);
		login_not_password_after.setOnTouchListener(userpass1_ontouch);

	}

	// 点击事件
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			listener(v);
		}

		private void listener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 注册并且绑定
			case R.id.login_not_button:
				lin_has.setVisibility(View.GONE);
				line_not.setVisibility(View.VISIBLE);

				break;
			// 只绑定
			case R.id.login_has_button:
				line_not.setVisibility(View.GONE);
				lin_has.setVisibility(View.VISIBLE);
				break;

			// 只绑定
			case R.id.login_has_submit:
				doLogin();
				break;
			// 注册 并且绑定
			case R.id.login_not_submit:
				doReg();
				break;
			// 返回
			case R.id.login_return:
				finish();
				break;
			default:
				break;
			}
		}

		// 注册并且绑定
		private void doReg() {
			final String username = login_not_username.getText().toString();
			final String userpass = login_not_password.getText().toString();
			final String useremail = login_not_email.getText().toString();
			final String userpass1 = login_not_password_after.getText()
					.toString();
			if (username == null || username.trim().equals("")) {
				Toast.makeText(context, "用户名不能为空！", Toast.LENGTH_LONG).show();
				return;
			}
			if (userpass == null || userpass.trim().equals("")) {
				Toast.makeText(context, "密码不能为空！", Toast.LENGTH_LONG).show();
				return;
			}
			if (useremail == null || userpass.trim().equals("")) {
				Toast.makeText(context, "邮箱不能为空！", Toast.LENGTH_LONG).show();
				return;
			}
			if (!StringUtils.checkEmail(useremail)) {
				Toast.makeText(context, "邮箱格式不正确！", Toast.LENGTH_LONG).show();
				return;
			}
			if (!StringUtils.equals(userpass, userpass1)) {
				Toast.makeText(context, "两次密码不一致！", Toast.LENGTH_LONG).show();
				return;
			}
			if (userpass.length()<6) {
				Toast.makeText(context, "密码不能少于六位！", Toast.LENGTH_LONG).show();
				return;
			}
			if (userpass1.length()<6) {
				Toast.makeText(context, "密码不能少于六位！", Toast.LENGTH_LONG).show();
				return;
			}

			new Thread() {
				@Override
				public void run() {
					super.run();
					state = personalControllers.regBangding(username, userpass,
							useremail, connect_cooperate, connect_code);
					handler.sendEmptyMessage(3);
				}
			}.start();

		}

		// 只登录
		private void doLogin() {
			final String username = login_has_username.getText().toString();
			final String userpass = login_has_password.getText().toString();
			if (username == null || username.trim().equals("")) {
				handler.sendEmptyMessage(1);
				return;
			}
			if (userpass == null || userpass.trim().equals("")) {
				handler.sendEmptyMessage(2);
				return;
			}
			new Thread() {
				@Override
				public void run() {
					super.run();
					state = personalControllers.checkLoginBangding(username,
							userpass, connect_cooperate, connect_code);
					handler.sendEmptyMessage(4);
				}
			}.start();
		}
	};

	// 监听账号输入框 显示 X图片
	private TextWatcher username_delects = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				login_not_username.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
				return;
			} else {
				login_not_username.setCompoundDrawablesWithIntrinsicBounds(
						null, null, null, null);
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
	private OnTouchListener username_ontouchs = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(login_not_username.getText())) {
					login_not_username.setText("");
					// login_password_etxt.setText("");
					login_not_username.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					int cacheInputType = login_not_username.getInputType();
					login_not_username.setInputType(InputType.TYPE_NULL);
					login_not_username.onTouchEvent(event);
					login_not_username.setInputType(cacheInputType);
					login_not_username.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听密码输入框 显示 X图片
	private TextWatcher userpass_delects = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				login_not_password.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
				return;
			} else {
				login_not_password.setCompoundDrawablesWithIntrinsicBounds(
						null, null, null, null);
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
	private OnTouchListener userpass_ontouchs = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(login_not_password.getText())) {
					login_not_password.setText("");
					// login_password_etxt.setText("");
					login_not_password.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					int cacheInputType = login_not_password.getInputType();
					login_not_password.setInputType(InputType.TYPE_NULL);
					login_not_password.onTouchEvent(event);
					login_not_password.setInputType(cacheInputType);
					login_not_password.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
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
				login_not_email.setCompoundDrawablesWithIntrinsicBounds(null,
						null, drawable, null);
				return;
			} else {
				login_not_email.setCompoundDrawablesWithIntrinsicBounds(null,
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
						&& !TextUtils.isEmpty(login_not_email.getText())) {
					login_not_email.setText("");
					// login_password_etxt.setText("");
					login_not_email.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					int cacheInputType = login_not_email.getInputType();
					login_not_email.setInputType(InputType.TYPE_NULL);
					login_not_email.onTouchEvent(event);
					login_not_email.setInputType(cacheInputType);
					login_not_email.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
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
				login_not_password_after
						.setCompoundDrawablesWithIntrinsicBounds(null, null,
								drawable, null);
				return;
			} else {
				login_not_password_after
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
						&& !TextUtils.isEmpty(login_not_password_after
								.getText())) {
					login_not_password_after.setText("");
					// login_password_etxt.setText("");
					login_not_password_after
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, drawable, null);
					int cacheInputType = login_not_password_after
							.getInputType();
					login_not_password_after.setInputType(InputType.TYPE_NULL);
					login_not_password_after.onTouchEvent(event);
					login_not_password_after.setInputType(cacheInputType);
					login_not_password_after
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// ===================================================================================================================
	// 监听账号输入框 显示 X图片
	private TextWatcher username_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				login_has_username.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
				return;
			} else {
				login_has_username.setCompoundDrawablesWithIntrinsicBounds(
						null, null, null, null);
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
	// 监听 判断密码 显示 X图片
	private TextWatcher userpass_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				login_has_password.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
				return;
			} else {
				login_has_password.setCompoundDrawablesWithIntrinsicBounds(
						null, null, null, null);
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
						&& !TextUtils.isEmpty(login_has_username.getText())) {
					login_has_username.setText("");
					login_has_password.setText("");
					login_has_username.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					int cacheInputType = login_has_username.getInputType();
					login_has_username.setInputType(InputType.TYPE_NULL);
					login_has_username.onTouchEvent(event);
					login_has_username.setInputType(cacheInputType);
					login_has_username.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					return true;
				}
				break;
			}
			return false;
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
						&& !TextUtils.isEmpty(login_has_password.getText())) {
					login_has_password.setText("");
					login_has_password.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					int cacheInputType = login_has_password.getInputType();
					login_has_password.setInputType(InputType.TYPE_NULL);
					login_has_password.onTouchEvent(event);
					login_has_password.setInputType(cacheInputType);
					login_has_password.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	// 去的返回状态
	private void getStatus() {
		if (stauts == 1001) {
			Toast.makeText(context, "用户(id)不存在", Toast.LENGTH_LONG).show();
		} else if (stauts == 1002) {
			Toast.makeText(context, "用户(id)存在", Toast.LENGTH_LONG).show();
		} else if (stauts == 1010) {
			Toast.makeText(context, "用户名为空", Toast.LENGTH_LONG).show();
		} else if (stauts == 1011) {
			Toast.makeText(context, "用户名不存在", Toast.LENGTH_LONG).show();
		} else if (stauts == 1012) {
			Toast.makeText(context, "用户名已存在", Toast.LENGTH_LONG).show();
		} else if (stauts == 1019) {
			Toast.makeText(context, "用户名不符合规则(3-40位数限制等)", Toast.LENGTH_LONG)
					.show();
		} else if (stauts == 1020) {
			Toast.makeText(context, "邮箱为空", Toast.LENGTH_LONG).show();
		} else if (stauts == 1021) {
			Toast.makeText(context, "邮箱格式不对", Toast.LENGTH_LONG).show();
		} else if (stauts == 1022) {
			Toast.makeText(context, "邮箱已存在", Toast.LENGTH_LONG).show();
		} else if (stauts == 1029) {
			Toast.makeText(context, "邮箱格式不对", Toast.LENGTH_LONG).show();
		} else if (stauts == 1033) {
			Toast.makeText(context, "密码不正确(登录时候的判断)", Toast.LENGTH_LONG).show();
		} else if (stauts == 7102) {
			Toast.makeText(context, "QQ互联Openid已绑定用户", Toast.LENGTH_LONG)
					.show();
		} else if (stauts == 7202) {
			Toast.makeText(context, "weibo同步登录已绑定用户", Toast.LENGTH_LONG).show();
		} else if (stauts == 1722) {
			Toast.makeText(context, "已绑定weibo同步登录", Toast.LENGTH_LONG).show();
		} else if (stauts == 1721) {
			Toast.makeText(context, "未绑定weibo同步登录", Toast.LENGTH_LONG).show();
		} else if (stauts == 1711) {
			Toast.makeText(context, "未绑定qq互联", Toast.LENGTH_LONG).show();
		} else if (stauts == 1712) {
			Toast.makeText(context, "已绑定qq互联", Toast.LENGTH_LONG).show();
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
