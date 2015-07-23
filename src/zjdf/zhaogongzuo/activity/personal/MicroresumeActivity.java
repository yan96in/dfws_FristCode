package zjdf.zhaogongzuo.activity.personal;

import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.MainActivity;
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.resume.OptAddressActivity;
import zjdf.zhaogongzuo.activity.resume.OptJobPositionActivity;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
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
 * 注册 成功时 需 注册 微简历
 * 
 * @author Administrator
 * 
 */
public class MicroresumeActivity extends Activity {
	private ImageButton micro_return;// 返回
	private Button micro_submit;// 提交

	private EditText ed_user_name;// 用户名
	private TextView gender_name;// 性别
	private EditText ed_number_name;// 手机号
	private TextView text_data;// 出生日期
	private TextView intention_name;// 意向地点
	private TextView job_name;// 意向职位
	private EditText height_name;// 身高
	private TextView record_name;// 学历
	private TextView treatment_name;// 薪资

	private RelativeLayout rel_name;// 用户
	private RelativeLayout rel_gender;// 性别
	private RelativeLayout rel_number;// 手机
	private RelativeLayout rel_data;// 出生日期、
	private RelativeLayout rel_intention;// 意向地点
	private RelativeLayout rel_job;// 意向职位
	private RelativeLayout rel_height;
	private RelativeLayout rel_record;// 学历
	private RelativeLayout rel_treatment;// 薪资

	private Context context;// 上下文
	private Drawable c = null;// 删除图片
	private MyResumeConttroller myResumeConttroller;// 控制器
	private EorrerBean state;// 返回状态


	// 日期用的
	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_DATAPICK = 0;
	private int mYear;
	private int mMonth;
	private int mDay;

	/** 工作地点 */
	public static final int REQUEST_AREA = 0x0072;
	private String address_code;
	private String address_value;

	/** 意向职位 */
	public static final int REQUEST_POSITION = 0x0073;
	private String posi_code;
	private String posi_value;

	/** 期望薪资 */
	public static final int REQUEST_DSIRED_SALARY = 0x0077;

	// 薪资返回值
	private String mode_code;
	private String mode_value;
	private String currency_code;
	private String currency_value;
	private String scope_code;
	private String scope_value;

