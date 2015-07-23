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
package zjdf.zhaogongzuo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池代理
 * 
 * @author Eilin.Yang VearyEast
 * @since 2014年5月23日
 * @version v1.0.0
 * @modify
 */
public final class ThreadPoolProxy {

	private static ExecutorService mExecutorService = null;
	private ThreadPoolProxy() {

	}

	/**
	 * 添加任务
	 * 
	 * @param runnable
	 */
	public static void submit(Runnable runnable) {
		if (mExecutorService == null) {
			mExecutorService = Executors.newCachedThreadPool();
		}
		mExecutorService.submit(runnable);
	}

	/**
	 * 停止任务
	 */
	public static void stopTask(){
		if (mExecutorService!=null) {
			mExecutorService.shutdown();
			try{
			if(!mExecutorService.awaitTermination(60,TimeUnit.SECONDS)){
				mExecutorService.shutdownNow();
				if(!mExecutorService.awaitTermination(60,TimeUnit.SECONDS)){
					
				}
			}
			}catch(InterruptedException ie){
				mExecutorService.shutdownNow();Thread.currentThread().interrupt();
			}
		}
	}

}
