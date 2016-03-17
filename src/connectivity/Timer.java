/* Timer class receives via GUITemplate class a String containing start & endTime from the database. 
* String contains date & time for start and end of the test. This gets parsed into int's and 
* each value get striped off the string by position. It takes that value and divide it by /1000
* to get seconds. today value subtract from that day equals remaining milliseconds.
* Milliseconds then gets decreased for each loop by a thread. Milliseconds gets converted back
* to day, hour, minute and second that will be displayed on a label in GUITemplate class.
*/

package connectivity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import GUITemplate.GUITemplate;
import entity.Test;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class Timer extends Thread {

	static Test currentTest;
	public static int SECONDS_IN_A_DAY = 24 * 60 * 60;
	static int year;
	static int month;
	static int day;
	static int hour;
	static int min;
	static Boolean run;
	public String timerText;
	Label label;


	@Override
	public void run() {
		
		String testEnd = currentTest.getTestEnd();
		year = Integer.parseInt("20" + testEnd.substring(0, 2));
		month = Integer.parseInt(testEnd.substring(3, 5));
		day = Integer.parseInt(testEnd.substring(6, 8));
		hour = Integer.parseInt(testEnd.substring(9, 11));
		min =Integer.parseInt(testEnd.substring(12, 14));
		System.out.println(testEnd);
		System.out.println(year + "  " + month + "  " + day + " " + hour + " " + min);
		
		while (run) {
			

			Calendar thatDay = Calendar.getInstance();
			thatDay.setTime(new Date(0)); /* reset */
			thatDay.set(Calendar.DAY_OF_MONTH, day);
			thatDay.set(Calendar.MONTH, (month - 1)); // 0-11 so 1 less
			thatDay.set(Calendar.YEAR, year);
			thatDay.set(Calendar.HOUR, hour);
			thatDay.set(Calendar.MINUTE, min);

			Calendar today = Calendar.getInstance();
			long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
			long diffSec = diff / 1000;

			long days = diffSec / SECONDS_IN_A_DAY;
			long secondsDay = diffSec % SECONDS_IN_A_DAY;
			long seconds = secondsDay % 60;
			long minutes = (secondsDay / 60) % 60;
			long hours = (secondsDay / 3600); // % 24 not needed
			if(run){
		Platform.runLater(()->{
			GUITemplate.setLabelTime(days,hours,minutes,seconds);
		});
			}
			
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	public void setTest(Test test) {
		this.currentTest = test;

	}
	
	public void setText(String time){
		timerText=time;
		
	}
	public void setLabel(Label lbl) {
		label = lbl;
		
	}
	public void setRun(Boolean value){
		run=value;
	}
}
