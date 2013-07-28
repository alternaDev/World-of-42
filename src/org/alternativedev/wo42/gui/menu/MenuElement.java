package org.alternativedev.wo42.gui.menu;

import org.alternativedev.wo42.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface MenuElement {

	public abstract int getWidth(Graphics g);

	public abstract int getHeight(Graphics g);

	public abstract void updateDraw(Graphics g, Game game,
			GameContainer gContainer) throws SlickException;

	public abstract int getX();

	public abstract int getY();

	public abstract void setX(int x);

	public abstract void setY(int y);
}
