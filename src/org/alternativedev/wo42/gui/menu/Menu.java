package org.alternativedev.wo42.gui.menu;

import java.util.ArrayList;
import java.util.Iterator;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.Settings;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Menu {

	private ArrayList<MenuElement> elements = new ArrayList<MenuElement>();

	public void render(GameContainer container, Game game, Graphics g)
			throws SlickException {
		// Render Menu Buttons
		int distY = 0;
		int completeHeight = 0;
		Iterator<MenuElement> it = elements.iterator();
		while (it.hasNext()) {
			MenuElement type = (MenuElement) it.next();
			completeHeight += type.getHeight(g);

		}

		it = elements.iterator();
		while (it.hasNext()) {
			MenuElement option = it.next();
			option.setX(Settings.getWIDTH() / 2 - option.getWidth(g) / 2);
			option.setY(Settings.getHEIGHT() / 2 + distY - completeHeight / 2);
			distY += option.getHeight(g);
			option.updateDraw(g, game, container);
		}

	}

	public void addMenuElement(MenuElement menuOption) {
		this.elements.add(menuOption);
	}

}
