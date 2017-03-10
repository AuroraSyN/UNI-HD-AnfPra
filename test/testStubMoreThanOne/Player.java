/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
@Feature({ "CharacterClass", "CollisionDetection", "ItemManagement",
		"LevelManagement" })
public class Player extends Ent {

	/**
	 * Instantiates a new player.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Player(int x, int y) {
		super(Constants.ID_PLAYER, x, y, Constants.SPEED_PLAYER);
	}

	/* (non-Javadoc)
	 * @see testStubMoreThanOne.Ent#reset()
	 */
	@Override
	public void reset() {
		this.x = 0;
		this.y = 0;
		this.jumping = false;
		this.grounded = false;
	}

	/* (non-Javadoc)
	 * @see testStubMoreThanOne.Ent#checkItemCollision()
	 */
	@Override
	@Feature({ "ItemManagement", "CollisionDetection", "LevelManagement" })
	protected void checkItemCollision() {
		Game g = Game.getInstance();

		if (g.map.hasZombieCollision(x, y)) {
			reset();
			return;
		}

		g.map.collectItems(x, y);

		if (g.map.hasExitCollision(x, y)) {
			g.map.nextMap();
			reset();
		}
	}
}
