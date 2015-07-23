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
package zjdf.zhaogongzuo.databases.handlers;

import java.util.ArrayList;
import java.util.List;

import zjdf.zhaogongzuo.databases.CommonDatabase;
import zjdf.zhaogongzuo.databases.properties.SearchKeywordsProperty;
import zjdf.zhaogongzuo.entity.Keywords;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *<h2>关键字数据库操作类</h2>
 *<pre>  </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月3日
 * @version 
 * @modify 
 * 
 */
public class SearchKeywordsHandler {

	/**上下文*/
	private Context context;
	/**数据库对象*/
	private SQLiteDatabase database=null;
	/**创建数据库*/
	private CommonDatabase databaseBox=null;
	
	public SearchKeywordsHandler(Context context) {
		this.context=context;
		databaseBox=new CommonDatabase(context, SearchKeywordsProperty.DB_NAME, new String[]{SearchKeywordsProperty.CREATE_TABLE},new String[]{SearchKeywordsProperty.TABLE});
		database=databaseBox.getWritableDatabase();
	}
	
	/**
	 * 
	 *<pre>关闭数据库 </pre>
	 */
	public void close() {
		// TODO Auto-generated method stub
		if (database.isOpen()) {
			database.close();
		}
	}
	
	/**
	 * 获取关键字列表
	 *<pre>方法  </pre>
	 * @param userid 用户id.可以为空
	 * @param keywords 搜索字段
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<Keywords> getKeywords(String userid,String keywords){
		ArrayList<Keywords> list=null;
		Cursor cursor=null;
		if (StringUtils.isEmpty(userid)) {
			cursor =database.rawQuery(SearchKeywordsProperty.RAW_QUERY_NONE_USER,new String[]{keywords});
		}else {
			cursor =database.rawQuery(SearchKeywordsProperty.RAW_QUERY,new String[]{userid,keywords});
		}
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				Keywords keyword=null;
				if (list==null) {
					list=new ArrayList<Keywords>();
				}else
					list.clear();
				do{
					keyword=new Keywords();
					keyword.setKeywords(cursor.getString(0));
					list.add(keyword);
				}while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}
	
	/**
	 * 获取指定条数的关键字
	 *<pre>方法  </pre>
	 * @param num 指定数量
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<Keywords> getKeywords(int num){
		if (num<1) {
			num=1;
		}
		ArrayList<Keywords> list=null;
		Cursor cursor=database.query(SearchKeywordsProperty.TABLE, new String[]{SearchKeywordsProperty.KEYWORDS,SearchKeywordsProperty.FREQUENCY}, null, null, null, null, SearchKeywordsProperty.FREQUENCY+" desc", "0,"+num);		
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				Keywords keyword=null;
				if (list==null) {
					list=new ArrayList<Keywords>();
				}else
					list.clear();
				do{
					keyword=new Keywords();
					keyword.setKeywords(cursor.getString(0));
					keyword.setCount(cursor.getInt(1));
					list.add(keyword);
				}while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}
	
	/**
	 * 获取单个关键字搜索次数
	 *<pre>方法  </pre>
	 * @param userid 用户id.可以为空
	 * @param keywords 搜索字段
	 * @return
	 */
	@SuppressWarnings("unused")
	public int getKeywordSearchCount(String keyword){
		int count = 0;
		Cursor cursor =database.query(SearchKeywordsProperty.TABLE, new String[]{SearchKeywordsProperty.FREQUENCY}, SearchKeywordsProperty.KEYWORDS + " = ?", new String[]{keyword}, null, null, null);
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				do{
					count=cursor.getInt(0);
				}while (cursor.moveToNext());
			}
		}
		cursor.close();
		return count;
	}
	
	/**
	 * 搜索记录保存
	 *<pre>方法  </pre>
	 * @param userid 用户id可以为空
	 * @param username 用户名 。可以为空
	 * @param keyword 关键字 .可以为空
	 * @param frequency 关键字搜索次数
	 * @param updatetime 记录更新时间
	 * @return
	 */
	public long insertKeyword(String userid,String username,String keyword,String updatetime){
		ContentValues values=new ContentValues();
		values.put(SearchKeywordsProperty.ID, userid);
		values.put(SearchKeywordsProperty.NAME, username);
		values.put(SearchKeywordsProperty.KEYWORDS, keyword);
		values.put(SearchKeywordsProperty.FREQUENCY, 1);
		values.put(SearchKeywordsProperty.UPDATE_TIME, updatetime);
		return database.insert(SearchKeywordsProperty.TABLE, null, values);
	}
	
	/**
	 * 搜索记录查询次数更新
	 *<pre>方法  </pre>
	 * @param userid 用户id可以为空
	 * @param username 用户名 。可以为空
	 * @param keyword 关键字 .可以为空
	 * @param frequency 关键字搜索次数
	 * @param updatetime 记录更新时间
	 * @return
	 */
	public long updateKeywordCount(String keyword,String updatetime){
		if (isKeywordExist(keyword)) {
			int count=getKeywordSearchCount(keyword);
			ContentValues values=new ContentValues();
			values.put(SearchKeywordsProperty.FREQUENCY, count+1);
			values.put(SearchKeywordsProperty.UPDATE_TIME, updatetime);
			return database.update(SearchKeywordsProperty.TABLE, values, SearchKeywordsProperty.KEYWORDS + " =?", new String[]{keyword});
		}
		return -1;
	}
	
	/**
	 * 删除所有关键字搜索记录
	 *<pre>方法  </pre>
	 * @return  the number of rows affected if a whereClause is passed in, 0
     *          otherwise. To remove all rows and get a count pass "1" as the
     *          whereClause.
	 */
	public int  deleteAllKeywords(){
		return database.delete(SearchKeywordsProperty.TABLE, null, null);
	}
	
	/**
	 * 判断关键字是否存在
	 *<pre>查找关键字 </pre>
	 * @param keywords 关键字
	 * @return
	 */
	public boolean isKeywordExist(String keywords){
		Cursor cursor=database.query(
				SearchKeywordsProperty.TABLE,
				null, 
				SearchKeywordsProperty.KEYWORDS+" =?",
				new String[]{keywords},
				null, 
				null, 
				null);
		if (null!=cursor&&cursor.moveToFirst()) {
			return true;
		}
		return false;
	}
}
