package de.uhd.ifi.feature.metric.calculator.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.*;
import java.net.URL;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

	/**
	 * The Class HistoryUI. Final version 
	 * @author Aleksandr Soloninov
	 */
	@SuppressWarnings("serial")
	public class HistoryUI extends JFrame {
		
		/** The table. */
		private JTable table;
		
		/** The model. */
		private DefaultTableModel model;
		
		/** The test folder name. */
		private String testFolderName;
		
		/**
		 * Takes data from a CSV file and places it into a table for display.
		 *
		 * @param title the title
		 * @param source - a reference to the file where the CSV data is located.
		 * @param _testFolderName the test folder name
		 */
	public HistoryUI(final String title, final String source, String _testFolderName) {
		super(title);
		testFolderName = _testFolderName;
		table = new JTable();
		table.setFont(new Font("Serif", Font.PLAIN, 14));
		final JScrollPane scroll = new JScrollPane(table);
		final String[] colNames = { "Project Name", "Destination", "Methods feature name", "Methods lines", "Methods Ratio" ,"Classes feature name","Classes lines" ,"Classe ratio"};
		model = new DefaultTableModel(colNames, 0);
		InputStream inputStream;
		try {
			if(source.indexOf("http")==0) {
				final URL facultyURL = new URL(source);
				inputStream = facultyURL.openStream();
			}
			else {
				final File file = new File(source);
				inputStream = new FileInputStream(file);
			} insertData(inputStream);
		}
		catch(IOException ioe) {
			JOptionPane.showMessageDialog(this, ioe, "Error reading data", JOptionPane.ERROR_MESSAGE);
		}
		final JPanel notesPanel = new JPanel();
		final BoxLayout layout = new BoxLayout(notesPanel, BoxLayout.Y_AXIS);
		notesPanel.setLayout(layout);     
		getContentPane().add(notesPanel, BorderLayout.NORTH);
		getContentPane().add(scroll, BorderLayout.CENTER);
	}
	
	/**
	 * Insert data.
	 *
	 * @param inputStream the input stream
	 */
	
	void insertData(final InputStream inputStream) {
		@SuppressWarnings("resource")
		final Scanner scan = new Scanner(inputStream);
		String[] array;
		while (scan.hasNextLine()) {
			final String line = scan.nextLine();
			if(line.indexOf(",")>-1){
				array = line.split(",");
			}
			else{
				array = line.split("\t");
			}
			Object[] data = new Object[array.length];
			for (int i = 0; i < array.length; i++){
				
				if ((i==2 || i ==5) && array[i].length()>2 && !array[i].equals("\"Total lines >>>  \"")){
					data[i] = array[i].substring(3, array[i].length()-3);	
				}
				else{
					data[i] = array[i].substring(1, array[i].length()-1);
				}
					
			}
			
			model.addRow(data);
			final String[] emptyLine = {""};
			if (data[0].equals("END OF ") && data[1].equals(testFolderName)){
				model.addRow(emptyLine);
			}
		}
		table.setModel(model);
	} 
}