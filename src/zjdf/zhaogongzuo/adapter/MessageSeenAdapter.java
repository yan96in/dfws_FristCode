package zjdf.zhaogongzuo.adapter;

import java.util.List;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.options.CompanyInfoActivity;
import zjdf.zhaogongzuo.entity.Viewed;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 消息中心 谁看过我 适配器
 * 
 * @author Administrator
 * 
 */
public class MessageSeenAdapter extends BaseAdapter {

	private LayoutInflater layout;
	private Context context;
	private List<Viewed> listnews;
	private ListView listView;

	public MessageSeenAdapter(Context context, List<Viewed> lsNews,
			ListView listView) {
		this.context = context;
		layout = LayoutInflater.from(context);
		this.listView = listView;
		this.listnews = lsNews;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listnews.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position < 0) {
			return listnews.get(0);
		}
		if (position > listnews.size() - 1) {
			return listnews.get(listnews.size() - 1);
		}
		return listnews.get(position);
	}

	// 获取ID
	public int getItemObjectId(int position) {
		return listnews.get(position).getCompany_id();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder h = null;
		if (convertView == null) {
			h = new Holder();
			convertView = layout.inflate(R.layout.layout_message_list_message_item, null);
			h.msg_title_name = (TextView) convertView.findViewById(R.id.msg_title_name);
			h.msg_title_time = (TextView) convertView.findViewById(R.id.msg_title_time);
			h.msg_title_nable = (TextView) convertView.findViewById(R.id.msg_title_nable);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}
		final Viewed news = listnews.get(position);
		h.msg_title_name.setText(news.getCompany_name());
		h.msg_title_time.setText(news.getViewed_date());
		h.msg_title_nable.setText(news.getViewed_times() + "");
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (!NetWorkUtils.checkNetWork(context)) {
					Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG)
							.show();
					return;
				}
				int index = (int) id;
				int meet_id = getItemObjectId(index);
				Intent intent = new Intent(context, CompanyInfoActivity.class);
				intent.putExtra("company_id", String.valueOf(meet_id));
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	private class Holder {
		private TextView msg_title_name;
		private TextView msg_title_time;
		private TextView msg_title_nable;
	}
}
