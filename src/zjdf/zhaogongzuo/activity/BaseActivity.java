/**
 * Copyright © 2014年5月10日 FindJob www.veryeast.com
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;

/**
 * 基本父类Activity
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月10日
 * @version v1.0.0
 * @modify 
 */
public class BaseActivity extends Activity {

	protected static ExecutorService executorService;
	protected ApplicationConfig mApplication;
	/**请求数据成功*/
	public static final int REQUEST_OK=0x0061;
	/**请求数据失败*/
	public static final int REQUEST_FAIL=0x0062;
	/**保存数据成功*/
	public static final int SAVE_OK=0x0063;
	/**保存数据失败*/
	public static final int SAVE_FAIL=0x0064;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApplication=(ApplicationConfig)getApplication();
		executorService=Executors.newCachedThreadPool();
	}
}
