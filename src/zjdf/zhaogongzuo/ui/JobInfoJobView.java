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
package zjdf.zhaogongzuo.ui;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 *<h2> JobInfoJobView</h2>
 *<pre> 职位详情 职位详情 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月29日
 * @version 
 * @modify 
 * 
 */
public class JobInfoJobView extends ScrollView {

	private Context context;
	/**总的容器*/
	private ScrollView page_jobinfo;
	/**职位名称*/
	private TextView  txt_jobinfo_jobname;
	/**更新时间*/
	private TextView txt_update_time;
	/**招聘人数*/
	private TextView txt_recruit_num;
	/**企业名称*/
	private TextView txt_jobinfo_company;
	/**工作地点*/
	private TextView txt_workplace;
	/**招聘条件*/
	private TextView txt_conditions;
	/**薪资待遇*/
	private TextView txt_salary;
	/**提供食宿*/
	private TextView txt_room_board;
	/**岗位职责1*/
	private TextView txt_duty1;
	/**岗位职责2*/
	private TextView txt_duty2;
	/**显示全部*/
	private ImageButton ibtn_show_more;
	/**联系我们*/
	private JobInfoContactView contact;

	public JobInfoJobView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public JobInfoJobView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.layout_jobinfo_job,
				this, true);
		initView();
	}
	
	private void initView(){
		page_jobinfo=(ScrollView)findViewById(R.id.page_jobinfo);
		txt_jobinfo_jobname=(TextView)findViewById(R.id.txt_jobinfo_jobname);
		txt_update_time=(TextView)findViewById(R.id.txt_update_time);
		txt_recruit_num=(TextView)findViewById(R.id.txt_recruit_num);
		txt_jobinfo_company=(TextView)findViewById(R.id.txt_jobinfo_company);
		txt_workplace=(TextView)findViewById(R.id.txt_workplace);
		txt_conditions=(TextView)findViewById(R.id.txt_conditions);
		txt_salary=(TextView)findViewById(R.id.txt_salary);
		txt_room_board=(TextView)findViewById(R.id.txt_room_board);
		txt_duty1=(TextView)findViewById(R.id.txt_duty1);
		txt_duty2=(TextView)findViewById(R.id.txt_duty2);
		ibtn_show_more=(ImageButton)findViewById(R.id.ibtn_show_more);
		contact=(JobInfoContactView)findViewById(R.id.contact);
		switchMoreLayout();
	}
	
	private void switchMoreLayout(){
		int flag=txt_duty2.getVisibility();
		if (flag==View.GONE) {
			ibtn_show_more.setBackgroundResource(R.drawable.ic_jobinfo_show_more_100_28);
		}else {
			ibtn_show_more.setBackgroundResource(R.drawable.ic_jobinfo_close_more_100_28);
		}
	}
	
	/**
	 * 初始化数据
	 *<pre>方法  </pre>
	 * @param position 职位数据
	 */
	public void setDatas(Position position) {
		if (position==null) {
			return;
		}
		txt_jobinfo_jobname.setText(position.getName());
		txt_update_time.setText(position.getUpdateTime());
		String pp=position.getRecruitingNumbers();
		txt_recruit_num.setText("招聘人数："+(StringUtils.isEmpty(pp)?"不限":pp));
		txt_jobinfo_company.setText(position.getCompanyName());
		txt_workplace.setText(position.getAddress());
		txt_conditions.setText(position.getCondition());
		txt_salary.setText(position.getSalary());
		if (position.getFoodStatus().contains("null")) {
			txt_room_board.setText("");
		}else {
			
			txt_room_board.setText(position.getFoodStatus());
		}
		String des=position.getDescription();
		if (!StringUtils.isEmpty(des)) {
			String ss=Html.fromHtml(des).toString();
			if (ss.length()>80) {
				txt_duty1.setText(ss.substring(0, ss.length()/2));
				txt_duty2.setText(ss.substring(ss.length()/2));
			}else {
				txt_duty1.setText(ss);
				txt_duty2.setText("");
			}
		}
		contact.setDatas(position.getContacts(), position.getTelephone(), position.getPhone(), position.getEmail(), position.getCompanyaddress(),position.getCompanyName(),position.getLongitude(),position.getLatitude());
		txt_duty2.setVisibility(View.GONE);
		ibtn_show_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int vg=(txt_duty2.getVisibility()==View.GONE) ? View.VISIBLE : View.GONE ;
				txt_duty2.setVisibility(vg);
				switchMoreLayout();
			}
		});
	}
	
}
