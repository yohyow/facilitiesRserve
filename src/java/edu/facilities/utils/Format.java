/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.facilities.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  工具类
 * 
 * @author user
 */
public class Format {
    
    //一年毫秒数
    public static final long LONG_ONE_YEAR = 366 * 24 * 60 * 60 * 1000;
    //两个月毫秒数
    public static final long LONG_TWO_MONTH = 2*31 * 24 * 60 * 60 * 1000;
    //一天毫秒数
    public static final long LONG_ONE_DAY = 24 * 60 * 60 * 1000;
    
    public static String null2Blank(Object str) {
        String result = "";
        if(null == str || str.equals("null")) {
            return result;
        }
        return str.toString();
    }
    
    public static int str2Int(Object obj) {
        int intVal = -1;
        if(null == obj) {
            return intVal;
        }
        try {
            intVal = Integer.parseInt(obj.toString());
        } catch (Exception e) {
            intVal = -1;
        }
        return intVal;
    }
    
    /**
     * 中文字符串解码
     * @param str
     * @param charsetfrom
     * @param charsetto
     * @return 
     */
    public static String chartsetString(String str, String charsetfrom, String charsetto) {
        try {
            return new String(str.getBytes(charsetfrom), charsetto);
        } catch (UnsupportedEncodingException ex) {
            return str;
        }
    }
    
    /**
     * 中文字符串解码
     * @param str
     * @param charsetfrom
     * @param charsetto
     * @return 
     */
    public static String iso2utf8(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return str;
        }
    }
    
    /**
     * 格式化日期
     * @param date
     * @return 
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    /**
     * 格式化日期
     * @param date
     * @return 
     */
    public static Date formatDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * 格式化日期为yyyy-MM格式
     * @param date
     * @return 
     */
    public static String formatStringToMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            return sdf.format(date);
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * 格式化日期为yyyy-MM格式
     * @param date
     * @return 
     */
    public static Date formatStringToMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            return sdf.parse(date);
        } catch (Exception e) {
        }
        return null;
    }
    
     /**
     * 格式化日期为yyyy-MM-dd格式
     * @param date
     * @return 
     */
    public static Date formatString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * 格式化日期为yyyy-MM-dd格式
     * @param date
     * @return 
     */
    public static String formatString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(date);
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * 格式化日期为yyyy-MM-dd HH:mm格式
     * @param date
     * @return 
     */
    public static Date formatStringWithHour(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(date);
        } catch (Exception e) {
        }
        return null;
    }
    
//    /**
//     * 由
//     * @param from
//     * @param to
//     * @return 
//     */
//    public static String changeDateStyle(String from, String to) {
//        
//    }
    
    /**
     * 比较fromdate是否比todate大 大返回1 小则返回-1
     * @param fromDate
     * @param toDate
     * @return 
     */
    public static int compareDate(String fromDate, String toDate) {
        Date fromdate = formatDate(fromDate);
        Date todate = formatString(toDate);
        if(fromDate != null && todate != null) {
            if (fromdate.getTime() > todate.getTime()) { 
                return 1; 
            } else if (fromdate.getTime() < todate.getTime()) { 
                return -1; 
            } else { 
                return 0; 
            }
        }else {
            return 2;
        }
    }
    
    /**
     * 比较fromdate是否比todate大 大返回1 小则返回-1
     * @param fromDate
     * @param toDate
     * @return 
     */
    public static int compareDateWithReserve(String fromDate, String toDate) {
        Date fromdate = formatDate(fromDate);
        Date todate = formatDate(toDate);
        if(fromDate != null && todate != null) {
            if (fromdate.getTime() > todate.getTime()) { 
                return 1; 
            } else if (fromdate.getTime() < todate.getTime()) { 
                return -1; 
            } else { 
                return 0; 
            }
        }else {
            return 2;
        }
    }
    
    /**
     * 比较fromdate是否比todate大 大返回1 小则返回-1
     * @param fromDate
     * @param toDate
     * @return 
     */
    public static int compareDateWithDate(Date fromDate, Date toDate) {
        if(fromDate != null && toDate != null) {
            if (fromDate.getTime() > toDate.getTime()) { 
                return 1; 
            } else if (fromDate.getTime() < toDate.getTime()) { 
                return -1; 
            } else { 
                return 0; 
            }
        }else {
            return 2;
        }
    }
}
