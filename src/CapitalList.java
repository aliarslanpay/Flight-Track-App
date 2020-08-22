import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CapitalList {

	public CapitalList() throws FileNotFoundException {
		initComponents();
	}

	
	private void initComponents() throws FileNotFoundException {
		
		String[] columns = {"ID", "Name"};
		Capital capital = new Capital();
		Object[][] data = new Object[capital.getList().size()][2];
		
		int i = 0;
		for (Capital s : capital.getList()) {
			data[i][0] = s.getId();
			data[i][1] = s.getName();
			i++;
		}
		
		capitalListFrame = new JFrame();
		capitalListFrame.setTitle("Capital List");
		capitalListFrame.setBounds(100, 100, 450, 300);
		capitalListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		capitalListFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		DefaultTableModel tableModel = new DefaultTableModel(data, columns){
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		JTable capitalListTable = new JTable(tableModel);
		capitalListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		capitalListTable.setFillsViewportHeight(true);
		capitalListTable.getTableHeader().setReorderingAllowed(false);
		capitalListTable.setPreferredScrollableViewportSize(new Dimension(150, 96));
		
		capitalListTable.addMouseListener(new MouseAdapter() {
		@Override
			public void mouseClicked(MouseEvent e) {
				int row = capitalListTable.getSelectedRow();
				int id = (int) capitalListTable.getValueAt(row, 0);
				String name = (String) capitalListTable.getValueAt(row, 1);
				int dialogResult = JOptionPane.showConfirmDialog(null,"Confirm to delete " + name + "?","Delete Capital", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					tableModel.removeRow(row);
					try {
						List<Capital> capitalList = capital.getList();
						capital.allRecordDelete();
						for (Capital capital : capitalList) {
							if (id != capital.getId()) {
								Capital c = new Capital();
								c.setId(capital.getId());
								c.setName(capital.getName());
								c.updateCapital();
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		scrollPane.setViewportView(capitalListTable);
		capitalListFrame.setVisible(true);
	}

	private JFrame capitalListFrame;
}
