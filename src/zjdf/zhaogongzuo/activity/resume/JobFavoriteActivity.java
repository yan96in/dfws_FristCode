/**
 * Copyright © 2014年4月1日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity.resume;

import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.activity.mycenter.MyResumeActivity;
import zjdf.zhaogongzuo.activity.options.OptAreaActivity;
import zjdf.zhaogongzuo.activity.options.OptArriveTimeActivity;
import zjdf.zhaogongzuo.activity.options.OptIndustryActivity;
import zjdf.zhaogongzuo.activity.options.OptPositionActivity;
import zjdf.zhaogongzuo.activity.options.OptSalaryActivity;
import zjdf.zhaogongzuo.activity.options.OptStarActivity;
import zjdf.zhaogongzuo.activity.options.OptWorkTypeActivity;
import zjdf.zhaogongzuo.controllers.ResumeInformationControllers;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredCompanyType;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredJob;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredLocation;
import zjdf.zhaogongzuo.entity.ResumeIntentionDesiredPosition;
import zjdf.zhaogongzuo.entity.ResumeJobIntention;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 *<h2> 求职意向</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月1日
 * @version 
 * @modify 
 * 
 */
public class JobFavoriteActivity extends BaseActivity {

	private Context context;
	/**保存*/
	private  Button btn_save;
	 /**返回*/
	private ImageButton ibtn_goback;
	/**工作类型*/
	private LinearLayout rela_jobfavorite_jobtype;
	private TextView txt_jobfavorite_jobtype;
	/**工作地点*/
	private LinearLayout rela_jobfavorite_address;
	private TextView txt_jobfavorite_address;
	/**意向职位*/
	private LinearLayout rela_jobfavorite_favorite_position;
	private TextView txt_jobfavorite_favorite_position;
	/**意向企业*/
	private LinearLayout rela_jobfavorite_favorite_enterprise;
	private TextView txt_jobfavorite_favorite_enterprise;
	
	/**星级*/
	private LinearLayout rela_jobfavorite_star;
	private TextView txt_jobfavorite_star;
	/**到岗时间*/
	private LinearLayout rela_jobfavorite_starttime;
	private TextView txt_jobfavorite_starttime;
	/**目前薪资*/
	private LinearLayout rela_jobfavorite_haven_salary;
	private EditText txt_jobfavorite_haven_salary;
	
	/**期望薪资*/
	private LinearLayout rela_jobfavorite_expect_salary;
	private TextView txt_jobfavorite_expect_salary;
	/**求职意向*/
	private ResumeJobIntention intention;
	/**基本属性*/
	public static ResumeIntentionDesiredJob desiredJob;
	/**意向企业*/
	public static List<ResumeIntentionDesiredCompanyType> desiredCompanyTypes;
	/**意向地址*/
	public static List<ResumeIntentionDesiredLocation> desiredLocations;
	/**意向职位*/
	public static List<ResumeIntentionDesiredPosition> desiredPositions;
	
	/**控制器*/
	private ResumeInformationControllers controllers;
	
	
	/**请求路径*/
	private int request_code=0;
	
	/**工作类型*/
	public static final int REQUEST_WORK_MODE=0x0071;
	/**工作地点*/
	public static final int REQUEST_AREA=0x0072;
	
	/**意向职位*/
	public static final int REQUEST_POSITION=0x0073;
	/**意向企业所属行业*/
	public static final int REQUEST_INDUSTRY=0x0074;
	
	/**星级*/
	public static final int REQUEST_STAR=0x0075;
	/**到岗时间*/
	public static final int REQUEST_ARRIVE=0x0076;
	/**期望薪资*/
	public static final int REQUEST_DSIRED_SALARY=0x0077;
	
