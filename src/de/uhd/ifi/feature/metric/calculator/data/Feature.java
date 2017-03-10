/*
 * 
 */
package de.uhd.ifi.feature.metric.calculator.data;

// Final. Version 1.0.0
/**
 * Describes a feature which is implemented in one or more classes in a project.
 * The code that implements the feature is marked with a @feature annotation.
 * It can comprise a class or a method. 
 * Final version.
 * Version 1.0.0
 * @author Rafael Gerner
 *
 */
public class Feature {
	
	/**  The name of the feature. */
	private String name;
	
	/**  The type of the feature. */
	private Type type;
	
	/**  The lines of code of the feature. */
	private int loc;
	
	/**
	 * The contructor which initializes all attributes with given parameters.
	 *
	 * @param name the name of the feature.
	 * @param loc the lines of code of the feature.
	 * @param type the type of the feature.
	 */
	public Feature (final String name, final int loc ,final Type type){
		this.name = name;
		this.type = type;
		this.loc = loc;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(final Type type) {
		this.type = type;
	}

	/**
	 * Gets the loc.
	 *
	 * @return the loc
	 */
	public int getLoc() {
		return loc;
	}

	/**
	 * Sets the loc.
	 *
	 * @param loc the loc to set
	 */
	public void setLoc(final int loc) {
		this.loc = loc;
	}
}
