package zjdf.zhaogongzuo.activity.more;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.umeng.analytics.MobclickAgent;

/**
 * 更多---只为订阅---推送频率
 * 
 * @author Administrator
 * 
 */
public class PushFrequencyActivity extends Activity {

	private Context context;// 上下文
	private Button txt_more_push_submite;// 确定
	private ImageButton push_return;// 返回
	private RadioButton chebox_Every_day, chebox_Every_weeky,
			chebox_Every_weekys, chebox_Every_month;// 四个单选框
	private RadioGroup rea_group;// 单选组
	private String value;// 传递返回数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more_job_subscribe_pushfrequency);
		context = this;
		initView();
	}

	// 初始化 数据
	private void initView() {
		txt_more_push_submite = (Button) findViewById(R.id.txt_more_push_submite);
		push_return = (ImageButton) findViewById(R.id.push_return);
		txt_more_push_submite.setOnClickListener(listener);
		push_return.setOnClickListener(listener);

		chebox_Every_day = (RadioButton) findViewById(R.id.chebox_Every_day);
		chebox_Every_weeky = (RadioButton) findViewById(R.id.chebox_Every_weeky);
		chebox_Every_weekys = (RadioButton) findViewById(R.id.chebox_Every_weekys);
		chebox_Every_month = (RadioButton) findViewById(R.id.chebox_Every_month);
		rea_group = (RadioGroup) findViewById(R.id.rea_group);
		rea_group.setOnCheckedChangeListener(changeListener);
		if (value==null) {
			value="每天";
		}

	}

	// 单选组 监听器
	private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.chebox_Every_day:

				//chebox_Every_day.setChecked(true);

				MobclickAgent.onEvent(context, "position_subscriber_sets_push_frequency_day");
				value = (String) chebox_Every_day.getText();
				break;

			case R.id.chebox_Every_weeky:
				MobclickAgent.onEvent(context, "position_subscriber_sets_push_frequency_week");
				value = (String) chebox_Every_weeky.getText();
				break;

			case R.id.chebox_Every_weekys:
				MobclickAgent.onEvent(context, "position_subscriber_sets_push_frequency_2week");
				value = (String) chebox_Every_weekys.getText();
				break;

			case R.id.chebox_Every_month:
				MobclickAgent.onEvent(context, "position_subscriber_sets_push_frequency_moonth");
				value = (String) chebox_Every_month.getText();
				break;
			}

		}
	};
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
			case R.id.push_return:
				Intent it1 = new Intent();
				it1.putExtra("PushFrequency", "");
				setResult(Activity.RESULT_OK, it1);
				finish();
				break;
			// 提交
			case R.id.txt_more_push_submite:
				Intent it = new Intent();
				it.putExtra("PushFrequency", value);
				setResult(Activity.RESULT_OK, it);
				finish();
				break;
			}
		}
	};
	
	// 监听返回键
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Intent it1 = new Intent();
				it1.putExtra("PushFrequency", "");
				setResult(Activity.RESULT_OK, it1);
				finish();
			} else if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				Intent it1 = new Intent();
				it1.putExtra("PushFrequency", "");
				setResult(Activity.RESULT_OK, it1);
				finish();
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
