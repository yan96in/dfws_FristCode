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
import java.util.List;

/**
 *<h2> 省实体</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年3月21日
 * @version 
 * @modify 
 * 
 */
public class Province implements Serializable {

	/**字段*/
	private static final long serialVersionUID = -520942829689141691L;
	/**省id*/
	private int id;
	/**省id string*/
	private String idStr;
	/**省名称*/
	private String name;
	/**城市列表*/
	private List<City> citys;
	
	public Province() {
		
	}
	
	/**
	 * @param 
	 * @param id
	 * @param idStr
	 * @param name
	 * @param citys
	 */
	public Province(int id, String idStr, String name, List<City> citys) {
	
		this.id = id;
		this.idStr = idStr;
		this.name = name;
		this.citys = citys;
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
	 * @return the citys
	 */
	public List<City> getCitys() {
		return citys;
	}
	/**
	 * @param citys the citys to set
	 */
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Province [id=" + id + ", idStr=" + idStr + ", name=" + name
				+ ", citys=" + citys + "]";
	}
	

}
