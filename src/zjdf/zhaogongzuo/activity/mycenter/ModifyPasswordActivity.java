/**
 * Copyright © 2014年3月27日 FindJob www.veryeast.com
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

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.more.JobPlaceActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.controllers.PersonalController;
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
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 修改密码
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月27日
 * @version
 * @modify
 * 
 */
public class ModifyPasswordActivity extends Activity {
	private ImageButton pass_return;// 返回
	private Button modify_pass_submit;// 保存
	private EditText user_pass_1;// 原始密码
	private EditText user_pass_2;// 原始密码
	private EditText user_pass_3;// 原始密码
	private ApplicationConfig applicationConfig;// 全局控制
	private PersonalController personalControllers;// 控制器
	private Context context;// 上下文
	private int value;// 返回状态
	private Drawable drawable = null;// 删除图片

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycenter_modifypassword);
		context = this;
		applicationConfig = (ApplicationConfig) context.getApplicationContext();
		personalControllers = new PersonalController(context);
		initView();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				CustomMessage.showToast(context, "原始密码不能为空!", Gravity.CENTER, 0);
				break;
			case 2:
				CustomMessage.showToast(context, "密码不能为空!", Gravity.CENTER, 0);
				break;
			case 3:
				CustomMessage.showToast(context, "密码不能为空!", Gravity.CENTER, 0);
				break;
			case 4:
				CustomMessage.showToast(context, "两次密码不一致！", Gravity.CENTER, 0);
				break;
			case 5:
				if (value == 1) {
					CustomMessage.showToast(context, "修改成功，请重新登录！", Gravity.CENTER, 0);
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					CustomMessage.showToast(context, "原始密码不对！", Gravity.CENTER, 0);
				}
				break;

			default:
				break;
			}
		};
	};

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		pass_return = (ImageButton) findViewById(R.id.pass_return);
		modify_pass_submit = (Button) findViewById(R.id.modify_pass_submit);
		user_pass_1 = (EditText) findViewById(R.id.user_pass_1);
		user_pass_2 = (EditText) findViewById(R.id.user_pass_2);
		user_pass_3 = (EditText) findViewById(R.id.user_pass_3);

		drawable = getResources().getDrawable(R.drawable.ic_close_gary);

		// 特殊点击事件
		user_pass_1.addTextChangedListener(pass1_delect);
		user_pass_1.setOnTouchListener(pass1_ontouch);
		user_pass_2.addTextChangedListener(pass2_delect);
		user_pass_2.setOnTouchListener(pass2_ontouch);
		user_pass_3.addTextChangedListener(pass3_delect);
		user_pass_3.setOnTouchListener(pass3_ontouch);

		pass_return.setOnClickListener(listener);
		modify_pass_submit.setOnClickListener(listener);

	}

	// 判断
	private void checkPass() {
		final String pass1 = user_pass_1.getText().toString();
		final String pass2 = user_pass_2.getText().toString();
		final String pass3 = user_pass_3.getText().toString();
		if (pass1 == null || pass1.trim().equals("")) {
			handler.sendEmptyMessage(1);
			return;
		}
		if (pass2 == null || pass1.trim().equals("")) {
			handler.sendEmptyMessage(2);
			return;
		}
		if (pass3 == null || pass1.trim().equals("")) {
			handler.sendEmptyMessage(3);
			return;
		}
		if (!StringUtils.equals(pass2, pass3)) {
			handler.sendEmptyMessage(4);
			return;
		}
		if (user_pass_2.length()<6) {
			 CustomMessage.showToast(context, "密码不能少于六位！", Gravity.CENTER, 0);
			return;
		}
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 0);
			return ;
		}
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				value = personalControllers.changePass(pass1, pass2);
				handler.sendEmptyMessage(5);
			}
		}.start();
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			listener(v);

		}

		private void listener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 返回
			case R.id.pass_return:
				finish();
				break;
			// 保存
			case R.id.modify_pass_submit:
				checkPass();
				break;
			case R.id.user_pass_1:
				if (user_pass_1.length() > 1) {
					user_pass_1.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					user_pass_1.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			case R.id.user_pass_2:
				if (user_pass_2.length() > 1) {
					user_pass_2.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					user_pass_2.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			case R.id.user_pass_3:
				if (user_pass_3.length() > 1) {
					user_pass_3.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					user_pass_3.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			}
		}
	};
	// 监听输入框 显示 X图片
	private TextWatcher pass1_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				user_pass_1.setCompoundDrawablesWithIntrinsicBounds(null, null,
						drawable, null);
				return;
			} else {
				user_pass_1.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	private OnTouchListener pass1_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(user_pass_1.getText())) {
					user_pass_1.setText("");
					user_pass_1.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					int cacheInputType = user_pass_1.getInputType();
					user_pass_1.setInputType(InputType.TYPE_NULL);
					user_pass_1.onTouchEvent(event);
					user_pass_1.setInputType(cacheInputType);
					user_pass_1.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听账号输入框 显示 X图片
	private TextWatcher pass2_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				user_pass_2.setCompoundDrawablesWithIntrinsicBounds(null, null,
						drawable, null);
				return;
			} else {
				user_pass_2.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	private OnTouchListener pass2_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(user_pass_2.getText())) {
					user_pass_2.setText("");
					user_pass_2.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					int cacheInputType = user_pass_2.getInputType();
					user_pass_2.setInputType(InputType.TYPE_NULL);
					user_pass_2.onTouchEvent(event);
					user_pass_2.setInputType(cacheInputType);
					user_pass_2.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听账号输入框 显示 X图片
	private TextWatcher pass3_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				user_pass_3.setCompoundDrawablesWithIntrinsicBounds(null, null,
						drawable, null);
				return;
			} else {
				user_pass_3.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	private OnTouchListener pass3_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(user_pass_3.getText())) {
					user_pass_3.setText("");
					user_pass_3.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					int cacheInputType = user_pass_3.getInputType();
					user_pass_3.setInputType(InputType.TYPE_NULL);
					user_pass_3.onTouchEvent(event);
					user_pass_3.setInputType(cacheInputType);
					user_pass_3.setCompoundDrawablesWithIntrinsicBounds(null,
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
