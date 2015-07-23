package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.resume.AddCertificateActivity;
import zjdf.zhaogongzuo.activity.resume.AddTrainingActivity;
import zjdf.zhaogongzuo.adapter.CInformationAdapter.MyCheckBoxChangedListener;
import zjdf.zhaogongzuo.entity.Certificate;
import zjdf.zhaogongzuo.entity.Training;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 培训经历 适配器
 * 
 * @author Administrator
 * 
 */
public class TrainingAdapter extends BaseAdapter {

	private LayoutInflater layout;// 布局
	private Context context;// 上下文
	private List<Training> listnews;// list数据

	private boolean isManage=false;
	/**选中状态*/
	private boolean[] itemStatus;

	public TrainingAdapter(Context context, List<Training> lsNews) {
		if (lsNews==null) {
			listnews=new ArrayList<Training>();
		}else {
			listnews=lsNews;
		}
		this.context = context;
		layout = LayoutInflater.from(context);
		itemStatus = new boolean[listnews.size()];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listnews.size();
	}

	@Override
	public Training getItem(int position) {
		// TODO Auto-generated method stub
		return listnews.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	// 获取ID
	public String getItemObjectId(int position) {
		return listnews.get(position).getId();
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
	 * 清除选中状态
	 */
	public void clearStates(int position){
		if (itemStatus!=null) {
			for (int i = 0; i < itemStatus.length; i++) {
				if (position!=i) {
					itemStatus[i]=false;
				}
			}
		}
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
			ids[i]=listnews.get(indexs[i]).getId();
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

	// 清除
	public void clearItems() {
		if (listnews.size() > 0) {
			listnews.clear();
		}
	}

	// 刷新list
	public void addItems(List<Training> list) {
		if (list!=null&&list.size()>0) {
			listnews.addAll(list);
			itemStatus=Arrays.copyOf(itemStatus, listnews.size());
			notifyDataSetChanged();
		}
	}

	// 删除
	public void removeItem(int position) {
		if (position > getCount() || position < 0) {
		} else {
			listnews.remove(position);
			notifyDataSetChanged();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder h = null;
		if (convertView == null) {
			h = new Holder();
			convertView = layout.inflate(
					R.layout.layout_my_resume_training_item, null);
			h.linear_context=(LinearLayout)convertView.findViewById(R.id.linear_context);
			h.tra_time = (TextView) convertView.findViewById(R.id.tra_time);
			h.tra_cn = (TextView) convertView
					.findViewById(R.id.tra_certificates);
			h.tra_in = (TextView) convertView
					.findViewById(R.id.tra_institutions);
			h.tra_detail = (TextView) convertView.findViewById(R.id.tra_detail);
			h.cbx_status = (CheckBox) convertView.findViewById(R.id.cbx_status);
			h.tra_loca = (TextView) convertView.findViewById(R.id.tra_location);
			h.cbx_status = (CheckBox) convertView.findViewById(R.id.cbx_status);
			h.cbx_status.setFocusable(false);
			h.cbx_status.setClickable(false);
			h.cbx_status.setVisibility(View.GONE);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}
		final int pp=position;
		final Training tt = listnews.get(position);
		if (tt!=null) {
			h.tra_in.setText(tt.getInstitutions_cn());
			h.tra_time.setText(tt.getBegin_year() + "~"+ tt.getBegin_month() + "-" + tt.getEnd_year() + "~"+ tt.getEnd_month());
			h.tra_cn.setText(tt.getCertificates_cn());
			h.tra_detail.setText(tt.getDetail_cn());
			h.tra_loca.setText(tt.getLocation_txt());
			//item 跳转到职位
			h.linear_context.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isManage) {
						clearStates(pp);
						toggle(pp);
					}else {
						String idd=tt.getId();
						if (!StringUtils.isEmpty(idd)) {
							String name =tt.getInstitutions_cn();
							String beng_time = tt.getBegin_year();
							String beng_tiems = tt.getBegin_month();
							String eng_time = tt.getEnd_year();
							String eng_times = tt.getEnd_month();
							String addrees = tt.getLocation_txt();
							String certificates = tt.getCertificates_cn();
							String deteail = tt.getDetail_cn();
							Intent intent = new Intent(context,AddTrainingActivity.class);
							intent.putExtra("id", idd);
							intent.putExtra("name", name);
							intent.putExtra("beng_time", beng_time);
							//intent.putExtra("beng_times", beng_tiems);
							if (!beng_tiems.contains("0")) {
								intent.putExtra("beng_times",0+ beng_tiems);
							}else {
								intent.putExtra("beng_times", beng_tiems);
							}
							intent.putExtra("eng_time", eng_time);
							//intent.putExtra("eng_times", eng_times);
							if (!eng_times.contains("0")) {
								intent.putExtra("eng_times",0+ eng_times);
							}else {
								intent.putExtra("eng_times", eng_times);
							}
							intent.putExtra("addrees", addrees);
							intent.putExtra("certificates", certificates);
							intent.putExtra("deteail", deteail);
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
	// 实体
	private class Holder {
		
		private LinearLayout linear_context;
		private TextView tra_time;// 培训时间
		private TextView tra_in;// 培训机构
		private TextView tra_cn;// 获得证书
		private TextView tra_detail;// 培训描述
		private TextView tra_loca;// 培训位置
		private CheckBox cbx_status;// 选择状态
	}
}
