package zjdf.zhaogongzuo.activity.more;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.resume.AddressActivitys;
import zjdf.zhaogongzuo.activity.resume.PositionClassActivityss;
import zjdf.zhaogongzuo.activity.search.PositionClassActivity;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.databases.sharedpreferences.JobKeeper;
import zjdf.zhaogongzuo.entity.OptionKeyValue;
import zjdf.zhaogongzuo.entity.SubPush;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * 更多 -----职位订阅
 * 
 * @author Administrator
 * 
 */
public class SubscribeActivity extends Activity {
	private ImageButton image_more_subscribe_return;// 返回
	private Button txt_more_subscribe_submit;// 保存

	private ImageButton tb_sets_subscribe_open;// 职位订阅

	private RelativeLayout set_job_relativelayout;// ְ职位类别
	private RelativeLayout set_place_relativelayout;// 工作地点
	private RelativeLayout set_salary_relativelayout;// 薪资待遇
	private RelativeLayout set_room_relativelayout;// ʳ食宿情况
	private RelativeLayout set_jodnature_relativelayout;// ְ职位性质
	private RelativeLayout set_push_relativelayout;// 推送频率
	private LinearLayout line_set;// 设置开关

	private Context context;// 上下文

	private TextView job_text_roon;// 食宿 情况
	private TextView job_text_nature;// 职位性质
	private TextView job_text_push;// 推送频率
	private TextView job_text_salary;// 薪资待遇
	private TextView job_text_place;// 工作地点
	private TextView job_text_category;// 工作类别

	/** 工作地点 */
	public static final int REQUESTCODE_ADDRESS = 0x00001011;
	private String address_code;
	private String address_value;

	/** 意向职位 */
	public static final int REQUESTCODE_POSITIONCLASS = 0x00001021;
	private String posi_code;
	private String posi_value;
	
	
	

	// 用于返回 传递值
	private String roon;// 食宿
	private String nature;// 职位性质
	private String salar;// 薪资待遇
	private String push;// 推送频率

	private int num;
	private int nums;

	/** 请求路径 */
	private int requst;

	// 转换
	private Integer number;
	private int i_num;
	private int i_nums;

	// 储存信息
	private SharedPreferences sp = null;
	private Editor editor;
	private int status;
	public static final String DATABASE = "sub_keeper";

	@SuppressWarnings({ "deprecation" })
	@SuppressLint("WorldWriteableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more_job_subscribe);
		context = this;
		requst = getIntent().getIntExtra("request", 0);
		sp = getSharedPreferences(DATABASE, Activity.MODE_WORLD_WRITEABLE);
		editor = sp.edit();
		initView();
	}

	// 初始化数据
	private void initView() {
		image_more_subscribe_return = (ImageButton) findViewById(R.id.image_more_subscribe_return);
		txt_more_subscribe_submit = (Button) findViewById(R.id.txt_more_subscribe_submit);

		tb_sets_subscribe_open = (ImageButton) findViewById(R.id.tb_sets_subscribe_open);
		set_job_relativelayout = (RelativeLayout) findViewById(R.id.set_job_relativelayout);
		set_place_relativelayout = (RelativeLayout) findViewById(R.id.set_place_relativelayout);
		set_salary_relativelayout = (RelativeLayout) findViewById(R.id.set_salary_relativelayout);
		set_room_relativelayout = (RelativeLayout) findViewById(R.id.set_room_relativelayout);
		set_jodnature_relativelayout = (RelativeLayout) findViewById(R.id.set_jodnature_relativelayout);
		set_push_relativelayout = (RelativeLayout) findViewById(R.id.set_push_relativelayout);

		// 显示返回值
		job_text_push = (TextView) findViewById(R.id.job_text_push);
		job_text_roon = (TextView) findViewById(R.id.job_text_roon);
		job_text_nature = (TextView) findViewById(R.id.job_text_nature);
		job_text_salary = (TextView) findViewById(R.id.job_text_salary);
		job_text_place = (TextView) findViewById(R.id.job_text_place);
		job_text_category = (TextView) findViewById(R.id.job_text_category);

		line_set = (LinearLayout) findViewById(R.id.line_set);
		image_more_subscribe_return.setOnClickListener(listener);
		txt_more_subscribe_submit.setOnClickListener(listener);
		tb_sets_subscribe_open.setOnClickListener(listener);
		set_job_relativelayout.setOnClickListener(listener);
		set_place_relativelayout.setOnClickListener(listener);
		set_salary_relativelayout.setOnClickListener(listener);
		set_room_relativelayout.setOnClickListener(listener);
		set_jodnature_relativelayout.setOnClickListener(listener);
		set_push_relativelayout.setOnClickListener(listener);

		get_SubKeeper();
		setPressView();
	}

