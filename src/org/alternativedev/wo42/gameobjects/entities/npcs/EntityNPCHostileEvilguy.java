package org.alternativedev.wo42.gameobjects.entities.npcs;

import org.alternativedev.wo42.gameobjects.entities.EntityNPCHostile;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityNPCHostileEvilguy extends EntityNPCHostile {

	public EntityNPCHostileEvilguy(int locX, int locY) throws SlickException {
		super(locX, locY, new Image[] { new Image("data/mnv2_bk1.png"),
				new Image("data/mnv2_bk2.png") },
				new Image[] { new Image("data/mnv2_fr1.png"),
						new Image("data/mnv2_fr2.png") }, new Image[] {
						new Image("data/mnv2_lf1.png"),
						new Image("data/mnv2_lf2.png") }, new Image[] {
						new Image("data/mnv2_rt1.png"),
						new Image("data/mnv2_rt2.png") },
				new int[] { 300, 300 });
	}

}
