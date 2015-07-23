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
package zjdf.zhaogongzuo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import zjdf.zhaogongzuo.entity.PositionClassify;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 *<h2> PositionClassifyController</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月14日
 * @version 
 * @modify 
 * 
 */
public class PositionClassifyController extends AbsController{

	/**职位分类*/
	public static List<PositionClassify> positionClassifies;
	/**职位分类集合*/
	public static HashMap<String, PositionClassify> mapPositionClassifies;
	
	public PositionClassifyController(Context context){
		super(context);
	}
	
	/**
	 * 
	 *<pre>解析职位分类列表 </pre>
	 */
	public static void parsePositionClass(){
		if (!StringUtils.isEmpty(jarray_positions)) {
			mapPositionClassifies=new HashMap<String, PositionClassify>(jarray_positions.size());
			try {
				positionClassifies=parsePositionClassList(jarray_positions,null);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 *<pre>解析封装职位分类列表  </pre>
	 * @param array JsonArray
	 * @return
	 * @throws JSONException
	 */
	private static List<PositionClassify> parsePositionClassList(JSONArray array,String pcode) throws JSONException{
		List<PositionClassify> positionClasss=null;
		if (!StringUtils.isEmpty(array)) {
			int nn=array.size();
			PositionClassify positionClassify=null;
			positionClasss=new ArrayList<PositionClassify>(nn);
			for (int i = 0; i < nn; i++) {
				JSONObject jObject=array.getJSONObject(i);
				positionClassify=new PositionClassify();
				String code=jObject.getString("code");
				positionClassify.setCode(code);
				positionClassify.setPcode(pcode);
				positionClassify.setValue(jObject.getString("value"));
				boolean has=jObject.getIntValue("hassub")==1;
				positionClassify.setHasSub(has);
				if (has) {
					JSONArray subArray=jObject.getJSONArray("sublist");
					if (!StringUtils.isEmpty(subArray)) {
						positionClassify.setSubList(parsePositionClassList(subArray,code));
					}
				}
				positionClasss.add(positionClassify);
				mapPositionClassifies.put(code, positionClassify);
			}
		}
		return positionClasss;
	}
}
