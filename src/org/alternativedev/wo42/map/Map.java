package org.alternativedev.wo42.map;

import java.util.ArrayList;
import java.util.Iterator;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.Settings;
import org.alternativedev.wo42.enums.NPCMovement;
import org.alternativedev.wo42.gameobjects.BlockingField;
import org.alternativedev.wo42.gameobjects.Entity;
import org.alternativedev.wo42.gameobjects.GameObject;
import org.alternativedev.wo42.gameobjects.Item;
import org.alternativedev.wo42.gameobjects.Spell;
import org.alternativedev.wo42.gameobjects.entities.EntityNPC;
import org.alternativedev.wo42.gameobjects.entities.EntityPlayer;
import org.alternativedev.wo42.gameobjects.entities.npcs.EntityNPCHostileEvilguy;
import org.alternativedev.wo42.gameobjects.entities.npcs.EntityNPCPomegranate;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

/**
 * Class describing a whole Level including the Player and so on
 * 
 * @author janni-futz
 * 
 */
public abstract class Map extends TiledMapPlus {

	public Music mainMusic;
	public Music deathMusic;
	/** ArrayList containing all the Entities on the Map */
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	/** The Main PlayerEntity on the Map */
	private EntityPlayer player;

	/** Scroller handling the Scrolling of the Map */
	private Scroll scroller;
	/**
	 * The position on the X-Axis
	 * 
	 * @see #getMapX()
	 */
	private float mapX = 0;

	/**
	 * The position on the Y-Axis
	 * 
	 * @see #getMapY()
	 */
	private float mapY = 0;

	/** The players' spawnpoint */
	private int playerSpawnX, playerSpawnY;

	/**
	 * Constructs a new map
	 * 
	 * @param ref
	 *            tua mater
	 * @throws SlickException
	 */
	public Map(String ref, String mainMusicRef, String deathMusicRef)
			throws SlickException {
		super(ref, "data");

		int id = this.getObjectGroupID("Collision Objects");
		for (int i = 0; i < this.getObjectCount(id); i++) {
			gameObjects.add(new BlockingField(this.getObjectX(id, i), this
					.getObjectY(id, i), this.getObjectWidth(id, i), this
					.getObjectHeight(id, i)));
		}
		Log.debug("Groups: " + this.getObjectGroupCount());

		id = this.getObjectGroupID("Player");

		playerSpawnX = this.convertToMapX(this.getObjectX(id, 0));
		playerSpawnY = this.convertToMapY(this.getObjectY(id, 0));

		this.player = new EntityPlayer(playerSpawnX, playerSpawnY);

		loadNPCs();

		setScroller(new Scroll());
		this.mainMusic = new Music(mainMusicRef);
		this.deathMusic = new Music(deathMusicRef);
		this.music = mainMusic;
		music.play();
	}

	private void loadNPCs() throws SlickException {
		int gId = this.getObjectGroupID("NPCs");
		for (int i = 0; i < this.getObjectCount(gId); i++) {
			EntityNPC enemy = null;
			String name = this.getObjectName(gId, i);
			int x = this.getObjectX(gId, i);
			int y = this.getObjectY(gId, i);

			if (name.equals("Pomegranate")) {
				String dialogFileName = "data/dialogs/"
						+ this.getObjectProperty(gId, i, "dialog_file", "");
				enemy = new EntityNPCPomegranate(x, y, dialogFileName, this);
			} else if (name.equals("EvilGuy")) {
				enemy = new EntityNPCHostileEvilguy(x, y);
				enemy.say(this.getObjectProperty(gId, i, "saying", "").replace("\\n", "\n"));
			}
			
			Log.debug(name);

			NPCMovement movement = NPCMovement.NONE;

			String movementString = this.getObjectProperty(gId, i, "movement",
					"NONE");

			for (NPCMovement mov : NPCMovement.values()) {
				if (mov.name().equalsIgnoreCase(movementString))
					movement = mov;
			}

			float speed = Float.valueOf(this.getObjectProperty(gId, i, "speed",
					"1.0"));
			if (enemy != null)
				enemy.setMove(movement, speed);

			if (enemy != null)
				this.gameObjects.add(enemy);
		}
	}

	public void stopMusic() {
		this.mainMusic.stop();
	}

	public void startMusic() {
		this.music.play();
	}

	public boolean musicPlaying() {
		return this.music.playing();
	}

