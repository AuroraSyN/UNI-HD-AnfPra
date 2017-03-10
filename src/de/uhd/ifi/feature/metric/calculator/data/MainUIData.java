package de.uhd.ifi.feature.metric.calculator.data;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.TableColumn;
/**
 * The Class MainUIData.Final version 1.0.0.
 * MainUI Data Container
 * @author Aleksandr Soloninov
 */
public class MainUIData {
	
	/** The settings. */
	private Action settings;
	
	/** The refresh action. */
	private Action refreshAction;
	
	/** The export. */
	private Action export;
	
	/** The diagram. */
	private Action diagram;
	
	/** The history. */
	private Action history;
	
	/** The project name column. */
	private TableColumn projectNameColumn;
	
	/** The destination column. */
	private TableColumn destinationColumn;
	
	/** The methods name column. */
	private TableColumn methodsNameColumn;
	
	/** The methods lines column. */
	private TableColumn methLinesColumn;
	
	/** The methods ratio column. */
	private TableColumn methRatioColumn;
	
	/** The classes name column. */
	private TableColumn classesNameColumn;
	
	/** The classes lines column. */
	private TableColumn classLinesColumn;
	
	/** The classes ratio column. */
	private TableColumn classRatioColumn;

	/** The entries. */
	private List<String[]> entries = new ArrayList<>();
	//SRC Block
	
	/** The class lines ratio. */
	private String classLinesRatio;
	
	/** The methods lines ratio. */
	private String methodsLinesRatio;

	/**
	 * Gets the export.
	 *
	 * @return the export
	 */
	public Action getExport() {
		return export;
	}
	
	/**
	 * Sets the export.
	 *
	 * @param export the export to set
	 */
	public void setExport(final Action export) {
		this.export = export;
	}
	
	/**
	 * Gets the refresh action.
	 *
	 * @return the refreshAction
	 */
	public Action getRefreshAction() {
		return refreshAction;
	}
	
	/**
	 * Sets the refresh action.
	 *
	 * @param refreshAction the refreshAction to set
	 */
	public void setRefreshAction(final Action refreshAction) {
		this.refreshAction = refreshAction;
	}

	/**
	 * Gets the history.
	 *
	 * @return the history
	 */
	public Action getHistory() {
		return history;
	}
	
	/**
	 * Sets the history.
	 *
	 * @param history the history to set
	 */
	public void setHistory(final Action history) {
		this.history = history;
	}

	/**
	 * Gets the project name column.
	 *
	 * @return the projectNameColumn
	 */
	public TableColumn getProjectNameColumn() {
		return projectNameColumn;
	}
	
	/**
	 * Sets the project name column.
	 *
	 * @param projectNameColumn the projectNameColumn to set
	 */
	public void setProjectNameColumn(final TableColumn projectNameColumn) {
		this.projectNameColumn = projectNameColumn;
	}
	
	/**
	 * Gets the destination column.
	 *
	 * @return the destinationColumn
	 */
	public TableColumn getDestinationColumn() {
		return destinationColumn;
	}
	
	/**
	 * Sets the destination column.
	 *
	 * @param destinationColumn the destinationColumn to set
	 */
	public void setDestinationColumn(final TableColumn destinationColumn) {
		this.destinationColumn = destinationColumn;
	}
	
	/**
	 * Gets the methods name column.
	 *
	 * @return the methodsNameColumn
	 */
	public TableColumn getMethodsNameColumn() {
		return methodsNameColumn;
	}
	
	/**
	 * Sets the methods name column.
	 *
	 * @param methodsNameColumn the methodsNameColumn to set
	 */
	public void setMethodsNameColumn(final TableColumn methodsNameColumn) {
		this.methodsNameColumn = methodsNameColumn;
	}
	
	/**
	 * Gets the methods lines column.
	 *
	 * @return the methodslinesColumn
	 */
	public TableColumn getMethodsLinesColumn() {
		return methLinesColumn;
	}
	
