package org.alternativedev.wo42.gui.guielements;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.Settings;
import org.alternativedev.wo42.gui.GuiElement;
import org.newdawn.slick.Graphics;

public class GuiElementHealthBar implements GuiElement {

	@Override
	public void draw(Graphics g, Game game) {
		int xLoc = 40, yLoc = Settings.getHEIGHT() - 75;
		g.setColor(org.newdawn.slick.Color.red);
		g.fillRect(xLoc, yLoc, 200, 10);
		g.setColor(org.newdawn.slick.Color.green);
		g.fillRect(xLoc, yLoc, game.map.getPlayer().getHealth() * 2, 10);
		if (game.map.getPlayer().getHealth() <= 49
				&& game.map.getPlayer().getHealth() >= 25)
			g.setColor(org.newdawn.slick.Color.yellow);
		else if (game.map.getPlayer().getHealth() < 25)
			g.setColor(org.newdawn.slick.Color.red);
		g.drawString("HP:" + game.map.getPlayer().getHealth() + "/100", xLoc,
				yLoc + 15);
	}

}
