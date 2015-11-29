package one;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Shop extends Thing{
	private String name;
	public ArrayList<Item> onsale;
	public Shop(int sx, int sy, int sw, int sh, World smyworld, String sname) {
		super(sx, sy, sw, sh, smyworld);
		onsale = new ArrayList<Item>();
		name = sname;
		
	}
	
	public String getName() { return name; }
	
	public Item buy(String name) {
		for(Item i : onsale) {
			if(i.name.equals(name)) {
				i.amount--;
				return new Item(i.name, 1, myworld);
			}
		}
		return null;
	}
	public Item buy(int index) {
		if(index<onsale.size() && index>=0) {
			Item i = onsale.get(index);
			if(i.amount>0) {
				i.amount--;
				return new Item(i.name, 1, myworld);
			}
		}
		return null;
	}
	public void draw(Graphics2D g) {
		int drawx = (x-myworld.p.x)/World.ZOOM+470;
		int drawy = (y-myworld.p.y)/World.ZOOM+310;
		int w = super.w/World.ZOOM;
		int h = super.h/World.ZOOM;
		if(drawx+w/2>0 && drawx-w/2<990 && drawy+h/2>0 && drawy-h/2<670) {
			Color cur = g.getColor();
			g.setColor(Color.yellow);
			g.fill(new Rectangle(drawx-w/2, drawy-h/2, w, h));
			g.setColor(Color.black);
			g.drawString(name, drawx-w/2+10, drawy+g.getFont().getSize()/2);
			
			
			g.setColor(cur);
		}
	}
	public void drawgui(Graphics2D g, int sel, Point m) {
		Color cur = g.getColor();
		g.setColor(Color.blue);
		myworld.drawStat(g, 960, 35, "Money", myworld.p.money);
		for(int a=0; a<onsale.size(); a++) {
			g.setColor(Color.blue);
			Item i = onsale.get(a);
			int xp = a%5;
			int yp = a/5;
			xp = 940+25+xp*50;
			yp = 50+yp*50;
			i.draw(g, xp, yp, 40, 40, m, true);
			
			if(a==sel) {
				g.setColor(Color.red);
				g.drawRect(xp-1, yp-1, 42, 42);
				g.drawRect(xp-2, yp-2, 44, 44);
			}
		}
		
		g.setColor(cur);
	}
	
}
