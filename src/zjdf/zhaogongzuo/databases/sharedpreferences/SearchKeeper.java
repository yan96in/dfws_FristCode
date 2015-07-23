/**
 * Copyright © 2014-3-18 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.databases.sharedpreferences;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 
 *<h2> 搜索模块sharedPreferences数据存储</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月9日
 * @version 
 * @modify 
 *
 */
public class SearchKeeper {
	/**
	 * 主要数据
	 */
	private static final String SETS_PREFERENCES_NAME = "search_keeper";
	
	/**
	 *保存搜索关键字
	 * @param context 上下文环境
	 * @param  一个完整的用户
	 */
	public static void keepKeywords(Context context,String keyword) {
		if (!StringUtils.isEmpty(keyword)) {
			SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.putString("keywords", keyword);
			editor.commit();
		}
	}
	
	/**
	 * 清空sharePreference 的关键字信息
	 * @param context
	 */
	public static void clearKeyword(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("keywords");
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取关键字
	 * @param context
	 * @return 
	 */
	public static String readKeyword(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		String k=pref.getString("keywords", null);
		return k;
	}
}