	// 需要转换的
	private String user_data;// 日期
	private String user_record;// 学历
	private int int_degree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_micro_resume);
		context = this;
		MobclickAgent.onEvent(context, "mycenter_microresume_sets");
		myResumeConttroller = new MyResumeConttroller(context);
		initView();

	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			case 7:
				if (state != null && state.status == 1) {
					CustomMessage
							.showToast(context, "保存成功!", Gravity.CENTER, 0);
					finish();
					MainActivity.mainActivity.setTabSelection(2);
				} else {
					Toast.makeText(context,
							(state == null ? "保存失败！" : state.errMsg),
							Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
			}
		};
	};

	// 微简历 提交 判断
	private void doSumbit() {
		final String user_name = ed_user_name.getText().toString();
		final String user_gender = gender_name.getText().toString();
		final String user_number = ed_number_name.getText().toString();
		user_data = text_data.getText().toString();
		final String user_intention = intention_name.getText().toString();
		final String user_job = job_name.getText().toString();
		final String user_height = height_name.getText().toString();
		user_record = record_name.getText().toString();
		final String user_treatment = treatment_name.getText().toString();
		if (user_name == null || user_name.trim().equals("")) {
			CustomMessage.showToast(context, "姓名不能为空！", Gravity.CENTER, 0);
			return;
		}
		if (user_gender == null || gender_name.getText().equals("")) {
			CustomMessage.showToast(context, "性别不能为空！", Gravity.CENTER, 0);
			return;
		}
		if (user_number == null || user_number.trim().equals("")) {
			CustomMessage.showToast(context, "手机号码不能为空！", Gravity.CENTER, 0);
			return;
		}

		if (user_data == null || text_data.getText().equals("")) {
			CustomMessage.showToast(context, "出生日期不能为空！", Gravity.CENTER, 0);
			return;
		}
		if (user_intention == null || intention_name.getText().equals("")) {
			CustomMessage.showToast(context, "意向地点不能为空！", Gravity.CENTER, 0);
			return;
		}
		if (user_job == null || job_name.getText().equals("")) {
			CustomMessage.showToast(context, "意向职位不能为空！", Gravity.CENTER, 0);
			return;
		}

		// 正则表达式 判断 支持汉子和字母和.
		if (!StringUtils.checkVariety(user_name)) {
			CustomMessage.showToast(context, "只支持汉字和字母并且。", Gravity.CENTER, 0);
			return;
		}

		if (!StringUtils.checkPhone(user_number)) {
			CustomMessage.showToast(context, "手机号码格式不正确！", Gravity.CENTER, 0);
			return;
		}

		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 0);
			return;
		}
		// 学历转换
		if (user_record.contains("初中")) {
			int_degree = 1;
		} else if (user_record.contains("高中")) {
			int_degree = 2;
		} else if (user_record.contains("中技")) {
			int_degree = 3;
		} else if (user_record.contains("中专")) {
			int_degree = 4;
		} else if (user_record.contains("大专")) {
			int_degree = 5;
		} else if (user_record.contains("本科")) {
			int_degree = 6;
		} else if (user_record.contains("硕士")) {
			int_degree = 7;
		} else if (user_record.contains("博士")) {
			int_degree = 8;
		}
		// 日期拼接
		user_data = Integer.toString(mYear) + "-"
				+ Integer.toString(mMonth + 1) + "-" + Integer.toString(mDay);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				state = myResumeConttroller.setResume(user_name, user_gender+"",
						user_number, user_data, address_code, posi_code,
						user_height, int_degree + "", user_treatment);
				handler.sendEmptyMessage(7);
			}
		}.start();

	}

	// 初始化
	private void initView() {
		c = getResources().getDrawable(R.drawable.ic_clear_28_28);

		micro_return = (ImageButton) findViewById(R.id.micro_return);
		micro_submit = (Button) findViewById(R.id.micro_submit);
		ed_user_name = (EditText) findViewById(R.id.ed_user_name);
		gender_name = (TextView) findViewById(R.id.gender_name);
		ed_number_name = (EditText) findViewById(R.id.ed_number_name);
		text_data = (TextView) findViewById(R.id.text_data);
		intention_name = (TextView) findViewById(R.id.intention_name);
		job_name = (TextView) findViewById(R.id.job_name);
		height_name = (EditText) findViewById(R.id.height_name);
		record_name = (TextView) findViewById(R.id.record_name);
		treatment_name = (TextView) findViewById(R.id.treatment_name);

		rel_name = (RelativeLayout) findViewById(R.id.rel_name);
		rel_gender = (RelativeLayout) findViewById(R.id.rel_gender);
		rel_number = (RelativeLayout) findViewById(R.id.rel_number);
		rel_data = (RelativeLayout) findViewById(R.id.rel_data);
		rel_intention = (RelativeLayout) findViewById(R.id.rel_intention);
		rel_job = (RelativeLayout) findViewById(R.id.rel_job);
		rel_height = (RelativeLayout) findViewById(R.id.rel_height);
		rel_record = (RelativeLayout) findViewById(R.id.rel_record);
		rel_treatment = (RelativeLayout) findViewById(R.id.rel_treatment);

		micro_submit.setOnClickListener(listener);

		// 日期控件
		text_data.setOnClickListener(new DateButtonOnClickListener());
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// 添加事件
		ed_user_name.setOnClickListener(listener);
		ed_number_name.setOnClickListener(listener);
		micro_return.setOnClickListener(listener);

		rel_gender.setOnClickListener(listener);
		rel_data.setOnClickListener(listener);
		rel_intention.setOnClickListener(listener);
		rel_job.setOnClickListener(listener);
		rel_record.setOnClickListener(listener);
		rel_treatment.setOnClickListener(listener);

		// 触屏事件
		ed_user_name.addTextChangedListener(username_delect);
		ed_user_name.setOnTouchListener(username_ontouch);
		ed_number_name.addTextChangedListener(number_delect);
		ed_number_name.setOnTouchListener(number_ontouch);

	}

	// 对话框
	private void doReturn() {
		new AlertDialog.Builder(this).setTitle("确认对话框")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("您还没有保存当前内容，确定退出吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.mainActivity.setTabSelection(2);
						finish();
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作
					}
				}).show();
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
			// 用户名
			case R.id.ed_user_name:
				if (ed_user_name.length() > 1) {
					ed_user_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, c, null);
					return;
				} else {
					ed_user_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 手机号
			case R.id.ed_number_name:
				if (ed_number_name.length() > 1) {
					ed_number_name.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return;
				} else {
					ed_number_name.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
				}
				break;
			// 返回
			case R.id.micro_return:
				doReturn();
				break;
			// 提交
			case R.id.micro_submit:
				doSumbit();
				break;

			// 性别
			case R.id.rel_gender:
				Intent intent_SalaryActivity = new Intent(context,
						GenderActivity.class);
				startActivityForResult(intent_SalaryActivity, 1);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 出生日期
			case R.id.rel_data:

				break;
			// 意向地点
			case R.id.rel_intention:

				Intent intent = new Intent(context, OptAddressActivity.class);
				intent.putExtra("request", REQUEST_AREA);
				startActivityForResult(intent, REQUEST_AREA);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 意向职位
			case R.id.rel_job:
				intent = new Intent(context, OptJobPositionActivity.class);
				intent.putExtra("request", REQUEST_POSITION);
				startActivityForResult(intent, REQUEST_POSITION);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 学历
			case R.id.rel_record:
				Intent intent_RecordActivity = new Intent(context,
						RecordActivity.class);
				startActivityForResult(intent_RecordActivity, 2);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 期望薪资
			case R.id.rel_treatment:
				intent = new Intent(context, OptSalarysActivity.class);
				intent.putExtra("request", REQUEST_DSIRED_SALARY);
				startActivityForResult(intent, REQUEST_DSIRED_SALARY);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			}

		}
	};

	// 接收返回值
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 性别
		case 1:
			if (data == null || "".equals(data)) {
				return;
			} else {
				gender_name.setText(data.getStringExtra("Gender"));
			}
			break;
		// 学历
		case 2:
			if (data == null || "".equals(data)) {
				return;
			} else {
				record_name.setText(data.getStringExtra("Record"));
			}
			break;
		// 意向地点
		case REQUEST_AREA:
			if (data == null || "".equals(data)) {
				intention_name.setText("");
				return;
			} else {
				address_code = data.getStringExtra("code");
				address_value = data.getStringExtra("value");
				intention_name.setText(address_value);
			}

			break;
		// 意向职位
		case REQUEST_POSITION:
			if (data == null || "".equals(data)) {
				job_name.setText("");
				return;
			} else {
				posi_code = data.getStringExtra("code");
				posi_value = data.getStringExtra("value");
				job_name.setText(posi_value);
			}
			break;

		// 期望薪资
		case REQUEST_DSIRED_SALARY:
			if (data == null || "".equals(data)) {
				return;
			} else {
				mode_code = data.getStringExtra("mode_code");
				mode_value = data.getStringExtra("mode_value");
				currency_code = data.getStringExtra("currency_code");
				currency_value = data.getStringExtra("currency_value");
				scope_code = data.getStringExtra("scope_code");
				scope_value = data.getStringExtra("scope_value");
				treatment_name.setText(scope_value);
			}
			break;
		}
	};

	// 监听 显示 X图片
	private TextWatcher username_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				ed_user_name.setCompoundDrawablesWithIntrinsicBounds(null,
						null, c, null);
				return;
			} else {
				ed_user_name.setCompoundDrawablesWithIntrinsicBounds(null,
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
	// 触屏
	private OnTouchListener username_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(ed_user_name.getText())) {
					ed_user_name.setText("");
					ed_user_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, c, null);
					int cacheInputType = ed_user_name.getInputType();
					ed_user_name.setInputType(InputType.TYPE_NULL);
					ed_user_name.onTouchEvent(event);
					ed_user_name.setInputType(cacheInputType);
					ed_user_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, c, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听显示 X图片
	private TextWatcher number_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				ed_number_name.setCompoundDrawablesWithIntrinsicBounds(null,
						null, c, null);
				return;
			} else {
				ed_number_name.setCompoundDrawablesWithIntrinsicBounds(null,
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
	// 触屏
	private OnTouchListener number_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(ed_number_name.getText())) {
					ed_number_name.setText("");
					ed_number_name.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					int cacheInputType = ed_number_name.getInputType();
					ed_number_name.setInputType(InputType.TYPE_NULL);
					ed_number_name.onTouchEvent(event);
					ed_number_name.setInputType(cacheInputType);
					ed_number_name.setCompoundDrawablesWithIntrinsicBounds(
							null, null, c, null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	// 更新日期
	private void updateDisplay() {
		text_data.setText(new StringBuilder()
				.append(mYear + "年")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1 + "月")
						: (mMonth + 1) + "月")
				.append((mDay < 10) ? "0" + mDay : mDay)
				+ "日");
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
			if (text_data.equals((TextView) v)) {
				msg.what = MicroresumeActivity.SHOW_DATAPICK;
			}
			MicroresumeActivity.this.saleHandler.sendMessage(msg);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	/**
	 * 
	 * 处理日期控件的Handler
	 */

	private Handler saleHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MicroresumeActivity.SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			}
		}
	};
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			doReturn();
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
