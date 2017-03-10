/**
 * @author Aleksandr Soloninov
 *
 */
package de.uhd.ifi.feature.metric.calculator.ui;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.*;

import de.uhd.ifi.feature.metric.calculator.ProjectHandler;
import de.uhd.ifi.feature.metric.calculator.calculations.FeatureCalculator;
import de.uhd.ifi.feature.metric.calculator.data.MainUIData;
import de.uhd.ifi.feature.metric.calculator.data.ProviderData;
import de.uhd.ifi.feature.metric.calculator.support.Provider;
import de.uhd.ifi.feature.metric.calculator.support.SingleRootFileSystemView;
import de.uhd.ifi.feature.metric.calculator.support.Writer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
// TODO: Auto-generated Javadoc
/**
 * The Class MainUI.
 */
//Version RC2
public class MainUI extends ViewPart{
	
	/** The Constant ID. */
	public static final String ID = "de.uhd.ifi.feature.metric.calculator.ui.MainUI";
	
	/** The project. */
	private IProject project = ProjectHandler.getCurrentProject();
	
	/** The ui. */
	private MainUIData ui = new MainUIData();
	
	/** The metrics. */
	private FeatureCalculator metrics = new FeatureCalculator();
	
	/** The test metrics. */
	private FeatureCalculator testMetrics = new FeatureCalculator();
	
	/** The src. */
	private ProviderData src = new ProviderData();
	
	/** The test. */
	private ProviderData test = new ProviderData();
	
	/** The src provider. */
	private Provider srcProvider;
	
	/** The test provider. */
	private Provider testProvider;
	
	/** The viewer. */
	private TableViewer viewer;
	
	/** The table. */
	private Table table;
	
	/** The parent. */
	private Composite parent;
	
	/** The path src. */
	private File pathSrc;
	
	/** The path test. */
	private File pathTest;
	
	/** The destination. */
	private File destination ;
	
	/** The csv. */
	private Writer csv;
	
	/** The src item. */
	private TableItem srcItem;
	
	/** The test item. */
	private TableItem testItem;
	
	/** The color src methods. */
	private Color colorSrcMethods;
	
	/** The color src classes. */
	private Color colorSrcClasses;
	
	/** The color test methods. */
	private Color colorTestMethods;
	
	/** The color test classes. */
	private Color colorTestClasses;
	
	/** The color total lines. */
	private Color colorTotalLines;
	
	/** The color backround. */
	private Color colorBackround;
	
	/** The folder name. */
	private String srcFolderName;
	
	/** The test folder name. */
	private String testFolderName;
	
	/** The counter. */
	private int counter ;
	
	/** The time. */
	private int time;
	
	/** The save time. */
	private String saveTime;
	
	/** The src saved path. */
	private String srcSavedPath;
	
	/** The test saved path. */
	private String testSavedPath;
	/**
	 *  (non-Javadoc).
	 *
	 * @param arg0 the arg 0
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl
	 * (org.eclipse.swt.widgets.Composite)
	 */
	@Override
	@SuppressWarnings("PMD.LawOfDemeter")
	public void createPartControl(final Composite arg0) {
		counter = 0;
		parent = arg0;
		viewer = new TableViewer(arg0, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);	
		table =  viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setRedraw(true);
		colorSrcMethods = arg0.getDisplay().getSystemColor(6);
		colorSrcClasses = arg0.getDisplay().getSystemColor(12);
		colorTestMethods = arg0.getDisplay().getSystemColor(8);
		colorTestClasses = arg0.getDisplay().getSystemColor(14);
		colorTotalLines = arg0.getDisplay().getSystemColor(10);
		colorBackround = arg0.getDisplay().getSystemColor(19);
	    if (project == null) { 
	    	initFailure();
	      	actions();
	      	hookContextMenu();
	      	contributeToActionBars();
	    }else{	  
	    	init();
	    }
	}
	
