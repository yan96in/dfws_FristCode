package zjdf.zhaogongzuo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import zjdf.zhaogongzuo.databases.sharedpreferences.UserKeeper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * The String tools
 * 
 * @author Eilin.Yang 2013-4-1
 */
public class StringUtils extends DeviceUtils {

	/**
	 * <pre>
	 * 复制
	 * </pre>
	 * 
	 * @param context
	 * @param txt
	 */
	@SuppressLint("NewApi")
	public static void copy(Context context, String txt) {
		int v = getApiVersion();
		if (v < 11) {
			android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboardManager.setText(txt);
		} else {
			android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData cd = ClipData.newPlainText("label", txt);
			clipboardManager.setPrimaryClip(cd);
		}
	}

	/**
	 * <pre>
	 * 获取复制的数据
	 * </pre>
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String getCopyString(Context context) {
		int v = getApiVersion();
		if (v < 11) {
			android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			return clipboardManager.getText().toString();
		} else {
			android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			return clipboardManager.getText().toString();
		}
	}

	/**
	 * filter "null" or null
	 * 
	 * @param source
	 *            input
	 * @return source or ""
	 */
	public static String stringFilter(String source) {
		source = (source == null ? "" : source);
		source = ("null".equals(source) ? "" : source);
		return source;
	}

	/**
	 * filter "" or null
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isNullOrEmpty(String source) {
		if (null == source) {
			return true;
		} else if (source.length() == 0) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns true if the string is null or 0-length.
	 * 
	 * @param str
	 *            the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns true if the string is null or 0-length.
	 * 
	 * @param str
	 *            the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(com.alibaba.fastjson.JSONArray array) {
		if (array == null || array.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns true if the JSONObject is null or 0-length.
	 * 
	 * @param str
	 *            the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(JSONObject object) {
		if (object == null || object == JSONObject.NULL || object.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns true if the JSONArray is null or 0-length.
	 * 
	 * @param str
	 *            the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(JSONArray object) {
		if (object == null || object == JSONObject.NULL || object.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * get the picture name from URL
	 * 
	 * @param url
	 *            the URI of picture
	 * @return name removed the extension
	 */
	public static String getPictureName(String url) {
		if (null != url) {
			String x = url.substring(url.lastIndexOf("/") + 1);
			if (x.contains(".")) {
				return x.substring(0, x.lastIndexOf("."));
			}
			return x;
		}
		return url;
	}

	/**
	 * get file name
	 * 
	 * @param url
	 *            file's URL
	 * @return
	 */
	public static String getFileName(String url) {
		if (null != url) {
			return url.substring(url.lastIndexOf("/") + 1);
		}
		return url;
	}

	/**
	 * replace the tag of "<img/>" from HTML data
	 * 
	 * @param content
	 *            HTML data
	 * @param imgs_path
	 *            the local path of images
	 * @return has been replace HTML file:///android_asset/pic.jpg
	 */
	public static String replaceImgTag(int readingid, String content,
			String[] imgs_path) {
		if (imgs_path == null || content == null) {
			return null;
		}
		String regex = "<!--image#[0-9]+-->";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		if (matcher == null) {
			return null;
		}
		int n = 0;
		while (matcher.find()) {
			String targt = matcher.group();
			String re = "<img style=\"max-width:95%; box-shadow: 0px 0px 6px #000;\" src =\""
					+ "图片路径" + readingid + "/" + imgs_path[n] + "\"/>";
			content = content.replace(targt, re);
			n++;
		}
		return content;
	}

	/**
	 * replace the tag of "<img/>" from HTML data
	 * 
	 * @param content
	 *            HTML data
	 * @param imgs_path
	 *            the local path of images
	 * @return has been replace HTML
	 * 
	 */
	public static String replaceImgToDefualt(int readingid, String content,
			String[] imgs_path) {
		if (imgs_path == null || content == null) {
			return null;
		}
		String regex = "<!--image#[0-9]+-->";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		if (matcher == null) {
			return null;
		}
		int n = 0;
		while (matcher.find()) {
			String targt = matcher.group();
			String re = "<p style=\"text-align:center; margin:0; border:1px solid #f00;\"><img style=\"width:95%; box-shadow: 0px 0px 6px #000;\" src =\"file:///android_asset/pic_load_def.png\"/></p>";
			content = content.replace(targt, re);
			n++;
		}
		return content;
	}

