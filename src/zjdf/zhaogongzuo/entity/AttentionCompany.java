/**
 * Copyright © 2014年3月27日 FindJob www.veryeast.com
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

import java.io.Serializable;

/**
 *<h2> AttentionCompany</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月27日
 * @version 
 * @modify 
 * 
 */
public class AttentionCompany implements Serializable {

	/**字段*/
	private static final long serialVersionUID = -8482045551248606500L;

	/**关注的企业id*/
	private int id;
	/**关注的企业id string类型*/
	private String idStr;
	/**关注的企业名称*/
	private String name;
	/**用户id*/
	private String userid;
	/**用户名称*/
	private String username;
	/**关注日期*/
	private String datetime;
	/**关注状态*/
	private String attentionStatus;
	/**招聘岗位个数*/
	private int recruitNum;
	
	
	/**
	 * @param 
	 */
	public AttentionCompany() {
		
	}
	
	/**
	 * @param id 关注的企业id
	 * @param idStr 
	 * @param name 企业名称
	 * @param userid 用户id
	 * @param username 用户名称
	 * @param datetime 关注日期
	 * @param attentionStatus 关注状态
	 * @param recruitNum 招聘岗位数量
	 */
	public AttentionCompany(int id, String idStr, String name, String userid,
			String username, String datetime, String attentionStatus,
			int recruitNum) {
		this.id = id;
		this.idStr = idStr;
		this.name = name;
		this.userid = userid;
		this.username = username;
		this.datetime = datetime;
		this.attentionStatus = attentionStatus;
		this.recruitNum = recruitNum;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the idStr
	 */
	public String getIdStr() {
		return idStr;
	}
	/**
	 * @param idStr the idStr to set
	 */
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the datetime
	 */
	public String getDatetime() {
		return datetime;
	}
	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	/**
	 * @return the attentionStatus
	 */
	public String getAttentionStatus() {
		return attentionStatus;
	}
	/**
	 * @param attentionStatus the attentionStatus to set
	 */
	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}
	/**
	 * @return the recruitNum
	 */
	public int getRecruitNum() {
		return recruitNum;
	}
	/**
	 * @param recruitNum the recruitNum to set
	 */
	public void setRecruitNum(int recruitNum) {
		this.recruitNum = recruitNum;
	}
	
}
