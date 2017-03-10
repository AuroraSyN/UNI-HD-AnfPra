package de.uhd.ifi.feature.metric.calculator.ui;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.Rotation;

import de.uhd.ifi.feature.metric.calculator.calculations.FeatureCalculator;
import de.uhd.ifi.feature.metric.calculator.data.Feature;
import de.uhd.ifi.feature.metric.calculator.support.Provider;

/**
 * The Class DiagramUI. Final version 1.4.18
 * @author Aleksandr Soloninov
 */
public class DiagramUI extends JFrame{
	
	/** The metrics. */
	private FeatureCalculator metrics = new FeatureCalculator();
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The project. */
	private IProject project;
	
	/** The parent. */
	private Composite parent;
    
    /** The shell. */
    private Shell shell;
    
    /** The test methods frame. */
    private ChartComposite testMethodsFrame;
    
    /** The test class frame. */
    private ChartComposite testClassFrame;
    
    /** The src methods frame. */
    private ChartComposite srcMethodsFrame;
    
    /** The src class frame. */
    private ChartComposite srcClassFrame;  
    
    /** The src left. */
    private Composite srcLeft;
    
    /** The src right. */
    private Composite srcRight;
    
    /** The src LB. */
    private Composite srcLB;
    
    /** The src RB. */
    private Composite srcRB;
    
    /** The test left. */
    private Composite testLeft;
    
    /** The test right. */
    private Composite testRight;
    
    /** The test LB. */
    private Composite testLB;
    
    /** The test RB. */
    private Composite testRB;  
	
	/** The src methods data. */
    private PieDataset srcMethodsData;
	
	/** The src class data. */
    private PieDataset srcClassData;
    
    /** The test methods data. */
    private PieDataset testMethodsData;
	
	/** The test class data. */
    private PieDataset testClassData;	
	
    /** The src methods chart. */
    private JFreeChart srcMethodsChart;
	
	/** The src class chart. */
    private JFreeChart srcClassChart;
	
	/** The test methods chart. */
    private JFreeChart testMethodsChart;
	
	/** The test class chart. */
    private JFreeChart testClassChart;
	
	/** The folder name. */
    private String srcFolderName;
    
    /** The test folder name. */
    private String testFolderName;
	
	/** The src classes list. */
	private List<Feature> srcClassesList = new ArrayList<Feature>();
	
	/** The src methods list. */
	private List<Feature> srcMethodsList = new ArrayList<Feature>();
	
	/** The test classes list. */
	private List<Feature> testClassesList = new ArrayList<Feature>();
	
	/** The test methods list. */
	private List<Feature> testMethodsList = new ArrayList<Feature>();
	
	/** The src methods ratio. */
	private String[] srcMethodsRatio;
	
	/** The src classes ratio. */
	private String[] srcClassesRatio;
	
	/** The test methods ratio. */
	private String[] testMethodsRatio;
	
	/** The test classes ratio. */
	private String[] testClassesRatio;
	
	/** The class lines ratio. */
	private String classLinesRatio;
	
	/** The methods lines ratio. */
	private String methodsLinesRatio;
	
	/** The src. */
	private Provider src;
	
	/** The test. */
	private Provider test;

    /**
     * Instantiates a new diagram UI.
     *
     * @param _project the project
     * @param _parent the parent
     * @param _src the src
     * @param _test the test
     * @param _srcFolderName the src folder name
     * @param _testFolderName the test folder name
     */
    public DiagramUI(final IProject _project, final Composite _parent, final Provider _src, final Provider _test, final String _srcFolderName, final String _testFolderName) {
    	super("Feature Metrics Calculate");
		project = _project;						// for project name and destination
		parent = _parent;						// for shell
		srcFolderName = _srcFolderName; 		// src folder name for labels and etc.
		testFolderName = _testFolderName;		// test folder name for labels and etc.
		src = _src;								// src provider with data
		test = _test;							// test provider with data
		
		srcClassesList = src.getListClasses();  
		srcMethodsList = src.getListMethods();
		testClassesList = test.getListClasses();
		testMethodsList = test.getListMethods();
		srcMethodsRatio = src.getRatio(srcMethodsList, testMethodsList);
		srcClassesRatio = src.getRatio(srcClassesList, testClassesList);
		testMethodsRatio = test.getRatio(testMethodsList, srcMethodsList);
		testClassesRatio = test.getRatio(testClassesList, srcClassesList);	
		classLinesRatio = metrics.getLinesRatio(metrics.getTotalLines(srcClassesList), metrics.getTotalLines(testClassesList));
		methodsLinesRatio = metrics.getLinesRatio(metrics.getTotalLines(srcMethodsList), metrics.getTotalLines(testMethodsList));
    }
    
