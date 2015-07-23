package zjdf.zhaogongzuo.databases.handlers;

import java.util.ArrayList;
import java.util.List;

import zjdf.zhaogongzuo.databases.CommonDatabase;
import zjdf.zhaogongzuo.databases.properties.SearchKeywordsProperty;
import zjdf.zhaogongzuo.databases.properties.UserProperty;
import zjdf.zhaogongzuo.entity.User;
import zjdf.zhaogongzuo.utils.DateTimeUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

/**
 * <h2>用户数据操作</h2>
 * <pre>用户信息管理</pre>
 * @author Eilin.Yang
 *2013-10-21
 */
public class UserDatabaseHelper{

	private static final String TAG="UserDatabaseHelper";
	/**上下文*/
	private Context context;
	/**数据库对象*/
	private SQLiteDatabase database=null;
	/**创建数据库*/
	private CommonDatabase databaseBox=null;
	private boolean userDatabaseStatus;
	
	public UserDatabaseHelper(Context context) {
		this.context=context;
		databaseBox=new CommonDatabase(context,UserProperty.DB_NAME,new String[]{UserProperty.CREATE_TABLE},new String[]{UserProperty.TABLE});
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
		Log.i(TAG, "userdatabase is closed");
	}
	
	/***
	 * get all User from database
	 * @return the list of User or null.
	 */
	public List<User> getAllUsers(){
		ArrayList<User> list=null;
		Cursor cursor =database.query(UserProperty.TABLE, null, null, null, null, null, UserProperty.UPDATE_TIME+" DESC");
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				list=new ArrayList<User>();
				do{
					User user=new User();
					user.setId(cursor.getString(1));
					user.setName(cursor.getString(2));
					list.add(user);
				}while (cursor.moveToNext());
			}
		}
		cursor.close();
		return list;
	}
	
	/***
	 * get the User through User id
	 * @param user_id  User ID
	 * @return user or null.
	 */
	public User getUser(String name){
		User user=null;
		Cursor cursor=database.rawQuery(UserProperty.RAW_QUERY, new String[]{name});
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				user=new User();
				user.setId(cursor.getString(1));
				user.setName(cursor.getString(2));
			}
		}
		cursor.close();
		return user;
	}
	
	/***
	 * get the User through User id
	 * @param user_id  User ID
	 * @return user or null.
	 */
	public User getUser(){
		User user=null;
		Cursor cursor=database.query(UserProperty.TABLE, new String[]{UserProperty.YY,UserProperty.ZZ}, null, null, null, null, UserProperty.UPDATE_TIME+" desc", "0,1");
		if (cursor!=null) {
			if (cursor.moveToFirst()) {
				user=new User();
				user.setId(cursor.getString(1));
				user.setName(cursor.getString(2));
			}
		}
		cursor.close();
		return user;
	}
	

	/***
	 * judge the user is or not exist
	 * @param user_id user ID
	 * @return true：exist。false：not exist
	 */
	public boolean isUserExist(String name) {
		boolean flag=false;
		if (!TextUtils.isEmpty(name)) {
			Cursor cursor=database.query(
					UserProperty.TABLE,
					null, 
					UserProperty.YY+" = "+name,
					null, 
					null, 
					null, 
					null);
			flag=(cursor==null ? false:cursor.moveToFirst());
			if (cursor!=null) {
				cursor.close();
			}
		}
		return flag;
	}
	
	/***
	 * insert an user to database
	 * @param content user instance
	 * @return true：success;false：not success
	 */
	public boolean insertUser(User user){
		boolean flag=false;
		if (user!=null) {
			if (isUserExist(user.getId())) {
				return false;
			}
			ContentValues values=new ContentValues();
			values.put(UserProperty.YY, user.getName());
			values.put(UserProperty.ZZ, user.getPassword());
			values.put(UserProperty.UPDATE_TIME, DateTimeUtils.getLongDateTime(true));
			long r= database.insert(UserProperty.TABLE, null, values);
			if (r!=-1) {
				flag=true;
			}
		}
		return flag;
	}
	
	/***
	 * insert an user to database
	 * @param user user
	 * @param status login status.true:login,false:logout
	 * @return true：success;false：not success
	 */
	public boolean updateUser(User user){
		boolean flag=false;
		if (user!=null) {
			if (!isUserExist(user.getName())) {
				return false;
			}
			ContentValues values=new ContentValues();
			values.put(UserProperty.YY, user.getName());
			values.put(UserProperty.ZZ, user.getPassword());
			values.put(UserProperty.UPDATE_TIME, DateTimeUtils.getLongDateTime(true));
			long r= database.update(UserProperty.TABLE, values, UserProperty.YY+" = ?", new String[]{user.getName()});
			if (r>0) {
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * delete an User
	 * @param user_id user's id
	 * @return delete status.
	 * the number of rows affected if a whereClause is passed in, 0 otherwise.
	 *  To remove all rows and get a count pass "1" as the whereClause.
	 */
	public  int deleteUser(String name) {
		return database.delete(UserProperty.TABLE, UserProperty.YY+" = ? ", new String[]{name});
	}
	
	/**
	 * delete all User
	 * @return delete status. 
	 * the number of rows affected if a whereClause is passed in, 0 otherwise. 
	 * To remove all rows and get a count pass "1" as the whereClause.
	 */
	public int deleteAllUser(){
		return database.delete(UserProperty.TABLE, null, null);
	}
}
