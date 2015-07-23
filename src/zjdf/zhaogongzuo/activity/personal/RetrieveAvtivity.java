package zjdf.zhaogongzuo.activity.personal;

import com.umeng.analytics.MobclickAgent;

import zjdf.zhaogongzuo.R;
import android.app.Activity;
import android.os.Bundle;

/**
 *    找回密码
 * @author Administrator
 *
 */
public class RetrieveAvtivity  extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_personal_retrieve_password);
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
}
