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
package zjdf.zhaogongzuo.entity;

import java.io.Serializable;

/**
 *<h2> 城市实体</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月21日
 * @version 
 * @modify 
 * 
 */
public class City implements Serializable {

	/**字段*/
	private static final long serialVersionUID = -4208350026639572916L;

	/**城市id*/
	private int id;
	/**城市id字符串类型*/
	private String idStr;
	/**城市名称*/
	private String name;
	/**省会id*/
	private int parentId;
	/**省会id String类型*/
	private String parentIdStr;
	/**parent name*/
	private String parentName;
	/**
	 * @param 
	 * @param id 城市id
	 * @param idStr
	 * @param name 城市名
	 * @param parentId 省id
	 * @param parentIdStr 
	 * @param parentName 省名称
	 */
	public City(int id, String idStr, String name, int parentId,
			String parentIdStr, String parentName) {
		super();
		this.id = id;
		this.idStr = idStr;
		this.name = name;
		this.parentId = parentId;
		this.parentIdStr = parentIdStr;
		this.parentName = parentName;
	}
	public City() {
		
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
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the parentIdStr
	 */
	public String getParentIdStr() {
		return parentIdStr;
	}
	/**
	 * @param parentIdStr the parentIdStr to set
	 */
	public void setParentIdStr(String parentIdStr) {
		this.parentIdStr = parentIdStr;
	}
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "City [id=" + id + ", idStr=" + idStr + ", name=" + name
				+ ", parentId=" + parentId + ", parentIdStr=" + parentIdStr
				+ ", parentName=" + parentName + "]";
	}
	
	
	
}