    /**
     * initialize Diagram.
     */
    public void init(){	
        shell = new Shell(parent.getDisplay(),SWT.SHELL_TRIM & (~SWT.RESIZE));
        shell.setLayout(new GridLayout(2,false));
        shell.setText("Feature Metric Calculate Diagram");
        // src / & block
        srcLeft = new Composite(shell, SWT.BORDER);
        srcRight = new Composite(shell, SWT.BORDER);
        srcLB = new Composite(shell, SWT.BOTTOM); 		// src block leftButton
        srcRB = new Composite(shell, SWT.BOTTOM);		// src block rightButton
    	srcMethodsData = createSrcMethodsSet(); 
    	srcClassData = createSrcClassesSet(); 
    	srcInit();
    	//test Block
        testLeft = new Composite(shell, SWT.BORDER); 
        testRight = new Composite(shell, SWT.BORDER);
        testLB = new Composite(shell, SWT.BOTTOM);		// test block leftButton
        testRB = new Composite(shell, SWT.BOTTOM);		// test block rightButton
        testMethodsData = createTestMethodsSset();
    	testClassData = createTestClassesSet();
    	testInit();
    	//shell
        setButtons();
        shell.layout();
        shell.pack();
        shell.open();
    }

    /**
     * Src frame arguments.
     */
    public void srcFrameArguments(){
        srcMethodsFrame.setDisplayToolTips(true);
        srcMethodsFrame.setHorizontalAxisTrace(false);
        srcMethodsFrame.setVerticalAxisTrace(false);
        srcMethodsFrame.setSize(650,400);
        srcClassFrame.setDisplayToolTips(true);
        srcClassFrame.setHorizontalAxisTrace(false);
        srcClassFrame.setVerticalAxisTrace(false);
        srcClassFrame.setSize(650,400);	
    }

    /**
     * Test frame arguments.
     */
    public void testFrameArguments(){
        testMethodsFrame.setDisplayToolTips(true);
        testMethodsFrame.setHorizontalAxisTrace(false);
        testMethodsFrame.setVerticalAxisTrace(false);
        testMethodsFrame.setSize(650,400);
        testClassFrame.setDisplayToolTips(true);
        testClassFrame.setHorizontalAxisTrace(false);
        testClassFrame.setVerticalAxisTrace(false);
        testClassFrame.setSize(650,400);	
    }
    
    /**
     * Src pie init.
     */
    public void srcInit(){
    	TextTitle legendText = new TextTitle("Total lines = " + Integer.toString(metrics.getTotalLines(srcMethodsList))+ " | Total ratio " + methodsLinesRatio);
    	legendText.setPosition(RectangleEdge.BOTTOM);
    	srcLeft.setLayoutData(srcMethodsFrame);
        srcRight.setLayoutData(srcClassFrame); 
        srcMethodsChart = createPieChart(srcMethodsData, "Methods feature metrics from " + srcFolderName + " folder in  "  + project.getName());
        srcMethodsChart.addSubtitle(legendText);
    	srcClassChart = createPieChart(srcClassData, "Classes feature metrics from " + srcFolderName + " folder in  " + project.getName());
    	legendText = new TextTitle("Total lines = " + Integer.toString(metrics.getTotalLines(srcClassesList)) + " | Total ratio " + classLinesRatio);
    	legendText.setPosition(RectangleEdge.BOTTOM);
    	srcClassChart.addSubtitle(legendText);
        srcMethodsFrame = new ChartComposite(srcLeft, SWT.NONE, srcMethodsChart, true);
        srcClassFrame = new ChartComposite(srcRight, SWT.NONE, srcClassChart, true);
        srcClassFrame.setCursor(new Cursor(srcClassFrame.getDisplay(), SWT.CURSOR_HAND));
        srcFrameArguments();
        srcMethodsFrame.addSWTListener(new MouseListener() {
            public void mouseDoubleClick(final MouseEvent e) {
            	final Shell shell = new Shell(parent.getDisplay(), SWT.SHELL_TRIM & (~SWT.RESIZE));
         	    shell.setSize(1400, 950);
         	    shell.setText("Methods feature metrics from " + srcFolderName + " folder in  "  + project.getName());
         	    shell.setLayout(new FillLayout());
                srcMethodsFrame = new ChartComposite(shell, SWT.NONE, srcMethodsChart, true);
         	    shell.open();
            }
            
            public void mouseDown(final MouseEvent e) {          
            }

            public void mouseUp(final MouseEvent e) {
            }
            
         });
        
        srcClassFrame.addSWTListener(new MouseListener() {
           public void mouseDoubleClick(final MouseEvent e) {
        	   	final Shell shell = new Shell(parent.getDisplay(), SWT.SHELL_TRIM & (~SWT.RESIZE));
        	    shell.setSize(1400, 950);
        	    shell.setText("Classes feature metrics from " + srcFolderName + " folder in  "  + project.getName());
        	    shell.setLayout(new FillLayout());
                srcClassFrame = new ChartComposite(shell, SWT.NONE, srcClassChart, true);
        	    shell.open();
           }
           
           public void mouseDown(MouseEvent e) {        
           }

           public void mouseUp(MouseEvent e) {
           }
           
        });
    }

