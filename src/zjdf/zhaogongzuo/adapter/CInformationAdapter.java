package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import zjdf.zhaogongzuo.activity.resume.AddCertificateActivity;
import zjdf.zhaogongzuo.entity.Certificate;
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
 *    证书适配器
 * @author Administrator
 * 
 */
public class CInformationAdapter extends BaseAdapter {

	private LayoutInflater layout;// 布局
	private Context context;// 上下文
	private List<Certificate> listnews;// list数据
	private boolean isManage=false;
	/**选中状态*/
	private boolean[] itemStatus;

	public CInformationAdapter(Context context, List<Certificate> lsNews) {
		if (lsNews==null) {
			listnews=new ArrayList<Certificate>();
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
	public Certificate getItem(int position) {
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
	public void addItems(List<Certificate> list) {
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
					R.layout.layout_my_resume_information_item, null);
			h.linear_context=(LinearLayout)convertView.findViewById(R.id.linear_context);
			h.info_time = (TextView) convertView.findViewById(R.id.info_time);
			h.info_name = (TextView) convertView.findViewById(R.id.info_name);
			h.info_content = (TextView) convertView.findViewById(R.id.info_content);
			h.cbx_status = (CheckBox) convertView.findViewById(R.id.cbx_status);
			h.cbx_status.setFocusable(false);
			h.cbx_status.setClickable(false);
			h.cbx_status.setVisibility(View.GONE);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}
		final int pp=position;
		final Certificate cc = listnews.get(position);
		if (cc!=null) {
			h.info_time.setText(cc.getObtained_year()+"-"+cc.getObtained_month());
			h.info_name.setText(cc.getCertificate_cn());
			h.info_content.setText(cc.getDetail_cn());
			
			//item 跳转到职位
			h.linear_context.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isManage) {
						clearStates(pp);
						toggle(pp);
					}else {
						String idd=cc.getId();
						if (!StringUtils.isEmpty(idd)) {
							String name = cc.getCertificate_cn();
							String time = cc.getObtained_year();
							String times=cc.getObtained_month();
							String detail = cc.getDetail_cn();
							Intent intent = new Intent(context,AddCertificateActivity.class);
							intent.putExtra("id", idd);
							intent.putExtra("name", name);
							intent.putExtra("time", time);
							if (!times.contains("0")) {						
								intent.putExtra("times", 0+times);
							}else {
								intent.putExtra("times", times);
							}
							intent.putExtra("detail", detail);
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
		private TextView info_time;// 证书时间
		private TextView info_name;// 证书名字
		private TextView info_content;// 关注企业岗位数
		private CheckBox cbx_status;// 选择状态
	}
}