	/**
	 * Inits the.
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	private void init(){
		final Date data = new Date();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_hh.mm");
		final String date = dateFormat.format(data);	
		ui.getEntries().add(new String[] {
			"Date : " + date});
	    createTable();
      	actions();
      	hookContextMenu();
      	contributeToActionBars();
      	
      	try {
			srcSavedPath = new String(Files.readAllBytes(Paths.get(project.getWorkspace().getRoot().getProject(project.getName()).getFolder("pathSrc").getLocation().toString() + ".fmc")));
		} catch (IOException e) {
			e.printStackTrace();
		}
      	try {
			testSavedPath = new String(Files.readAllBytes(Paths.get(project.getWorkspace().getRoot().getProject(project.getName()).getFolder("pathTest").getLocation().toString() + ".fmc")));
		} catch (Exception e) {
			e.printStackTrace();
		}
      	try {
			saveTime = new String(Files.readAllBytes(Paths.get(project.getWorkspace().getRoot().getProject(project.getName()).getFolder("timer_interval").getLocation().toString() + ".fmc")));
		} catch (Exception e) {
			e.printStackTrace();
		}
      	
      	autoRefresh();

      	final boolean flag = Provider.getFlag();
		if (flag == true){
			final String projectPath = project.getWorkspace().getRoot().getProject(project.getName()).getLocation().toString();
			final File root = new File(projectPath);
			final FileSystemView fsv = new SingleRootFileSystemView(root);
			final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.*","*.*");					
			final JFileChooser fileChooser = new JFileChooser(fsv);			
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setFileFilter(filter);		    
		    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    	destination = fileChooser.getSelectedFile();	 
		    }
		}
        if (destination != null){
			pathSrc = destination;
			pathTest = null;			
			srcFolderName = destination.getName();
		} else { 
			if (!(srcSavedPath == null)){
				File swap = new File(srcSavedPath);
				pathSrc = swap;
				srcFolderName = pathSrc.getName();
			}else{
				if(Provider.getSrcPath() == null){
					pathSrc = project.getWorkspace().getRoot().getProject(project.getName()).getFolder("src").getLocation().toFile();
					srcFolderName = "src";
				}else{
					pathSrc = Provider.getSrcPath();
					srcFolderName = Provider.getSrcPath().getName();
				}
			} 
			if (!(testSavedPath == null)){
				File swap = new File(testSavedPath);
				pathTest = swap;
				testFolderName = pathTest.getName();
			}else {
				if(Provider.getTestPath() == null){
					pathTest = project.getWorkspace().getRoot().getProject(project.getName()).getFolder("test").getLocation().toFile();
					testFolderName = "test";
				}else{
					pathTest = Provider.getTestPath();
					testFolderName = Provider.getTestPath().getName();
				}
			}
		}
		src.setClassesList(metrics.getClassesList(pathSrc));
		src.setMethodsList(metrics.getMethodsList(pathSrc));
		test.setClassesList(testMetrics.getClassesList(pathTest));
		test.setMethodsList(testMetrics.getMethodsList(pathTest));	
		srcProvider = new Provider(src.getClassesList(), src.getMethodsList());
		testProvider = new Provider(test.getClassesList(),test.getMethodsList());
		srcProvider.equaliseListSize();		
		testProvider.equaliseListSize();
		src.setTotalClassLines(srcProvider.getTotalClassLines(src.getClassesList()));
		src.setTotalMethodsLines(srcProvider.getTotalMethodsLines(src.getMethodsList()));
		src.setClassesRatio(srcProvider.getRatio(src.getClassesList(),test.getClassesList()));
		src.setMethodsRatio(srcProvider.getRatio(src.getMethodsList(),test.getMethodsList()));
		ui.setMethodsLinesRatio(metrics.getLinesRatio(src.getTotalMethodsLines(), test.getTotalMethodsLines()));
		ui.setClassLinesRatio(metrics.getLinesRatio(src.getTotalClassLines(), test.getTotalClassLines()));	
		test.setTotalClassLines(testProvider.getTotalClassLines(test.getClassesList()));
		test.setTotalMethodsLines(testProvider.getTotalMethodsLines(test.getMethodsList()));
		test.setClassesRatio(testProvider.getRatio(test.getClassesList(),src.getClassesList()));
		test.setMethodsRatio(testProvider.getRatio(test.getMethodsList(),src.getMethodsList()));
		ui.setClassLinesRatio(metrics.getLinesRatio(src.getTotalClassLines(), test.getTotalClassLines()));
		ui.setMethodsLinesRatio(metrics.getLinesRatio(src.getTotalMethodsLines(), test.getTotalMethodsLines()));
		fillSrcData();
        fillTestData();
		csv = new Writer(project, ui.getEntries(),viewer);
		csv.writeStartUp();
		csv.writeHistory();            
	}
	
	public void autoRefresh(){
		if(!(saveTime == null)){
			if (saveTime.equals("0")){
				time = 600000;
			}else {
				System.out.println("timer = " + saveTime);
				time = Integer.parseInt(saveTime);
			}
		} else {
			if (Provider.getTime() == 0){
				time = 600000;
			}else {
				time = Provider.getTime();
			}
		}
		final Runnable timer = new Runnable () {
			@Override
			/**
			 * implements run()
			 */
			public void run () { 
		        IWorkbenchPage workPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		        IViewPart myView = workPage.findView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
		        workPage.hideView(myView);
		        try {
					workPage.showView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
					workPage.hideView(myView);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		};
		parent.getDisplay().timerExec (time, timer);
	}
	
