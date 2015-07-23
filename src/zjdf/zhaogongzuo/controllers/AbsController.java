/**
 * Copyright © 2014年4月10日 FindJob www.veryeast.com
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;

import zjdf.zhaogongzuo.configures.FrameConfigures;
import zjdf.zhaogongzuo.databases.sharedpreferences.SetsKeeper;
import zjdf.zhaogongzuo.entity.Position;
import zjdf.zhaogongzuo.net.HttpTools;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * <h2>AbsController</h2>
 * 
 * <pre>
 * 选项解析的父类
 * </pre>
 * 
 * @author Eilin.Yang VeryEast
 * @since 2014年4月10日
 * @version
 * @modify
 * 
 */
public class AbsController extends BaseConttroller{

	/** 地址 */
	public static String areas = null;
	public static JSONArray jarray_areas = null;
	/** 热门地区 */
	public static String hot_areas = null;
	public static JSONArray jarray_hot_areas = null;
	/** 职位分类 */
	// public static String positions=null;
	public static JSONArray jarray_positions = null;
	/** 更新时间 */
	// public static String opts_update_time=null;
	public static JSONArray jarray_update_time = null;
	/** 工作经验 */
	// public static String opts_work_years=null;
	public static JSONArray jarray_work_years = null;
	/** 教育经历 */
	// public static String opts_education=null;
	public static JSONArray jarray_education = null;
	/**薪资大小_搜索*/
	public static JSONArray jarray_salary_search=null;
	/** 期望薪资大小 */
	// public static String opts_salary=null;
	public static JSONArray jarray_salary_scope = null;
	/** 期望薪资的币种 */
	public static JSONArray jarray_salary_currency = null;
	/** 期望薪资的形式 */
	public static JSONArray jarray_salary_mode = null;
	/** 是否提供食宿 */
	// public static String opts_room_board=null;
	public static JSONArray jarray_room_board = null;
	/** 工作性质 */
	// public static String opts_work_mode=null;
	public static JSONArray jarray_work_mode = null;
	/** 证件类型 */
	// public static String opts_id_type=null;
	public static JSONArray jarray_id_type = null;
	/** 所属行业 */
	// public static String opts_industry=null;
	public static JSONArray jarray_industry = null;
	/** 星级 */
	// public static String opts_star_level=null;
	public static JSONArray jarray_star_level = null;
	/** 到岗时间 */
	// public static String opts_arrival_time=null;
	public static JSONArray jarray_arrival_time = null;
	/** 专业 */
	// public static String opts_edu_major=null;
	public static JSONArray jarray_edu_major = null;
	/** 语言 */
	// public static String opts_language=null;
	public static JSONArray jarray_language = null;
	/** 语言掌握程度 */
	// public static String opts_master_degree=null;
	public static JSONArray jarray_master_degree = null;
	/** 公司性质 */
	// public static String opts_company_type=null;
	public static JSONArray jarray_company_type = null;
	/** 行业类型 */
	// public static String opts_company_type=null;
	public static JSONArray jarray_company_industry = null;
	/** 求职状态 */
	// public static String opts_job_status=null;
	public static JSONArray jarray_job_status = null;
	/** 简历状态 */
	// public static String opts_resume_status=null;
	public static JSONArray jarray_resume_status = null;
	/** 性别 */
	// public static String opts_gender=null;
	public static JSONArray jarray_gender = null;
	/** 毕业院校 */
	// public static String opts_schools=null;
	public static JSONArray jarray_schools = null;

	public AbsController(Context context) {
		super(context);
	}
	
