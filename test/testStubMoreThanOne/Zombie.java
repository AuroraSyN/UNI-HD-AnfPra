/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class Zombie.
 */
@Feature("CharacterClass")
public class Zombie extends Ent {

	/**
	 * Instantiates a new zombie.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Zombie(int x, int y) {
		super(Constants.ID_ZOMBIE, x, y, Constants.SPEED_ZOMBIE);
	}

	/* (non-Javadoc)
	 * @see testStubMoreThanOne.Ent#update()
	 */
	@Override
	public void update() {
		int oldx = x, oldy = y;
		
		super.update();
		super.move(left);
		
		if (x == oldx && y == oldy) {
			// Not moving.
			left = !left;
		}
	}
	
	/* (non-Javadoc)
	 * @see testStubMoreThanOne.Ent#reset()
	 */
	@Override
	public void reset() {
		// Game.getInstance().map.zombies.remove(this);
		// Prevent a ConcurrentModificationException error
		this.remove = true;
	}
}
