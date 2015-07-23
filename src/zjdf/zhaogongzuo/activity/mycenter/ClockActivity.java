package zjdf.zhaogongzuo.activity.mycenter;

import java.util.Calendar;
import java.util.Date;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.databases.sharedpreferences.SetsKeeper;
import zjdf.zhaogongzuo.service.AlarmReceiver;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.DateTimeUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 闹钟
 * 
 * @author Administrator
 * 
 */
public class ClockActivity extends Activity {
	private RelativeLayout r1;// 提醒开关
	private RelativeLayout r2;// 闹钟设置
	private TextView tet_clock_time;// 显示时间的
	private Button but_set;// 保存
	private ImageButton image_but_return;// 返回
	private ImageButton ibtn_open_close;// 闹钟开关
	private Context context;// 上下文
	private int state ;
	private int meetid;
	private AlarmManager alarmManager;
	private Intent intent;
	private String time;
	private PendingIntent pendingIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_clock);
		context = this;
		meetid = getIntent().getIntExtra("meetid", 0);
		alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
		intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("meetid", meetid);
		// 建立Intent和PendingIntent来调用闹钟管理器
		pendingIntent = PendingIntent.getBroadcast(ClockActivity.this, meetid,intent, 0);
		state=SetsKeeper.readJobfairAlarmState(context, meetid+"");
		time=SetsKeeper.readJobfairAlarmTime(context, meetid+"");
		initView();
	}

	// 初始化数据
	@SuppressLint("CutPasteId")
	private void initView() {
		// TODO Auto-generated method stub
		r1 = (RelativeLayout) findViewById(R.id.rel_clock_1);
		r2 = (RelativeLayout) findViewById(R.id.rel_clock_2);
		tet_clock_time = (TextView) findViewById(R.id.tet_clock_time);
		but_set = (Button) findViewById(R.id.but_set);
		image_but_return = (ImageButton) findViewById(R.id.image_but_return);
		ibtn_open_close = (ImageButton) findViewById(R.id.ibtn_open_close);
		ibtn_open_close.setOnClickListener(listener);
		but_set.setOnClickListener(listener);
		image_but_return.setOnClickListener(listener);
		r2.setOnClickListener(listener);
		if (!StringUtils.isEmpty(time)) {
			tet_clock_time.setText(time);
		}else {
			if (state==1) {
				time=DateTimeUtils.getCustomDateTime(DateTimeUtils.getLongDateTime(true), "yyyy-MM-dd HH:mm");
			}else {
				time="";
			}
			tet_clock_time.setText(time);
		}
		switchOpenClose();
	}
	
	/**
	 * 开关状态
	 */
	private void switchOpenClose(){
		if (state==1) {
			ibtn_open_close.setBackgroundResource(R.drawable.ic_button_open);
			r2.setEnabled(true);
		}else {
			ibtn_open_close.setBackgroundResource(R.drawable.ic_button_cloes);
			r2.setEnabled(false);
		}
	}

	private void initClock(Calendar cal){
		alarmManager.setRepeating(
				AlarmManager.RTC_WAKEUP,
				cal.getTimeInMillis(), 10000,
				pendingIntent);
	}

	private void createDateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(this, R.layout.layout_dialog_datetime, null);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.date_picker);
		final TimePicker timePicker = (android.widget.TimePicker) view
				.findViewById(R.id.time_picker);
		builder.setView(view);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), null);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
		builder.setTitle("选取日期时间");
		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d",
								datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						sb.append(" ");
						sb.append(timePicker.getCurrentHour()).append(":")
								.append(timePicker.getCurrentMinute());
						tet_clock_time.setText(sb);
						Date date=DateTimeUtils.getDate(sb.toString()+":00");
						if (date.getTime()<System.currentTimeMillis()) {
							CustomMessage.showToast(context, "时间日期不能小于当前日期时间！", Gravity.CENTER, 0);
						}
						time=sb.toString();
						calendar.set(Calendar.YEAR, datePicker.getYear());
						calendar.set(Calendar.MONTH,datePicker.getMonth());
						calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
						calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
						calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);
						initClock(calendar);
						dialog.cancel();
					}
				});
		Dialog dialog = builder.create();
		dialog.show();
	}

	// 监听器
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 闹钟提醒开关
			case R.id.ibtn_open_close:
				if (state == 1) {
					cancelClock();
					state = 0;
				} else {
					state = 1;
					if (!StringUtils.isEmpty(time)) {
						if (System.currentTimeMillis()<DateTimeUtils.getDate(time+":00").getTime()) {
							initClock(DateTimeUtils.getCalendar(time+":00"));
							CustomMessage.showToast(context, "闹钟已经开启！", Gravity.CENTER, 0);
						}else {
							CustomMessage.showToast(context, "时间已过，请重新设定时间！", Gravity.CENTER, 0);
						}
					}else {
						createDateDialog();
					}
				}
				SetsKeeper.keepJobfairAlarmState(context, state, meetid+"");
				switchOpenClose();
				break;

			// 闹钟设置
			case R.id.rel_clock_2:
				createDateDialog();
				break;
			// 保存
			case R.id.but_set:
				if (!StringUtils.isEmpty(time)) {
					SetsKeeper.keepJobfairAlarmTime(context, time, meetid+"");
				}
				finish();
				break;
			// 返回
			case R.id.image_but_return:
				finish();
				break;
			default:
				break;
			}
		}
	};

	// 取消闹钟
	private void cancelClock() {
		// 获取闹钟管理器
		alarmManager.cancel(pendingIntent);
		CustomMessage.showToast(context, "闹钟已经取消！", Gravity.CENTER, 0);
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
