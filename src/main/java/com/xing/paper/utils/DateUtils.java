package com.xing.paper.utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateUtils
 * @Description: TODO
 */

public class DateUtils {
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
	
	public static String dateFormat = "yyyy-MM-dd HH:mm:ss";//日期格式化格式
	public static String dateBeginSuffix = " 00:00:00";     //开始日期后缀
	public static String dateEndSuffix   = " 23:59:59";     //结束日期后缀
	/**
	 * 将日期类型转换成自定义格式的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format) {
		String ret = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			ret = df.format(date);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}
	
	/**
	 * 将日期类型转换成自定义格式的字符串
	 * @param date 
	 * @param format yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String formatDateTime(Date date) {
		String ret = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			ret = df.format(date);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}
	/**
	 * 将字符串转换成日期类型
	 * 
	 * @param date
	 *            传入的字符串
	 * @return 转换成的日期值
	 */
	public static Date parseDate(String date) {
		Date ret = null;
		if (date != null && date != "") {
			try {
				ret = df.parse(date);
			} catch (ParseException e) {
				ret = null;
			}
		}
		return ret;
	}
	public static Date parseDate(String date,String format) {
		Date ret = null;
		SimpleDateFormat fd = new SimpleDateFormat(format);
		if (date != null && date != "") {
			try {
				ret = fd.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
				ret = null;
			}
		}
		return ret;
	}
	/**
	 * 将日期类型转换成"yyyy-MM-dd"字符串
	 * 
	 * @param date
	 *            传入的日期值
	 * @return 字符串类型的日期值
	 */
	public static String formatDate(Date date) {
		String ret = "";
		try {
			ret = df.format(date);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}

	/**
	 * 将日期类型转换成"yyyy-MM-dd hh:mm:ss"字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		String ret = "";
		try {
			ret = tf.format(date);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}
	/**
	 * 将日期类型转换成"yyyyMMddhhmmssSSS"字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatsfTime(Date date) {
		String ret = "";
		try {
			ret = sf.format(date);
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}
	/**
	 * 返回java.sql.Time
	 * @param strDate
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static java.sql.Time strToTime(String strDate){
		//String str=strDate;
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		java.util.Date d=null;
		try{
			d=df.parse(strDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		java.sql.Time time=new java.sql.Time(d.getTime());
		return time.valueOf(strDate);
	}
	/**
	 * 得到本月的第一天
	 * 
	 * @return
	 */
	public static Date getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return
	 */
	public static Date getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 得到30天后的日期
	 * 
	 * @return
	 */
	public static Date getValidityTime() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_WEEK, 29);
		return c.getTime();
	}
	
	public static  String getNextDay(String dateStr,String format)
	{
		Date date = parseDate(dateStr, format);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		Date rightDateStr = c.getTime();
		return formatDate(rightDateStr, format);
	}
	public static  String getPreDay(String dateStr,String format)
	{
		Date date = parseDate(dateStr, format);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		Date rightDateStr = c.getTime();
		return formatDate(rightDateStr, format);
	}
	public static  String getBeforeYesterDay(String dateStr,String format)
	{
		Date date = parseDate(dateStr, format);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -2);
		Date rightDateStr = c.getTime();
		return formatDate(rightDateStr, format);
	}
	
	/**
	 * 
	 * Description: 获取本周第一天(周一)
	 * 
	 * @author cuizheng
	 * @return
	 * @return Date
	 */
	public static String getWeekFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMinimum(Calendar.DAY_OF_WEEK) + 1);
		return formatDate(calendar.getTime());
	}
	
	/**
	 * 获取某天的所属周的第一天日期
	 * @param date
	 * @return
	 */
	public static Date getDateWeakFirstDay(String date){
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime((Date)parseDate(date));
			currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
			currentDate.set(Calendar.HOUR_OF_DAY, 0);  
			currentDate.set(Calendar.MINUTE, 0);  
			currentDate.set(Calendar.SECOND, 0);  
			currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
		return currentDate.getTime();
	}
	
	/**
	 * 获取某天的所属周的最后一天日期
	 * @param date
	 * @return
	 */
	public static Date getDateWeakLastDay(String date){
			Calendar currentDate = Calendar.getInstance();  
			currentDate.setTime((Date)parseDate(date));
			currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
			currentDate.set(Calendar.HOUR_OF_DAY, 23);  
			currentDate.set(Calendar.MINUTE, 59);  
			currentDate.set(Calendar.SECOND, 59);  
			currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 
		return currentDate.getTime();
	}
	
	/**
     * 获取一个月前的日期，
     * 格式：yyyy-MM-dd
     * @return
     */
	public static String getRecentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date d = cal.getTime();
		Date nd = new Date(d.getTime() + 86400000);
		String lastMonthDate = df.format(nd);  
    	return lastMonthDate;
	}
	
	/**
     * 获取六天前的日期，
     * 格式：yyyy-MM-dd
     * @return
     */
	public static String getRecentWeek() {
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-6);
        Date d = cal.getTime();
		String lastWeekDate = df.format(d);
    	return lastWeekDate;
	}
	
	/**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate() {    
    	Calendar cal = Calendar.getInstance();
    	Date d = cal.getTime();
        String currentDate = df.format(d);    
        return currentDate;    
    }
	
	/**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate(Date ndate) {    
        String currentDate = df.format(ndate);    
        return currentDate;    
    }
	
    /**
       * 毫秒转日期字符串
       * @param str
       * @return
      */
      public static String getDateTimeByMillisecond(String str) {
    	  Date date = new Date(Long.valueOf(str));
    	  String time = tf.format(date);
    	  return time;
      }
    
      public static String getSelLastOrNextDays(String dateStr,String format,Integer days){
	  		Date date = parseDate(dateStr, format);
	  		Calendar c = Calendar.getInstance();
	  		c.setTime(date);
	  		c.add(Calendar.DATE,days);
	  		Date rightDateStr = c.getTime();
	  		return formatDate(rightDateStr, format);
  	}
    
      public static Long timeStrTran1970Seconds(String timeStr){
    	  Date date = parseDate(timeStr,dateFormat);
          if (date != null)
             return date.getTime() / 1000;
          else
             return 0l;
     }
      
    public static String getLastDate(){
    	Calendar c = Calendar.getInstance();
  		c.setTime(new Date());
  		c.add(Calendar.DATE,-1);
  		Date lastDate = c.getTime();
  		return formatDate(lastDate, "yyyy-MM-dd");
    }
    
    /**
	 * 
	 * Description: 获取指定月份的第一天
	 * Date:2014-2-12
	 * @author cuizheng
	 * @param year
	 * @param month
	 * @return 
	 * @return String
	 */
	public static String getFirstDayInSelMonth(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return formatDate(calendar.getTime(),"yyyy-MM-dd");
	}
	
	/**
	 * 
	 * Description: 获取指定月份的最后一天
	 * Date:2014-2-12
	 * @author cuizheng
	 * @param year
	 * @param month
	 * @return 
	 * @return String
	 */
	public static String getLastDayInSelMonth(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return formatDate(calendar.getTime(),"yyyy-MM-dd");
	}
    
	 /**
     * 
     * Description: 获得当前日期年份
     * Date:2014-2-13
     * @author cuizheng
     * @return 
     * @return int
     */
    public static int getYearCurDate(){
    	Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }
	
    /**
     * 
     * Description: 获得当前日期月份
     * Date:2014-2-13
     * @author cuizheng
     * @return 
     * @return int
     */
    public static int getMonthCurDate(){
    	Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }
	
    public static int getYearSelDate(String date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime((Date)parseDate(date));
        return cal.get(Calendar.YEAR);
    }
    
    public static int getMonthSelDate(String date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime((Date)parseDate(date));
        return cal.get(Calendar.MONTH) + 1;
    }
    
	/**
     * Description:根据传入的月份，返回上一个或后一个月份
     * @author cuizheng
     * @param flag -1为获得前一个月份，1为获得后一个月份
     * @return String
     * @throws ParseException
     */
	 public static Integer getLastMonth(int flag){
    	Calendar calendar = Calendar.getInstance();   
    	calendar.add(Calendar.MONTH, flag);    //得到前一个月   
    	int month = calendar.get(Calendar.MONTH)+1;
    	return month;
	}
	
	
	public static int getDayCountSelMonth(int year,int month){
		Calendar calendar = Calendar.getInstance();  
		int date = 1;   
		calendar.set(year,month-1,date); 
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
		return maxDay;
	}
	
	public static int compateDate(String startTime,String endTime){
		Date date1=parseDate(startTime);
		Date date2=parseDate(endTime);
		return date1.compareTo(date2);
	}
	
	public static boolean isBetweenTwoDate(String targetDate,String beginDate,String endDate){
		Date date0=parseDate(targetDate);
		Date date1=parseDate(beginDate);
		Date date2=parseDate(endDate);
		if(date0.compareTo(date1)>=0&&date0.compareTo(date2)<=0){
			return true;
		}else{
			return false;
		}
		
	}
    public static int diffDays(Date beginDate, Date endDate) {

   	 long margin = 0;

   	    margin = endDate.getTime() - beginDate.getTime();

   	    margin = margin/(1000*60*60*24);

   	    return Integer.parseInt(String.valueOf(margin));
    }

    /**
     * 
     * getWeekDate:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author zyq
     * @param n n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
     * @param m 传周几Calendar.MONDAY（TUESDAY...）
     * @return
     * @since JDK 1.6
     */
    @SuppressWarnings("unused")
	public static String getWeekDate(int n, int m) {
        Calendar cal = Calendar.getInstance();
        //n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
        String monday;
        cal.add(Calendar.DATE, n*7);
        //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK,m);
        return  monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }
    public static int getLastYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -1);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定月份的最后一天
     * getLastDay:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author zyq
     * @param year
     * @param month
     * @return
     * @since JDK 1.6
     */
	public static int getLastDay(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	* @Description:获取N分钟后的时间
	* @author:CuiZheng
	* @param:@param minutes
	* @param:@return
	* @return:Date
	* @throws
	 */
	public static Date getTimeForMinutesLast(int minutes){
		Calendar now=Calendar.getInstance();
		now.add(Calendar.MINUTE,minutes);
		String dateStr=tf.format(now.getTimeInMillis());
		return parseDate(dateStr,"yyyy-MM-dd HH:mm:ss");
	}
	
    /**
     * beginCountDown:是否进入倒计时时间
     * 比较当前日期下的时分秒范围
     * @author wanglj
     * @param datestr  格式（HH:mm:ss）
     * @return false-尚未开始，true-已经开始
     * @since JDK 1.6
     */
    public static boolean beginCountDown(String datestr){
    	Calendar c = Calendar.getInstance();
		long nowl = c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(datestr.split(":")[0]));
		c.set(Calendar.MINUTE, Integer.valueOf(datestr.split(":")[1]));
		c.set(Calendar.SECOND, Integer.valueOf(datestr.split(":")[2]));
		long nowt = c.getTimeInMillis();
		if(nowt-nowl>0){
			return false;
		}else{
			return true;
		}
    }
    /**
     *
     * 获取当前时间的小时
     * @author hequan
     * @date  2018/7/6 0006 10:06
     * @param []
     * @return int
     */
    public static int getHour(){
		Calendar c = Calendar.getInstance();
		//获取当前时间的小时
		return c.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 *
	 * 获取距离当前时间n天前，后的日期
	 * @author hequan
	 * @date  2018/7/6 0006 14:50
	 * @param [format, offset]
	 * @return java.lang.String
	 */
	public static Date getBeforeOrAfterDate(String format,int offset){
		Calendar cd = Calendar.getInstance();
		//正数表示该日期后n天，负数表示该日期的前n天
		cd.add(Calendar.DATE, offset);
		Date dat = cd.getTime();
		SimpleDateFormat dformat = new SimpleDateFormat(format);
		return parseDate(dformat.format(dat), format);
	}
	public static void main(String[] args) {
		String lastDate = getLastDate();
		System.err.println(lastDate);
		System.out.println(getBeforeOrAfterDate("yyyy-MM-dd HH:mm:ss",-2));
	}

}
