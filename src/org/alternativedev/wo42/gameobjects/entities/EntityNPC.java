package org.alternativedev.wo42.gameobjects.entities;

import java.util.Random;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.Settings;
import org.alternativedev.wo42.enums.MoveDirection;
import org.alternativedev.wo42.enums.NPCMovement;
import org.alternativedev.wo42.gameobjects.BlockingField;
import org.alternativedev.wo42.gameobjects.Entity;
import org.alternativedev.wo42.gameobjects.GameObject;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * NPCEntity Class for Non Player Characters.
 * 
 * @author janni-futz
 * 
 */
// THIS IS RANDOM SHIT!!!!!111111einselftua!!111einselfmater
public abstract class EntityNPC extends Entity {

	private MoveDirection moveDirection;
	private NPCMovement movement;
	private int posX;
	private int posY;
	private float moveSpeed = 0.1f;
	private boolean isInterrupted;
	private boolean isKnockedBack;

	public EntityNPC(int locX, int locY, Image[] gfxUp, Image[] gfxDown,
			Image[] gfxLeft, Image[] gfxRight, int[] dur) {

		super(locX, locY, gfxUp, gfxDown, gfxLeft, gfxRight, dur);

		this.setPositionX(locX);
		this.setPositionY(locY);
		this.posX = locX;
		this.posY = locY;

	}

	public void doMovementUpdate(int delta, Map map) {

		if (this.movement.equals(NPCMovement.RECTANGLE)) {
			if (map.convertToMapX((int) this.getPositionX()) <= posX + 150
					&& map.convertToMapY((int) this.getPositionY()) <= posY + 5
					&& map.convertToMapY((int) this.getPositionY()) >= posY - 5
					&& !isInterrupted)
				this.moveRight(delta, moveSpeed);
			else if (map.convertToMapX((int) this.getPositionX()) >= posX + 150
					&& map.convertToMapY((int) this.getPositionY()) < posY + 150
					&& !isInterrupted)
				this.moveDown(delta, moveSpeed);
			else if (map.convertToMapX((int) this.getPositionX()) <= posX + 155
					&& map.convertToMapX((int) this.getPositionX()) > posX
					&& map.convertToMapY((int) this.getPositionY()) >= posY + 145
					&& map.convertToMapY((int) this.getPositionY()) <= posY + 155
					&& !isInterrupted)
				this.moveLeft(delta, moveSpeed);
			else if (map.convertToMapX((int) this.getPositionX()) < posX + 2
					&& map.convertToMapX((int) this.getPositionX()) > posX - 4
					&& map.convertToMapY((int) this.getPositionY()) <= posY + 170
					&& map.convertToMapY((int) this.getPositionY()) >= posY
					&& !isInterrupted)
				this.moveUp(delta, moveSpeed);
		} else if (movement.equals(NPCMovement.RANDOM) && !isInterrupted) {
			moveCount++;
			if (moveCount == 60 || isKnockedBack) {
				moveCount = 0;
				Random rand = new Random();
				num = rand.nextInt(4);
			}
			switch (num) {
			case 3:
				this.moveLeft(delta, moveSpeed);
				break;
			case 2:
				this.moveUp(delta, moveSpeed);
				break;
			case 1:
				this.moveRight(delta, moveSpeed);
				break;
			default:
				this.moveDown(delta, moveSpeed);

				break;
			}
		}

	}

	private int moveCount = 0, num;

	public void setMove(NPCMovement npcm, float speed) {
		this.moveSpeed = speed;
		this.movement = npcm;

	}

	/**
	 * Move the player UP
	 * 
	 * @param delta
	 *            The Time between the last and this frame.
	 * @param speed
	 *            The movement Speed
	 */
	public void moveUp(int delta, float speed) {
		sprite = this.movementUp;
		sprite.update(delta);
		this.setPositionY(this.getPositionY() - delta * speed);
		this.setMoveDirection(MoveDirection.UP);
	}

	public void setInterrupted(boolean b) {
		isInterrupted = b;
	}

	/**
	 * Move the player DOWN
	 * 
	 * @param delta
	 *            The Time between the last and this frame.
	 * @param speed
	 *            The movement Speed
	 */
	public void moveDown(int delta, float speed) {
		sprite = this.movementDown;
		sprite.update(delta);
		this.setPositionY(this.getPositionY() + delta * speed);
		this.setMoveDirection(MoveDirection.DOWN);
	}

