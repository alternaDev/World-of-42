package org.alternativedev.wo42.map;

import org.alternativedev.wo42.enums.Scrolling;

public class Scroll {

	/** Defines if the map's scrolling or not */
	private boolean scrolling, scrollingLeft;

	/** Defines the direction the Map is scrolling to */
	private boolean scrollingRight, scrollingUp, scrollingDown;

	/**
	 * Returns if the map is scrolling or not.
	 * 
	 * @return Returns if the map is scrolling or not.
	 */
	public boolean isScrolling() {
		return scrolling;
	}

	/**
	 * Returns if the map is scrolling to the left.
	 * 
	 * @return Returns if the map is scrolling to the left
	 */
	public boolean isScrollingLeft() {
		return scrollingLeft;
	}

	/**
	 * Returns if the map is scrolling to the right.
	 * 
	 * @return Returns if the map is scrolling to the right
	 */
	public boolean isScrollingRight() {
		return scrollingRight;
	}

	/**
	 * Returns if the map is scrolling upwards.
	 * 
	 * @return Returns if the map is scrolling upwards
	 */
	public boolean isScrollingUp() {
		return scrollingUp;
	}

	/**
	 * Returns if the map is scrolling downwards.
	 * 
	 * @return Returns if the map is scrolling downwards
	 */
	public boolean isScrollingDown() {
		return scrollingDown;
	}

	/**
	 * Sets the ScrollingState determined by a Scrolling Enum.
	 * 
	 * @param s
	 *            the Scrolling Enum
	 */
	public void setScrolling(Scrolling s) {
		if (s == Scrolling.UP) {
			scrollingLeft = false;
			scrollingRight = false;
			scrollingDown = false;
			scrollingUp = true;
			scrolling = true;
		}
		if (s == Scrolling.DOWN) {
			scrollingLeft = false;
			scrollingRight = false;
			scrollingDown = true;
			scrollingUp = false;
			scrolling = true;
		}
		if (s == Scrolling.LEFT) {
			scrollingLeft = true;
			scrollingRight = false;
			scrollingDown = false;
			scrollingUp = false;
			scrolling = true;
		}
		if (s == Scrolling.RIGHT) {
			scrollingLeft = false;
			scrollingRight = true;
			scrollingDown = false;
			scrollingUp = false;
			scrolling = true;
		}
		if (s == Scrolling.RESET) {
			scrollingLeft = false;
			scrollingRight = false;
			scrollingDown = false;
			scrollingUp = false;
			scrolling = false;
		}

	}

}
