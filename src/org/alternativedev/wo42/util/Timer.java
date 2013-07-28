package org.alternativedev.wo42.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.EventListenerList;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;

public class Timer {

	private String command = null;
	protected EventListenerList listenerList = new EventListenerList();

	protected int delay;
	protected boolean done = false;
	protected boolean active;
	private GameContainer container;
	protected long lastTime = getTime();

	public Timer(int delay, ActionListener l) {
		this.delay = delay;
		this.addActionListener(l);
	}

	protected void fireActionPerformed(String command) {
		ActionEvent evt = null;

		final ActionListener[] listeners = (ActionListener[]) listenerList
				.getListeners(ActionListener.class);
		for (int i = 0; i < listeners.length; i++) {
			if (evt == null) {
				evt = new ActionEvent(this, 42 * i + hashCode(), command);
			}
			listeners[i].actionPerformed(evt);
		}
	}

	public synchronized void addActionListener(ActionListener s) {
		listenerList.add(ActionListener.class, s);
	}

	public synchronized void removeActionListener(ActionListener s) {
		listenerList.remove(ActionListener.class, s);
	}

	public void update(GameContainer container, int delta) {
		if (!active)
			return;
		if (done)
			return;
		if (getTime() >= lastTime + delay) {
			fireActionPerformed(command);
			done = true;
		}
	}

	public void start() {
		boolean old = active;
		active = true;
		if (active != old) { // changed
			lastTime = getTime();
			done = false;
		}
	}

	public boolean isRunning() {
		return active;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	public void stop() {
		active = false;
	}

	public String getCommand() {
		return command;
	}

	private long getTime() {
		return container != null ? container.getTime() : (Sys.getTime() * 1000)
				/ Sys.getTimerResolution();
	}

}
