import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flight {
	private int id;
	private String name_airline;
	private String flight_number;
	private String aircraftModel;
	private int departure_id;
	private int arrival_id;
	private String scheduled_departure;
	private String scheduled_arrival;
	
	public int getLastId() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream("flight.txt"));
		String[] lastLine = null;
		int i = 0;
		
		while (scanner.hasNextLine()) {
			lastLine = scanner.nextLine().split("/");
			i++;
		}
		scanner.close();
		
		if (i == 0) {
			return 0;
		}
		else {
			return Integer.parseInt(lastLine[0]);
		}
	}
	
	public boolean addFlight() throws IOException {
		FileWriter writer = new FileWriter("flight.txt", true);
		
		writer.write((getLastId() + 1) + "/" 
				+ getName_airline() + "/"
				+ getFlight_number() + "/"
				+ getAircraftModel() + "/"
				+ getDeparture_id()+ "/" 
				+ getArrival_id() + "/" 
				+ getScheduled_departure() + "/"
				+ getScheduled_arrival() + "/");
				writer.write("\n");
				writer.close();
				
				return true;
	}
	
	public boolean updateFlight() throws IOException {
		FileWriter writer = new FileWriter("flight.txt", true);
		
		writer.write((getId()) + "/" 
				+ getName_airline() + "/"
				+ getFlight_number() + "/"
				+ getAircraftModel() + "/"
				+ getDeparture_id()+ "/" 
				+ getArrival_id() + "/"
				+ getScheduled_departure() + "/"
				+ getScheduled_arrival() + "/");
				writer.write("\n");
				writer.close();
				
				return true;
	}
	
	public List<Flight> getList() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream("flight.txt"));
		List<Flight> list = new ArrayList<Flight>();
		String[] item = null;
		while (scanner.hasNextLine()) {
			item = scanner.nextLine().split("/");
			Flight flight = new Flight();
			flight.setId(Integer.parseInt(item[0]));
			flight.setName_airline(item[1]);
			flight.setFlight_number(item[2]);
			flight.setAircraftModel(item[3]);
			flight.setDeparture_id(Integer.parseInt(item[4]));
			flight.setArrival_id(Integer.parseInt(item[5]));
			flight.setScheduled_departure(item[6]);
			flight.setScheduled_arrival(item[7]);

			list.add(flight);
		}
		scanner.close();

		return list;
	}
	
	public void allRecordDelete() throws IOException {
		FileWriter writer = new FileWriter("flight.txt");
		writer.write("");
		writer.close();
	}
	
	public List<Flight> findByDepartureTime(String time) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream("flight.txt"));
		List<Flight> list = new ArrayList<Flight>();
		String[] item = null;
		
		while (scanner.hasNextLine()) {
			item = scanner.nextLine().split("/");
			if (item[6].equals(time)) {
				Flight flight = new Flight();
				flight.setId(Integer.parseInt(item[0]));
				flight.setName_airline(item[1]);
				flight.setFlight_number(item[2]);
				flight.setAircraftModel(item[3]);
				flight.setDeparture_id(Integer.parseInt(item[4]));
				flight.setArrival_id(Integer.parseInt(item[5]));
				flight.setScheduled_departure(item[6]);
				flight.setScheduled_arrival(item[7]);
				list.add(flight);
			}
		}
		scanner.close();

		return list;

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFlight_number() {
		return flight_number;
	}
	public void setFlight_number(String flight_number) {
		this.flight_number = flight_number;
	}
	public String getName_airline() {
		return name_airline;
	}
	public void setName_airline(String name_airline) {
		this.name_airline = name_airline;
	}
	public String getAircraftModel() {
		return aircraftModel;
	}
	public void setAircraftModel(String aircraftModel) {
		this.aircraftModel = aircraftModel;
	}
	public int getDeparture_id() {
		return departure_id;
	}
	public void setDeparture_id(int departure_id) {
		this.departure_id = departure_id;
	}
	public int getArrival_id() {
		return arrival_id;
	}
	public void setArrival_id(int arrival_id) {
		this.arrival_id = arrival_id;
	}
	public String getScheduled_departure() {
		return scheduled_departure;
	}
	public void setScheduled_departure(String scheduled_departure) {
		this.scheduled_departure = scheduled_departure;
	}
	public String getScheduled_arrival() {
		return scheduled_arrival;
	}
	public void setScheduled_arrival(String scheduled_arrival) {
		this.scheduled_arrival = scheduled_arrival;
	}
}