	/**
	 * Move the player LEFT
	 * 
	 * @param delta
	 *            The Time between the last and this frame.
	 * @param speed
	 *            The movement Speed
	 */
	public void moveLeft(int delta, float speed) {
		sprite = this.movementLeft;
		sprite.update(delta);
		this.setPositionX(this.getPositionX() - delta * speed);
		this.setMoveDirection(MoveDirection.LEFT);
	}

	/**
	 * Move the player RIGHT
	 * 
	 * @param delta
	 *            The Time between the last and this frame.
	 * @param speed
	 *            The movement Speed
	 */
	public void moveRight(int delta, float speed) {
		sprite = this.movementRight;
		sprite.update(delta);
		this.setPositionX(this.getPositionX() + delta * speed);
		this.setMoveDirection(MoveDirection.RIGHT);
	}

	public MoveDirection getMoveDirection() {
		return moveDirection;
	}

	public void setMoveDirection(MoveDirection moveDirection) {
		this.moveDirection = moveDirection;
	}

	private boolean saying;
	private String text = "";
	private int health = this.getMaxHealth();

	public void say(String s) {
		saying = true;
		text = s;
	}

	public void stopSaying() {
		saying = false;
	}

	@Override
	public void draw(Graphics g, Game game) {
		super.draw();
		drawHealthBar(g);
	}

	public void drawSpeechBubble(Graphics g, Game game) {
		try {
			if (saying) {
				int xMod = 40, yMod = 5;
				int y = (int) this.getPositionY() - g.getFont().getHeight(text)
						- 20;
				int x = (int) this.getPositionX() - g.getFont().getWidth(text)
						/ 2;

				if (x < 0)
					x = 0;
				if (x > Settings.getWIDTH() - g.getFont().getWidth(text) - xMod)
					x = Settings.getWIDTH() - g.getFont().getWidth(text) - xMod;

				if (y < 0)
					y = 0;
				if (y > Settings.getHEIGHT() - g.getFont().getHeight(text)
						- yMod)
					y = Settings.getHEIGHT() - g.getFont().getHeight(text)
							- yMod;
				
				g.setColor(org.newdawn.slick.Color.white);

				g.fillRoundRect(x, y, g.getFont().getWidth(text) + 40, g
						.getFont().getHeight(text) + 5, 15);
				g.setColor(org.newdawn.slick.Color.black);

				g.drawString(text, x + 10, y);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}

	}

	@Override
	public void onUpdate(GameContainer gc, int delta, Map map, Game game)
			throws SlickException {
		this.doMovementUpdate(delta, map);

		if (this.getHealth() <= 0) {
			die();
		}
		isKnockedBack = false;

	}

	public void die() {
		System.out.println("Dieiengaisdh!");
		this.deleteRequested = true;
	}

	/**
	 * Knockback the player at collision
	 * 
	 */
	public void knockback(int delta) {

		switch (moveDirection) {
		case UP:
			this.setPositionY(this.getPositionY() + 2f);
			this.moveDown(delta, this.moveSpeed);
			this.setMoveDirection(MoveDirection.DOWN);
			break;
		case DOWN:
			this.setPositionY(this.getPositionY() - 2);
			this.moveUp(delta, this.moveSpeed);
			this.setMoveDirection(MoveDirection.UP);

			break;
		case LEFT:
			this.setPositionX(this.getPositionX() + 2f);
			this.moveRight(delta, this.moveSpeed);
			this.setMoveDirection(MoveDirection.RIGHT);

			break;
		case RIGHT:
			this.setPositionX(this.getPositionX() - 2f);
			this.moveLeft(delta, this.moveSpeed);
			this.setMoveDirection(MoveDirection.LEFT);

			break;
		}

	}

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		if (gameObject instanceof BlockingField) {
			this.knockback(delta);
			isKnockedBack = true;
		}
	}

	public int getHealth() {
		return this.health;
	}

	public abstract int getMaxHealth();

	public void setHealth(int i) {
		if (i > getMaxHealth())
			health = getMaxHealth();
		else if (i <= 0)
			health = 0;
		else
			health = i;

	}

	private void drawHealthBar(Graphics g) {
		int xLoc = (int) this.getPositionX() - this.getWidth() / 2 + 10;
		int yLoc = (int) this.getPositionY() + this.getHeight() + 5;
		int sizeX = 50, sizeY = 5;
		g.setColor(org.newdawn.slick.Color.red);
		g.fillRect(xLoc, yLoc, sizeX, sizeY);
		g.setColor(org.newdawn.slick.Color.green);
		g.fillRect(xLoc, yLoc,
				this.getHealth() * (sizeX / this.getMaxHealth()), sizeY);
	}
}
