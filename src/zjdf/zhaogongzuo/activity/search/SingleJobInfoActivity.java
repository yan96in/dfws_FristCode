/**
 * Copyright © 2014年4月29日 FindJob www.veryeast.com
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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.ui.CustomPoPuDialog;
import zjdf.zhaogongzuo.ui.JobInfoItemView;
import zjdf.zhaogongzuo.utils.StringUtils;

/**单个职位详情
 *<h2> JobInfoActivity</h2>
 *<pre> 职位详细页 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月29日
 * @version 
 * @modify 
 * 
 */
public class SingleJobInfoActivity extends Activity {

	
	private Context context;
	/**职位信息页面*/
	private JobInfoItemView jobView;
	/**返回*/
	private ImageButton goback;
	/**更多*/
	private Button more;
	/**标题*/
	private TextView title;
	/**来源*/
	private int request;
	/**职位id*/
	private String jobid;
	/**举报*/
	private PopupWindow report_pop;
	/**举报*/
	private Button btn_report;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_jobinfo_single);
		context=this;
		request=getIntent().getIntExtra("from", 0);
		jobid=getIntent().getStringExtra("ids");
		initView();
		initDatas();
	}
	
	private void initView(){
		goback=(ImageButton)findViewById(R.id.goback);
		more=(Button)findViewById(R.id.more);
		title=(TextView)findViewById(R.id.title);
		jobView=(JobInfoItemView)findViewById(R.id.jobView);
		View view=getLayoutInflater().inflate(R.layout.layout_report_pop_bg, null);
		btn_report=(Button)view.findViewById(R.id.btn_report);
		goback.setOnClickListener(listener);
		more.setOnClickListener(listener);
		btn_report.setOnClickListener(listener);
		report_pop=CustomPoPuDialog.createPopupWindow(context, view, R.drawable.shap_pop_bg, R.style.pop_anim_style);
	}
	
	private void initDatas(){
		jobView.setJobInfoId(jobid);
		jobView.start();
	}
	
	
	private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v==goback) {
				finish();
			}
			if (v==more) {
				if (report_pop!=null&&!report_pop.isShowing()) {
					report_pop.showAsDropDown(more);
				}
			}
			if (v==btn_report) {
				if (StringUtils.isEmpty(jobid)) {
					CustomMessage.showToast(context, "职位不能为空！", 0);
					return;
				}
				Intent intent=new Intent(context, JobReportActivity.class);
				intent.putExtra("ids", jobid);
				startActivity(intent);
				overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				if (report_pop.isShowing()) {
					report_pop.dismiss();
				}
			}
		}
	};

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
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
