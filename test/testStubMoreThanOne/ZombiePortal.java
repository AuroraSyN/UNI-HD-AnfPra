/*
 * 
 */
package testStubMoreThanOne;

import testStubMoreThanOne.Constants;
import testStubMoreThanOne.Game;

import de.uhd.ifi.se.feature.annotations.java.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class ZombiePortal.
 */
@Feature({ "CharacterClass", "ItemManagement" })
public class ZombiePortal extends GameObject {

	/**
	 * Instantiates a new zombie portal.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public ZombiePortal(int x, int y) {
		super(Constants.ID_ITEM, x, y);
	}

	/**
	 * On item collect.
	 */
	@Feature("ItemManagement")
	public void onItemCollect() {
		onTimer();
	}

	/* (non-Javadoc)
	 * @see testStubMoreThanOne.GameObject#onTimer()
	 */
	@Override
	@Feature("CharacterClass")
	public void onTimer() {
		Game g = Game.getInstance();
		if (g.map.items.size() == 0 && g.map.zombies.size() < 40) {
			Zombie zz = new Zombie(x, y);

			zz.left = g.r.nextBoolean();
			g.map.zombies.add(zz);
		}
	}
}
