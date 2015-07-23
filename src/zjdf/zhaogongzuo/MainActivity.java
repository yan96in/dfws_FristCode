package zjdf.zhaogongzuo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.personal.LoginActivity;
import zjdf.zhaogongzuo.configures.Configure;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.AddressController;
import zjdf.zhaogongzuo.controllers.MoreController;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.databases.sharedpreferences.SetsKeeper;
import zjdf.zhaogongzuo.entity.State;
import zjdf.zhaogongzuo.entity.User;
import zjdf.zhaogongzuo.entity.Versions;
import zjdf.zhaogongzuo.fragments.MessageFragment;
import zjdf.zhaogongzuo.fragments.MoreFragment;
import zjdf.zhaogongzuo.fragments.MycenterFragment;
import zjdf.zhaogongzuo.fragments.SearchFragment;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.service.DownloadService;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import zjdf.zhaogongzuo.utils.ThirdParty;
import zjdf.zhaogongzuo.utils.ThreadPoolProxy;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.igexin.slavesdk.MessageManager;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity {

	/**
	 * 搜索职位
	 */
	private SearchFragment mSearchFm;
	private TextView menu_item_search;
	/**
	 * 消息中心
	 */
	private MessageFragment mMessageFm;
	private TextView menu_item_msg;
	/**
	 * 个人中心
	 */
	private MycenterFragment mMycenterFm;
	private TextView menu_item_mycenter;
	/**
	 * 更多
	 */
	private MoreFragment mMoreFm;
	private TextView menu_item_more;
	/**
	 * 管理器
	 */
	private FragmentManager mFragmentManager;

	/** 没有选中 */
	private int color_normal;
	/** 选中状态 */
	private int color_selected;
	/** 搜索图标 */
	private Drawable searchDrawable_n, searchDrawable_p;
	/** 消息图标 */
	private Drawable msgDrawable_n, msgDrawable_p;
	/** 个人中心图标 */
	private Drawable mycenterDrawable_n, mycenterDrawable_p;
	/** 更多图标 */
	private Drawable moreDrawable_n, moreDrawable_p;

	private User user;
	private ApplicationConfig mApplication;// 全局控制

	private LinearLayout main_tabs;
	private Context context;// 上下文
	private MoreController moreController;// 检查更新

	// 获取当前地理位置
	private LocationClient locationClient = null;
	private final int UPDATE_TIME = 10000;
	private double longitude;// 经度
	private double latitude;// 纬度
	private String city;// 城市
	private String addrStr;// 编码

	private PersonalController personalControllers;// 控制器
	public static State state;// 显示消息数量
	private TextView menu_item_msg_notice;// 消息数量
	private TextView menu_item_mycenter_notice;// 个人中心 消息数量
	public static MainActivity mainActivity;
	private int bb = 1;
	private String mAreaCode;

	/**
	 * 检查更新
	 */
	private Versions version;// 应用程序版本
	private DownloadService.DownloadBinder binder;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_main);
		context = this;
		mainActivity = this;
		Configure.init(this);
		mFragmentManager = getSupportFragmentManager();
		mApplication = (ApplicationConfig) context.getApplicationContext();
		moreController = new MoreController(context);
		personalControllers = new PersonalController(context);
		MessageManager.getInstance().initialize(context);
		ThirdParty.registerWEIXIN(context);// 注册微信，和友盟
		MobclickAgent.openActivityDurationTrack(false);
		initService();
		initView();
		initDrawable();
		setTabSelection(0);
		locationClient = new LocationClient(context);
		if (locationClient == null) {
			return;
		}
		if (locationClient.isStarted()) {
			locationClient.stop();
		} else {
			locationClient.start();
			locationClient.requestLocation();
		}
		setLocation();
		checkOptUpdateVersion();
	}

	private void initService() {
		// 百度定位SDK需要的
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
	}

	/**
	 * <pre>
	 * 初始化菜单
	 * </pre>
	 * 
	 */
	private void initView() {
		menu_item_search = (TextView) findViewById(R.id.menu_item_search);
		menu_item_msg = (TextView) findViewById(R.id.menu_item_msg);
		menu_item_mycenter = (TextView) findViewById(R.id.menu_item_mycenter);
		menu_item_more = (TextView) findViewById(R.id.menu_item_more);
		menu_item_msg_notice = (TextView) findViewById(R.id.menu_item_msg_notice);
		menu_item_mycenter_notice = (TextView) findViewById(R.id.menu_item_mycenter_notice);
		menu_item_search.setOnClickListener(listener);
		menu_item_msg.setOnClickListener(listener);
		menu_item_mycenter.setOnClickListener(listener);
		menu_item_more.setOnClickListener(listener);
		color_normal = Color.parseColor("#666666");
		color_selected = Color.parseColor("#fc9a39");

	}

	/**
	 * <pre>
	 * 初始化图标
	 * </pre>
	 * 
	 */
	private void initDrawable() {
		searchDrawable_n = getResources().getDrawable(
				R.drawable.ic_menu_search_49_40_n);
		searchDrawable_p = getResources().getDrawable(
				R.drawable.ic_menu_search_49_40_p);
		searchDrawable_n.setBounds(0, 0, searchDrawable_n.getMinimumWidth(),
				searchDrawable_n.getMinimumHeight());
		searchDrawable_p.setBounds(0, 0, searchDrawable_p.getMinimumWidth(),
				searchDrawable_p.getMinimumHeight());

		msgDrawable_n = getResources().getDrawable(
				R.drawable.ic_menu_msg_49_40_n);
		msgDrawable_p = getResources().getDrawable(
				R.drawable.ic_menu_msg_49_40_p);
		msgDrawable_n.setBounds(0, 0, msgDrawable_n.getMinimumWidth(),
				msgDrawable_n.getMinimumHeight());
		msgDrawable_p.setBounds(0, 0, msgDrawable_p.getMinimumWidth(),
				msgDrawable_p.getMinimumHeight());

		mycenterDrawable_n = getResources().getDrawable(
				R.drawable.ic_menu_mycenter_49_40_n);
		mycenterDrawable_p = getResources().getDrawable(
				R.drawable.ic_menu_mycenter_49_40_p);
		mycenterDrawable_n.setBounds(0, 0,
				mycenterDrawable_n.getMinimumWidth(),
				mycenterDrawable_n.getMinimumHeight());
		mycenterDrawable_p.setBounds(0, 0,
				mycenterDrawable_p.getMinimumWidth(),
				mycenterDrawable_p.getMinimumHeight());

		moreDrawable_n = getResources().getDrawable(
				R.drawable.ic_menu_more_49_40_n);
		moreDrawable_p = getResources().getDrawable(
				R.drawable.ic_menu_more_49_40_p);
		moreDrawable_n.setBounds(0, 0, moreDrawable_n.getMinimumWidth(),
				moreDrawable_n.getMinimumHeight());
		moreDrawable_p.setBounds(0, 0, moreDrawable_p.getMinimumWidth(),
				moreDrawable_p.getMinimumHeight());
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.menu_item_msg:
				setTabSelection(1);
				break;
			case R.id.menu_item_mycenter:
				if (StringUtils.isEmpty(mApplication.user_ticket)
						|| StringUtils.isEmpty(mApplication.user.getName())) {
					startActivity(new Intent(context, LoginActivity.class));
					return;
				} else {
					setTabSelection(2);
				}
				break;
			case R.id.menu_item_more:

				setTabSelection(3);

				break;
			case R.id.menu_item_search:
			default:
				setTabSelection(0);
				break;

			}
		}
	};

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	public void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			menu_item_search
					.setBackgroundResource(R.drawable.ic_menu_item_120_80_p);
			menu_item_search.setCompoundDrawables(null, searchDrawable_p, null,
					null);
			menu_item_search.setTextColor(color_selected);
			if (mSearchFm == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mSearchFm = new SearchFragment();
				transaction.add(R.id.frame_content, mSearchFm);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mSearchFm);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			menu_item_msg
					.setBackgroundResource(R.drawable.ic_menu_item_120_80_p);
			menu_item_msg.setCompoundDrawables(null, msgDrawable_p, null, null);
			menu_item_msg.setTextColor(color_selected);
			if (mMessageFm == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				mMessageFm = new MessageFragment();
				transaction.add(R.id.frame_content, mMessageFm);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(mMessageFm);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			menu_item_mycenter
					.setBackgroundResource(R.drawable.ic_menu_item_120_80_p);
			menu_item_mycenter.setCompoundDrawables(null, mycenterDrawable_p,
					null, null);
			menu_item_mycenter.setTextColor(color_selected);

			// startActivity(new Intent(context, LoginActivity.class));
			if (mMycenterFm == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				mMycenterFm = new MycenterFragment();
				transaction.add(R.id.frame_content, mMycenterFm);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(mMycenterFm);
			}

			break;
		case 3:
			// 当点击了设置tab时，改变控件的图片和文字颜色

			menu_item_more
					.setBackgroundResource(R.drawable.ic_menu_item_120_80_p);
			menu_item_more.setCompoundDrawables(null, moreDrawable_p, null,
					null);
			menu_item_more.setTextColor(color_selected);
			if (mMoreFm == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				mMoreFm = new MoreFragment();
				transaction.add(R.id.frame_content, mMoreFm);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(mMoreFm);
			}

			break;
		}
		// transaction.commit();
		transaction.commitAllowingStateLoss();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {

		menu_item_search.setCompoundDrawables(null, searchDrawable_n, null,
				null);
		menu_item_search
				.setBackgroundResource(R.drawable.ic_menu_item_120_80_n);
		menu_item_search.setTextColor(color_normal);
		menu_item_msg.setCompoundDrawables(null, msgDrawable_n, null, null);
		menu_item_msg.setBackgroundResource(R.drawable.ic_menu_item_120_80_n);
		menu_item_msg.setTextColor(color_normal);
		menu_item_mycenter.setCompoundDrawables(null, mycenterDrawable_n, null,
				null);
		menu_item_mycenter
				.setBackgroundResource(R.drawable.ic_menu_item_120_80_n);
		menu_item_mycenter.setTextColor(color_normal);
		menu_item_more.setCompoundDrawables(null, moreDrawable_n, null, null);
		menu_item_more.setBackgroundResource(R.drawable.ic_menu_item_120_80_n);
		menu_item_more.setTextColor(color_normal);
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (mMessageFm != null) {
			transaction.hide(mMessageFm);
		}
		if (mMoreFm != null) {
			transaction.hide(mMoreFm);
		}
		if (mSearchFm != null) {
			transaction.hide(mSearchFm);
		}
		if (mMycenterFm != null) {
			transaction.hide(mMycenterFm);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int,
	 * android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (bb < 1) {
				finish();
				return true;
			} else {
				bb--;
				CustomMessage
						.showToast(context, "再按一次，退出应用", Gravity.CENTER, 0);
				return false;
			}
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	// 貌似是百度用的
	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	public BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				info = connectivityManager.getActiveNetworkInfo();
				
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (info != null && info.isAvailable()) {
							if (mApplication.user_ticket != null
									|| mApplication.user.getName() != null) {
								ThreadPoolProxy.submit(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										state = personalControllers.getStatus();
										if (state != null) {
											sendMessage(1);
										} else {
											sendMessage(0);
										}
									}
								});
								if (mSearchFm != null) {
									mSearchFm.loadDatas();
								}
								if (mMessageFm != null) {
									mMessageFm.refresh();
								}
							}
						} else {
							CustomMessage.showToast(context, "网络已经断开，请检查！", 0);
						}
					}
				}, 160);
			}
		}
	};

	/**
	 * 设置条件 通过百度SDK 获取当前地址
	 */
	private void setLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 是否打开GPS
		option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
		option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
		option.setProdName("最佳东方"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setScanSpan(UPDATE_TIME);// 设置定时定位的时间间隔。单位毫秒
		option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
		locationClient.setLocOption(option);
		// 注册位置监听器
		locationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				if (location == null) {
					return;
				}
				double Latitude = location.getLatitude();
				double Longitude = location.getLongitude();
				String addrStr = location.getAddrStr();
				String CityCode = location.getCityCode();
				// 将用户输入的经纬度值转换成int类型
				longitude = Latitude;
				latitude = Longitude;
				city = location.getCity();
				ApplicationConfig.city = city;
				ApplicationConfig.longitude = longitude;
				ApplicationConfig.latitude = latitude;
				ApplicationConfig.addrStr = addrStr;
				ApplicationConfig.CityCode = CityCode;
				if (!StringUtils.isEmpty(longitude + "") && longitude != 0) {
					getArea();
				}
			}

			@Override
			public void onReceivePoi(BDLocation location) {
			}
		});
	}

	/**
	 * 获取本地地址
	 */
	public void getArea() {
		ThreadPoolProxy.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mAreaCode = moreController.get_Location(longitude + "",
						latitude + "");
				handler.sendEmptyMessage(11);
			}
		});
	}

	private void stopLocation() {
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.stop();
			// locationClient.requestLocation();
			locationClient = null;
		}
	}

	// 销毁
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopLocation();
		unregisterReceiver(mReceiver);

		// 下子APK服务
		if (null != binder) {
			context.unbindService(conn);
		}
		cancel();
		ApplicationConfig.isResume=false;
	}

	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ApplicationConfig.isResume=true;
		bb = 1;
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
		if (mApplication.user_ticket != null
				|| mApplication.user.getName() != null) {
			ThreadPoolProxy.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					state = personalControllers.getStatus();
					if (state != null) {
						sendMessage(1);
					} else {
						sendMessage(0);
					}
				}
			});
			if (mSearchFm != null) {
				mSearchFm.loadDatas();
			}
			if (mMessageFm != null) {
				mMessageFm.refresh();
			}
		}
		if (StringUtils.isEmpty(mApplication.user_ticket)) {
			menu_item_msg_notice.setVisibility(View.INVISIBLE);
			menu_item_msg_notice.setText("");
		}
		if (AddressController.allAreas==null) {
			ThreadPoolProxy.submit(new Runnable() {
				
				@Override
				public void run() {
					mApplication.initDatas(context);
				}
			});
		}
	}

	// 刷新消息数量
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1:
				if (state.getUnread_message_num() > 0) {
					menu_item_msg_notice.setVisibility(View.VISIBLE);
					menu_item_msg_notice.setText(state.getUnread_message_num()
							+ "");
				} else {
					menu_item_msg_notice.setVisibility(View.INVISIBLE);
					menu_item_msg_notice.setText("");
				}
				if (mApplication.user_ticket != null
						|| mApplication.user.getName() != null) {
					if (mMycenterFm != null) {
						mMycenterFm.setupView(state);
					}
				}
				break;
			case 11:
				if (!StringUtils.isEmpty(mAreaCode)
						&& AddressController.mapAreas != null) {
					AddressController.mArea = AddressController.mapAreas
							.get(mAreaCode);
					stopLocation();
				}
				break;

			case 2:
				String currentVersion = StringUtils.getAppVersionName(context);
				String netVersion = version.getLatest_version();
				// String netVersion = "2.0";
				int a = StringUtils.getAppVersionNameInt(context,"zjdf.zhaogongzuo");
				int b = StringUtils.parseArrayToInt(netVersion);
				if (!StringUtils.isEmpty(netVersion)) {
					if (currentVersion.equalsIgnoreCase(netVersion)) {
						// Toast.makeText(context,
						// "暂无新版本，当前已是最新版本!",Toast.LENGTH_LONG).show();
					} else if (b > a) {
						dialog();
					} else {
						// Toast.makeText(context,
						// "暂无新版本，当前已是最新版本!",Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(context, "获取版本信息失败，请重试！", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case 3:
				Toast.makeText(context, "暂无新版本，当前已是最新版本", Toast.LENGTH_LONG)
						.show();
				break;
			}
		};
	};

	/**
	 * 发送消息
	 * 
	 * @param what
	 */
	private void sendMessage(int what) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		handler.sendMessage(msg);
	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();
		checkUpdate();
	}

	// 对话框
	private void dialog() {
		new AlertDialog.Builder(this)
				.setTitle(version.getUpdate_log())
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(version.getLatest_version())
				.setPositiveButton("立即更新",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								doUpdate();
								dialog.dismiss();
							}
						})
				.setNegativeButton("稍后更新",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 点击“返回”后的操作,这里不设置没有任何操作
								dialog.dismiss();
							}
						}).show();
	}

	// 检查更新
	public void checkUpdate() {
		if (!NetWorkUtils.checkNetWork(context)) {
			Toast.makeText(context, "请检查网络连接！", Toast.LENGTH_LONG).show();
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				version = moreController.get_checkVersion();
				if (version != null) {
					handler.sendEmptyMessage(2);
				}/*
				 * else { handler.sendEmptyMessage(3); }
				 */

			}
		}).start();
	}

	// 立即更新
	public void doUpdate() {
		url = version.getApp_url();
		Intent intent = new Intent(context, DownloadService.class);
		intent.putExtra("url", url);
		intent.putExtra("version", version.getLatest_version());
		context.startService(intent); // 如果先调用startService,则在多个服务绑定对象调用unbindService后服务仍不会被销毁
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (DownloadService.DownloadBinder) service;
			binder.start();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	public void cancel() {
		if (null != binder && !binder.isCancelled()) {
			binder.cancel();
		}
	}

	private void checkOptUpdateVersion(){
		if (!NetWorkUtils.checkNetWork(context)) {
			return;
		}
		final int ov=SetsKeeper.readOptionVersion(context);
		ThreadPoolProxy.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int v=getOptVersion();
				if (v>ov) {
					getOptDatas();
					SetsKeeper.keepOptionVersion(context, v);
					mApplication.initDatas(context);
				}
			}
		});
	}
	
	/**
	 * 
	 *<pre>选项版本  </pre>
	 * @return List
	 */
	private int getOptVersion(){
		int v=0;
		String result=HttpTools.getNetString_get(FrameConfigures.HOST+"data/optionsvernum");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("data")) {
					JSONObject object=jObject.getJSONObject("data");
					if (object.has("ver_num")) {
						v=object.getInt("ver_num");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return v;
			}
		}
		return v;
	}
	
	/**
	 * 
	 *<pre>选项版本  </pre>
	 * @return List
	 */
	private void getOptDatas(){
		String result=HttpTools.getNetString_get(FrameConfigures.HOST+"data/options");
		if (!StringUtils.isEmpty(result)) {
			try {
				JSONObject jObject=new JSONObject(result);
				if (jObject.has("data")) {
					JSONObject object=jObject.getJSONObject("data");
					StringBuffer sb=new StringBuffer();
					sb.append("{");
					if (object.has("positions")) {
						sb.append("\"positions\":"+object.getString("positions")+",");
					}
					if (object.has("opts_update_time")) {
						sb.append("\"opts_update_time\":"+object.getString("opts_update_time")+",");
					}
					if (object.has("opts_work_years")) {
						sb.append("\"opts_work_years\":"+object.getString("opts_work_years")+",");
					}
					if (object.has("opts_education")) {
						sb.append("\"opts_education\":"+object.getString("opts_education")+",");
					}
					if (object.has("opts_salary")) {
						sb.append("\"opts_salary\":"+object.getString("opts_salary")+",");
					}
					if (object.has("opts_room_board")) {
						sb.append("\"opts_room_board\":"+object.getString("opts_room_board")+",");
					}
					if (object.has("opts_work_mode")) {
						sb.append("\"opts_work_mode\":"+object.getString("opts_work_mode")+",");
					}
					if (object.has("opts_id_type")) {
						sb.append("\"opts_id_type\":"+object.getString("opts_id_type")+",");
					}
					if (object.has("opts_industry")) {
						sb.append("\"opts_industry\":"+object.getString("opts_industry")+",");
					}
					if (object.has("opts_star_level")) {
						sb.append("\"opts_star_level\":"+object.getString("opts_star_level")+",");
					}
					if (object.has("opts_arrival_time")) {
						sb.append("\"opts_arrival_time\":"+object.getString("opts_arrival_time")+",");
					}
					if (object.has("opts_edu_major")) {
						sb.append("\"opts_edu_major\":"+object.getString("opts_edu_major")+",");
					}
					if (object.has("opts_language")) {
						sb.append("\"opts_language\":"+object.getString("opts_language")+",");
					}
					if (object.has("opts_master_degree")) {
						sb.append("\"opts_master_degree\":"+object.getString("opts_master_degree")+",");
					}
					if (object.has("opts_company_type")) {
						sb.append("\"opts_company_type\":"+object.getString("opts_company_type")+",");
					}
					if (object.has("opts_company_industry")) {
						sb.append("\"opts_company_industry\":"+object.getString("opts_company_industry")+",");
					}
					if (object.has("opts_job_status")) {
						sb.append("\"opts_job_status\":"+object.getString("opts_job_status")+",");
					}
					if (object.has("opts_resume_status")) {
						sb.append("\"opts_resume_status\":"+object.getString("opts_resume_status")+",");
					}
					if (object.has("opts_gender")) {
						sb.append("\"opts_gender\":"+object.getString("opts_gender")+",");
					}
					if (object.has("opts_search_salarys")) {
						sb.append("\"opts_search_salarys\":"+object.getString("opts_search_salarys")+",");
					}
					
					if (sb.length()>0) {
						sb.deleteCharAt(sb.lastIndexOf(","));
					}
					sb.append("}");
					
						try {
							FileUtils.writeStringToFile(new File(FrameConfigures.FOLDER_OPTIONS), sb.toString(), "UTF-8");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
