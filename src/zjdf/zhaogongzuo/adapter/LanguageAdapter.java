package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zjdf.zhaogongzuo.R;
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
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import zjdf.zhaogongzuo.activity.resume.AddLanguageActivity;
import zjdf.zhaogongzuo.activity.resume.AddWorksActivity;
import zjdf.zhaogongzuo.adapter.WorksAdapter.MyCheckBoxChangedListener;
import zjdf.zhaogongzuo.entity.Certificate;
import zjdf.zhaogongzuo.entity.ResumeLanguage;
import zjdf.zhaogongzuo.entity.ResumeWorks;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 语言 适配器
 * 
 * @author Administrator
 * 
 */
public class LanguageAdapter extends BaseAdapter {

	private LayoutInflater layout;// 布局
	private Context context;// 上下文
	private List<ResumeLanguage> listnews;// list数据

	private boolean[] itemStatus;// 选择状态
	private boolean isManage = false;

	public LanguageAdapter(Context context, List<ResumeLanguage> lsNews) {
		if (lsNews == null) {
			listnews = new ArrayList<ResumeLanguage>();
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
	public ResumeLanguage getItem(int position) {
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

/*	// 获取ID
	public int getItemObjectId(int position) {
		return listnews.get(position).id;
	}*/

	// 获取语言ID
	public String getItemObjectLanguageId(int position) {
		return listnews.get(position).language_value;

	}

	// 获取熟练程度
	public String getItemObjectLanguageIds(int position) {
		return listnews.get(position).mastery_value;
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

	// 添加
	public void addItem(int position, ResumeLanguage company) {
		if (position > getCount() || position < 0) {
			listnews.add(company);
		} else {
			listnews.add(position, company);
		}
		notifyDataSetChanged();
	}

	// 刷新list
	public void addItems(List<ResumeLanguage> list) {
		if (list != null && list.size() > 0) {
			listnews.addAll(list);
			itemStatus = Arrays.copyOf(itemStatus, listnews.size());
			notifyDataSetChanged();
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

	// 删除
	public void removeItem(int position) {
		if (position > getCount() || position < 0) {
		} else {
			listnews.remove(position);
			notifyDataSetChanged();
		}
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
			convertView = layout.inflate(
					R.layout.layout_my_resume_language_item, null);
			h.linear_context = (LinearLayout) convertView
					.findViewById(R.id.linear_context);
			h.la_name = (TextView) convertView.findViewById(R.id.la_name);
			h.la_names = (TextView) convertView.findViewById(R.id.la_names);
			h.cbx_status = (CheckBox) convertView.findViewById(R.id.cbx_status);
			h.cbx_status.setFocusable(false);
			h.cbx_status.setClickable(false);
			h.cbx_status.setVisibility(View.GONE);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}
		/*
		 * ResumeLanguage company = listnews.get(position);
		 * h.la_name.setText(company.language_value);
		 * h.la_names.setText(company.mastery_value);
		 */
		final int pp = position;
		final ResumeLanguage res = listnews.get(position);
		if (res != null) {
			h.la_name.setText(res.language_value);
			h.la_names.setText(res.mastery_value);

			// item 跳转到职位
			h.linear_context.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isManage) {
						clearStates(pp);
						toggle(pp);
					} else {
						String idd = res.id + "";
						if (!StringUtils.isEmpty(idd)) {
							String language_id = res.language_value;
							String mastery_id = res.mastery_value;
							Intent intent = new Intent(context,AddLanguageActivity.class);
							intent.putExtra("ids", idd);
							intent.putExtra("language_id", language_id);
							intent.putExtra("mastery_id", mastery_id);
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
		private LinearLayout linear_context;
		private TextView la_name;// 语言名字
		private TextView la_names;// 语言熟练程度
		private CheckBox cbx_status;// 选择状态
	}

}
