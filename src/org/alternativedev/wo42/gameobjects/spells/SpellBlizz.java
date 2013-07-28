package org.alternativedev.wo42.gameobjects.spells;

import org.alternativedev.wo42.enums.MoveDirection;
import org.alternativedev.wo42.gameobjects.Spell;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SpellBlizz extends Spell {
	private static String imagePathString = "data/spells/blizz.png";

	private static SpriteSheet spriteSheet;

	public static void initSpell(){
		try {
			spriteSheet = new SpriteSheet(new Image(imagePathString), 64, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public SpellBlizz(int startX, int startY, MoveDirection moveDirection)
			throws SlickException {
		super(new Image[] { //
						spriteSheet.getSubImage(0, 2), //
						spriteSheet.getSubImage(1, 2),
						spriteSheet.getSubImage(2, 2),
						spriteSheet.getSubImage(3, 2), }, //
				new Image[] { //
						spriteSheet.getSubImage(0, 6), //
						spriteSheet.getSubImage(1, 6),
						spriteSheet.getSubImage(2, 6),
						spriteSheet.getSubImage(3, 6), },//
				new Image[] { //
						spriteSheet.getSubImage(0, 0), //
						spriteSheet.getSubImage(1, 0),
						spriteSheet.getSubImage(2, 0),
						spriteSheet.getSubImage(3, 0), }, //
				new Image[] { //
						spriteSheet.getSubImage(0, 4), //
						spriteSheet.getSubImage(1, 4),
						spriteSheet.getSubImage(2, 4),
						spriteSheet.getSubImage(3, 4), },//
				new int[] { 100, 100, 100, 100 }, //
				moveDirection, startX, startY);
	}

	@Override
	public int getManaNeeds() {
		return 10;
	}

	@Override
	public int getLPDiff() {
		return 10;
	}

}
