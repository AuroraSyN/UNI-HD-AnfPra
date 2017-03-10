/*
 * 
 */
package testStubOneAnn;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import testStubMoreThanOne.GamePanel;
import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
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

/**
 * The Class MainApp3.
 */
@Feature("PlatformIndependence")
public class MainApp3 extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The mp. */
	private GamePanel mp = new GamePanel();

	/**
	 * Instantiates a new main app 3.
	 */
	public MainApp3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(mp, BorderLayout.CENTER);
		mp.setPreferredSize(new Dimension(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT));
		pack();		
		setVisible(true);

		enableInputMethods(true);
		setFocusable(true);
		addKeyListener(Game.getInstance());
        requestFocusInWindow();
        mp.start();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	@Feature("PlatformIndependence")
	public static void main(String[] args) {
		new MainApp3();
	}

}