    /**
     * Test init.
     */
    public void testInit(){
    	TextTitle legendText = new TextTitle("Total lines = " + Integer.toString(metrics.getTotalLines(testMethodsList))+ " | Total ratio " + methodsLinesRatio);
    	legendText.setPosition(RectangleEdge.BOTTOM);
        testLeft.setLayoutData(testMethodsFrame);
        testRight.setLayoutData(testClassFrame);
    	testMethodsChart = createPieChart(testMethodsData, "Methods feature metrics from " + testFolderName + " folder in  " + project.getName());
    	testMethodsChart.addSubtitle(legendText);
    	testClassChart = createPieChart(testClassData, "Classes feature metrics from " + testFolderName + " folder in  " + project.getName());
    	legendText = new TextTitle("Total lines = " + Integer.toString(metrics.getTotalLines(testClassesList)) + " | Total ratio " + classLinesRatio);
    	legendText.setPosition(RectangleEdge.BOTTOM);
    	testClassChart.addSubtitle(legendText);
        testMethodsFrame = new ChartComposite(testLeft, SWT.NONE, testMethodsChart, true);
        testClassFrame = new ChartComposite(testRight, SWT.NONE, testClassChart, true);
        testFrameArguments();
        testMethodsFrame.addSWTListener(new MouseListener() {
            public void mouseDoubleClick(final MouseEvent e) {
         	   	final Shell shell = new Shell(parent.getDisplay(), SWT.SHELL_TRIM & (~SWT.RESIZE));
         	    shell.setSize(1400, 950);
         	    shell.setText("Methods feature metrics from " + testFolderName + " folder in  "  + project.getName());
         	    shell.setLayout(new FillLayout());
                testMethodsFrame = new ChartComposite(shell, SWT.NONE, testMethodsChart, true);
         	    shell.open();
            }
            
            public void mouseDown(MouseEvent e) {
        
            }

            public void mouseUp(MouseEvent e) {

            }
            
         });
        
        testClassFrame.addSWTListener(new MouseListener() {
           public void mouseDoubleClick(final MouseEvent e) {
        	   	final Shell shell = new Shell(parent.getDisplay(), SWT.SHELL_TRIM & (~SWT.RESIZE));
        	    shell.setSize(1400, 950);
        	    shell.setText("Classes feature metrics from " + srcFolderName + " folder in  "  + project.getName());
        	    shell.setLayout(new FillLayout());
                srcClassFrame = new ChartComposite(shell, SWT.NONE, testClassChart, true);
        	    shell.open();
           }
           
           public void mouseDown(final MouseEvent e) {        
           }

           public void mouseUp(final MouseEvent e) {
           }
           
        });
    }  
    /**
     * Gets the buttons.
     *
     * @return the buttons
     */
    public void setButtons(){
    	final Button srcMethodsButton = new Button(srcLB, SWT.PUSH);
     	srcMethodsButton.setText("save " + srcFolderName + " methods diagram of : " + project.getName());
     	srcMethodsButton.setBounds(150, 5, 300, 25);
     	final Button srcClassesButton = new Button(srcRB, SWT.PUSH);
     	srcClassesButton.setText("save " + srcFolderName + " classes diagram of : " + project.getName());
        srcClassesButton.setBounds(150, 5, 300, 25);
        final Button methodsButton = new Button(testLB, SWT.PUSH);
     	methodsButton.setText("save " + testFolderName + " methods diagram of : " + project.getName());
     	methodsButton.setBounds(150, 5, 300, 25);
     	final Button classButton = new Button(testRB, SWT.PUSH);
     	classButton.setText("save " + testFolderName + " classes diagram of : " + project.getName());
        classButton.setBounds(150, 5, 300, 25);
         
        srcMethodsButton.addListener(SWT.Selection, new Listener() {
         	@Override
         	/** 
         	 * override handleEvent
         	 */
         	public void handleEvent(final Event event){    
         		try {
         			final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg","*.*");
         			final JFileChooser fileChooser = new JFileChooser();
         			fileChooser.setFileFilter(filter);
         			if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
         				final OutputStream out = new FileOutputStream(fileChooser.getSelectedFile()+".jpeg");
         				ChartUtilities.writeChartAsJPEG(out,srcMethodsChart, 1200,600);
         			}
         		} catch (IOException ex) {
         				ex.printStackTrace();
         		}
         	}});
         srcClassesButton.addListener(SWT.Selection, new Listener() {
         	@Override
         	/**
         	 * override handleEvent
         	 */
         	public void handleEvent(final Event event){    
         		try {
         			final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg","*.*");
         	   		final JFileChooser fileChooser = new JFileChooser();
         	   		fileChooser.setFileFilter(filter);
         	   		if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
         	   		 	final OutputStream out = new FileOutputStream(fileChooser.getSelectedFile()+".jpeg");
         	   		 	ChartUtilities.writeChartAsJPEG(out,srcClassChart, 1200,600);
         	   		 }
         	   	} catch (IOException ex) {
         	            ex.printStackTrace();
         	     }
         	    }
         	});
         methodsButton.addListener(SWT.Selection, new Listener() {
         	@Override
         	public void handleEvent(final Event event){    
         		try {
         			final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg","*.*");
         	   		final JFileChooser fileChooser = new JFileChooser();
         	   		fileChooser.setFileFilter(filter);
         	   		if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
         	   		 	final OutputStream out = new FileOutputStream(fileChooser.getSelectedFile()+".jpeg");
         	   		 	ChartUtilities.writeChartAsJPEG(out,testMethodsChart, 1200,600);
         	   		 }
         	   	} catch (IOException ex) {
         	            ex.printStackTrace();
         	     }
         	    }
         	});
         classButton.addListener(SWT.Selection, new Listener() {
         	@Override
         	/**
         	 * override handleEvent
         	 */
         	public void handleEvent(final Event event){ 
         		try {
         			final FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg","*.*");
         	   		final JFileChooser fileChooser = new JFileChooser();
         	   		fileChooser.setFileFilter(filter);
         	   		if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
         	   			final OutputStream out = new FileOutputStream(fileChooser.getSelectedFile()+".jpeg");
         	   		 	ChartUtilities.writeChartAsJPEG(out,testClassChart, 1200,600);
         	   		 	}
         		} catch (IOException ex) {
         	            ex.printStackTrace();
         	     	}
         	    }
         	});
    }
    
    /**
     * Inits the.
     *
     * @return the category dataset
     */ 
   /**
    * Src bar methods dataset.
    *
    * @return the category dataset
    */
   /**
    * Creates the src methods set.
    *
    * @return the pie dataset
    */
   // PIE 
    private  PieDataset createSrcMethodsSet() {
    	final DefaultPieDataset result = new DefaultPieDataset();    
    	for (int i = 0;i <srcMethodsList.size() ; i++){
    			result.setValue(srcMethodsList.get(i).getName() + "|Ratio " + srcMethodsRatio[i], srcMethodsList.get(i).getLoc());
    	} return result;
    }
    
    /**
     * Creates the src classes set.
     *
     * @return the pie dataset
     */
    private  PieDataset createSrcClassesSet() {
    	final DefaultPieDataset result = new DefaultPieDataset();    
    	for (int i = 0;i < srcClassesList.size(); i++){
			result.setValue(srcClassesList.get(i).getName() + "|Ratio " + srcClassesRatio[i], srcClassesList.get(i).getLoc());
    	} return result;
    } 
    
    /**
     * Creates the test methods sset.
     *
     * @return the pie dataset
     */
    private  PieDataset createTestMethodsSset() {
    	final DefaultPieDataset result = new DefaultPieDataset();    
    	for (int i = 0;i < testMethodsList.size(); i++){
    			result.setValue(testMethodsList.get(i).getName() + "|Ratio " + testMethodsRatio[i], testMethodsList.get(i).getLoc());
    		}return result;
    }

	/**
	 * Creates the test classes set.
	 *
	 * @return the pie dataset
	 */
	private  PieDataset createTestClassesSet() {
    final DefaultPieDataset result = new DefaultPieDataset();    
    for (int i = 0;i < testClassesList.size(); i++){
    		result.setValue(testClassesList.get(i).getName() + "|Ratio " + testClassesRatio[i], testClassesList.get(i).getLoc());
    	} return result;
	}
	/**
	 * Creates the pie chart.
	 *
	 * @param dataset the dataset
	 * @param title the title
	 * @return the j free chart
	 */
	private JFreeChart createPieChart(final PieDataset dataset, final String title) {
		final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0}  | Lines : {1} = {2}");
		final JFreeChart chart = ChartFactory.createPieChart(
				title,                  // chart title
				dataset,                // data
				true,                   // include legend
				true,
				true
    );
		final PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelGenerator(labelGenerator);
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.4f);
		return chart;
	}
}