	/**dialog*/
	private Dialog dialog;
	/**星级*/
	public static String star_code;
	/**星级*/
	public static String star_value;
	private Drawable image = null;// 删除图片
	private EorrerBean saveEorrerBean;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myresume_jobfavorite);
		context=this;
		initView();
		setListener();
		intention=new ResumeJobIntention();
		controllers=new ResumeInformationControllers(context);
		executorService.submit(new LoadDatasRunnable());
		if (dialog!=null&&!dialog.isShowing()) {
			dialog.show();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dialog!=null&&dialog.isShowing()) {
			dialog.dismiss();
			dialog=null;
		}
	}
	
	/**
	 *<pre>初始换控件  </pre>
	 */
	private void initView() {
		// TODO Auto-generated method stub
		btn_save=(Button)findViewById(R.id.btn_save);
		ibtn_goback=(ImageButton)findViewById(R.id.goback);
		rela_jobfavorite_jobtype=(LinearLayout)findViewById(R.id.rela_jobfavorite_jobtype);
		txt_jobfavorite_jobtype=(TextView)findViewById(R.id.txt_jobfavorite_jobtype);
		rela_jobfavorite_address=(LinearLayout)findViewById(R.id.rela_jobfavorite_address);
		txt_jobfavorite_address=(TextView)findViewById(R.id.txt_jobfavorite_address);
		rela_jobfavorite_favorite_position=(LinearLayout)findViewById(R.id.rela_jobfavorite_favorite_position);
		txt_jobfavorite_favorite_position=(TextView)findViewById(R.id.txt_jobfavorite_favorite_position);
		rela_jobfavorite_favorite_enterprise=(LinearLayout)findViewById(R.id.rela_jobfavorite_favorite_enterprise);
		txt_jobfavorite_favorite_enterprise=(TextView)findViewById(R.id.txt_jobfavorite_favorite_enterprise);
		rela_jobfavorite_star=(LinearLayout)findViewById(R.id.rela_jobfavorite_star);
		txt_jobfavorite_star=(TextView)findViewById(R.id.txt_jobfavorite_star);
		rela_jobfavorite_starttime=(LinearLayout)findViewById(R.id.rela_jobfavorite_starttime);
		txt_jobfavorite_starttime=(TextView)findViewById(R.id.txt_jobfavorite_starttime);
		rela_jobfavorite_haven_salary=(LinearLayout)findViewById(R.id.rela_jobfavorite_haven_salary);
		txt_jobfavorite_haven_salary=(EditText)findViewById(R.id.txt_jobfavorite_haven_salary);
		rela_jobfavorite_expect_salary=(LinearLayout)findViewById(R.id.rela_jobfavorite_expect_salary);
		txt_jobfavorite_expect_salary=(TextView)findViewById(R.id.txt_jobfavorite_expect_salary);
		dialog=CustomMessage.createProgressDialog(context, null, false);
		// 删除图片
		image = getResources().getDrawable(R.drawable.ic_clear_28_28);
	}
	
	// 监听 显示 X图片
		private TextWatcher phone_delect = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					txt_jobfavorite_haven_salary.setCompoundDrawablesWithIntrinsicBounds(null, null,
							image, null);
					return;
				} else {
					txt_jobfavorite_haven_salary.setCompoundDrawablesWithIntrinsicBounds(null, null,
							null, null);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
		// 触屏
		private OnTouchListener phone_ontouch = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					int curx = (int) event.getX();
					if (curx > v.getWidth() - 38
							&& !TextUtils.isEmpty(txt_jobfavorite_haven_salary.getText())) {
						txt_jobfavorite_haven_salary.setText("");
						txt_jobfavorite_haven_salary.setCompoundDrawablesWithIntrinsicBounds(null,
								null, image, null);
						int cacheInputType = txt_jobfavorite_haven_salary.getInputType();
						txt_jobfavorite_haven_salary.setInputType(InputType.TYPE_NULL);
						txt_jobfavorite_haven_salary.onTouchEvent(event);
						txt_jobfavorite_haven_salary.setInputType(cacheInputType);
						txt_jobfavorite_haven_salary.setCompoundDrawablesWithIntrinsicBounds(null,
								null, image, null);
						return true;
					}
					break;
				}
				return false;
			}
		};
	
	/**
	 *<pre>设置监听 </pre>
	 */
	private void setListener() {
		// TODO Auto-generated method stub
		btn_save.setOnClickListener(listener);
		ibtn_goback.setOnClickListener(listener);
		rela_jobfavorite_jobtype.setOnClickListener(listener);
		rela_jobfavorite_address.setOnClickListener(listener);
		rela_jobfavorite_favorite_position.setOnClickListener(listener);
		rela_jobfavorite_favorite_enterprise.setOnClickListener(listener);
		rela_jobfavorite_star.setOnClickListener(listener);
		rela_jobfavorite_starttime.setOnClickListener(listener);
		rela_jobfavorite_haven_salary.setOnClickListener(listener);
		rela_jobfavorite_expect_salary.setOnClickListener(listener);
		txt_jobfavorite_haven_salary.addTextChangedListener(phone_delect);
		txt_jobfavorite_haven_salary.setOnTouchListener(phone_ontouch);
		txt_jobfavorite_haven_salary.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (txt_jobfavorite_haven_salary.length() > 1) {
					txt_jobfavorite_haven_salary.setCompoundDrawablesWithIntrinsicBounds(null,
							null, image, null);
					return;
				} else {
					txt_jobfavorite_haven_salary.setCompoundDrawablesWithIntrinsicBounds(null,
							null, null, null);
				}
			}
		});
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_AREA:
			if (intention==null) {
				intention=new ResumeJobIntention();
			}
			intention.desiredLocations=desiredLocations;
			String adds=intention.getAreasValue();
			txt_jobfavorite_address.setText(adds);
			break;

		case REQUEST_ARRIVE:
			if (data!=null) {
				desiredJob.arrival_time_key=data.getStringExtra("code");
				desiredJob.arrival_time_value=data.getStringExtra("value");
				txt_jobfavorite_starttime.setText(desiredJob.arrival_time_value);
			}
			break;
		case REQUEST_DSIRED_SALARY:
	
			if (data!=null) {
				desiredJob.desired_salary_mode_key=data.getStringExtra("mode_code");
				desiredJob.desired_salary_mode_value=data.getStringExtra("mode_value");
				desiredJob.desired_salary_currency_key=data.getStringExtra("currency_code");
				desiredJob.desired_salary_currency_value=data.getStringExtra("currency_value");
				desiredJob.desired_salary_key=data.getStringExtra("scope_code");
				desiredJob.desired_salary_value=data.getStringExtra("scope_value");
				desiredJob.desired_salary_is_show=data.getStringExtra("is_show");
				txt_jobfavorite_expect_salary.setText(desiredJob.desired_salary_value);
			}
			break;
		case REQUEST_INDUSTRY:
	
			if (desiredCompanyTypes!=null) {
				intention.desiredCompanyTypes=desiredCompanyTypes;
			}
			String iii=intention.getIndustryValue();
			txt_jobfavorite_favorite_enterprise.setText(iii);
			break;
		case REQUEST_POSITION:
	
			if (desiredLocations!=null) {
				intention.desiredPositions=desiredPositions;
			}
			String ppp=intention.getPositionsValue();
			txt_jobfavorite_favorite_position.setText(ppp);
			break;
		case REQUEST_STAR:
	
			if (data!=null) {
				star_code=data.getStringExtra("code");
				star_value=data.getStringExtra("value");
				txt_jobfavorite_star.setText(star_value);
				setupStar();
			}
			break;
		case REQUEST_WORK_MODE:
			if (data!=null) {
				desiredJob.work_mode_code=data.getStringExtra("code");
				desiredJob.work_mode_value=data.getStringExtra("value");
				txt_jobfavorite_jobtype.setText(desiredJob.work_mode_value);
			}
			break;
		}
	}
	
	/**
	 * 加载数据,初始化数据
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月10日
	 * @version v1.0.0
	 * @modify
	 */
	private class LoadDatasRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			intention=controllers.get_intentions();
			if (intention==null) {
				sendMessage(REQUEST_FAIL);
			}else {
				sendMessage(REQUEST_OK);
			}
		}
		
	}
	
	
	/**
	 * 保存求职意向
	 * @author Eilin.Yang  VearyEast 
	 * @since 2014年5月10日
	 * @version v1.0.0
	 * @modify
	 */
	private class SaveDatasRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			saveEorrerBean=controllers.setIntention(intention);
			if (saveEorrerBean!=null&&saveEorrerBean.status==1) {
				sendMessage(SAVE_OK);
			}else {
				sendMessage(SAVE_FAIL);
			}
		}
		
	}
	
	
	private Handler handler=new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			if (msg.what==REQUEST_OK) {
				setupDatas(intention);
			}
			if (msg.what==REQUEST_FAIL) {
				CustomMessage.showToast(context, "获取数据失败！", 0);
				setupDatas(null);
			}
			
			if (msg.what==SAVE_OK) {
				CustomMessage.showToast(context, "保存成功！", 0);
				finish();
			}
			
			if (msg.what==SAVE_FAIL) {
				CustomMessage.showToast(context, (saveEorrerBean==null?"保存失败！":saveEorrerBean.errMsg), 0);
			}
			
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
		
	};
	
	private void sendMessage(int what){
		Message msg=handler.obtainMessage();
		msg.what=what;
		handler.sendMessage(msg);
	}
	
	/**
	 * 初始化数据
	 */
	private void setupDatas(ResumeJobIntention intention){
		if (intention==null) {
			intention=new ResumeJobIntention();
		}
		desiredJob=intention.desiredJob;
		desiredCompanyTypes=intention.desiredCompanyTypes;
		desiredLocations=intention.desiredLocations;
		desiredPositions=intention.desiredPositions;
		txt_jobfavorite_jobtype.setText(intention.getWorkModeValue());
		txt_jobfavorite_address.setText(intention.getAreasValue());
		txt_jobfavorite_favorite_position.setText(intention.getPositionsValue());
		txt_jobfavorite_favorite_enterprise.setText(intention.getIndustryValue());
		star_code=StringUtils.isEmpty(intention.getStarCode()) ? "0":intention.getStarCode();
		star_value=StringUtils.isEmpty(intention.getStarValue()) ? "不限":intention.getStarValue();
		txt_jobfavorite_star.setText(star_value);
		txt_jobfavorite_starttime.setText(intention.getArriveTimeValue());
		final String sala=intention.getCurrentSalary();
		txt_jobfavorite_haven_salary.setText(sala);
		txt_jobfavorite_expect_salary.setText(intention.getDsiredSalaryValue()==null?"不限":intention.getDsiredSalaryValue());
		
		if (!StringUtils.isEmpty(sala)) {
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					txt_jobfavorite_haven_salary.setSelection(sala.length());
				}
			}, 120);
		}
	}
	
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent=null;
			switch (v.getId()) {
			case R.id.btn_save:
				intention.desiredCompanyTypes=desiredCompanyTypes;
				desiredJob.current_salary=txt_jobfavorite_haven_salary.getText().toString();
				intention.desiredJob=desiredJob;
				intention.desiredLocations=desiredLocations;
				intention.desiredPositions=desiredPositions;
				setupStar();
				if (intention.checkWorkModeEmpty()) {
					CustomMessage.showToast(context, "工作类型不能为空！", Gravity.CENTER, 0);
					return;
				}
				
				if (intention.checkLocationEmpty()) {
					CustomMessage.showToast(context, "工作地点不能为空！", Gravity.CENTER, 0);
					return;
				}
				
				if (intention.checkPositionEmpty()) {
					CustomMessage.showToast(context, "意向职位不能为空！", Gravity.CENTER, 0);
					return;
				}
				
				if (intention.checkIndustryEmpty()) {
					CustomMessage.showToast(context, "意向企业不能为空！", Gravity.CENTER, 0);
					return;
				}
				
				if (intention.checkCurrentSalaryEmpty()) {
					CustomMessage.showToast(context, "目前薪资不能为空！", Gravity.CENTER, 0);
					return;
				}
				  
				if (intention.checkdesiredSalaryEmpty()) {
					CustomMessage.showToast(context, "期望薪资不能为空！", Gravity.CENTER, 0);
					return;
				}
				
				if (dialog!=null&&!dialog.isShowing()) {
					dialog.show();
				}
				executorService.submit(new SaveDatasRunnable());

				//finish();
				break;
			case R.id.goback:

/*				Intent intentss=new Intent(context,MyResumeActivity.class);
				startActivity(intentss);*/

				finish();
				break;
			case R.id.rela_jobfavorite_jobtype:
				if (desiredJob==null) {
					desiredJob=new ResumeIntentionDesiredJob();
				}
				intent=new Intent(context, OptWorkTypeActivity.class);
				intent.putExtra("request", REQUEST_WORK_MODE);
				startActivityForResult(intent, REQUEST_WORK_MODE);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.rela_jobfavorite_address:
//				if (desiredLocations==null) {
//					desiredLocations=new ResumeIntentionDesiredJob();
//				}
				intent=new Intent(context, OptAreaActivity.class);
				intent.putExtra("request", REQUEST_AREA);
				startActivityForResult(intent, REQUEST_AREA);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.rela_jobfavorite_favorite_position:
	
				intent=new Intent(context, OptPositionActivity.class);
				intent.putExtra("request", REQUEST_POSITION);
				startActivityForResult(intent, REQUEST_POSITION);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.rela_jobfavorite_favorite_enterprise:
	
				intent=new Intent(context, OptIndustryActivity.class);
				intent.putExtra("request", REQUEST_INDUSTRY);
				startActivityForResult(intent, REQUEST_INDUSTRY);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.rela_jobfavorite_star:
	
				intent=new Intent(context, OptStarActivity.class);
				intent.putExtra("request", REQUEST_STAR);
				startActivityForResult(intent, REQUEST_STAR);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.rela_jobfavorite_starttime:
	
				if (desiredJob==null) {
					desiredJob=new ResumeIntentionDesiredJob();
				}
				intent=new Intent(context, OptArriveTimeActivity.class);
				intent.putExtra("request", REQUEST_ARRIVE);
				startActivityForResult(intent, REQUEST_ARRIVE);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			case R.id.rela_jobfavorite_haven_salary:
	
				break;
			case R.id.rela_jobfavorite_expect_salary:
	
				if (desiredJob==null) {
					desiredJob=new ResumeIntentionDesiredJob();
				}
				intent=new Intent(context, OptSalaryActivity.class);
				intent.putExtra("request", REQUEST_DSIRED_SALARY);
				startActivityForResult(intent, REQUEST_DSIRED_SALARY);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				break;
			}
		}
	};
	
	private void setupStar(){
		if (desiredCompanyTypes!=null&&desiredCompanyTypes.size()>0) {
			int nn=desiredCompanyTypes.size();
			for ( int i = 0; i < nn; i++ ) {
				ResumeIntentionDesiredCompanyType companyType=desiredCompanyTypes.get(i);
				companyType.star_code=star_code;
				companyType.star_value=star_value;
				desiredCompanyTypes.set(i, companyType);
			}
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
