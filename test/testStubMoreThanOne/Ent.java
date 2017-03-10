/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class Ent.
 */
@Feature({"ItemManagement", "CollisionDetection"})
public class Ent extends GameObject {

	/** The speed. */
	public int speed = 0; // delta_move in .py
	
	/** The jumping. */
	public boolean jumping = false;
	
	/** The jump pos. */
	public int jump_pos = 0;
	
	/** The grounded. */
	public boolean grounded = false;
	
	/** The left. */
	public boolean left = true;
	
	/**
	 * Instantiates a new ent.
	 *
	 * @param imgNum the img num
	 * @param x the x
	 * @param y the y
	 * @param speed the speed
	 */
	public Ent(int imgNum, int x, int y, int speed) {
		super(imgNum, x, y);
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see testStubMoreThanOne.GameObject#update()
	 */
	@Feature("CollisionDetection")
	@Override
	public void update() {
		Game g = Game.getInstance();
		int newx = x, newy = y;
		int miny;
		
		// Out of the map
		if (g.map.outOfBounds(x, y)) {
			reset();
			return;
		}
		
		// Update position on elevator movement
		miny = g.map.findMinCollisionY(x, y);
		if (miny != Constants.PANEL_HEIGHT) {
			if (y < miny) {
				y = miny - Constants.TILE_SIZE;
			} else if ((miny + Constants.TILE_SIZE) - y > speed) {
				reset();
				return;
			}
		}
		
		// Jump.
		if (jumping) {
			newy -= speed;
			jump_pos -= speed;
			if (jump_pos <= 0) {
				jumping = false;
				jump_pos = 0;
			}
		} else {
			newy += speed;
		}
		
		miny = g.map.findMinCollisionY(newx, newy);
		grounded = (miny != Constants.PANEL_HEIGHT);
		
		if (!grounded) {
			x = newx;
			y = newy;
		} else {
			if (y < miny) {
				y = miny - Constants.TILE_SIZE;
			} else if (jumping) {
				if ((miny + Constants.TILE_SIZE) - y <= speed) {
					y = miny + Constants.TILE_SIZE;
				}
			}
			
			if (jumping) {
				jumping = false;
				jump_pos = 0;
			}
		}
		
		checkItemCollision();
	}
	
	/**
	 * Move.
	 *
	 * @param moveLeft the move left
	 */
	@Feature("CollisionDetection")
	public void move(boolean moveLeft) {
		Game g = Game.getInstance();
		int newx = x, newy = y;
		
		if (moveLeft) {
			newx -= speed;
		} else {
			newx += speed;
		}
		left = moveLeft;
		
		if (!g.map.outOfBounds(newx, newy)) {
			if (!g.map.hasCollision(newx, newy)) {
				this.x = newx;
				this.y = newy;
			}
		}
	}
	
	/**
	 * Reset.
	 */
	public void reset() {
		
	}
	
	/**
	 * Jump.
	 */
	public void jump() {
		if (!this.jumping && this.grounded) {
			this.jumping = true;
			this.jump_pos = Constants.TILE_SIZE + Constants.TILE_SIZE / 2;
		}
	}
	
	/**
	 * Fire.
	 */
	public void fire() {
		Game.getInstance().map.fire(this.x, this.y, this.left);
	}
	
	/**
	 * Check item collision.
	 */
	@Feature("CollisionDetection")
	protected void checkItemCollision() {
		
	}
}
