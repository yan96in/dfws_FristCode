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
 * 培训经历
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月7日
 * @version v1.0.0
 * @modify 
 */
public final class ResumeTrains {

	/**用户id*/
	public String userid;
	
	/**培训时间key*/
	public String time_key;
	/**培训时间value*/
	public String time_value;
	
	/**培训课程key*/
	public String course_key;
	/**培训课程value*/
	public String course_value;
	
	/**培训机构*/
	public String organization;
	
	/**所在城市key*/
	public String area_key;
	/**所在城市value*/
	public String area_value;
	
	/**获得证书*/
	public String certificate;
	/**描述*/
	public String describe;
}
