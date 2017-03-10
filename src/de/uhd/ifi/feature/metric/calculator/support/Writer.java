package de.uhd.ifi.feature.metric.calculator.support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import com.opencsv.CSVWriter;

/**
 * The Class Writer. Final version 1.0.3
 * Write CSV and History
 * @author Aleksandr Soloninov
 */
public class Writer {

	/** The project. */
	final private IProject project;
	
	/** The entries. */
	private List<String[]> entries = new ArrayList<>();
	
	/** The viewer. */
	private TableViewer viewer;
	/**
	 * Instantiates a new writer.
	 * 
	 * @param _project the project
	 * @param _entries the entries
	 * @param _viewer the viewer
	 */
	public Writer(final IProject _project, final List<String[]> _entries, final TableViewer _viewer) {
		project = _project;   // for Name and Destination
		entries = _entries;	  // List with data
		viewer = _viewer;	  // shell
	}
	
	/**
	 * Write.
	 */
	public void write(){
		final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv","*.*");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
			try (CSVWriter writer = new CSVWriter(new FileWriter(fileChooser.getSelectedFile()+".csv"))) {
				writer.writeAll(entries);
				entries.clear();
		        showMessage("CSV export is done");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}}
	/**
	 * Write history.
	 */
	public void writeHistory(){
		final File file = new File(project.getProject().getFolder("fmc")
									.getLocation().toString()+ ".history");
	     try (CSVWriter writer = new CSVWriter(new FileWriter(file,true))) {
	            writer.writeAll(entries);
	        } catch (IOException e) {
				e.printStackTrace();
				showMessage("History is failed");
			}
	}

	/**
	 * Write start up.
	 */
	public void writeStartUp(){
		try {
			Files.createDirectory(Paths.get(project.getProject().getFolder("fmc")
															.getLocation().toString()));
			Files.createDirectory(Paths.get(project.getProject()
															.getFolder("fmc").getFolder("csv")
															.getLocation().toString()));
			Files.createDirectory(Paths.get(project.getProject()
															.getFolder("fmc").getFolder("csv")
															.getFolder("data").getLocation().toString()));
		} catch (IOException e1) {
			
		} 
		final Date date = new Date();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_hh.mm");
		final String data = dateFormat.format(date);
		System.out.println(data);
		final File file = new File(project.getProject().getFolder("fmc").getFolder("cvs").getLocation().toString() + data + ".csv");
	     try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
	            writer.writeAll(entries);
	        } catch (IOException e) {
				e.printStackTrace();
				showMessage("CSV export is Failed");
			}}
	/**
	 * Show message.
	 *
	 * @param message the message
	 */
	private void showMessage(final String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Feature Metrics Calculator",message);
	}
	
}
