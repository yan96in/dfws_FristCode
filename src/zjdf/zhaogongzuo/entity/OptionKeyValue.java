/**
 * Copyright © 2014年4月23日 FindJob www.veryeast.com
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
 *<h2> OptionKeyValue</h2>
 *<pre> 选项的对象 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月23日
 * @version 
 * @modify 
 * 
 */
public class OptionKeyValue {
	/**key*/
	public String key;
	/**value*/
	public Integer value;
	/**
	 * @param 
	 * @param key
	 * @param value
	 */
	public OptionKeyValue(String key, Integer value) {
		super();
		this.key = key;
		this.value = value;
	}
	/**
	 * @param 
	 */
	public OptionKeyValue() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
