/**
 * Copyright © 2014年4月3日 FindJob www.veryeast.com
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
package zjdf.zhaogongzuo.databases.properties;

/**
 *<h2>搜索关键字</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月3日
 * @version
 * @modify 
 * 
 */
public class SearchKeywordsProperty {

	/**用户数据库*/
	public static final String DB_NAME="searchs.db";
	/**表名称*/
	public static final String TABLE="keywords_table";
	/**行ID*/
	public static final String RAW_ID="raw_id";
	/**用户ID*/
	public static final String ID="user_id";
	/**用户名*/
	public static final String NAME="user_name";
	/**关键字*/
	public static final String KEYWORDS="keywords";
	/**关键字搜索次数*/
	public static final String FREQUENCY="frequency";
	/**记录更新时间*/
	public static final String UPDATE_TIME="update_time";
	/**创建表格*/
	public static final String CREATE_TABLE="create table if not exists "
			+TABLE+"("
			+RAW_ID+" integer primary key autoincrement, "
			+ID+" text, "
			+NAME+" text, "
			+KEYWORDS+" text, "
			+FREQUENCY+" integer, "
			+UPDATE_TIME+" text )";
	/**查询指定用户id的搜索记录*/
	public static final String RAW_QUERY="select "+KEYWORDS+" from "+TABLE+" where "+ID+" = ? and "+KEYWORDS+" like '%?%' order by "+FREQUENCY+" DESC limit 0,10";
	/**查询指定所有用户搜索记录*/
	public static final String RAW_QUERY_NONE_USER="select "+KEYWORDS+" from "+TABLE+" where "+KEYWORDS+" like '%?%' order by "+FREQUENCY+" DESC limit 0,10";
}
