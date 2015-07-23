package zjdf.zhaogongzuo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;
/**
 * the tools for date time
 * @author Eilin.Yang
 *
 */
public final class DateTimeUtils extends StringUtils{
	/**
	 * 获取完整的日期时间类型.年月日，时分秒
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getLongDateTime(boolean hasFormat) {
		String format="yyyy-MM-dd HH:mm:ss";
		if (!hasFormat) {
			format="yyyyMMddHHmmss";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return date;
	}
	
	/**
	 * 时分秒都为0的长时间类型,年月日，时分秒
	 *<pre>方法  </pre>
	 * @param hasFormat 是否带格式
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getLongDateTime00(boolean hasFormat) {
		String format="yyyy-MM-dd HH:mm:ss";
		if (!hasFormat) {
			format="yyyyMMddHHmmss";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return (hasFormat?date.substring(0, 10)+" 00:00:00":date.substring(0, 8)+" 000000");
	}

	/**
	 * 年月日
	 *<pre>方法  </pre>
	 * @param hasFormat
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getSimpleDateTime(boolean hasFormat) {
		String format="yyyy-MM-dd";
		if (!hasFormat) {
			format="yyyyMMdd";
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String date = sDateFormat.format(new java.util.Date()) + "";
		return date;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getPutDateTime(String date) {
		Log.i("getPutDateTime", "date:"+date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
			Log.i("getPutDateTime", "dates:"+dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getWeiBoDateTime(String date) {
		Log.i("getPutDateTime", "date:"+date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
			Log.i("getPutDateTime", "dates:"+dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	/**
	 * 从时间中获取日期
	 * date "yyyy-MM-dd HH:mm:ss"
	 * 返回 "yyyy-MM-dd"
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateFromDatetime(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	/**
	 * 获取自定义时间
	 * @param date "yyyy-MM-dd HH:mm:ss"
	 * @param format 返回的日期时间格式.如："yyyy-MM-dd"
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCustomDateTime(String date,String format) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return dates;
		}
		return dates;
	}
	
	/**
	 * 获取自定义时间
	 * @param date "yyyy-MM-dd HH:mm:ss"
	 * @param getformat 返回的日期时间格式.如："yyyy-MM-dd"
	 * @param inputFormat 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCustomDateTime(String date,String getFormat,String inputFormat) {
		SimpleDateFormat df = new SimpleDateFormat(inputFormat);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(getFormat);
		String dates="";
		try {
			dates = sDateFormat.format(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return dates;
		}
		return dates;
	}
	
	/**
	 * 根据时间取到日期
	 *<pre>方法  </pre>
	 * @param time yyyy-MM-dd HH:mm:ss
	 * @return
	 */

	public static Date getDate(String time) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 java.util.Date date=null;
		 try {
			 date=df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
		  return date;
	}
	
	/**
	 * 根据时间取到日期
	 *<pre>方法  </pre>
	 * @param time yyyy-MM-dd HH:mm:ss
	 * @return
	 */

	public static int getWeek(String time) {
		int day=1;
		day=getDate(time).getDay();
		return day;
	}
	
	/**
	 * 获取该时间与今天的时间差
	 *<pre>方法  </pre>
	 * @param time 时间
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getDiffer(String time) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 long l=0;
		 try {
			 String today=getLongDateTime(true);
			 java.util.Date now = df.parse(today);
			 java.util.Date date=df.parse(time);
			 l=now.getTime()-date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return l;
	}
	
	/**
	 * 年月日 00:00:00 与今天 00:00:00的时间差
	 *<pre>方法  </pre>
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getDiffer00(String time) {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 long l=0;
		 try {
			 String today=getLongDateTime00(true);
			 if (null!=time&&time.length()>0) {
				time=time.substring(0, 10)+" 00:00:00";
			 }
			 java.util.Date now = df.parse(today);
			 java.util.Date date=df.parse(time);
			 l=now.getTime()-date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  return l;
	}
	
	/**
	 * 获取今天
	 *<pre>方法  </pre>
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date getToday() {
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 java.util.Date now=null;
		 try {
			 String today=getLongDateTime(true);
			 now = df.parse(today);
		} catch (ParseException e) {
			e.printStackTrace();
			return now;
		}
		  return now;
	}
	
	/**
	 * 获取今天
	 *<pre>方法  </pre>
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Calendar getCalendar(String datetime) {
		 java.util.Date date=getDate(datetime);
		 Calendar calendar=Calendar.getInstance();
		 calendar.setTime(date);
//		 calendar.set(Calendar.YEAR, date.getYear());
//		 calendar.set(Calendar.MONTH,date.getMonth());
//		 calendar.set(Calendar.DAY_OF_MONTH, date.getDate());
//		 calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
//		 calendar.set(Calendar.MINUTE, date.getMinutes());
//		 calendar.set(Calendar.SECOND, 0);
		 return calendar;
	}
	
	/**
	 * 获取发布时间
	 *<pre>方法  </pre>
	 * @param date
	 * @return x天前，，x小时前，，x分钟前，，1分钟内
	 */
	public static String getPutDate(String date){
		if (date==null||date.length()==0) {
			return "";
		}
		long l=DateTimeUtils.getDiffer(date);
		long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		if (day>0) {
			return day+"天前";
		}else {
			if (hour>0) {
				return hour+"小时前";
			}else {
				if (min>0) {
					return min+"分钟前";
				}else {
					return "1分钟内";
				}
			}
		}
	}
	
	/**
	 * 时间格式转化。把完整的时间日期转化成 "MM月dd日 HH:mm"格式
	 * @param date 完整日期格式  
	 * @return
	 */
	public static String parseDateTime(String date) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
		String dates="";
		Date d=new Date(date);
		dates = sDateFormat.format(d);
		return dates;
	}
	
	
}
