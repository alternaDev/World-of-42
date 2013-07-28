package org.alternativedev.wo42.gameobjects.entities.npcs;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.gameobjects.GameObject;
import org.alternativedev.wo42.gameobjects.entities.EntityNPC;
import org.alternativedev.wo42.gameobjects.entities.EntityPlayer;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityNPCTest extends EntityNPC {

	public EntityNPCTest(int locX, int locY) throws SlickException {
		super(locX, locY, new Image[] { new Image("mnv2_bk1.png"),
				new Image("mnv2_bk2.png") },
				new Image[] { new Image("mnv2_fr1.png"),
						new Image("mnv2_fr2.png") }, new Image[] {
						new Image("mnv2_lf1.png"),
						new Image("mnv2_lf2.png") }, new Image[] {
						new Image("mnv2_rt1.png"),
						new Image("mnv2_rt2.png") },
				new int[] { 300, 300 });
		// TODO Auto-generated constructor stub
	}

	int waitNPC = 0;
	private boolean collidedWithPlayer;

	@Override
	public void onUpdate(GameContainer gc, int delta, Map map, Game game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.onUpdate(gc, delta, map, game);
		if (collidedWithPlayer) {
			waitNPC++;
			if (waitNPC >= 60) {
				collidedWithPlayer = false;
				waitNPC = 0;
				this.setInterrupted(false);
				this.stopSaying();
			}
		}
		this.say("TUA MATER!" + this.getHealth());

	}

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		super.onCollideWith(gameObject, delta);
		if (gameObject instanceof EntityPlayer) {
			collidedWithPlayer = true;
			waitNPC = 0;
			this.setInterrupted(true);
		}
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 50;
	}

}
