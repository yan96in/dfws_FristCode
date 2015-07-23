package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.resume.AddEducatitonsActivity;
import zjdf.zhaogongzuo.entity.ResumeEducation;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 教育经历适配器
 * 
 * @author Administrator
 * 
 */
public class EducationsAdapter extends BaseAdapter {

	private LayoutInflater layout;// 布局
	private Context context;// 上下文
	private List<ResumeEducation> listnews;// list数据
	private ListView listView;

	private boolean[] itemStatus;// 选择状态

	private boolean isManage = false;

	public EducationsAdapter(Context context, List<ResumeEducation> lsNews,
			ListView listView) {
		if (lsNews == null) {
			listnews = new ArrayList<ResumeEducation>();
		} else {
			listnews = lsNews;
		}
		this.context = context;
		layout = LayoutInflater.from(context);
		itemStatus = new boolean[this.listnews.size()];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listnews.size();
	}

	@Override
	public ResumeEducation getItem(int position) {
		// TODO Auto-generated method stub
		if (position < 0) {
			return listnews.get(0);
		}
		if (position > listnews.size() - 1) {
			return listnews.get(listnews.size() - 1);
		}
		return listnews.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 获取ID
	public String getItemObjectId(int position) {
		return listnews.get(position).id;
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

	// 取消
	public void clearItems() {
		if (listnews.size() > 0) {
			listnews.clear();
		}
	}

	/**
	 * 清除选中状态
	 */
	public void clearStates() {
		if (itemStatus != null) {
			for (int i = 0; i < itemStatus.length; i++) {
				itemStatus[i] = false;
			}
		}
	}

	// 添加
	public void addItem(int position, ResumeEducation company) {
		if (position > getCount() || position < 0) {
			listnews.add(company);
		} else {
			listnews.add(position, company);
		}
		notifyDataSetChanged();
	}

	// 刷新list
	public void addItems(List<ResumeEducation> list) {
		if (list != null && list.size() > 0) {
			listnews.addAll(list);
			itemStatus = Arrays.copyOf(itemStatus, listnews.size());
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
	 * 获取选中的职位id
	 * 
	 * @return String ,职位id之间以","分割
	 */
	public String getSelectedJobIdsString() {
		String[] ids = getSelectedJobIdsArray();
		if (ids == null || ids.length == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(ids.length);
		for (int i = 0; i < ids.length; i++) {
			buffer.append(ids[i] + ",");
		}
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		return buffer.toString();
	}

	/**
	 * 获取选中的职位id
	 * 
	 * @return 返回 String[]形式
	 */
	public String[] getSelectedJobIdsArray() {
		int[] indexs = getSelectedItemIndexes();
		int indexCount = indexs.length;
		if (indexCount < 1) {
			return null;
		}
		String[] ids = new String[indexCount];
		for (int i = 0; i < indexCount; i++) {
			ids[i] = listnews.get(indexs[i]).id;
		}
		return ids;
	}

	/**
	 * 获取选中的行
	 * 
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
	 * 启动管理状态
	 * 
	 * @param manage
	 */
	public void isManage(boolean manage) {
		isManage = manage;
		if (!manage) {
			clearStates();
		}
		notifyDataSetChanged();
	}

	/**
	 * 改变选择状态
	 * 
	 * @param position
	 */
	public void state(int position) {
		if (itemStatus[position] == true) {
			itemStatus[position] = false;
		} else {
			itemStatus[position] = true;
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 清除选中状态
	 */
	public void clearStates(int position) {
		if (itemStatus != null) {
			for (int i = 0; i < itemStatus.length; i++) {
				if (position != i) {
					itemStatus[i] = false;
				}
			}
		}
	}

	/**
	 * 改变选中状态,单击整个条的时候使用
	 * 
	 * @param position
	 */
	public void toggle(int position) {
		if (itemStatus[position] == true) {
			itemStatus[position] = false;
		} else {
			itemStatus[position] = true;
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder h = null;
		if (convertView == null) {
			h = new Holder();
			convertView = layout.inflate(R.layout.layout_edu_item, null);
			h.linear_context = (RelativeLayout) convertView
					.findViewById(R.id.linear_context);
			h.txt_edu_item_school = (TextView) convertView
					.findViewById(R.id.txt_edu_item_school);
			h.txt_edu_item_timequantum = (TextView) convertView
					.findViewById(R.id.txt_edu_item_timequantum);
			h.cbx_status = (CheckBox) convertView.findViewById(R.id.cbx_status);
			h.cbx_status.setFocusable(false);
			h.cbx_status.setClickable(false);
			h.cbx_status.setVisibility(View.GONE);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}

		final int pp = position;
		final ResumeEducation res = listnews.get(position);
		if (res != null) {
			h.txt_edu_item_school.setText(res.school_value);
			h.txt_edu_item_timequantum.setText(res.start_time_year + "年" + "-"
					+ res.start_time_month + "月" + "~" + res.end_time_year
					+ "年" + "-" + res.end_time_month + "月");
			// item 跳转到职位
			h.linear_context.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isManage) {
						clearStates(pp);
						toggle(pp);
					} else {
						String idd = res.id;
						if (!StringUtils.isEmpty(idd)) {
							String name = res.edu_degree_value;
							String names = res.school_value;
							String area = res.area_value;
							String major = res.major_value;
							String edu = res.study_abroad_key;
							String beng_time = res.start_time_year;
							String beng_times = res.start_time_month;
							String eng_time = res.end_time_year;
							String eng_times = res.end_time_month;
							String areaCode = res.area_key;
							String major_key = res.major_key;
							Intent intent = new Intent(context,
									AddEducatitonsActivity.class);
							intent.putExtra("id", idd);
							intent.putExtra("name", name);
							intent.putExtra("names", names);
							intent.putExtra("area", area);
							intent.putExtra("major", major);
							intent.putExtra("edu", edu);
							intent.putExtra("beng_time", beng_time);
							intent.putExtra("eng_time", eng_time);
							if (!beng_times.contains("0")) {
								intent.putExtra("beng_times", 0 + beng_times);
							} else {
								intent.putExtra("beng_times", beng_times);
							}
							if (!eng_times.contains("0")) {
								intent.putExtra("eng_times", 0 + eng_times);
							} else {
								intent.putExtra("eng_times", eng_times);
							}
							intent.putExtra("areaCode", areaCode);
							intent.putExtra("major_key", major_key);
							context.startActivity(intent);
						}
					}
				}
			});
			h.cbx_status
					.setOnCheckedChangeListener(new MyCheckBoxChangedListener(
							pp));
			h.cbx_status.setChecked(itemStatus[position]);
			if (isManage) {
				h.cbx_status.setVisibility(View.VISIBLE);
			} else {
				h.cbx_status.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	class MyCheckBoxChangedListener implements
			android.widget.CompoundButton.OnCheckedChangeListener {
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
		private RelativeLayout linear_context;
		private TextView txt_edu_item_school;// 学校名字
		private TextView txt_edu_item_timequantum;// 学习时间
		private CheckBox cbx_status;// 选择状态
	}

}
