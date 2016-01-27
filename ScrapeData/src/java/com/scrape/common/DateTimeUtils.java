/*
 * DateTimeUtils.java
 *
 * Created on August 6, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.scrape.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Vu Thi Thu Huong
 */
public class DateTimeUtils {

    private static Logger log = Logger.getLogger(DateTimeUtils.class);
    /** Creates a new instance of DateTimeUtils */
    public DateTimeUtils() {
    }

    public static Date truncMonth(Date date) throws Exception {
        return DateTimeUtils.convertStringToTime(DateTimeUtils.convertDateTimeToString(date, "yyyy/MM/dd"), "yyyy/MM/dd");
    }

    public static Date convertStringToTime(String date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Date ParseException, string value:" + date);
        }
        return null;
    }

    public static Date convertStringToDate(String date) throws Exception {
        //String pattern = "dd/MM/yyyy";
        String pattern = "yyyy-MM-dd";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDate(String date, String pattern) throws Exception {
        return convertStringToTime(date, pattern);
    }

//    public static Date convertStringToDateTime(String date) throws Exception
//    {
//        String pattern = "dd/MM/yyyy hh24:mi:ss";
//        return convertStringToTime(date, pattern);
//    }
    public static String convertDateToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String convertDateToString(Date date, String pattern) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     *  @author: dungnt
     *  @todo: get sysdate
     *  @return: String sysdate
     */
    public static String getSysdate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        return convertDateToString(calendar.getTime());
    }

    /*
     *  @author: dungnt
     *  @todo: get sysdate detail
     *  @return: String sysdate
     */
    public static String getSysDateTime() throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
    }
    /*
     *  @author: ThanhNC
     *  @todo: get sysdate detail formated in pattern
     *  @return: String sysdate 
     */

    public static String getSysDateTime(String pattern) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     *  @author: dungnt
     *  @todo: convert from String to DateTime detail
     *  @param: String date
     *  @return: Date
     */
    public static Date convertStringToDateTime(String date) throws Exception {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static String convertDateTimeToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String convertDateTimeToStringDetail(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String convertDateTimeToString(Date date, String pattern) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @author: ThangPV
     * @todo: convert from java.util.Date to java.sql.Date
     */
    public static java.sql.Date convertToSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * @anhlt - Get the first day on month selected.
     * @param dateInput
     * @return
     */
    public static String parseDate(int monthInput) {
        String dateReturn = "01/01/";
        Calendar cal = Calendar.getInstance();
        switch (monthInput) {
            case 1:
                dateReturn = "01/01/";
                break;
            case 2:
                dateReturn = "01/02/";
                break;
            case 3:
                dateReturn = "01/03/";
                break;
            case 4:
                dateReturn = "01/04/";
                break;
            case 5:
                dateReturn = "01/05/";
                break;
            case 6:
                dateReturn = "01/06/";
                break;
            case 7:
                dateReturn = "01/07/";
                break;
            case 8:
                dateReturn = "01/08/";
                break;
            case 9:
                dateReturn = "01/09/";
                break;
            case 10:
                dateReturn = "01/10/";
                break;
            case 11:
                dateReturn = "01/11/";
                break;
            case 12:
                dateReturn = "01/12/";
                break;
        }
        return dateReturn + cal.get(Calendar.YEAR);
    }

    /*
     *  @author: ToiNA
     *  @todo: Get first day of current month
     *  @return: Date
     */
    public static Date getFirstDayOfCurrentMonth(int month, int year) {
        //Using for get first day of the current month
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, cal.get(Calendar.DATE));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get last day of current month
     *  @return: Date
     */
    public static Date getLastDayOfCurrentMonth(int month, int year) {
        //Using for get last day of the current month
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, cal.get(Calendar.DATE));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get first day of month before
     *  @return: Date
     */
    public static Date getFirstDayOfMonthBefore(int month, int year) {
        //Using for get first day of the month before
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 2, cal.get(Calendar.DATE));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get first day of current month
     *  @return: Date
     */
    public static Date getFirstDayOfBillCycleFrom(int date) {
        //Using for get first day of the current month
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), date);
        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get last day of current month
     *  @return: Date
     */
    public static Date getLastDayOfbillCycleFrom(int date) {
        //Using for get last day of the current month
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, date - 1);
        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get current day
     *  @return: Long
     */
    public static Long getCurrentDay() {
        //Using for get current day
        Calendar cal = Calendar.getInstance();
        Long currentDay = Long.valueOf(Integer.toString(cal.get(Calendar.DATE)));
        return currentDay;
    }

    /*
     *  @author: ToiNA
     *  @todo: Get N1K date of current month
     *  @return: Date
     */
    public static Date getN1KDateOfCurrentMonth(int month, int year) {
        //Using for get last day of the current month
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 15);
        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get N1K date of two month ago
     *  @return: Date
     */
    public static Date getN1KDateOfTwoMonthAgo(int month, int year) {
        //Using for get last day of the current month
        Calendar cal = Calendar.getInstance();
        if (month == 1) {
            cal.set(year, 10, 16);
        }
        if (month == 2) {
            cal.set(year, 11, 16);
        } else {
            cal.set(year, month - 3, 16);
        }

        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get first day of N1K date for two month ago
     *  @return: Date
     */
    public static Date getFirstDayOfN1KDateForTwoMonthAgo(int month, int year) {
        //Using for get last day of the current month
        Calendar cal = Calendar.getInstance();
        if (month == 1) {
            cal.set(year, 10, 1);
        }
        if (month == 2) {
            cal.set(year, 11, 1);
        } else {
            cal.set(year, month - 3, 1);
        }
        return cal.getTime();
    }

    /*
     *  @author: ToiNA
     *  @todo: Get current day
     *  @return: Long
     */
    public static java.sql.Date getSQLDate(Date date) {        
        //Using for get current day
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(date);
        java.sql.Date sqlDate = java.sql.Date.valueOf(new String(strDate));
        return sqlDate;
    }

    /*
     *  @author: TUNGTV
     *  @todo: Get last day of previous month
     *  @return: Date
     */
    public static Date getLastDayOfMonthBefore(int month, int year) {
        //Using for get last day of the current month
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-2, cal.get(Calendar.DATE));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


    /*
     *  @author: TUNGTV
     *  @todo: Get next month
     *  @return: Date
     */
    public static Date getNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
        return cal.getTime();
    }


    /*
     *  @author: TUNGTV
     *  @todo: get End Of Day
     *  @return: Date
     */
    public static Date getEndOfDay(Date date) throws Exception {
        String strToDate = DateTimeUtils.convertDateToString(date);
        String strTDate = strToDate.substring(0, 10) + " 23:59:59";
        Date tDate = DateTimeUtils.convertStringToTime(strTDate, "yyyy-MM-dd HH:mm:ss");
        return tDate;
    }


    /*
     *  @author: TUNGTV
     *  @todo: get End Of Day
     *  @return: Date
     */
    public static Date getStartOfDay(Date date) throws Exception {
        String strToDate = DateTimeUtils.convertDateToString(date);
        String strTDate = strToDate.substring(0, 10) + " 00:00:00";
        Date tDate = DateTimeUtils.convertStringToTime(strTDate, "yyyy-MM-dd HH:mm:ss");
        return tDate;
    }

    /*
     *  @author: NAMDX
     *  @todo: Get next year
     *  @return: Date
     */
    public static Date getNextYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear() + 1, date.getMonth(), date.getDate());
        return cal.getTime();
    }

    /*
     *  @author: NAMDX
     *  @todo: daysBetween
     *  @return: Long
     */
    public static long daysBetween(Date d1, Date d2) {
        Long ONE_HOUR = 60 * 60 * 1000L;
        return ((d2.getTime() - d1.getTime() +
                ONE_HOUR) / (ONE_HOUR * 24));
    }

    /**
     * Get quarter of year written by ToiNA
     * @param strMonth Month number
     * @return quater
     * @throws Exception if having any runtime exception happen
     */
    public static String QuarterOfYear(String strMonth) {
        String quarter = "0";
        try {
            int month = Integer.parseInt(strMonth);
            switch (month) {
                case 1:
                    quarter = "1";
                    break;
                case 2:
                    quarter = "1";
                    break;
                case 3:
                    quarter = "1";
                    break;
                case 4:
                    quarter = "2";
                    break;
                case 5:
                    quarter = "2";
                    break;
                case 6:
                    quarter = "2";
                    break;
                case 7:
                    quarter = "3";
                    break;
                case 8:
                    quarter = "3";
                    break;
                case 9:
                    quarter = "3";
                    break;
                case 10:
                    quarter = "4";
                    break;
                case 11:
                    quarter = "4";
                    break;
                case 12:
                    quarter = "4";
                    break;
                default:
                    System.err.println("convert error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quarter;
    }

    /**
     * Get half of year written by ToiNA
     * @param strMonth Month number
     * @return half of year
     * @throws Exception if having any runtime exception happen
     */
    public static String halfOfYear(String strMonth) {
        String quarter = "0";
        try {
            int month = Integer.parseInt(strMonth);
            switch (month) {
                case 1:
                    quarter = "1";
                    break;
                case 2:
                    quarter = "1";
                    break;
                case 3:
                    quarter = "1";
                    break;
                case 4:
                    quarter = "1";
                    break;
                case 5:
                    quarter = "1";
                    break;
                case 6:
                    quarter = "1";
                    break;
                case 7:
                    quarter = "2";
                    break;
                case 8:
                    quarter = "2";
                    break;
                case 9:
                    quarter = "2";
                    break;
                case 10:
                    quarter = "2";
                    break;
                case 11:
                    quarter = "2";
                    break;
                case 12:
                    quarter = "2";
                    break;
                default:
                    System.err.println("convert error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quarter;
    }

    public static Date getFirstDayOfMonth(){
		Calendar cal1 = Calendar.getInstance();
		cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));
		cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal1.getTime();
    }
    
     public static Date getLastDayOfMonth(){
		Calendar cal1 = Calendar.getInstance();
		cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));
		cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal1.getTime();
    }

    public static Date getFirstDayOfMonth(Date date){
		Calendar cal1 = Calendar.getInstance();
		cal1.set(date.getYear(), date.getMonth(), date.getDay());
		cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal1.getTime();
    }

    /* Tung TV add */
    public static Date getFirstDayOfMonthByDate(Date date){
		Calendar cal1 = Calendar.getInstance();
		cal1.set(date.getYear() + 1900, date.getMonth(), date.getDay()+ 10);
		cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal1.getTime();
    }

    public static Date getLastDayOfMonthByDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDateForGetEndDate = dateFormat.format(date);
        String[] arrstrDateForGetEndDate = strDateForGetEndDate.split("-");
        Date endOfMonthDate = DateTimeUtils.getLastDayOfCurrentMonth(Integer.parseInt(arrstrDateForGetEndDate[1]), Integer.parseInt(arrstrDateForGetEndDate[2]));
        return endOfMonthDate;
    }

    public static Date addDayToDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear() + 1900, date.getMonth(), date.getDate() + day, date.getHours(), date.getMinutes(), date.getSeconds());
        return cal.getTime();
    }

    public static int getMonthOfDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        String[] arrStrDate = strDate.split("-");
        int month = Integer.valueOf(arrStrDate[1]);
        return month;
    }

    //for Oracle
    public static Date getSysDate(Session session) throws Exception {
        log.info("Get sysdate from dual");
        try {
            if(session == null){
                return new Date();
            }
            String pattern = "MM/dd/yyyy HH:mm:ss";
            String queryString = "SELECT to_Char(sysdate,'MM/dd/yyyy hh24:mi:ss') from dual";
            log.info("queryString=" + queryString);
            Query queryObject = session.createSQLQuery(queryString);
            List list = queryObject.list();

            if (list != null && list.size() > 0) {
                log.info("List of Subscriber size = " + list.size());
                return DateTimeUtils.convertStringToTime(((Object) list.get(0)).toString(), pattern);
            }
            return new Date();
        } catch (Exception e) {
            log.error("Get sysdate from dual error", e);
            throw e;
        }
    }

    /**
     * @purpose: Lay ve dinh dang ngay dang yyyymmddhh24miss
     **/
    public static String getPosStandardDate(Date date)
    {
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMddHHmmss");
        String returnTemp = sdf.format(date);
        return returnTemp;
//        Date curr = date;
//        int yyyy = curr.getYear() + 1900;
//        String temp = new Long(yyyy).toString();
//        int month = curr.getMonth() + 1;
//        String monthTemp = StringUtils.addZeroToString(new Long(month).toString(), 2);
//        temp += monthTemp;
//        int dd = curr.getDate();
//        String ddTemp = StringUtils.addZeroToString(new Long(dd).toString(), 2);
//        temp += ddTemp;
//        int hh = curr.getHours();
//        String hhTemp = StringUtils.addZeroToString(new Long(hh).toString(), 2);
//        temp += hhTemp;
//        int mm = curr.getMinutes();
//        String mmTemp = StringUtils.addZeroToString(new Long(mm).toString(), 2);
//        temp += mmTemp;
//        int ss = curr.getSeconds();
//        String ssTemp = StringUtils.addZeroToString(new Long(ss).toString(), 2);
//        temp += ssTemp;
//        return temp;
    }


    /**
     * @purpose: Lay ve dinh dang ngay dang yyyymmdd
     **/
    public static String getPosProcessCodeDate(Date date)
    {
        Date curr = date;
        int yyyy = curr.getYear() + 1900;
        String temp = new Long(yyyy).toString();
        int month = curr.getMonth() + 1;
        String monthTemp = addZeroToString(new Long(month).toString(), 2);
        temp += monthTemp;
        int dd = curr.getDate();
        String ddTemp = addZeroToString(new Long(dd).toString(), 2);
        temp += ddTemp;
        return temp;
    }


    public static String getPosIDDate(Date date)
    {
        Date curr = date;
        int yyyy = curr.getYear() + 1900;
        String temp = new Long(yyyy).toString();
        int month = curr.getMonth() + 1;
        String monthTemp = addZeroToString(new Long(month).toString(), 2);
        temp += monthTemp;
        int dd = curr.getDate();
        String ddTemp = addZeroToString(new Long(dd).toString(), 2);
        temp += ddTemp;

        return temp;
    }

    public static String addZeroToString(String input, int strLength){
        String result = input;
        for(int i = 1; i<= strLength - input.length(); i++){
            result = "0" + result;
        }
        return "";
    }

    public static Date convertPosDate(String posDate) throws Exception
    {
        if (posDate == null || posDate.length() < 14)
        {
            return null;
        }
        String year = posDate.substring(0,4);
        String month = posDate.substring(4,6);
        String day = posDate.substring(6, 8);
        String hh = posDate.substring(8,10);
        String mm = posDate.substring(10, 12);
        String ss = posDate.substring(12, 14);

        String date = day + "/" + month +"/" + year + " " + hh +":" + mm + ":" + ss;
        Date tempDate = convertStringToDate(date, "dd/MM/yyyy HH:mm:ss");
        return tempDate;
    }

    public static  Date getLastDayOfMonthAfter(int month, int year) {
        //Using for get last day of the current month
        Calendar cal = Calendar.getInstance();
        cal.set(year, (month - 1) - 1, 15);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Long getDate(Date date)
    {
        int temp = date.getDate();
        return new Long(temp);
    }

     public static String getMonthYearOfDate(Date date,String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String strDate = dateFormat.format(date);
        String[] arrStrDate = strDate.split("/");
        String monthYear = arrStrDate[1] + "/" + arrStrDate[2];
        return monthYear;
    }

    public static boolean isEquals(Date d1, Date d2) throws Exception{
        if((d1.getDate() == d2.getDate()) && (d1.getMonth() == d2.getMonth()) && (d1.getYear() == d2.getYear())){
            return true;
        }
        else{
            return false;
        }
    }


    public static void main(String[] args) throws Exception
    {
        Date d = new Date();
        System.out.println("datesdfsdf : " + DateTimeUtils.convertStringToDate(DateTimeUtils.convertDateTimeToString(d, "yyyy-MM-dd"), "yyyy-MM-dd"));

    }

}