	/**
	 * Inits the failure.
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	private void initFailure(){
		final TableColumn noProjectColumn = new TableColumn(table, SWT.CENTER); 			//1
		noProjectColumn.setWidth(500);
		noProjectColumn.setText(" No Project selected ! ");
		final TableItem[] item = new TableItem[6];
	    for (; counter < 6; counter++) {
	        item[counter] = new TableItem(table, SWT.NONE);
	      } counter = 0;
		item[0].setText(" To use the Feature Metric Calculator please select a Project.");
		item[1].setText("");
		item[2].setText(" --> Java Package Explorer ");
		item[3].setText(" Right mouse click on Project --> Feature Metric Calculator ");
		item[4].setText(" To calculate metrics for src and test folder click --> Run ");
		item[5].setText("To calculate metrics for a folder with a different name click --> Select other folder");
	}
	
	/**
	 * Creates the table.
	 */
	@SuppressWarnings({ "PMD.LawOfDemeter"})
	private void createTable(){
		ui.setProjectNameColumn(new TableColumn(table, SWT.NONE)); 				//0 ProjectName
		ui.setDestinationColumn(new TableColumn(table, SWT.NONE)); 				//1 Destination
		ui.setMethodsNameColumn(new TableColumn(table, SWT.LEFT));      		//2 Methods Name
		ui.setMethodsLinesColumn(new TableColumn(table, SWT.LEFT)); 			//3 Methods Line
		ui.setMethodsRatioColumn(new TableColumn(table, SWT.LEFT));  			//4 Methods Ratio
		ui.setClassesNameColumn(new TableColumn(table, SWT.LEFT));				//6 Classes Name
		ui.setClassesLinesColumn(new TableColumn(table, SWT.LEFT)); 			//7 Classes Line
		ui.setClassesRatioColumn(new TableColumn(table, SWT.LEFT));				//8 Classes Ratio
		ui.getProjectNameColumn().setWidth(90);
		ui.getProjectNameColumn().setText("project");		
		ui.getDestinationColumn().setWidth(75);
		ui.getDestinationColumn().setText("destination");
		ui.getDestinationColumn().setToolTipText("The folder name of the project");
		ui.getMethodsNameColumn().setWidth(300);
		ui.getMethodsNameColumn().setText("method features");
		ui.getMethodsNameColumn().setToolTipText("the names of features implemented in methods");
		ui.getMethodsLinesColumn().setWidth(125);
		ui.getMethodsLinesColumn().setText("lines of code");
		ui.getMethodsRatioColumn().setWidth(225);
		ui.getMethodsRatioColumn().setText("ratio src/test");
		ui.getMethodsRatioColumn().setToolTipText("the ratio between source and test code of the method feature");
		ui.getClassesNameColumn().setWidth(225);
		ui.getClassesNameColumn().setText("class features");
		ui.getClassesNameColumn().setToolTipText("the names of features implemented in classes");
		ui.getClassesLinesColumn().setWidth(125);
		ui.getClassesLinesColumn().setText("lines of code");
		ui.getClassesRatioColumn().setWidth(225);
		ui.getClassesRatioColumn().setText("ratio src/test");
		ui.getClassesRatioColumn().setToolTipText("the ratio between source and test code of the class feature");
	}
	
