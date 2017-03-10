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

import javax.swing.JApplet;

import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class MainApplet.
 */
@Feature("PlatformIndependence")
public class MainApplet extends JApplet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The mp. */
	private GamePanel mp = new GamePanel();
	
	/**
	 * Instantiates a new main applet.
	 */
	public MainApplet() {
		getContentPane().add(mp);
		enableInputMethods(true);
		setFocusable(true);
		addKeyListener(Game.getInstance());
        requestFocusInWindow();
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	@Feature("PlatformIndependence")
	public void init() {
		super.init();
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#start()
	 */
	@Override
	@Feature("PlatformIndependence")
	public void start() {
		super.start();
		mp.start();
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#stop()
	 */
	@Override
	@Feature("PlatformIndependence")
	public void stop() {
		super.stop();
		mp.stop();
	}
}

