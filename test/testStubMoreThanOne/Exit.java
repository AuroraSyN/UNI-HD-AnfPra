/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class Exit.
 */
@Feature("ItemManagement")
public class Exit extends GameObject {

	/**
	 * Instantiates a new exit.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Exit(int x, int y) {
		super(Constants.ID_EXIT, x, y);
	}

}
