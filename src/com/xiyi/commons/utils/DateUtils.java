package com.xiyi.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateFormatUtils;
/**
 * 有小数点时间转换
 * @author Administrator
 *
 */

public class DateUtils {
	/**
	 *  十三位数
	 * @param shijian
	 * @return
	 */
	public static String returnDate(String shijian){
		
		
		String haomiao = shijian.substring(10, shijian.length());
		String time = shijian.substring(0, 10)+"000";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = format.format(new Date(Long.valueOf(time)));
		time = time+haomiao;
		return time;
	}
	/**
	 * 十位数
	 * @param time
	 * @return
	 */
	public static String dateFromat(String time){
		time = time+"000";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = format.format(new Date(Long.valueOf(time)));
		return time;
	}
	
	 /**
     * 返回年月日
     * 
     * @return yyyyMMdd
     */
    public static String getTodayChar8()
    {
        return DateFormatUtils.format(new Date(), "yyyyMMdd");
    }
    public static String getTodayTime(){
    	return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 取时间戳返回年月日
     * @return String
     */
    public static String getDate(){
		String time = String.valueOf(new Date().getTime()).substring(0, 10);
		return time;
	}
    /**
     * 时间戳转换正常时间(yyyy-MM-dd HH:mm:ss)
     * @param time
     * @return
     */
    public static String getNewDate(String time){
    	time=time+"000";
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long lt = new Long(time);
	    Date date = new Date(lt);
	    String res = simpleDateFormat.format(date);
	    return res;
    }
}
