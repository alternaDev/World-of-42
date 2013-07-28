package org.alternativedev.wo42.gui.menu.menus;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.enums.GameState;
import org.alternativedev.wo42.gui.menu.Menu;
import org.alternativedev.wo42.gui.menu.MenuButton;
import org.alternativedev.wo42.gui.menu.MenuText;
import org.alternativedev.wo42.gui.menu.MouseButtonListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MenuHelp extends Menu {
	public MenuHelp(GameContainer container) throws SlickException {
		MenuText test = new MenuText("Up Arrow Key - walk up \n"
				+ "Down Arrow Key - walk down \n"
				+ "Left Arrow Key - walk left \n"
				+ "Right Arrow Key -  walk right \n"
				+ "F Key - Fire Spell \n"
				+ "B Key - Shock Spell");
		test.setX(350);
		test.setY(250);
		this.addMenuElement(test);
		this.addMenuElement(new MenuButton(new MouseButtonListener() {

			@Override
			public void onHover(Game game, GameContainer gContainer) {

			}

			@Override
			public void onClick(Game game, GameContainer gContainer) {
				game.setGameState(GameState.MENU);

			}

			@Override
			public void onMouseDown(Game game, GameContainer gContainer) {
				// TODO Auto-generated method stub
				
			}
		}, container, new Image("data/menu/buttons/start_1.png"), new Image(
				"data/menu/buttons/start_2.png")));
	}
}
