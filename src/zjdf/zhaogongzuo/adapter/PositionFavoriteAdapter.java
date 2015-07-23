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
import zjdf.zhaogongzuo.entity.Position;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *<h2> PositionApplyLogAdapter</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月25日
 * @version 
 * @modify 
 * 
 */
public class PositionFavoriteAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	/**职位集合*/
	private List<Position> positions;
	
	public PositionFavoriteAdapter(Context context, List<Position> positions){
		
		if (positions!=null) {
			this.positions=positions;
		}else {
			this.positions=new ArrayList<Position>();
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
		if (positions!=null) {
			count=positions.size(); 
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Position getItem(int position) {
		// TODO Auto-generated method stub
		if (positions!=null) {
			return positions.get(position); 
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
			convertView=inflater.inflate(R.layout.layout_mycenter_position_apply_log_item, null);
			holder=new ViewHolder();
			holder.iv_status=(ImageView)convertView.findViewById(R.id.iv_status);
			holder.txt_position_apply_title=(TextView)convertView.findViewById(R.id.txt_position_apply_title);
			holder.txt_position_apply_company=(TextView)convertView.findViewById(R.id.txt_position_apply_company);
			holder.txt_position_apply_address=(TextView)convertView.findViewById(R.id.txt_position_apply_address);
			holder.txt_position_apply_date=(TextView)convertView.findViewById(R.id.txt_position_apply_date);
			holder.txt_position_apply_status=(TextView)convertView.findViewById(R.id.txt_position_apply_status);
			convertView.setTag(holder);
		}
		else {
			holder=(ViewHolder)convertView.getTag();
		}
		if (positions!=null&&positions.size()>0) {
			Position p=positions.get(position);
			if (p!=null) {
				holder.iv_status.setVisibility(View.VISIBLE);
				holder.txt_position_apply_title.setText(p.getName());
				holder.txt_position_apply_company.setText(p.getCompany().getName());
				holder.txt_position_apply_address.setText(p.getCompany().getAddress());
				holder.txt_position_apply_date.setText(p.getAddress());
				holder.txt_position_apply_status.setText("收藏");
			}
		}
		return convertView;
	}
	
	private class ViewHolder{
		/**职位申请记录状态*/
		public ImageView iv_status;
		/**职位名称*/
		public TextView txt_position_apply_title;
		/**公司名称*/
		public TextView txt_position_apply_company;
		/**工作地址*/
		public TextView txt_position_apply_address;
		/**申请时间*/
		public TextView txt_position_apply_date;
		/**操作*/
		public TextView txt_position_apply_status;
		
	}

}