	/**
	 * restoring the picture name with extension
	 * 
	 * @param aName
	 *            picture name
	 * @param bName
	 *            extension
	 * @return a full name
	 */
	public static String replaceLastNameForPic(String aName, String bName) {
		if (aName != null && bName != null && !"".equals(aName)
				&& !"".equals(bName)) {
			bName = aName.substring(0, aName.lastIndexOf(".") + 1) + bName;
		}
		return bName;
	}

	/**
	 * URLencoding an String CharSets
	 * 
	 * @param str
	 * @param charsetName
	 *            <li>ISO-8859-1 <li>US-ASCII <li>UTF-16 <li>UTF-16BE <li>
	 *            UTF-16LE <li>UTF-8
	 * @return
	 */
	public static String getURLEncode(String str, String charsetName) {
		if (!isNullOrEmpty(str)) {
			try {
				return URLEncoder.encode(str, charsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 功能：去掉所有的<*>标记,去除html标签
	 * 
	 * @param content
	 * @return
	 */
	public static String removeTagFromText(String content) {
		Pattern p = null;
		Matcher m = null;
		String value = null;
		// 去掉<>标签
		p = Pattern.compile("(<[^>]*>)");
		m = p.matcher(content);
		String temp = content;
		while (m.find()) {
			value = m.group(0);
			temp = temp.replace(value, "");
		}
		// 去掉换行或回车符号
		p = Pattern.compile("(/r+|/n+)");
		m = p.matcher(temp);
		while (m.find()) {
			value = m.group(0);
			temp = temp.replace(value, " ");
		}
		return temp;
	}

	/**
	 * 返回当前程序版本名称
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "3.0.0";
		try {
			// Get the package info
			versionName = context.getPackageManager().getPackageInfo(
					"zjdf.zhaogongzuo", 0).versionName;
		} catch (Exception e) {
			return versionName;
		}
		return versionName;
	}
	
	/**
	 * 返回当前程序版本名称
	 * @param context
	 * @param packageName 包名
	 * @return
	 */
	public static String getAppVersionName(Context context,String packageName) {
		String versionName = "3.0.0";
		try {
			// Get the package info
			versionName = context.getPackageManager().getPackageInfo(
					packageName, 0).versionName;
		} catch (Exception e) {
			return versionName;
		}
		return versionName;
	}

	/**
	 * 返回当前程序版本名称
	 * @param context
	 * @param packageName 包名
	 * @return
	 */
	public static int getAppVersionNameInt(Context context, String packageName) {
		int versionNameInt = 3;context.getPackageName();
		try {
			// Get the package info
			String versionName = context.getPackageManager().getPackageInfo(
					packageName, 0).versionName;
			if (!isEmpty(versionName) && versionName.contains(".")) {
				String[] a = versionName.split("\\.");
				String b = "";
				for (int i = 0; i < a.length; i++) {
					b = b + a[i];
				}
				versionNameInt = Integer.parseInt(b);
			}
		} catch (Exception e) {
			return versionNameInt;
		}
		return versionNameInt;
	}
	
	/**
	 * 返回当前程序版本名称
	 */
	public static int getAppVersionCode(Context context, String packageName) {
		int versionNameInt = 3;
		try {
			// Get the package info
			versionNameInt = context.getPackageManager().getPackageInfo(
					packageName, 0).versionCode;
		} catch (Exception e) {
			return versionNameInt;
		}
		return versionNameInt;
	}

	/**
	 * 返回当前程序版本名称
	 */
	public static int parseArrayToInt(String str) {
		int versionNameInt = 3;
		try {
			// Get the package info
			if (!isEmpty(str) && str.contains(".")) {
				String[] a = str.split("\\.");
				String b = "";
				for (int i = 0; i < a.length; i++) {
					b = b + a[i];
				}
				versionNameInt = Integer.parseInt(b);
			}
		} catch (Exception e) {
			return versionNameInt;
		}
		return versionNameInt;
	}

	/**
	 * 判断两个字符串是否相等
	 * 
	 * @param cs1
	 * @param cs2
	 * @return
	 */
	public static boolean equals(CharSequence cs1, CharSequence cs2) {
		return cs1 == null ? cs2 == null : cs1.equals(cs2);
	}

	/**
	 * get the width of screen
	 * 
	 * @param activity
	 * @return width in px
	 */
	public static int getScreenWith(Activity activity) {
		int width = 0;
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		return width;
	}

	/**
	 * get the width of screen,when API>3.2
	 * 
	 * @param activity
	 * @return width in px
	 */
	@SuppressLint("NewApi")
	public static int getScreenWithNew(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.x;
	}

	/**
	 * 把String 转换 Unicode
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static String Conversion(String s) throws IOException {
		String s1 = new String();
		String s2 = new String();
		byte abyte0[] = s.getBytes("Unicode");
		for (int j = 2; j < abyte0.length; j += 2) {
			String s3 = Integer.toHexString(abyte0[j + 1]);
			int i = s3.length();
			if (i < 2)
				s1 = s1 + "&#x" + "0" + s3;
			else
				s1 = s1 + "&#x" + s3.substring(i - 2);
			s3 = Integer.toHexString(abyte0[j]);
			i = s3.length();
			if (i < 2)
				s1 = s1 + "0" + s3 + ";";
			else
				s1 = s1 + s3.substring(i - 2) + ";";
		}

		return s1;
	}

	/**
	 * 验证Email
	 * 
	 * @param email
	 *            email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkEmail(String email) {
		// String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z0-9]+\\s*$";
		return Pattern.matches(regex, email);
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param idCard
	 *            居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
	 * 
	 * @param mobile
	 *            移动、联通、电信运营商的号码段
	 *            <p>
	 *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
	 *            </p>
	 *            <p>
	 *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
	 *            </p>
	 *            <p>
	 *            电信的号段：133、153、180（未启用）、189
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPhone(String mobile) {
		// String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		String res = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
		return Pattern.matches(res, mobile);
	}

	/**
	 * 验证固定电话号码
	 * 
	 * @param phone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 *            <p>
	 *            <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
	 *            的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
	 *            </p>
	 *            <p>
	 *            <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。
	 *            </p>
	 *            <p>
	 *            <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkTelePhone(String telephone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, telephone);
	}

	/**
	 * 验证整数（正整数和负整数）
	 * 
	 * @param digit
	 *            一位或多位0-9之间的整数
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 * 
	 * @param decimals
	 *            一位或多位0-9之间的浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * 验证空白字符
	 * 
	 * @param blankSpace
	 *            空白字符，包括：空格、\t、\n、\r、\f、\x0B
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * 验证中文
	 * 
	 * @param chinese
	 *            中文字符
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * 验证中文,字母和。
	 * 
	 * @param str
	 *            
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkVariety(String str){
		String regex = "[\u4e00-\u9fa5[a-zA-Z](\\.)?\\s+]+";
		return Pattern.matches(regex, str);
	} 

	/**
	 * 验证日期（年月日）
	 * 
	 * @param birthday
	 *            日期，格式：1992-09-03，或1992.09.03
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * 验证URL地址
	 * 
	 * @param url
	 *            格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
	 *            http://www.csdn.net:80
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkURL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * 匹配中国邮政编码
	 * 
	 * @param postcode
	 *            邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 * 
	 * @param ipAddress
	 *            IPv4标准地址
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	/**
	 * 判断一个字符串是否有字母 
	 * @param idcard
	 * @return
	 */
	public static boolean isNum(String idcard) {

		boolean isNO = idcard.matches("[0-9]+");
		if (isNO) {
			return true;
		} else
			return false;
	}
}