	/**
	 * Sets the methods lines column.
	 *
	 * @param methLinesColumn the methodslinesColumn to set
	 */
	public void setMethodsLinesColumn(final TableColumn methLinesColumn) {
		this.methLinesColumn = methLinesColumn;
	}
	
	/**
	 * Gets the methods ratio column.
	 *
	 * @return the ratioMethodsColumn
	 */
	public TableColumn getMethodsRatioColumn() {
		return methRatioColumn;
	}
	
	/**
	 * Sets the methods ratio column.
	 *
	 * @param ratioMethColumn the ratioMethodsColumn to set
	 */
	public void setMethodsRatioColumn(final TableColumn ratioMethColumn) {
		this.methRatioColumn = ratioMethColumn;
	}
	
	/**
	 * Gets the classes name column.
	 *
	 * @return the classesNameColumn
	 */
	public TableColumn getClassesNameColumn() {
		return classesNameColumn;
	}
	
	/**
	 * Sets the classes name column.
	 *
	 * @param classesNameColumn the classesNameColumn to set
	 */
	public void setClassesNameColumn(final TableColumn classesNameColumn) {
		this.classesNameColumn = classesNameColumn;
	}
	
	/**
	 * Gets the classes lines column.
	 *
	 * @return the classesLinesColumn
	 */
	public TableColumn getClassesLinesColumn() {
		return classLinesColumn;
	}
	
	/**
	 * Sets the classes lines column.
	 *
	 * @param classLinesColumn the classesLinesColumn to set
	 */
	public void setClassesLinesColumn(final TableColumn classLinesColumn) {
		this.classLinesColumn = classLinesColumn;
	}
	
	/**
	 * Gets the classes ratio column.
	 *
	 * @return the ratioClassesColumn
	 */
	public TableColumn getClassesRatioColumn() {
		return classRatioColumn;
	}
	
	/**
	 * Sets the classes ratio column.
	 *
	 * @param ratioClassColumn the ratioClassesColumn to set
	 */
	public void setClassesRatioColumn(final TableColumn ratioClassColumn) {
		this.classRatioColumn = ratioClassColumn;
	}
	
	/**
	 * Gets the class lines ratio.
	 *
	 * @return the classLinesRatio
	 */
	public String getClassLinesRatio() {
		return classLinesRatio;
	}
	
	/**
	 * Sets the class lines ratio.
	 *
	 * @param classLinesRatio the classLinesRatio to set
	 */
	public void setClassLinesRatio(final String classLinesRatio) {
		this.classLinesRatio = classLinesRatio;
	}
	
	/**
	 * Gets the methods lines ratio.
	 *
	 * @return the methodsLinesRatio
	 */
	public String getMethodsLinesRatio() {
		return methodsLinesRatio;
	}
	
	/**
	 * Sets the methods lines ratio.
	 *
	 * @param methodsLinesRatio the methodsLinesRatio to set
	 */
	public void setMethodsLinesRatio(final String methodsLinesRatio) {
		this.methodsLinesRatio = methodsLinesRatio;
	}
	
	/**
	 * Gets the entries.
	 *
	 * @return the entries
	 */
	public List<String[]> getEntries() {
		return entries;
	}
	
	/**
	 * Sets the entries.
	 *
	 * @param entries the entries to set
	 */
	public void setEntries(final List<String[]> entries) {
		this.entries = entries;
	}
	
	/**
	 * Gets the diagram.
	 *
	 * @return the diagram
	 */
	public Action getDiagram() {
		return diagram;
	}
	
	/**
	 * Sets the diagram.
	 *
	 * @param diagram the diagram to set
	 */
	public void setDiagram(final Action diagram) {
		this.diagram = diagram;
	}

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public Action getSettings() {
		return settings;
	}

	/**
	 * Sets the settings.
	 *
	 * @param settings the settings to set
	 */
	public void setSettings(Action settings) {
		this.settings = settings;
	}
}