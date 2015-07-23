package zjdf.zhaogongzuo.activity.personal;

import org.json.JSONObject;

import zjdf.zhaogongzuo.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tencent.mm.sdk.platformtools.Log;
import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 微博 账号绑定
 * 
 * @author Administrator
 * 
 */
public class AccountbindingSinaActivity extends Activity {
	private Context context;
	private WebView webView;
	private WebSettings settings;
	private String data = "";// 返回数据
	private ApplicationConfig applicationConfig;// 全局控制
	private PersonalController personalControllers;// 控制器
	private String connect_cooperate;//
	private String connect_code;//
	private String urls = "http://sso.veryeast.cn/user/login?";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.layout_personal_accountbinding);
		setContentView(R.layout.layout_personal_sina);

		context = this;
		webView = (WebView) findViewById(R.id.webview);
		settings = webView.getSettings();
		applicationConfig = (ApplicationConfig) context.getApplicationContext();
		personalControllers = new PersonalController(context);
		initWebView();

	}

	/**
	 * 
	 * <pre>
	 * 初始化webview
	 * </pre>
	 */
	private void initWebView() {
		settings.setJavaScriptEnabled(true);
		settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webView.loadUrl("http://sso.veryeast.cn/connect/link/weibo?return_type=json&unset_cookie=1");
		webView.setWebViewClient(new WebViewClient() {
			// data{"flag":"7201","connect_cooperate":"weibo","connect_code":"DSZshue24e03fc1aC9LEmNC5GVwu00.2"}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,
					final String url) {
				Log.i("TEST", "shouldOverrideUrlLoading---------url=" + url);
				webView.loadUrl(url);
				return true;
			}
			@SuppressLint("NewApi")
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view,
					String url) {
				Log.i("TEST", "shouldInterceptRequest---------url=" + url);
				if (url.contains("http://sso.veryeast.cn/user/login?")) {
					data = HttpTools.getNetString_get(url);
					check();
					webView.loadUrl("file:///android_asset/loading.html");
					handler.sendEmptyMessage(4);

				}
				return null;
			}
		});
	}

	// 检查是否已经绑定
	private void check() {
		if (!StringUtils.isEmpty(data)) {
			try {
				JSONObject jObject = new JSONObject(data);
				if (!StringUtils.isEmpty(jObject)) {
					if (jObject.has("connect_cooperate")) {
						connect_cooperate = jObject.getString("connect_cooperate");
						connect_code = jObject.getString("connect_code");
						handler.sendEmptyMessage(3);
					} else {
						applicationConfig.user.setId(jObject.getJSONObject("data").optString("userid"));
						applicationConfig.user.setName(jObject.getJSONObject("data").getString("username"));
						String user_ticket = jObject.getJSONObject("data").getString("ticket");
						applicationConfig.user_ticket = user_ticket;
						handler.sendEmptyMessage(1);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return;
			}
		}
	}

	// 刷新界面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(context, "登录成功", Toast.LENGTH_LONG).show();
				finish();
				break;
			case 2:
				Toast.makeText(context, "登录失败", Toast.LENGTH_LONG).show();
				break;
			case 3:
				Intent intent = new Intent(context,AccountbindingActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("connect_cooperate", connect_cooperate);
				bundle.putString("connect_code", connect_code);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
				break;
			case 4:
				finish();
				break;
			}
		};
	};
	
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
}
