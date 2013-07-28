package org.alternativedev.wo42.enums;

/**
 * The GameState enum containing the state the game's in
 * 
 * @author janni-futz
 * 
 */
public enum GameState {
	/** The Game's diplaying the Menu */
	MENU,

	/** The Game's running */
	INGAME,

	/** The Game's paused */
	PAUSED,
	
	/** The Game's loading */
	LOADING,
	
	/** The Player failed */
	DEAD,
	
	/** Show a help Menu */
	HELP;
}
