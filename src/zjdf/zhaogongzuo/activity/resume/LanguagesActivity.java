package zjdf.zhaogongzuo.activity.resume;

import com.umeng.analytics.MobclickAgent;

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

/**
 * 添加语言下 语言类别
 * 
 * @author Administrator
 * 
 */
public class LanguagesActivity extends Activity {

	private Context context;// 上下文
	private Button txt_more_push_submite;// 确定
	private ImageButton push_return;// 返回
	private RadioGroup radioGroup;// 单选组
	private String value = "";// 获得选择状态
	private RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13,
			r14, r15, r16, r17, r18, r19, r20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_languages_item);
		context = this;
		initView();
	}

	// 初始化 数据
	private void initView() {
		txt_more_push_submite = (Button) findViewById(R.id.txt_more_push_submite);
		push_return = (ImageButton) findViewById(R.id.push_return);
		txt_more_push_submite.setOnClickListener(listener);
		push_return.setOnClickListener(listener);
		radioGroup = (RadioGroup) findViewById(R.id.readin_grop);
		//r1 = (RadioButton) findViewById(R.id.rad_1);
		r2 = (RadioButton) findViewById(R.id.rad_2);
		r3 = (RadioButton) findViewById(R.id.rad_3);
		r4 = (RadioButton) findViewById(R.id.rad_4);
		r5 = (RadioButton) findViewById(R.id.rad_5);
		r6 = (RadioButton) findViewById(R.id.rad_6);
		r7 = (RadioButton) findViewById(R.id.rad_7);
		r8 = (RadioButton) findViewById(R.id.rad_8);
		r9 = (RadioButton) findViewById(R.id.rad_9);
		r10 = (RadioButton) findViewById(R.id.rad_10);
		r11 = (RadioButton) findViewById(R.id.rad_11);
		r12 = (RadioButton) findViewById(R.id.rad_12);
		r13 = (RadioButton) findViewById(R.id.rad_13);
		r14 = (RadioButton) findViewById(R.id.rad_14);
		r15 = (RadioButton) findViewById(R.id.rad_15);
		r16 = (RadioButton) findViewById(R.id.rad_16);
		r17 = (RadioButton) findViewById(R.id.rad_17);
		r18 = (RadioButton) findViewById(R.id.rad_18);
		r19 = (RadioButton) findViewById(R.id.rad_19);
		r20 = (RadioButton) findViewById(R.id.rad_20);

		radioGroup.setOnCheckedChangeListener(changeListener);
	}

	// 单选框
	private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
		/*	case R.id.rad_1:
				value = (String) r1.getText();
				break;
*/
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

			case R.id.rad_9:
				value = (String) r9.getText();
				break;

			case R.id.rad_10:
				value = (String) r10.getText();
				break;
			case R.id.rad_11:
				value = (String) r11.getText();
				break;

			case R.id.rad_12:
				value = (String) r12.getText();
				break;

			case R.id.rad_13:
				value = (String) r13.getText();
				break;

			case R.id.rad_14:
				value = (String) r14.getText();
				break;

			case R.id.rad_15:
				value = (String) r15.getText();
				break;
			case R.id.rad_16:
				value = (String) r16.getText();
				break;

			case R.id.rad_17:
				value = (String) r17.getText();
				break;

			case R.id.rad_18:
				value = (String) r18.getText();
				break;

			case R.id.rad_19:
				value = (String) r19.getText();
				break;

			case R.id.rad_20:
				value = (String) r20.getText();
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
				it1.putExtra("Languages", "");
				setResult(Activity.RESULT_OK, it1);
				finish();
				break;
			// 提交
			case R.id.txt_more_push_submite:
				Intent it = new Intent();
				it.putExtra("Languages", value);
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
			it1.putExtra("Languages", "");
			setResult(Activity.RESULT_OK, it1);
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent it1 = new Intent();
			it1.putExtra("Languages", "");
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
