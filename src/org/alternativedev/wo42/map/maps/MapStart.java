package org.alternativedev.wo42.map.maps;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.gameobjects.items.ItemCoin;
import org.alternativedev.wo42.gameobjects.spells.SpellBlizz;
import org.alternativedev.wo42.gameobjects.spells.SpellFireball;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class MapStart extends Map {

	private static String mapFile = "data/levels/level1/level1.tmx";
	private static String mapMusic = "data/music/main.ogg";
	private static String deathMusic = "data/music/death.ogg";

	// private EntityNPC stupidNPC = new EntityNPCTest(90, 90);

	public MapStart() throws SlickException {
		super(mapFile, mapMusic, deathMusic);
		this.addGameObject(new ItemCoin(60, 60));

		// this.addGameObject(stupidNPC);
		this.getPlayer().setPositionX(620);
		this.getPlayer().setPositionY(900);

		// Initialization of the Spells
		SpellBlizz.initSpell();
		SpellFireball.initSpell();
	}

	int waitNPC = 0;

	@Override
	public void update(GameContainer gc, int delta, Game game)
			throws SlickException {
		super.update(gc, delta, game);

	}
}
