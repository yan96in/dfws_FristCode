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
import java.util.Arrays;
import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.mycenter.JobFiarParticulars;
import zjdf.zhaogongzuo.activity.options.PositionInfoActivity;
import zjdf.zhaogongzuo.activity.search.SingleJobInfoActivity;
import zjdf.zhaogongzuo.adapter.PositionApplyLogAdapter.MyCheckBoxChangedListener;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <h2>PositionApplyLogAdapter</h2>
 * 
 * <pre> </pre>
 *  职位收藏夹
 * @author Eilin.Yang VeryEast
 * @since 2014年3月25日
 * @version
 * @modify
 * 
 */
public class JobCollectionAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	/** 职位集合 */
	private List<Position> lisPositions;
	private boolean isManage=false;
	/**选中状态*/
	private boolean[] itemStatus;


	public JobCollectionAdapter(Context context, List<Position> positions) {
		if (positions != null) {
			this.lisPositions = positions;
		} else {
			this.lisPositions = new ArrayList<Position>();
		}
		this.context = context;
		inflater = LayoutInflater.from(context);
		itemStatus = new boolean[lisPositions.size()];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lisPositions.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Position getItem(int position) {
		// TODO Auto-generated method stub
		return lisPositions.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 获取ID
	public String getItemObjectId(int position) {
		return lisPositions.get(position).getIdStr();
	}

	// 获取名字
	public String getItemObjectName(int position) {
		return lisPositions.get(position).getName();
	}

	// 取消
	public void clearItems() {
		if (lisPositions.size() > 0) {
			lisPositions.clear();
		}
	}

	// 刷新list
	public void addItems(List<Position> list) {
		if (list!=null&&list.size()>0) {
			lisPositions.addAll(list);
			itemStatus=Arrays.copyOf(itemStatus, lisPositions.size());
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 启动管理状态
	 * @param manage 
	 */
	public void isManage(boolean manage){
		isManage=manage;
		if (!manage) {
			clearStates();
		}
		notifyDataSetChanged();
	}
	
	/**
	 * 清除选中状态
	 */
	public void clearStates(){
		if (itemStatus!=null) {
			for (int i = 0; i < itemStatus.length; i++) {
				itemStatus[i]=false;
			}
		}
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
		notifyDataSetChanged();
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
			ids[i]=lisPositions.get(indexs[i]).getIdStr();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_mycenter_collection_item, null);
			holder = new ViewHolder();
			holder.iv_status = (ImageView) convertView.findViewById(R.id.iv_status);
			holder.linear_context=(LinearLayout)convertView.findViewById(R.id.linear_context);
			holder.txt_position_apply_title = (TextView) convertView.findViewById(R.id.txt_position_apply_title);
			holder.txt_position_apply_company = (TextView) convertView.findViewById(R.id.txt_position_apply_company);
			holder.txt_position_apply_address = (TextView) convertView.findViewById(R.id.txt_position_apply_address);
			holder.txt_position_apply_date = (TextView) convertView.findViewById(R.id.txt_position_apply_date);
			holder.txt_position_apply_status = (TextView) convertView.findViewById(R.id.txt_position_apply_status);
			holder.cbx_status = (CheckBox) convertView.findViewById(R.id.cbx_status);
			holder.cbx_status.setFocusable(false);
			holder.cbx_status.setVisibility(View.GONE);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final int pp=position;
		final Position p = lisPositions.get(position);
		if (p!=null) {
			holder.iv_status.setVisibility(View.VISIBLE);
			holder.txt_position_apply_title.setText(p.getName());
			holder.txt_position_apply_company.setText(p.getCompanyName());
			holder.txt_position_apply_address.setText(p.getAddress());
			holder.txt_position_apply_date.setText(p.getFavorite_date());
			boolean state = p.getIs_stops();
			if (!state) {
				holder.iv_status.setVisibility(View.GONE);
			}else {
				holder.iv_status.setVisibility(View.VISIBLE);
			}
			holder.txt_position_apply_status.setText("收藏");
			
			//item 跳转到职位
			holder.linear_context.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isManage) {
						toggle(pp);
					}else {
						String idd=p.getIdStr();
						if (!StringUtils.isEmpty(idd)) {
							Intent intent = new Intent(context,SingleJobInfoActivity.class);
							intent.putExtra("ids", idd);
							context.startActivity(intent);
							((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
						}
					}
				}
			});
			holder.cbx_status.setOnCheckedChangeListener(new MyCheckBoxChangedListener(pp));
			holder.cbx_status.setChecked(itemStatus[position]);
			if (isManage) {
				holder.cbx_status.setVisibility(View.VISIBLE);
			}else {
				holder.cbx_status.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	private class ViewHolder {
		/** 职位申请记录状态 */
		public ImageView iv_status;
		/**容器*/
		public LinearLayout linear_context;
		/** 职位名称 */
		public TextView txt_position_apply_title;
		/** 公司名称 */
		public TextView txt_position_apply_company;
		/** 工作地址 */
		public TextView txt_position_apply_address;
		/** 申请时间 */
		public TextView txt_position_apply_date;
		/** 操作 */
		public TextView txt_position_apply_status;
		public CheckBox cbx_status;// 选择状态

	}
	
	class MyCheckBoxChangedListener implements android.widget.CompoundButton.OnCheckedChangeListener {
		int position;

		MyCheckBoxChangedListener(int position) {
			this.position = position;
			System.out.println("position=="+position);
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
				itemStatus[position] = isChecked;
				System.out.println("isChecked=="+isChecked);
		}
	}

}
