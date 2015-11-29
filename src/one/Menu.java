package one;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Menu {
	public ArrayList<MenuButton> buts;
	public boolean active;
	/**
	 * the index of the selected button
	 */
	private int selected;
	public Menu() {
		active = false;
		buts = new ArrayList<MenuButton>();
		setsel(0);
	}
	public void setsel(int sel) {
		if(sel>=0 && sel<buts.size() && buts.size()!=0) {
			buts.get(selected).press();
			selected = sel;
			buts.get(selected).press();
		}
	}
	public int getsel() {
		return selected;
	}
	public void setactive(boolean a) {
		active = a;
	}
	public void draw(Graphics2D g) {
		if(active) {
			
			for(int a=0; a<buts.size(); a++) {
				MenuButton b = buts.get(a);
				if(b instanceof MenuButtonGroup) {
					MenuButtonGroup m = (MenuButtonGroup)b;
					m.draw(g, (a==selected));
				} else {
					b.draw(g);
				}
			}
		}
	}
	public MenuButton geton() {
		for(MenuButton b : buts) {
			if(b.check()) {
				return b;
			}
		}
		return null;
	}
	public void add(MenuButton b) {
		
		buts.add(b);
		if(buts.size() == 1) {
			buts.get(0).press();
		}
	}
	/**
	 * Only used for blinky buttons in MenuButtonGroup
	 */
	public void tic() {
		if(buts.get(selected) instanceof MenuButtonGroup) {
			((MenuButtonGroup)buts.get(selected)).tic();
		}
	}
}
