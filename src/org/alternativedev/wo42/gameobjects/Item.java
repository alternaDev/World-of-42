package org.alternativedev.wo42.gameobjects;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

/**
 * Items can be found on the map and picked up.
 * 
 * @author janni-futz
 * 
 */
public abstract class Item extends GameObject {
	/**
	 * The position on the X-Axis
	 * 
	 * @see #getPositionX()
	 */
	private float positionX = 0f;

	/**
	 * The position on the Y-Axis
	 * 
	 * @see #getPositionY()
	 */
	private float positionY = 0f;

	/** The Items' sprite */
	private Image sprite;

	/**
	 * The constructor of the Item.
	 * 
	 * @param texture
	 *            The Items' sprite
	 * @param locX
	 *            The Items' location on the X-Axis
	 * @param locY
	 *            The Items' location on the Y-Axis
	 */
	public Item(Image texture, int locX, int locY) {
		this.sprite = texture;
		this.positionX = locX;
		this.positionY = locY;
	}

	/**
	 * Get the Items' position on the X-Axis.
	 * 
	 * @return The Items' position on the X-Axis
	 */
	public float getPositionX() {
		return positionX;
	}

	/**
	 * Set the items' position on the X-Axis
	 * 
	 * @param positionX
	 *            The new position on the X-Axis.
	 */
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	/**
	 * Get the Items' position on the Y-Axis.
	 * 
	 * @return The Items' position on the Y-Axis
	 */
	public float getPositionY() {
		return positionY;
	}

	/**
	 * Set the items' position on the Y-Axis
	 * 
	 * @param positionY
	 *            The new position on the Y-Axis.
	 */
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	/**
	 * Get the Items' sprite.
	 * 
	 * @return The Items' sprite
	 */
	public Image getImage() {
		return sprite;
	}

	/**
	 * Set the Items' sprite.
	 * 
	 * @param image
	 *            The new sprite
	 */
	public void setImage(Image image) {
		this.sprite = image;
	}

	/**
	 * Draw the Items' sprite onto the screen.
	 */
	public void draw() {
		this.sprite.draw((int) this.positionX, (int) this.positionY);
	}

	/**
	 * Get the Items' sprites' width.
	 * 
	 * @return The Items' sprites' width
	 */
	@Override
	public int getWidth() {
		return this.sprite.getWidth();
	}

	/**
	 * Get the Items' sprites' height.
	 * 
	 * @return The Items' sprites' height
	 */
	@Override
	public int getHeight() {
		return this.sprite.getHeight();
	}

	@Override
	public abstract void onUpdate(GameContainer gc, int delta, Map map, Game game);

}
