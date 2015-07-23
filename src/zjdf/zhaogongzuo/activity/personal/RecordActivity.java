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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 学历
 * 
 * @author Administrator
 * 
 */
public class RecordActivity extends Activity {
	private Button txt_submite;// 保存
	private ImageButton micro_return;// 返回
	private RadioGroup readin_grop;// 单选组
	private String value="";//返回数据
	private RadioButton radb_1,radb_2,radb_3,radb_4,radb_5,radb_6,radb_7,radb_8;
	private int degree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_micro_resume_record);
		degree=getIntent().getIntExtra("degree", 0);
		initView();
	}

	// 初始化
	private void initView() {
		txt_submite = (Button) findViewById(R.id.txt_submite);
		micro_return = (ImageButton) findViewById(R.id.micro_return);
		readin_grop = (RadioGroup) findViewById(R.id.readin_grop);
		radb_1=(RadioButton) findViewById(R.id.radb_1);
		radb_2=(RadioButton) findViewById(R.id.radb_2);
		radb_3=(RadioButton) findViewById(R.id.radb_3);
		radb_4=(RadioButton) findViewById(R.id.radb_4);
		radb_5=(RadioButton) findViewById(R.id.radb_5);
		radb_6=(RadioButton) findViewById(R.id.radb_6);
		radb_7=(RadioButton) findViewById(R.id.radb_7);
		radb_8=(RadioButton) findViewById(R.id.radb_8);

		// 点击事件
		txt_submite.setOnClickListener(listener);
		micro_return.setOnClickListener(listener);

		
		readin_grop.setOnCheckedChangeListener(changeListener);
		if (degree==1) {
			radb_1.setChecked(true);			
		}else if(degree==2){
			radb_2.setChecked(true);
		}else if(degree==3){
			radb_3.setChecked(true);
		}else if(degree==4){
			radb_4.setChecked(true);
		}else if(degree==5){
			radb_5.setChecked(true);
		}else if(degree==6){
			radb_6.setChecked(true);
		}else if(degree==7){
			radb_7.setChecked(true);
		}else if(degree==8){
			radb_8.setChecked(true);
		}
		
	}

	// 点击事件
	private View.OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			listener(v);

		}

		private void listener(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 提交
			case R.id.txt_submite:
				Intent it1 = new Intent();
				it1.putExtra("Record", value);
				setResult(Activity.RESULT_OK, it1);
				finish();
				break;
			// 返回
			case R.id.micro_return:
/*				Intent it1s = new Intent();
				it1s.putExtra("Record","");
				setResult(Activity.RESULT_OK, it1s);*/
				finish();
				break;
			default:
				break;
			}
		}
	};
	// 单选组 事件
	private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radb_1:
				value=(String) radb_1.getText();
				break;
			case R.id.radb_2:
				value=(String) radb_2.getText();
				break;
			case R.id.radb_3:
				value=(String) radb_3.getText();
				break;
			case R.id.radb_4:
				value=(String) radb_4.getText();
				break;
			case R.id.radb_5:
				value=(String) radb_5.getText();
				break;
			case R.id.radb_6:
				value=(String) radb_6.getText();
				break;
			case R.id.radb_7:
				value=(String) radb_7.getText();
				break;
			case R.id.radb_8:
				value=(String) radb_8.getText();
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
			//it1.putExtra("Record", "");
			setResult(Activity.RESULT_OK, it1);
			finish();
		} else if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent it1 = new Intent();
			//it1.putExtra("Record",  "");
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
