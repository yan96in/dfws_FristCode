/**
 * Copyright © 2014年4月24日 FindJob www.veryeast.com
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
import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.entity.OptionEntity;
import zjdf.zhaogongzuo.entity.OptionKeyValue;
import zjdf.zhaogongzuo.entity.OptionCodeValue;
import zjdf.zhaogongzuo.utils.StringUtils;

/**
 *<h2> OptionController</h2>
 *<pre>选项信息控制器  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月24日
 * @version 
 * @modify 
 * 
 */
public class OptionController extends AbsController {

	/**
	 * @param 
	 * @param context
	 */
	public OptionController(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 解析更新时间
	 *<pre>方法  </pre>
	 */
	public static void parseUpdateTime(){
		if (!StringUtils.isEmpty(jarray_update_time)) {
			try {
				FrameConfigures.map_updateTimes=new HashMap<String, OptionCodeValue>(jarray_update_time.size());
				FrameConfigures.list_updateTimes=parseKeyValue(jarray_update_time,3,FrameConfigures.map_updateTimes);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析工作经历
	 *<pre>方法  </pre>
	 */
	public static void parseWorks(){
		if (!StringUtils.isEmpty(jarray_work_years)) {
			try {
				FrameConfigures.map_works=new HashMap<String, OptionCodeValue>(jarray_work_years.size());
				FrameConfigures.list_works=parseKeyValue(jarray_work_years,4,FrameConfigures.map_works);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析学历
	 *<pre>方法  </pre>
	 */
	public static void parseEducations(){
		if (!StringUtils.isEmpty(jarray_education)) {
			try {
				FrameConfigures.map_educations=new HashMap<String, OptionCodeValue>(jarray_education.size());
				FrameConfigures.list_educations=parseKeyValue(jarray_education,5,FrameConfigures.map_educations);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析薪资要求
	 *<pre>方法  </pre>
	 */
	public static void parseSalary(){
		parseSalary_scope_month();
		parseSalary_scope_year();
		parseSalary_currency();
		parseSalary_mode();
		parseSalary_scope_search();
	}
	
	/**
	 * 解析薪资要求
	 *<pre>方法  </pre>
	 */
	private static void parseSalary_scope_search(){
		if (!StringUtils.isEmpty(jarray_salary_search)) {
			try {
				FrameConfigures.list_salary_search=parseKeyValueExtra(jarray_salary_search,122);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析薪资要求
	 *<pre>方法  </pre>
	 */
	private static void parseSalary_scope_month(){
		if (!StringUtils.isEmpty(jarray_salary_scope)) {
			try {
				FrameConfigures.map_salary_month=new HashMap<String, OptionCodeValue>();
				FrameConfigures.list_salary_month=parseKeyValueExtraMoon(jarray_salary_scope,FrameConfigures.map_salary_month);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析薪资要求
	 *<pre>方法  </pre>
	 */
	private static void parseSalary_scope_year(){
		if (!StringUtils.isEmpty(jarray_salary_scope)) {
			try {
				FrameConfigures.map_salary_year=new HashMap<String, OptionCodeValue>();
				FrameConfigures.list_salary_year=parseKeyValueExtraYear(jarray_salary_scope,FrameConfigures.map_salary_year);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析薪资要求--货币种类
	 *<pre>方法  </pre>
	 */
	private static void parseSalary_currency(){
		if (!StringUtils.isEmpty(jarray_salary_currency)) {
			try {
				FrameConfigures.map_salary_currency=new HashMap<String, OptionCodeValue>();
				FrameConfigures.list_salary_currency=parseKeyValue(jarray_salary_currency,6,FrameConfigures.map_salary_currency);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析薪资要求--薪资方式
	 *<pre>方法  </pre>
	 */
	private static void parseSalary_mode(){
		if (!StringUtils.isEmpty(jarray_salary_mode)) {
			try {
				FrameConfigures.map_salary_mode=new HashMap<String, OptionCodeValue>();
				FrameConfigures.list_salary_mode=parseKeyValue(jarray_salary_mode,6,FrameConfigures.map_salary_mode);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析食宿情况
	 *<pre>方法  </pre>
	 */
	public static void parseRoomBoard(){
		if (!StringUtils.isEmpty(jarray_room_board)) {
			try {
				FrameConfigures.map_room_board=new HashMap<String, OptionCodeValue>(jarray_room_board.size());
				FrameConfigures.list_room_board=parseKeyValue(jarray_room_board,7,FrameConfigures.map_room_board);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析工作性质
	 *<pre>方法  </pre>
	 */
	public static void parseWorkMode(){
		if (!StringUtils.isEmpty(jarray_work_mode)) {
			try {
				FrameConfigures.map_work_mode=new HashMap<String, OptionCodeValue>(jarray_work_mode.size());
				FrameConfigures.list_work_mode=parseKeyValue(jarray_work_mode,8,FrameConfigures.map_work_mode);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析证件类型
	 *<pre>方法  </pre>
	 */
	public static void parseIDType(){
		if (!StringUtils.isEmpty(jarray_id_type)) {
			try {
				FrameConfigures.map_id_type=new HashMap<String, OptionCodeValue>(jarray_id_type.size());
				FrameConfigures.list_id_type=parseKeyValue(jarray_id_type,9,FrameConfigures.map_id_type);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析所属行业
	 *<pre>方法  </pre>
	 */
	public static void parseIndustry(){
		if (!StringUtils.isEmpty(jarray_industry)) {
			try {
				FrameConfigures.map_industry=new HashMap<String, OptionEntity>(jarray_industry.size());
				FrameConfigures.list_industry=parseOptionEntity(jarray_industry,null,FrameConfigures.map_industry);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析星级水平
	 *<pre>方法  </pre>
	 */
	public static void parseStarLevel(){
		if (!StringUtils.isEmpty(jarray_star_level)) {
			try {
				FrameConfigures.map_star_level=new HashMap<String, OptionCodeValue>(jarray_star_level.size());
				FrameConfigures.list_star_level=parseKeyValue(jarray_star_level,11,FrameConfigures.map_star_level);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析到岗时间
	 *<pre>方法  </pre>
	 */
	public static void parseArrivalTime(){
		if (!StringUtils.isEmpty(jarray_arrival_time)) {
			try {
				FrameConfigures.map_arrival_time=new HashMap<String, OptionCodeValue>(jarray_arrival_time.size());
				FrameConfigures.list_arrival_time=parseKeyValue(jarray_arrival_time,12,FrameConfigures.map_arrival_time);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析主修专业
	 *<pre>方法  </pre>
	 */
	public static void parseEduMajor(){
		if (!StringUtils.isEmpty(jarray_edu_major)) {
			try {
				FrameConfigures.map_edu_major=new HashMap<String, OptionCodeValue>(jarray_edu_major.size());
				FrameConfigures.list_edu_major=parseKeyValue(jarray_edu_major,13,FrameConfigures.map_edu_major);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析掌握语言
	 *<pre>方法  </pre>
	 */
	public static void parseLanguage(){
		if (!StringUtils.isEmpty(jarray_language)) {
			try {
				FrameConfigures.map_language=new HashMap<String, OptionCodeValue>(jarray_language.size());
				FrameConfigures.list_language=parseKeyValue(jarray_language,14,FrameConfigures.map_language);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析语言掌握程度
	 *<pre>方法  </pre>
	 */
	public static void parseMasterDegree(){
		if (!StringUtils.isEmpty(jarray_master_degree)) {
			try {
				FrameConfigures.map_master_degree=new HashMap<String, OptionCodeValue>(jarray_master_degree.size());
				FrameConfigures.list_master_degree=parseKeyValue(jarray_master_degree,15,FrameConfigures.map_master_degree);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析企业性质
	 *<pre>方法  </pre>
	 */
	public static void parseCompanyType(){
		if (!StringUtils.isEmpty(jarray_company_type)) {
			try {
				FrameConfigures.map_company_type=new HashMap<String, OptionCodeValue>(jarray_company_type.size());
				FrameConfigures.list_company_type=parseKeyValue(jarray_company_type,16,FrameConfigures.map_company_type);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		parseCompanyIndustry();
	}
	
	/**
	 * 解析企业性质
	 *<pre>方法  </pre>
	 */
	public static void parseCompanyIndustry(){
		if (!StringUtils.isEmpty(jarray_company_industry)) {
			try {
				FrameConfigures.map_company_industry=new HashMap<String, OptionCodeValue>(jarray_company_industry.size());
				FrameConfigures.list_company_industry=parseKeyValue(jarray_company_industry,20,FrameConfigures.map_company_industry);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析求职状态
	 *<pre>方法  </pre>
	 */
	public static void parseJobStatus(){
		if (!StringUtils.isEmpty(jarray_job_status)) {
			try {
				FrameConfigures.map_job_status=new HashMap<String, OptionCodeValue>(jarray_job_status.size());
				FrameConfigures.list_job_status=parseKeyValue(jarray_job_status,17,FrameConfigures.map_job_status);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析简历公开程度
	 *<pre>方法  </pre>
	 */
	public static void parseResumeStarus(){
		if (!StringUtils.isEmpty(jarray_resume_status)) {
			try {
				FrameConfigures.map_resume_status=new HashMap<String, OptionCodeValue>(jarray_resume_status.size());
				FrameConfigures.list_resume_status=parseKeyValue(jarray_resume_status,18,FrameConfigures.map_resume_status);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析性别
	 *<pre>方法  </pre>
	 */
	public static void parseGender(){
		if (!StringUtils.isEmpty(jarray_gender)) {
			try {
				FrameConfigures.map_gender=new HashMap<String, OptionCodeValue>(jarray_gender.size());
				FrameConfigures.list_gender=parseKeyValue(jarray_gender,19,FrameConfigures.map_gender);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * <pre>解析封装选项键值对  </pre>
	 * @param array json数组
	 * @param flag 标志
	 * @param maps 集合
	 * @return  List<OptionKeyValue>
	 * @throws JSONException
	 */
	private static List<OptionKeyValue> parseKeyValue(JSONArray array,int flag,HashMap<String, OptionCodeValue> maps) throws JSONException{
		List<OptionKeyValue> keyValues=null;
		if (!StringUtils.isEmpty(array)) {
			int nn=array.size();
			OptionKeyValue keyValue=null;
			OptionCodeValue codeValue=null;
			keyValues=new ArrayList<OptionKeyValue>(nn);
			for (int i = 0; i < nn; i++) {
				JSONObject jObject=array.getJSONObject(i);
				String value=jObject.getString("value");
				int key=jObject.getIntValue("code");
				keyValue=new OptionKeyValue(value,key);
				keyValues.add(keyValue);
				codeValue=new OptionCodeValue(key+"", value, null);
				maps.put(key+"", codeValue);
			}
		}
		return keyValues;
	}
	
	/**
	 * <pre>解析封装选项键值对  </pre>
	 * @param array json数组
	 * @param flag 标志
	 * @param maps 集合
	 * @return  List<OptionKeyValue>
	 * @throws JSONException
	 */
	private static List<OptionKeyValue> parseKeyValueExtra(JSONArray array,int flag) throws JSONException{
		List<OptionKeyValue> keyValues=null;
		if (!StringUtils.isEmpty(array)) {
			int nn=array.size();
			int j=0;
			if (flag==6) {
				nn=12;
			}
			if (flag==66) {
				j=12;
			}
			OptionKeyValue keyValue=null;
			keyValues=new ArrayList<OptionKeyValue>(nn);
			for (int i = j; i < nn; i++) {
				JSONObject jObject=array.getJSONObject(i);
				String value=jObject.getString("value");
				if (!StringUtils.isEmpty(value)&&value.contains("10001-1000000")) {
					value="10000以上";
				}
				int key=jObject.getIntValue("code");
				keyValue=new OptionKeyValue(value,key);
				keyValues.add(keyValue);
			}
		}
		return keyValues;
	}
	
	/**
	 * <pre>解析封装选项键值对  </pre>
	 * @param array json数组
	 * @param flag 标志
	 * @param maps 集合
	 * @return  List<OptionKeyValue>
	 * @throws JSONException
	 */
	private static List<OptionKeyValue> parseKeyValueExtra(JSONArray array,int flag,HashMap<String, OptionCodeValue> maps) throws JSONException{
		List<OptionKeyValue> keyValues=null;
		if (!StringUtils.isEmpty(array)) {
			int nn=array.size();
			int j=0;
			if (flag==6) {
				nn=12;
			}
			if (flag==66) {
				j=12;
			}
			OptionKeyValue keyValue=null;
			OptionCodeValue codeValue=null;
			keyValues=new ArrayList<OptionKeyValue>(nn);
			for (int i = j; i < nn; i++) {
				JSONObject jObject=array.getJSONObject(i);
				String value=jObject.getString("value");
				int key=jObject.getIntValue("code");
				keyValue=new OptionKeyValue(value,key);
				keyValues.add(keyValue);
				codeValue=new OptionCodeValue(key+"", value, null);
				maps.put(key+"", codeValue);
			}
		}
		return keyValues;
	}
	
	/**
	 * <pre>解析封装选项键值对  </pre>
	 * @param array json数组
	 * @param flag 标志
	 * @param maps 集合
	 * @return  List<OptionKeyValue>
	 * @throws JSONException
	 */
	private static List<OptionKeyValue> parseKeyValueExtraMoon(JSONArray array,HashMap<String, OptionCodeValue> maps) throws JSONException{
		List<OptionKeyValue> keyValues=null;
		if (!StringUtils.isEmpty(array)) {
			int nn=array.size();
			OptionKeyValue keyValue=null;
			OptionCodeValue codeValue=null;
			keyValues=new ArrayList<OptionKeyValue>();
			for (int i = 0; i < nn; i++) {
				JSONObject jObject=array.getJSONObject(i);
				int mode=jObject.getIntValue("mode");
				if (mode==1) {
					String value=jObject.getString("value");
					if (!StringUtils.isEmpty(value)&&value.contains("50000-1000000")) {
						value="50000以上";
					}
					int key=jObject.getIntValue("code");
					keyValue=new OptionKeyValue(value,key);
					keyValues.add(keyValue);
					codeValue=new OptionCodeValue(key+"", value, null);
					maps.put(key+"", codeValue);
				}
			}
		}
		return keyValues;
	}
	
	/**
	 * <pre>解析封装选项键值对  </pre>
	 * @param array json数组
	 * @param flag 标志
	 * @param maps 集合
	 * @return  List<OptionKeyValue>
	 * @throws JSONException
	 */
	private static List<OptionKeyValue> parseKeyValueExtraYear(JSONArray array,HashMap<String, OptionCodeValue> maps) throws JSONException{
		List<OptionKeyValue> keyValues=null;
		if (!StringUtils.isEmpty(array)) {
			int nn=array.size();
			OptionKeyValue keyValue=null;
			OptionCodeValue codeValue=null;
			keyValues=new ArrayList<OptionKeyValue>();
			for (int i = 0; i < nn; i++) {
				JSONObject jObject=array.getJSONObject(i);
				int mode=jObject.getIntValue("mode");
				if (mode==2) {
					String value=jObject.getString("value");
					int key=jObject.getIntValue("code");
					keyValue=new OptionKeyValue(value,key);
					keyValues.add(keyValue);
					codeValue=new OptionCodeValue(key+"", value, null);
					maps.put(key+"", codeValue);
				}
			}
		}
		return keyValues;
	}
	
	/**
	 * <pre>解析封装选项键值对  </pre>
	 * @param array json数组
	 * @param pcode 父类编码
	 * @param maps 集合
	 * @return List<OptionEntity>
	 * @throws JSONException
	 */
	private static List<OptionEntity> parseOptionEntity(JSONArray array,String pcode, HashMap<String, OptionEntity> maps) throws JSONException{
		List<OptionEntity> entities=null;
		if (!StringUtils.isEmpty(array)) {
			int nn=array.size();
			OptionEntity entity=null;
			entities=new ArrayList<OptionEntity>(nn);
			for (int i = 0; i < nn; i++) {
				JSONObject jObject=array.getJSONObject(i);
				String value=jObject.getString("value");
				String code=jObject.getString("code");
				int hassub=jObject.getIntValue("hassub");
				entity=new OptionEntity(code+"", value, pcode, hassub, null);
				if (hassub==1&&jObject.containsKey("sublist")) {
					JSONArray ja=jObject.getJSONArray("sublist");
					if (ja.size()>0) {
						entity.subEntities=parseOptionEntity(ja, code+"",maps);
					}
				}
//				entities.add(new OptionEntity(value, code+"", pcode, hassub, null));
				entities.add(entity);
				maps.put(code+"", entity);
			}
		}
		return entities;
	}
}
