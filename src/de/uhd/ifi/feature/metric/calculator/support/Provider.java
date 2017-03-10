package de.uhd.ifi.feature.metric.calculator.support;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.uhd.ifi.feature.metric.calculator.calculations.FeatureCalculator;
import de.uhd.ifi.feature.metric.calculator.data.Feature;

/**
 * The Class DataProvider. Final Version 1.0.0
 * Provider between FeatureCalculator and MainUI
 * @author Aleksandr Soloninov
 */
public class Provider {

	/** The List classes. */
	private List<Feature> listClasses = new ArrayList<Feature>();
	
	/** The List methods. */
	private List<Feature> listMethods = new ArrayList<Feature>();
	
	/** The null feature. */
	private Feature nullFeature = new Feature("--None--",0,null);
	
	/** The metrics. */
	private FeatureCalculator metrics = new FeatureCalculator();
	
	/** The total class lines. */
	@SuppressWarnings("unused")
	private int totalClassLines;
	
	/** The total methods lines. */
	@SuppressWarnings("unused")
	private int totalMethodsLines;
	
	/** The src path. */
	protected static File srcPath;
	
	/** The test path. */
	protected static File testPath;
	
	protected static int time;
	
	public static void writeTime(int _time){
		time = _time;
	}
	
	public static int getTime(){
		return time;
	}
	
	/**
	 * Gets the src path.
	 *
	 * @return the src path
	 */
	public static File getSrcPath(){
		return srcPath;
	}
	
	/**
	 * Write src path.
	 *
	 * @param _srcPath the src path
	 */
	public static void writeSrcPath(File _srcPath){
		srcPath = _srcPath;
	}
	
	/**
	 * Gets the test path.
	 *
	 * @return the test path
	 */
	public static File getTestPath(){
		return testPath;
	}
	
	/**
	 * Write test path.
	 *
	 * @param _testPath the test path
	 */
	public static void writeTestPath(File _testPath){
		testPath = _testPath;
	}
	
	/** The flag. */
	protected static boolean flag;
	
	/**
	 * Gets the flag.
	 *
	 * @return the flag
	 */
	public static boolean getFlag(){
		return flag;
	}
	
	/**
	 * Write flag.
	 *
	 * @param _flag the flag
	 */
	public static void writeFlag(final boolean _flag){
		flag = _flag;
	}
	
	/**
	 * Instantiates a new data provider.
	 *
	 * @param _classList the class list
	 * @param _methodsList the methods list
	 */
	public Provider(final List<Feature> _classList, final List<Feature> _methodsList) {
		listClasses = _classList;
		listMethods = _methodsList;
	}
	
	/**
	 * Gets the list classes.
	 *
	 * @return the list classes
	 */
	public List<Feature> getListClasses(){
		return listClasses;
	}
	
	/**
	 * Gets the list methods.
	 *
	 * @return the list methods
	 */
	public List<Feature> getListMethods(){
		return listMethods;
	}
	
	
	/**
	 * Equalise list size.
	 */
	public void equaliseListSize(){
		final int dif = Math.abs(listMethods.size() - listClasses.size());
		if(listMethods.size() < listClasses.size()){
			for(int i = 0; i < dif; i++){
				listMethods.add(nullFeature);
			}
		} else if(listMethods.size() > listClasses.size()){
			for(int i = 0; i < dif; i++){
				listClasses.add(nullFeature);
			}
		}
	}
	
	/**
	 * Gets the total methods lines.
	 *
	 * @param featureList the feature list
	 * @return the total methods lines
	 */
	public int getTotalMethodsLines(final List<Feature> featureList) {
		this.totalMethodsLines = metrics.getTotalLines(featureList);
		return this.totalMethodsLines;
	} 
		
	/**
	 * Gets the total class lines.
	 *
	 * @param featureList the feature list
	 * @return the total class lines
	 */
	public int getTotalClassLines(final List<Feature> featureList) {
		this.totalClassLines = metrics.getTotalLines(featureList);
		return this.totalClassLines;
	} 

	/**
	 * Gets the ratio.
	 *
	 * @param srcFeature the src feature
	 * @param testFeature the test feature
	 * @return the ratio
	 */
	
	public String[] getRatio(final List<Feature> srcFeature, final List<Feature> testFeature){
		String[] returnRatio = new String[srcFeature.size()];
		boolean isEqual = false;
		int index = 0;
		for(int i = 0; i < srcFeature.size(); i++){
			for(int j = 0; j < testFeature.size(); j++){
				if(srcFeature.get(i).getName().equals(testFeature.get(j).getName())){
					isEqual = true;
					index = j;
					continue;
				}
			}
			if(isEqual){
				returnRatio[i] = metrics.getLinesRatio(srcFeature.get(i).getLoc(),testFeature.get(index).getLoc());
				isEqual = false;
				index = 0;
			}
			else{
				returnRatio[i] = Integer.toString(srcFeature.get(i).getLoc()) + " : 0";
			}
		}
		return returnRatio;
	}
}
