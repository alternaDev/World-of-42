package org.alternativedev.wo42.gameobjects.entities;

import org.alternativedev.wo42.gameobjects.GameObject;
import org.newdawn.slick.Image;

public class EntityNPCHostile extends EntityNPC {

	public EntityNPCHostile(int locX, int locY, Image[] gfxUp, Image[] gfxDown,
			Image[] gfxLeft, Image[] gfxRight, int[] dur) {
		super(locX, locY, gfxUp, gfxDown, gfxLeft, gfxRight, dur);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		// TODO Auto-generated method stub
		super.onCollideWith(gameObject, delta);
		if (gameObject instanceof EntityPlayer)
			((EntityPlayer) gameObject).setHealth(((EntityPlayer) gameObject)
					.getHealth() - 10);
	}

}
