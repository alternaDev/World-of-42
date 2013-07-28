package org.alternativedev.wo42.gui.menu;

import org.alternativedev.wo42.Game;
import org.newdawn.slick.GameContainer;

public interface MouseButtonListener {
	public abstract void onClick(Game game, GameContainer gContainer);
	
	public abstract void onHover(Game game, GameContainer gContainer);

	public abstract void onMouseDown(Game game, GameContainer gContainer);
}
