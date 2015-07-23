package zjdf.zhaogongzuo.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.options.OptSalaryActivity.IflashViewsCallback;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.databases.handlers.UserDatabaseHelper;
import zjdf.zhaogongzuo.databases.sharedpreferences.UserKeeper;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.State;
import zjdf.zhaogongzuo.entity.User;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.FileAccess;
import zjdf.zhaogongzuo.utils.MD5Utils;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 个人中心 控制器
 * 
 * @author Administrator
 * 
 */
public class PersonalController extends BaseConttroller {

//	private UserDatabaseHelper helper;
	public PersonalController(Context context) {
		super(context);
//		helper=new UserDatabaseHelper(context);
	}

	/**
	 * 登录
	 */
	public EorrerBean checkLogin(String username, String password) {
		EorrerBean eb = null;
		User user = new User();
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("username", username));
		nv.add(new BasicNameValuePair("password", password));
		nv.add(new BasicNameValuePair("platform", 1 + ""));
		nv.add(new BasicNameValuePair("device_token", ApplicationConfig.client_id));
		String request = NET_HOST + "user/login";
		String sreResult = HttpTools.getNetString_post(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("data")) {
						JSONObject jsonObject = jObject.getJSONObject("data");
						if (!StringUtils.isEmpty(jsonObject)) {
							String uid = jsonObject.getString("user_id");
							mApplication.user.setId(uid);
							ApplicationConfig.email = jsonObject
									.getString("email");
							mApplication.user.setName(jsonObject
									.getString("user_name"));
							mApplication.user_ticket = jsonObject
									.getString("user_ticket");
							// 保存到SharePreference
							user.setId(jsonObject.getString("user_id"));
							user.setName(jsonObject.getString("user_name"));
							user.setPassword(password);
							UserKeeper.keepUser(mContext, user);
							UserKeeper.keepToken(mContext,mApplication.user_ticket);
							keepToFile(username,password,mApplication.user_ticket);
						}
					}
					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * 注册
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public EorrerBean registered(String username, String password,
			String useremail) {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		User user = new User();
		nv.add(new BasicNameValuePair("username", username));
		nv.add(new BasicNameValuePair("password", password));
		nv.add(new BasicNameValuePair("email", useremail));
		nv.add(new BasicNameValuePair("platform", "1"));
		nv.add(new BasicNameValuePair("device_token", ApplicationConfig.client_id));
		String request = NET_HOST + "user/registe";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status")) {
					eb = new EorrerBean();
					eb.status = jObject.getInt("status");
					if (jObject.has("data")) {
						JSONObject jsonObject = jObject.getJSONObject("data");
						if (!StringUtils.isEmpty(jsonObject)) {
							mApplication.user.setId(jsonObject
									.getString("user_id"));
							mApplication.user.setName(jsonObject
									.getString("user_name"));
							String userTicket = jsonObject
									.getString("user_ticket");
							mApplication.user_ticket = userTicket;
							user.setId(jsonObject.getString("user_id"));
							user.setName(jsonObject.getString("user_name"));
							user.setPassword(password);
							UserKeeper.keepUser(mContext, user);
							UserKeeper.keepToken(mContext, userTicket);
							keepToFile(username,password,userTicket);
//							if (helper.isUserExist(username)) {
//								helper.updateUser(user);
//							}else {
//								helper.insertUser(user);
//							}
						}
					}

					if (jObject.has("errCode"))
						eb.errCode = jObject.getInt("errCode");
					if (jObject.has("errMsg"))
						eb.errMsg = jObject.getString("errMsg");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * user_ticket 用户票据 platform 平台 device_token 设备推送码
	 * 
	 * @return
	 */
	public EorrerBean logout() {
		EorrerBean eb = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("platform", 1 + ""));
		nv.add(new BasicNameValuePair("device_token", ApplicationConfig.client_id));
		String request = NET_HOST + "user/logout";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (jObject.has("status")) {
						eb = new EorrerBean();
						eb.status = jObject.getInt("status");
						if (jObject.has("errCode")) {
							eb.errCode = jObject.getInt("errCode");
						}
						if (jObject.has("errMsg")) {
							eb.errMsg = jObject.getString("errMsg");
						}
						if (eb.status==1) {
							keepToFile("","","");
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * 绑定登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public EorrerBean checkLoginBangding(String username, String password,
			String connect_cooperate, String connect_code) {
		EorrerBean eb = null;
		User user = new User();
		String request = "http://sso.veryeast.cn/user/login?username="
				+ username + "&password=" + password + "&connect_cooperate="
				+ connect_cooperate + "&connect_code=" + connect_code
				+ "&login_type=connect_bind&return_type=json&unset_cookie=1";
		String sreResult = HttpTools.getNetString(request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject jsonObject = jObject.getJSONObject("data");
					eb = new EorrerBean();
					if (jsonObject.has("flag")) {
						eb.errCode = jsonObject.getInt("flag");
					}
					if (!StringUtils.isEmpty(jsonObject)) {
						mApplication.user.setId(jsonObject.getString("userid"));
						mApplication.user.setName(jsonObject.getString("username"));
						String user_ticket = jsonObject.getString("ticket");
						mApplication.user_ticket = user_ticket;
						user.setId(jsonObject.getString("userid"));
						user.setName(jsonObject.getString("username"));
						user.setPassword(password);
						UserKeeper.keepUser(mContext, user);
						UserKeeper.keepToken(mContext, user_ticket);
						keepToFile(username,password,user_ticket);
//						if (helper.isUserExist(username)) {
//							helper.updateUser(user);
//						}else {
//							helper.insertUser(user);
//						}
					}				
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * 注册绑定登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public EorrerBean regBangding(String username, String password,
			String email, String connect_cooperate, String connect_code) {
		EorrerBean eb = null;
		User user = new User();
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("username", username));
		nv.add(new BasicNameValuePair("password", password));
		nv.add(new BasicNameValuePair("connect_cooperate", connect_cooperate));
		nv.add(new BasicNameValuePair("connect_code", connect_code));
		nv.add(new BasicNameValuePair("register_type", "connect_bind"));
		nv.add(new BasicNameValuePair("platform", 1 + ""));
		nv.add(new BasicNameValuePair("return_type", "json"));
		nv.add(new BasicNameValuePair("unset_cookie", 1 + ""));
		nv.add(new BasicNameValuePair("email", email));
		nv.add(new BasicNameValuePair("user_type", 2 + ""));
		String request = "http://sso.veryeast.cn/user/register";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		// {"data":{"flag":"1022"}} 出错的时候
		// {"data":{"flag":"1022"}}
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject jsonObject = jObject.getJSONObject("data");
					eb = new EorrerBean();
					if (jsonObject.has("flag")) {
						eb.errCode = jsonObject.getInt("flag");
					}
					if (!StringUtils.isEmpty(jsonObject)) {
						mApplication.user.setId(jsonObject.getString("userid"));
						mApplication.user.setName(jsonObject.getString("username"));
						String user_ticket = jsonObject.getString("ticket");
						mApplication.user_ticket = user_ticket;
						user.setId(jsonObject.getString("userid"));
						user.setName(jsonObject.getString("username"));
						user.setPassword(password);
						UserKeeper.keepUser(mContext, user);
						UserKeeper.keepToken(mContext, user_ticket);
						keepToFile(username,password,user_ticket);
//						if (helper.isUserExist(username)) {
//							helper.updateUser(user);
//						}else {
//							helper.insertUser(user);
//						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return eb;
			}
		}
		return eb;
	}

	/**
	 * 修改 登录 密码
	 * 
	 * @return
	 */
	public int changePass(String old_password, String new_password) {
		int value = 0;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		nv.add(new BasicNameValuePair("old_password", old_password));
		nv.add(new BasicNameValuePair("new_password", new_password));
		String request = NET_HOST + "user/rest_password";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (jObject.has("status")) {
						value = jObject.getInt("status");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return value;
			}
		}
		return value;
	}

	/**
	 * 获取个人中心、消息 状态信息
	 * 
	 * @return
	 */
	public State getStatus() {
		State state = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket", mApplication.user_ticket));
		String request = NET_HOST + "user/status";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (jObject.has("status") && jObject.optInt("status") == 1) {
					JSONObject jsonObject = jObject.getJSONObject("data");
					if (!StringUtils.isEmpty(jsonObject)) {
						state = new State();
						state.setResume_complete(jsonObject
								.optDouble("resume_complete"));
						state.setResume_status(jsonObject
								.getInt("resume_status"));
						state.setAvatar(jsonObject.getString("avatar"));
						state.setApplied_num(jsonObject.getInt("applied_num"));
						state.setFavorited_num(jsonObject
								.getInt("favorited_num"));
						state.setFollowed_num(jsonObject.getInt("followed_num"));
						state.setUnread_message_num(jsonObject
								.getInt("unread_message_num"));
						state.setIs_need_fill_micro_resume(jsonObject
								.getInt("is_need_fill_micro_resume"));
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return state;
			}
		}
		return state;
	}

	/**
	 * 获取网落图片资源
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			// conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}

	/**
	 * 加载本地图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void keepToFile(String name,String password,String token){
		try {
			FileAccess.writeToFile(FrameConfigures.FOLDER_YY, "yy.yy", StringUtils.isEmpty(name)?"":MD5Utils.encryptDES(name, "1234abcd"));
			FileAccess.writeToFile(FrameConfigures.FOLDER_YY, "zz.zz", StringUtils.isEmpty(password)?"":MD5Utils.encryptDES(password, "abcd1234"));
			FileAccess.writeToFile(FrameConfigures.FOLDER_YY, "kk.kk", StringUtils.isEmpty(token)?"":MD5Utils.encryptDES(token, "kkcd1234"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	// 关闭数据库
//	public void CloseDB() {
//		if (helper!=null)
//			helper.close();
//	}
}
