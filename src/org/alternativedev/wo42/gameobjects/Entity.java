package org.alternativedev.wo42.gameobjects;

import org.alternativedev.wo42.Game;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 * GameObject describing all living and/or interactive objects
 * 
 * @author janni-futz
 * 
 */
public abstract class Entity extends GameObject {
	/** The Animations of the Entity */
	protected Animation sprite, movementUp, movementLeft, movementDown,
			movementRight;

	/** The Entities' Polygon */
	protected Polygon poly = new Polygon();

	/** The length of one frame from the Animations */
	private int[] movementDuration;

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

	protected boolean deleteRequested = false;

	/**
	 * Constructs a new entity
	 * 
	 * @param locX
	 *            X-Location of the Entity
	 * @param locY
	 *            Y-Location of the Entity
	 * @param gfxUp
	 *            The Animation of the Entity moving upwards
	 * @param gfxDown
	 *            The Animation of the Entity moving downwards
	 * @param gfxLeft
	 *            The Animation of the Entity moving to the left
	 * @param gfxRight
	 *            The Animation of the Entity moving to the right
	 * @param dur
	 *            The duration of the Animations
	 */
	public Entity(int locX, int locY, Image[] gfxUp, Image[] gfxDown,
			Image[] gfxLeft, Image[] gfxRight, int[] dur) {
		this.movementUp = new Animation(gfxUp, dur, false);
		this.movementDown = new Animation(gfxDown, dur, false);
		this.movementLeft = new Animation(gfxLeft, dur, false);
		this.movementRight = new Animation(gfxRight, dur, false);
		this.sprite = this.movementRight;
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
	 * Get the entities' Sprite
	 * 
	 * @return The sprite
	 */
	public Animation getSprite() {
		return sprite;
	}

	/**
	 * Set the sprite of the entity
	 * 
	 * @param sprite
	 *            The new sprite
	 */
	public void setSprite(Animation sprite) {
		this.sprite = sprite;
	}

	/**
	 * Get the duration of the entities' movement
	 * 
	 * @return The duration
	 */
	public int[] getMovementDuration() {
		return movementDuration;
	}

	/**
	 * Set the the duration of the entities' movement
	 * 
	 * @param movementDuration
	 *            the new Duration
	 */
	public void setMovementDuration(int[] movementDuration) {
		this.movementDuration = movementDuration;
	}

	/**
	 * Get the upwards animation
	 * 
	 * @return The upwards animation
	 */
	public Animation getMovementUp() {
		return movementUp;
	}

	/**
	 * Set the upwards animation
	 * 
	 * @param movementUp
	 *            The new upwards animation
	 */
	public void setMovementUp(Animation movementUp) {
		this.movementUp = movementUp;
	}

	/**
	 * Get the downwards animation
	 * 
	 * @return The downwards animation
	 */
	public Animation getMovementDown() {
		return movementDown;
	}

	/**
	 * Set the downwards animation
	 * 
	 * @param movementDown
	 *            The new downwards animation
	 */
	public void setMovementDown(Animation movementDown) {
		this.movementDown = movementDown;
	}

	/**
	 * Get the animation to the left
	 * 
	 * @return The animation to the left
	 */
	public Animation getMovementLeft() {
		return movementLeft;
	}

	/**
	 * Set the animation to the left
	 * 
	 * @param movementLeft
	 *            The new animation to the left
	 */
	public void setMovementLeft(Animation movementLeft) {
		this.movementLeft = movementLeft;
	}

	/**
	 * Get the animation to the right
	 * 
	 * @return The animation to the right
	 */
	public Animation getMovementRight() {
		return movementRight;
	}

	/**
	 * Set the animation to the right
	 * 
	 * @param movementRight
	 *            The new animation to the left
	 */
	public void setMovementRight(Animation movementRight) {
		this.movementRight = movementRight;
	}

	/**
	 * Get the entities' position on the Y-Axis
	 * 
	 * @return The entities' position on the Y-Axis
	 */
	public float getPositionY() {
		return positionY;
	}

	/**
	 * Set the entities' position on the Y-Axis
	 * 
	 * @param positionY
	 *            The new Position on the Y-Axis
	 */
	public void setPositionY(float positionY) {
		this.positionY = positionY;
		updatePoly();
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
	 * Set the entities' position on the X-Axis
	 * 
	 * @param positionX
	 *            The new Position on the X-Axis
	 */
	public void setPositionX(float positionX) {
		this.positionX = positionX;
		updatePoly();
	}

	/**
	 * Draw the entities' sprite on the screen.
	 */
	public void draw() {
		this.sprite.draw((int) this.positionX, (int) this.positionY);
	}

	/**
	 * Draw the entities' sprite on the screen.
	 * 
	 * @param g
	 *            the Games' Graphics
	 */
	public void draw(Graphics g, Game game) {
		this.draw();
	}

	/**
	 * Draw the entities' sprite on the screen.
	 * 
	 * @param addX
	 *            X-Modifier
	 * @param addY
	 *            Y-Modifier
	 */
	public void draw(int addX, int addY) {
		this.sprite.draw((int) this.positionX + addX, (int) this.positionY
				+ addY);
	}

	/**
	 * Get the sprites' Width
	 * 
	 * @return The sprites' Width
	 */
	@Override
	public int getWidth() {
		return this.sprite.getWidth();

	}

	/**
	 * Get the sprites' Height
	 * 
	 * @return The sprites' Height
	 */
	@Override
	public int getHeight() {
		return this.sprite.getHeight();

	}

	/**
	 * Get the entities' Polygon
	 * 
	 * @return The entities' Polygon
	 */
	public Polygon getPoly() {
		return poly;
	}

	/**
	 * Set the entities' Polygon
	 * 
	 * @param poly
	 *            The new Polygon
	 */
	public void setPoly(Polygon poly) {
		this.poly = poly;
	}

	/**
	 * Update the Entities Polygon.
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


	public boolean deleteRequested() {
		return this.deleteRequested;
	}

}
