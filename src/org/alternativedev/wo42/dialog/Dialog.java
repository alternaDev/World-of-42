package org.alternativedev.wo42.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.alternativedev.wo42.gameobjects.entities.EntityNPC;
import org.alternativedev.wo42.gameobjects.entities.EntityPlayer;
import org.alternativedev.wo42.util.Timer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.util.ResourceLoader;

public class Dialog {
	// private String txt;
	private ArrayList<DialogObject> dialogObjects = new ArrayList<DialogObject>();
	private EntityNPC speaking;
	private Iterator<DialogObject> itObjects;
	private EntityPlayer player;

	// Momentan kann man nur mit nicht feindlichen NPCs dialoge f�hren. nicht :D
	public Dialog(String txt, EntityNPC speaking, EntityPlayer player) {
		this.speaking = speaking;
		this.player = player;

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					ResourceLoader.getResourceAsStream(txt)));
			String str;
			int i = 0;
			while ((str = in.readLine()) != null) {
				i++;
				str = str.replace("\\n", "\n");
				str = org.apache.commons.lang3.StringEscapeUtils
						.unescapeHtml4(str);
				dialogObjects
						.add(new DialogObject(i % 2 == 0, new String(str)));
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		itObjects = dialogObjects.iterator();

	}

	int pP = 0, npcP = 0;

	private synchronized DialogObject next() {

		return itObjects.next();

	}

	private boolean hasNext() {
		return itObjects.hasNext();

	}

	public void update(GameContainer gc, int delta) {
		for (Timer timer : timers)
			timer.update(gc, delta);
	}

	private static final int WAIT_TIME = 6000;
	private boolean running = false;
	private List<Timer> timers = new ArrayList<Timer>();

	public synchronized void start() {

		if (running)
			return;

		running = true;
		int i = 0;
		while (hasNext()) {
			final DialogObject dObject = next();
			timers.add(new Timer(WAIT_TIME * i, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.setLock(true);
					if (!dObject.player) {
						player.stopSaying();
						speaking.say(dObject.text);

					} else {
						speaking.stopSaying();
						player.say(dObject.text);

					}
				}
			}));
			i++;
		}

		timers.add(new Timer(WAIT_TIME * i + 1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setLock(false);
				player.stopSaying();
				running = false;
			}
		}));

		for (Timer timer : timers)
			timer.start();

	}

	private class DialogObject {
		boolean player;
		String text;

		public DialogObject(boolean player, String text) {
			this.player = player;
			this.text = text;
		}
	}

}
