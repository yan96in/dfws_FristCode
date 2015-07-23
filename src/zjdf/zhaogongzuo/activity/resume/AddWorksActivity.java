package zjdf.zhaogongzuo.activity.resume;

import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.search.PositionClassActivity;
import zjdf.zhaogongzuo.controllers.ResumeInformationControllers;
import zjdf.zhaogongzuo.entity.ResumeWorks;
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
 * 添加工作经验
 * 
 * @author Administrator
 * 
 */
public class AddWorksActivity extends Activity {
	private ImageButton image_but;// 返回
	private Button txt_posit;// 保存

	private RelativeLayout rela_time_beng;// 开始时间
	private TextView txt_time;
	private RelativeLayout rela_time_end;// 结束时间
	private TextView txt_time_end;
	private RelativeLayout rela_local;// 所在城市
	private TextView txt_local;
	private RelativeLayout rela_enterprise;// 企业名称
	private EditText txt_enterprise;
	private RelativeLayout rela_industry;// 所属行业
	private TextView txt_industry;
	private RelativeLayout rela_job;// 所属职位
	private TextView txt_job;
	private Context context;// 上下文
	private int ind_industry;// 行业
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
	/** 职位分类编码 */
	public static String positionsClassCode;
	/** 职位分类 */
	public static String positionsClassValue;

	private static int state;// 返回状态

	// 接收item 传递过来的数据
	private String id;// 经验ID
	private String bengin_time;// 开始年月
	private String eng_time;// 结束 年月
	private String bengin_times;// 开始年月
	private String eng_times;// 结束 年月
	private String city;// 所在城市
	private String enterprise;// 企业名字
	private String industr;// 所属行业
	private String positions;// 所属职业
	private String posi_code;//所属职业Code

	private Drawable drawable = null;// 删除图片

	private String year_time; // 年
	private String moth_time;// 月
	private String year_times; // 年
	private String moth_times;// 月
	
