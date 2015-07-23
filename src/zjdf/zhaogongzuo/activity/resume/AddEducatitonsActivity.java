package zjdf.zhaogongzuo.activity.resume;

import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.personal.RecordActivity;
import zjdf.zhaogongzuo.controllers.ResumeInformationControllers;
import zjdf.zhaogongzuo.entity.ResumeEducation;
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
 * 添加教育教育经历
 * 
 * @author Administrator
 * 
 */
public class AddEducatitonsActivity extends Activity {
	private ImageButton image_but;// 返回
	private Button txt_posit;// 保存

	private RelativeLayout rela_edu;// 学历
	private TextView txt_edu;
	private RelativeLayout rela_time_beng;// 开始时间
	private TextView txt_time;
	private RelativeLayout rela_time_end;// 结束时间
	private TextView txt_time_end;
	private RelativeLayout rela_edus;// 学校
	private EditText txt_eds_name;
	private RelativeLayout rela_local;// 所在城市
	private TextView txt_local;
	private RelativeLayout rela_professional;// 专业类别
	private TextView txt_professional;
	private RelativeLayout rela_learning;// 是够海外学习
	private TextView txt_learning;

	private Context context;// 上下文
	private ResumeInformationControllers resumeInformationControllers;// 控制器

	// 时间
	private static final int SHOW_DATAPICK = 0;// 生日日期修改
	private static final int SHOW_DATAPICKS = 1;// 毕业日期修改
	private int mYear; // 开始时间
	private int mMonth;
	private int mDay;

	private int mYears; // 结束时间
	private int mMonths;
	private int mDays;

	/** 地址编码 */
	public static String areaCode;
	/** 地址 */
	public static String areaValue;

	// 专业类别
	public static String major_Key;// 编码
	public static String major_Value;

	private int int_degree;// 学历整数
	private int int_is;// 是否海外学习
	// 教育对象
	private ResumeEducation education;
	/** 专业类型 */
	public static final int REQUEST_WORK_MODE = 0x0071;

	private int state;// 返回状态

	// 接收列表传递过来的 数据
	private String id;// 教育ID
	private String name;// 学历名字
	private String names;// 学校名字
	private String area;// 所在城市
	private String edu;// 海外学习经历
	private String beng_time;// 开始学习时间
	private String eng_time;// 结束学习时间
	private String beng_times;// 开始学习时间
	private String eng_times;// 结束学习时间
	private String areaCodes;// 城市code
	private String major_value;// 专业类别
	private String major_key;// 专业类别key

	private Drawable drawable = null;// 删除图片

