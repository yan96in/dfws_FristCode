package zjdf.zhaogongzuo.activity.resume;

import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
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
 * 添加培训经历
 * 
 * @author Administrator
 * 
 */
public class AddTrainingActivity extends Activity {
	private RelativeLayout rela_name;// 培训机构名称
	private EditText tra_name;
	private RelativeLayout rela_beng_time;// 培训开始时间
	private TextView tra_beng_time;
	private RelativeLayout rela_end_time;// 培训结束时间
	private TextView tra_end_time;
	private RelativeLayout rela_local;// 所在城市
	private TextView tra_addres;
	private RelativeLayout rela_cer;// 获得证书
	private EditText tra_cer;
	private RelativeLayout rela_des;// 培训描述
	private EditText tra_des;

	private ImageButton image_but;// 返回
	private Button txt_posit;// 保存

	private Context context;// 上下文
	private MyResumeConttroller myResumeConttroller;// 控制器
	private int state;// 返回状态

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
	public static final int REQUESTCODE_ADDRESS = 0x0000101;

	// 培训经历 列表传递过来的 数据
	private String id;// 培训经历ID
	private String name;// 培训名称
	private String beng_time;// 培训开始时间
	private String eng_time;// 培训结束时间
	private String beng_times;// 培训开始时间
	private String eng_times;// 培训结束时间
	private String addrees;// 培训地址
	private String certificates;// 培训证书
	private String deteail;// 培训描述

	private Drawable drawable = null;// 删除图片

	String t_des;// 开始时间
	String t_end;// 结束时间