	/**
	 * Get all Entities on the Map
	 * 
	 * @return all Entities on the Map
	 */
	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}

	/**
	 * Get the Maps' coordinates on the X-Axis.
	 * 
	 * @return The Maps' coordinates on the X-Axis
	 */
	public float getMapX() {
		return mapX;
	}

	/**
	 * Get the Maps' coordinates on the Y-Axis.
	 * 
	 * @return The Maps' coordinates on the Y-Axis
	 */
	public float getMapY() {
		return mapY;
	}

	/**
	 * Add an Entity to the Map
	 * 
	 * @param ent
	 *            The Entity to add
	 */
	public void addGameObject(GameObject ent) {
		gameObjects.add(ent);
	}

	/**
	 * Get the PlayerEntity.
	 * 
	 * @return The PlayerEntity
	 */
	public EntityPlayer getPlayer() {
		return player;
	}

	/**
	 * Change the Maps' and all Entities, Items' and BLockingFields' position
	 * for scrolling
	 * 
	 * @param delta
	 *            The delta thingie
	 */
	private void scroll(int delta) {
		int mod = (int) (Settings.getPlayerMovementSpeed() * delta);

		if (scroller.isScrollingLeft()
				&& this.getPlayer().getPositionX() <= Settings
						.getPlayerDistance())
			scrollLeft(delta, mod);
		else if (scroller.isScrollingRight()
				&& this.getPlayer().getPositionX() >= Settings.getWIDTH()
						- Settings.getPlayerDistance())
			scrollRight(delta, mod);
		else if (scroller.isScrollingDown()
				&& this.getPlayer().getPositionY() >= Settings.getHEIGHT()
						- Settings.getPlayerDistance())
			scrollDown(delta, mod);
		else if (scroller.isScrollingUp()
				&& this.getPlayer().getPositionY() <= Settings
						.getPlayerDistance())
			scrollUp(delta, mod);
	}

	private void scrollRight(int delta, int mod) {
		this.setMapX((int) (this.getMapX() - mod));
		this.setMapY((int) this.getMapY());

		for (int i = 0; i < this.gameObjects.size(); i++) {
			gameObjects.get(i).setPositionX(
					gameObjects.get(i).getPositionX() - mod);
		}
	}

	private void scrollLeft(int delta, int mod) {
		this.setMapX((int) (this.getMapX() + mod));
		this.setMapY((int) this.getMapY());

		for (int i = 0; i < this.gameObjects.size(); i++) {
			gameObjects.get(i).setPositionX(
					gameObjects.get(i).getPositionX() + mod);
		}
	}

	private void scrollUp(int delta, int mod) {
		this.setMapX((int) this.getMapX());
		this.setMapY((int) (this.getMapY() + mod));

		for (int i = 0; i < this.gameObjects.size(); i++) {
			gameObjects.get(i).setPositionY(
					gameObjects.get(i).getPositionY() + mod);
		}
	}

	private void scrollDown(int delta, int mod) {
		this.setMapX((int) (this.getMapX()));
		this.setMapY((int) (this.getMapY() - mod));

		for (int i = 0; i < this.gameObjects.size(); i++) {
			gameObjects.get(i).setPositionY(
					gameObjects.get(i).getPositionY() - mod);
		}
	}

	/**
	 * Draw all the Entities. Items, BlockingFields (Debug), the Player and the
	 * map itself onto the screen.
	 * 
	 * @param g
	 *            The Games' Graphics
	 */
	int dieTime = 0;
	private Music music;

	public void drawAll(Graphics g, Game game) {
		Iterator<GameObject> itgo;
		this.render((int) mapX, (int) mapY);
		player.draw(g, game);

		itgo = gameObjects.iterator();

		while (itgo.hasNext()) {
			GameObject next = itgo.next();
			if (next instanceof Entity)
				((Entity) next).draw(g, game);
			else if (next instanceof Spell) {
				((Spell) next).draw(game);
			} else if (next instanceof Item) {
				((Item) next).draw();
			}
		}

		this.render((int) this.getMapX(), (int) this.getMapY(), 1);

		itgo = gameObjects.iterator();

		while (itgo.hasNext()) {
			GameObject next = itgo.next();
			if (next instanceof EntityNPC)
				((EntityNPC) next).drawSpeechBubble(g, game);
		}

		player.drawSpeechBubble(g, game);
	}

	/**
	 * Get the Maps' Width.
	 * 
	 * @return The Maps' Width
	 */
	public int getMapWidth() {
		return this.getWidth() * this.getTileWidth();
	}

	/**
	 * Get the Maps' Height.
	 * 
	 * @return The Maps' Height
	 */
	public int getMapHeight() {
		return this.getHeight() * this.getTileHeight();
	}

	/**
	 * Set the Maps' X-Coordinates
	 * 
	 * @param mapX
	 *            The new coordinates on the X-Axis
	 */
	public void setMapX(float mapX) {
		this.mapX = mapX;
	}

	/**
	 * Set the Maps' Y-Coordinates
	 * 
	 * @param mapY
	 *            The new coordinates on the Y-Axis
	 */
	public void setMapY(float mapY) {
		this.mapY = mapY;
	}

	/**
	 * Convert the Coordinates got from the window (upper left corner = 0) to
	 * Coordinates on the map.
	 * 
	 * @param windowX
	 *            The window Coordinates (x)
	 * @return Coordinates on the map
	 */
	public int convertToMapX(int windowX) {
		return windowX - (int) this.getMapX();

	}

	public int convertToMapY(int windowY) {
		return windowY - (int) this.getMapY();

	}

	public void update(GameContainer gc, int delta, Game game)
			throws SlickException {
		Iterator<GameObject> itgo = gameObjects.iterator();
		while (itgo.hasNext()) {
			GameObject ent = itgo.next();
			ent.onUpdate(gc, (int) delta, this, game);
			Iterator<GameObject> dm = colliding(ent).iterator();
			while (dm.hasNext()) {
				GameObject gO = dm.next();
				if (gO != null) {
					gO.onCollideWith(ent, delta);
					ent.onCollideWith(gO, delta);
				}
			}
			if (ent instanceof Spell)
				if (((Spell) ent).deleteRequested()) {
					itgo.remove();
					System.out.println("Spell removed!");
				}

			if (ent instanceof Entity)
				if (((Entity) ent).deleteRequested()) {
					itgo.remove();
					System.out.println("Entity removed!");
				}
		}

		this.player.onUpdate(gc, (int) delta, this, game);
		Iterator<GameObject> dm = colliding(this.player).iterator();
		while (dm.hasNext()) {
			GameObject gO = dm.next();
			if (gO != null) {
				gO.onCollideWith(this.player, delta);
				this.player.onCollideWith(gO, delta);
			}
		}

		/*
		 * if (scroller.isScrollingLeft() && this.getPlayer().getPositionX() <=
		 * Settings .getPlayerDistance()) this.scroll(this.getMapX() + mod,
		 * this.getMapY(), mod); else if (scroller.isScrollingRight() &&
		 * this.getPlayer().getPositionX() >= Settings.getWIDTH() -
		 * Settings.getPlayerDistance()) this.scroll(this.getMapX() - mod,
		 * this.getMapY(), mod); else if (scroller.isScrollingDown() &&
		 * this.getPlayer().getPositionY() >= Settings.getHEIGHT() -
		 * Settings.getPlayerDistance()) this.scroll(this.getMapX(),
		 * this.getMapY() - mod, mod); else if (scroller.isScrollingUp() &&
		 * this.getPlayer().getPositionY() <= Settings .getPlayerDistance())
		 * this.scroll(this.getMapX(), this.getMapY() + mod, mod); // else //
		 * this.scroll(this.getMapX(), this.getMapY(), mod);
		 */
		this.scroll((int) delta);
	}

	public Scroll getScroller() {
		return scroller;
	}

	public void setScroller(Scroll scroller) {
		this.scroller = scroller;
	}

	public void onPlayerDeath() throws SlickException {
		if (music != this.deathMusic) {
			this.music.stop();
			this.music = this.deathMusic;
			music.play();
		}
	}

	private ArrayList<GameObject> colliding(GameObject gO) {
		ArrayList<GameObject> coll = new ArrayList<GameObject>();

		Iterator<GameObject> itsp = gameObjects.iterator();
		while (itsp.hasNext()) {
			GameObject dm = itsp.next();
			if (gO.collidesWith(dm))
				coll.add(dm);
		}

		if (this.getPlayer().collidesWith(gO))
			coll.add(this.player);

		if (coll.contains(gO))
			coll.remove(gO);

		return coll;
	}

	public void updatePaused(GameContainer gc, int delta, Game game)
			throws SlickException {
		// WHAT'S ACTUALLY THE SENSE OF THIS?! xD 42
	}

}
