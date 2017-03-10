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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import testStubMoreThanOne.Elevator;
import testStubMoreThanOne.Exit;
import testStubMoreThanOne.FireBall;
import testStubMoreThanOne.GameObject;
import testStubMoreThanOne.Item;
import testStubMoreThanOne.Zombie;
import testStubMoreThanOne.ZombiePortal;
import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Data;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class Map.
 */
@Feature({"LevelManagement","CollisionDetection", "ItemManagement"})
public class Map {
	
	/** The data. */
	public char[][] data;
	
	/** The h. */
	public int W, H;
	
	/** The current map num. */
	public int currentMapNum = 0;
	
	/** The items. */
	public LinkedList<GameObject> items = new LinkedList<GameObject>();
	
	/** The zombies. */
	public ArrayList<GameObject> zombies = new ArrayList<GameObject>();
	
	/** The exits. */
	public ArrayList<GameObject> exits = new ArrayList<GameObject>();
	
	/** The elevators. */
	public ArrayList<GameObject> elevators = new ArrayList<GameObject>();
	
	/** The specials. */
	public ArrayList<GameObject> specials = new ArrayList<GameObject>();
	
	/** The fireballs. */
	public ArrayList<GameObject> fireballs = new ArrayList<GameObject>();
	
	/**
	 * Instantiates a new map.
	 */
	public Map() {
		loadMap(1);
		
		assert !tileIntersection(0, 0, 32, 0);
		assert !tileIntersection(0, 0, 32, 32);
		assert !tileIntersection(0, 0, 0, 32);
		
		assert !tileIntersection(32, 0, 0, 0);
		assert !tileIntersection(32, 32, 0, 0);
		assert !tileIntersection(0, 32, 0, 0);
	}
	
	/**
	 * Next map.
	 */
	@Feature("LevelManagement")
	public void nextMap() {
		loadMap(currentMapNum + 1);
	}
	
	/**
	 * Load map.
	 *
	 * @param n the n
	 */
	@Feature("LevelManagement")
	private void loadMap(int n) {
		int x, y;
		
		if (n == Data.MAP_DATA.length)
			return;
		
		currentMapNum = n;
		String[] d = Data.MAP_DATA[n].split("\n");
		data = new char[d.length][];
		
		for (y = 0; y < d.length; ++y) {
			data[y] = d[y].toCharArray();
		}
		
		W = data[0].length;
		H = data.length;
		
		items.clear();
		zombies.clear();
		exits.clear();
		elevators.clear();
		specials.clear();
		fireballs.clear();
		
		for (y = 0; y < H; ++y) {
			for (x = 0; x < W; ++x) {
				int pixelx = x * Constants.TILE_SIZE, pixely = y * Constants.TILE_SIZE;
				
				switch (data[y][x]) {
				case '+': // item
					items.add(new Item(pixelx, pixely));
					break;
					
				case '*': // Exit
					exits.add(new Exit(pixelx, pixely));
					break;
					
				case '!': // Elevator 1, unbounded.
				case '?': // Elevator 2, bounded.
					elevators.add(new Elevator(pixelx, pixely, (data[y][x] == '?')));
					break;
					
				case 'z': // Zombie
					zombies.add(new Zombie(pixelx, pixely));
					break;
					
				case '$': // Zombie Portal
					specials.add(new ZombiePortal(pixelx, pixely));
					break;
				
				default:
					assert false;
				}
			}
		}
	}
	
	/**
	 * Update.
	 */
	public void update() {
		update(elevators);
		update(zombies);
		update(fireballs);
	}
	
	/**
	 * On timer.
	 */
	public void onTimer() {
		for (Iterator<GameObject> it = specials.iterator(); it.hasNext(); ) {
			GameObject w = it.next();
			
			if (w.remove) {
				it.remove();
			} else {
				w.onTimer();
			}
		}	
	}
	
	/**
	 * Update.
	 *
	 * @param L the l
	 */
	private static final void update(List<GameObject> L) {
		for (Iterator<GameObject> it = L.iterator(); it.hasNext(); ) {
			GameObject w = it.next();
			
			if (w.remove) {
				it.remove();
			} else {
				w.update();
			}
		}		
	}
	
	/**
	 * Tile intersection.
	 *
	 * @param x1 the x 1
	 * @param y1 the y 1
	 * @param x2 the x 2
	 * @param y2 the y 2
	 * @return true, if successful
	 */
	@Feature("CollisionDetection")
	private static final boolean tileIntersection(int x1, int y1, int x2, int y2) {
		final int t = Constants.TILE_SIZE;
		return (x1 + t > x2 && x1 < x2 + t && y1 + t > y2 && y1 < y2 + t);
	}
	
