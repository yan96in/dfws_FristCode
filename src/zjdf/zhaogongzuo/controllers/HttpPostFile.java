package zjdf.zhaogongzuo.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.BaseConttroller;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.util.Log;

public class HttpPostFile extends BaseConttroller {

	/**
	 * @param context
	 */
	public HttpPostFile(Context context) {
		super(context);

		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * 
	 * @param f
	 *            上传图片 文件
	 * @return
	 */

	public boolean postFile(File f) {
		if (f == null) {
			return false;
		}
		String url = FrameConfigures.HOST + "resume/upload_avatar";
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = null;
		try {
			MultipartEntity mpEntity = new MultipartEntity();
			long l = f.length();
			Log.i("tag", l + "");
			ContentBody cbFile = new FileBody(f, "image/png");
			ContentBody cbuserid = new StringBody(mApplication.user_ticket);
			mpEntity.addPart("avatar", cbFile);// 上传文件
			mpEntity.addPart("user_ticket", cbuserid);// 上传用户名
			httpPost.setEntity(mpEntity);
			response = client.execute(httpPost);
			if (response != null
					&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String json = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				JSONObject jObject = new JSONObject(json);
				if (!StringUtils.isEmpty(jObject)) {
					if (jObject.has("status")) {
						int value = jObject.getInt("status");
						if (value == 1) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}
}
