package one;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Spawn extends Thing{
	public ArrayList<Mob> mobs;
//	public Random rand;
	public int experience;
	
	public Spawn(int sx, int sy, int sw, int sh, World smyworld, int exp) {
		super(sx, sy, sw, sh, smyworld);
		mobs = new ArrayList<Mob>();
//		rand = new Random();
		experience = exp;
	}
	public void addmob(Mob m) {
		mobs.add(m);
	}
	public boolean isin(Mob m) {
		for(Mob t : mobs) {
			if(m==t) {
				return true;
			}
		}
		return false;
	}
	public Mob remove(Mob m) {
		Mob toret = m;
		int wp = m.w;
		int hp = m.h;
		boolean good = false;
		int yp = 0;
		int xp = 0;
		while(!good) {
			yp = (int)(Math.random()*h)+y-h/2;
//			yp = rand.nextInt(h)+y-h/2;
			xp = (int)(Math.random()*w)+x-w/2;
//			xp = rand.nextInt(w)+x-w/2;
			Rectangle  di = new Rectangle(xp-wp/2, yp-hp/2, wp, hp);
			if(!myworld.collides(di)) {
				good = true;
			}
		}
		m.setPos(xp, yp);
		m.dead = false;
		m.popups.clear();
		m.experience+=experience;
		m.lvlup();
		m.health = m.getMaximumHealth();
		return toret;
	}
	public String tosave() {
		String toret = "Spawn "+super.toString()+" "+experience+" "+mobs.size();
		for(Mob m : mobs) {
			toret += " "+m.tosave();
		}
		return toret;
	}
	
}
