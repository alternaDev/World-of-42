package org.alternativedev.wo42.gui.guielements;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.Settings;
import org.alternativedev.wo42.gui.GuiElement;
import org.newdawn.slick.Graphics;

public class GuiElementMagickaBar implements GuiElement {

	@Override
	public void draw(Graphics g, Game game) {
		int xLoc = Settings.getWIDTH() - 250, yLoc = Settings.getHEIGHT() - 75;
		g.setColor(org.newdawn.slick.Color.yellow);

		g.fillRect(xLoc, yLoc, 200, 10);
		g.setColor(org.newdawn.slick.Color.blue);

		g.fillRect(xLoc, yLoc, game.map.getPlayer().getMagicka() * 2, 10);
		if (game.map.getPlayer().getMagicka() <= 49
				&& game.map.getPlayer().getMagicka() >= 25)
			g.setColor(org.newdawn.slick.Color.yellow);
		else if (game.map.getPlayer().getMagicka() < 25)
			g.setColor(org.newdawn.slick.Color.red);
		g.drawString("Magicka:" + game.map.getPlayer().getMagicka() + "/100",
				xLoc, yLoc + 15);
	}

}
