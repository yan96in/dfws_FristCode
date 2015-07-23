package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.options.CompanyInfoActivity;
import zjdf.zhaogongzuo.entity.Company;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 关注企业列表
 * 
 * @author Administrator
 * 
 */
public class FocusAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;// 布局
	private Context context;// 上下文
	private List<Company> mCompanies;// list数据
	private boolean isManage=false;
	/**选中状态*/
	private boolean[] itemStatus;

	public FocusAdapter(Context context, List<Company> companies) {
		if (companies != null) {
			mCompanies = companies;
		} else {
			mCompanies = new ArrayList<Company>();
		}
		this.context = context;
		inflater = LayoutInflater.from(context);
		itemStatus = new boolean[mCompanies.size()];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCompanies.size();
	}

	@Override
	public Company getItem(int position) {
		// TODO Auto-generated method stub
		if (position < 0) {
			return mCompanies.get(0);
		}
		if (position > mCompanies.size() - 1) {
			return mCompanies.get(getCount() - 1);
		}
		return mCompanies.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 获取ID
	public String getItemObjectId(int position) {
		return mCompanies.get(position).getId();
	}

	// 获取名字
	public String getItemObjectName(int position) {
		return mCompanies.get(position).getName();
	}

	/**
	 * 
	 * 获取选中行
	 * 
	 * @return
	 */
	public int[] getSelectIetmIndexes() {
		if (itemStatus == null || itemStatus.length == 0) {
			return new int[0];
		} else {
			int size = itemStatus.length;
			int counter = 0;
			for (int i = 0; i < size; i++) {
				if (itemStatus[i] == true)
					counter = counter + 1;
			}
			int[] selectIndexs = new int[counter];
			int index = 0;
			for (int i = 0; i < size; i++) {
				if (itemStatus[i] == true)
					selectIndexs[index++] = i;
			}
			return selectIndexs;
		}
	}

	/**
	 * 清除数据
	 */
	public void clearItems() {
		if (mCompanies.size() > 0) {
			mCompanies.clear();
		}
	}

	/**
	 * 添加公司信息
	 * @param position
	 * @param list
	 */
	public void addItems(List<Company> list) {
		if (list!=null&&list.size()>0) {
			mCompanies.addAll(list);
			itemStatus=Arrays.copyOf(itemStatus, mCompanies.size());
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
			ids[i]=mCompanies.get(indexs[i]).getId();
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
		Holder h = null;
		if (convertView == null) {
			h = new Holder();
			convertView = inflater.inflate(R.layout.layout_mycenter_focus_item,null);
			h.linear_context=(LinearLayout)convertView.findViewById(R.id.linear_context);
			h.focus_name = (TextView) convertView.findViewById(R.id.focus_name);
			h.focus_titme = (TextView) convertView.findViewById(R.id.focus_titme);
			h.focus_number = (TextView) convertView.findViewById(R.id.focus_number);
			h.cbx_status = (CheckBox) convertView.findViewById(R.id.cbx_status);
			h.cbx_status.setFocusable(false);
			h.cbx_status.setVisibility(View.GONE);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}
		final int pp=position;
		final Company company = mCompanies.get(position);
		if (company!=null) {
			h.focus_name.setText(company.getName());
			h.focus_titme.setText(company.getFollowed_date());
			h.focus_number.setText(company.getRecruitNum() + "");
			//item 跳转到职位
			h.linear_context.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isManage) {
						toggle(pp);
					}else {
						String idd=company.getId();
						if (!StringUtils.isEmpty(idd)) {
							Intent intent = new Intent(context,CompanyInfoActivity.class);
							intent.putExtra("company_id", idd);
							context.startActivity(intent);
							((Activity)context).overridePendingTransition(anim.slide_in_right,anim.slide_out_left);
						}
					}
				}
			});
			h.cbx_status.setOnCheckedChangeListener(new MyCheckBoxChangedListener(pp));
			h.cbx_status.setChecked(itemStatus[position]);
			if (isManage) {
				h.cbx_status.setVisibility(View.VISIBLE);
			}else {
				h.cbx_status.setVisibility(View.GONE);
			}
		}
		return convertView;
	}
	
	// 实体
	private class Holder {
		private TextView focus_name;// 企业名字
		private TextView focus_titme;// 关注事件
		private TextView focus_number;// 关注企业岗位数
		private CheckBox cbx_status;// 选择状态
		private LinearLayout linear_context;
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
