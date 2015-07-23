/**
 * Copyright © 2014年3月25日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.List;

import zjdf.zhaogongzuo.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import zjdf.zhaogongzuo.entity.AttentionCompany;

/**
 *<h2> AttentionEnterpriseAdapter</h2>
 *<pre>  </pre>      关注企业
 * @author Eilin.Yang VeryEast
 * @since 2014年3月25日
 * @version 
 * @modify 
 * 
 */
public class AttentionEnterpriseAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	/**企业集合*/
	private List<AttentionCompany> companies;
	
	public AttentionEnterpriseAdapter(Context context, List<AttentionCompany> companies){
		
		if (companies!=null) {
			this.companies=companies;
		}else {
			this.companies=new ArrayList<AttentionCompany>();
		}
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count=0;
		if (companies!=null) {
			count=companies.size(); 
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public AttentionCompany getItem(int position) {
		// TODO Auto-generated method stub
		if (companies!=null) {
			return companies.get(position); 
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.layout_attention_enterprise_item, null);
			holder=new ViewHolder();
			holder.txt_company_name=(TextView)convertView.findViewById(R.id.txt_company_name);
			holder.txt_attention_date=(TextView)convertView.findViewById(R.id.txt_attention_date);
			holder.txt_attention_status=(TextView)convertView.findViewById(R.id.txt_attention_status);
			holder.txt_postnum=(TextView)convertView.findViewById(R.id.txt_postnum);
			convertView.setTag(holder);
		}
		else {
			holder=(ViewHolder)convertView.getTag();
		}
		if (companies!=null&&companies.size()>0) {
			AttentionCompany company=companies.get(position);
			if (company!=null) {
				holder.txt_company_name.setText(company.getName());
				holder.txt_attention_date.setText(company.getName());
				holder.txt_attention_status.setText(company.getAttentionStatus());
				holder.txt_postnum.setText(company.getRecruitNum());
			}
		}
		return convertView;
	}
	
	private class ViewHolder{
		/**企业名称*/
		public TextView txt_company_name;
		/**关注时间*/
		public TextView txt_attention_date;
		/**关注状态*/
		public TextView txt_attention_status;
		/**岗位个数*/
		public TextView txt_postnum;
		
	}

}
