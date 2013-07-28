package org.alternativedev.wo42.gui;

import java.util.ArrayList;
import java.util.Iterator;

import org.alternativedev.wo42.Game;
import org.newdawn.slick.Graphics;

public class GuiElementsRenderer {

	ArrayList<GuiElement> elements = new ArrayList<GuiElement>();
	
	public void drawAll(Graphics g, Game game){
		Iterator<GuiElement> itel = elements.iterator();
		
		while(itel.hasNext()){
			GuiElement elem = itel.next();
			elem.draw(g, game);
		}
	}
	
	public void addElement(GuiElement elem){
		this.elements.add(elem);
	}
}
