package org.alternativedev.wo42.gameobjects;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

/**
 * GameObject describing a place the player can't walk at.
 * 
 * @author janni-futz
 * 
 */
public class BlockingField extends GameObject {
	/** Width and Height of the BlockingField */
	private int w, h;

	/** The form of the BlockingField as a Polygon */
	Polygon poly = new Polygon();

	/**
	 * Get the Polygon of the BlockingField
	 * 
	 * @return The BlockingFields' Polygon
	 */
	public Polygon getPoly() {
		return poly;
	}

	/**
	 * Construct a new BlockingField
	 * 
	 * @param x
	 *            The Position on the X-Axis
	 * @param y
	 *            The Position on the Y-Axis
	 * @param w
	 *            The Width
	 * @param h
	 *            The Height
	 */
	public BlockingField(int x, int y, int w, int h) {
		this.setPositionX(x);
		this.setPositionY(y);
		this.w = w;
		this.h = h;
		this.updatePoly();
	}

	/**
	 * Get the BlockingFields' Width
	 * 
	 * @return The BlockingFields' Width
	 */
	@Override
	public int getWidth() {
		return w;
	}

	/**
	 * Get the BlockingFields' Height
	 * 
	 * @return The BlockingFields' Height
	 */
	@Override
	public int getHeight() {
		return h;
	}

	/**
	 * Draw the Polygon on the Screen (Debug Purposes)
	 * 
	 * @param g
	 *            The Graphics to be drawn on
	 */
	public void draw(Graphics g) {
		g.draw(poly);
	}

	/**
	 * Update the BlockingFields' Polygon
	 */
	private void updatePoly() {
		this.poly = new Polygon();
		poly.addPoint(this.getPositionX(), this.getPositionY());
		poly.addPoint(this.getPositionX(),
				this.getHeight() + this.getPositionY());
		poly.addPoint(this.getPositionX() + this.getWidth(), this.getHeight()
				+ this.getPositionY());
		poly.addPoint(this.getPositionX() + this.getWidth(),
				this.getPositionY());
	}

	/**
	 * Set the BlockingFields' Position on the X-Axis
	 * 
	 * @param pos
	 *            The new Position on the X-Axis
	 */
	@Override
	public void setPositionX(float pos) {
		super.setPositionX(pos);
		updatePoly();

	}

	/**
	 * Set the BlockingFields' Position on the Y-Axis
	 * 
	 * @param pos
	 *            The new Position on the Y-Axis
	 */
	@Override
	public void setPositionY(float pos) {
		super.setPositionY(pos);
		updatePoly();

	}

	public void onUpdate(GameContainer gc, int delta, Map map, Game game) {
		updatePoly();
	}

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		// TODO Auto-generated method stub
		
	}

}
