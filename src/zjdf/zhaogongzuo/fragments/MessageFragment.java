/**
 * Copyright © 2014-3-15 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.fragments;

import java.util.ArrayList;
import java.util.List;
import zjdf.zhaogongzuo.MainActivity;
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.message.MessageDetailsActivity;
import zjdf.zhaogongzuo.activity.more.SubscribeActivity;
import zjdf.zhaogongzuo.activity.mycenter.MyResumeActivity;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.activity.search.SingleJobInfoActivity;
import zjdf.zhaogongzuo.adapter.MessageAdapter;
import zjdf.zhaogongzuo.adapter.MessageMyAdapter;
import zjdf.zhaogongzuo.adapter.MessageSeenAdapter;
import zjdf.zhaogongzuo.configures.Configure;
import zjdf.zhaogongzuo.controllers.MessageControllers;
import zjdf.zhaogongzuo.controllers.PositionController;
import zjdf.zhaogongzuo.databases.sharedpreferences.JobKeeper;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.entity.SubPush;
import zjdf.zhaogongzuo.entity.Viewed;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

/**
 * <h2>消息中心<h2>
 * 
 * <pre> </pre>
 * 
 * @author 东方网升Eilin.Yang
 * @since 2014-3-15
 * @version
 * @modify ""
 */
public class MessageFragment extends BaseFragment {
	private Context context;// 上下文
	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片
	private TextView textView1, textView2, textView3;
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1, view2, view3;// 各个页卡
	private ImageButton msg_set_job;// 职位订阅独有的 推送设置
	private MessageControllers messageControllers;// 控制器
	// 消息订阅
	private ListView positionListView;
	private List<Position> positionList;
	private MessageAdapter positionAdapter;
	// 誰看過我
	private ListView viewedListView;// listview
	private List<Viewed> viewedList;// 数据list
	private MessageSeenAdapter viewedAdapter;// 适配器
	// 我的消息
	private ListView messageView;// listview
	private List<zjdf.zhaogongzuo.entity.Message> messageList;// 数据list
	private MessageMyAdapter messageAdapter;// 适配器
	// 三个显示 数据为空的状态
	private LinearLayout line_msg_visibility;// 谁看过我
	private LinearLayout line_msg_mymsg;// 我的消息
	private LinearLayout line_msg_jobmsg;// 职位订阅
	private Button but_mymsg;// 我的消息
	private Button but_msg_seed;// 谁看过我
	private Button but_jobmsg;// 职位订阅

	/** 三个item下 显示有数据的红圈 */
	private TextView menu_item_mycenter_notice;
	private TextView menu_item_mycenter_notice1;
	private TextView menu_item_mycenter_notice2;

	private String s_category;// 职位类别
	private String s_address;// 地址
	private String s_salry;// 薪资待遇
	private String s_board;// 食宿
	private String s_job;// 职位性质
	private PositionController controller;// 简历控制器

	private MainActivity activity;// 全局activity

	// 读取保存
	private SharedPreferences sp = null;
	private int stauts;// 读取保存的状态

	private SubPush subPush;// 职位订阅条件

	private TextView t1, t2, t3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		messageControllers = new MessageControllers(context);
		controller = new PositionController(context);
		activity = new MainActivity();

