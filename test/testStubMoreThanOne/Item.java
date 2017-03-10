/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class Item.
 */
@Feature("ItemManagement")
public class Item extends GameObject {

	/**
	 * Instantiates a new item.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Item(int x, int y) {
		super(Constants.ID_ITEM, x, y);
	}

}
