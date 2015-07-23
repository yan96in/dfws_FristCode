/**
 * Copyright © 2014-3-18 FindJob www.veryeast.com
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

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.search.SingleJobInfoActivity;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**<h2>职位列表单个项 <h2>
 * <pre> </pre>
 * @author 东方网升Eilin.Yang
 * @since 2014-3-18
 * @version 
 * @modify ""
 */
public class PositionItemView extends RelativeLayout implements android.view.View.OnClickListener{

	private Context context;
	/**职位名称*/
	private  TextView txt_target_position_name;
	/**所属公司*/
	private TextView txt_target_position_company;
	/**发布日期*/
	private  TextView txt_target_position_date;
	/**薪资待遇*/
	private TextView txt_target_position_salary;
	/**所在地*/
	private TextView txt_target_position_address;
	/**是否热门*/
	private ImageView iv_target_position_hot;
	/**容器*/
	private RelativeLayout rela_item;
	
	/**职位id*/
	private String position_id;
	
	public PositionItemView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public PositionItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.layout_position_item,
				this, true);
		rela_item=(RelativeLayout)findViewById(R.id.rela_item);
		txt_target_position_name=(TextView)findViewById(R.id.txt_target_position_name);
		txt_target_position_company=(TextView)findViewById(R.id.txt_target_position_company);
		txt_target_position_date=(TextView)findViewById(R.id.txt_target_position_date);
		txt_target_position_salary=(TextView)findViewById(R.id.txt_target_position_salary);
		txt_target_position_address=(TextView)findViewById(R.id.txt_target_position_address);
		iv_target_position_hot=(ImageView)findViewById(R.id.iv_target_position_hot);
		iv_target_position_hot.setVisibility(View.INVISIBLE);
		rela_item.setOnClickListener(this);
		
	}
	
	/**
	 * 填充数据
	 *<pre>方法  </pre>
	 * @param position_id 职位id
	 * @param position_name 职位名称
	 * @param company 公司名称
	 * @param date 更新日期
	 * @param salary 薪资范围
	 * @param address 工作地点
	 * @param ishot 是否人们职位
	 */
	public void setDatas(int position_id,String position_name,String company,String date,String salary,String address,boolean ishot){
		this.position_id=position_id+"";
		txt_target_position_name.setText(position_name+"");
		txt_target_position_company.setText(company+"");
		txt_target_position_date.setText(date+"");
		if (StringUtils.isEmpty(salary)||salary.equals("0-0")||salary.equals("0")) {
			salary="面议";
		}
		txt_target_position_salary.setText(salary);
		txt_target_position_address.setText(address+"");
		if (ishot) {
			iv_target_position_hot.setVisibility(View.VISIBLE);
		}else {
			iv_target_position_hot.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 填充数据
	 *<pre>方法  </pre>
	 * @param position 职位
	 */
	public void setDatas (Position position) {
		if (position==null) {
			return;
		}
		position_id=position.getIdStr();
		txt_target_position_name.setText(position.getName());
		txt_target_position_company.setText(position.getCompanyName());
		txt_target_position_date.setText(position.getUpdateTime());
		String salary=position.getSalary();
		if (StringUtils.isEmpty(salary)||salary.equals("0-0")||salary.equals("0")) {
			salary="面议";
		}
		txt_target_position_salary.setText(salary);
		txt_target_position_address.setText(position.getAddress());
		if (position.getIs_urgent()==1) {
			iv_target_position_hot.setVisibility(View.VISIBLE);
		}else {
			iv_target_position_hot.setVisibility(View.INVISIBLE);
		}
		rela_item.setOnClickListener(this);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MobclickAgent.onEvent(context, "position_search_want_to_find");
		if (!StringUtils.isEmpty(position_id)) {
			Intent intent=new Intent(context, SingleJobInfoActivity.class);
			intent.putExtra("ids", position_id);
			context.startActivity(intent);
			((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
		}
	}

}
