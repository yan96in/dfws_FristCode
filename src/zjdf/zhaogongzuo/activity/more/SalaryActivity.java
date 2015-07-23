package zjdf.zhaogongzuo.activity.more;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.umeng.analytics.MobclickAgent;

/**
 * 更多---职位订阅---薪资待遇
 * 
 * @author Administrator
 * 
 */
public class SalaryActivity extends Activity {

	private Button txt_more_subscribe_submit;// 提交
	private ImageButton image_more_subscribe_return;// 返回
	private String value;// 返回数据
	private RadioGroup rad_job;// 单选组
	private RadioButton r0, r1, r2, r3, r4, r5, r6, r7, r8;// 单选按钮

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more_job_subscribe_salary);
		initView();

	}

	// 初始化
	private void initView() {
		// TODO Auto-generated method stub
		txt_more_subscribe_submit = (Button) findViewById(R.id.txt_more_subscribe_submit);
		image_more_subscribe_return = (ImageButton) findViewById(R.id.image_more_subscribe_return);
		r0 = (RadioButton) findViewById(R.id.rad_0);
		r1 = (RadioButton) findViewById(R.id.rad_1);
		r2 = (RadioButton) findViewById(R.id.rad_2);
		r3 = (RadioButton) findViewById(R.id.rad_3);
		r4 = (RadioButton) findViewById(R.id.rad_4);
		r5 = (RadioButton) findViewById(R.id.rad_5);
		r6 = (RadioButton) findViewById(R.id.rad_6);
		r7 = (RadioButton) findViewById(R.id.rad_7);
		r8 = (RadioButton) findViewById(R.id.rad_8);
		/*
		 * r9 = (RadioButton) findViewById(R.id.rad_9); r10 = (RadioButton)
		 * findViewById(R.id.rad_10); r11 = (RadioButton)
		 * findViewById(R.id.rad_11); r12 = (RadioButton)
		 * findViewById(R.id.rad_12); r13 = (RadioButton)
		 * findViewById(R.id.rad_13); r14 = (RadioButton)
		 * findViewById(R.id.rad_14);
		 */
		rad_job = (RadioGroup) findViewById(R.id.rad_job);
		rad_job.setOnCheckedChangeListener(changeListener);
		image_more_subscribe_return.setOnClickListener(listenter);
		txt_more_subscribe_submit.setOnClickListener(listenter);
	}

	// 监听器
	private View.OnClickListener listenter = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			listenter(v);
		}

		private void listenter(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 保存
			case R.id.txt_more_subscribe_submit:
				Intent it = new Intent();
				it.putExtra("salry", value);
				setResult(Activity.RESULT_OK, it);
				finish();
				break;
			// 返回
			case R.id.image_more_subscribe_return:
				Intent it1 = new Intent();
				it1.putExtra("salry", "");
				setResult(Activity.RESULT_OK, it1);
				finish();
				break;
			default:
				break;
			}
		}
	};

	// 监听返回键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent it1 = new Intent();
			//it1.putExtra("salry", "");
			setResult(Activity.RESULT_OK, it1);
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent it1 = new Intent();
			//it1.putExtra("salry", "");
			setResult(Activity.RESULT_OK, it1);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// 单选事件
	private RadioGroup.OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.rad_0:
				value = (String) r0.getText();
				break;
			case R.id.rad_1:
				value = (String) r1.getText();
				break;

			case R.id.rad_2:
				value = (String) r2.getText();
				break;

			case R.id.rad_3:
				value = (String) r3.getText();
				break;

			case R.id.rad_4:
				value = (String) r4.getText();
				break;

			case R.id.rad_5:
				value = (String) r5.getText();
				break;
			case R.id.rad_6:
				value = (String) r6.getText();
				break;

			case R.id.rad_7:
				value = (String) r7.getText();
				break;

			case R.id.rad_8:
				value = (String) r8.getText();
				break;

			/*
			 * case R.id.rad_9: value = (String) r9.getText(); break;
			 * 
			 * case R.id.rad_10: value = (String) r10.getText(); break; case
			 * R.id.rad_11: value = (String) r11.getText(); break;
			 * 
			 * case R.id.rad_12: value = (String) r12.getText(); break;
			 * 
			 * case R.id.rad_13: value = (String) r13.getText(); break;
			 * 
			 * case R.id.rad_14: value = (String) r14.getText(); break;
			 */

			}
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