		sp = context.getSharedPreferences(SubscribeActivity.DATABASE,
				Activity.MODE_WORLD_WRITEABLE);
		// 读取职位订阅，如果关闭，那么不值行该方法
		stauts = sp.getInt("stauts", stauts);

	}

	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_fragment_msg, container,
				false);

		imageView = (ImageView) view.findViewById(R.id.cursor);
		msg_set_job = (ImageButton) view.findViewById(R.id.msg_set_job);

		// tab 欄目
		InitImageView();
		textView1 = (TextView) view.findViewById(R.id.text1);
		textView2 = (TextView) view.findViewById(R.id.text2);
		textView3 = (TextView) view.findViewById(R.id.text3);
		// tab监听
		InitTextView();
		// tab栏目 右边红圈
		menu_item_mycenter_notice = (TextView) view
				.findViewById(R.id.menu_item_mycenter_notice);
		menu_item_mycenter_notice1 = (TextView) view
				.findViewById(R.id.menu_item_mycenter_notice1);
		menu_item_mycenter_notice2 = (TextView) view
				.findViewById(R.id.menu_item_mycenter_notice2);

		// tab欄目 內容加載
		viewPager = (ViewPager) view.findViewById(R.id.vPager);
		views = new ArrayList<View>();
		LayoutInflater inflaters = ((Activity) context).getLayoutInflater();
		// 职位订阅
		view1 = inflaters.inflate(R.layout.layout_fragment_msg_job, null);
		line_msg_jobmsg = (LinearLayout) view1
				.findViewById(R.id.line_msg_jobmsg);
		but_jobmsg = (Button) view1.findViewById(R.id.but_jobmsg);
		t1 = (TextView) view1.findViewById(R.id.t1);
		t2 = (TextView) view1.findViewById(R.id.t2);
		t3 = (TextView) view1.findViewById(R.id.t3);
		// 谁看过我
		view2 = inflaters.inflate(R.layout.layout_fragment_msg_message, null);
		line_msg_visibility = (LinearLayout) view2
				.findViewById(R.id.line_msg_visibility);
		but_msg_seed = (Button) view2.findViewById(R.id.but_msg_seed);
		// 我的消息
		view3 = inflaters.inflate(R.layout.layout_fragment_msg_mymessage, null);
		line_msg_mymsg = (LinearLayout) view3.findViewById(R.id.line_msg_mymsg);
		but_mymsg = (Button) view3.findViewById(R.id.but_mymsg);

		views.add(view1);
		views.add(view2);
		views.add(view3);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		// 三个liset view 数据
		messageView = (ListView) view3.findViewById(R.id.msg_my_listview);
		viewedListView = (ListView) view2.findViewById(R.id.msg_seenlist);
		positionListView = (ListView) view1.findViewById(R.id.msg_job_ListView);

		if (stauts == 1) {
			mExecutorService.submit(new LoadDataThreadSubscribe());
		} else if (stauts == 0) {
			positionList = null;
			handler.sendEmptyMessage(3);
		}

		// 职位订阅
		msg_set_job.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, SubscribeActivity.class);
				startActivityForResult(intent, 1);
			}
		});

		// 职位订阅 跳转
		but_jobmsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, SubscribeActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		// 职位订阅
		positionListView.setOnItemClickListener(new OnItemClickListener() {
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
				String msg_id = positionAdapter.getItemObjectId(index);
				Intent intent = new Intent(context, SingleJobInfoActivity.class);
				intent.putExtra("ids", msg_id);
				startActivity(intent);
			}
		});
		mExecutorService.submit(new LoadDataThreadSubscribe());
		return view;
	}

	// 加载数据  谁看过我
	private class LoadDataThreadSeen implements Runnable {
		@Override
		public void run() {
			if (!NetWorkUtils.checkNetWork(context)) {
				Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG)
						.show();
				return;
			}
			initDatas();
		}
	}

	// 加载数据   我的消息
	private class LoadDataThreadMsg implements Runnable {
		@Override
		public void run() {
			if (!NetWorkUtils.checkNetWork(context)) {
				Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG)
						.show();
				return;
			}
			initDataMsg();
		}
	}

	// 加载数据   职位订阅
	private class LoadDataThreadSubscribe implements Runnable {
		@Override
		public void run() {
			if (!NetWorkUtils.checkNetWork(context)) {
				Toast.makeText(context, "连接网络失败，请检查网络！", Toast.LENGTH_LONG)
						.show();
				return;
			}
			initDataSubscribe();
		}
	}

	// 初始化 谁看过我
	private void initDatas() {
		viewedList = messageControllers.getResumeViewew();
		handler.sendEmptyMessage(1);
	}

	// 初始化 我的消息
	private void initDataMsg() {
		messageList = messageControllers.getMessage();
		handler.sendEmptyMessage(2);
	}

	// 初始化 消息订阅
	private void initDataSubscribe() {
		subPush = JobKeeper.readSubPush(context);
		final int scope = 2;
		final int size = 10;
		final int x = -1;
		final int xx = 0;
		if (subPush.status == 1) {
			positionList = controller.getPositions("", scope, 1, size,
					sp.getString("address_code", null),
					sp.getString("posi_code", null), x, xx, xx,
					sp.getString("salar", 0 + ""),
					sp.getString("roon", 0 + ""),
					sp.getString("nature", 0 + ""));
		}
		handler.sendEmptyMessage(3);
	}

	// 刷新界面
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			// 谁看过我
			case 1:
				if (viewedList == null || viewedList.size() == 0) {
					line_msg_visibility.setVisibility(View.VISIBLE);
					viewedListView.setVisibility(View.GONE);
				} else {
					line_msg_visibility.setVisibility(View.GONE);
					viewedListView.setVisibility(View.VISIBLE);
					viewedAdapter = new MessageSeenAdapter(context, viewedList,viewedListView);
					viewedListView.setAdapter(viewedAdapter);
				}
				break;
			case 11:
				line_msg_visibility.setVisibility(View.VISIBLE);
				viewedListView.setVisibility(View.GONE);
				break;
			// 我的消息
			case 2:
				if (messageList == null || messageList.size() == 0) {
					line_msg_mymsg.setVisibility(View.VISIBLE);
					messageView.setVisibility(View.GONE);
				} else {
					line_msg_mymsg.setVisibility(View.GONE);
					messageView.setVisibility(View.VISIBLE);
					messageAdapter = new MessageMyAdapter(context, messageList,messageView);
					messageView.setAdapter(messageAdapter);
				}
				break;
			case 22:
				line_msg_mymsg.setVisibility(View.VISIBLE);
				messageView.setVisibility(View.GONE);
				break;
			// 职位订阅
			case 3:
				if (positionList == null || positionList.size() == 0) {
					line_msg_jobmsg.setVisibility(View.VISIBLE);
					positionListView.setVisibility(View.GONE);
					t1.setVisibility(View.GONE);
					t2.setVisibility(View.VISIBLE);
					t3.setVisibility(View.GONE);
				} else {
					line_msg_jobmsg.setVisibility(View.GONE);
					positionListView.setVisibility(View.VISIBLE);
					positionAdapter = new MessageAdapter(context, positionList);
					positionListView.setAdapter(positionAdapter);
				}
				break;
			case 4:
				Toast.makeText(context, "必须登录哦！", Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

	/**
	 * 初始化 图片
	 */
	private void InitImageView() {
		ViewGroup.LayoutParams params = imageView.getLayoutParams();
		params.width = Configure.screenWidth / 3;
		imageView.setLayoutParams(params);
		offset = (Configure.screenWidth / 3 - imageView.getWidth()) / 2;
		bmpW = imageView.getWidth();
	}

	/**
	 * 头标监听
	 */

	private void InitTextView() {
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));

	}

	/**
	 * 
	 * 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}

	}

	// 适配器
	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	/**
	 * 监听器
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		// 改变选中状态
		public void onPageSelected(int arg0) {
			currIndex = arg0;
			switch (arg0) {
			// 职位订阅
			case 0:
				MobclickAgent.onEvent(context, "message_subscribe");
				Animation animation = new TranslateAnimation(one * currIndex,one * arg0, 0, 0);
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(300);
				imageView.startAnimation(animation);
				textView1.setBackgroundColor(Color.WHITE);
				textView2.setBackgroundResource(R.drawable.ic_hand_bg);
				textView3.setBackgroundResource(R.drawable.ic_hand_bg);
				msg_set_job.setVisibility(View.VISIBLE);
				mExecutorService.submit(new LoadDataThreadSubscribe());
				if (stauts == 1) {
					mExecutorService.submit(new LoadDataThreadSubscribe());
				} else if (stauts == 0) {
					positionList = null;
					handler.sendEmptyMessage(3);
				}
				break;
			// 谁看过我
			case 1:
				MobclickAgent.onEvent(context, "message_who_scan_me");
				Animation animation1 = new TranslateAnimation(one * currIndex,one * arg0, 0, 0);
				animation1.setFillAfter(true);// True:图片停在动画结束位置
				animation1.setDuration(300);
				imageView.startAnimation(animation1);
				textView1.setBackgroundResource(R.drawable.ic_hand_bg);
				textView2.setBackgroundColor(Color.WHITE);
				textView3.setBackgroundResource(R.drawable.ic_hand_bg);
				msg_set_job.setVisibility(View.INVISIBLE);
				mExecutorService.submit(new LoadDataThreadSeen());
				// 谁看过我 跳转
				but_msg_seed.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mApplication.user_ticket != null&& mApplication.user.getName() != null) {
							Intent intent = new Intent(context,MyResumeActivity.class);
							startActivity(intent);
						} else {
							handler.sendEmptyMessage(4);
							Intent intent = new Intent(context,LoginActivity.class);
							startActivity(intent);
						}
					}
				});
				if (viewedList==null||viewedList.size()==0) {
					handler.sendEmptyMessage(11);
				}
				break;
			// 我的消息
			case 2:
				MobclickAgent.onEvent(context, "message_my_message");
				Animation animation2 = new TranslateAnimation(one * currIndex,one * arg0, 0, 0);
				animation2.setFillAfter(true);// True:图片停在动画结束位置
				animation2.setDuration(300);
				imageView.startAnimation(animation2);
				textView1.setBackgroundResource(R.drawable.ic_hand_bg);
				textView2.setBackgroundResource(R.drawable.ic_hand_bg);
				textView3.setBackgroundColor(Color.WHITE);
				msg_set_job.setVisibility(View.INVISIBLE);
				mExecutorService.submit(new LoadDataThreadMsg());
				messageView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						if (!NetWorkUtils.checkNetWork(context)) {
							Toast.makeText(context, "连接网络失败，请检查网络！",Toast.LENGTH_LONG).show();
							handler.sendEmptyMessage(11);
							return;
						}
						int index = (int) id;
						int msg_id = messageAdapter.getItemObjectId(index);
						Intent intent = new Intent(context,MessageDetailsActivity.class);
						intent.putExtra("msg_id", msg_id);
						startActivity(intent);
					}
				});
				// 这里要跳转到 职位申请页面
				but_mymsg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mApplication.user_ticket != null
								&& mApplication.user.getName() != null) {
							MainActivity.mainActivity.setTabSelection(0);

						} else {
							handler.sendEmptyMessage(4);
							Intent intent = new Intent(context,
									LoginActivity.class);
							startActivity(intent);

						}
					}
				});
                if (messageList==null||messageList.size()==0) {
					handler.sendEmptyMessage(22);
				}
				break;
			}
		}
	}

	// 处理返回值
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (data == null) {
				break;
			}
			s_category = data.getStringExtra("posi_code");
			s_address = data.getStringExtra("address_code");
			s_salry = data.getStringExtra("salar");
			s_board = data.getStringExtra("roon");
			s_job = data.getStringExtra("nature");
			stauts = Integer.parseInt(data.getStringExtra("status"));
			if (stauts == 1) {
				mExecutorService.submit(new LoadDataThreadSubscribe());
			} else if (stauts == 0) {
				positionList = null;
				handler.sendEmptyMessage(3);
			}
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("MessageFragment");
		if (stauts == 1) {
			mExecutorService.submit(new LoadDataThreadSubscribe());
		} else if (stauts == 0) {
			positionList = null;
			handler.sendEmptyMessage(3);
		}
		mExecutorService.submit(new LoadDataThreadMsg());
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MessageFragment");
	}

	// 我的消息
	public void refresh() {
		if (currIndex == 1) {
			mExecutorService.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					initDataMsg();
				}
			});
		}
	}
	// 职位订阅
	public void refreshs() {
		if (stauts == 1) {
			mExecutorService.submit(new LoadDataThreadSubscribe());
		} else if (stauts == 0) {
			positionList = null;
			handler.sendEmptyMessage(3);
		}
	}
}