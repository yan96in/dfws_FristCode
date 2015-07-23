/**
 * Copyright © 2014年4月14日 FindJob www.veryeast.com
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

import java.util.List;

/**
 *<h2>职位分类实体</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月14日
 * @version 
 * @modify 
 * 
 */
public class PositionClassify {

	/**职位分类编码*/
	private String code;
	/**职位分类名称*/
	private String value;
	/**父类编码*/
	private String pcode;
	/**是否有下级分类*/
	private boolean hasSub;
	/**子类*/
	private List<PositionClassify> subList;
	/**
	 * @param 
	 */
	public PositionClassify() {
		
	}
	/**
	 * @param 
	 * @param code 职位分类编码
	 * @param value 职位分类名称
	 * @param hasSub 是否有子分类 true:有子分类；false:无子分类
	 * @param subList 子分类
	 */
	public PositionClassify(String code, String value, String pcode, boolean hasSub,
			List<PositionClassify> subList) {
		super();
		this.code = code;
		this.value = value;
		this.pcode=pcode;
		this.hasSub = hasSub;
		this.subList = subList;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the hasSub
	 */
	public boolean isHasSub() {
		return hasSub;
	}
	/**
	 * @param hasSub the hasSub to set
	 */
	public void setHasSub(boolean hasSub) {
		this.hasSub = hasSub;
	}
	/**
	 * @return the subList
	 */
	public List<PositionClassify> getSubList() {
		return subList;
	}
	/**
	 * @param subList the subList to set
	 */
	public void setSubList(List<PositionClassify> subList) {
		this.subList = subList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PositionClassify [code=" + code + ", value=" + value
				+ ", hasSub=" + hasSub + ", subList=" + subList + "]";
	}
	/**父类编码
	 * @return the pcode
	 */
	public String getPcode() {
		return pcode;
	}
	/**父类编码
	 * @param pcode the pcode to set
	 */
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}	
	
	
}
