package eu.sidzej.wc.utils;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimeUtils {

	public static Calendar getCalendar(Timestamp t){
		if(t == null)
			return null;
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(t);
	    return (Calendar) calendar.clone();
	}
	
	public static Timestamp getTimestamp(Calendar c){
		if(c == null)
			return null;
		return new Timestamp(c.getTime().getTime());
	}
	
	public static String getTime(){
		Calendar calendar = Calendar.getInstance();
	    return (new Timestamp(calendar.getTime().getTime())).toString();
	}
	
	
}
