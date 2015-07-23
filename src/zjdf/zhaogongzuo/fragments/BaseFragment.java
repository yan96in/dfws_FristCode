/**
 * Copyright © 2014年5月23日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.fragments;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import zjdf.zhaogongzuo.activity.ApplicationConfig;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * fragment 父类
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月23日
 * @version v1.0.0
 * @modify 
 */
public class BaseFragment extends Fragment {

	/**
	 * 主进程
	 */
	protected ApplicationConfig mApplication;
	/**
	 * 线程池
	 */
	protected ExecutorService mExecutorService;
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mApplication=(ApplicationConfig)getActivity().getApplication();
		mExecutorService=Executors.newCachedThreadPool();
	}
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopTask();
		super.onDestroy();
	}
	
	/**
	 * 停止任务
	 */
	private void stopTask(){
		if (mExecutorService!=null) {
			mExecutorService.shutdown();
			try{
			if(!mExecutorService.awaitTermination(60,TimeUnit.SECONDS)){
				mExecutorService.shutdownNow();
				if(!mExecutorService.awaitTermination(60,TimeUnit.SECONDS)){
					
				}
			}
			}catch(InterruptedException ie){
				mExecutorService.shutdownNow();
			}
		}
	}
}
