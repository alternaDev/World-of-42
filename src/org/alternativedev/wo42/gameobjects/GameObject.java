package org.alternativedev.wo42.gameobjects;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Basic GameObject. Everything which appears on the screen is a GameObject
 * 
 * @author janni-futz
 * 
 */
public abstract class GameObject {
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

	/**
	 * Get the entities' position on the Y-Axis
	 * 
	 * @return The entities' position on the Y-Axis
	 */
	public float getPositionY() {
		return positionY;
	}

	/**
	 * Set the GameObjects position on the Y-Axis.
	 * 
	 * @param positionY
	 *            The new Position on the Y-Axis.
	 */
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	/**
	 * Get the entities' position on the X-Axis
	 * 
	 * @return The entities' position on the X-Axis
	 */
	public float getPositionX() {
		return positionX;
	}

	/**
	 * Set the GameObjects position on the X-Axis.
	 * 
	 * @param positionX
	 *            The new Position on the X-Axis.
	 */
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	/**
	 * Get the GameObjects' width.
	 * 
	 * @return The GameObjects' width.
	 */
	public abstract int getWidth();

	/**
	 * Get the GameObjects' height.
	 * 
	 * @return The GameObjects' height.
	 */
	public abstract int getHeight();

	/**
	 * Detects if the GameObject is colliding with another GameObject.
	 * 
	 * @param gO
	 *            The other GameObject
	 * @return Returns if the GameObject is colliding with the other one (true)
	 *         or not (false).
	 */
	public boolean collidesWith(GameObject gO) {
		Rectangle first = new Rectangle(this.getPositionX(),
				this.getPositionY(), this.getWidth(), this.getHeight());
		Rectangle second = new Rectangle(gO.getPositionX(), gO.getPositionY(),
				gO.getWidth(), gO.getHeight());

		if (first.intersects(second) || second.intersects(first))
			return true;
		return false;

	}

	public abstract void onUpdate(GameContainer gc, int delta, Map map,
			Game game) throws SlickException;

	public abstract void onCollideWith(GameObject gameObject, int delta);

}