	/**
	 * Fill src data.
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	private void fillSrcData(){
		for (; counter < src.getClassesList().size(); counter++ ){
			srcItem = new TableItem( table, SWT.NONE); 		
			srcItem.setText( new String[] {
					project.getName(),							
					srcFolderName,				
					src.getMethodsList().get(counter).getName(),		
					Integer.toString(src.getMethodsList().get(counter).getLoc()),			
					src.getMethodsRatio()[counter],
					src.getClassesList().get(counter).getName(),
					Integer.toString(src.getClassesList().get(counter).getLoc()),
					src.getClassesRatio()[counter]});
			ui.getEntries().add(new String[] {
					project.getName(),	
					srcFolderName,
					src.getMethodsList().get(counter).getName(),		
					Integer.toString(src.getMethodsList().get(counter).getLoc()),	
					src.getMethodsRatio()[counter],
					src.getClassesList().get(counter).getName(),
					Integer.toString(src.getClassesList().get(counter).getLoc()),
					src.getClassesRatio()[counter]});	
			int column = 2;
			while(column <= 4){
				srcItem.setForeground(column, colorSrcMethods);
				srcItem.setBackground(column, colorBackround);
				column++;
			}
			while (column < 8){
				srcItem.setForeground(column, colorSrcClasses);
				srcItem.setBackground(column, colorBackround);
				column++;
			}
		} counter = 0;
		final TableItem totalSrcLine = new TableItem(table, SWT.NONE);
		totalSrcLine.setText(new String[] {
			"END OF ", srcFolderName, "Total lines >>>  ", "Methods : " + src.getTotalMethodsLines(),
			srcFolderName +"/"+ testFolderName +" " + ui.getMethodsLinesRatio(), "", "Classes : " + src.getTotalClassLines(), 
			srcFolderName +"/"+ testFolderName +" " + ui.getClassLinesRatio()});
		ui.getEntries().add(new String[] {
			"END OF ", srcFolderName,"Total lines >>>  ","Methods : " + src.getTotalMethodsLines(),
			srcFolderName +"/"+ testFolderName +" "  + ui.getMethodsLinesRatio(), "", "Classes : " + src.getTotalClassLines(), 	
			srcFolderName +"/"+ testFolderName +" " + ui.getClassLinesRatio()});
		for (; counter < 8; counter++){
			totalSrcLine.setForeground(counter, colorTotalLines);
			totalSrcLine.setBackground(counter, colorBackround);
		} counter = 0;
	}
	
	/**
	 * Fill test data.
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	private void fillTestData(){
		for (; counter < test.getClassesList().size(); counter++ ){
			testItem = new TableItem( table, SWT.NONE);
			testItem.setText( new String[] {
				project.getName(),			
				testFolderName,
				test.getMethodsList().get(counter).getName(),		
				Integer.toString(test.getMethodsList().get(counter).getLoc()),		
				test.getMethodsRatio()[counter],	
				test.getClassesList().get(counter).getName(),
				Integer.toString(test.getClassesList().get(counter).getLoc()),		
				test.getClassesRatio()[counter]});
			ui.getEntries().add(new String[] {
					project.getName(),	
					testFolderName,
					test.getMethodsList().get(counter).getName(),		
					Integer.toString(test.getMethodsList().get(counter).getLoc()),	
					test.getMethodsRatio()[counter],
					test.getClassesList().get(counter).getName(),
					Integer.toString(test.getClassesList().get(counter).getLoc()),	
					test.getClassesRatio()[counter],
					Integer.toString(test.getTotalMethodsLines()),
					Integer.toString(test.getTotalClassLines())});		
			int column = 2;
			while(column <= 4){
				testItem.setForeground(column, colorTestMethods);
				testItem.setBackground(column, colorBackround);
				column++;
			}
			while(column < 8){
				testItem.setForeground(column, colorTestClasses);
				testItem.setBackground(column, colorBackround);
				column++;
			} 
		} counter = 0;
		final TableItem totalTestLine = new TableItem(table, SWT.NONE);
		totalTestLine.setText(new String[] {
			"END OF ", testFolderName, "Total lines >>>  ", "Methods : " + test.getTotalMethodsLines(),
			srcFolderName +"/"+ testFolderName + " " + ui.getMethodsLinesRatio(), "", "Classes : " + test.getTotalClassLines(),
			srcFolderName +"/"+ testFolderName + " " + ui.getClassLinesRatio()});
		ui.getEntries().add(new String[]{
			"END OF ", testFolderName, "Total lines >>>  ", "Methods : " + test.getTotalMethodsLines(),
			srcFolderName +"/"+ testFolderName + " " + ui.getMethodsLinesRatio(), "", "Classes : " + test.getTotalClassLines(), 
			srcFolderName +"/"+ testFolderName + " "+ ui.getClassLinesRatio()});
		for (; counter < 8; counter++){
			totalTestLine.setForeground(counter, colorTotalLines);
			totalTestLine.setBackground(counter, colorBackround);
		} counter = 0;
	}

	/**
	 * Hook context menu.
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	private void hookContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			/**
			 * implements menuAboutToShow
			 */
			public void menuAboutToShow(final IMenuManager manager) {
				MainUI.this.fillContextMenu(manager);
			}});
		final Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Contribute to action bars.
	 */
	public void contributeToActionBars() {
		final IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}
	
	/**
	 * Fill context menu.
	 *
	 * @param manager the manager
	 */
	private void fillContextMenu(final IMenuManager manager) {
		manager.add(ui.getRefreshAction());
		manager.add(ui.getDiagram());
		manager.add(ui.getHistory());
		manager.add(ui.getExport());
		manager.add(ui.getSettings());
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	/**
	 * Fill local tool bar.
	 *
	 * @param manager the manager
	 */
	private void fillLocalToolBar(final IToolBarManager manager) {
		manager.add(new ComboContributionItem());
		manager.add(ui.getRefreshAction());
		manager.add(ui.getDiagram());
		manager.add(ui.getHistory());
		manager.add(ui.getExport());
		manager.add(ui.getSettings());
	}

	/**
	 * Actions.
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	private void actions() {
		ui.setRefreshAction(new Action() {
			/**
			 * override run()
			 */
			public void run() {
				final IWorkbenchPage workPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		        final IViewPart myView = workPage.findView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
		        workPage.hideView(myView);
		        try {
					workPage.showView("de.uhd.ifi.feature.metric.calculator.ui.MainUI");
					workPage.hideView(myView);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
		ui.getRefreshAction().setText("Refresh");
		ui.getRefreshAction().setToolTipText("Press to update metrics");
		ui.setExport(new Action() {
			/**
			 * override run
			 */
			public void run() {
				csv.write();
			}
		});	
		ui.getExport().setText("Export to CSV");
		ui.getExport().setToolTipText("Press for Export metrics to a CSV file");
		
		ui.setDiagram(new Action() {
			/**
			 * override run()
			 */
			public void run() {
				final DiagramUI diagram = new DiagramUI(project, parent, srcProvider, testProvider,srcFolderName, testFolderName);
				diagram.init();
			}
		});
		ui.getDiagram().setText("Diagram");
		ui.getDiagram().setToolTipText("Show metrics in a pie chart");
		ui.setHistory(new Action() {
			/**
			 * override run()
			 */
			public void run() {
				final HistoryUI frame = new HistoryUI("Feature Metrics Calculator",project.getProject().getFolder("fmc").getLocation().toString()+ ".history",testFolderName);
				frame.setVisible(true);
				frame.setSize(1400, 612);
			}
		});
		ui.getHistory().setText("History");
		ui.getHistory().setToolTipText("View history of metrics");
		
		ui.setSettings(new Action() {
			@Override
			public void run() {
				SettingsUI setting = new SettingsUI(project, parent);
				setting.init();
			}
		});
		ui.getSettings().setText("Settings");
		ui.getSettings().setToolTipText("Press to entry in Setting");
		
		
	}

	/**
	 *  (non-Javadoc).
	 *
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	public void setFocus() {
		 viewer.getControl().setFocus();
	}
	
	/**
	 * The Class ComboContributionItem.
	 */
	public class ComboContributionItem extends ControlContribution {
	    
    	/**
    	 * Instantiates a new combo contribution item.
    	 */
    	public ComboContributionItem() {
	        super("comboID");
	    }
	    
    	/**
	     *  (non-Javadoc).
	     *
	     * @param parent the parent
	     * @return the control
	     * @see org.eclipse.jface.action.ControlContribution#createControl
	     * (org.eclipse.swt.widgets.Composite)
	     */
    	@Override
	    public Control createControl(final Composite parent) {
	        final Combo combo = new Combo(parent, SWT.NONE);
	        combo.setItems(new String[] {"all","src","test"});
	        combo.select(0);
	        combo.setToolTipText("View only metrics for source code, test code or for both");
	        combo.addSelectionListener(new SelectionAdapter() {
	        	/**
	        	 * override widgetSelected
	        	 */
	            @SuppressWarnings("PMD.LawOfDemeter")
				public void widgetSelected(final SelectionEvent event) {
	              if (combo.getText().equals("all")) {
	            	  table.removeAll();
	            	  fillSrcData();
	            	  fillTestData();
	              }
	              if (combo.getText().equals("src")){
	            	  table.removeAll();
	            	  fillSrcData();
	              }
	              if (combo.getText().equals("test")){
	            	  table.removeAll();
	            	  fillTestData();
	              }
	            }
	        });return combo;
	    }
	}
}