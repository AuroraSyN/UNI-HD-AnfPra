/*
 * 
 */
package testStubMoreThanOne;

/*
 * Copyright (C) Alejandro Santos, 2014, ale@ralo.com.ar.
 *
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

import testStubMoreThanOne.GameCanvas;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class GameObject.
 */
@Feature("ItemManagement")
public class GameObject {
	
	/** The img num. */
	public int imgNum;
	
	/** The y. */
	public int x, y;
	
	/** The remove. */
	public boolean remove = false;
	
	/**
	 * Instantiates a new game object.
	 *
	 * @param imgNum the img num
	 * @param x the x
	 * @param y the y
	 */
	public GameObject(int imgNum, int x, int y) {
		this.imgNum = imgNum;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Draw.
	 *
	 * @param canvas the canvas
	 */
	public void draw(GameCanvas canvas) {
		canvas.drawTileNum(imgNum, x, y);
	}
	
	/**
	 * Update.
	 */
	public void update() {
		// Nothing to do here. This is meant to be @Overriden by subclasses.
	}
	
	/**
	 * On timer.
	 */
	public void onTimer() {
		// Nothing to do here. This is meant to be @Overriden by subclasses.
	}
}