	private void setPressView() {
		if (status == 0) {
			setPushClose();
			tb_sets_subscribe_open
					.setBackgroundResource(R.drawable.ic_button_cloes);
		}
		if (status == 1) {
			tb_sets_subscribe_open
					.setBackgroundResource(R.drawable.ic_button_open);
			setPushOpen();
		}
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
			case R.id.image_more_subscribe_return:
				finish();

				break;
			// 保存
			case R.id.txt_more_subscribe_submit:
				startIntent();
				finish();
				break;
			// 职位订阅 开关
			case R.id.tb_sets_subscribe_open:
				if (status == 1) {
					setPushClose();
					tb_sets_subscribe_open
							.setBackgroundResource(R.drawable.ic_button_cloes);
					status = 0;
				} else if (status == 0) {
					tb_sets_subscribe_open
							.setBackgroundResource(R.drawable.ic_button_open);
					setPushOpen();
					status = 1;
				}

				break;
			// 职位类别
			case R.id.set_job_relativelayout:
				MobclickAgent.onEvent(context,"position_subscriber_sets_classify");
				Intent classIntent = new Intent(context,PositionClassActivityss.class);
				classIntent.putExtra("posi_code", posi_code);
				classIntent.putExtra("posi_value", posi_value);
				classIntent.putExtra("request", 5);
				startActivityForResult(classIntent, REQUESTCODE_POSITIONCLASS);
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 工作地点
			case R.id.set_place_relativelayout:
				MobclickAgent.onEvent(context, "position_subscriber_sets_area");
				Intent addressIntent = new Intent(context, AddressActivitys.class);
				addressIntent.putExtra("areaCode", address_code);
				startActivityForResult(addressIntent, REQUESTCODE_ADDRESS);
				((Activity) context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 薪资待遇
			case R.id.set_salary_relativelayout:
				MobclickAgent.onEvent(context,"position_subscriber_sets_salary");
				Intent intent_SalaryActivity = new Intent(context,SalaryActivity.class);
				intent_SalaryActivity.putExtra("request", 3);
				startActivityForResult(intent_SalaryActivity, 3);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 食宿情况
			case R.id.set_room_relativelayout:
				MobclickAgent.onEvent(context,
						"position_subscriber_sets_room_board");
				Intent intent_RoonActivity = new Intent(context,
						RoonActivity.class);
				intent_RoonActivity.putExtra("request", 4);
				startActivityForResult(intent_RoonActivity, 4);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// ְ职位性质
			case R.id.set_jodnature_relativelayout:
				MobclickAgent.onEvent(context,
						"position_subscriber_sets_work_mode");
				Intent intent_JobNatureActivity = new Intent(context,
						JobNatureActivity.class);
				intent_JobNatureActivity.putExtra("request", 5);
				startActivityForResult(intent_JobNatureActivity, 5);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			// 推送频率
			case R.id.set_push_relativelayout:
				MobclickAgent.onEvent(context,
						"position_subscriber_sets_push_frequency");
				Intent intent_PushFrequencyActivity = new Intent(context,
						PushFrequencyActivity.class);
				intent_PushFrequencyActivity.putExtra("request", 6);
				startActivityForResult(intent_PushFrequencyActivity, 6);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			}
		}

	};

	/**
	 * 返回数据 处理
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// ְ意向职位
		case REQUESTCODE_POSITIONCLASS:
			if (data != null) {
				String position_code = data.getStringExtra(PositionClassActivityss.POSITION_CODES);
				String position_value = data.getStringExtra(PositionClassActivityss.POSITION_VALUES);
				posi_code = position_code == null ? "" : position_code;
				posi_value = position_value == null ? "": position_value;
			} else {
				if (!StringUtils.isEmpty(PositionClassActivityss.code)&& !StringUtils.isEmpty(PositionClassActivityss.value)) {
					posi_code = PositionClassActivityss.code;
					posi_value = PositionClassActivityss.value;
				}
			}
			job_text_category.setText(posi_value);
			break;
		// 工作地点
		case REQUESTCODE_ADDRESS:
			if (data != null) {
				String area_code = data.getStringExtra(AddressActivitys.AREA_CODE);
				String area_value = data.getStringExtra(AddressActivitys.AREA_VALUE);
				address_code = area_code == null ? "" : area_code;
				address_value = area_value == null ? "" : area_value;
			} else {
				if (!StringUtils.isEmpty(AddressActivitys.code)&& !StringUtils.isEmpty(AddressActivitys.value)) {
					address_code = AddressActivitys.code;
					address_value = AddressActivitys.value;
				}
			}
			job_text_place.setText(address_value);
			break;
		// 薪资待遇
		case 3:
			salar = data.getStringExtra("salry");
			if (data.getStringExtra("salry") != null) {
				job_text_salary.setText(salar);
			}
			break;
		// ʳ食宿情况
		case 4:
			roon = data.getStringExtra("roon");
			if (data.getStringExtra("roon") != null) {
				job_text_roon.setText(roon);
			}
			break;
		// 职位性质
		case 5:
			nature = data.getStringExtra("JobNature");
			if (data.getStringExtra("JobNature") != null) {
				job_text_nature.setText(nature);
			}
			break;
		// 推送频率
		case 6:
			push = data.getStringExtra("PushFrequency");
			if (data.getStringExtra("PushFrequency") != null) {
				job_text_push.setText(push);
			}
			break;
		}
	};

	// 数据转换
	private Integer get_salary(String value) {
		if (value == null) {
			return null;
		}
		for (OptionKeyValue element : FrameConfigures.list_salary_search) {
			if (element != null) {
				if (element.key.equals(value)) {
					return element.value;
				}
			}
		}
		return null;
	}

	// 食宿情况
	private int get_roon(String value) {
		if (roon.contains("不限")) {
			num = 0;
		} else if (roon.contains("提供食宿")) {
			num = 1;
		} else if (roon.contains("不提供")) {
			num = 2;
		} else if (roon.contains("提供吃")) {
			num = 3;
		} else if (roon.contains("提供住")) {
			num = 4;
		}
		return num;

	}

	// 职位性质nums
	private int get_nature(String value) {

		if (nature.contains("全职")) {
			nums = 1;
		} else if (nature.contains("兼职")) {
			nums = 2;
		} else if (nature.contains("实习")) {
			nums = 3;
		} else if (nature.contains("临时")) {
			nums = 4;
		}
		return nums;

	}

	// 传递返回值
	private void startIntent() {
		number =0;
		i_num = 0;
		i_nums = 0;
		if (salar != null) {
			number = get_salary(salar);
		}
		if (roon != null) {
			i_num = get_roon(roon);
		}
		if (nature != null) {
			i_nums = get_nature(nature);
		}

		Intent intent = new Intent();

		if (address_value == null || address_code == null) {
			intent.putExtra("address_code",sp.getString("address_code", address_code));
		} else {
			intent.putExtra("address_code", address_code);
			intent.putExtra("address_value", address_value);
		}

		if (posi_code == null || posi_value == null) {
			intent.putExtra("posi_code", sp.getString("posi_code", posi_code));
		} else {
			intent.putExtra("posi_code", posi_code);
			intent.putExtra("posi_value", posi_value);
		}
		if (salar == null ) {
			intent.putExtra("salar", sp.getString("salar", number + ""));
		} else {
			intent.putExtra("salar", number + "");
			intent.putExtra("salar_value", salar + "");
		}
		if ( roon == null) {
			intent.putExtra("roon", sp.getString("roon", i_num + ""));
		} else {
			intent.putExtra("roon", i_num + "");
			intent.putExtra("roon_value", roon);
		}
		if (nature == null) {
			intent.putExtra("nature", sp.getString("nature", i_nums + ""));
		} else {
			intent.putExtra("nature", i_nums + "");
			intent.putExtra("nature_value", nature);
		}
		if (push == null) {
			intent.putExtra("push", sp.getString("push", push));
		} else {
			intent.putExtra("push", push);
		}

		intent.putExtra("status", status + "");

		sub_keeper();
		this.setResult(requst, intent);
	}

	// 保存当前传递的信息，一遍下次进来获取数据显示
	private void sub_keeper() {
		SubPush subPush=new SubPush();
		
		if (address_value != null) {
			editor.putString("address_value", address_value);
			editor.putString("address_code", address_code);
			subPush.area_code=address_code;
		}
		if (posi_value != null) {
			editor.putString("posi_value", posi_value);
			editor.putString("posi_code", posi_code);
			subPush.posi_code=posi_code;
		}
		if (salar != null) {
			editor.putString("salar_value", salar);
			editor.putString("salar", number + "");
			subPush.salary=number+"";
		}
		if (roon != null) {
			editor.putString("roon_value", roon);
			editor.putString("roon", i_num + "");
			subPush.room=roon;
		}
		if (nature != null) {
			editor.putString("nature_value", nature);
			editor.putString("nature", i_nums + "");
			subPush.job_starts=i_nums + "";
		}
		if (push != null) {
			editor.putString("push", push);
			subPush.push=push;
		}
		editor.putInt("status", status);
		subPush.status=status;
		
		JobKeeper.clearJob(context);
		JobKeeper.keepPush(context, subPush);
		editor.commit();

	}

	// 读取 SharedPreferences 保存的值
	private void get_SubKeeper() {
		status = sp.getInt("status", status);
		job_text_category.setText(sp.getString("posi_value", posi_value));
		job_text_place.setText(sp.getString("address_value", address_value));
		job_text_salary.setText(sp.getString("salar_value", salar));
		job_text_nature.setText(sp.getString("nature_value", nature));
		job_text_roon.setText(sp.getString("roon_value", roon));
		job_text_push.setText(sp.getString("push", push));

	}

	// 设置
	private void setPushClose() {
		set_job_relativelayout.setEnabled(false);
		set_place_relativelayout.setEnabled(false);
		set_salary_relativelayout.setEnabled(false);
		set_room_relativelayout.setEnabled(false);
		set_jodnature_relativelayout.setEnabled(false);
		set_push_relativelayout.setEnabled(false);
		line_set.setVisibility(View.GONE);

	}

	// 设置
	private void setPushOpen() {
		line_set.setVisibility(View.VISIBLE);
		set_job_relativelayout.setEnabled(true);
		set_place_relativelayout.setEnabled(true);
		set_salary_relativelayout.setEnabled(true);
		set_room_relativelayout.setEnabled(true);
		set_jodnature_relativelayout.setEnabled(true);
		set_push_relativelayout.setEnabled(true);
	}

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
