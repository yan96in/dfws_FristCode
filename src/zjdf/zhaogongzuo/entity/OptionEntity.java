/**
 * Copyright © 2014年5月8日 FindJob www.veryeast.com
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
 * @author Eilin.Yang  VearyEast 
 * @since 2014年5月8日
 * @version v1.0.0
 * @modify 
 */
public final class OptionEntity {

	/**编码*/
	public String code;
	/**值*/
	public String value;
	/**父类编码*/
	public String pcode;
	/**是否有子集*/
	public int hassub;
	/**子集*/
	public List<OptionEntity> subEntities;
	/**
	 * @param code 编码
	 * @param value 值
	 * @param pcode 父类编码
	 * @param hassub 是否有子集，1：有，0：没有
	 * @param subEntities 子集
	 */
	public OptionEntity(String code, String value, String pcode, int hassub,
			List<OptionEntity> subEntities) {
		super();
		this.code = code;
		this.value = value;
		this.pcode = pcode;
		this.hassub = hassub;
		this.subEntities = subEntities;
	}
	/**
	 * 
	 */
	public OptionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
