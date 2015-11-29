package one;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MenuButtonGroup extends MenuButton {
	public ArrayList<MyButton> buts;
	public int selected;
	private boolean drawed;
	public MenuButtonGroup(String name) {
		super(0, 0, 0, 0, name, null, null);
		buts = new ArrayList<MyButton>();
		selected = 0;
		// TODO Auto-generated constructor stub
	}
	public void tic() {
		drawed = !drawed;
	}
	public void draw(Graphics2D g, boolean active) {
		
		for(int a=0; a<buts.size(); a++) {
			MyButton m = buts.get(a);
			if(a==selected && active) {
				if(drawed) {
					m.press();
					m.draw(g);
					m.press();
				} else {
					m.draw(g);
				}
			} else {
				m.draw(g);
			}
		}
	}
	public MyButton getSelected() {
		return buts.get(selected);
	}
	public void setSelected(int s) {
		if(s<0 || s>=buts.size())
			return;
		buts.get(selected).press();
		selected = s;
		buts.get(selected).press();
		
	}
	public void add(MyButton m) {
		buts.add(m);
		if(buts.size()==1)
			buts.get(0).press();
	}

}
