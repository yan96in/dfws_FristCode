package zjdf.zhaogongzuo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import zjdf.zhaogongzuo.entity.Message;
import zjdf.zhaogongzuo.entity.Viewed;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;

/**
 * 消息中心 控制器
 * 
 * @author Administrator
 * 
 */
public class MessageControllers extends BaseConttroller{

	private Viewed viewed;
	private Message message;

	public MessageControllers(Context context) {
		super(context);
	}

	/**
	 * 查看简历的企业
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	public List<Viewed> getResumeViewew() {
		ArrayList<Viewed> newsList = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("user_ticket",
				mApplication.user_ticket));
		String request = NET_HOST+"user/resume_viewed_list";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				JSONObject Object = jsonObject.getJSONObject("data");
				if (!StringUtils.isEmpty(Object)) {
					JSONArray array = Object.getJSONArray("list");
					int n = array.length();
					newsList = new ArrayList<Viewed>(n);
					for (int i = 0; i < n; i++) {
						JSONObject data = array.getJSONObject(i);
						viewed = new Viewed();
						viewed.setCompany_name(data.getString("company_name"));
						viewed.setCompany_id(data.optInt("c_userid"));
						viewed.setViewed_times(data.optInt("viewed_times"));
						viewed.setViewed_date(data.getString("viewed_date").substring(0, 10));
						newsList.add(viewed);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return newsList;

	}

	/**
	 * 获取消息列表
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	public List<Message> getMessage() {
		ArrayList<Message> newsList = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("user_ticket",
				mApplication.user_ticket));
		String request = NET_HOST+"user/messages";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(strResult);
				JSONObject Object = jsonObject.getJSONObject("data");
				if (!StringUtils.isEmpty(Object)) {
					JSONArray array = Object.getJSONArray("list");
					int n = array.length();
					newsList = new ArrayList<Message>(n);
					for (int i = 0; i < n; i++) {
						JSONObject data = array.getJSONObject(i);
						message = new Message();
						message.setMessage_id(data.getInt("message_id"));
						message.setType(data.getInt("type"));
						message.setDate(data.getString("date"));
						message.setSender(data.getString("sender"));
						message.setSubject(data.getString("subject"));
						message.setIs_read(data.optInt("is_read"));
						newsList.add(message);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return newsList;

	}

	/**
	 * 消息详情
	 * 
	 * 接口地址
	 * 
	 * POST user/message_detail
	 */
	@SuppressWarnings("null")
	public Message getMessage_detail(int message_id) {
		Message message = null;
		List<NameValuePair> ln = new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("user_ticket",mApplication.user_ticket));
		ln.add(new BasicNameValuePair("message_id", message_id+""));
		String request = NET_HOST+"user/message_detail";
		String strResult = HttpTools.getHttpRequestString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			try {
				JSONObject jsonObject = new JSONObject(strResult);
				if (!StringUtils.isEmpty(jsonObject)) {
					//{"data":{"content":"    您好!您的求职简历已经收到,我们会尽快进行阅评,之后再决定是否与您面谈!","sender":false,"message_id":"2990","date":"2014-01-17","type":2,"subject":"自动回复"},"status":1}
					JSONObject Object = jsonObject.getJSONObject("data");
					message=new Message();
					message.setType(Object.optInt("type"));
					message.setDate(Object.getString("date"));				
					message.setSender(Object.optString("sender"));
					message.setSubject(Object.getString("subject"));
					message.setContent(Object.getString("content"));
					return message;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return message;

	}

}
