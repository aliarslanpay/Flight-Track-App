import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class FlightList {

	public FlightList() throws Exception {
		initialize();
	}

	
	private void initialize() throws Exception {
		String[] column = {"#", "Airline Name", "Flight Number", "Aircraft Model", "Departure City", "Arrival City", "Scheduled Departure Time", "Scheduled Arrival Time"};
		
		Flight flight= new Flight();
		Capital capital=new Capital();
		
		Object[][] data = new Object[flight.getList().size()][8];
		int i = 0;
		for (Flight s : flight.getList()) {
			 Capital query_for_arrival =capital.findById(s.getArrival_id());
			 Capital query_for_departure = capital.findById(s.getDeparture_id());
		
			data[i][0] = s.getId();
			data[i][1] = s.getName_airline();
			data[i][2] = s.getFlight_number();
			data[i][3] = s.getAircraftModel();
			data[i][4] = query_for_departure.getName();
			data[i][5] = query_for_arrival.getName();
			data[i][6] = s.getScheduled_departure();
			data[i][7] = s.getScheduled_arrival();
			i++;
		}
			
		    
		    flightListFrame = new JFrame();
		    flightListFrame.setTitle("Flight List");
			flightListFrame.setBounds(100, 100, 1250, 300);
			flightListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			flightListFrame.setLocation(new java.awt.Point(100, 400));
			
			JScrollPane scrollPane = new JScrollPane();
			flightListFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
			@SuppressWarnings("serial")
			DefaultTableModel tableModel = new DefaultTableModel(data, column){
			      public boolean isCellEditable(int rowIndex, int mColIndex) {
			          return false;
			        }
			      };
			JTable flightListTable = new JTable(tableModel);
			flightListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			flightListTable.setFillsViewportHeight(true);
			
			TableColumnModel colModel=flightListTable.getColumnModel();
			colModel.getColumn(0).setPreferredWidth(10);
			colModel.getColumn(2).setPreferredWidth(15);
			
			flightListTable.getTableHeader().setReorderingAllowed(false);
			flightListTable.setPreferredScrollableViewportSize(new Dimension(150,96));
			flightListTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Flight flight=new Flight();
					int row = flightListTable.getSelectedRow();
					int id = (int) flightListTable.getValueAt(row, 0);
					String flightNumber=(String) flightListTable.getValueAt(row, 2);
					int dialogResult = JOptionPane.showConfirmDialog (null, "Confirm to delete flight " +flightNumber+ "?","Warning",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
							tableModel.removeRow(row);
							try {
								List<Flight> flightList = flight.getList();
								flight.allRecordDelete();
								for (Flight f : flightList) {
									if (id != f.getId()) {
										Flight s = new Flight();
										s.setId(f.getId());
										s.setName_airline(f.getName_airline());
										s.setFlight_number(f.getFlight_number());
										s.setAircraftModel(f.getAircraftModel());
										s.setDeparture_id(f.getDeparture_id());
										s.setArrival_id(f.getArrival_id());
										s.setScheduled_departure(f.getScheduled_departure());
										s.setScheduled_arrival(f.getScheduled_arrival());
										s.updateFlight();
									}
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					   }}
			});
			scrollPane.setViewportView(flightListTable);
			flightListFrame.setVisible(true);
	}
	
	private JFrame flightListFrame;
}
