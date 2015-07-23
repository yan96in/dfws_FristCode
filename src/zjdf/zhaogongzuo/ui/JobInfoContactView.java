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
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.mycenter.LocsMapActivity;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *<h2> JobInfoContactView</h2>
 *<pre> 职位详情 联系方式 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月29日
 * @version 
 * @modify 
 * 
 */
public class JobInfoContactView extends LinearLayout {

	private Context context;
	/**联系人*/
	private TextView txt_contacter;
	/**联系电话*/
	private TextView txt_tele;
	/**手机*/
	private TextView txt_phone;
	/**邮箱*/
	private TextView txt_email;
	/**地址*/
	private TextView txt_address;
	/**表示职位还是企业。0表示职位，1表示企业*/
	private int flag=0;
	/**经度*/
	private String lo;
	/**纬度*/
	private String la;
	/**企业名称*/
	private String company;
	private String mPhone;
	private String tele;

	public JobInfoContactView(Context context) {
		this(context, null);
	}
	/**<pre></pre>
	 * @param context
	 * @param attrs
	 */
	public JobInfoContactView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.layout_jobinfo_contact,
				this, true);
		initView();
	}
	
	private void initView(){
		txt_contacter=(TextView)findViewById(R.id.txt_contacter);
		txt_tele=(TextView)findViewById(R.id.txt_tele);
		txt_phone=(TextView)findViewById(R.id.txt_phone);
		txt_email=(TextView)findViewById(R.id.txt_email);
		txt_address=(TextView)findViewById(R.id.txt_address);
		txt_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (StringUtils.isEmpty(lo)||StringUtils.isEmpty(la)) {
					return;
				}
				if (!StringUtils.checkDecimals(lo)||!StringUtils.checkDecimals(la)) {
					return;
				}
				Intent intent=new Intent(context, LocsMapActivity.class);
				intent.putExtra("longitude", Double.parseDouble(lo));
				intent.putExtra("latitude", Double.parseDouble(la));
				intent.putExtra("name", company);
				context.startActivity(intent);
				((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
			}
		});
		txt_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!StringUtils.isEmpty(mPhone)) {
					Uri urip = Uri.parse("tel:"+mPhone); 
					Intent it = new Intent(Intent.ACTION_DIAL, urip);
					context.startActivity(it);
					((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				}
			}
		});
		txt_tele.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!StringUtils.isEmpty(tele)) {
					Uri urip = Uri.parse("tel:"+tele); 
					Intent it = new Intent(Intent.ACTION_DIAL, urip);   
					context.startActivity(it);
					((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				}
			}
		});
	}
	
	/**
	 * 填充信息
	 * @param contacter 联系人
	 * @param tele 电话号码
	 * @param phone 手机
	 * @param email 邮箱
	 * @param address 地址
	 * @param company 公司名称
	 * @param longtitude 经度
	 * @param latitude 纬度
	 */
	public void setDatas(String contacter, String tele,String phone,String email,String address,final String company,final String longtitude,final String latitude){
		
		if (StringUtils.isEmpty(phone)) {
			txt_phone.setVisibility(View.GONE);
		}
		if (StringUtils.isEmpty(tele)) {
			txt_tele.setVisibility(View.GONE);
		}
		if (StringUtils.isEmpty(latitude)||StringUtils.isEmpty(longtitude)||"0".equals(longtitude)||"0".equals(latitude)) {
			txt_address.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
		
		if (!StringUtils.isEmpty(contacter)) {
			txt_contacter.setText(contacter);
		}
		if (!StringUtils.isEmpty(tele)) {
			txt_tele.setText(Html.fromHtml("<u>"+tele+"</u>"));
		}
		if (!StringUtils.isEmpty(phone)) {
			txt_phone.setText(Html.fromHtml("<u>"+phone+"</u>"));
		}
		if (!StringUtils.isEmpty(email)) {
			
			txt_email.setText(email);
		}
		if (!StringUtils.isEmpty(address)) {
			txt_address.setText(Html.fromHtml("<u>"+address+"</u>"));
		}
		lo=longtitude;
		la=latitude;
		this.company=company;
		mPhone=phone;
		this.tele=tele;
	}
}
