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

import java.util.ArrayList;
import java.util.List;

import zjdf.zhaogongzuo.utils.StringUtils;

/**
 * 求职意向 对象
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月7日
 * @version v1.0.0
 * @modify 
 */
public final class ResumeJobIntention {

	/**用户id*/
	public String userid;
	/**意向id*/
	public String id;	
	/**意向企业*/
	public List<ResumeIntentionDesiredCompanyType> desiredCompanyTypes;
	
	/**求职意向信息*/
	public ResumeIntentionDesiredJob desiredJob;
	
	/**意向地址*/
	public List<ResumeIntentionDesiredLocation> desiredLocations;
	
	/**意向职位*/
	public List<ResumeIntentionDesiredPosition> desiredPositions;
	
	public ResumeJobIntention(){
		desiredCompanyTypes=new ArrayList<ResumeIntentionDesiredCompanyType>();
		desiredJob=new ResumeIntentionDesiredJob();
		desiredLocations=new ArrayList<ResumeIntentionDesiredLocation>();
		desiredPositions=new ArrayList<ResumeIntentionDesiredPosition>();
	}
	
	/**
	 * "[\n  
	 * {\n    \"industry\" : \"0100\",\n    \"company_type\" : \"0101\",\n    \"star\" : \"5\"\n  },\n  
	 * {\n    \"industry\" : \"0100\",\n    \"company_type\" : \"0103\",\n    \"star\" : \"5\"\n  }\n]";
	 * @return
	 */
	public String companyTypesToPostString() {
		if (desiredCompanyTypes==null||desiredCompanyTypes.size()==0) {
			return null;
		}
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		for (ResumeIntentionDesiredCompanyType item : desiredCompanyTypes) {
			if (item!=null) {
				sb.append(item.toPostString()+",");
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 检查意向企业是否为空
	 * @return
	 */
	public boolean checkIndustryEmpty(){
		if (desiredCompanyTypes==null||desiredCompanyTypes.size()==0) {
			return true;
		}
		for (ResumeIntentionDesiredCompanyType item : desiredCompanyTypes) {
			if (item!=null) {
				if (StringUtils.isEmpty(item.industry_code)||StringUtils.isEmpty(item.parent_industry_code)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 *"{\n  \"current_salary_mode\" : \"1\",\n  \"desired_salary_currency\" : \"3\",\n  \"arrival_time\" : \"1\",\n  \"desired_salary_mode\" : \"1\",\n  \"current_salary\" : \"1024\",\n  \"current_salary_is_show\" : true,\n  \"current_salary_currency\" : \"4\",\n  \"desired_salary\" : \"0\",\n  \"work_mode\" : \"4\",\n  \"desired_salary_is_show\" : true\n}";
	 * @return
	 */
	public String desiredJobsToPostString() {
		if (desiredJob==null) {
			return null;
		}
		return desiredJob.toPostString();
	}
	
	/**
	 * 意向地点是否为空
	 * @return
	 */
	public boolean checkLocationEmpty(){
		if (desiredLocations==null||desiredLocations.size()==0) {
			return true;
		}
		for (ResumeIntentionDesiredLocation item : desiredLocations) {
			if (item!=null) {
				if (StringUtils.isEmpty(item.location_code)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 *"[\n  \"010000\",\n  \"070100\",\n  \"240200\",\n  \"210100\"\n]"
	 * @return
	 */
	public String desiredLocationsToPostString() {
		if (desiredLocations==null||desiredLocations.size()==0) {
			return null;
		}
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		for (ResumeIntentionDesiredLocation item : desiredLocations) {
			if (item!=null) {
				sb.append(item.toPostString()+",");
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 意向职位是否为空
	 * @return
	 */
	public boolean checkPositionEmpty(){
		if (desiredPositions==null||desiredPositions.size()==0) {
			return true;
		}
		for (ResumeIntentionDesiredPosition item : desiredPositions) {
			if (item!=null) {
				if (StringUtils.isEmpty(item.position_code)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 *"[\n  \"1400\",\n  \"0400\",\n  \"0300\",\n  \"0500\"\n]"
	 * @return
	 */
	public String desiredPositionsToPostString() {
		if (desiredPositions==null||desiredPositions.size()==0) {
			return null;
		}
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		for (ResumeIntentionDesiredPosition item : desiredPositions) {
			if (item!=null) {
				sb.append(item.toPostString()+",");
			}
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 获取工作类型值
	 * @return
	 */
	public String getWorkModeValue(){
		if (desiredJob!=null) {
			return desiredJob.work_mode_value;
		}
		return null;
	}
	
	/**
	 * 获取目前薪资
	 * @return
	 */
	public String getCurrentSalary(){
		if (desiredJob!=null) {
			return desiredJob.current_salary;
		}
		return null;
	}
	
	/**
	 * 获取期望薪资
	 * @return
	 */
	public String getDsiredSalaryValue(){
		if (desiredJob!=null) {
			return desiredJob.desired_salary_value;
		}
		return null;
	}
	
	/**
	 * 获取到岗时间
	 * @return
	 */
	public String getArriveTimeValue(){
		if (desiredJob!=null) {
			return desiredJob.arrival_time_value;
		}
		return null;
	}
	
	/**
	 * 获取意向地点值
	 * @return
	 */
	public String getAreasValue(){
		if (desiredLocations!=null&&desiredLocations.size()>0) {
			StringBuffer sb=new StringBuffer();
			for (ResumeIntentionDesiredLocation item : desiredLocations) {
				if (item!=null) {
					sb.append(item.location_value+",");
				}
			}
			if (sb.length()>0) {
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 获取意向职位值
	 * @return
	 */
	public String getPositionsValue(){
		if (desiredPositions!=null&&desiredPositions.size()>0) {
			StringBuffer sb=new StringBuffer();
			for (ResumeIntentionDesiredPosition item : desiredPositions) {
				if (item!=null) {
					sb.append(item.position_value+",");
				}
			}
			if (sb.length()>0) {
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 获取意向企业所属行业
	 * @return
	 */
	public String getIndustryValue(){
		if (desiredCompanyTypes!=null&&desiredCompanyTypes.size()>0) {
			StringBuffer sb=new StringBuffer();
			for (ResumeIntentionDesiredCompanyType item : desiredCompanyTypes) {
				if (item!=null) {
					sb.append(item.industry_value+",");
				}
			}
			if (sb.length()>0) {
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 获取意向企业星级
	 * @return
	 */
	public String getStarValue(){
		if (desiredCompanyTypes!=null&&desiredCompanyTypes.size()>0) {
			return desiredCompanyTypes.get(0).star_value;
		}
		return null;
	}
	
	/**
	 * 获取意向企业星级
	 * @return
	 */
	public String getStarCode(){
		if (desiredCompanyTypes!=null&&desiredCompanyTypes.size()>0) {
			return desiredCompanyTypes.get(0).star_code;
		}
		return null;
	}
	
	/**
	 * 检查工作类型是否为空
	 * @return
	 */
	public boolean checkWorkModeEmpty() {
		if (desiredJob==null) {
			return true;
		}
		if (StringUtils.isEmpty(desiredJob.work_mode_code)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查目前薪资是否为空
	 * @return
	 */
	public boolean checkCurrentSalaryEmpty() {
		if (desiredJob==null) {
			return true;
		}
		if (StringUtils.isEmpty(desiredJob.current_salary)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查期望薪资是否为空
	 * @return
	 */
	public boolean checkdesiredSalaryEmpty() {
		if (desiredJob==null) {
			return true;
		}
		if (StringUtils.isEmpty(desiredJob.desired_salary_key)) {
			return true;
		}
		return false;
	}
}
