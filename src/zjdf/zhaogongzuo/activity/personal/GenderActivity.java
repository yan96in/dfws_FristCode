package zjdf.zhaogongzuo.activity.personal;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 微简历 性别选择
 * 
 * @author Administrator
 * 
 */
public class GenderActivity extends Activity {
	private Button txt_submite;// 保存
	private ImageButton micro_return;// 返回
	private RadioButton radioButton_gender_max;// 男
	private RadioButton radioButton_gender_female;// 女
	private String value = "";// 返回值
	private RadioGroup readin_grop;// 单选组

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_micro_resume_gender);
		initView();
	}

	// 初始化
	private void initView() {
		txt_submite = (Button) findViewById(R.id.txt_submite);
		micro_return = (ImageButton) findViewById(R.id.micro_return);
		radioButton_gender_max = (RadioButton) findViewById(R.id.chebox_gender_max);
		radioButton_gender_female = (RadioButton) findViewById(R.id.chebox_gender_female);
		readin_grop = (RadioGroup) findViewById(R.id.readin_grop);

		readin_grop.setOnCheckedChangeListener(changeListener);
		txt_submite.setOnClickListener(listener);
		micro_return.setOnClickListener(listener);

	}

	// 单选事件

	private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.chebox_gender_max:
				value = (String) radioButton_gender_max.getText();
				break;
			case R.id.chebox_gender_female:
				value = (String) radioButton_gender_female.getText();
				break;
			}

		}
	};
	// 普通监听器
	private View.OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			listener(v);
		}
		private void listener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 保存
			case R.id.txt_submite:
				Intent it1 = new Intent();
				it1.putExtra("Gender", value);
				setResult(Activity.RESULT_OK, it1);
				finish();
				break;
			// 返回
			case R.id.micro_return:
				Intent it1s = new Intent();
				it1s.putExtra("Gender", value);
				setResult(Activity.RESULT_OK, it1s);
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
				it1.putExtra("Genders", "");
				setResult(Activity.RESULT_OK, it1);
				finish();
			} else if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				Intent it1 = new Intent();
				it1.putExtra("Genders", "");
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
