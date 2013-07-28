package org.alternativedev.wo42.gui.menu.menus;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.gui.menu.Menu;
import org.alternativedev.wo42.gui.menu.MenuButton;
import org.alternativedev.wo42.gui.menu.MouseButtonListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MenuDeath extends Menu {
	public MenuDeath(GameContainer container) throws SlickException {
		this.addMenuElement(new MenuButton(new MouseButtonListener() {

			@Override
			public void onClick(Game game, GameContainer gContainer) {
				try {
					game.reset();
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onHover(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMouseDown(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub
				
			}
		}, container, new Image("data/menu/buttons/tryAgain_1.png"), new Image(
				"data/menu/buttons/tryAgain_2.png")));
		this.addMenuElement(new MenuButton(new MouseButtonListener() {

			@Override
			public void onClick(Game game, GameContainer gContainer) {
				gContainer.exit();
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
