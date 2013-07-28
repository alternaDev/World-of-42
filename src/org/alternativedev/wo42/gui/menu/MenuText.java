package org.alternativedev.wo42.gui.menu;

import org.alternativedev.wo42.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class MenuText implements MenuElement {

	private String text;
	private int x, y;

	public MenuText(String text) {
		this.text = text;
	}

	public void updateDraw(Graphics g, Game game, GameContainer gContainer) {
		g.setColor(org.newdawn.slick.Color.white);
		g.drawString(this.text, this.x, this.y);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth(Graphics g) {
		return g.getFont().getWidth(text);
	}

	public int getHeight(Graphics g) {
		return g.getFont().getHeight(text);
	}
}