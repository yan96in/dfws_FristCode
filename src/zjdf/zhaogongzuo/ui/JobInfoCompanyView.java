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

import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.search.PositionSingleListActivity;
import zjdf.zhaogongzuo.controllers.MyResumeConttroller;
import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 *<h2> JobInfoCompanyView</h2>
 *<pre> 职位详情， 企业详情 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月29日
 * @version 
 * @modify 
 * 
 */
public class JobInfoCompanyView extends ScrollView {

	private Context context;
	/**容器*/
	private ScrollView page_companyinfo;
	/**企业名称*/
	private TextView txt_company;
	/**行业*/
	private TextView txt_industry;
	/**星级*/
	private TextView txt_star_level;
	/**企业性质*/
	private TextView txt_company_mode;
	/**规模*/
	private TextView txt_scope;
	/**雇主指数*/
	private TextView txt_degrees;
	/**企业介绍1*/
	private TextView txt_company_describle1;
	/**企业介绍 2*/
	private TextView txt_company_describle2;
	/**显示更多*/
	private ImageButton ibtn_company_show_more;
	/**联系我们*/
	private JobInfoContactView contact;
	/**其他职位*/
	private RelativeLayout rela_other_positions;
	/**公司详情*/
	private Company mCompany;

	/**简历控制器*/
	private MyResumeConttroller resumeConttroller;
	/**数据集*/
	private List<Position> positions;
	public JobInfoCompanyView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public JobInfoCompanyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		resumeConttroller=new MyResumeConttroller(context);
		LayoutInflater.from(context).inflate(R.layout.layout_jobinfo_company,
				this, true);
		initView();
	}
	
	private void initView(){
		page_companyinfo=(ScrollView)findViewById(R.id.page_companyinfo);
		txt_company=(TextView)findViewById(R.id.txt_company);
		txt_industry=(TextView)findViewById(R.id.txt_industry);
		txt_star_level=(TextView)findViewById(R.id.txt_star_level); 
		txt_company_mode=(TextView)findViewById(R.id.txt_company_mode);
		txt_scope=(TextView)findViewById(R.id.txt_scope);
		txt_degrees=(TextView)findViewById(R.id.txt_degrees);
		txt_company_describle1=(TextView)findViewById(R.id.txt_company_describle1);
		txt_company_describle2=(TextView)findViewById(R.id.txt_company_describle12);
		ibtn_company_show_more=(ImageButton)findViewById(R.id.ibtn_company_show_more);
		contact=(JobInfoContactView)findViewById(R.id.contact);
		rela_other_positions=(RelativeLayout)findViewById(R.id.rela_other_positions);
		switchMoreLayout();
	}
	
	private void switchMoreLayout(){
		int flag=txt_company_describle2.getVisibility();
		if (flag==View.GONE) {
			ibtn_company_show_more.setBackgroundResource(R.drawable.ic_jobinfo_show_more_100_28);
		}else {
			ibtn_company_show_more.setBackgroundResource(R.drawable.ic_jobinfo_close_more_100_28);
		}
	}
	
	/**
	 * 初始化企业信息
	 * @param company 企业信息
	 * @param listener 查看企业相关职位信息
	 */
	public void setDatas(Company company,View.OnClickListener listener) {
		if (company==null) {
			return;
		}
		mCompany=company;
		txt_company.setText(company.getName());
		txt_industry.setText(company.getTrade());
		String star=company.getStarLevel();
		if (StringUtils.isEmpty(star)||star.contains("不限")) {
			txt_star_level.setVisibility(View.GONE);
		}
		txt_star_level.setText(star);
		txt_company_mode.setText(company.getProperty());
		String scale=company.getScale();
		if (StringUtils.isEmpty(scale)||"null".equalsIgnoreCase(scale)) {
			txt_scope.setText("保密");
		}else {
			txt_scope.setText(scale);
		}
		txt_degrees.setText(company.getGrade());
		String des=company.getIntroduce();
		if (!StringUtils.isEmpty(des)) {
			String ss=Html.fromHtml(des).toString();
			if (ss.length()>80) {
				txt_company_describle1.setText(ss.substring(0, ss.length()/2));
				txt_company_describle2.setText(ss.substring(ss.length()/2));
			}else {
				txt_company_describle1.setText(ss);
				txt_company_describle2.setText("");
			}
		}
		txt_company_describle2.setVisibility(View.GONE);
		contact.setDatas(company.getContacts(), company.getTelephone(), company.getPhone(), company.getEmail(), company.getAddress(),company.getName(),company.getLongtitude(),company.getLatitude());
		ibtn_company_show_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int vg=(txt_company_describle2.getVisibility()==View.GONE) ? View.VISIBLE : View.GONE ;
				txt_company_describle2.setVisibility(vg);
				switchMoreLayout();
			}
		});
		rela_other_positions.setOnClickListener(listener==null?mListener:listener);
	}
	/**
	 * 默认监听
	 */
	private View.OnClickListener mListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (mCompany==null) {
				return ;
			}
			Intent intent = new Intent(context, PositionSingleListActivity.class);
			intent.putExtra("cid", mCompany.getId());
			intent.putExtra("cname", mCompany.getName());
			context.startActivity(intent);
			((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
		}
	};
	

	
}
