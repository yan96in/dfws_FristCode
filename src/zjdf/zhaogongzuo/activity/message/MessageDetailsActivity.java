package zjdf.zhaogongzuo.activity.message;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.controllers.MessageControllers;
import zjdf.zhaogongzuo.entity.Message;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 我的消息详细页面
 * 
 * @author Administrator
 * 
 */
public class MessageDetailsActivity extends Activity {
	private TextView txt_datails_title;// 来源标题
	private TextView txt_datails_head;// 头部
	private TextView txt_datails_name;// 发送人
	private TextView txt_datails_time;// 时间
	private TextView txt_datails_content;// 正文
	private MessageControllers messageControllers;// 控制器
	private Context context;// 上下文
	private Message message;// 实体
	private int msg_id;// 接收消息 ID
	private ImageButton subscribe_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_msg_details);
		context = this;
		messageControllers = new MessageControllers(context);
		msg_id = getIntent().getIntExtra("msg_id", 0);
		initview();
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}

	// 刷新界面
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (message != null) {
					int number = message.getType();
					if (number == 1) {

						txt_datails_title.setText("系统消息");
					} else {
						txt_datails_title.setText("HR来信");
					}
					String name = message.getSender();
					if (name.contains("false")) {
						txt_datails_name.setText(message.getSubject());
					} else {
						txt_datails_name.setText(message.getSender());
					}
					txt_datails_head.setText(message.getSubject());
					txt_datails_time.setText(message.getDate());
					txt_datails_content.setText(Html.fromHtml(message.getContent()));

				}

				break;
			}
		};
	};

	// 初始化数据
	private void initview() {
		// TODO Auto-generated method stub
		txt_datails_title = (TextView) findViewById(R.id.txt_datails_title);
		txt_datails_head = (TextView) findViewById(R.id.txt_datails_head);
		txt_datails_name = (TextView) findViewById(R.id.txt_datails_name);
		txt_datails_time = (TextView) findViewById(R.id.txt_datails_time);
		txt_datails_content = (TextView) findViewById(R.id.txt_datails_content);
		subscribe_return = (ImageButton) findViewById(R.id.subscribe_return);
		subscribe_return.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		 getData() ;
	}

	//获取消息详细数据
	private void getData() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				message = (Message) messageControllers
						.getMessage_detail(msg_id);
				handler.sendEmptyMessage(1);
			}
		}.start();
	}
}
