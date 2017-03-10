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

import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class GamePanel.
 */
@Feature({ "LevelManagement", "PlatformIndependence" })
public class GamePanel extends JPanel implements Runnable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The t. */
	private Thread t;
	
	/** The mc. */
	private GameCanvas mc;

	/** The updater. */
	private Runnable updater;

	/**
	 * Instantiates a new game panel.
	 */
	public GamePanel() {
		super();

		setLayout(new BorderLayout());
		validate();

		mc = new GameCanvas();
		removeAll();
		add(mc, BorderLayout.CENTER);
		mc.setVisible(true);
	}

	/**
	 * Start.
	 */
	public void start() {
		mc.init();

		updater = new Runnable() {
			@Override
			public void run() {
				// tk.sync();
				try {
					Game.getInstance().update();
				} catch (RuntimeException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		t = new Thread(this);
		t.start();
	}

	/**
	 * Stop.
	 */
	public void stop() {
		t.interrupt();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			mc.repaint();
			syncToolkit();

			try {
				Thread.sleep(1000 / 25);
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
	}

	/**
	 * Sync toolkit.
	 */
	private void syncToolkit() {
		try {
			SwingUtilities.invokeAndWait(updater);
		} catch (InterruptedException e1) {
			System.err.println(e1);
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			System.err.println(e1);
			e1.printStackTrace();
		}
	}
}
