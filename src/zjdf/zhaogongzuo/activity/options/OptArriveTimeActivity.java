/**
 * Copyright © 2014年5月10日 FindJob www.veryeast.com
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

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.mycenter.JobFairActivity;
import zjdf.zhaogongzuo.activity.resume.JobFavoriteActivity;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.entity.OptionKeyValue;
import zjdf.zhaogongzuo.ui.ResumeOptItemView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 到岗时间选项
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月10日
 * @version v1.0.0
 * @modify 
 */
public class OptArriveTimeActivity extends Activity {

	private Context context;
	private Bundle bundle;
	/**请求来源*/
	private String from;
	/**请求来源*/
	private int request;
	
	/**返回*/
	private ImageButton goback;
	
	/**容器*/
	private LinearLayout linear_container;
	/**容器*/
	private List<ResumeOptItemView> itemViews;
	private TextView title;
	/**上一次选中的选项编码*/
	public static String code;
	/**上一次选中的选项值*/
	public static String value;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_opt_work_type);
		context=this;
		request=getIntent().getIntExtra("request", 0);
		initView();
		initDatas();
	}

	/**
	 * 
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
		if (FrameConfigures.list_arrival_time!=null&&FrameConfigures.list_arrival_time.size()>0) {
			int nn=FrameConfigures.list_arrival_time.size();
			itemViews=new ArrayList<ResumeOptItemView>(nn);
			for (int i=0; i< nn; i++) {
				final OptionKeyValue item=FrameConfigures.list_arrival_time.get(i);
				if (item!=null) {
					final ResumeOptItemView itemView=new ResumeOptItemView(context);
					final String key=item.key;
					final String val=item.value+"";
					itemView.setKeyValue( key, val);
					if (val.equals(JobFavoriteActivity.desiredJob.arrival_time_key)) {
						itemView.setChecked(true);
					}
					itemView.setItemClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if (itemView.getIsChecked()) {
								code="";
								value="";
								itemView.setChecked(false);
							}else {
								code=key;
								value=val;
								startResult(code,value);
							}
						}
					});
					itemViews.add(itemView);
					linear_container.addView(itemViews.get(i));
				}
			}
		}
	}

	/**
	 * 
	 */
	private void initView() {
		goback=(ImageButton)findViewById(R.id.goback);
		title=(TextView)findViewById(R.id.title);
		linear_container=(LinearLayout)findViewById(R.id.linear_container);
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				startResult("不限","0");
				finish();
			}
		});
		title.setText(R.string.myresume_intention_arrive);
	}
	
	/**清除选中状态*/
	private void clearStates(){
		if (itemViews!=null&&itemViews.size()>0) {
			for (ResumeOptItemView view : itemViews) {
				view.setChecked(false);
			}
		}
	}
	
	/**
	 * 结束页面
	 * @param code 编码
	 * @param val 值
	 */
    private void startResult(String code,String val){
    	Intent intent=new Intent();
    	intent.putExtra("code", val);
    	intent.putExtra("value", code);
    	this.setResult(request,intent);
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
