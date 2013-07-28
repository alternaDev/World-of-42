package org.alternativedev.wo42;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.alternativedev.wo42.enums.GameState;
import org.alternativedev.wo42.gameobjects.entities.EntityPlayer;
import org.alternativedev.wo42.gui.GuiElementsRenderer;
import org.alternativedev.wo42.gui.guielements.GuiElementHealthBar;
import org.alternativedev.wo42.gui.guielements.GuiElementMagickaBar;
import org.alternativedev.wo42.gui.menu.menus.MenuDeath;
import org.alternativedev.wo42.gui.menu.menus.MenuHelp;
import org.alternativedev.wo42.gui.menu.menus.MenuMain;
import org.alternativedev.wo42.map.Map;
import org.alternativedev.wo42.map.maps.MapStart;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Game class controlling all the stuff
 * 
 * @author janni-futz
 * 
 */
public class Game extends BasicGame implements Runnable {
	/** The Map that's being used right now */
	public Map map;

	/** The player that's being controlled by the player */
	public EntityPlayer player;

	/** The state the game's in */
	public GameState gameState;

	/** The Menu of the game */
	private MenuMain mainMenu;
	private MenuDeath deathMenu;
	private MenuHelp helpMenu;

	/** The GuiElements */
	private GuiElementsRenderer guiElements;

	/**
	 * Constructor of the game
	 */
	public Game() {
		super("World of 42");
	}

	/**
	 * Set the GameState of the game.
	 * 
	 * @param newGameState
	 *            The new GameState
	 */
	public void setGameState(GameState newGameState) {
		gameState = newGameState;
	}

	/**
	 * Get the GameState of the game.
	 * 
	 * @return GameState of the game.
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Rendering Method.
	 * 
	 * @param gc
	 *            The GameContainer
	 * @param g
	 *            The Graphics
	 * @throws SlickException
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (gameState == GameState.MENU) {
			mainMenu.render(gc, this, g);
		} else if (gameState == GameState.INGAME) {
			map.drawAll(g, this);
			guiElements.drawAll(g, this);
		} else if (gameState == GameState.DEAD) {
			deathMenu.render(gc, this, g);
		} else if (gameState == GameState.HELP) {
			helpMenu.render(gc, this, g);
		}

	}

	/**
	 * Initialization of the game.
	 * 
	 * @param gc
	 *            The GameContainer
	 * @throws SlickException
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		this.deathMenu = new MenuDeath(gc);
		this.mainMenu = new MenuMain(gc);
		this.helpMenu = new MenuHelp(gc);
		guiElements = new GuiElementsRenderer();

		guiElements.addElement(new GuiElementHealthBar());
		guiElements.addElement(new GuiElementMagickaBar());
		gameState = GameState.MENU;

		// Map laden
		map = new MapStart();

		// System.gc();
	}

	/**
	 * Updating of the game. Handling Key-presses 'n stuff
	 * 
	 * @param gc
	 *            The GameContainer
	 * @param delta
	 *            The time between the last and the new frame.
	 * @throws SlickException
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (gameState == GameState.INGAME) {
			map.update(gc, delta, this);
		} else if (gameState == GameState.DEAD) {
			map.onPlayerDeath();
		}
		Settings.setHEIGHT(gc.getHeight());
		Settings.setWIDTH(gc.getWidth());
	}

	/**
	 * Run Method running the game.
	 * 
	 */
	public void run() {
		try {
			final CanvasGameContainer pong = new CanvasGameContainer(new Game());
			pong.getContainer().setTargetFrameRate(60);
			pong.getContainer().setShowFPS(true);

			JFrame frame = new JFrame("World of 42");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(Settings.getWIDTH(), Settings.getHEIGHT());
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					Display.destroy();
					System.exit(0);
				}
			});
			frame.add(pong);
			try {
				frame.setIconImage(ImageIO.read(new File("data/icon.png")));
			} catch (Exception e) {
			}

			frame.setVisible(true);

			pong.start();

		} catch (SlickException e1) {
			e1.printStackTrace();
		}

	}

	public void reset() throws SlickException {
		this.map = new MapStart();
		this.setGameState(GameState.INGAME);

	}

}
