package de.uhd.ifi.feature.metric.calculator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ProviderData. Final version.Version 1.0.0
 * Provider Data Container
 * @author Aleksandr Soloninov
 */
public class ProviderData {
	
	/** The classes list. */
	private List<Feature> classesList = new ArrayList<Feature>();
	
	/** The methods list. */
	private List<Feature> methodsList = new ArrayList<Feature>();
	
	/** The classes ratio. */
	private String[] classesRatio;
	
	/** The methods ratio. */
	private String[] methodsRatio;
	
	/** The total class lines. */
	private int totalClassLines;
	
	/** The total methods lines. */
	private int totalMethodsLines;

	/**
	 * Gets the classes list.
	 *
	 * @return the classesList
	 */
	public List<Feature> getClassesList() {
		return classesList;
	}
	
	/**
	 * Sets the classes list.
	 *
	 * @param classesList the classesList to set
	 */
	public void setClassesList(final List<Feature> classesList) {
		this.classesList = classesList;
	}
	
	/**
	 * Gets the methods list.
	 *
	 * @return the methodsList
	 */
	public List<Feature> getMethodsList() {
		return methodsList;
	}
	
	/**
	 * Sets the methods list.
	 *
	 * @param methodsList the methodsList to set
	 */
	public void setMethodsList(final List<Feature> methodsList) {
		this.methodsList = methodsList;
	}

	/**
	 * Gets the methods ratio.
	 *
	 * @return the methodsRatio
	 */
	public String[] getMethodsRatio() {
		return methodsRatio;
	}
	
	/**
	 * Sets the methods ratio.
	 *
	 * @param methodsRatio the methodsRatio to set
	 */
	public void setMethodsRatio(final String[] methodsRatio) {
		this.methodsRatio = methodsRatio;
	}
	
	/**
	 * Gets the classes ratio.
	 *
	 * @return the classesRatio
	 */
	public String[] getClassesRatio() {
		return classesRatio;
	}
	
	/**
	 * Sets the classes ratio.
	 *
	 * @param classesRatio the classesRatio to set
	 */
	public void setClassesRatio(final String[] classesRatio) {
		this.classesRatio = classesRatio;
	}
	
	/**
	 * Gets the total class lines.
	 *
	 * @return the totalClassLines
	 */
	public int getTotalClassLines() {
		return totalClassLines;
	}
	
	/**
	 * Sets the total class lines.
	 *
	 * @param totalClassLines the totalClassLines to set
	 */
	public void setTotalClassLines(final int totalClassLines) {
		this.totalClassLines = totalClassLines;
	}
	
	/**
	 * Gets the total methods lines.
	 *
	 * @return the totalMethodsLines
	 */
	public int getTotalMethodsLines() {
		return totalMethodsLines;
	}
	
	/**
	 * Sets the total methods lines.
	 *
	 * @param totalMethodsLines the totalMethodsLines to set
	 */
	public void setTotalMethodsLines(final int totalMethodsLines) {
		this.totalMethodsLines = totalMethodsLines;
	}
}
