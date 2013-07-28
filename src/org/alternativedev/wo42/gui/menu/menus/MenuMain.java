package org.alternativedev.wo42.gui.menu.menus;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.enums.GameState;
import org.alternativedev.wo42.gui.menu.Menu;
import org.alternativedev.wo42.gui.menu.MenuButton;
import org.alternativedev.wo42.gui.menu.MouseButtonListener;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MenuMain extends Menu {

	public MenuMain(GameContainer container) throws SlickException {
		this.addMenuElement(new MenuButton(new MouseButtonListener() {

			@Override
			public void onClick(Game game, GameContainer gContainer) {
				game.setGameState(GameState.INGAME);
			}

			@Override
			public void onHover(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseDown(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub
				
			}
		}, container, new Image("data/menu/buttons/start_1.png"), new Image(
				"data/menu/buttons/start_2.png")));
		this.addMenuElement(new MenuButton(new MouseButtonListener() {

			@Override
			public void onClick(Game game, GameContainer gContainer) {
				game.setGameState(GameState.HELP);
			}

			@Override
			public void onHover(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseDown(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub
				
			}
		}, container, new Image("data/menu/buttons/start_1.png"), new Image(
				"data/menu/buttons/start_2.png")));
		this.addMenuElement(new MenuButton(new MouseButtonListener() {

			@Override
			public void onClick(Game game, GameContainer gContainer) {
				Display.destroy();
				System.exit(0);
			}

			@Override
			public void onHover(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseDown(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub
				
			}
		}, container, new Image("data/menu/buttons/quit_1.png"), new Image(
				"data/menu/buttons/quit_2.png")));
	}

}
