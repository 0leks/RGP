package one;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuButton extends MyButton{	
	public Menu menu;
	public MenuButton(int sx, int sy, int sw, int sh,String sname, Color scolor, Menu m) {
		super("but", sx, sy, sw, sh, sname, scolor);
		menu = m;
	}
	public Menu getmenu() {
		return menu;
	}

}
