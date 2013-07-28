package org.alternativedev.wo42.gui;

import org.alternativedev.wo42.Game;
import org.newdawn.slick.Graphics;

public interface GuiElement {
	public abstract void draw(Graphics g, Game game);
}
