/*
 * Takes two Strings
 * Converts to long
 * calculates the remainder of string 1 and 2
 * decreases the remainder to zero
 * counting ms
 * Converts ms to string and HH:mm:ss format
 * Prints HH:mm:ss format
 * Prints "30min Remaining" for 1 min at 30min mark
 * Prints "Time up 0" at end of countdown
 */

package GUITemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Timers extends Thread {

    static String startTime = "23:00:00";
    static String endTime = "23:00:10";
	

    static Thread thread = new Thread();
    
	
	public static void main(String args[]) throws InterruptedException, ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    Date date1 = format.parse(startTime);
	    Date date2 = format.parse(endTime);
	    long remainTime = date2.getTime() - date1.getTime();
		
		for(long i = remainTime; i >= 0; i--)
		{
			Thread.sleep(1);
			
		    long millis = i;
		    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
		            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
		            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		    System.out.println(hms);
		    
		    
		    while(i <= 1800000 && i >= 1750000) {
		    	System.out.println("30 min Remaining ");
		    	i--;
		    	Thread.sleep(1);
		    } 
		    
		    for(;i == 0;){
		    	System.out.println("Test Time Ended");
		    		break;
		    }
		    
		}
	}	
}