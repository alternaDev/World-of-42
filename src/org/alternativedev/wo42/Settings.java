package org.alternativedev.wo42;

/**
 * Settings class containing all the Settings etc.
 * 
 * @author janni-futz
 */
public class Settings {

	/** Width and Height of the Window */
	private static int WIDTH = 965;
	private static int HEIGHT = 645;

	/** Scrolling Player Distance */
	private static int playerDistance = 65;

	/** Player MOvement Speed */
	private static float playerMovementSpeed = 0.2f;

	public static int getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public static int getPlayerDistance() {
		return playerDistance;
	}

	public static void setPlayerDistance(int playerDistance) {
		Settings.playerDistance = playerDistance;
	}

	public static float getPlayerMovementSpeed() {
		return playerMovementSpeed;
	}

	public static void setPlayerMovementSpeed(float playerMovementSpeed) {
		Settings.playerMovementSpeed = playerMovementSpeed;
	}

}
