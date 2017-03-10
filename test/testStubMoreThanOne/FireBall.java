/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class FireBall.
 */
@Feature({"ItemManagement", "CollisionDetection"})
public class FireBall extends GameObject {

	/** The left. */
	boolean left;
	
	/**
	 * Instantiates a new fire ball.
	 *
	 * @param x the x
	 * @param y the y
	 * @param left the left
	 */
	public FireBall(int x, int y, boolean left) {
		super(Constants.ID_FIREBALL, x, y);
		this.left = left;
	}

	/* (non-Javadoc)
	 * @see testStubMoreThanOne.GameObject#update()
	 */
	@Override
	@Feature("CollisionDetection")
	public void update() {
		Game g = Game.getInstance();
		
		int newx = x, newy = y;
		
		newx += left ? -Constants.SPEED_FIREB : Constants.SPEED_FIREB;
		
		if (g.map.outOfBounds(newx, newy) || g.map.hasCollision(newx, newy)) {
			// g.map.fireballs.remove(this);
			remove = true;
		} else {
			x = newx;
			y = newy;
		}
		
		GameObject z = g.map.findZombieCollision(newx, newy);
		if (z != null) {
			// g.map.fireballs.remove(this);
			remove = true;
			// g.map.zombies.remove(z);
			z.remove = true;
		}
	}
}