	private static void checkOptionVersion(Context context){
		int mV=SetsKeeper.readOptionVersion(context);
		
	}
	
	
	/**
	 * 解析所有数据
	 * 
	 * <pre>
	 * 方法
	 * </pre>
	 * 
	 * @param context
	 */
	public static void parseDatas(Context context) {
		// String resut=FileAccess.readDatasFromAssets(context,
		// "configure1.json");
		// hot_areas=FileAccess.readDatasFromAssets(context, "hotarea1.json");
		String resut = "";
		try {
			File file=new File(FrameConfigures.FOLDER_OPTIONS);
			if (file.exists()) {
				resut=FileUtils.readFileToString(file, "UTF-8");
			}else {
				resut = IOUtils
						.toString(
								context.getResources().getAssets()
										.open("baseoptions.json"), "UTF-8");
			}
			areas = IOUtils.toString(
					context.getResources().getAssets().open("allareas.json"),
					"UTF-8");
			hot_areas = IOUtils.toString(context.getResources().getAssets()
					.open("hotarea.json"), "UTF-8");
			if (!StringUtils.isEmpty(areas)) {
				jarray_areas = JSON.parseArray(areas);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!StringUtils.isEmpty(resut)) {
			try {
				JSONObject jObject = JSON.parseObject(resut);
				if (jObject != null) {
					// JSONObject datas=jObject.getJSONObject("data");
					// if (datas.containsKey("areas")) {
					// // areas=datas.getString("areas");
					// jarray_areas=datas.getJSONArray("areas");
					// }
					if (jObject.containsKey("positions")) {
						// positions=datas.getString("positions");
						jarray_positions = jObject.getJSONArray("positions");
					}
					if (jObject.containsKey("opts_update_time")) {
						// opts_update_time=datas.getString("opts_update_time");
						jarray_update_time = jObject
								.getJSONArray("opts_update_time");
					}
					if (jObject.containsKey("opts_work_years")) {
						// opts_work_years=datas.getString("opts_work_years");
						jarray_work_years = jObject
								.getJSONArray("opts_work_years");
					}
					if (jObject.containsKey("opts_education")) {
						// opts_education=datas.getString("opts_education");
						jarray_education = jObject
								.getJSONArray("opts_education");
					}
					if (jObject.containsKey("opts_salary")) {
						// opts_salary=datas.getString("opts_salary");
						jarray_salary_scope = jObject.getJSONObject(
								"opts_salary").getJSONArray("salary_scope");
						jarray_salary_currency = jObject.getJSONObject(
								"opts_salary").getJSONArray("salary_currency");
						jarray_salary_mode = jObject.getJSONObject(
								"opts_salary").getJSONArray("salary_mode");
					}
					if (jObject.containsKey("opts_room_board")) {
						// opts_room_board=datas.getString("opts_room_board");
						jarray_room_board = jObject
								.getJSONArray("opts_room_board");
					}
					if (jObject.containsKey("opts_work_mode")) {
						// opts_work_mode=datas.getString("opts_work_mode");
						jarray_work_mode = jObject
								.getJSONArray("opts_work_mode");
					}
					if (jObject.containsKey("opts_id_type")) {
						// opts_id_type=datas.getString("opts_id_type");
						jarray_id_type = jObject.getJSONArray("opts_id_type");
					}
					if (jObject.containsKey("opts_industry")) {
						// opts_industry=datas.getString("opts_industry");
						jarray_industry = jObject.getJSONArray("opts_industry");
					}
					if (jObject.containsKey("opts_star_level")) {
						// opts_star_level=datas.getString("opts_star_level");
						jarray_star_level = jObject
								.getJSONArray("opts_star_level");
					}
					if (jObject.containsKey("opts_arrival_time")) {
						// opts_arrival_time=datas.getString("opts_arrival_time");
						jarray_arrival_time = jObject
								.getJSONArray("opts_arrival_time");
					}
					if (jObject.containsKey("opts_edu_major")) {
						// opts_edu_major=datas.getString("opts_edu_major");
						jarray_edu_major = jObject
								.getJSONArray("opts_edu_major");
					}
					if (jObject.containsKey("opts_language")) {
						// opts_language=datas.getString("opts_language");
						jarray_language = jObject.getJSONArray("opts_language");
					}
					if (jObject.containsKey("opts_master_degree")) {
						// opts_master_degree=datas.getString("opts_master_degree");
						jarray_master_degree = jObject
								.getJSONArray("opts_master_degree");
					}
					if (jObject.containsKey("opts_company_type")) {
						// opts_company_type=datas.getString("opts_company_type");
						jarray_company_type = jObject
								.getJSONArray("opts_company_type");
					}
					if (jObject.containsKey("opts_job_status")) {
						// opts_job_status=datas.getString("opts_job_status");
						jarray_job_status = jObject
								.getJSONArray("opts_job_status");
					}
					if (jObject.containsKey("opts_resume_status")) {
						// opts_resume_status=datas.getString("opts_resume_status");
						jarray_resume_status = jObject
								.getJSONArray("opts_resume_status");
					}
					if (jObject.containsKey("opts_gender")) {
						// opts_gender=datas.getString("opts_gender");
						jarray_gender = jObject.getJSONArray("opts_gender");
					}
					if (jObject.containsKey("opts_company_industry")) {
						// opts_schools=datas.getString("opts_company_industry");
						jarray_company_industry = jObject
								.getJSONArray("opts_company_industry");
					}
					if (jObject.containsKey("opts_search_salarys")) {
						jarray_salary_search = jObject
								.getJSONArray("opts_search_salarys");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
