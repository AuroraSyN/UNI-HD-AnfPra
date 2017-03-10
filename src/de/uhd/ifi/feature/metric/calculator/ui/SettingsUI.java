/**
 * @author Aleksandr Soloninov
 *
 */
package de.uhd.ifi.feature.metric.calculator.ui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.uhd.ifi.feature.metric.calculator.support.Provider;
import de.uhd.ifi.feature.metric.calculator.support.SingleRootFileSystemView;
import de.uhd.ifi.feature.metric.calculator.ProjectHandler;

// 
/**
 * The Class SettingsUI.
 */
public class SettingsUI {
    
    /** The shell. */
    private Shell shell;
	
	/** The project. */
	private IProject project = ProjectHandler.getCurrentProject();
	
	/** The viewer. */
	
	/** The parent. */
	private Composite parent;
	
	/**
	 * Instantiates a new settings UI.
	 *
	 * @param _viewer the viewer
	 * @param _project the project
	 * @param _parent the parent
	 */
	public SettingsUI(IProject _project, Composite _parent){
		project = _project;
		parent = _parent;
	}
	/**
	 * Inits the.
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	public void init(){
        shell = new Shell(parent.getDisplay(), SWT.CENTER | SWT.SHELL_TRIM & (~SWT.RESIZE));
        shell.setSize(600, 180);
        shell.setText("Feature Metrics Calculator - Settings");
        shell.setLayout(new GridLayout(3,false));
        
        final Label srcLabel = new Label(shell, SWT.NONE);
        srcLabel.setText(" Set path for 'source' folder ");
        final Button srcButton = new Button(shell, SWT.PUSH);
        srcButton.setText("Browse 'src' ... ");
        srcButton.addSelectionListener(new SelectionAdapter() {
            @Override
            @SuppressWarnings("PMD.LawOfDemeter")
			public void widgetSelected(SelectionEvent e) {
            	final String projectPath;
            	if (!(project == null)){
            		projectPath = project.getWorkspace().getRoot().getProject(project.getName()).getLocation().toString();
            	}else {
            		projectPath = "";
            	}
    			final File root = new File(projectPath);
    			final FileSystemView fsv = new SingleRootFileSystemView(root);
    			@SuppressWarnings("PMD.AvoidDuplicateLiterals")
				final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.*","*.*");					
    			final JFileChooser fileChooser = new JFileChooser(fsv);			
    			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    			fileChooser.setFileFilter(filter);		    
    		    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		    	try {
        		    	Provider.writeSrcPath(fileChooser.getSelectedFile());
        		    	try (PrintStream out = new PrintStream(new FileOutputStream(project.getWorkspace().getRoot()
        		    							.getProject(project.getName()).getFolder("pathSrc").getLocation().toString() + ".fmc"))) {
        		    	    out.print(fileChooser.getSelectedFile().toString());
        		    	}
        		    	showMessage("'src' path change was successfully, please press Apply button");
        		    	shell.open();
					} catch (Exception e2) {
						e2.printStackTrace();
        		    	showMessage("'src' path change was fail");
					}

    		    }
            }
        }); @SuppressWarnings("unused")
		final Label srcEndLabel = new Label(shell, SWT.NONE);
        
        final Label testLabel = new Label(shell, SWT.NONE);
        testLabel.setText(" Set path for 'test' folder ");
        final Button testButton = new Button(shell, SWT.PUSH);
        testButton.setText("Browse 'test' ... ");
        testButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	final String projectPath = project.getWorkspace().getRoot().getProject(project.getName()).getLocation().toString();
    			final File root = new File(projectPath);
    			final FileSystemView fsv = new SingleRootFileSystemView(root);
    			final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.*","*.*");					
    			final JFileChooser fileChooser = new JFileChooser(fsv);			
    			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    			fileChooser.setFileFilter(filter);		    
    		    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		    	try {
        		    	Provider.writeTestPath(fileChooser.getSelectedFile());
        		    	try (PrintStream out = new PrintStream(new FileOutputStream(project.getWorkspace().getRoot()
        		    						.getProject(project.getName()).getFolder("pathTest").getLocation().toString() + ".fmc"))) {
        		    	    out.print(fileChooser.getSelectedFile().toString());
        		    	}
        		    	showMessage("'test' path change was successfully, please press Apply button");
        		    	shell.open();
					} catch (Exception e2) {
						e2.printStackTrace();
        		    	showMessage("'test' path change was fail");
					}
    		    }
            }
        });         final Label testEndLabel = new Label(shell, SWT.NONE);
        
        Label textLabel = new Label(shell, SWT.NONE);
        textLabel.setText("Set saveintervall between 1-200 MIN: ");
        Text text = new Text(shell, SWT.NONE);
        text.setBounds(300, 20, 5, 5);
        text.setText("000");
        text.setTextLimit(3);
        text.addListener(SWT.Verify, new Listener() {
        	   public void handleEvent(Event e) {
        	      String string = e.text;
        	      char[] chars = new char[string.length()];
        	      string.getChars(0, chars.length, chars, 0);
        	      for (int i = 0; i < chars.length; i++) {
        	         if (!('0' <= chars[i] && chars[i] <= '9')) {
        	            e.doit = false;
        	            return;
        	         }
        	      }
        	   }
        	});
        Label textEndLabel = new Label(shell, SWT.NONE);

        final Label voidLabel = new Label(shell, SWT.NONE);
        voidLabel.setText("Press 'Apply' for refresh new paths ");
        final Button applyButton = new Button(shell, SWT.PUSH);
        applyButton.setText("Apply");
        applyButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                final IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                final IViewPart myView = workbenchPage.findView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
                workbenchPage.hideView(myView);
                try {
            		int swap = Integer.parseInt(text.getText());
            		if (swap < 201){
            			swap = swap*1000*60;
            			Provider.writeTime(swap);
            			try (PrintStream out = new PrintStream(new FileOutputStream(project.getWorkspace().getRoot()
            							.getProject(project.getName()).getFolder("timer_interval").getLocation().toString() + ".fmc"))) {
            				out.print(swap);
            			} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}	
            			workbenchPage.showView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
            			workbenchPage.hideView(myView);
            			shell.dispose();
            		}else {
            			MessageDialog.openInformation(shell, "Feature Metrics Calculator", "Save interval is to big, set to default (10 min)");
            			workbenchPage.showView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
            			workbenchPage.hideView(myView);
            			Provider.writeTime(0);      
            			shell.dispose();
            		}
        		} catch (PartInitException s) {
        			s.printStackTrace();
        		}
            } 
        });
        final Button closeButton = new Button(shell, SWT.PUSH);
        closeButton.setText(" Cancel ...");
        closeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	try {
    		    	shell.dispose();;
				} catch (Exception e2) {
					e2.printStackTrace();
				}

            }
        });
        shell.open();
    }
	/**
	 * Show message.
	 *
	 * @param message the message
	 */
	private void showMessage(final String message) {
		MessageDialog.openInformation(parent.getShell(), "Feature Metrics Calculator",message);
	}
}
