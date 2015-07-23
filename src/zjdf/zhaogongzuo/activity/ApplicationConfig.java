package zjdf.zhaogongzuo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import zjdf.zhaogongzuo.controllers.AbsController;
import zjdf.zhaogongzuo.controllers.AddressController;
import zjdf.zhaogongzuo.controllers.OptionController;
import zjdf.zhaogongzuo.controllers.PositionClassifyController;
import zjdf.zhaogongzuo.entity.User;
import zjdf.zhaogongzuo.utils.StringUtils;

public class ApplicationConfig extends Application {
	private static ApplicationConfig instance = null;

	public User user;// 用户实体
	public static List<User> loginStack;
	// public Areas localArea;//本地地址
	public static String Push_token = "";// 推送
	public static String client_id = "";
	public String user_ticket = "";
	// set 获取当前地址
	public static double longitude;// 经度
	public static double latitude;// 纬度
	public static String city;// 城市
	public static String CityCode;//城市编码
	public static String addrStr;//详细地址
	public static String email;//邮箱
	public static boolean isResume=true;
	//百度地图
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;
    public static final String strKey = "XMUtC7n0Nhw7uiHTN8d9tkpj";
	public ApplicationConfig() {

	}

	/**
	 * 全局变量
	 * 
	 * @return
	 */
	public synchronized ApplicationConfig getInstance() {
		if (null == instance) {
			instance = (ApplicationConfig) getApplicationContext();
		}
		return instance;
	}
	
	public static ApplicationConfig getInstances() {

		return instance;
	}
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		user = new User();
		Push_token = StringUtils.getIMEI(this);
		if (loginStack == null) {
			loginStack = new ArrayList<User>();
		} else {
			loginStack.clear();
		}
		initEngineManager(this);
	}


	public void initEngineManager(Context context) {
		getInstance();
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(instance.getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
               // Toast.makeText(instance.getApplicationContext(), "您的网络出错啦！",Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
              //  Toast.makeText(instance.getApplicationContext(), "输入正确的检索条件！",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onGetPermissionState(int iError) {
        	//非零值表示key验证未通过
            if (iError != 0) {
                //授权Key错误：
              //  Toast.makeText(instance.getApplicationContext(), "请在 ApplicationConfig.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "+iError, Toast.LENGTH_LONG).show();
                instance.m_bKeyRight = false;
            }else{
            	ApplicationConfig.getInstances().m_bKeyRight = true;
            	//Toast.makeText(ApplicationConfig.getInstances().getApplicationContext(),  "key认证成功", Toast.LENGTH_LONG).show();
            }
        }
    }
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public String getPush_token() {
		return Push_token;
	}

	public void setPush_token(String push_token) {
		Push_token = push_token;
	}

	public String getUser_ticket() {
		return user_ticket;
	}

	public void setUser_ticket(String user_ticket) {
		this.user_ticket = user_ticket;
	}

	/**
	 * 解析数据
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 */
	public void initDatas(Context context) {
		AbsController.parseDatas(context);
		AddressController.parseHotAreas();
		AddressController.parseAllAreas();
		PositionClassifyController.parsePositionClass();
		OptionController.parseArrivalTime();
		OptionController.parseCompanyType();
		OptionController.parseEducations();
		OptionController.parseEduMajor();
		OptionController.parseGender();
		OptionController.parseIDType();
		OptionController.parseIndustry();
		OptionController.parseJobStatus();
		OptionController.parseLanguage();
		OptionController.parseMasterDegree();
		OptionController.parseResumeStarus();
		OptionController.parseRoomBoard();
		OptionController.parseSalary();
		OptionController.parseStarLevel();
		OptionController.parseUpdateTime();
		OptionController.parseWorkMode();
		OptionController.parseWorks();
	}
}
