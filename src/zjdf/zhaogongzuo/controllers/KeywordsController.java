/**
 * Copyright © 2014年4月4日 FindJob www.veryeast.com
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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zjdf.zhaogongzuo.entity.Keywords;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;

/**
 *<h2> KeywordsController</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月4日
 * @version 
 * @modify 
 * 
 */
public class KeywordsController extends BaseConttroller {

	/**
	 * @param 
	 */
	public KeywordsController(Context context) {
		super(context);
	}
	
	/**
	 * 获取关键字
	 *<pre>方法  </pre>
	 * @param apikey   
	 * @param keyword
	 * @param scope  关键词匹配范围，默认为1。1为全文匹配，2为职位明匹配，3为企业名匹配。
	 * @return
	 */
	public List<Keywords> getKeywords(String apikey,String keyword,int scope) {
		
		List<Keywords> keywords=null;
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("apikey", apikey));
		nv.add(new BasicNameValuePair("keyword", keyword));
		nv.add(new BasicNameValuePair("scope", scope+""));
		String strResult = HttpTools.getNetString_post(nv, NET_HOST+"job/associated_keyword",1000,3000);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)&&jObject.optInt("status")==1) {
					JSONArray array=jObject.getJSONArray("data");
					if (!StringUtils.isEmpty(array)) {
						int nn=array.length();
						keywords=new ArrayList<Keywords>(nn);
						Keywords k=null;
						for (int i = 0; i < nn; i++) {
							k=new Keywords();
							k.setKeywords(array.getJSONObject(i).getString("text"));
							k.setCount(array.getJSONObject(i).optInt("counts"));
							keywords.add(k);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		return keywords;
	}
	
}