	private String str_job;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_addworks);
		context = this;
		resumeInformationControllers = new ResumeInformationControllers(context);
		
		id = getIntent().getStringExtra("id");
		bengin_time = getIntent().getStringExtra("bengin_time");
		eng_time = getIntent().getStringExtra("eng_time");
		bengin_times = getIntent().getStringExtra("bengin_times");
		eng_times = getIntent().getStringExtra("eng_times");
		city = getIntent().getStringExtra("city");
		enterprise = getIntent().getStringExtra("enterprise");
		industr = getIntent().getStringExtra("industr");
		positions = getIntent().getStringExtra("positions");
		posi_code=getIntent().getStringExtra("posi_code");
		
		initView();
		assignment();

	}

	// 赋值
	private void assignment() {
		if (id != null) {
			txt_time.setText(bengin_time + "-" + bengin_times);
			txt_time_end.setText(eng_time + "-" + eng_times);
			txt_local.setText(city);
			txt_enterprise.setText(enterprise);
			txt_industry.setText(industr);
			txt_job.setText(positions);
		}
	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			txt_posit.setEnabled(true);
			switch (msg.what) {
			case 1:
				if (state == 1) {
					Toast.makeText(context, "保存成功！", Toast.LENGTH_LONG).show();

					finish();
				} else {
					Toast.makeText(context, "添加失败！", Toast.LENGTH_LONG).show();
				}

				break;
			}
		};
	};

	// 初始化 数据
	private void initView() {
		// TODO Auto-generated method stub
		image_but = (ImageButton) findViewById(R.id.image_but);
		txt_posit = (Button) findViewById(R.id.txt_posit);

		rela_time_beng = (RelativeLayout) findViewById(R.id.rela_time_beng);
		txt_time = (TextView) findViewById(R.id.txt_time);

		rela_time_end = (RelativeLayout) findViewById(R.id.rela_time_end);
		txt_time_end = (TextView) findViewById(R.id.txt_time_end);

		rela_local = (RelativeLayout) findViewById(R.id.rela_local);
		txt_local = (TextView) findViewById(R.id.txt_local);

		rela_enterprise = (RelativeLayout) findViewById(R.id.rela_enterprise);
		txt_enterprise = (EditText) findViewById(R.id.txt_enterprise);

		rela_industry = (RelativeLayout) findViewById(R.id.rela_industry);
		txt_industry = (TextView) findViewById(R.id.txt_industry);

		txt_industry = (TextView) findViewById(R.id.txt_industry);

		rela_job = (RelativeLayout) findViewById(R.id.rela_job);
		txt_job = (TextView) findViewById(R.id.txt_job);

		drawable = getResources().getDrawable(R.drawable.ic_close_gary);

		image_but.setOnClickListener(listener);
		txt_posit.setOnClickListener(listener);
		txt_enterprise.setOnClickListener(listener);

		rela_time_beng.setOnClickListener(listener);
		rela_time_end.setOnClickListener(listener);
		rela_local.setOnClickListener(listener);
		rela_enterprise.setOnClickListener(listener);
		rela_industry.setOnClickListener(listener);
		rela_job.setOnClickListener(listener);

		txt_enterprise.addTextChangedListener(username_delect);
		txt_enterprise.setOnTouchListener(username_ontouch);

		txt_time.setOnClickListener(new DateButtonOnClickListener());
		txt_time_end.setOnClickListener(new DateButtonOnClickListeners());
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		mYears = c.get(Calendar.YEAR);
		mMonths = c.get(Calendar.MONTH);
		mDays = c.get(Calendar.DAY_OF_MONTH);
		
		if (posi_code!=null) {		
			positionsClassCode=posi_code;
		}

	}

	// 添加工作经验
	private void setResumeWorks() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		final String name = txt_enterprise.getText().toString();
		final String industry_name = txt_industry.getText().toString();
		final String str_time = txt_time.getText().toString();
		final String str_time_end = txt_time_end.getText().toString();
		str_job=txt_job.getText().toString();

		if (str_time != null&&str_time.length()>5) {
			year_time = str_time.substring(0, 4);
			moth_time = str_time.substring(6, 7);
		}
		if (str_time_end != null&&str_time_end.length()>5) {
			year_times = str_time_end.substring(0, 4);
			moth_times = str_time_end.substring(6, 7);
		}

		if (industry_name.contains("酒店业")) {
			ind_industry = 1;
		} else if (industry_name.contains("餐饮业")) {
			ind_industry = 2;
		} else if (industry_name.contains("娱乐业")) {
			ind_industry = 3;
		} else if (industry_name.contains("旅行社")) {
			ind_industry = 4;
		} else if (industry_name.contains("旅游用品业")) {
			ind_industry = 5;
		} else if (industry_name.contains("其他行业")) {
			ind_industry = 6;
		}

		if (str_time == null || str_time.trim().equals("")) {
			Toast.makeText(context, "开始时间不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_time_end == null || str_time_end.equals("")) {
			Toast.makeText(context, "结束时间不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		/*
		 * if (areaCode == null || areaCode.trim().equals("")) {
		 * Toast.makeText(context, "工作城市不能为空！", Toast.LENGTH_LONG).show();
		 * return; }
		 */
		if (name == null || name.trim().equals("")) {
			Toast.makeText(context, "企业名不能为空！", Toast.LENGTH_LONG).show();
			return;
		}

		if (industry_name == null || industry_name.trim().equals("")) {
			Toast.makeText(context, "所属行业不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if (positionsClassCode == null || positionsClassCode.trim().equals("")) {
			Toast.makeText(context, "职位不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		// 正则表达式 判断 支持汉子和字母和.
		if (!StringUtils.checkVariety(name)) {
			Toast.makeText(context, "只支持汉字和字母并且。", Toast.LENGTH_LONG).show();
			return;
		}

		final ResumeWorks resumeWorks = new ResumeWorks();
		resumeWorks.id = id;
		resumeWorks.begin_time_year = year_time;
		resumeWorks.begin_time_month = moth_time;
		resumeWorks.end_time_year = year_times;
		resumeWorks.end_time_month = moth_times;
		resumeWorks.area_key = areaCode + "";
		resumeWorks.enterprise_value = name;
		resumeWorks.position_key = positionsClassCode;
		resumeWorks.industry_key = ind_industry + "";
		txt_posit.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				state = resumeInformationControllers
						.setResumeWorks(resumeWorks);
				handler.sendEmptyMessage(1);
			}
		}.start();
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
			case R.id.image_but:
				// Intent intent = new Intent(context, WorksActivity.class);
				// startActivity(intent);
				finish();
				break;
			// 保存
			case R.id.txt_posit:
				setResumeWorks();
				break;
			// 开始时间
			case R.id.rela_time_beng:

				break;
			// 结束时间
			case R.id.rela_time_end:
				break;
			// 所在城市
			case R.id.rela_local:
				Intent addressIntent = new Intent(context,
						NowAddresActivity.class);
				addressIntent.putExtra("request", 2);
				addressIntent.putExtra("areaCode", areaCode);
				startActivityForResult(addressIntent, 1);
				((Activity) context).overridePendingTransition(
						anim.slide_in_right, anim.slide_out_left);
				break;
			// 企业名称
			case R.id.rela_enterprise:
				// 所属行业
			case R.id.rela_industry:
				Intent industryIntent = new Intent(context,
						IndustryActivity.class);
				startActivityForResult(industryIntent, 3);
				((Activity) context).overridePendingTransition(
						android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				break;
			// 所属职业
			case R.id.rela_job:
				Intent classIntent = new Intent(context,
						PositionClassActivitys.class);
				startActivityForResult(classIntent, 2);
				((Activity) context).overridePendingTransition(
						anim.slide_in_right, anim.slide_out_left);
				break;

			case R.id.txt_enterprise:
				if (txt_enterprise.length() > 1) {
					txt_enterprise.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					return;
				} else {
					txt_enterprise.setCompoundDrawablesWithIntrinsicBounds(
							null, null, null, null);
				}
				break;
			}
		}
	};

	// 处理返回参数
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
				} else {
					txt_local.setText("不限");
				}
				areaCode = area_code;
			}
			break;
		case 2:
			if (data == null || "".equals(data)) {
				return;
			} else {
				String position_code = data
						.getStringExtra(PositionClassActivitys.POSITION_CODES);
				String position_value = data
						.getStringExtra(PositionClassActivitys.POSITION_VALUES);
				if (!StringUtils.isEmpty(position_value)) {
					txt_job.setText(position_value);
				}
				positionsClassCode = position_code;
				positionsClassValue = position_value;
			}
			break;
		case 3:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_industry.setText(data.getStringExtra("Industry"));
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
				msg.what = AddWorksActivity.SHOW_DATAPICK;
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
				msg.what = AddWorksActivity.SHOW_DATAPICKS;
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
			case AddWorksActivity.SHOW_DATAPICK:
				showDialog(3);
				break;
			case AddWorksActivity.SHOW_DATAPICKS:
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
				txt_enterprise.setCompoundDrawablesWithIntrinsicBounds(null,
						null, drawable, null);
				return;
			} else {
				txt_enterprise.setCompoundDrawablesWithIntrinsicBounds(null,
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
						&& !TextUtils.isEmpty(txt_enterprise.getText())) {
					txt_enterprise.setText("");
					txt_enterprise.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
					int cacheInputType = txt_enterprise.getInputType();
					txt_enterprise.setInputType(InputType.TYPE_NULL);
					txt_enterprise.onTouchEvent(event);
					txt_enterprise.setInputType(cacheInputType);
					txt_enterprise.setCompoundDrawablesWithIntrinsicBounds(
							null, null, drawable, null);
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
