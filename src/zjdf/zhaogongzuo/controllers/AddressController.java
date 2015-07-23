/**
 * Copyright © 2014年3月21日 FindJob www.veryeast.com
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
import java.util.HashMap;
import java.util.List;

import zjdf.zhaogongzuo.entity.Areas;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * <h2>解析地址</h2>
 * 
 * <pre> </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年3月21日
 * @version
 * @modify
 * 
 */
public class AddressController extends AbsController{


	/**热门城市*/
	public static List<Areas> hotAreas;
	/**所有城市*/
	public static List<Areas> allAreas;
	/**所有地址map集合*/
	public static HashMap<String, Areas> mapAreas;
	/**本地地址*/
	public static Areas mArea;
	
	public AddressController(Context context) {
		super(context);
	}
	
	/**
	 * 解析地址信息
	 *<pre>方法  </pre>
	 * @param ishot 是否是热门城市。true:热门,false:非热门城市
	 * @return
	 */
	public static List<Areas> getAreas(boolean ishot) {
		if (ishot) {
			return hotAreas;
		}else {
			return allAreas;
		}
	}
	
	/**
	 * 热门城市
	 * 全国、北京、深圳、广州、海口、上海、杭州、成都、苏州、天津、武汉
	 *<pre>方法  </pre>
	 */
	public static void parseHotAreas(){
		if (!StringUtils.isEmpty(hot_areas)) {
			hotAreas =new ArrayList<Areas>();
			try {
				JSONArray array=JSON.parseArray(hot_areas);
				 hotAreas=parseAreasList(array,false,null);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 解析热门城市
	 *<pre>方法  </pre>
	 * @param array 
	 * @return
	 * @throws JSONException
	 */
	private static List<Areas> parseAreasList(JSONArray array,boolean isAll,String pcode) throws JSONException{
		List<Areas> lareas=null;
		if (null!=array&&!array.isEmpty()) {
			int nn=array.size();
			lareas=new ArrayList<Areas>(nn);
			Areas area=null;
			for (int i = 0; i < nn; i++) {
				JSONObject object=array.getJSONObject(i);
				area=new Areas();
				String code=object.getString("code");
				area.setCode(code);
				if (pcode!=null)
					area.setParentcode(pcode);
				area.setValue(object.getString("value"));
				area.setIshot(object.getIntValue("ishot"));
				int hassub=object.getIntValue("hassub");
				area.setHassub(hassub);
				if (hassub==1&&object.containsKey("sublist")) {
					JSONArray subArray=object.getJSONArray("sublist");
					List<Areas> subAreas=parseAreasList(subArray,isAll,code);
					area.setAreas(subAreas);
				}
				if (object.containsKey("sortkey")) {
					area.setSortkey(object.getString("sortkey"));
				}
				lareas.add(area);
				if (isAll) {
					mapAreas.put(code, area);
				}
			}
		}
		return lareas;
	}
	
	/**
	 * 解析出所有城市
	 *<pre>方法  </pre>
	 */
	public static void parseAllAreas(){
		if (jarray_areas!=null&&!jarray_areas.isEmpty()) {
			mapAreas=new HashMap<String, Areas>(jarray_areas.size());
			try {
				allAreas=parseAreasList(jarray_areas,true,null);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
