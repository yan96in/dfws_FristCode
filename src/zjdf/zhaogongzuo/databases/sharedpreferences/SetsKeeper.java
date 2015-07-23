package zjdf.zhaogongzuo.databases.sharedpreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 该类用于保存数据到SharePreference，并提供读取功能
 * @author Eilin.Yang
 *
 */
public class SetsKeeper {
	/**
	 * 主要数据
	 */
	private static final String SETS_PREFERENCES_NAME = "sets_keeper";
	private static final String SETS_PREFERENCES_JOBFAIR_STATE="sets_jobfair_state";
	private static final String SETS_PREFERENCES_JOBFAIR_TIME="sets_jobfair_time";

	/**
	 * 保存数据选项版本
	 * @param context 上下文环境
	 * @param 
	 */
	public static void keepOptionVersion(Context context,int optVersion) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("optversion", optVersion);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 的加载模式信息
	 * @param context
	 */
	public static void clearOptionVersion(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("optversion");
	    editor.commit();
	}

	/**
	 * 从SharedPreferences读取加载模式信息
	 * @param context
	 * @return 有FrameConfigure.TYPE_IMG_NULL、FrameConfigure.TYPE_IMG_ALLOW、FrameConfigure.TYPE_IMG_SMART
	 */
	public static int readOptionVersion(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref.getInt("optversion", 100);
	}
	
	
	/**
	 * 保存招聘会闹钟提醒状态
	 * @param context
	 * @param state 1:开启,其他关闭
	 * @param meetid 招聘会id
	 */
	public static void keepJobfairAlarmState(Context context,int state,String meetid) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_JOBFAIR_STATE, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(meetid, state);
		editor.commit();
	}
	
	/**
	 * 清除招聘会闹钟提醒状态
	 * @param context
	 * @param meetid 招聘会id
	 */
	public static void clearJobfairAlarmState(Context context,String meetid){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_JOBFAIR_STATE, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove(meetid);
	    editor.commit();
	}

	/**
	 * 读取招聘会闹钟提醒状态
	 * @param context
	 * @param meetid招聘会id
	 * @return
	 */
	public static int readJobfairAlarmState(Context context,String meetid){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_JOBFAIR_STATE, Context.MODE_PRIVATE);
		return pref.getInt(meetid, 0);
	}
	
	/**
	 * 保存招聘会闹钟提醒时间
	 * @param context
	 * @param state 1:开启,其他关闭
	 * @param meetid 招聘会id
	 */
	public static void keepJobfairAlarmTime(Context context,String time,String meetid) {
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_JOBFAIR_TIME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(meetid, time);
		editor.commit();
	}
	
	/**
	 * 清除招聘会闹钟提醒时间
	 * @param context
	 * @param meetid 招聘会id
	 */
	public static void clearJobfairAlarmTime(Context context,String meetid){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_JOBFAIR_TIME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove(meetid);
	    editor.commit();
	}

	/**
	 * 读取招聘会闹钟提醒时间
	 * @param context
	 * @param meetid招聘会id
	 * @return
	 */
	public static String readJobfairAlarmTime(Context context,String meetid){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_JOBFAIR_TIME, Context.MODE_PRIVATE);
		return pref.getString(meetid, null);
	}
	
}
