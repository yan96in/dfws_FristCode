/**
 * Copyright © 2014年4月4日 FindJob www.veryeast.com
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
 *<h2> GetKeywords</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月4日
 * @version 
 * @modify 
 * 
 */
public class GetKeywords {

	/**关键字ids*/
	private int id;
	/**关键字*/
	private String keywords;
	/**查询次数*/
	private int count;
	
	public GetKeywords(){
		
	}
	
	/**
	 * @param 
	 * @param id 关键字id
	 * @param keywords 关键字
	 * @param count 关键字
	 */
	public GetKeywords(int id,String keywords,int count){
		this.id=id;
		this.keywords=keywords;
		this.count=count;
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
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GetKeywords [id=" + id + ", keywords=" + keywords + ", count="
				+ count + "]";
	}
	
}
