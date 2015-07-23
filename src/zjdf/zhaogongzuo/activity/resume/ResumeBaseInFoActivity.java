/**
 * Copyright © 2014年3月31日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.resume;

import java.util.Calendar;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.personal.RecordActivity;
import zjdf.zhaogongzuo.controllers.ResumeInformationControllers;
import zjdf.zhaogongzuo.entity.ResumeInformation;
import zjdf.zhaogongzuo.utils.DateTimeUtils;
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
 * 我的简历 基本信息
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月31日
 * @version
 * @modify
 * 
 */
public class ResumeBaseInFoActivity extends Activity {

	private EditText edt_name;// 用户名
	// * 性别*/
	private RelativeLayout rela_gender;
	private TextView txt_gender;
	/* 日期 */
	private RelativeLayout rela_birthday;
	private TextView txt_birthday;
	/* 工作经验 */
	private RelativeLayout rela_works;
	private EditText txt_works;
	/* 毕业时间 */
	private RelativeLayout rela_graduate;
	private TextView txt_graduate;
	/* 最高学历 */
	private RelativeLayout rela_degree;
	private TextView txt_degree;
	/* 现居住地 */
	private RelativeLayout rela_current_address;
	private TextView txt_current_address;//
	/* 户籍地 */
	private RelativeLayout rela_home_address;
	private TextView txt_home_address;
	/* 手机号码 */
	private RelativeLayout rela_phone;
	private EditText txt_phone;
	/* 求职状态 */
	private RelativeLayout rela_jobstatus;
	private TextView txt_jobstatus;
	/* 身高 */
	private RelativeLayout rela_height;
	private EditText txt_height;
	/* 电子邮箱 */
	private RelativeLayout rela_email;
	private EditText txt_email;
	/* 体重 */
	private RelativeLayout rela_weight;
	private EditText txt_weight;
	/* 证件类型 */
	private RelativeLayout rela_idtype;
	private TextView txt_type;
	/* 证件号码 */
	private RelativeLayout rela_idnumber;
	private EditText txt_idnumber;

	private ResumeInformationControllers informationControllers;// 简历控制器
	private Context context;// 上下文
	private ResumeInformation information;// 返回数据实体
	private ImageButton goback;// 返回
	private Button btn_save;// 保存
	/** 现居地 地址 */
	public static String areaValue;
	/** 现居地 地址编码 */
	public static String areaCode;
	/** 户籍地址 */
	public static String areaValues;
	/** 户籍地址编码 */
	public static String areaCodes;

	// 日期用的
	private static final int SHOW_DATAPICK = 0;// 生日日期修改
	private static final int SHOW_DATAPICKS = 1;// 毕业日期修改
	private int mYear;
	private int mMonth;
	private int mDay;

	private int mYears;
	private int mMonths;
	private int mDays;

	private Drawable image = null;// 删除图片

	// 判断 字符串和是否 为空
	private String str_name;
	private String str_gender;
	private String str_birthday;
	private String str_works;
	private String str_degree;
	private String str_graduate;
	private String str_current_address;
	private String str_home_address;
	private String str_phone;

	private String str_height;
	private String str_email;
	private String str_weight;
	private String str_type;
	private String str_idnumber;

	private int int_gender;
	// private int int_works;
	private int int_degree;
	private int int__type;