	/**
	 * Find zombie collision.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the game object
	 */
	@Feature("CollisionDetection")
	public GameObject findZombieCollision(int x, int y) {
		for (GameObject w : zombies) {
			if (tileIntersection(x, y, w.x, w.y))
				return w;
		}
		return null;
	}

	/**
	 * Checks for zombie collision.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	@Feature("CollisionDetection")
	public boolean hasZombieCollision(int x, int y) {
		return findZombieCollision(x, y) != null;
	}
	
	/**
	 * Checks for exit collision.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	@Feature("CollisionDetection")
	public boolean hasExitCollision(int x, int y) {
		for (GameObject w : exits) {
			if (tileIntersection(x, y, w.x, w.y))
				return true;
		}
		return false;
	}
	
	/**
	 * Collect items.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	@Feature("ItemManagement")
	public boolean collectItems(int x, int y) {
		boolean ret = false;
		
		for (Iterator<GameObject> it = items.iterator(); it.hasNext(); ) {
			GameObject w = it.next();
			if (tileIntersection(x, y, w.x, w.y)) {
				it.remove();
				ret = true;
			}
		}
		
		return ret;

	}
	
	/**
	 * Find min collision Y.
	 *
	 * @param ex the ex
	 * @param ey the ey
	 * @param exceptElevator the except elevator
	 * @return Returns Constants.PANEL_HEIGHT on no collision.
	 */
	@Feature("CollisionDetection")
	public int findMinCollisionY(int ex, int ey, Elevator exceptElevator) {
		int retminy = Constants.PANEL_HEIGHT;
		
		// A 32x32 rect can intersect with, at most, four 32x32 rects.
		int sx1 = ex / Constants.TILE_SIZE;
		int sy1 = ey / Constants.TILE_SIZE;
		int sx2 = ((ex % Constants.TILE_SIZE) != 0) ? sx1 + 2 : sx1 + 1;
		int sy2 = ((ey % Constants.TILE_SIZE) != 0) ? sy1 + 2 : sy1 + 1;
		int x, y;
		
		sx1 = Math.max(sx1, 0);
		sy1 = Math.max(sy1, 0);
		sx2 = Math.min(sx2, W);
		sy2 = Math.min(sy2, H);
		
		for (y = sy1; y < sy2; ++y) {
			for (x = sx1; x < sx2; ++x) {
				if (data[y][x] == '.' && tileIntersection(
						x * Constants.TILE_SIZE, 
						y * Constants.TILE_SIZE, ex, ey)) {
					retminy = Math.min(retminy, y * Constants.TILE_SIZE);
				}
			}
		}
		
		for (GameObject w : elevators) {
			if (w != exceptElevator && tileIntersection(ex, ey, w.x, w.y))
				retminy = Math.min(retminy, w.y);
		}
		
		return retminy;
	}
	
	/**
	 * Find min collision Y.
	 *
	 * @param ex the ex
	 * @param ey the ey
	 * @return the int
	 */
	@Feature("CollisionDetection")
	public int findMinCollisionY(int ex, int ey) {
		return findMinCollisionY(ex, ey, null);
	}
	
	/**
	 * Checks for collision.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	@Feature("CollisionDetection")
	public boolean hasCollision(int x, int y) {
		return findMinCollisionY(x, y, null) != Constants.PANEL_HEIGHT;
	}
	
	/**
	 * Checks for collision except.
	 *
	 * @param x the x
	 * @param y the y
	 * @param exceptElevator the except elevator
	 * @return true, if successful
	 */
	@Feature("CollisionDetection")
	public boolean hasCollisionExcept(int x, int y, Elevator exceptElevator) {
		return findMinCollisionY(x, y, exceptElevator) != Constants.PANEL_HEIGHT;
	}
	
	/**
	 * Out of bounds.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	@Feature("CollisionDetection")
	public boolean outOfBounds(int x, int y) {
		return (x < 0) || (y < 0) || 
				((x + Constants.TILE_SIZE) > Constants.PANEL_WIDTH) || 
				((y + Constants.TILE_SIZE) > Constants.PANEL_HEIGHT); 
	}
	
	/**
	 * Fire.
	 *
	 * @param x the x
	 * @param y the y
	 * @param left the left
	 */
	@Feature("CollisionDetection")
	public void fire(int x, int y, boolean left) {
		fireballs.add(new FireBall(x, y, left));
	}
}
