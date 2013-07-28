package org.alternativedev.wo42.gameobjects.spells;

import org.alternativedev.wo42.enums.MoveDirection;
import org.alternativedev.wo42.gameobjects.Spell;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SpellFireball extends Spell {
	private static String imagePathString = "data/spells/fireball.png";
	private static SpriteSheet spriteSheet;


	public static void initSpell(){
		try {
				spriteSheet = new SpriteSheet(new Image(imagePathString), 64, 64);
			} catch (SlickException e) {
				e.printStackTrace();
			}
	}

	public SpellFireball(int startX, int startY, MoveDirection moveDirection)
			throws SlickException {
		super(new Image[] { //
						spriteSheet.getSubImage(0, 2), //
						spriteSheet.getSubImage(1, 2),
						spriteSheet.getSubImage(2, 2),
						spriteSheet.getSubImage(3, 2),
						spriteSheet.getSubImage(4, 2),
						spriteSheet.getSubImage(5, 2),
						spriteSheet.getSubImage(6, 2),
						spriteSheet.getSubImage(7, 2) }, //
				new Image[] { //
						spriteSheet.getSubImage(0, 6),//
						spriteSheet.getSubImage(1, 6),
						spriteSheet.getSubImage(2, 6),
						spriteSheet.getSubImage(3, 6),
						spriteSheet.getSubImage(4, 6),
						spriteSheet.getSubImage(5, 6),
						spriteSheet.getSubImage(6, 6),
						spriteSheet.getSubImage(7, 6) },//
				new Image[] { //
						spriteSheet.getSubImage(0, 0),//
						spriteSheet.getSubImage(1, 0),
						spriteSheet.getSubImage(2, 0),
						spriteSheet.getSubImage(3, 0),
						spriteSheet.getSubImage(4, 0),
						spriteSheet.getSubImage(5, 0),
						spriteSheet.getSubImage(6, 0),
						spriteSheet.getSubImage(7, 0) }, //
				new Image[] { //
						spriteSheet.getSubImage(0, 4), //
						spriteSheet.getSubImage(1, 4),
						spriteSheet.getSubImage(2, 4),
						spriteSheet.getSubImage(3, 4),
						spriteSheet.getSubImage(4, 4),
						spriteSheet.getSubImage(5, 4),
						spriteSheet.getSubImage(6, 4),
						spriteSheet.getSubImage(7, 4) },//
				new int[] { 50, 50, 50, 50, 50, 50, 50, 50 }, //
				moveDirection, startX, startY);
	}

	@Override
	public int getManaNeeds() {
		return 15;
	}

	@Override
	public int getLPDiff() {
		return 20;
	}
}
