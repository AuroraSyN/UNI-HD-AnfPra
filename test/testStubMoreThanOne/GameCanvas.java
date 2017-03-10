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

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import testStubMoreThanOne.GameObject;
import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Data;
import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class GameCanvas.
 */
@Feature("LevelManagement")
public class GameCanvas extends Canvas {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The tiles. */
	private BufferedImage tiles = null;
	
	/** The g 2 d. */
	private Graphics2D g2d;
	
	/**
	 * Instantiates a new game canvas.
	 */
	public GameCanvas() {
		super();
	}

	/**
	 * Inits the.
	 */
	public void init() {
		loadGraphics();
		setIgnoreRepaint(true);
		createBufferStrategy(2);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Canvas#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics arg0) {
		paint(arg0);
	}		// setIgnoreRepaint(false);

	/**
	 * Load graphics.
	 */
	public void loadGraphics() {
		BufferedImage img;
		
		// Load the image data.
		try {
			img = ImageIO.read(new ByteArrayInputStream(Data.TILES_DATA));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		// Make 0xFF00FF pixels transparent.
		int w = img.getWidth(), h = img.getHeight();
		int x, y, p = 0;
		int[] data = img.getRGB(0, 0, w, h, null, 0, w);
		
		for (y = 0; y < h; ++y) {
			for (x = 0; x < w; ++x, ++p) {
				if ((data[p] & 0xffffff) == 0xff00ff) /* Pink, it's not even a question */
					data[p] = 0x0;
			}
		}
		
		GraphicsConfiguration config = GraphicsEnvironment.
				getLocalGraphicsEnvironment().getDefaultScreenDevice().
				getDefaultConfiguration();
		
		// Update image data.
		if (config != null)
			tiles = config.createCompatibleImage(w, h, Transparency.BITMASK);
		else
			tiles = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		tiles.setRGB(0, 0, w, h, data, 0, w);
	}
	
	/**
	 * Draw tile num.
	 *
	 * @param num the num
	 * @param dx the dx
	 * @param dy the dy
	 */
	public final void drawTileNum(int num, int dx, int dy) {
		int sx = num * 32;
		g2d.drawImage(tiles, 
				dx, dy, 
				dx + Constants.TILE_SIZE, dy + Constants.TILE_SIZE, 
				sx, 0, 
				sx + Constants.TILE_SIZE, Constants.TILE_SIZE, null);
	}
	
	/**
	 * Draw objects.
	 *
	 * @param l the l
	 */
	public final void drawObjects(List<GameObject> l) {
		for (GameObject w : l) {
			drawTileNum(w.imgNum, w.x, w.y);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics arg0) {
		Game g = Game.getInstance();
		BufferStrategy bs = getBufferStrategy();
		int x, y, px, py;
		
		if (bs == null) {
			loadGraphics();
			return;
		}
		
		g2d = (Graphics2D) bs.getDrawGraphics();
		
		for (y = 0, py = 0; y < g.map.H; ++y, py += Constants.TILE_SIZE) {
			for (x = 0, px = 0; x < g.map.W; ++x, px += Constants.TILE_SIZE) {
				drawTileNum(
						(g.map.data[y][x] == '.') ? Constants.ID_WALL : Constants.ID_BACKGROUND,
						px, py);
			}
		}
		
		drawObjects(g.map.items);
		drawObjects(g.map.exits);
		drawObjects(g.map.elevators);
		drawObjects(g.map.zombies);
		drawObjects(g.map.fireballs);
		drawObjects(g.map.specials);
		
		drawTileNum(Constants.ID_PLAYER, g.p.x, g.p.y);
		
		g2d.dispose();
		
		if (!bs.contentsLost()) {
			bs.show();
		} else {
			loadGraphics();
		}
	}
}
