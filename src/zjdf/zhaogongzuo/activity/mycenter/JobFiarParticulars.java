package zjdf.zhaogongzuo.activity.mycenter;
import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.activity.options.CompanyInfoActivity;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.JobFair;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.DateTimeUtils;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 招聘会 详情
 * 
 * @author Administrator
 * 
 */
public class JobFiarParticulars extends BaseActivity {
	private int meet_id;// 接收返回过来的ID
	private Context context;// 上下文
	private TextView txt_name;// 标题
	private TextView txt_name1;// 名字
	private TextView txt_time_end;// 招聘时间
	private TextView txt_time_beg;// 招聘时间
	private TextView txt_addres;// 招聘地点
	private Button btn_clock;// 闹钟
	private ImageButton but_return;// 返回
	private MyResumeConttroller myResumeConttroller;// 控制器
	private JobFair jobFair;// 招聘会对象
	private JobFair jobFair2;// 接收返回百度地图坐标数据
	private LinearLayout list_line;// 动态显示在线围观企业
	private RelativeLayout rel_map;// map
	private String longitude;// 经度
	private String latitude;// 纬度
	private String name;// 名字
	private Dialog dialog;
	private ImageButton txt_map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycenter_jobfiarparticulars);
		context = this;
		myResumeConttroller = new MyResumeConttroller(context);
		meet_id = getIntent().getIntExtra("meet_id", 0);
		initView();
		loadDatas();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (jobFair != null) {
					//txt_name.setText(jobFair.getName());
					String stime=jobFair.getBegin_time();
					String etime=jobFair.getEnd_time();
					String sdate="";
					String sstime="";
					int week=1;
					if (!StringUtils.isEmpty(stime)&&!StringUtils.isEmpty(etime)) {
						if (DateTimeUtils.getDiffer00(stime)==DateTimeUtils.getDiffer00(etime)) {
							etime=DateTimeUtils.getCustomDateTime(etime, "HH:mm");
						}else {
							etime=DateTimeUtils.getCustomDateTime(etime, "yyyy-MM-dd HH:mm");
						}
						week=DateTimeUtils.getWeek(stime);
//						stime=DateTimeUtils.getCustomDateTime(stime, "yyyy-MM-dd HH:mm");
						sdate=DateTimeUtils.getCustomDateTime(stime, "yyyy-MM-dd");
						sstime=DateTimeUtils.getCustomDateTime(stime, "HH:mm");
					}
					txt_name1.setText(jobFair.getName());
					txt_time_beg.setText(sdate+" 星期"+(week==0?"天":week)+" "+sstime);
					txt_time_end.setText(etime);
					txt_addres.setText(jobFair.getAddress());
					longitude = jobFair.getLongitude();
					latitude = jobFair.getLatitude();
					if (StringUtils.isEmpty(longitude)||StringUtils.isEmpty(latitude)||"null".equalsIgnoreCase(longitude)||"null".equalsIgnoreCase(latitude)||!StringUtils.checkDecimals(longitude)||!StringUtils.checkDecimals(latitude)) {
						txt_map.setVisibility(View.GONE);
					}
					name = jobFair.getName();
					LinearLayout.LayoutParams params = null;
					for (final Company company : jobFair.getListCompanies()) {
						TextView txt = new TextView(context);
						txt.setText(company.getName());
						txt.setTextColor(Color.BLACK);
						txt.setTextSize(16);
						txt.setPadding(0, 5, 0, 0);
						txt.setBackgroundResource(R.drawable.selector_item_bg);
						params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
						params.leftMargin=15;
						params.bottomMargin=8;
						txt.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								String company_id=company.getId();
								Intent intent = new Intent(context,CompanyInfoActivity.class);
								intent.putExtra("company_id", company_id);
								startActivity(intent);
							}
						});
						list_line.addView(txt, params);
					}
				}else
					CustomMessage.showToast(context, "获取数据失败，请重试", Gravity.CENTER, 0);
				break;
			}
			if (dialog!=null&&dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	};

	// 初始化数据
	private void initView() {
		// TODO Auto-generated method stub
		txt_name = (TextView) findViewById(R.id.txt_name);
		txt_name1 = (TextView) findViewById(R.id.txt_name1);
		txt_time_beg = (TextView) findViewById(R.id.txt_time_beg);
		txt_time_end = (TextView) findViewById(R.id.txt_time_end);
		txt_addres = (TextView) findViewById(R.id.txt_addres);
		btn_clock = (Button) findViewById(R.id.btn_clock);
		but_return = (ImageButton) findViewById(R.id.but_return);
		list_line = (LinearLayout) findViewById(R.id.list_line);
		rel_map = (RelativeLayout) findViewById(R.id.rel_map);
		txt_map=(ImageButton)findViewById(R.id.txt_map);
		rel_map.setOnClickListener(listener);
		but_return.setOnClickListener(listener);
		btn_clock.setOnClickListener(listener);
		dialog=CustomMessage.createProgressDialog(context, null, false);
	}
	
	private void loadDatas(){
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomMessage.showToast(context, "网络已断开，请检查网络！", Gravity.CENTER, 0);
			return;
		}
		if (dialog!=null&&!dialog.isShowing()) {
			dialog.show();
		}
		// 请求网络 显示 数据
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				jobFair = myResumeConttroller.getJobmeet_detail(meet_id);
				handler.sendEmptyMessage(1);
			}
		});
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
			case R.id.rel_map:
				if (StringUtils.isEmpty(longitude)||StringUtils.isEmpty(latitude)||"null".equalsIgnoreCase(longitude)||"null".equalsIgnoreCase(latitude)||!StringUtils.checkDecimals(longitude)||!StringUtils.checkDecimals(latitude)) {
					return;
				}
				Intent intent = new Intent(context, LocsMapActivity.class);
				intent.putExtra("longitude", Double.parseDouble(longitude));
				intent.putExtra("latitude", Double.parseDouble(latitude));
				intent.putExtra("name", name);
				startActivity(intent);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;

			case R.id.btn_clock:
				if (meet_id==0) {
					return;
				}
				Intent intents = new Intent(context, ClockActivity.class);
				intents.putExtra("meetid", meet_id);
				startActivity(intents);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.but_return:
				finish();
				break;
			}
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
