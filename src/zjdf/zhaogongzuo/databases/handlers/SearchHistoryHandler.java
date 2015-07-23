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
package zjdf.zhaogongzuo.databases.handlers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import zjdf.zhaogongzuo.databases.CommonDatabase;
import zjdf.zhaogongzuo.databases.properties.SearchHistoryProperty;
import zjdf.zhaogongzuo.entity.SearchHistory;

/**
 *<h2> SearchHistoryHandler</h2>
 *<pre> 历史记录 </pre>
 * @author Eilin.Yang VeryEast
 * @since 2014年4月25日
 * @version 
 * @modify 
 * 
 */
public class SearchHistoryHandler {
	/**数据库对象*/
	private SQLiteDatabase database=null;
	/**创建数据库*/
	private CommonDatabase databaseBox=null;
	
	public SearchHistoryHandler(Context context) {
		databaseBox=new CommonDatabase(context, SearchHistoryProperty.DB_NAME, new String[]{SearchHistoryProperty.CREATE_TABLE},new String[]{SearchHistoryProperty.TABLE});
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
	 * 获取指定条数的历史记录
	 *<pre>方法  </pre>
	 * @param num 指定数量
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<SearchHistory> getKeywords(int num){
		if (num<1) {
			num=1;
		}
		ArrayList<SearchHistory> list=null;
		Cursor cursor=database.query(SearchHistoryProperty.TABLE, new String[]{SearchHistoryProperty.ID,SearchHistoryProperty.KEYWORD,SearchHistoryProperty.SCOPE,SearchHistoryProperty.ADDRESS,SearchHistoryProperty.ADDRESS_CODE,SearchHistoryProperty.POSITIONCLASS,SearchHistoryProperty.POSITIONCLASS_CODE}, null, null, null, null, SearchHistoryProperty.UPDATE_TIME+" desc", "0,"+num);		
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				SearchHistory keyvalue=null;
				if (list==null) {
					list=new ArrayList<SearchHistory>();
				}else
					list.clear();
				do{
					keyvalue=new SearchHistory(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
					list.add(keyvalue);
				}while (cursor.moveToNext());
			}
			cursor.close();
		}
		return list;
	}
	
	
	/**
	 * 插入历史记录
	 *<pre>方法  </pre>
	 * @param userid
	 * @param keyword
	 * @param scope
	 * @param address
	 * @param positionclass
	 * @param updatetime
	 * @return
	 */
	public long insertHistory(String userid,String keyword,int scope,String address,String address_code,String positionclass,String positionclass_code,String updatetime){
		ContentValues values=new ContentValues();
		values.put(SearchHistoryProperty.ID, userid==null ? "" : userid);
		values.put(SearchHistoryProperty.KEYWORD, keyword==null?"": keyword);
		values.put(SearchHistoryProperty.SCOPE, scope);
		values.put(SearchHistoryProperty.ADDRESS, address==null?"":address);
		values.put(SearchHistoryProperty.ADDRESS_CODE, address_code==null?"":address_code);
		values.put(SearchHistoryProperty.POSITIONCLASS, positionclass==null?"":positionclass);
		values.put(SearchHistoryProperty.POSITIONCLASS_CODE, positionclass_code==null?"":positionclass_code);
		values.put(SearchHistoryProperty.UPDATE_TIME, updatetime);
		return database.insert(SearchHistoryProperty.TABLE, null, values);
	}
	
	/**
	 * 插入历史记录
	 *<pre>方法  </pre>
	 * @param history
	 * @param updatetime
	 * @return
	 */
	public long insertHistory(SearchHistory history,String updatetime){
		ContentValues values=new ContentValues();
		values.put(SearchHistoryProperty.ID, history.userid==null ? "" : history.userid);
		values.put(SearchHistoryProperty.KEYWORD, history.keyword==null ? "" : history.keyword);
		values.put(SearchHistoryProperty.SCOPE, history.scope);
		values.put(SearchHistoryProperty.ADDRESS, history.address==null ? "" : history.address);
		values.put(SearchHistoryProperty.ADDRESS_CODE, history.address_code==null ? "" : history.address_code);
		values.put(SearchHistoryProperty.POSITIONCLASS, history.positionclass==null ? "" : history.positionclass);
		values.put(SearchHistoryProperty.POSITIONCLASS_CODE, history.positionclass_code==null ? "" : history.positionclass_code);
		values.put(SearchHistoryProperty.UPDATE_TIME, updatetime);
		return database.insert(SearchHistoryProperty.TABLE, null, values);
	}
	
