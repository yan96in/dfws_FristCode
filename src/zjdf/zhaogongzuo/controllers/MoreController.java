package zjdf.zhaogongzuo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.entity.SetPush;
import zjdf.zhaogongzuo.entity.Versions;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.DeviceUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;

/**
 * 更多 控制器
 * 
 * @author Administrator
 * 
 */
public class MoreController extends BaseConttroller{
	
	public MoreController(Context context) {
		super(context);
	}

	/**
	 * 意见反馈
	 * 
	 * @param apikey
	 *            apikey true string -
	 * @param contact
	 *            true string 联系方式（手机号码、邮箱）。
	 * @param content
	 *            true string 意见反馈正文。
	 * @param device_model
	 *            true string 设备硬件型号
	 * @param device_system
	 *            true string 设备系统，例：iOS 7.0、Android 4.4
	 * @param app_version
	 *            true string 应用版本，例：1.0
	 */
	public int set_FeedbackMore(String contact, String content) {
		int number = 0;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("contact", contact));
		nv.add(new BasicNameValuePair("content", content));
		nv.add(new BasicNameValuePair("device_model", DeviceUtils
				.getMobleInfo()));
		nv.add(new BasicNameValuePair("device_system", DeviceUtils
				.getSystemVersion()));
		nv.add(new BasicNameValuePair("app_version", "3.0"));
		String request = NET_HOST+"util/feedback";
		String sreResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(sreResult)) {
			try {
				JSONObject jObject = new JSONObject(sreResult);
				if (!StringUtils.isEmpty(jObject)) {
					number = jObject.getInt("status");
				}
				return 1;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return number;

	}

	/**
	 * 检查更新
	 * 
	 * @param apikey
	 *            key
	 * @param device_type
	 *            {"status":1,"data":{"latest_version":"1.0","update_log":
	 *            "123456","app_url":null}}
	 */
	public Versions get_checkVersion() {
		Versions versions = null;
		String strResult = "";
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("device_type", 1 + ""));
		String request = NET_HOST+"util/check_version";
		strResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject = new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject jsonObject = jObject.getJSONObject("data");
					if (jObject.has("data")) {
						versions = new Versions();
						versions.setLatest_version(jsonObject
								.getString("latest_version"));
						versions.setUpdate_log(jsonObject
								.getString("update_log"));
						 versions.setApp_url(jsonObject.getString("app_url"));
						//versions.setApp_url("http://gdown.baidu.com/data/wisegame/b171d56b3df67755/QQ_110.apk");
						return versions;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return versions;
	}

	/**
	 * 更新 推送设置
	 * 
	 * @paramapikey true string -
	 * @param user_ticket
	 *            true string 用户票据
	 * @param device_token
	 *            true string 设备推送码
	 * @param push_enable
	 *            false int 推送是否开启（总开关）,1为开启，0为关闭。
	 * @param push_job_enable
	 *            false int 是否推送职位订阅,1为推送，0为不推送。
	 * @param push_msg_enable
	 *            false int 是否推送我的消息,1为推送，0为不推送。
	 * @param push_viewed_enable
	 *            false int 是否推送谁看过我,1为推送，0为不推送。
	 * @param push_meets_enable
	 *            false int 是否推送招聘会,1为推送，0为不推送。
	 */
	public int Set_PushNotice(int push_enable, int push_job_enable,
			int push_msg_enable, int push_viewed_enable, int push_meets_enable) {
		int state=0;
		SetPush setPush = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket",mApplication.user_ticket));
		nv.add(new BasicNameValuePair("platform", 1+""));
		nv.add(new BasicNameValuePair("device_token",ApplicationConfig.Push_token));
		nv.add(new BasicNameValuePair("push_enable", push_enable + ""));
		nv.add(new BasicNameValuePair("push_job_enable", push_job_enable + ""));
		nv.add(new BasicNameValuePair("push_msg_enable", push_msg_enable + ""));
		nv.add(new BasicNameValuePair("push_viewed_enable", push_viewed_enable+ ""));
		nv.add(new BasicNameValuePair("push_meets_enable", push_meets_enable+ ""));
		nv.add(new BasicNameValuePair("device_token", mApplication.client_id));
		String request = NET_HOST+"util/set_push_notice_setting";
		String strResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject = new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject jsonObject = jObject.getJSONObject("data");
					if (jsonObject != null) {
						setPush = new SetPush();
						setPush.setPush_enable(jsonObject.getInt("push_enable"));
						setPush.setPush_job_enable(jsonObject.getInt("push_job_enable"));
						setPush.setPush_meets_enable(jsonObject.getInt("push_meets_enable"));
						setPush.setPush_msg_enable(jsonObject.getInt("push_msg_enable"));
						setPush.setPush_viewed_enable(jsonObject.getInt("push_viewed_enable"));
						return state;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return state;
			}
		}

		return state;

	}

	/**
	 * 获取推送设置
	 * 
	 * @param apikey
	 *            true string 、
	 * @param user_ticket
	 *            true string 用户票据
	 * @param device_token
	 *            true string 设备推送码
	 *            {"status":1,"data":{"push_enable":1,"push_job_enable"
	 *            :1,"push_msg_enable"
	 *            :1,"push_viewed_enable":1,"push_meets_enable":1}}
	 * 
	 */
	public SetPush get_PushNotice() {
		SetPush setPush = null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("user_ticket",
				mApplication.user_ticket));
		nv.add(new BasicNameValuePair("device_type",
				mApplication.Push_token));
		String request = NET_HOST+"util/get_push_notice_setting";
		String strResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject = new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					JSONObject jsonObject = jObject.getJSONObject("data");
					if (jsonObject != null) {
						setPush = new SetPush();
						setPush.setPush_enable(jsonObject.getInt("push_enable"));
						setPush.setPush_job_enable(jsonObject.getInt("push_job_enable"));
						setPush.setPush_meets_enable(jsonObject.getInt("push_meets_enable"));
						setPush.setPush_msg_enable(jsonObject.getInt("push_msg_enable"));
						setPush.setPush_viewed_enable(jsonObject.getInt("push_viewed_enable"));
						return setPush;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return setPush;
	}

	/**
	 * 获取当前位置
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 */
	public String get_Location(String longitude, String latitude) {
		String code = "050100";
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("longitude",longitude));
		nv.add(new BasicNameValuePair("latitude",latitude));
		String request = NET_HOST+"util/get_location";
		String strResult = HttpTools.getHttpRequestString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jObject = new JSONObject(strResult);
				if (jObject.has("status")&&jObject.has("data")) {
					JSONObject jdata = jObject.getJSONObject("data");
					if (jdata.has("code")) {
						code = jdata.getString("code");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return code;
			}
		}
		return code;
	}

}
