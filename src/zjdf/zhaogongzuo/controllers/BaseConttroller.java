/**
 * Copyright © 2014年5月7日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.controllers;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.configures.FrameConfigures;
import android.content.Context;

/**
 * 所有控制器的父类
 * 
 * @author Eilin.Yang VearyEast
 * @since 2014年5月7日
 * @version v1.0.0
 * @modify
 */
public abstract class BaseConttroller {

	/**
	 * 访问域
	 */
	protected String NET_HOST;

	protected Context mContext;

	/**
	 * 全局
	 */
	protected ApplicationConfig mApplication;

	public BaseConttroller(Context context) {
		mContext = context;
		mApplication = (ApplicationConfig) context.getApplicationContext();
		NET_HOST = FrameConfigures.HOST;
	}

}
