package zjdf.zhaogongzuo.databases.sharedpreferences;

import zjdf.zhaogongzuo.entity.SubPush;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 保存职位订阅的状态
 * 
 * @author Administrator
 * 
 */
public class JobKeeper {

	/**
	 * 主要数据
	 */
	private static final String SETS_PREFERENCES_NAME = "job_keeper";

	/**
	 * 保存用户信息
	 * 
	 * @param context
	 *            上下文环境
	 * @param 一个完整的用户
	 */
	public static void keepPush(Context context, SubPush subPush) {

		SharedPreferences pref = context.getSharedPreferences(
				SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("posi", subPush.posi_code);
		editor.putString("area", subPush.area_code);
		editor.putString("salary", subPush.salary);
		editor.putString("room", subPush.room);
		editor.putString("job", subPush.job_starts);
		editor.putString("push", subPush.push);
		editor.putInt("status", subPush.status);
		editor.commit();

	}

	/**
	 * 从SharedPreferences读取 用户信息
	 * 
	 * @param context
	 * @return
	 */
	public static SubPush readSubPush(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		String posi = pref.getString("posi", null);
		String area = pref.getString("area", null);
		String salary = pref.getString("salary", null);
		String room = pref.getString("room", null);
		String job = pref.getString("job", null);
		String push = pref.getString("push", null);
		int status = pref.getInt("status", -1);
		SubPush subpush = new SubPush();
		subpush.posi_code = posi;
		subpush.area_code = area;
		subpush.salary = salary;
		subpush.room = room;
		subpush.job_starts = job;
		subpush.push = push;
		subpush.status = status;
		return subpush;
	}

	/**
	 * 清空sharePreference 的用户信息
	 * 
	 * @param context
	 */
	public static boolean clearJob(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		// editor.remove("posi").remove("area").remove("salary");
		editor.clear();
		return editor.commit();
	}
}
