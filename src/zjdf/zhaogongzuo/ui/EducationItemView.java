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
package zjdf.zhaogongzuo.ui;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *<h2> EducationItemView</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月1日
 * @version 
 * @modify 
 * 
 */
public class EducationItemView extends RelativeLayout implements android.view.View.OnClickListener{

	private Context context;
	/**学校名称*/
	private TextView txt_edu_item_college;
	/**时间段*/
	private TextView txt_edu_item_timequantum;
	/**教育经历ID*/
	private String educationId;
	/**容器*/
	private LinearLayout rela_edu;
	/**选择状态*/
	private CheckBox cbx_status;// 选择状态
	
	public EducationItemView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public EducationItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.layout_edu_item,
				this, true);
		rela_edu=(LinearLayout)findViewById(R.id.rela_edu);
		txt_edu_item_college=(TextView)findViewById(R.id.txt_edu_item_school);
		txt_edu_item_timequantum=(TextView)findViewById(R.id.txt_edu_item_timequantum);
		cbx_status=(CheckBox) findViewById(R.id.cbx_status);
		rela_edu.setOnClickListener(this);
	}
	
	/**
	 * 
	 *<pre>设置教育经历Id</pre>
	 * @param eduId 教育经历Id
	 */
	public void setEducationId(String eduId) {
		if (null!=eduId) {
			educationId=eduId;
		}
	}
	
	/**
	 * 
	 *<pre>填写学校的名称 </pre>
	 * @param schoolName 学校名称
	 */
	public void setSchoolName(String schoolName){
		if (null!=schoolName) {
			txt_edu_item_college.setText(schoolName);
		}
	}
	
	/**
	 * 
	 *<pre>填写所在学校的时间段 </pre>
	 * @param timeQuantum 时间段
	 */
	public void setTimeQuantum(String timeQuantum){
		if (null!=timeQuantum) {
			txt_edu_item_timequantum.setText(timeQuantum);
		}
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (!StringUtils.isEmpty(educationId)) {
			
		}
	}
}
