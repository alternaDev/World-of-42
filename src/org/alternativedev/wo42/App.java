package org.alternativedev.wo42;

import java.io.IOException;

import org.alternativedev.wo42.natives.NativeCopier;
import org.newdawn.slick.SlickException;

/**
 * Main of the game
 */
class App {

	/**
	 * Main-method creating the Game
	 * 
	 * @param args
	 * @throws SlickException
	 * @throws IOException
	 */
	public static void main(final String[] args) throws SlickException,
			IOException {
		App app = new App();

		app.run();

	}

	public void run() throws SlickException, IOException {
		new NativeCopier().copyNatives();
		System.setProperty("org.lwjgl.librarypath",
				System.getProperty("java.io.tmpdir") + "wo42/natives");
		System.out.println("Yo naisiger start et cetera");
		new Thread(new Game()).start();

	}
}
