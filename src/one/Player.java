package one;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import gui.Frame;

public class Player extends Mob{
	public Player(int sx, int sy,  World smyworld, Race r) {
		super(sx, sy, "player", smyworld, r);
	}
	@Override
	public boolean damage(int d) {
		boolean alreadydead = dead;
		health-=d;
		dead = (health<=0);
		if(dead && !alreadydead) {
			myworld.changeSound(null);
			myworld.death.play(0);
      myworld.deathTransparency = 0;
		}
		return dead;
	}
	public boolean buyItem(Item i) {
		boolean buysuccessful = false;
		if(i instanceof Weapon) {
			if(money-i.cost>=0) {
				getWeap(i.name);
				money -= i.cost;
				rescale();
				buysuccessful = true;
			}
		} else {
			if(money-i.cost>=0) {
				addItem(i.name, 1);
				money -= i.cost;
				buysuccessful = true;
			}
		}
		rescale();
		return buysuccessful;
	}
	public void drawinv(Graphics2D g, Point m) {
		g.setColor(Color.red);
		int dx = 190;
		int dy1 = 210;
		int dy2 = 120;
		if(inv.size()>0) {
			inv.get(0).draw(g, Panel.DIMX - dx, Panel.DIMY - dy1, 80, 80, m, false);
		} else {
			g.drawRect(Panel.DIMX - dx, Panel.DIMY - dy1, 80, 80);
		}
		if(inv.size()>1) {
			inv.get(1).draw(g, Panel.DIMX - dx+90, Panel.DIMY - dy1, 80, 80, m, false);
		} else {
			g.drawRect(Panel.DIMX - dx+90, Panel.DIMY - dy1, 80, 80);
		} 
		if(inv.size()>2) {
			inv.get(2).draw(g, Panel.DIMX - dx-90, Panel.DIMY - dy2, 80, 80, m, false);
		} else {
			g.drawRect(Panel.DIMX - dx-90, Panel.DIMY - dy2, 80, 80);
		}
		if(inv.size()>3) {
			inv.get(3).draw(g, Panel.DIMX - dx, Panel.DIMY - dy2, 80, 80, m, false);
		} else {
			g.drawRect(Panel.DIMX - dx, Panel.DIMY - dy2, 80, 80);
		}
		if(inv.size()>4) {
			inv.get(4).draw(g, Panel.DIMX - dx+90, Panel.DIMY - dy2, 80, 80, m, false);
		} else {
			g.drawRect(Panel.DIMX - dx+90, Panel.DIMY - dy2, 80, 80);
		}
		weapon.draw(g, Panel.DIMX - dx-90, Panel.DIMY - dy1, 80, 80, m, false);
	}
	
	public void setspeed(int xs, int ys) {
		if(xs==1) {
			xspeed = getAccel();
		} 
		if(xs==-1) {
			xspeed = -getAccel();
		}
		if(xs == -2) {
			if(xspeed<0)
				xspeed = 0;
		}
		if(xs == 2) {
			if(xspeed>0)
				xspeed = 0;
		}
		if(ys==1) {
			yspeed = getAccel();
		}
		if(ys==-1) {
			yspeed = -getAccel();
		}
		if(ys == -2) {
			if(yspeed<0)
				yspeed = 0;
		}
		if(ys == 2) {
			if(yspeed>0)
				yspeed = 0;
		}
	}
	public String tosave() {
		String s = "Player "+race.name+" "+experience+" "+money+" "+x+" "+y;
		s+=" "+health+" "+weapon.name+" , "+itemsininv();
		System.out.println("Saving player");
		for(int a=0; a<inv.size(); a++) {
			Item i = inv.get(a);
			if(i!=null) {
				System.out.println(i.name);
				String tosavename = i.name.replace(' ', '_');
				System.out.println(tosavename);
				s+=" "+tosavename+" "+i.amount;
			}
		}
		System.out.println("Player will be saved as:"+s);
		return s;
	}

}
