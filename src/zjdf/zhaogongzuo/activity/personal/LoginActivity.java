package zjdf.zhaogongzuo.activity.personal;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseActivity {
	private AutoCompleteTextView login_userid_etxt;// 用户名
	private AutoCompleteTextView login_password_etxt;// 密码
	private Button login_loginbtn;// 登录
	private Context context;// 上下文
	private PersonalController personalControllers;// 控制器
	private EorrerBean state;
	private TextView login_reg;// 注册
	// private LinearLayout login_forget;// 忘记密码
	private Button login_qq;// qq
	private Button login_sina;// sina
	private Drawable drawable = null;// 删除图片
	private SharedPreferences sp = null;// 保存账号密码
	private String username;// 账号
	private String userpass;// 密码
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_login);
		context = this;
		personalControllers = new PersonalController(context);
		sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		initView();

	}

	// 刷新UI
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				if (state!=null&&state.status == 1) {
					CustomMessage.showToast(context, "登录成功!", Gravity.CENTER,0);
//					personalControllers.CloseDB();
					finish();
				} else {
					Toast.makeText(context, (state==null?"登录失败！":state.errMsg), Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
			}
		};

	};

	// 初始化
	private void initView() {
		login_userid_etxt = (AutoCompleteTextView) findViewById(R.id.login_userid_etxt);
		login_password_etxt = (AutoCompleteTextView) findViewById(R.id.login_password_etxt);
		login_reg = (TextView) findViewById(R.id.login_reg);
		login_loginbtn = (Button) findViewById(R.id.login_loginbtn);
		login_qq = (Button) findViewById(R.id.login_qq);
		login_sina = (Button) findViewById(R.id.login_sina);
		drawable = getResources().getDrawable(R.drawable.ic_close_gary);

		// 点击事件
		login_userid_etxt.setOnClickListener(listener);
		login_password_etxt.setOnClickListener(listener);
		login_sina.setOnClickListener(listener);
		login_qq.setOnClickListener(listener);
		login_reg.setOnClickListener(listener);
		login_loginbtn.setOnClickListener(listener);

		// 特殊点击事件
		login_userid_etxt.addTextChangedListener(username_delect);
		login_userid_etxt.setOnTouchListener(username_ontouch);
		login_password_etxt.addTextChangedListener(userpass_delect);
		login_password_etxt.setOnTouchListener(userpass_ontouch);

		//dialogSina = new DialogSina(context);
	}

	// 处理登录
	private void doLogin() {
		username = login_userid_etxt.getText().toString();
		userpass = login_password_etxt.getText().toString();
		if (StringUtils.isEmpty(username)) {
			CustomMessage.showToast(context, "用户名不能为空!", Gravity.CENTER, 0);
			return;
		}
		if (StringUtils.isEmpty(userpass)) {
			CustomMessage.showToast(context, "密码不能为空!", Gravity.CENTER, 0);
			return;
		}
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "网络已断开，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				state = personalControllers.checkLogin(username, userpass);
				handler.sendEmptyMessage(2);
			}
		});
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			listener(v);
		}

		private void listener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 登录
			case R.id.login_loginbtn:
				doLogin();
				break;
			// 注册
			case R.id.login_reg:
				startActivity(new Intent(context, RegisteredActivity.class));
				finish();
				break;
			// QQ登录
			case R.id.login_qq:
				startActivity(new Intent(context,AccountbindingQQActivity.class));
				finish();

				break;
			// sina登录
			case R.id.login_sina:
				 startActivity(new Intent(context,AccountbindingSinaActivity.class));
			  //	dialogSina.show();
				 finish();
				break;
			// 账号
			case R.id.login_userid_etxt:
				if (login_userid_etxt.length() > 1) {
					login_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					return;
				} else {
					login_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
				}
				break;
			// 密码
			case R.id.login_password_etxt:
				if (login_password_etxt.length() > 1) {
					login_password_etxt
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, drawable, null);
					return;
				} else {
					login_password_etxt
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
				login_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
						null, drawable, null);
				return;
			} else {
				login_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(null,
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
	// 监听 判断密码 显示 X图片
	private TextWatcher userpass_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				login_password_etxt.setCompoundDrawablesWithIntrinsicBounds(
						null, null, drawable, null);
				return;
			} else {
				login_password_etxt.setCompoundDrawablesWithIntrinsicBounds(
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
						&& !TextUtils.isEmpty(login_userid_etxt.getText())) {
					login_userid_etxt.setText("");
					login_password_etxt.setText("");
					login_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					int cacheInputType = login_userid_etxt.getInputType();
					login_userid_etxt.setInputType(InputType.TYPE_NULL);
					login_userid_etxt.onTouchEvent(event);
					login_userid_etxt.setInputType(cacheInputType);
					login_userid_etxt.setCompoundDrawablesWithIntrinsicBounds(
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
						&& !TextUtils.isEmpty(login_password_etxt.getText())) {
					login_password_etxt.setText("");
					login_password_etxt
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, drawable, null);
					int cacheInputType = login_password_etxt.getInputType();
					login_password_etxt.setInputType(InputType.TYPE_NULL);
					login_password_etxt.onTouchEvent(event);
					login_password_etxt.setInputType(cacheInputType);
					login_password_etxt
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, drawable, null);
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
