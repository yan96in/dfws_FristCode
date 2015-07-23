package zjdf.zhaogongzuo.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
/**
 * 
 * <h2>获取设备信息 <h2>
 * <pre> 设备操作</pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-26
 * @version v1.0
 * @modify ""
 */
public class DeviceUtils {

	/**
	 * 
	 *<pre>获取ip地址</pre>
	 * @return
	 */
	public static String getIpAddress() {  
        String ipaddress="";          
        try {  
            for (Enumeration<NetworkInterface> en = NetworkInterface  
                    .getNetworkInterfaces(); en.hasMoreElements();) {  
                NetworkInterface intf = en.nextElement();  
                for (Enumeration<InetAddress> enumIpAddr = intf  
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                    InetAddress inetAddress = enumIpAddr.nextElement();  
                    if (!inetAddress.isLoopbackAddress()) {  
                            ipaddress=ipaddress+";"+ inetAddress.getHostAddress().toString();  
                    }  
                }  
            }  
        } catch (SocketException ex) {  
        }  
        return ipaddress; 
    }
	/**
	 * 
	 *<pre>获取MAC地址</pre>
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {   
	    WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	    WifiInfo info = wifi.getConnectionInfo();   
	    return info.getMacAddress();   
	}
	
	/**
	 * 
	 *<pre>获取IMEI号</pre>
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {   
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei=telephonyManager.getDeviceId();
	    return imei;   
	}
	
	/**
	 * 
	 *<pre>获取手机型号</pre>
	 * @param context
	 * @return
	 */
	public static String getMobleInfo() {   
	    return android.os.Build.MODEL;   
	}
	
	/**
	 * 
	 *<pre>获取系统版本</pre>
	 * @param context
	 * @return
	 */
	public static String getSystemVersion() {   
	    return android.os.Build.VERSION.RELEASE;   
	}
	
	/**
	 * 
	 *<pre>获取系统API版本</pre>
	 * @param context
	 * @return
	 */
	public static int getApiVersion() {   
	    return android.os.Build.VERSION.SDK_INT;   
	}
	
	/**
	 * dip/dp transform px
	 * @param context activity
	 * @param dpValue dip/dp value
	 * @return px value
	 */
	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px  transform  dp/dip
	 * @param context activity
	 * @param pxValue px value
	 * @return dp/dip value
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	    
	/**
	 * get the width of screen
	 * @param activity
	 * @return width in px
	 */
	public static int getScreenWith(Activity activity) {
		int width=0;
		DisplayMetrics metric=new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		return width;
	}
	
	 /**
	  * 将px值转换为sp值，保证文字大小不变
	  * 
	  * @param pxValue
	  * @param fontScale（DisplayMetrics类中属性scaledDensity）
	  * @return
	  */
	 public static int px2sp(float pxValue, Context context) {
		 float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		 return (int) (pxValue / fontScale + 0.5f);
	 }

	 /**
	  * 将sp值转换为px值，保证文字大小不变
	  * 
	  * @param spValue
	  * @param fontScale（DisplayMetrics类中属性scaledDensity）
	  * @return
	  */
	 public static int sp2px(float spValue, Context context) {
		 float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		 return (int) (spValue * fontScale + 0.5f);
	 }
		
	public static int getStatusBarHeight(Activity activity)
	{
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	
	
	/**
     * 
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
	
	/**
	 * get the height of screen
	 * @param activity
	 * @return height in px
	 */
	public static int getScreenHeight(Activity activity) {
		int height=0;
		DisplayMetrics metric=new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);		
		height = metric.heightPixels;     
		return height;
	}
}