	String year_time; // 年
	String moth_time;// 月
	String year_times; // 年
	String moth_times;// 月

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_trainings_item);
		context = this;
		myResumeConttroller = new MyResumeConttroller(context);
		id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("name");
		beng_time = getIntent().getStringExtra("beng_time");
		eng_time = getIntent().getStringExtra("eng_time");
		beng_times = getIntent().getStringExtra("beng_times");
		eng_times = getIntent().getStringExtra("eng_times");
		addrees = getIntent().getStringExtra("addrees");
		certificates = getIntent().getStringExtra("certificates");
		deteail = getIntent().getStringExtra("deteail");
		initView();
		assignment();
	}

	// 赋值
	private void assignment() {
		if (!StringUtils.isEmpty(id)) {
			tra_name.setText(name);
			tra_beng_time.setText(beng_time + "-" + beng_times);
			tra_end_time.setText(eng_time + "-" + eng_times);
			tra_addres.setText(addrees);
			tra_cer.setText(certificates);
			tra_des.setText(deteail);
		}
	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			txt_posit.setEnabled(true);
			switch (msg.what) {
			case 1:
				if (state == 1) {
					Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();

					finish();
				} else {
					Toast.makeText(context, "保存失败", Toast.LENGTH_LONG).show();
				}

				break;
			}
		};
	};

	// 保存
	private void setData() {

		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		final String t_name = tra_name.getText().toString();
		final String t_cer = tra_cer.getText().toString();
		final String t_dess = tra_des.getText().toString();
		t_des = tra_beng_time.getText().toString();
		t_end = tra_end_time.getText().toString();
		if (t_name == null || t_name.trim().equals("")) {
			Toast.makeText(context, "培训机构不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if (t_des == null || t_des.trim().equals("")) {
			Toast.makeText(context, "开始时间不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if (t_des != null) {
			year_time = t_des.substring(0, 4);
			moth_time = t_des.substring(6, 7);
		}		
		if (t_end == null || t_end.trim().equals("")) {
			Toast.makeText(context, "结束时间不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if (t_end != null) {
			year_times = t_end.substring(0, 4);
			moth_times = t_end.substring(6, 7);
		}
		
		txt_posit.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				state = myResumeConttroller.set_training_exp(t_name, year_time,moth_time, year_times, moth_times , t_cer, t_dess,areaCode, id);
				handler.sendEmptyMessage(1);
			}
		}.start();

	}

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		rela_name = (RelativeLayout) findViewById(R.id.rela_name);
		rela_beng_time = (RelativeLayout) findViewById(R.id.rela_beng_time);
		rela_end_time = (RelativeLayout) findViewById(R.id.rela_end_time);
		rela_local = (RelativeLayout) findViewById(R.id.rela_local);
		rela_cer = (RelativeLayout) findViewById(R.id.rela_cer);
		rela_des = (RelativeLayout) findViewById(R.id.rela_des);

		tra_name = (EditText) findViewById(R.id.tra_name);
		tra_beng_time = (TextView) findViewById(R.id.tra_beng_time);
		tra_end_time = (TextView) findViewById(R.id.tra_end_time);
		tra_addres = (TextView) findViewById(R.id.tra_addres);
		tra_cer = (EditText) findViewById(R.id.tra_cer);
		tra_des = (EditText) findViewById(R.id.tra_des);

		image_but = (ImageButton) findViewById(R.id.image_but);
		txt_posit = (Button) findViewById(R.id.txt_posit);

		drawable = getResources().getDrawable(R.drawable.ic_close_gary);

		image_but.setOnClickListener(clickListener);
		txt_posit.setOnClickListener(clickListener);
		rela_local.setOnClickListener(clickListener);
		tra_name.setOnClickListener(clickListener);
		tra_cer.setOnClickListener(clickListener);
		tra_des.setOnClickListener(clickListener);

		tra_name.addTextChangedListener(username_delect);
		tra_name.setOnTouchListener(username_ontouch);
		tra_cer.addTextChangedListener(describe_delect);
		tra_cer.setOnTouchListener(describe_ontouch);
		tra_des.addTextChangedListener(des_delect);
		tra_des.setOnTouchListener(des_ontouch);

		tra_beng_time.setOnClickListener(new DateButtonOnClickListener());
		tra_end_time.setOnClickListener(new DateButtonOnClickListeners());

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		mYears = c.get(Calendar.YEAR);
		mMonths = c.get(Calendar.MONTH);
		mDays = c.get(Calendar.DAY_OF_MONTH);

	}

	// 监听器
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
				Intent intent = new Intent(context, TrainingActivity.class);
				startActivity(intent);
				finish();
				break;
			// 保存
			case R.id.txt_posit:
				setData();
				break;
			// 所在城市
			case R.id.rela_local:
				Intent addressIntent = new Intent(context,
						NowAddresActivity.class);
				addressIntent.putExtra("request", 2);
				startActivityForResult(addressIntent, REQUESTCODE_ADDRESS);
				((Activity) context).overridePendingTransition(
						anim.slide_in_right, anim.slide_out_left);
				break;
			// 培训机构名称
			case R.id.tra_name:
				if (tra_name.length() > 1) {
					tra_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					return;
				} else {
					tra_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 获得证书
			case R.id.tra_cer:
				if (tra_cer.length() > 1) {
					tra_cer.setCompoundDrawablesWithIntrinsicBounds(null, null,
							drawable, null);
					return;
				} else {
					tra_cer.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, null);
				}
				break;
			// 获得描述
			case R.id.tra_des:
				if (tra_des.length() > 1) {
					tra_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
							drawable, null);
					return;
				} else {
					tra_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, null);
				}
				break;
			}
		}
	};

	// 处理返回值
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_ADDRESS:
			if (data == null || "".equals(data)) {
				return;
			} else {
				String area_code = data
						.getStringExtra(NowAddresActivity.AREA_CODE);
				String area_value = data
						.getStringExtra(NowAddresActivity.AREA_VALUE);
				areaValue = area_value;
				if (!StringUtils.isEmpty(area_code)) {
					tra_addres.setText(area_value);
				} else {
					tra_addres.setText("不限");
				}
				areaCode = area_code;
			}
			break;

		}
	};

	// 更新生日日期
	private void updateDisplay() {
		tra_beng_time.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay));

	}

	// 更新毕业时间日期
	private void updateDisplays() {
		tra_end_time.setText(new StringBuilder()
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
			if (tra_beng_time.equals((TextView) v)) {
				msg.what = AddTrainingActivity.SHOW_DATAPICK;
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
			if (tra_end_time.equals((TextView) v)) {
				msg.what = AddTrainingActivity.SHOW_DATAPICKS;
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
			case AddTrainingActivity.SHOW_DATAPICK:
				showDialog(3);
				break;
			case AddTrainingActivity.SHOW_DATAPICKS:
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
				tra_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
						drawable, null);
				return;
			} else {
				tra_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
						&& !TextUtils.isEmpty(tra_name.getText())) {
					tra_name.setText("");
					tra_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, drawable, null);
					int cacheInputType = tra_name.getInputType();
					tra_name.setInputType(InputType.TYPE_NULL);
					tra_name.onTouchEvent(event);
					tra_name.setInputType(cacheInputType);
					tra_name.setCompoundDrawablesWithIntrinsicBounds(null,
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
				tra_cer.setCompoundDrawablesWithIntrinsicBounds(null, null,
						drawable, null);
				return;
			} else {
				tra_cer.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	private OnTouchListener describe_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(tra_cer.getText())) {
					tra_cer.setText("");
					tra_cer.setCompoundDrawablesWithIntrinsicBounds(null, null,
							drawable, null);
					int cacheInputType = tra_cer.getInputType();
					tra_cer.setInputType(InputType.TYPE_NULL);
					tra_cer.onTouchEvent(event);
					tra_cer.setInputType(cacheInputType);
					tra_cer.setCompoundDrawablesWithIntrinsicBounds(null, null,
							drawable, null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	// 监听账号输入框 显示 X图片
	private TextWatcher des_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				tra_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
						drawable, null);
				return;
			} else {
				tra_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	private OnTouchListener des_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(tra_des.getText())) {
					tra_des.setText("");
					tra_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
							drawable, null);
					int cacheInputType = tra_des.getInputType();
					tra_des.setInputType(InputType.TYPE_NULL);
					tra_des.onTouchEvent(event);
					tra_des.setInputType(cacheInputType);
					tra_des.setCompoundDrawablesWithIntrinsicBounds(null, null,
							drawable, null);
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
