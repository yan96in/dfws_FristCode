package zjdf.zhaogongzuo.activity.resume;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.mycenter.MyResumeActivity;
import zjdf.zhaogongzuo.controllers.ResumeInformationControllers;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 求职状态
 * 
 * @author Administrator
 * 
 */
public class JobStatusActivity extends Activity {

	private RadioGroup radioGroup;// 单选组
	private RadioButton r1, r2, r3;// 三个单选按钮
	private String value = "";// 获得选择状态
	private TextView but_set;// 保存
	private ImageButton image_but_return;// 返回
	private Context context;// 上下文
	private ResumeInformationControllers controllers;
	int number;
	int flag;
	private String JobStatus;//求职状态

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_stauts);
		context = this;
		controllers = new ResumeInformationControllers(context);
		JobStatus=getIntent().getStringExtra("JobStatus");
		initView();
	}

	// 初始化数据
	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.readin_grop);
		r1 = (RadioButton) findViewById(R.id.rb_1);
		r2 = (RadioButton) findViewById(R.id.rb_2);
		r3 = (RadioButton) findViewById(R.id.rb_3);

		but_set = (TextView) findViewById(R.id.but_set);
		image_but_return = (ImageButton) findViewById(R.id.image_but_return);

		radioGroup.setOnCheckedChangeListener(changeListener);
		but_set.setOnClickListener(clickListener);
		image_but_return.setOnClickListener(clickListener);
		if (!StringUtils.isEmpty(JobStatus)) {
			if (JobStatus.contains("正在找工作")) {
				r1.setChecked(true);
			}else if (JobStatus.contains("我愿意考虑好的工作机会")){
				r2.setChecked(true);
			}else if (JobStatus.contains("暂时不想找工作")){
				r3.setChecked(true);
			}
		}
	}

	// 按钮监听器 单选框
	private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rb_1:
				value = (String) r1.getText();
				break;

			case R.id.rb_2:
				value = (String) r2.getText();
				break;

			case R.id.rb_3:
				value = (String) r3.getText();
				break;

			}

		}
	};

	// 文本监听器
	private View.OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			clickListener(v);
		}

		private void clickListener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 保存
			case R.id.but_set:

				doSet();
				
/*				Intent intents=new Intent(context,MyResumeActivity.class);
				startActivity(intents);*/
				break;
			// 返回
			case R.id.image_but_return:
				Intent it2 = new Intent();
				it2.putExtra("JobStatus", "");
				setResult(Activity.RESULT_OK, it2);
/*				Intent intent=new Intent(context,MyResumeActivity.class);
				startActivity(intent);*/
				finish();
				break;
			}
		}
	};

	// 监听返回键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent it1 = new Intent();
			it1.putExtra("JobStatus", "");
			setResult(Activity.RESULT_OK, it1);
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent it1 = new Intent();
			it1.putExtra("JobStatus", "");
			setResult(Activity.RESULT_OK, it1);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// 请求网络
	private void doSet() {
		number = 0;
		if (value.contains("正在找工作")) {
			number = 1;
		} else if (value.contains("我愿意考虑好的工作机会")) {
			number = 2;
		} else if (value.contains("暂时不想找工作")) {
			number = 3;
		}
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				flag = controllers.set_state(number);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}

	// 刷新UI
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (flag==1) {					
					Toast.makeText(context, "设置成功！", Toast.LENGTH_LONG).show();
					Intent it1 = new Intent();
					it1.putExtra("JobStatus", value);
					setResult(Activity.RESULT_OK, it1);
					finish();
				}else {
					Toast.makeText(context, "设置失败！", Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
			}
		};
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
