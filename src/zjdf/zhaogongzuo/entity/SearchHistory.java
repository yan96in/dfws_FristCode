/**
 * Copyright © 2014年4月25日 FindJob www.veryeast.com
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
 *<h2> SearchKeyValue</h2>
 *<pre>搜索记录实体 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月25日
 * @version 
 * @modify 
 * 
 */
public final class SearchHistory {

	/**用户id*/
	public String userid;
	/**关键字*/
	public String keyword;
	/**关键字搜索范围*/
	public int scope=1;
	/**地址*/
	public String address;
	/**地址编码*/
	public String address_code;
	/**职位分类*/
	public String positionclass;
	/**职位分类编码*/
	public String positionclass_code;
	/**
	 * @param 
	 */
	public SearchHistory() {
		
	}
	/**
	 * @param 
	 * @param userid 用户id
	 * @param keyword 关键字
	 * @param scope 关键字搜索范围
	 * @param address 地址
	 * @param address_code 地址编码
	 * @param positionclass 职位分类
	 * @param positionclass_code 职位分类编码
	 */
	public SearchHistory(String userid, String keyword, int scope,
			String address,String address_code, String positionclass,String positionclass_code) {
		this.userid = userid;
		this.keyword = keyword;
		this.scope = scope;
		this.address = address;
		this.address_code=address_code;
		this.positionclass = positionclass;
		this.positionclass_code=positionclass_code;
	}
	
	
}
