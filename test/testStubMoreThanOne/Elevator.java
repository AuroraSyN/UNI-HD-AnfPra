/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class Elevator.
 */
@Feature({ "ItemManagement", "CollisionDetection"})
public class Elevator extends GameObject {

	/** The bounded. */
	private boolean bounded;
	
	/** The down. */
	private boolean down = true;
	
	/** The levellim. */
	private int levellim = Constants.PANEL_HEIGHT - Constants.TILE_SIZE * 3;
	
	/** The level. */
	private int level = levellim;
	
	/**
	 * Instantiates a new elevator.
	 *
	 * @param x the x
	 * @param y the y
	 * @param bounded the bounded
	 */
	public Elevator(int x, int y, boolean bounded) {
		super(Constants.ID_ELEVATOR, x, y);
		this.bounded = bounded;
	}
	
	/* (non-Javadoc)
	 * @see testStubMoreThanOne.GameObject#update()
	 */
	@Override
	@Feature("CollisionDetection")
	public void update() {
		if (bounded) {
			int newy = y;
			newy += down ? 1 : -1;
			if (Game.getInstance().map.hasCollisionExcept(x, newy, this) || Game.getInstance().map.outOfBounds(x, newy)) {
				down = !down;
			} else {
				y = newy;
			}
		} else {
			y += down ? 1 : -1;
			level--;
			if (level == 0) {
				level = levellim;
				down = !down;
			}
		}
	}

}
