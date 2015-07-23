package zjdf.zhaogongzuo.databases.sharedpreferences;
import zjdf.zhaogongzuo.entity.User;
import zjdf.zhaogongzuo.utils.MD5Utils;
import zjdf.zhaogongzuo.utils.StringUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 该类用于保存数据到SharePreference，并提供读取功能
 * @author Eilin.Yang
 *
 */
public final class UserKeeper {
	/**
	 * 主要数据
	 */
	private static final String SETS_PREFERENCES_NAME = "users_keeper";
	
	/**
	 *保存用户信息
	 * @param context 上下文环境
	 * @param  一个完整的用户
	 */
	public static void keepUser(Context context,User user) {
		if (user!=null&&!StringUtils.isEmpty(user.getId())) {
			SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
			Editor editor = pref.edit();
			String y=user.getName();
			String x=user.getId();
			String z=user.getPassword();
			x=MD5Utils.encoding(x, '+');
			y=MD5Utils.encoding(y, '~');
			z=MD5Utils.encoding(z, '!');
			editor.putString("xx", x);
			editor.putString("yy", y);
			editor.putString("zz", z);
			editor.commit();
		}
	}
	
	/**
	 * 清空sharePreference 的用户信息
	 * @param context
	 */
	public static boolean clearUser(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("xx").remove("yy").remove("zz");
	    return editor.commit();
	}

	/**
	 * 从SharedPreferences读取 用户信息
	 * @param context
	 * @return 
	 */
	public static User readUser(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		String x=pref.getString("xx", null);
		String y=pref.getString("yy", null);
		String z=pref.getString("zz", null);
		if (StringUtils.isEmpty(x)||StringUtils.isEmpty(y)||StringUtils.isEmpty(z)) {
			return null;
		}
		x=MD5Utils.decoding(x, '+');
		y=MD5Utils.decoding(y, '~');
		z=MD5Utils.decoding(z, '!');
		User user=new User();
		user.setId(x);
		user.setName(y);
		user.setPassword(z);
		return user;
	}
	
	/**
	 * 读取信息
	 * @param context
	 * @return
	 */
	public static String readToken(Context context){
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		String x=pref.getString("tt", null);
		if (StringUtils.isEmpty(x)) {
			return null;
		}
		x=MD5Utils.decoding(x, '^');
		return x;
	}
	
	/**
	 * 保存token
	 * @param context
	 * @param tt 信息
	 */
	public static void keepToken(Context context,String tt){
		if (StringUtils.isEmpty(tt)) {
			return;
		}
		SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		String x=MD5Utils.encoding(tt, '^');
		editor.putString("tt", x);
		editor.commit();
	}
	
	/**
	 * 清空sharePreference 的用户Token信息
	 * @param context
	 */
	public static boolean clearToken(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.remove("tt");
	    return editor.commit();
	}
	
	/**
	 * 清空sharePreference 的信息
	 * @param context
	 */
	public static boolean clear(Context context){
	    SharedPreferences pref = context.getSharedPreferences(SETS_PREFERENCES_NAME, Context.MODE_PRIVATE);
	    Editor editor = pref.edit();
	    editor.clear();
	    return editor.commit();
	}
}
