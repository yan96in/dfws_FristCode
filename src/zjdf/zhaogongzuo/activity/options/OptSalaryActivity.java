/**
 * Copyright © 2014年5月14日 FindJob www.veryeast.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package zjdf.zhaogongzuo.activity.options;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import zjdf.zhaogongzuo.activity.resume.JobFavoriteActivity;
import zjdf.zhaogongzuo.configures.enums.SalaryEnum;
import zjdf.zhaogongzuo.ui.SalarySelectDialog;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 期望薪资
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月14日
 * @version v1.0.0
 * @modify 
 */
public class OptSalaryActivity extends Activity {

	
	private Context context;
	/**薪资类型*/
	public static String mode_code="1";
	public static String mode_value="月薪";
	/**货币类型*/
	public static String currency_code="1";
	public static String currency_value="人民币";
	/**薪资范围*/
	public static String scope_code="0";
	public static String scope_value="不限";
	/**是否显示*/
	public static String is_show="2";
	
	private ImageButton goback;
	
	private Button save;
	
	/**薪资类型*/
	private LinearLayout rela_salary_mode;
	private TextView txt_salary_mode;
	/**货币类型*/
	private LinearLayout rela_salary_currency;
	private TextView txt_salary_currency;
	/**薪资范围*/
	private LinearLayout rela_salary_scope;
	private TextView txt_salary_scope;
	/**是否公开*/
	private CheckBox cbx;
	/**选项对话框*/
	private SalarySelectDialog selectionsDialog;
	
	private int request=0;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_opt_desired_salary);
		context=this;
		request=getIntent().getIntExtra("request", 0);
		initView();
		initDialog();
	}
	
	
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		save=(Button)findViewById(R.id.btn_save);
		rela_salary_mode=(LinearLayout)findViewById(R.id.rela_salary_mode);
		txt_salary_mode=(TextView)findViewById(R.id.txt_salary_mode);
		rela_salary_currency=(LinearLayout)findViewById(R.id.rela_salary_currency);
		txt_salary_currency=(TextView)findViewById(R.id.txt_salary_currency);
		rela_salary_scope=(LinearLayout)findViewById(R.id.rela_salary_scope);
		txt_salary_scope=(TextView)findViewById(R.id.txt_salary_scope);
		cbx=(CheckBox)findViewById(R.id.cbx);
		goback.setOnClickListener(listener);
		save.setOnClickListener(listener);
		rela_salary_currency.setOnClickListener(listener);
		rela_salary_mode.setOnClickListener(listener);
		rela_salary_scope.setOnClickListener(listener);
		mode_code=JobFavoriteActivity.desiredJob.desired_salary_mode_key==null?"1":JobFavoriteActivity.desiredJob.desired_salary_mode_key;
		mode_value=JobFavoriteActivity.desiredJob.desired_salary_mode_value==null?"月薪":JobFavoriteActivity.desiredJob.desired_salary_mode_value;
		currency_code=JobFavoriteActivity.desiredJob.desired_salary_currency_key==null?"1":JobFavoriteActivity.desiredJob.desired_salary_currency_key;
		currency_value=JobFavoriteActivity.desiredJob.desired_salary_currency_value==null?"人民币":JobFavoriteActivity.desiredJob.desired_salary_currency_value;
		scope_code=JobFavoriteActivity.desiredJob.desired_salary_key==null?"1":JobFavoriteActivity.desiredJob.desired_salary_key;
		scope_value=JobFavoriteActivity.desiredJob.desired_salary_value==null?"不限":JobFavoriteActivity.desiredJob.desired_salary_value;
		is_show=JobFavoriteActivity.desiredJob.desired_salary_is_show==null?"2":JobFavoriteActivity.desiredJob.desired_salary_is_show;
		cbx.setChecked(is_show.contains("2"));
		txt_salary_mode.setText(mode_value);
		txt_salary_currency.setText(currency_value);
		txt_salary_scope.setText((!StringUtils.isEmpty(scope_value)&scope_value.contains("50000-1000000"))?"50000以上":scope_value);
		cbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					is_show="2";
				}else {
					is_show="1";
				}
			}
		});
	}
	
	private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==goback) {
				finish();
			}
			if (v==save) {
				startIntents();
			}
			if (v==rela_salary_mode) {
				selectionsDialog.show(SalaryEnum.SALARY_MODE);
			}
			if (v==rela_salary_currency) {
				selectionsDialog.show(SalaryEnum.SALARY_CURRENCY);
			}
			if (v==rela_salary_scope) {
				selectionsDialog.show(SalaryEnum.SALARY_SCOPE);
			}
		}
	};
	
	
	private void initDialog(){
		
		selectionsDialog=new SalarySelectDialog(context);
		selectionsDialog.setOnDataFlashListener(new OptSalaryActivity.IflashViewsCallback() {
			
			@Override
			public void doFlash(SalaryEnum opt, String key, Integer value) {
				// TODO Auto-generated method stub
				if (StringUtils.isEmpty(key)) {
					return;
				}
				switch (opt) {
				case SALARY_MODE:
					if (!mode_value.equalsIgnoreCase(key)) {
						scope_code="0";
						scope_value="不限";
						txt_salary_scope.setText(scope_value);
					}
					mode_code=value+"";
					mode_value=key;
					txt_salary_mode.setText(key);
					break;

				case SALARY_CURRENCY:
					currency_code=value+"";
					currency_value=key;
					txt_salary_currency.setText(key);
					break;
				case SALARY_SCOPE:
					scope_code=value+"";
					scope_value=key;
					txt_salary_scope.setText(key);
					break;
				}
			}
		});
	}
	
	/**
	 * 刷新界面
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月14日
	 * @version v1.0.0
	 * @modify
	 */
	public static interface IflashViewsCallback{
		void doFlash(SalaryEnum opt,String key,Integer value);
	}
	
	private  void startIntents(){
		Intent intent=new Intent();
		intent.putExtra("mode_code", mode_code);
		intent.putExtra("mode_value", mode_value);
		intent.putExtra("currency_code", currency_code);
		intent.putExtra("currency_value", currency_value);
		intent.putExtra("scope_code", scope_code);
		intent.putExtra("scope_value", scope_value);
		intent.putExtra("is_show", is_show);
		this.setResult(request, intent);
		finish();
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
