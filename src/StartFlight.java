import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JMenu;

public class StartFlight implements Runnable{

	private JMenu menuTime;
	private long miliseconds;
	private static int state = 1;
	
	public StartFlight(JMenu menuTime) {
		this.menuTime = menuTime;
		miliseconds = 1591012800000L;
	}
	
	@Override
	public void run() {
		
		JMenu menuTime = this.getMenuTime();
		long miliseconds = 1591012800000L;
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			long ms = miliseconds;
			@Override
			public void run() {
				if (state == 1) {
					Date time = new Date(ms);
					String timeToText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
					menuTime.setText(timeToText);
					ms += TimeUnit.MINUTES.toMillis(1);
					System.out.println(timeToText+"\n");
					
					Flight flight = new Flight();
					List<Flight> flightList = null;
					try {
						flightList = flight.findByDepartureTime(timeToText);
						for (Flight s : flightList) {
							if (flightList.size() > 0) {
								Tower fligt = new Tower(s);
								Thread t = new Thread(fligt);
								t.start();
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		timer.schedule(task, 0, 1000);
	}
	
	public JMenu getMenuTime() {
		return menuTime;
	}
	public void setMenuTime(JMenu menuTime) {
		this.menuTime = menuTime;
	}
	public long getMiliseconds() {
		return miliseconds;
	}
	public void setMiliseconds(long miliseconds) {
		this.miliseconds = miliseconds;
	}
	public static int getState() {
		return state;
	}
	public static void setState(int state) {
		StartFlight.state = state;
	}
}
