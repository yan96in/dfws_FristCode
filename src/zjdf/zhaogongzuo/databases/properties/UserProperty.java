package zjdf.zhaogongzuo.databases.properties;

/**
 * <h2>用户表属性</h2>
 * <pre>记录登录用户详细信息</pre>
 * @author Eilin.Yang
 * @since 2013-10-21
 * @version v1.0
 */
public class UserProperty {

	/**用户数据库*/
	public static final String DB_NAME="very.db";
	/**表名称*/
	public static final String TABLE="very_table";
	/**行ID*/
	public static final String RAW_ID="raw_id";
	/**用户ID*/
	public static final String XX="xx";
	/**用户名*/
	public static final String YY="yy";
	/**用户名*/
	public static final String ZZ="zz";
	/**记录更新时间*/
	public static final String UPDATE_TIME="update_time";
	/**创建表格*/
	public static final String CREATE_TABLE="create table if not exists "
			+TABLE+"("
			+RAW_ID+" integer primary key autoincrement, "
			+XX+" text, "
			+YY+" text, "
			+ZZ+" text, "
			+UPDATE_TIME+" text )";
	/**查询指定ID的用户*/
	public static final String RAW_QUERY="select * from "+TABLE+" where "+YY+" = ? ";
}
