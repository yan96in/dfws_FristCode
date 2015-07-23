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
package zjdf.zhaogongzuo.entity;

/**
 * 教育经历  对象
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月7日
 * @version v1.0.0
 * @modify 
 */
public final class ResumeEducation {

	/**教育经历id*/
	public String id;
	/**用户id*/
	public String userid;
	
	/**学历key*/
	public String edu_degree_key;
	/**学历value*/
	public String edu_degree_value;
	
	/**开始时间-年*/
	public String start_time_year;
	/**开始时间-月*/
	public String start_time_month;
	
	/**结束时间-年*/
	public String end_time_year;
	/**结束时间-月*/
	public String end_time_month;
	
	/**学校名称key*/
	public String school_key;
	/**学校名称value*/
	public String school_value;
	
	/**所在城市key*/
	public String area_key;
	/**所在城市value*/
	public String area_value;
	
	/**专业类别key*/
	public String major_key;
	/**专业类别value*/
	public String major_value;
	
	/**描述*/
	public String describe;
	
	
	/**海外学习经历key*/
	public String study_abroad_key;
	/**海外学习经历value*/
	public String study_abroad_value;
}
