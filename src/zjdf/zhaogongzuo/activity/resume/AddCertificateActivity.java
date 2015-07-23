package zjdf.zhaogongzuo.activity.resume;

import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加证书
 * 
 * @author Administrator
 * 
 */
public class AddCertificateActivity extends Activity {
	private ImageButton but_return;// 返回
	private Button but_submit;// 保存

	private RelativeLayout rela_time;// 时间
	private TextView txt_time;
	private RelativeLayout rela_name;// 证书名字
	private EditText txt_name;
	private RelativeLayout rela_describe;// 证书描述
	private EditText txt_describe;
	// 时间
	private static final int SHOW_DATAPICK = 0;// 生日日期修改
	private int mYear; // 开始时间
	private int mMonth;
	private int mDay;

	private MyResumeConttroller myResumeConttroller;// 控制器
	private Context context;// 上下文
	private int state;// 返回状态

	// 获取 证书列表传递过来的值
	private String id;// 证书ID
	private String name;// 证书名字
	private String time;// 获得时间
	private String times;// 获得时间月
	private String detail;// 获取描述

	private Drawable drawable = null;// 删除图片

	String year_time; // 年
	String moth_time;// 月

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_certifcate_item);
		context = this;
		myResumeConttroller = new MyResumeConttroller(context);
		id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("name");
		time = getIntent().getStringExtra("time");
		times = getIntent().getStringExtra("times");
		detail = getIntent().getStringExtra("detail");
		initView();
		assignment();
	}

	// 赋值
	private void assignment() {
		if (!StringUtils.isEmpty(id)) {
			txt_name.setText(name);
			txt_time.setText(time + "-" + times);
			txt_describe.setText(detail);
		}

	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			but_submit.setEnabled(true);
			switch (msg.what) {
			case 1:
				if (state == 1) {
					Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show();

					finish();
				} else {
					Toast.makeText(context, "添加失败", Toast.LENGTH_LONG).show();
				}
				break;
			}
		};
	};

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		but_return = (ImageButton) findViewById(R.id.but_return);
		but_submit = (Button) findViewById(R.id.but_submit);

		rela_time = (RelativeLayout) findViewById(R.id.rela_time);
		rela_name = (RelativeLayout) findViewById(R.id.rela_name);
		rela_describe = (RelativeLayout) findViewById(R.id.rela_describe);

		txt_time = (TextView) findViewById(R.id.txt_time);
		txt_name = (EditText) findViewById(R.id.txt_name);
		txt_describe = (EditText) findViewById(R.id.txt_describe);

		drawable = getResources().getDrawable(R.drawable.ic_close_gary);

		rela_time.setOnClickListener(listener);
		rela_name.setOnClickListener(listener);
		rela_describe.setOnClickListener(listener);
		but_submit.setOnClickListener(listener);
		but_return.setOnClickListener(listener);
		txt_name.setOnClickListener(listener);
		txt_describe.setOnClickListener(listener);

		txt_name.addTextChangedListener(username_delect);
		txt_name.setOnTouchListener(username_ontouch);
		txt_describe.addTextChangedListener(describe_delect);
		txt_describe.setOnTouchListener(describe_ontouch);

		txt_time.setOnClickListener(new DateButtonOnClickListener());
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
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
			case R.id.but_return:
				Intent intents = new Intent(context,
						CertificateInformationActivity.class);
				startActivity(intents);
				finish();
				break;
			// 保存
			case R.id.but_submit:
				setData();
				break;
			// 证书名字
			case R.id.txt_name:
				if (txt_name.length() > 1) {
					txt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					txt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 证书描述
			case R.id.txt_describe:
				if (txt_describe.length() > 1) {
					txt_describe.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					txt_describe.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			}
		}
	};

	// 保存数据
	private void setData() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		final String certificate_txt = txt_name.getText().toString();
		final String detail_txt = txt_describe.getText().toString();
		final String str_time = txt_time.getText().toString();
		if (str_time == null || str_time.trim().equals("")) {
			Toast.makeText(context, "时间不能为空!", Toast.LENGTH_LONG).show();
			return;
		}
		if (certificate_txt == null || certificate_txt.trim().equals("")) {
			Toast.makeText(context, "证书内容不能为空", Toast.LENGTH_LONG).show();
			return;
		}

		if (str_time != null) {
			year_time = str_time.substring(0, 4);
			moth_time = str_time.substring(6, 7);
		}
		but_submit.setEnabled(false);

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				state = myResumeConttroller.set_certificate(year_time,
						moth_time, certificate_txt, detail_txt, id);
				handler.sendEmptyMessage(1);
			}
		}.start();

	}

	// 更新生日日期
	private void updateDisplay() {
		txt_time.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay));

	}

	// 日期控件的事件
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	// 选择日期的事件处理
	private class DateButtonOnClickListener implements
			android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			Message msg = new Message();
			if (txt_time.equals((TextView) v)) {
				msg.what = AddCertificateActivity.SHOW_DATAPICK;
			}
			saleHandler.sendMessage(msg);
		}
	}

	// 弹出 Dialog
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 3:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	// 弹出 Dialog 点击事件
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case 3:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	/**
	 * 
	 * 处理日期控件的Handler
	 */
	private Handler saleHandler = new Handler() {
		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AddCertificateActivity.SHOW_DATAPICK:
				showDialog(3);
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
				txt_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
						drawable, null);
				return;
			} else {
				txt_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
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

	// 触屏 删除账号
	private OnTouchListener username_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_name.getText())) {
					txt_name.setText("");
					txt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					int cacheInputType = txt_name.getInputType();
					txt_name.setInputType(InputType.TYPE_NULL);
					txt_name.onTouchEvent(event);
					txt_name.setInputType(cacheInputType);
					txt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	// 监听账号输入框 显示 X图片
	private TextWatcher describe_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				txt_describe.setCompoundDrawablesWithIntrinsicBounds(null,
						null, drawable, null);
				return;
			} else {
				txt_describe.setCompoundDrawablesWithIntrinsicBounds(null,
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
	private OnTouchListener describe_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_describe.getText())) {
					txt_describe.setText("");
					txt_describe.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					int cacheInputType = txt_describe.getInputType();
					txt_describe.setInputType(InputType.TYPE_NULL);
					txt_describe.onTouchEvent(event);
					txt_describe.setInputType(cacheInputType);
					txt_describe.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	/*
	 * (non-Javadoc)
	 * 
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
