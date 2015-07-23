package zjdf.zhaogongzuo.activity.personal;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 期望薪资
 * 
 * @author Administrator
 * 
 */
public class ExpectedSalaryActivity extends Activity {
	private ImageButton micro_return;// 返回
	private Button txt_submite;// 保存
	private CheckBox chebox_According;// 显示面议
	private Spinner spinner_salry;
	private Spinner spinner_coin;
	private Spinner spinner_pay;
	private Spinner spinner_pay1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_micro_resume_salary);
		initView();
	}

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
			case R.id.micro_return:
				finish();

				break;
			// 保存
			case R.id.txt_submite:
				finish();
				break;
			default:
				break;
			}
		}
	};

	// 初始化
	private void initView() {
		micro_return = (ImageButton) findViewById(R.id.micro_return);
		txt_submite = (Button) findViewById(R.id.txt_submite);
		spinner_salry = (Spinner) findViewById(R.id.spinner_salry);
		spinner_coin = (Spinner) findViewById(R.id.spinner_coin);
		spinner_pay = (Spinner) findViewById(R.id.spinner_pay);
		spinner_pay1 = (Spinner) findViewById(R.id.spinner_pay1);

		micro_return.setOnClickListener(listener);
		txt_submite.setOnClickListener(listener);

		List<String> list = new ArrayList<String>();
		list.add("年薪");
		list.add("月薪");

		ArrayAdapter arrayAdapter = new ArrayAdapter(this,
				R.layout.layout_personal_micro_resume_salary_item,
				R.id.TextViewID, list);
		// 将ArrayAdapter添加到Spinner中去
		spinner_salry.setAdapter(arrayAdapter);
		// 设置下拉菜单的名字
		spinner_salry.setPrompt("年月薪");
		// 为spinner设置监听器
		spinner_salry.setOnItemSelectedListener(new SpinnerListener());

		// ==============================================================
		List<String> list1 = new ArrayList<String>();
		list1.add("人民币");
		list1.add("美元");
		list1.add("英镑");
		list1.add("欧元");

		ArrayAdapter arrayAdapter1 = new ArrayAdapter(this,
				R.layout.layout_personal_micro_resume_salary_item,
				R.id.TextViewID, list1);
		// 将ArrayAdapter添加到Spinner中去
		spinner_coin.setAdapter(arrayAdapter1);
		// 设置下拉菜单的名字
		spinner_coin.setPrompt("币种");
		// 为spinner设置监听器
		spinner_coin.setOnItemSelectedListener(new SpinnerListener1());

		// =============================================================================
		List<String> list2 = new ArrayList<String>();
		list2.add("不限");
		list2.add("面议");
		list2.add("1000以下");
		list2.add("1001~2000");
		list2.add("2001~3000");
		list2.add("3001~4000");
		list2.add("4001~5000");
		list2.add("5001~6000");
		list2.add("6001~8000");
		list2.add("8001~10000");
		list2.add("10001~15000");
		list2.add("15001~20000");
		list2.add("20001~30000");
		list2.add("30001~50000");
		list2.add("50000以上");
		ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,
				R.layout.layout_personal_micro_resume_salary_item,
				R.id.TextViewID, list2);
		// 将ArrayAdapter添加到Spinner中去
		spinner_pay1.setAdapter(arrayAdapter2);
		// 设置下拉菜单的名字
		spinner_pay.setPrompt("月薪");
		// 为spinner设置监听器
		spinner_pay1.setOnItemSelectedListener(new SpinnerListener2());
		// =============================================================================
		List<String> list3 = new ArrayList<String>();
		list3.add("1到2万");
		list3.add("2到3万");
		list3.add("3到5万");
		list3.add("5到8万");
		list3.add("8到10万");
		list3.add("10到20万");
		list3.add("20万到30万");
		list3.add("30万到50万");
		list3.add("45到60万");
		list3.add("60到80万");
		list3.add("80到100万");
		list3.add("100万以上");

		ArrayAdapter arrayAdapter3 = new ArrayAdapter(this,
				R.layout.layout_personal_micro_resume_salary_item,
				R.id.TextViewID, list3);
		// 将ArrayAdapter添加到Spinner中去
		spinner_pay.setAdapter(arrayAdapter3);
		// 设置下拉菜单的名字
		spinner_pay.setPrompt("年薪");
		// 为spinner设置监听器
		spinner_pay.setOnItemSelectedListener(new SpinnerListener2());

	}

	// 每当点击一个条目时就会跳转到另一个与之对应的页面
	private class SpinnerListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String selected = adapterView.getItemAtPosition(position)
					.toString();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}

	}

	// 每当点击一个条目时就会跳转到另一个与之对应的页面
	private class SpinnerListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String selected = adapterView.getItemAtPosition(position)
					.toString();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}

	}

	// 每当点击一个条目时就会跳转到另一个与之对应的页面
	private class SpinnerListener2 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String selected = adapterView.getItemAtPosition(position)
					.toString();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}

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