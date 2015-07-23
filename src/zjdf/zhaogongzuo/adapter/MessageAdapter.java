package zjdf.zhaogongzuo.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 职位订阅 搜索职位列表
 * 
 * @author Administrator
 * 
 */
public class MessageAdapter extends BaseAdapter {

	private LayoutInflater layout;// 布局
	private Context mcontext;// 上下文
	private List<Position> listnews;// list数据
	private List<Position> lisiPosition;// 临时数据

	private boolean[] itemStatus;// 选择状态
	private boolean status;// 选择状态
	private boolean isoneline = false;
	private ListView listView;

	public MessageAdapter(Context context, List<Position> lsNews) {
		if (lsNews==null) {
			listnews=new ArrayList<Position>();
		}else {
			listnews=lsNews;
		}
	    mcontext = context;
		layout = LayoutInflater.from(context);
		itemStatus = new boolean[this.listnews.size()];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listnews.size();
	}

	@Override
	public Position getItem(int position) {
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
		return listnews.get(position).getIdStr();
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
	public void addItem(int position, Position company) {
		if (position > getCount() || position < 0) {
			listnews.add(company);
		} else {
			listnews.add(position, company);
		}
		notifyDataSetChanged();
	}

	// 刷新list
	public void addItems(int position, List<Position> list) {
		if (position > getCount() || position < 0) {
			listnews.addAll(list);
		} else {
			listnews.addAll(position, list);
		}
		notifyDataSetChanged();
	}

	// 删除
	public void removeItem(int position) {
		if (position > getCount() || position < 0) {
		} else {
			listnews.remove(position);
			notifyDataSetChanged();
		}
	}

	// 选择状态
	public void toggostatus(boolean status) {
		this.status = status;
		this.isoneline = false;
		notifyDataSetChanged();
	}

	/**
	 * 改变选择状态
	 * 
	 * @param position
	 */
	public void setLineState(int position) {
		itemStatus[position] = true;
		isoneline = true;
		status = false;
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
	 * 添加数据集
	 *<pre>方法  </pre>
	 * @param positions
	 */
	public void addDatas(List<Position> positions){
		if (positions!=null) {
			lisiPosition=positions;
			listnews.addAll(lisiPosition);
			itemStatus=Arrays.copyOf(itemStatus, listnews.size());
			notifyDataSetChanged();
		}
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder h = null;
		if (convertView == null) {
			h = new Holder();
			convertView = layout.inflate(R.layout.layout_job_items, null);
			h.txt_target_position_name = (TextView) convertView.findViewById(R.id.txt_target_position_name);
			h.txt_target_position_company = (TextView) convertView.findViewById(R.id.txt_target_position_company);
			h.txt_target_position_date = (TextView) convertView.findViewById(R.id.txt_target_position_date);
			h.txt_target_position_salary = (TextView) convertView.findViewById(R.id.txt_target_position_salary);
			h.txt_target_position_address = (TextView) convertView.findViewById(R.id.txt_target_position_address);
			h.iv_target_position_hot = (ImageView) convertView.findViewById(R.id.iv_target_position_hot);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}
		Position company = listnews.get(position);
		h.txt_target_position_name.setText(company.getName());
		h.txt_target_position_company.setText(company.getCompanyName());
		h.txt_target_position_date.setText(company.getUpdateTime());
		String salary=company.getSalary();
		if (StringUtils.isEmpty(salary)||salary.equals("0-0")||salary.equals("0")) {
			salary="面议";
		}
		h.txt_target_position_salary.setText(salary);
		h.txt_target_position_address.setText(company.getAddress());
		int nn = company.getIs_urgent();
		if (nn == 1) {
			h.iv_target_position_hot.setVisibility(View.VISIBLE);
		} else {
			h.iv_target_position_hot.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	// chebox 监听器
	class CheckBoxListener implements OnCheckedChangeListener {
		int position;

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			// xxxx
			if (isChecked) {
				itemStatus[position] = true;
			} else {
				itemStatus[position] = false;
			}
		}

		private CheckBoxListener(int position) {
			// TODO Auto-generated method stub
			this.position = position;
		}

	}

	// 实体
	private class Holder {
		/** 职位名称 */
		private TextView txt_target_position_name;
		/** 所属公司 */
		private TextView txt_target_position_company;
		/** 发布日期 */
		private TextView txt_target_position_date;
		/** 薪资待遇 */
		private TextView txt_target_position_salary;
		/** 所在地 */
		private TextView txt_target_position_address;
		/** 是否热门 */
		private ImageView iv_target_position_hot;

	}
}
