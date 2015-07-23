package zjdf.zhaogongzuo.adapter;

import java.util.List;

import zjdf.zhaogongzuo.R;

import zjdf.zhaogongzuo.entity.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 我的消息 适配器
 * 
 * @author Administrator
 * 
 */
public class MessageMyAdapter extends BaseAdapter {

	private LayoutInflater layout;
	private Context context;
	private List<Message> listnews;
	private ListView lisetView;

	public MessageMyAdapter(Context context, List<Message> lsNews,
			ListView lisetView) {
		this.context = context;
		layout = LayoutInflater.from(context);
		this.listnews = lsNews;
		this.lisetView = lisetView;
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

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public int getItemObjectId(int position) {
		return listnews.get(position).getMessage_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder h = null;
		if (convertView == null) {
			h = new Holder();
			convertView = layout.inflate(
					R.layout.layout_message_list_mymessage_item, null);
			h.icon = (ImageView) convertView
					.findViewById(R.id.msg_title_image_icon);
			h.name = (TextView) convertView.findViewById(R.id.msg_title_name);
			h.content = (TextView) convertView
					.findViewById(R.id.msg_title_content);
			h.mytime = (TextView) convertView
					.findViewById(R.id.msg_title_mytime);
			convertView.setTag(h);
		} else {
			h = (Holder) convertView.getTag();
		}
		Message news = listnews.get(position);

		int is_read = news.getIs_read();
		int number = news.getType();
		if (is_read == 1) {
			if (number == 1) {
				h.icon.setBackgroundResource(R.drawable.ic_news);
			} else {
				h.icon.setBackgroundResource(R.drawable.ic_hr);
			}
		}else {
			if (number == 2) {
				h.icon.setBackgroundResource(R.drawable.ic_msg_news);
			} else {
				h.icon.setBackgroundResource(R.drawable.ic__msg_hr);
			}
		}

		h.name.setText(news.getSender());
		h.content.setText(news.getSubject());
		h.mytime.setText(news.getDate());

		return convertView;
	}

	private class Holder {
		private ImageView icon;// 消息图标
		private TextView name;// 企业名字
		private TextView mytime;// 时间
		private TextView content;// 内容
	}
}