	String year_time; // 年
	String moth_time;// 月
	String year_times; // 年
	String moth_times;// 月

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myresume_educations_add);
		context = this;
		resumeInformationControllers = new ResumeInformationControllers(context);
		education = new ResumeEducation();
		id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("name");
		names = getIntent().getStringExtra("names");
		area = getIntent().getStringExtra("area");
		areaCodes = getIntent().getStringExtra("areaCode");
		major_value = getIntent().getStringExtra("major");
		major_key = getIntent().getStringExtra("major_key");
		edu = getIntent().getStringExtra("edu");
		beng_time = getIntent().getStringExtra("beng_time");
		eng_time = getIntent().getStringExtra("eng_time");
		beng_times = getIntent().getStringExtra("beng_times");
		eng_times = getIntent().getStringExtra("eng_times");

		initView();
		assignment();
	}

	// 赋值
	private void assignment() {
		// TODO Auto-generated method stub
		if (id != null) {
			txt_edu.setText(name);
			txt_time.setText(beng_time + "-" + beng_times);
			txt_time_end.setText(eng_time + "-" + eng_times);
			txt_eds_name.setText(names);
			txt_local.setText(area);
			txt_professional.setText(major_value);
			int number = Integer.parseInt(edu);
			if (number == 0) {
				txt_learning.setText("否");
			} else if (number == 1) {
				txt_learning.setText("是");
			}

		}
	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			txt_posit.setEnabled(true);
			switch (msg.what) {
			case 1:
				if (state == 1) {
					Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(context, "保存失败", Toast.LENGTH_LONG).show();
				}
				break;
			}
		};
	};

	// 保存
	private void doData() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		// 学历
		String degeree = txt_edu.getText().toString();
		final String str_name = txt_eds_name.getText().toString();
		final String str_beng_time = txt_time.getText().toString();
		final String str_eng_time = txt_time_end.getText().toString();
		final String str_professional = txt_professional.getText().toString();
		String str_learning = txt_learning.getText().toString();

		if (degeree.contains("初中")) {
			int_degree = 1;
		} else if (degeree.contains("高中")) {
			int_degree = 2;
		} else if (degeree.contains("中技")) {
			int_degree = 3;
		} else if (degeree.contains("中专")) {
			int_degree = 4;
		} else if (degeree.contains("大专")) {
			int_degree = 5;
		} else if (degeree.contains("本科")) {
			int_degree = 6;
		} else if (degeree.contains("硕士")) {
			int_degree = 7;
		} else if (degeree.contains("博士")) {
			int_degree = 8;
		}

		if (str_learning.contains("是")) {
			int_is = 1;
		} else if (str_learning.contains("否")) {
			int_is = 0;
		}
		if (degeree == null || degeree.trim().equals("")) {
			Toast.makeText(context, "学历不能为空！", Toast.LENGTH_LONG).show();
			return;

		}
		if (str_beng_time == null || str_beng_time.trim().equals("")) {
			Toast.makeText(context, "开始时间不能为空！！", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_eng_time == null || str_eng_time.trim().equals("")) {
			Toast.makeText(context, "结束时间不能为空！！", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_name == null || str_name.trim().equals("")) {
			Toast.makeText(context, "学校名字不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		// 正则表达式 判断 支持汉子和字母和.
		if (!StringUtils.checkVariety(str_name)) {
			Toast.makeText(context, "只支持汉字和字母并且.", Toast.LENGTH_LONG).show();
			return;
		}
		/*
		 * // 正则表达式 判断 支持汉子和字母和. if (!StringUtils.checkVariety(str_learning)) {
		 * Toast.makeText(context, "只支持汉字和字母并且。", Toast.LENGTH_LONG).show();
		 * return; }
		 */
		/*
		 * if (areaCode == null || areaCode.trim().equals("")) {
		 * Toast.makeText(context, "所在城市不能为空！", Toast.LENGTH_LONG).show();
		 * return; }
		 */
		if (str_professional == null || str_professional.trim().equals("")) {
			Toast.makeText(context, "专业类别不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_beng_time != null) {
			year_time = str_beng_time.substring(0, 4);
			moth_time = str_beng_time.substring(6, 7);
		}
		if (str_eng_time != null) {
			year_times = str_eng_time.substring(0, 4);
			moth_times = str_eng_time.substring(6, 7);
		}
		if (areaCode == null) {
			areaCode = areaCodes;
		}
		if (major_Key == null) {
			major_Key = major_key;
		}
		education.id = id;
		education.edu_degree_key = int_degree + "";
		education.start_time_year = year_time;
		education.start_time_month = moth_time;
		education.end_time_year = year_times;
		education.end_time_month = moth_times;
		education.school_value = str_name;
		education.area_key = areaCode;
		education.study_abroad_key = int_is + "";
		education.major_key = major_Key;
		education.major_value = major_Value;

		txt_posit.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				state = resumeInformationControllers.setJobEducation(education);
				handler.sendEmptyMessage(1);
			}
		}.start();

	}

	// 初始化
	private void initView() {
		image_but = (ImageButton) findViewById(R.id.image_but);
		txt_posit = (Button) findViewById(R.id.txt_posit);

		rela_edu = (RelativeLayout) findViewById(R.id.rela_edu);
		txt_edu = (TextView) findViewById(R.id.txt_edu);
		rela_time_beng = (RelativeLayout) findViewById(R.id.rela_time_beng);
		txt_time = (TextView) findViewById(R.id.txt_time);
		rela_time_end = (RelativeLayout) findViewById(R.id.rela_time_end);
		txt_time_end = (TextView) findViewById(R.id.txt_time_end);
		rela_edus = (RelativeLayout) findViewById(R.id.rela_edus);
		txt_eds_name = (EditText) findViewById(R.id.txt_eds_name);
		rela_local = (RelativeLayout) findViewById(R.id.rela_local);
		txt_local = (TextView) findViewById(R.id.txt_local);
		rela_professional = (RelativeLayout) findViewById(R.id.rela_professional);
		txt_professional = (TextView) findViewById(R.id.txt_professional);
		rela_learning = (RelativeLayout) findViewById(R.id.rela_learning);
		txt_learning = (TextView) findViewById(R.id.txt_learning);

		drawable = getResources().getDrawable(R.drawable.ic_close_gary);

		rela_learning.setOnClickListener(clickListener);
		image_but.setOnClickListener(clickListener);
		txt_posit.setOnClickListener(clickListener);
		rela_professional.setOnClickListener(clickListener);
		rela_local.setOnClickListener(clickListener);
		rela_edu.setOnClickListener(clickListener);
		txt_eds_name.setOnClickListener(clickListener);
		txt_learning.setOnClickListener(clickListener);

		txt_eds_name.addTextChangedListener(username_delect);
		txt_eds_name.setOnTouchListener(username_ontouch);

		/*
		 * txt_learning.addTextChangedListener(describe_delect);
		 * txt_learning.setOnTouchListener(describe_ontouch);
		 */

		txt_time.setOnClickListener(new DateButtonOnClickListener());
		txt_time_end.setOnClickListener(new DateButtonOnClickListeners());
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		mYears = c.get(Calendar.YEAR);
		mMonths = c.get(Calendar.MONTH);
		mDays = c.get(Calendar.DAY_OF_MONTH);
	}

	// 监听事件
	private View.OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			clickListener(v);
		}

		private void clickListener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 返回
			case R.id.image_but:
				// Intent intent = new Intent(context,
				// EducationsActivity.class);
				// startActivity(intent);
				finish();
				break;
			// 保存
			case R.id.txt_posit:
				doData();
				break;
			// 学历
			case R.id.rela_edu:
				Intent intent_RecordActivity = new Intent(context,
						RecordActivity.class);
				startActivityForResult(intent_RecordActivity, 5);
				overridePendingTransition(anim.slide_in_right,
						anim.slide_out_left);
				break;
			// 开始时间
			case R.id.rela_time_beng:
				break;
			// 结束时间
			case R.id.rela_time_end:
				break;
			// 学校
			case R.id.rela_edus:
				break;
			// 所在城市
			case R.id.rela_local:
				Intent addressIntent = new Intent(context,
						NowAddresActivity.class);
				startActivityForResult(addressIntent, 1);
				((Activity) context).overridePendingTransition(
						anim.slide_in_right, anim.slide_out_left);
				break;
			// 专业
			case R.id.rela_professional:
				Intent intent1 = new Intent(context,
						OptEduMajorTypeActivity.class);
				intent1.putExtra("request", REQUEST_WORK_MODE);
				startActivityForResult(intent1, REQUEST_WORK_MODE);
				overridePendingTransition(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				break;
			// 是否海外学习
			case R.id.rela_learning:
				Intent Overseass = new Intent(context, Overseas.class);
				// Overseass.putExtra("request", 101);
				Overseass.putExtra("edu", edu);
				startActivityForResult(Overseass, 101);
				overridePendingTransition(anim.slide_in_right,
						anim.slide_out_left);
				break;
			// 学校名称
			case R.id.txt_eds_name:
				if (txt_eds_name.length() > 1) {
					txt_eds_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					txt_eds_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 是否海外学习经历
			case R.id.txt_learning:
				if (txt_learning.length() > 1) {
					txt_learning.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					txt_learning.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;

			}
		}
	};

	// 处理返回值
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (data == null || "".equals(data)) {
				return;
			} else {
				String area_code = data
						.getStringExtra(NowAddresActivity.AREA_CODE);
				String area_value = data
						.getStringExtra(NowAddresActivity.AREA_VALUE);
				areaValue = area_value;
				if (!StringUtils.isEmpty(area_code)) {
					txt_local.setText(area_value);
				}
				areaCode = area_code;
			}
			break;
		case REQUEST_WORK_MODE:
			if (data == null || "".equals(data)) {
				return;
			} else {
				String major_value = data.getStringExtra("value");
				String major_key = data.getStringExtra("code");
				major_Value = major_value;
				if (!StringUtils.isEmpty(major_key)) {
					txt_professional.setText(major_value);
				}
				major_Key = major_key;
			}
			break;
		case 5:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_edu.setText(data.getStringExtra("Record"));
			}
			break;
		case 101:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_learning.setText(data.getStringExtra("Overseas"));
			}
			break;
		}

	};

	// 更新生日日期
	private void updateDisplay() {
		txt_time.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay));

	}

	// 更新毕业时间日期
	private void updateDisplays() {
		txt_time_end.setText(new StringBuilder()
				.append(mYears)
				.append("-")
				.append((mMonths + 1) < 10 ? "0" + (mMonths + 1)
						: (mMonths + 1)).append("-")
				.append((mDays < 10) ? "0" + mDays : mDays));
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
	// 日期控件的事件
	private DatePickerDialog.OnDateSetListener mDateSetListeners = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYears = year;
			mMonths = monthOfYear;
			mDays = dayOfMonth;
			updateDisplays();
		}
	};

	// 选择日期的事件处理
	private class DateButtonOnClickListener implements
			android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			Message msg = new Message();
			if (txt_time.equals((TextView) v)) {
				msg.what = AddEducatitonsActivity.SHOW_DATAPICK;
			}
			saleHandler.sendMessage(msg);

		}
	}

	// 选择日期的事件处理
	private class DateButtonOnClickListeners implements
			android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			Message msg = new Message();
			if (txt_time_end.equals((TextView) v)) {
				msg.what = AddEducatitonsActivity.SHOW_DATAPICKS;
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

		case 4:
			return new DatePickerDialog(this, mDateSetListeners, mYears,
					mMonths, mDays);
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
		case 4:
			((DatePickerDialog) dialog).updateDate(mYears, mMonths, mDays);
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
			case AddEducatitonsActivity.SHOW_DATAPICK:
				showDialog(3);
				break;
			case AddEducatitonsActivity.SHOW_DATAPICKS:
				showDialog(4);
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
				txt_eds_name.setCompoundDrawablesWithIntrinsicBounds(null,
						null, drawable, null);
				return;
			} else {
				txt_eds_name.setCompoundDrawablesWithIntrinsicBounds(null,
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
						&& !TextUtils.isEmpty(txt_eds_name.getText())) {
					txt_eds_name.setText("");
					txt_eds_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					int cacheInputType = txt_eds_name.getInputType();
					txt_eds_name.setInputType(InputType.TYPE_NULL);
					txt_eds_name.onTouchEvent(event);
					txt_eds_name.setInputType(cacheInputType);
					txt_eds_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	/*
	 * // 监听账号输入框 显示 X图片 private TextWatcher describe_delect = new TextWatcher()
	 * {
	 * 
	 * @Override public void onTextChanged(CharSequence s, int start, int
	 * before, int count) { // TODO Auto-generated method stub if
	 * (!TextUtils.isEmpty(s)) {
	 * txt_learning.setCompoundDrawablesWithIntrinsicBounds(null, null,
	 * drawable, null); return; } else {
	 * txt_learning.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
	 * null); } }
	 * 
	 * @Override public void beforeTextChanged(CharSequence s, int start, int
	 * count, int after) { }
	 * 
	 * @Override public void afterTextChanged(Editable s) {
	 * 
	 * } };
	 * 
	 * // 触屏 删除账号 private OnTouchListener describe_ontouch = new
	 * OnTouchListener() {
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) { // TODO
	 * Auto-generated method stub switch (event.getAction()) { case
	 * MotionEvent.ACTION_UP: int curx = (int) event.getX(); if (curx >
	 * v.getWidth() - 38 && !TextUtils.isEmpty(txt_learning.getText())) {
	 * txt_learning.setText("");
	 * txt_learning.setCompoundDrawablesWithIntrinsicBounds(null, null,
	 * drawable, null); int cacheInputType = txt_learning.getInputType();
	 * txt_learning.setInputType(InputType.TYPE_NULL);
	 * txt_learning.onTouchEvent(event);
	 * txt_learning.setInputType(cacheInputType);
	 * txt_learning.setCompoundDrawablesWithIntrinsicBounds(null, null,
	 * drawable, null); return true; } break; } return false; } };
	 */

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
