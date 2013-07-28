package org.alternativedev.wo42.gameobjects;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.enums.MoveDirection;
import org.alternativedev.wo42.gameobjects.entities.EntityNPC;
import org.alternativedev.wo42.gameobjects.entities.EntityPlayer;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Spell extends GameObject {
	public final float SPELL_SPEED = 0.3f;

	private float posX, posY;
	private Animation spellLeft, spellRight, spellUp, spellDown, sprite;
	private MoveDirection moveDirection;

	public Spell(Image[] gfxUp, Image[] gfxDown, Image[] gfxLeft,
			Image[] gfxRight, int[] dur, MoveDirection moveDirection,
			int startX, int startY) {
		spellUp = new Animation(gfxUp, dur, false);
		spellDown = new Animation(gfxDown, dur, false);
		spellLeft = new Animation(gfxLeft, dur, false);
		spellRight = new Animation(gfxRight, dur, false);
		this.moveDirection = moveDirection;
		this.sprite = spellUp;
		posX = startX;
		posY = startY;
	}

	public void draw(Game game) {
		this.sprite.draw(posX, posY);
	}

	/**
	 * Get the entities' position on the Y-Axis
	 * 
	 * @return The entities' position on the Y-Axis
	 */
	public float getPositionY() {
		return posY;
	}

	/**
	 * Set the entities' position on the Y-Axis
	 * 
	 * @param positionY
	 *            The new Position on the Y-Axis
	 */
	public void setPositionY(float positionY) {
		this.posY = positionY;
	}

	/**
	 * Get the entities' position on the X-Axis
	 * 
	 * @return The entities' position on the X-Axis
	 */
	public float getPositionX() {
		return posX;
	}

	/**
	 * Set the entities' position on the X-Axis
	 * 
	 * @param positionX
	 *            The new Position on the X-Axis
	 */
	public void setPositionX(float positionX) {
		this.posX = positionX;
	}

	@Override
	public int getWidth() {
		return this.sprite.getWidth();
	}

	@Override
	public int getHeight() {
		return this.sprite.getHeight();
	}

	@Override
	public void onUpdate(GameContainer gc, int delta, Map map, Game game)
			throws SlickException {

		switch (this.moveDirection) {
		case UP:
			sprite = spellUp;
			break;
		case DOWN:
			sprite = spellDown;
			break;
		case LEFT:
			sprite = spellLeft;
			break;
		case RIGHT:
			sprite = spellRight;
			break;

		}

		switch (this.moveDirection) {
		case UP:
			this.setPositionY(this.getPositionY() - delta * SPELL_SPEED);
			break;
		case DOWN:
			this.setPositionY(this.getPositionY() + delta * SPELL_SPEED);
			break;
		case LEFT:
			this.setPositionX(this.getPositionX() - delta * SPELL_SPEED);
			break;
		case RIGHT:
			this.setPositionX(this.getPositionX() + delta * SPELL_SPEED);
			break;
		}

		this.sprite.update(delta);
	}

	public abstract int getManaNeeds();

	public abstract int getLPDiff();

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		if (gameObject instanceof EntityNPC)
			collideWithEntityNPC((EntityNPC) gameObject);
		if (!(gameObject instanceof EntityPlayer)
				&& !(gameObject instanceof Spell))
			deleteRequested = true;
	}

	private void collideWithEntityNPC(EntityNPC gameObject) {
		((EntityNPC) gameObject).setHealth(((EntityNPC) gameObject).getHealth()
				- this.getLPDiff());

	}

	protected boolean deleteRequested = false;

	public boolean deleteRequested() {
		return deleteRequested;
	}
}
