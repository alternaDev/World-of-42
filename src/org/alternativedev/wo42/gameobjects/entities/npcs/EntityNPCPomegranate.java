package org.alternativedev.wo42.gameobjects.entities.npcs;

import org.alternativedev.wo42.Game;
import org.alternativedev.wo42.dialog.Dialog;
import org.alternativedev.wo42.gameobjects.GameObject;
import org.alternativedev.wo42.gameobjects.Spell;
import org.alternativedev.wo42.gameobjects.entities.EntityNPC;
import org.alternativedev.wo42.gameobjects.entities.EntityPlayer;
import org.alternativedev.wo42.map.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class EntityNPCPomegranate extends EntityNPC {

	private Dialog dialog;
	
	@Override
	public void draw(Graphics g, Game game) {
		// TODO Auto-generated method stub
		super.draw(g, game);
	}

	private Rectangle actionArea;

	public EntityNPCPomegranate(int locX, int locY, String dialogName, Map map) throws SlickException {
		super(locX, locY, new Image[] {
				new Image("data/levels/level1/apple.png"),
				new Image("data/levels/level1/apple.png") }, new Image[] {
				new Image("data/levels/level1/apple.png"),
				new Image("data/levels/level1/apple.png") }, new Image[] {
				new Image("data/levels/level1/apple.png"),
				new Image("data/levels/level1/apple.png") }, new Image[] {
				new Image("data/levels/level1/apple.png"),
				new Image("data/levels/level1/apple.png") }, new int[] { 300,
				300 });
		refreshActionArea();
		this.dialog = new Dialog(dialogName, this, map.getPlayer());
	}

	int waitNPC = 0;
	boolean collidedWithPlayer;

	@Override
	public void onUpdate(GameContainer gc, int delta, Map map, Game game)
			throws SlickException {

		refreshActionArea();
		dialog.update(gc, delta);
		if(map.getPlayer().getPoly().intersects(this.getActionArea())) {
			dialog.start();
		}

	}

	@Override
	public void onCollideWith(GameObject gameObject, int delta) {
		super.onCollideWith(gameObject, delta);
		if (gameObject instanceof EntityPlayer) {

			
		} else if(gameObject instanceof Spell) {
			say("Aua!");
		}

	}

	@Override
	public int getMaxHealth() {
		return 50;
	}

	public Rectangle getActionArea() {
		return actionArea;
	}

	private void refreshActionArea() {
		actionArea = new Rectangle(this.getPositionX() - 50,
				this.getPositionY() - 50, 300, 300);
	}
}
