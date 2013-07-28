package org.alternativedev.wo42.gui.menu;

import java.util.ArrayList;

import org.alternativedev.wo42.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

public class MenuButton implements MenuElement {
	GameContainer container;
	Image upButton, downButton;
	int x = 0, y = 0;
	MouseOverArea mouseScan;
	ArrayList<MouseButtonListener> mouseButtonListeners = new ArrayList<MouseButtonListener>();
	boolean wasDown = false;

	public MenuButton(MouseButtonListener listener, GameContainer container,
			Image upButton, Image downButton) {
		this.container = container;
		this.upButton = upButton;
		this.downButton = downButton;
		mouseScan = new MouseOverArea(container, upButton, x, y);
		this.mouseButtonListeners.add(listener);
	}

	public void updateDraw(Graphics g, Game game, GameContainer gContainer)
			throws SlickException {
		mouseScan.setX(this.getX());
		mouseScan.setY(this.getY());

		upButton.draw(x, y);
		if (mouseScan.isMouseOver()) {
			downButton.draw(x, y);
			for (int i = 0; i < mouseButtonListeners.size(); i++)
				mouseButtonListeners.get(i).onHover(game, gContainer);
			
			if (!container.getInput()
					.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				if (wasDown)
					for (int i = 0; i < mouseButtonListeners.size(); i++)
						mouseButtonListeners.get(i).onClick(game, gContainer);
			wasDown = false;
		}
		if (mouseScan.isMouseOver()
				&& container.getInput().isMouseButtonDown(
						Input.MOUSE_LEFT_BUTTON)) {
			for (int i = 0; i < mouseButtonListeners.size(); i++)
				mouseButtonListeners.get(i).onMouseDown(game, gContainer);
			wasDown = true;
		}
	}

	public void addOnClickListener(MouseButtonListener listener) {
		this.mouseButtonListeners.add(listener);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getWidth(Graphics g) {
		// TODO Auto-generated method stub
		return this.upButton.getWidth();
	}

	@Override
	public int getHeight(Graphics g) {
		// TODO Auto-generated method stub
		return this.upButton.getHeight();
	}
}
