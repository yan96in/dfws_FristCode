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
package zjdf.zhaogongzuo.databases.properties;

/**
 *<h2> SearchHistoryProperty</h2>
 *<pre>搜索的历史记录 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月25日
 * @version 
 * @modify 
 * 
 */
public final class SearchHistoryProperty {

	/**用户数据库*/
	public static final String DB_NAME="search_history.db";
	/**表名称*/
	public static final String TABLE="historys";
	/**行ID*/
	public static final String RAW_ID="raw_id";
	/**用户ID*/
	public static final String ID="user_id";
	/**关键字*/
	public static final String KEYWORD="keyword";
	/**关键字范围*/
	public static final String SCOPE="scope";
	/**地址*/
	public static final String ADDRESS="address";
	/**地址编码*/
	public static final String ADDRESS_CODE="address_code";
	/**职位分类*/
	public static final String POSITIONCLASS="positionclass";
	/**职位分类编码*/
	public static final String POSITIONCLASS_CODE="positionclass_code";
	/**记录更新时间*/
	public static final String UPDATE_TIME="update_time";
	/**创建表格*/
	public static final String CREATE_TABLE="create table if not exists "
			+TABLE+"("
			+RAW_ID+" integer primary key autoincrement, "
			+ID+" text, "
			+KEYWORD+" text, "
			+SCOPE+" integer, "
			+ADDRESS+" text, "
			+ADDRESS_CODE+" text, "
			+POSITIONCLASS+" text, "
			+POSITIONCLASS_CODE+" text, "
			+UPDATE_TIME+" text )";
	/**查询按时间排列的前10条记录*/
	public static final String QUERY="select "+KEYWORD+","+SCOPE+","+ADDRESS_CODE+","+POSITIONCLASS_CODE+" from "+TABLE+" order by "+UPDATE_TIME+" DESC limit 0,10";
}
