
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Tower implements Runnable{

	private Flight flight;

	public Tower(Flight flight) {
		this.flight = flight;
	}
	
	
	@Override
	public void run() {
		int delayDeparture=0, delayArrive=0;
		int delayTime=0, delayTime2=0;
		String status="";
		Random random = new Random();
		
		synchronized (random) {
		delayDeparture = random.nextInt(5) + 1;  //for delayDeparture=1, flight cancel; for delayDeparture=2, take-off delay by tower
		delayArrive=random.nextInt(5) + 1;  //for delayArrive=3, landing delay by tower
		}
		
		Capital capital = new Capital();
		Capital arrivalCapitalQuery = null;
		Capital departureCapitalQuery = null;
		
		try {
			arrivalCapitalQuery = capital.findById(flight.getArrival_id());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			departureCapitalQuery = capital.findById(flight.getDeparture_id());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(delayDeparture != 1) {
				if (delayDeparture == 2) {
					try {
					delayTime=random.nextInt(10) + 1;
						System.out.print("\nThe take-off of flight "+flight.getFlight_number()+" was delayed by the tower for "+ delayTime +" minutes.\n");
						Thread.sleep(delayTime * 1000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			
			FlightMessage f_started = new FlightMessage();
			f_started.setVisible(true);
			String a = "Flight " +flight.getFlight_number()+ " is ready for departure!";
			System.out.print("Flight " +flight.getFlight_number()+ " is ready for departure!\n");
			f_started.sMessage(a);
			
			
			String d1 = flight.getScheduled_arrival();
			String d2 = flight.getScheduled_departure();
			
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			long milliSecondsElapsed = 0;
			try {
				Date date1 = sdf.parse(d1);
				Date date2 = sdf.parse(d2);
				milliSecondsElapsed = date1.getTime() - date2.getTime();
				milliSecondsElapsed/=60;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {	
				Thread.sleep(milliSecondsElapsed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (delayArrive == 3) {
				try {
					
				delayTime2=random.nextInt(10) + 1;
				System.out.print("\nThe landing of flight "+flight.getFlight_number() +" was delayed by the tower for "+delayTime2+" minutes.\n");
				Thread.sleep(delayTime2 * 1000); 
				status+="Landing delayed "+delayTime2+" minute! ";
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			
			FlightMessage f_completed = new FlightMessage();
			f_completed.setVisible(true);
			String b = "The flight " +flight.getFlight_number()+ " completed!";
			System.out.print("The flight " +flight.getFlight_number()+ " completed!\n");
			f_completed.sMessage(b);
		}
		
		
		if (delayDeparture == 1) {
			FlightMessage f_started = new FlightMessage();
			f_started.setVisible(true);
			String a = "Flight " +flight.getFlight_number()+ " has been canceled!";
			System.out.print("Flight " +flight.getFlight_number()+ " has been canceled!\n");
			f_started.sMessage(a);
			
			FileWriter fw = null;
			try {
				fw = new FileWriter("report.txt",true);
				fw.write("Flight Number: "+flight.getFlight_number()+"\n");
				fw.write("Airlines Name: "+flight.getName_airline()+"\n");
				fw.write("Departure: "+departureCapitalQuery.getName()+"\n");
				fw.write("Arrive : "+arrivalCapitalQuery.getName()+"\n");
				fw.write("Status : Flight cancelled! "+"\n\n\n");
				fw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (delayDeparture != 1) {
			FileWriter fw = null;
			try {
				fw = new FileWriter("report.txt",true);
				fw.write("Flight Number: "+flight.getFlight_number()+"\n");
				fw.write("Airlines Name: "+flight.getName_airline()+"\n");
				fw.write("Departure: "+departureCapitalQuery.getName()+"\n");
				fw.write("Arrive : "+arrivalCapitalQuery.getName()+"\n");
				fw.write("Take Off Time : "+delayDeparture +"\n");
				fw.write("Departure Time : "+flight.getScheduled_departure()+"\n");
				fw.write("Arrival Time : "+flight.getScheduled_arrival()+"\n");
				fw.write("Landing Time : "+delayArrive+"\n");
				fw.write("Status : "+status+"\n\n\n");
				fw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

}
