/**
 * Copyright © 2014年4月14日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.activity;

import zjdf.zhaogongzuo.MainActivity;
import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.R.anim;
import android.view.Gravity;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.controllers.PersonalController;
import zjdf.zhaogongzuo.entity.EorrerBean;
import zjdf.zhaogongzuo.entity.User;
import zjdf.zhaogongzuo.ui.CustomMessage;
import zjdf.zhaogongzuo.utils.FileAccess;
import zjdf.zhaogongzuo.utils.MD5Utils;
import zjdf.zhaogongzuo.utils.NetWorkUtils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.umeng.analytics.MobclickAgent;

/**
 * <h2>启动页面</h2>
 * 
 * <pre> </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年4月14日
 * @version
 * @modify
 * 
 */
public class SplashActivity extends BaseActivity {

	private PersonalController controller;
	private Context context;
	private EorrerBean eb;
	private Handler handler = new Handler() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			finish();
			overridePendingTransition(anim.slide_in_right, anim.slide_out_left);
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		context = this;
		controller = new PersonalController(context);
		FileAccess.MakeDir(FrameConfigures.FOLDER_PRA);
		FileAccess.MakeDir(FrameConfigures.FOLDER_TEMP);
		FileAccess.MakeDir(FrameConfigures.FOLDER_DATAS);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mApplication.initDatas(context);
				doLogin();
				handler.sendEmptyMessage(1);
			}
		}).start();
	}

	// 自动登录
	private void doLogin() {
		
		String yy = FileAccess.readJsonFromFile(FrameConfigures.FOLDER_YY,"yy.yy");
		String zz = FileAccess.readJsonFromFile(FrameConfigures.FOLDER_YY,"zz.zz");
		String kk = FileAccess.readJsonFromFile(FrameConfigures.FOLDER_YY, "kk.kk");
		if (StringUtils.isEmpty(yy) || StringUtils.isEmpty(yy)
				|| StringUtils.isEmpty(kk)) {
			return;
		}
		User user = new User();
		try {
			user.setName(MD5Utils.decryptDES(yy, "1234abcd"));
			user.setPassword(MD5Utils.decryptDES(zz, "abcd1234"));
			mApplication.user_ticket=MD5Utils.decryptDES(kk, "kkcd1234");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mApplication.setUser(user);
		if (NetWorkUtils.checkNetWork(context)) {
			if (!StringUtils.isEmpty(user.getName())) {
				eb=controller.checkLogin(user.getName(), user.getPassword());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
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
