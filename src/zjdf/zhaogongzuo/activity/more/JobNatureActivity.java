package zjdf.zhaogongzuo.activity.more;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;

import com.umeng.analytics.MobclickAgent;

/**
 * 更多---职位订阅-- 职位性质
 * 
 * @author Administrator
 * 
 */
public class JobNatureActivity extends Activity {

	private Button txt_more_push_submite;// 确定
	private ImageButton push_return;// 返回
	private CheckBox chebox_full_time, chebox_part_time, chebox_internship,
			chebox_temporary;// 四个单选框
	private String value, values;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_more_job_subscribe_jobnature);
		initView();
	}

	// 初始化 数据
	private void initView() {
		txt_more_push_submite = (Button) findViewById(R.id.txt_more_push_submite);
		push_return = (ImageButton) findViewById(R.id.push_return);
		txt_more_push_submite.setOnClickListener(listener);
		push_return.setOnClickListener(listener);

		chebox_full_time = (CheckBox) findViewById(R.id.chebox_full_time);
		chebox_part_time = (CheckBox) findViewById(R.id.chebox_part_time);
		chebox_internship = (CheckBox) findViewById(R.id.chebox_internship);
		chebox_temporary = (CheckBox) findViewById(R.id.chebox_temporary);

		chebox_full_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							chebox_full_time.setChecked(true);
							values = (String) buttonView.getText();
						}
						value = values;
					}
				});
		chebox_part_time
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							chebox_part_time.setChecked(true);
							values = (String) buttonView.getText();
						}
						value = values;
					}
				});
		chebox_internship
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							chebox_internship.setChecked(true);
							value = (String) buttonView.getText();
						}
						value = values;
					}
				});
		chebox_temporary
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							chebox_temporary.setChecked(true);
							value = (String) buttonView.getText();
						}
						value = values;
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
			// 返回
			case R.id.push_return:
				Intent it1 = new Intent();
				it1.putExtra("JobNature", value);
				setResult(Activity.RESULT_OK, it1);
				finish();
				break;
			// 提交
			case R.id.txt_more_push_submite:
				String result = ""; // 用来存放结果
				/* 取出选中的内容，换行显示 */
				if (chebox_full_time.isChecked())result += chebox_full_time.getText();
				if (chebox_part_time.isChecked())result += chebox_part_time.getText();
				if (chebox_internship.isChecked())result += chebox_internship.getText();
				if (chebox_temporary.isChecked())result += chebox_temporary.getText();
				if (result != "")// 判断是否为空值
				{
					value=result;
				}
				Intent it = new Intent();
				it.putExtra("JobNature", value);
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
			it1.putExtra("JobNature", "");
			setResult(Activity.RESULT_OK, it1);
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent it1 = new Intent();
			it1.putExtra("JobNature", "");
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