	/**
	 * 更新记录时间
	 *<pre>方法  </pre>
	 * @param history_value value,有关键字，地址，职位类型的value之间由&符号拼接
	 * @param updatetime 记录的更新时间
	 * @return
	 */
	public long updateHistory(String keyword,int scope,String address_code,String positionclass_code,String updatetime){
		
		ContentValues values=new ContentValues();
		values.put(SearchHistoryProperty.UPDATE_TIME, updatetime);
		return database.update(SearchHistoryProperty.TABLE, values, SearchHistoryProperty.KEYWORD+" =? and "+SearchHistoryProperty.SCOPE+" = ? and "+SearchHistoryProperty.ADDRESS_CODE+" = ? and "+SearchHistoryProperty.POSITIONCLASS_CODE+" = ?", new String[]{keyword==null?"":keyword,scope+"",address_code==null?"":address_code,positionclass_code==null?"":positionclass_code});
	
	}
	
	/**
	 * 更新记录时间
	 *<pre>方法  </pre>
	 * @param history_value value,有关键字，地址，职位类型的value之间由&符号拼接
	 * @param updatetime 记录的更新时间
	 * @return
	 */
	public long updateHistory(SearchHistory history,String updatetime){
		
		ContentValues values=new ContentValues();
		values.put(SearchHistoryProperty.UPDATE_TIME, updatetime);
		return database.update(SearchHistoryProperty.TABLE, values, SearchHistoryProperty.KEYWORD+" =? and "+SearchHistoryProperty.SCOPE+" = ? and "+SearchHistoryProperty.ADDRESS_CODE+" = ? and "+SearchHistoryProperty.POSITIONCLASS_CODE+" = ? ", new String[]{history.keyword==null?"":history.keyword,history.scope+"",history.address_code==null?"":history.address_code,history.positionclass_code==null?"":history.positionclass_code});
	
	}
	
	/**
	 * 删除所有搜索记录
	 *<pre>方法  </pre>
	 * @return  the number of rows affected if a whereClause is passed in, 0
     *          otherwise. To remove all rows and get a count pass "1" as the
     *          whereClause.
	 */
	public int  deleteAllHistory(){
		return database.delete(SearchHistoryProperty.TABLE, null, null);
	}
	
	/**
	 * 判断是否存在
	 *<pre>方法  </pre>
	 * @param keyword
	 * @param scope
	 * @param address_code
	 * @param positionclass_code
	 * @return
	 */
	public boolean isValueExist(String keyword,int scope,String address_code,String positionclass_code){
		Cursor cursor=database.query(
				SearchHistoryProperty.TABLE,
				null, 
				SearchHistoryProperty.KEYWORD+" =? and "+SearchHistoryProperty.SCOPE+" = ? and "+SearchHistoryProperty.ADDRESS_CODE+" = ? and "+SearchHistoryProperty.POSITIONCLASS_CODE+" = ?",
				new String[]{keyword==null?"":keyword,scope+"",address_code==null?"":address_code,positionclass_code==null?"":positionclass_code},
				null, 
				null, 
				null);
		if (null!=cursor&&cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}
	
	/**
	 * 判断是否存在
	 *<pre>方法  </pre>
	 * @param keyword
	 * @param scope
	 * @param address_code
	 * @param positionclass_code
	 * @return
	 */
	public boolean isValueExist(SearchHistory history){
		Cursor cursor=database.query(
				SearchHistoryProperty.TABLE,
				null, 
				SearchHistoryProperty.KEYWORD+" =? and "+SearchHistoryProperty.SCOPE+" = ? and "+SearchHistoryProperty.ADDRESS_CODE+" = ? and "+SearchHistoryProperty.POSITIONCLASS_CODE+" = ?",
				new String[]{history.keyword==null?"":history.keyword,history.scope+"",history.address_code==null?"":history.address_code,history.positionclass_code==null?"":history.positionclass_code},
				null, 
				null, 
				null);
		if (null!=cursor&&cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}
}
