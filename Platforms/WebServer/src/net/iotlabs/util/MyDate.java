package net.iotlabs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MyDate {

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd. HH:mm");
		Date currentTime = new Date ();
		String dTime = formatter.format ( currentTime );
		System.out.println(dTime);
		
		String newTime = addDate(dTime, "yyyy.MM.dd. HH:mm", 0,0,0,+2,0,0);
		System.out.println(newTime);
		System.out.println(getCurrnetDate("yyyyMMdd_HHmmss"));
		
		// 24시간 표시법
//		HH : 0 -23
//		kk : 1 - 24
//		KK : 0 -11
//		hh : 1 - 12
	}
	
	 /**
	  * @param  fromDate  fromDate   날짜 문자열
	  * @param  format  format  날짜 포멧
	  * @param addYear 가산・감산할 연
	  * @param addMonth 가산・감산할 월
	  * @param addDate 가산・감산할 일
	  * @param addHour 가산・감산할 시간
	  * @param addMinute 가산・감산할 분
	  * @param addSecond 가산・감산할 초
	  * @return String  날짜 문자열
	  * @throws ParseException
	  * 예)
	  * 1. 하루 후의 날짜 구하기
	  * String toDate = addDate(fromDate, "yyyy-MM-dd HH:mm:ss", 0, 0, 1, 0, 0, 0);
	  * 2. 30분 후의 날짜 구하기
	  * String toDate = addDate(fromDate, "yyyy-MM-dd HH:mm:ss", 0, 0, 0, 0, 30, 0);
	  */
	 public static String addDate(String fromDate, String format,
	                            int addYear, int addMonth, int addDate,
	                            int addHour, int addMinute, int addSecond) throws ParseException{

	     SimpleDateFormat sdf = new SimpleDateFormat(format);
	     Date date = sdf.parse(fromDate);
	     Calendar cal = new GregorianCalendar();

	     cal.setTime(date);
	     cal.add(Calendar.YEAR, +addYear);
	     cal.add(Calendar.MONTH, +addMonth);
	     cal.add(Calendar.DATE, +addDate);
	     cal.add(Calendar.HOUR_OF_DAY, +addHour);
	     cal.add(Calendar.MINUTE, +addMinute);
	     cal.add(Calendar.SECOND, +addSecond);

	     SimpleDateFormat sdf2 = new SimpleDateFormat(format);
	     String toDate = sdf2.format(cal.getTime());

	     return toDate;
	 }
	 
	 public static String getCurrnetDate(String format){

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String toDate = sdf.format(date);
		
		return toDate;
	 }
}
