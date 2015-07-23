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
 * 微简历
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月7日
 * @version v1.0.0
 * @modify 
 */
public final class MicroResume {

	/**用户id*/
	public String user_id;
	/**电话号码*/
	public String mobile;//: "18888976570"
	/**修改时间*/
	public String modify_time;//: "2014-05-07 18:17:14"
	/**真实姓名*/	
	public String true_name_cn;//: "吴海东"
	/**生日*/
	public String birthday;//: "0000-00-00"
	/**性别key*/	
	public String gender_key;//: "2"
	/**性别value*/	
	public String gender_value;//: "2"
	/**身高*/
	public String height;//: "0"
	/**学历key*/
	public String degree_key;//: "0"
	/**学历value*/
	public String degree_value;//: "0"
	/**意向地点key*/
	public String person_desired_location_key;
	/**意向地点value*/
	public String person_desired_location_value;
	/**意向职位key*/
	public String person_desired_position_key;
	/**意向职位value*/
	public String person_desired_position_value;
	/**意向薪资key*/
	public String desired_salary_key;//: "0"
	/**意向薪资value*/
	public String desired_salary_value;//: "0"
}
