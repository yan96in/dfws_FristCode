package zjdf.zhaogongzuo.activity.resume;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 基本信息里的  性别选择
 * @author Administrator
 *
 */
public class GendersActivity extends Activity {
	private RadioGroup radioGroup;// 单选组
	private RadioButton r1, r2, r3;// 三个单选按钮
	private String value = "";// 获得选择状态
	private TextView but_set;// 保存
	private ImageButton image_but_return;// 返回
	private String  gengder;//性别
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_resume_gender);
		gengder=getIntent().getStringExtra("gengder");
		initView();
	}
	// 初始化数据
	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.readin_grop);
		r1 = (RadioButton) findViewById(R.id.rb_1);
		r2 = (RadioButton) findViewById(R.id.rb_2);
		//r3 = (RadioButton) findViewById(R.id.rb_3);

		but_set = (TextView) findViewById(R.id.but_set);
		image_but_return = (ImageButton) findViewById(R.id.image_but_return);

		radioGroup.setOnCheckedChangeListener(changeListener);
		but_set.setOnClickListener(clickListener);
		image_but_return.setOnClickListener(clickListener);
		
		if (gengder.contains("男")) {
			r1.setChecked(true);
		}else if (gengder.contains("女")){
			r2.setChecked(true);
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
				Intent it1 = new Intent();
				it1.putExtra("Genders", value);
				setResult(Activity.RESULT_OK, it1);
				finish();
				break;
			// 返回
			case R.id.image_but_return:
				Intent it2 = new Intent();
				it2.putExtra("Genders", value);
				setResult(Activity.RESULT_OK, it2);
				finish();
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
