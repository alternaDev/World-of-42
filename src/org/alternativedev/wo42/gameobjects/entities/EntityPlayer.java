package org.alternativedev.wo42.gameobjects.entities;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.Settings;
import org.alternativedev.wo42.enums.GameState;
import org.alternativedev.wo42.enums.MoveDirection;
import org.alternativedev.wo42.enums.Scrolling;
import org.alternativedev.wo42.gameobjects.BlockingField;
import org.alternativedev.wo42.gameobjects.Entity;
import org.alternativedev.wo42.gameobjects.GameObject;
import org.alternativedev.wo42.gameobjects.Spell;
import org.alternativedev.wo42.gameobjects.items.ItemCoin;
import org.alternativedev.wo42.gameobjects.spells.SpellBlizz;
import org.alternativedev.wo42.gameobjects.spells.SpellFireball;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

/**
 * The player that's being controlled by the Player.
 * 
 * @author janni-futz
 * 
 */
public class EntityPlayer extends Entity {
	/** The MoveDirection the player's moving to right now */
	private MoveDirection moveDirection = MoveDirection.RIGHT;
	/** Initiate the spells for a shorter loading time, when they are used first */
	private int currentHealth = 100, currentMagicka = 100;
	private boolean justFired;
	private boolean locked;

	/**
	 * Constructs a new Player.
	 * 
	 * @param locX
	 *            The player's location on the X-Axis
	 * @param locY
	 *            The player's location on the X-Axis
	 * @throws SlickException
	 */
	public EntityPlayer(int locX, int locY) throws SlickException {
		super(locX, locY, new Image[] { new Image("data/mnv2_bk1.png"),
				new Image("data/mnv2_bk2.png") },
				new Image[] { new Image("data/mnv2_fr1.png"),
						new Image("data/mnv2_fr2.png") }, new Image[] {
						new Image("data/mnv2_lf1.png"),
						new Image("data/mnv2_lf2.png") }, new Image[] {
						new Image("data/mnv2_rt1.png"),
						new Image("data/mnv2_rt2.png") },
				new int[] { 300, 300 });

		this.setPositionX(locX);
		this.setPositionY(locY);

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
	 * Constructs a Player at (0,0).
	 * 
	 * @throws SlickException
	 */
	public EntityPlayer() throws SlickException {
		super(0, 0, new Image[] { new Image("data/mnv2_bk1.png"),
				new Image("data/mnv2_bk2.png") },
				new Image[] { new Image("data/mnv2_fr1.png"),
						new Image("data/mnv2_fr2.png") }, new Image[] {
						new Image("data/mnv2_lf1.png"),
						new Image("data/mnv2_lf2.png") }, new Image[] {
						new Image("data/mnv2_rt1.png"),
						new Image("data/mnv2_rt2.png") },
				new int[] { 300, 300 });
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
	 * Calculates the Limit at the borders of the Window.
	 * 
	 * @param WIDTH
	 *            The Windows' Width
	 * @param HEIGHT
	 *            The Windows' Height
	 */
	public void borderLimits(int WIDTH, int HEIGHT) {
		if (this.getPositionY() > HEIGHT - Settings.getPlayerDistance()) {
			this.setPositionY(HEIGHT - Settings.getPlayerDistance());
		}
		if (this.getPositionX() > WIDTH - Settings.getPlayerDistance()) {
			this.setPositionX(WIDTH - Settings.getPlayerDistance());
		}
		if (this.getPositionX() < Settings.getPlayerDistance())
			this.setPositionX(Settings.getPlayerDistance());
		if (this.getPositionY() < Settings.getPlayerDistance())
			this.setPositionY(Settings.getPlayerDistance());
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
		this.moveDirection = MoveDirection.UP;
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
		this.moveDirection = MoveDirection.DOWN;
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
		this.moveDirection = MoveDirection.LEFT;
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
		this.moveDirection = MoveDirection.RIGHT;
	}

	/**
	 * Knockback the player at collision
	 * 
	 */
	public void knockback() {

		switch (moveDirection) {
		case UP:
			this.setPositionY(this.getPositionY() + 0.5f);
			break;
		case DOWN:
			this.setPositionY(this.getPositionY() - 0.5f);
			break;
		case LEFT:
			this.setPositionX(this.getPositionX() + 0.5f);
			break;
		case RIGHT:
			this.setPositionX(this.getPositionX() - 0.5f);
			break;
		}

	}

	/**
	 * Update the players' polygon
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
	 * Set the Players' position on the X-Axis and update the poly.
	 */
	@Override
	public void setPositionX(float pos) {
		super.setPositionX(pos);
		updatePoly();
	}

	public MoveDirection getMovementDirection() {
		return moveDirection;

	}

	/**
	 * Set the Players' position on the Y-Axis and update the poly.
	 */
	@Override
	public void setPositionY(float pos) {
		super.setPositionY(pos);
		updatePoly();
	}

	public void setHealth(int i) {
		if (i > 100)
			currentHealth = 100;
		else if (i <= 0)
			currentHealth = 0;
		else
			currentHealth = i;
	}

	public int getHealth() {
		return currentHealth;
	}

	public void setMagicka(int i) {
		if (i > 100)
			currentMagicka = 100;
		else if (i <= 0)
			currentMagicka = 0;
		else
			currentMagicka = i;
	}

	public int getMagicka() {
		return currentMagicka;
	}

	public void setLock(boolean locked) {
		this.locked = locked;
	}

	public boolean getLock() {
		return locked;
	}

	/**
	 * Draw the Player onto the Screen
	 */
	@Override
	public void draw(Graphics g, Game game) {
		super.draw();
	}

	private boolean collidedWithBF = false;

	@Override
	public void onUpdate(GameContainer gc, int delta, Map map, Game game)
			throws SlickException {
		if (this.getHealth() <= 0)
			game.setGameState(GameState.DEAD);

		this.borderLimits(Settings.getWIDTH(), Settings.getHEIGHT());
		Input input = new Input(0);
		if (!this.getLock()) {
			input = gc.getInput();
			map.getScroller().setScrolling(Scrolling.RESET);
			if (input.isKeyDown(Input.KEY_UP) && !collidedWithBF) {
				this.moveUp(delta, Settings.getPlayerMovementSpeed());
				map.getScroller().setScrolling(Scrolling.UP);
			} else if (input.isKeyDown(Input.KEY_DOWN) && !collidedWithBF) {
				this.moveDown(delta, Settings.getPlayerMovementSpeed());
				map.getScroller().setScrolling(Scrolling.DOWN);

			} else if (input.isKeyDown(Input.KEY_LEFT) && !collidedWithBF) {
				this.moveLeft(delta, Settings.getPlayerMovementSpeed());
				map.getScroller().setScrolling(Scrolling.LEFT);

			} else if (input.isKeyDown(Input.KEY_RIGHT) && !collidedWithBF) {
				this.moveRight(delta, Settings.getPlayerMovementSpeed());
				map.getScroller().setScrolling(Scrolling.RIGHT);

			} else if (input.isKeyDown(Input.KEY_ESCAPE)) {
				game.setGameState(GameState.MENU);
			}
			if (input.isKeyDown(Input.KEY_F)) {
				fireSpell(
						new SpellFireball((int) this.getPositionX(),
								(int) this.getPositionY(),
								this.getMovementDirection()), map);

			}
			if (input.isKeyDown(Input.KEY_B)) {
				fireSpell(
						new SpellBlizz((int) this.getPositionX(),
								(int) this.getPositionY(),
								this.getMovementDirection()), map);

			}

			magRecr++;
			if (magRecr >= 16) {
				magRecr = 0;
				if (!justFired)
					this.setMagicka(this.getMagicka() + 5);
			}

			fired++;
			if (fired >= 60) {
				fired = 0;
				justFired = false;
			}
			collidedWithBF = false;
		}

	}

	private boolean saying;
	private String text;

	public void say(String s) {
		saying = true;
		text = s;
	}

	public void stopSaying() {
		saying = false;
	}

	public void drawSpeechBubble(Graphics g, Game game) {
		if (saying) {
			int xMod = 40, yMod = 5;
			int y = (int) this.getPositionY() - g.getFont().getHeight(text)
					- 20;
			int x = (int) this.getPositionX() - g.getFont().getWidth(text) / 2;

			if (x < 0)
				x = 0;
			if (x > Settings.getWIDTH() - g.getFont().getWidth(text) - xMod)
				x = Settings.getWIDTH() - g.getFont().getWidth(text) - xMod;

			if (y < 0)
				y = 0;
			if (y > Settings.getHEIGHT() - g.getFont().getHeight(text) - yMod)
				y = Settings.getHEIGHT() - g.getFont().getHeight(text) - yMod;

			g.setColor(org.newdawn.slick.Color.white);

			g.fillRoundRect(x, y, g.getFont().getWidth(text) + 40, g.getFont()
					.getHeight(text) + 5, 15);
			g.setColor(org.newdawn.slick.Color.black);

			g.drawString(text, x + 10, y);
		}
	}

	private int fired = 0;

	private void fireSpell(Spell spell, Map map) {
		if (!justFired) {
			if (this.getMagicka() >= spell.getManaNeeds()) {
				this.setMagicka(this.getMagicka() - spell.getManaNeeds());
				map.addGameObject(spell);
			}
			justFired = true;
		}
	}

	int magRecr = 0;
	int spellTime = 0;

	private void collideWithCoin(ItemCoin coin) {
		coin.setPositionX((float) Math.random() * Settings.getWIDTH());
		coin.setPositionY((float) Math.random() * Settings.getHEIGHT());

		this.setHealth(this.getHealth() + 10);
	}

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		if (gameObject instanceof ItemCoin)
			collideWithCoin((ItemCoin) gameObject);
		if (gameObject instanceof BlockingField) {
			this.knockback();
			collidedWithBF = true;
		}
		if (gameObject instanceof EntityNPC) {
			collidedWithBF = true;
			this.knockback();
		}

	}
}
