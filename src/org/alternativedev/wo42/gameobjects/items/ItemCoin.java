package org.alternativedev.wo42.gameobjects.items;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.gameobjects.GameObject;
import org.alternativedev.wo42.gameobjects.Item;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * An Item representing a retro coin
 * 
 * @author janni-futz
 * 
 */
public class ItemCoin extends Item {
	/**
	 * Construct a CoinItem.
	 * 
	 * @param x
	 *            Location on the X-Axis
	 * @param y
	 *            Location on the Y-Axis
	 * @throws SlickException
	 */
	public ItemCoin(int x, int y) throws SlickException {
		super(new Image("data/items/retro_coin.png"), x, y);
	}

	@Override
	public void onUpdate(GameContainer gc, int delta, Map map, Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		// TODO Auto-generated method stub
		
	}

}