	private int status;// 接收返回状态
	private String number;// 传递性别

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myresume_baseinfo);
		context = this;
		informationControllers = new ResumeInformationControllers(context);
		initView();

	}

	// 刷新Ui
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 赋值基本状态
			case 1:
				if (information != null) {
					edt_name.setText(information.getName());
					txt_gender.setText(information.getGender_text());
					txt_birthday.setText(information.getBirthday());
					txt_works.setText(information.getWork_exps() + "");
					if (information.getGraduate_date().contains("0000-00-00")) {						
						txt_graduate.setText("空");
					}else{						
						txt_graduate.setText(information.getGraduate_date());
					}					
					txt_degree.setText(information.getEdu_text());
					txt_current_address.setText(information.getCurrent_place_text());
					txt_home_address.setText(information.getDomicile_place_text());
					txt_phone.setText(information.getPhone());
					txt_height.setText(information.getHeight() + "");
					txt_email.setText(information.getEmail());
					txt_weight.setText(information.getWeight() + "");
					txt_type.setText(information.getId_type_text());
					txt_idnumber.setText(information.getId_num() + "");
				}
				break;
			case 100:
				if (status == 1) {
					Toast.makeText(context, "修改成功啦！", Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(context, "修改失败了！", Toast.LENGTH_LONG).show();
				}
				break;
			}

		};
	};

	// 保存基本信息
	private void set_Save() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		str_name = edt_name.getText().toString();
		str_gender = txt_gender.getText().toString();
		str_birthday = txt_birthday.getText().toString();
		str_works = txt_works.getText().toString();
		str_degree = txt_degree.getText().toString();
		str_graduate = txt_graduate.getText().toString();
		str_current_address = txt_current_address.getText().toString();
		str_home_address = txt_home_address.getText().toString();
		str_phone = txt_phone.getText().toString();
		str_height = txt_height.getText().toString();
		str_email = txt_email.getText().toString();
		str_weight = txt_weight.getText().toString();
		// str_type = txt_type.getText().toString();
		str_idnumber = txt_idnumber.getText().toString();

		if (str_name == null || str_name.trim().equals("")) {
			Toast.makeText(context, "请输入您的姓名", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_gender == null || txt_gender.getText().equals("")) {
			Toast.makeText(context, "请选择您的性别", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_birthday == null || txt_birthday.getText().equals("")) {
			Toast.makeText(context, "请输入您的出生日期", Toast.LENGTH_LONG).show();
			return;

		}
		str_birthday=DateTimeUtils.getCustomDateTime(str_birthday, "yyyyMMdd","yyyy-MM-dd");
		// str_works == null || str_works.contains("0")
		if (str_works == null || txt_works.getText().equals("")) {
			Toast.makeText(context, "请选择您的工作经验", Toast.LENGTH_LONG).show();
			return;
		}
		if (!str_graduate.contains("0000-00-00")) {
			if (str_graduate!=null&&txt_graduate.getText().length()>0&&!str_graduate.contains("空")) {
				str_graduate=DateTimeUtils.getCustomDateTime(str_graduate, "yyyyMMdd","yyyy-MM-dd");
			}
		}
		if (str_degree == null || str_degree.contains("空")) {
			Toast.makeText(context, "请选择你的最高学历", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_current_address == null
				|| txt_current_address.getText().equals("")) {
			Toast.makeText(context, "请选择你的现居地", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_home_address == null || txt_home_address.getText().equals("")) {
			Toast.makeText(context, "请选择你的户籍地", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_phone == null || txt_phone.getText().equals("")) {
			Toast.makeText(context, "请输入你的电话号码", Toast.LENGTH_LONG).show();
			return;
		}
		// 正则表达式 判断 支持汉子和字母和.
		if (!StringUtils.checkVariety(str_name)) {
			Toast.makeText(context, "只支持汉字和字母并且.", Toast.LENGTH_LONG).show();
			return;
		}
		if (!StringUtils.checkEmail(str_email)) {
			Toast.makeText(context, "邮箱格式不正确！", Toast.LENGTH_LONG).show();
			return;
		}
		if (!StringUtils.checkPhone(str_phone)) {
			Toast.makeText(context, "手机号码格式不正确！", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_height == null || str_height == 0 + "") {
			Toast.makeText(context, "请输入你的身高", Toast.LENGTH_LONG).show();
			return;
		}
		if (str_email == null || str_email.trim().equals("")) {
			Toast.makeText(context, "请输入你的电子邮箱", Toast.LENGTH_LONG).show();
			return;
		}
		
		// 性别变为数字
		number = txt_gender.getText().toString();
		if (number.contains("男")) {
			int_gender = 1;
		} else if (number.contains("女")) {
			int_gender = 2;
		}
		// 学历 转换成 int 类型
		String degeree = txt_degree.getText().toString();
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
		String type = txt_type.getText().toString();
		if (type.contains("身份证")) {
			int__type = 1;
		} else if (type.contains("军人证")) {
			int__type = 2;
		} else if (type.contains("台胞证")) {
			int__type = 4;
		} else if (type.contains("护照")) {
			int__type = 5;
		} else if (type.contains("其他证件")) {
			int__type = 6;
		}
		/**
		 * else if (type.contains("港澳身份证")) { int__type = 3; }
		 */
		if (StringUtils.equals(type, "港澳身份证")) {
			int__type = 3;
		}
		final int work = Integer.parseInt(str_works);
		
		if (areaCode == null) {
			areaCode = information.getCurrent_place();
		}
		if (areaCodes == null) {
			areaCodes = information.getDomicile_place();
		}
		new Thread() {
			@Override
			public void run() {
				status = informationControllers.set_base(str_name, int_gender,
						str_birthday, work, str_graduate, int_degree, areaCode,
						areaCodes, str_phone, str_height, str_email,
						str_weight, int__type, str_idnumber);
				handler.sendEmptyMessage(100);
			}
		}.start();

	}

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		// 文本类型
		edt_name = (EditText) findViewById(R.id.edt_name);
		txt_gender = (TextView) findViewById(R.id.txt_gender);
		txt_birthday = (TextView) findViewById(R.id.txt_birthday);
		txt_works = (EditText) findViewById(R.id.txt_works);
		txt_graduate = (TextView) findViewById(R.id.txt_graduate);
		txt_degree = (TextView) findViewById(R.id.txt_degree);
		txt_current_address = (TextView) findViewById(R.id.txt_current_address);
		txt_home_address = (TextView) findViewById(R.id.txt_home_address);
		txt_phone = (EditText) findViewById(R.id.txt_phone);
		// txt_jobstatus = (TextView) findViewById(R.id.txt_jobstatus);
		txt_height = (EditText) findViewById(R.id.txt_height);
		txt_email = (EditText) findViewById(R.id.txt_email);
		txt_weight = (EditText) findViewById(R.id.txt_weight);
		txt_type = (TextView) findViewById(R.id.txt_type);
		txt_idnumber = (EditText) findViewById(R.id.txt_idnumber);

		// 布局层
		rela_gender = (RelativeLayout) findViewById(R.id.rela_gender);
		rela_birthday = (RelativeLayout) findViewById(R.id.rela_birthday);
		rela_works = (RelativeLayout) findViewById(R.id.rela_works);
		rela_graduate = (RelativeLayout) findViewById(R.id.rela_graduate);
		rela_degree = (RelativeLayout) findViewById(R.id.rela_degree);
		rela_current_address = (RelativeLayout) findViewById(R.id.rela_current_address);
		rela_home_address = (RelativeLayout) findViewById(R.id.rela_home_address);
		rela_phone = (RelativeLayout) findViewById(R.id.rela_phone);
		rela_jobstatus = (RelativeLayout) findViewById(R.id.rela_jobstatus);
		rela_height = (RelativeLayout) findViewById(R.id.rela_height);
		rela_email = (RelativeLayout) findViewById(R.id.rela_email);
		rela_weight = (RelativeLayout) findViewById(R.id.rela_weight);
		rela_idtype = (RelativeLayout) findViewById(R.id.rela_idtype);
		rela_idnumber = (RelativeLayout) findViewById(R.id.rela_idnumber);

		// 添加监听
		goback = (ImageButton) findViewById(R.id.goback);
		btn_save = (Button) findViewById(R.id.btn_save);
		goback.setOnClickListener(clickListener);
		btn_save.setOnClickListener(clickListener);
		// 布局层
		rela_gender.setOnClickListener(clickListener);
		rela_works.setOnClickListener(clickListener);
		rela_jobstatus.setOnClickListener(clickListener);
		rela_idtype.setOnClickListener(clickListener);
		rela_degree.setOnClickListener(clickListener);
		rela_current_address.setOnClickListener(clickListener);
		rela_home_address.setOnClickListener(clickListener);

		// 日期
		txt_birthday.setOnClickListener(new DateButtonOnClickListener());
		txt_graduate.setOnClickListener(new DateButtonOnClickListeners());
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		mYears = c.get(Calendar.YEAR);
		mMonths = c.get(Calendar.MONTH);
		mDays = c.get(Calendar.DAY_OF_MONTH);

		// 删除图片
		image = getResources().getDrawable(R.drawable.ic_clear_28_28);

		// 用户名
		edt_name.addTextChangedListener(username_delect);
		edt_name.setOnTouchListener(username_ontouch);
		edt_name.setOnClickListener(clickListener);
		// 电话号码
		txt_phone.addTextChangedListener(phone_delect);
		txt_phone.setOnTouchListener(phone_ontouch);
		txt_phone.setOnClickListener(clickListener);
		// 身高
		txt_height.addTextChangedListener(height_delect);
		txt_height.setOnTouchListener(height_ontouch);
		txt_height.setOnClickListener(clickListener);
		// 电子邮箱
		txt_email.addTextChangedListener(email_delect);
		txt_email.setOnTouchListener(email_ontouch);
		txt_email.setOnClickListener(clickListener);
		// 体重
		txt_weight.addTextChangedListener(weight_delect);
		txt_weight.setOnTouchListener(weight_ontouch);
		txt_weight.setOnClickListener(clickListener);
		// 证件号码
		txt_idnumber.addTextChangedListener(idnumber_delect);
		txt_idnumber.setOnTouchListener(idnumber_ontouch);
		txt_idnumber.setOnClickListener(clickListener);

		// 删除工作年限
		txt_works.addTextChangedListener(work_delect);
		txt_works.setOnTouchListener(work_ontouch);
		txt_works.setOnClickListener(clickListener);
		// 获取服务器基本信息
		get_base();

	}

	// 获取服务器基本信息
	private void get_base() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG).show();
			return;
		}
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				information = informationControllers.get_base();
				handler.sendEmptyMessage(1);
			}
		}.start();

	}

	// 监听器
	private View.OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			clickListener(v);
		}

		private void clickListener(View v) {
			switch (v.getId()) {
			// 返回
			case R.id.goback:
				finish();
				break;
			// 保存
			case R.id.btn_save:
				set_Save();
				break;
			// 选择性别
			case R.id.rela_gender:
				Intent intent_Disclosure = new Intent(context,
						GendersActivity.class);
				intent_Disclosure.putExtra("gengder",
						information.getGender_text());
				startActivityForResult(intent_Disclosure, 1);
				overridePendingTransition(anim.slide_in_right,
						anim.slide_out_left);
				break;
			// 工作经验
			case R.id.rela_works:
				/*
				 * Intent intent_Experience = new Intent(context,
				 * ExperienceActivity.class);
				 * startActivityForResult(intent_Experience, 2);
				 * overridePendingTransition(android.R.anim.slide_in_left,
				 * android.R.anim.slide_out_right);
				 */
				break;
			// 求职状态
			case R.id.rela_jobstatus:
				Intent intent_JobStatus = new Intent(context,
						JobStatusActivity.class);
				startActivityForResult(intent_JobStatus, 3);
				overridePendingTransition(anim.slide_in_right,
						anim.slide_out_left);
				break;
			// 证件类型
			case R.id.rela_idtype:
				Intent intent_Certificate = new Intent(context,
						CertificateActivity.class);
				intent_Certificate.putExtra("type", information.getId_type());
				startActivityForResult(intent_Certificate, 4);
				overridePendingTransition(anim.slide_in_right,
						anim.slide_out_left);
				break;
			// 最高学历
			case R.id.rela_degree:
				Intent intent_RecordActivity = new Intent(context,
						RecordActivity.class);
				intent_RecordActivity.putExtra("degree", information.getEdu());
				startActivityForResult(intent_RecordActivity, 5);
				overridePendingTransition(anim.slide_in_right,
						anim.slide_out_left);
				break;
			// 现居地
			case R.id.rela_current_address:
				Intent addressIntent = new Intent(context,
						NowAddresActivity.class);
				addressIntent.putExtra("areaCode", areaCode);
				startActivityForResult(addressIntent, 100);
				((Activity) context).overridePendingTransition(
						anim.slide_in_right, anim.slide_out_left);
				break;
			// 户籍地
			case R.id.rela_home_address:
				Intent addressIntents = new Intent(context,
						NowAddresActivity.class);
				addressIntents.putExtra("areaCode", areaCodes);
				startActivityForResult(addressIntents, 101);
				((Activity) context).overridePendingTransition(
						anim.slide_in_right, anim.slide_out_left);
				break;
			// 用户名
			case R.id.edt_name:
				if (edt_name.length() > 1) {
					edt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					edt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 电话号码
			case R.id.txt_phone:
				if (txt_phone.length() > 1) {
					txt_phone.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					txt_phone.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 身高
			case R.id.txt_height:
				if (txt_height.length() > 1) {
					txt_height.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					txt_height.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 电子邮箱
			case R.id.txt_email:
				if (txt_email.length() > 1) {
					txt_email.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					txt_email.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 体重
			case R.id.txt_weight:
				if (txt_weight.length() > 1) {
					txt_weight.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					txt_weight.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 证件号码
			case R.id.txt_idnumber:
				if (txt_idnumber.length() > 1) {
					txt_idnumber.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					txt_idnumber.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;
			// 工作年限
			case R.id.txt_works:
				if (txt_works.length() > 1) {
					txt_works.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					txt_works.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
				break;

			}

		}
	};

	// 处理返回值
	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		switch (requestCode) {
		case 1:
			// 性别
			if (data.getStringExtra("Genders") != null) {
				txt_gender.setText(data.getStringExtra("Genders"));
			} else {
				txt_gender.setText(information.getName());
			}
			break;
		// 工作经验
		case 2:

			/*
			 * if (data.getStringExtra("Experience") != null) {
			 * txt_works.setText(data.getStringExtra("Experience")); } else {
			 * txt_works.setText(information.getWork_exps_text()); }
			 */
			break;
		// 求职状态
		case 3:
			/*
			 * if (data.getStringExtra("JobStatus") != null) {
			 * 
			 * txt_jobstatus.setText(data.getStringExtra("JobStatus")); } else {
			 * txt_jobstatus.setText(""); } break;
			 */

			// 证件类型
		case 4:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_type.setText(data.getStringExtra("Certificate"));
			}
			break;
		// 学历
		case 5:
			if (data == null || "".equals(data)) {
				return;
			} else {
				txt_degree.setText(data.getStringExtra("Record"));
			}
			break;
		// 现居地
		case 100:
			if (data == null || "".equals(data)) {
				return;
			} else {
				String area_code = data
						.getStringExtra(NowAddresActivity.AREA_CODE);
				String area_value = data
						.getStringExtra(NowAddresActivity.AREA_VALUE);
				areaValue = area_value;
				if (!StringUtils.isEmpty(area_code)) {
					txt_current_address.setText(area_value);
				}
				areaCode = area_code;
			}
			break;
		// 户籍地
		case 101:
			if (data == null || "".equals(data)) {
				return;
			} else {
				String area_codes = data
						.getStringExtra(NowAddresActivity.AREA_CODE);
				String area_values = data
						.getStringExtra(NowAddresActivity.AREA_VALUE);
				areaValues = area_values;
				if (!StringUtils.isEmpty(area_codes)) {
					txt_home_address.setText(area_values);
				}
				areaCodes = area_codes;
			}
			break;
		}
	};

	// 更新生日日期
	private void updateDisplay() {
		txt_birthday.setText(new StringBuilder()
				.append(mYear +"-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1 ) +"-": (mMonth + 1))
				.append((mDay < 10) ? "0" + mDay : mDay));

	}

	// 更新毕业时间日期
	private void updateDisplays() {
		txt_graduate.setText(new StringBuilder()
		         .append(mYears+"-")
				.append((mMonths + 1) < 10 ? "0" + (mMonths + 1)+"-": (mMonths + 1))
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
			if (txt_birthday.equals((TextView) v)) {
				msg.what = ResumeBaseInFoActivity.SHOW_DATAPICK;
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
			if (txt_graduate.equals((TextView) v)) {
				msg.what = ResumeBaseInFoActivity.SHOW_DATAPICKS;
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
			case ResumeBaseInFoActivity.SHOW_DATAPICK:
				showDialog(3);
				break;
			case ResumeBaseInFoActivity.SHOW_DATAPICKS:
				showDialog(4);
				break;
			}
		}

	};

	// 监听 显示 X图片
	private TextWatcher username_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				edt_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
						image, null);
				return;
			} else {
				edt_name.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	// 触屏
	private OnTouchListener username_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(edt_name.getText())) {
					edt_name.setText("");
					edt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					int cacheInputType = edt_name.getInputType();
					edt_name.setInputType(InputType.TYPE_NULL);
					edt_name.onTouchEvent(event);
					edt_name.setInputType(cacheInputType);
					edt_name.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听 显示 X图片
	private TextWatcher phone_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				txt_phone.setCompoundDrawablesWithIntrinsicBounds(null, null,
						image, null);
				return;
			} else {
				txt_phone.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	// 触屏
	private OnTouchListener phone_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_phone.getText())) {
					txt_phone.setText("");
					txt_phone.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					int cacheInputType = txt_phone.getInputType();
					txt_phone.setInputType(InputType.TYPE_NULL);
					txt_phone.onTouchEvent(event);
					txt_phone.setInputType(cacheInputType);
					txt_phone.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听 显示 X图片
	private TextWatcher height_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				txt_height.setCompoundDrawablesWithIntrinsicBounds(null, null,
						image, null);
				return;
			} else {
				txt_height.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	// 触屏
	private OnTouchListener height_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_height.getText())) {
					txt_height.setText("");
					txt_height.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					int cacheInputType = txt_height.getInputType();
					txt_height.setInputType(InputType.TYPE_NULL);
					txt_height.onTouchEvent(event);
					txt_height.setInputType(cacheInputType);
					txt_height.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听 显示 X图片
	private TextWatcher email_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				txt_email.setCompoundDrawablesWithIntrinsicBounds(null, null,
						image, null);
				return;
			} else {
				txt_email.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	// 触屏
	private OnTouchListener email_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_email.getText())) {
					txt_email.setText("");
					txt_email.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					int cacheInputType = txt_email.getInputType();
					txt_email.setInputType(InputType.TYPE_NULL);
					txt_email.onTouchEvent(event);
					txt_email.setInputType(cacheInputType);
					txt_email.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听 显示 X图片
	private TextWatcher weight_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				txt_weight.setCompoundDrawablesWithIntrinsicBounds(null, null,
						image, null);
				return;
			} else {
				txt_weight.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	// 触屏
	private OnTouchListener weight_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_weight.getText())) {
					txt_weight.setText("");
					txt_weight.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					int cacheInputType = txt_weight.getInputType();
					txt_weight.setInputType(InputType.TYPE_NULL);
					txt_weight.onTouchEvent(event);
					txt_weight.setInputType(cacheInputType);
					txt_weight.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return true;
				}
				break;
			}
			return false;
		}
	};
	// 监听 显示 X图片
	private TextWatcher idnumber_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				txt_idnumber.setCompoundDrawablesWithIntrinsicBounds(null,
						null, image, null);
				return;
			} else {
				txt_idnumber.setCompoundDrawablesWithIntrinsicBounds(null,
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
	private OnTouchListener idnumber_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_idnumber.getText())) {
					txt_idnumber.setText("");
					txt_idnumber.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					int cacheInputType = txt_idnumber.getInputType();
					txt_idnumber.setInputType(InputType.TYPE_NULL);
					txt_idnumber.onTouchEvent(event);
					txt_idnumber.setInputType(cacheInputType);
					txt_idnumber.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return true;
				}
				break;
			}
			return false;
		}
	};

	// 监听 显示 X图片
	private TextWatcher work_delect = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(s)) {
				txt_works.setCompoundDrawablesWithIntrinsicBounds(null, null,
						image, null);
				return;
			} else {
				txt_works.setCompoundDrawablesWithIntrinsicBounds(null, null,
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
	// 触屏
	private OnTouchListener work_ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				int curx = (int) event.getX();
				if (curx > v.getWidth() - 38
						&& !TextUtils.isEmpty(txt_idnumber.getText())) {
					txt_works.setText("");
					txt_works.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					int cacheInputType = txt_works.getInputType();
					txt_works.setInputType(InputType.TYPE_NULL);
					txt_works.onTouchEvent(event);
					txt_works.setInputType(cacheInputType);
					txt_works.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
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
