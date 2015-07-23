/**
 * Copyright © 2014年4月2日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.configures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zjdf.zhaogongzuo.entity.OptionCodeValue;
import zjdf.zhaogongzuo.entity.OptionEntity;
import zjdf.zhaogongzuo.entity.OptionKeyValue;

/**
 *<h2>应用配置文件</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月2日
 * @version
 * @modify
 * 
 */
public final class FrameConfigures {
	
//	/**host*/
//	public static final String HOST="http://168.192.122.29:8094/client/";
	/**host*/
	public static final String HOST="http://mobile.interface.veryeast.cn/";
	/**应用目录*/
	public static final String FOLDER = "/mnt/sdcard/veryeast/";
	/**应用头像目录*/
	public static final String FOLDER_PRA = FOLDER+"pra/";
	/**应用文件目录*/
	public static final String FOLDER_TEMP = FOLDER+"temp/";
	/**图片目录*/
	public static final String FOLDER_IMG = FOLDER+"images/";
	/**所有数据*/
	public static final String FOLDER_DATAS = FOLDER+"datas/";
	/**所有选项*/
	public static final String FOLDER_OPTIONS = FOLDER_DATAS+"baseoptions.veryeast";
	/**所有地点*/
	public static final String FOLDER_AREA = FOLDER_DATAS+"allareas.veryeast";
	/**所有热门地点*/
	public static final String FOLDER_HOT_AREA = FOLDER_DATAS+"hotarea.veryeast";
	/**图片目录*/
	public static final String FOLDER_YY = FOLDER+"yy/";
	/**图片目录*/
	public static final String FOLDER_ZZ = FOLDER+"zz/";
	public static final String MAIN_FOLDER = null;

	/**更新时间*/
	public static List<OptionKeyValue> list_updateTimes;
	/**更新时间*/
	public static HashMap<String,OptionCodeValue> map_updateTimes;
	
	
	/**工作经验*/
	public static List<OptionKeyValue> list_works;
	/**工作经验*/
	public static HashMap<String,OptionCodeValue> map_works;
	
	
	/**教育经验*/
	public static List<OptionKeyValue> list_educations;
	/**教育经验*/
	public static HashMap<String,OptionCodeValue> map_educations;
	
	/**薪资待遇——搜索使用*/
	public static List<OptionKeyValue> list_salary_search;
	
	/**薪资待遇*/
	public static List<OptionKeyValue> list_salary_month;
	/**薪资待遇*/
	public static HashMap<String,OptionCodeValue> map_salary_month;
	
	/**薪资待遇*/
	public static List<OptionKeyValue> list_salary_year;
	/**薪资待遇*/
	public static HashMap<String,OptionCodeValue> map_salary_year;
	
	/**薪资待遇方式*/
	public static List<OptionKeyValue> list_salary_mode;
	/**薪资待遇方式*/
	public static HashMap<String,OptionCodeValue> map_salary_mode;
	
	/**薪资待遇货币种类*/
	public static List<OptionKeyValue> list_salary_currency;
	/**薪资待遇货币种类*/
	public static HashMap<String,OptionCodeValue> map_salary_currency;
	
	/**是否包食宿*/
	public static List<OptionKeyValue> list_room_board;
	/**是否包食宿*/
	public static HashMap<String,OptionCodeValue> map_room_board;
	
	
	/**工作性质*/
	public static List<OptionKeyValue> list_work_mode;
	/**工作性质*/
	public static HashMap<String,OptionCodeValue> map_work_mode;
	
	
	/**证件类型*/
	public static List<OptionKeyValue> list_id_type;
	/**证件类型*/
	public static HashMap<String,OptionCodeValue> map_id_type;
	
	
	/**所属行业*/
	public static List<OptionEntity> list_industry;
	/**所属行业*/
	public static HashMap<String,OptionEntity> map_industry;
	
	
	/**星级*/
	public static List<OptionKeyValue> list_star_level;
	/**星级*/
	public static HashMap<String,OptionCodeValue> map_star_level;
	
		
	/**到岗时间*/
	public static List<OptionKeyValue> list_arrival_time;
	/**到岗时间*/
	public static HashMap<String,OptionCodeValue> map_arrival_time;
	
	
	/**主修专业*/
	public static List<OptionKeyValue> list_edu_major;
	/**主修专业*/
	public static HashMap<String,OptionCodeValue> map_edu_major;
	
	
	/**擅长语言*/
	public static List<OptionKeyValue> list_language;
	/**擅长语言*/
	public static HashMap<String,OptionCodeValue> map_language;
	
	
	/**语言掌握程度*/
	public static List<OptionKeyValue> list_master_degree;
	/**语言掌握程度*/
	public static HashMap<String,OptionCodeValue> map_master_degree;
	
	
	/**企业类型*/
	public static List<OptionKeyValue> list_company_type;
	/**企业类型*/
	public static HashMap<String,OptionCodeValue> map_company_type;
	
	/**企业所属行业*/
	public static List<OptionKeyValue> list_company_industry;
	/**企业所属行业*/
	public static HashMap<String,OptionCodeValue> map_company_industry;
	
	
	/**工作状态*/
	public static List<OptionKeyValue> list_job_status;
	/**工作状态*/
	public static HashMap<String,OptionCodeValue> map_job_status;
	
	
	/**简历公开程度*/
	public static List<OptionKeyValue> list_resume_status;
	/**简历公开程度*/
	public static HashMap<String,OptionCodeValue> map_resume_status;
	
	
	/**性别*/
	public static List<OptionKeyValue> list_gender;
	/**性别*/
	public static HashMap<String,OptionCodeValue> map_gender;
	
}
