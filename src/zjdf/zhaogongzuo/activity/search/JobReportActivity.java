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
package zjdf.zhaogongzuo.activity.search;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.BaseActivity;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 职位举报
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月14日
 * @version v1.0.0
 * @modify 
 */
public class JobReportActivity extends BaseActivity {

	private Context context;
	private ImageButton goback;
	private Button btn_save;
	private CheckBox cbx1,cbx2,cbx3,cbx4,cbx5;
	private EditText edi_description,edt_phone,edt_email;
	
	private String  des,phone,email;
	/**职位id*/
	private String jobid;
	private PositionController controller;
	/**去报反馈状态*/
	private EorrerBean bean;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_job_report);
		context=this;
		jobid=getIntent().getStringExtra("ids");
		initView();
		controller=new PositionController(context);
	}
	
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		btn_save=(Button)findViewById(R.id.btn_save);
		cbx1=(CheckBox)findViewById(R.id.cbx1);
		cbx2=(CheckBox)findViewById(R.id.cbx2);
		cbx3=(CheckBox)findViewById(R.id.cbx3);
		cbx4=(CheckBox)findViewById(R.id.cbx4);
		cbx5=(CheckBox)findViewById(R.id.cbx5);
		edi_description=(EditText)findViewById(R.id.edi_description);
		edt_phone=(EditText)findViewById(R.id.edt_phone);
		edt_email=(EditText)findViewById(R.id.edt_email);
		goback.setOnClickListener(listener);
		btn_save.setOnClickListener(listener);
		edt_email.setText(ApplicationConfig.email);
	}
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v==goback) {
				finish();
			}
			if (v==btn_save) {
				MobclickAgent.onEvent(context, "position_search_position_info_report");
				des=edi_description.getText().toString();
				phone=edt_phone.getText().toString();
				email=edt_email.getText().toString();
				final String typeids=getReportTtpes();
				if (StringUtils.isEmpty(typeids)) {
					CustomMessage.showToast(context, "请选择举报类型!", 0);
					return;
				}
				if (StringUtils.isEmpty(des)) {
					CustomMessage.showToast(context, "情况说明不能为空!", 0);
					return;
				}
				if (StringUtils.isEmpty(phone)&&StringUtils.isEmpty(email)) {
					CustomMessage.showToast(context, "手机号码和邮箱不能同时为空!", 0);
					return;
				}
				/*if (!StringUtils.isEmpty(phone)&&!StringUtils.checkPhone(phone)) {
					CustomMessage.showToast(context, "请填写正确的手机号码!", 0);
					return;
				}
				if (!StringUtils.isEmpty(email)&&!StringUtils.checkEmail(email)) {
					CustomMessage.showToast(context, "请填写正确的邮箱号码!", 0);
					return;
				}*/
				if (!StringUtils.isEmpty(phone)) {		
					if (!StringUtils.checkPhone(phone)) {
						Toast.makeText(context, "手机号码格式不正确！", Toast.LENGTH_LONG).show();
						return;
					}
				}else  if(!StringUtils.isEmpty(email)){
					if (!StringUtils.checkEmail(email)) {
						Toast.makeText(context, "邮箱格式不正确！", Toast.LENGTH_LONG).show();
						return;
					}
				}
				
				executorService.submit(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						bean=controller.report(jobid, typeids, des, phone, email);
						if (bean!=null&&bean.status==1) {
							handler.sendEmptyMessage(1);
						}else {
							handler.sendEmptyMessage(0);
						}
					}
				});
			}
		}
	};
	
	/**
	 * 组装类型id
	 * @return
	 */
	private String getReportTtpes(){
		StringBuffer bf=new StringBuffer(5);
		if (cbx1.isChecked()) {
			bf.append("1,");
		}
		if (cbx2.isChecked()) {
			bf.append("2,");
		}
		if (cbx3.isChecked()) {
			bf.append("3,");
		}
		if (cbx4.isChecked()) {
			bf.append("4,");
		}
		if (cbx5.isChecked()) {
			bf.append("5");
		}
		if (bf.length()>0&&bf.lastIndexOf(",")==bf.length()-1) {
			bf.deleteCharAt(bf.lastIndexOf(","));
		}
		return bf.toString();
	}
	
	private Handler handler=new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what==1) {
				CustomMessage.showToast(context, "举报成功！", 0);
				finish();
			}else {
				CustomMessage.showToast(context, (bean==null?"举报失败！":bean.errMsg), 0);
			}
		}
		
	};
	
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
