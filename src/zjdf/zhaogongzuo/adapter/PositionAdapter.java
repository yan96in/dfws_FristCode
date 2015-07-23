/**
 * Copyright © 2014年4月16日 FindJob www.veryeast.com
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.search.JobInfoActivity;
import zjdf.zhaogongzuo.activity.search.PositionListActivity;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.ui.JobInfoItemView;
import zjdf.zhaogongzuo.utils.DateTimeUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *<h2> PositionAdapter</h2>
 *<pre> 职位列表适配器 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月16日
 * @version 
 * @modify 
 * 
 */
public class PositionAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	/**职位集合*/
	private List<Position> mPositions;
	/**临时职位列表*/
	private List<Position> mPositions_temp;
	/**选中状态*/
	private boolean[] itemStatus;
	
	public PositionAdapter(Context context,List<Position> positions){
		if (positions==null) {
			mPositions=new ArrayList<Position>();
		}else {
			mPositions=positions;
		}
		mContext=context;
		inflater=LayoutInflater.from(mContext);
		itemStatus=new boolean[mPositions.size()];
	}
	
	/**
	 * 初始化数据集
	 *<pre>方法  </pre>
	 * @param positions
	 */
	public void setDatas(List<Position> positions) {
		if (null==positions||positions.size()<0) {
			return;
		}
		mPositions.clear();
		mPositions.addAll(positions);
//		itemStatus=new boolean[mPositions.size()];
		itemStatus=Arrays.copyOf(itemStatus, mPositions.size());
		notifyDataSetChanged();
	}
	
	/**
	 * 清除数据并更新界面
	 *<pre>方法  </pre>
	 */
	public void clear() {
		if (null!=mPositions) {
			mPositions.clear();
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 添加数据集
	 *<pre>方法  </pre>
	 * @param positions
	 */
	public void addDatas(List<Position> positions){
		if (positions!=null) {
			mPositions_temp=positions;
			mPositions.addAll(mPositions_temp);
			itemStatus=Arrays.copyOf(itemStatus, mPositions.size());
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 添加数据
	 *<pre>方法  </pre>
	 * @param position 职位
	 */
	public void addData(Position position){
		if (position!=null) {
			mPositions.add(position);
			itemStatus=Arrays.copyOf(itemStatus, mPositions.size());
			notifyDataSetChanged();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPositions.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Position getItem(int position) {
		// TODO Auto-generated method stub
		return mPositions.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	/**
	 * 改变选中状态,单击整个条的时候使用
	 * @param position
	 */
	public void toggle(int position){
		if(itemStatus[position] == true){
			itemStatus[position] = false;
		}else{
			itemStatus[position] = true;
		}
		this.notifyDataSetChanged();
	}
	
	/**
	 * 获取选中的行
	 * @return
	 */
	public int[] getSelectedItemIndexes() {

		if (itemStatus == null || itemStatus.length == 0) {
			return new int[0];
		} else {
			int size = itemStatus.length;
			int counter = 0;
			// TODO how can we skip this iteration?
			for (int i = 0; i < size; i++) {
				if (itemStatus[i] == true)
					++counter;
			}
			int[] selectedIndexes = new int[counter];
			int index = 0;
			for (int i = 0; i < size; i++) {
				if (itemStatus[i] == true)
					selectedIndexes[index++] = i;
			}
			return selectedIndexes;
		}
	}
	
	/**
	 * 获取选中的职位id
	 * @return 返回 String[]形式
	 */
	public String[] getSelectedJobIdsArray() {
		int[] indexs=getSelectedItemIndexes();
		int indexCount=indexs.length;
		if (indexCount<1) {
			return null;
		}
		String[] ids=new String[indexCount];
		for (int i = 0; i < indexCount; i++) {
			ids[i]=mPositions.get(indexs[i]).getIdStr();
		}
		return ids;
	}

	/**
	 * 获取选中的职位id
	 * @return String ,职位id之间以","分割
	 */
	public String getSelectedJobIdsString() {
		String [] ids=getSelectedJobIdsArray();
		if (ids==null||ids.length==0) {
			return null;
		}
		StringBuffer buffer=new StringBuffer(ids.length);
		for (int i = 0; i < ids.length; i++) {
			buffer.append(ids[i]+",");
		}
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		return buffer.toString();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		ViewHolder holder=null;
		final int pp=position;
		if (cView==null) {
			holder=new ViewHolder();
			cView=inflater.inflate(R.layout.layout_position_list_item, null);
			holder.rela_content=(RelativeLayout)cView.findViewById(R.id.rela_content);
			holder.cbx_position_status=(CheckBox)cView.findViewById(R.id.cbx_position_status);
			holder.txt_position_name=(TextView)cView.findViewById(R.id.txt_position_name);
			holder.txt_position_company=(TextView)cView.findViewById(R.id.txt_position_company);
			holder.txt_position_date=(TextView)cView.findViewById(R.id.txt_position_date);
			holder.iv_position_hot=(ImageView)cView.findViewById(R.id.iv_position_hot);
			holder.txt_position_salary=(TextView)cView.findViewById(R.id.txt_position_salary);
			holder.txt_position_address=(TextView)cView.findViewById(R.id.txt_position_address);
			holder.cbx_position_status.setFocusable(false);
			cView.setTag(holder);
		}else {
			holder=(ViewHolder)cView.getTag();
		}

		if (null!=mPositions&&mPositions.size()>0) {
			Position p=mPositions.get(position);
			holder.txt_position_name.setText(p.getName());
			holder.txt_position_company.setText(p.getCompanyName());
			holder.txt_position_date.setText(DateTimeUtils.getDateFromDatetime(p.getUpdateTime()));
			String salary=p.getSalary();
			if (StringUtils.isEmpty(salary)||salary.equals("0-0")||salary.equals("0")) {
				salary="面议";
			}
			holder.txt_position_salary.setText(salary);
			holder.txt_position_address.setText(p.getAddress());
			boolean ishot=p.getIs_urgent()==1;
			if (ishot) {
				holder.iv_position_hot.setVisibility(View.VISIBLE);
			}else {
				holder.iv_position_hot.setVisibility(View.INVISIBLE);
			}
			holder.rela_content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(mContext,JobInfoActivity.class);
					intent.putExtra("tags", PositionListActivity.TAG);
					intent.putExtra("position", pp);
					mContext.startActivity(intent);
					((Activity)mContext).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
				}
			});
			holder.cbx_position_status.setOnCheckedChangeListener(new MyCheckBoxChangedListener(pp));
			holder.cbx_position_status.setChecked(itemStatus[position]);
		}
		return cView;
	}

	
	private class ViewHolder{
		/**内容*/
		private RelativeLayout rela_content;
		/**选中状态*/
		private CheckBox cbx_position_status;
		/**职位名称*/
		private TextView txt_position_name;
		/**所属公司*/
		private TextView txt_position_company;
		/**更新日期*/
		private TextView txt_position_date;
		/**是否是热门职位*/
		private ImageView iv_position_hot;
		/**薪资范围*/
		private TextView txt_position_salary;
		/**工作地点*/
		private TextView txt_position_address;
	}
	
	/**
	 * 获取职位详情列表
	 * @param context
	 * @return
	 */
	public List<JobInfoItemView> getJobInfoItemViews(Context context){
		List<JobInfoItemView> jobInfoItemViews=null;
		if (mPositions!=null&&mPositions.size()>0) {
			jobInfoItemViews=new ArrayList<JobInfoItemView>(mPositions.size());
			JobInfoItemView view=null;
			for (Position p : mPositions) {
				if (p!=null) {
					view=new JobInfoItemView(context);
					view.setJobInfoId(p.getIdStr());
					jobInfoItemViews.add(view);
				}
			}
		}
		return jobInfoItemViews;
	}
	
	/**
	 * 获取添加的职位详情列表
	 * @param context
	 * @return
	 */
	public List<JobInfoItemView> getJobInfoAddItemViews(Context context){
		List<JobInfoItemView> jobInfoItemViews=null;
		if (mPositions_temp!=null&&mPositions_temp.size()>0) {
			jobInfoItemViews=new ArrayList<JobInfoItemView>(mPositions_temp.size());
			JobInfoItemView view=null;
			for (Position p : mPositions_temp) {
				if (p!=null) {
					view=new JobInfoItemView(context);
					view.setJobInfoId(p.getIdStr());
					jobInfoItemViews.add(view);
				}
			}
		}
		return jobInfoItemViews;
	}
	
	
	/**
	 * 获取职位详情控件
	 * @param context
	 * @return
	 */
	public JobInfoItemView getJobInfoItemView(Context context, int position){
		JobInfoItemView view=null;
		if (mPositions!=null&&mPositions.size()>0) {
			Position p = mPositions.get(position);
			if (p!=null) {
				view=new JobInfoItemView(context);
				view.setJobInfoId(p.getIdStr());
			}
			
		}
		return view;
	}
	
	
	class MyCheckBoxChangedListener implements android.widget.CompoundButton.OnCheckedChangeListener {
		int position;

		MyCheckBoxChangedListener(int position) {
			this.position = position;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
				itemStatus[position] = isChecked;
		}
	}
}
