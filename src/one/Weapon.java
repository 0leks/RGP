package one;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Weapon extends Item {
	public int width, length;
	public int range;
	public boolean continuous;
	public ArrayList<Debuff> debuffs;
	public Weapon(String sname, int amount, World m) {
		super(sname, amount, m);
	}
//	@Override
//	public void draw(Graphics2D g, int xp, int yp, int wp, int hp) {
////		super.draw(g, xp, yp, wp, hp);
//		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(45), xp, yp);
//		tx.setToRotation(Math.toRadians(180), xp, yp);
////		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//		Graphics2D g2d = (Graphics2D)g;
//		g2d.setTransform(tx);
////		tx.rotate(Math.toRadians(45));
//		g2d.drawImage(image, xp, yp, null);
//		g2d.dispose();
////		tx.rotate(-Math.toRadians(45));
////		g.drawImage(image, xp, yp, wp, hp, null);
////		g.drawRect(xp, yp, wp, hp);
////		g.drawString(amount+"", xp, yp+hp-5);
////		g.drawString(cost+"", xp, yp+15);
//	}
	/**
	 * puton
	 * Used only for deathaura, as deathaura range depends on player width
	 * @param m
	 */
	public void puton(Mob m) {
		if (name.contains("deathaura")) {
			int rank = 0;
			if(name.contains("wooden")) {
				rank = 1;
			} else if(name.contains("iron")) {
				rank = 2;
			} else if(name.contains("steel")) {
				rank = 3;
			} else if(name.contains("mithril")) {
				rank = 4;
			} else if(name.contains("adamant")) {
				rank = 5;
			} else if(name.contains("rune")) {
				rank = 6;
			} else if(name.contains("dragon")) {
				rank = 7;
			}
			range = -(60+10*rank)+(60+10*rank)/2-m.w/2;
		}
	}
	@Override
	public void init() {
    debuffs = new ArrayList<Debuff>();
		int rank = 0;
		if(name.contains("wooden")) {
			rank = 1;
		} else if(name.contains("iron")) {
			rank = 2;
		} else if(name.contains("steel")) {
			rank = 3;
		} else if(name.contains("mithril")) {
			rank = 4;
		} else if(name.contains("adamant")) {
			rank = 5;
		} else if(name.contains("rune")) {
			rank = 6;
		} else if(name.contains("dragon")) {
			rank = 7;
		}
		int multiplier = (int) Math.pow(rank, 2);
		if (name.contains("bomb")) {
			width = 200+40*rank;
			length = 200+40*rank;
			range = 2*rank;
			addbuff("dmg", 5+6*multiplier, true);
      Debuff d = new Debuff( Debuff.STUN, 10 + rank*2, .5);
      debuffs.add(d);
			cost = 100+12*multiplier;
		}
		if (name.contains("mace")) {
			width = 45+10*rank;
			length = 45+10*rank;
			range = rank*2;
			addbuff("dmg", 4+3*multiplier, true);
			addbuff("str", 5+5*multiplier, true);
			addcrit(5+rank, 100+35*rank);
      Debuff d = new Debuff( Debuff.STUN, 20 + rank*6, .8);
      debuffs.add(d);
			cost = 8*multiplier;
		}
		if (name.contains("fist")) {
			width = 40+3*rank;
			length = 40+3*rank;
			range = rank/2;
			addbuff("dmg", 2+multiplier, true);
			addbuff("hp", 10+multiplier*12, true);
			addbuff("adelay", 100-7*rank, false);
			cost = multiplier;
		}
		if (name.contains("sword")) {
			width = 100+12*rank;
			length = 20+8*rank;
			range = 2*rank;
			addbuff("dmg", 3+multiplier*3, true);
			addbuff("int", 1+3*multiplier, true);
      Debuff d = new Debuff( Debuff.STUN, 12, .8 - rank/20.0);
      debuffs.add(d);
			cost = 5*multiplier;
		}
		if (name.contains("dagger") && !name.contains("diamond dagger")) {
			width = 20+5*rank;
			length = 40+6*rank;
			range = (int) (1.5*rank);
			addbuff("dmg", 3+2*multiplier, true);
			addbuff("agi", 1+1*multiplier/2, true);
			addbuff("adelay", 100-6*rank, false);
      Debuff d = new Debuff( Debuff.POISON, 10 + multiplier/2);
      d.damage = 2;
      debuffs.add(d);
			cost = 6*multiplier;
		}
		if (name.contains("battleaxe")) {
			width = 120+12*rank;
			length = 30+8*rank;
			range = 30+0*rank;
			addbuff("dmg", 4+multiplier*4, true);
			addbuff("int", 2+4*multiplier, true);
      Debuff d = new Debuff( Debuff.STUN, 12, .75 - rank/20.0);
      debuffs.add(d);
      //TODO add new debuff weakness next attack deals less damage
			cost = 10*multiplier;
		}
		if (name.contains("spear")) {
			width = 20+5*rank;
			length = 98+12*rank;
			range = 5+3*rank;
			addbuff("dmg", 2+multiplier*3, true);
			addbuff("reg", 4+5*multiplier, true);
			addcrit(10+5*rank, 100+10*rank);
			cost = 7*multiplier;
		}
		if (name.contains("deathaura")) {
			width = 60+10*rank;
			length = 60+10*rank;
			range = -(60+10*rank)+(60+10*rank)/2-myworld.playerASDF.w/2;
			addbuff("dmg", 10+rank*10, false);
			addbuff("hp", 100-rank*10, false);
			continuous = true;
			cost = 50+24*multiplier;
		}

		if (name.equals("longbow")) {
			width = 60;
			length = 210;
			range = 190;
			addbuff("dmg", 120, false);
			addbuff("dmg", 10, true);
			addcrit(15, 200);
			cost = 25;
		}
		if (name.equals("shortbow")) {
			width = 40;
			length = 130;
			range = 125;
			addbuff("dmg", 110, false);
			addbuff("dmg", 5, true);
			addcrit(15, 160);
			cost = 15;
		}
		if (name.equals("test")) {
			width = 70;
			length = 160;
			range = 5;
			addbuff("agi", 300, false);
			addbuff("str", 500, false);
			addbuff("int", 10, false);
			addbuff("reg", 500, false);
			addbuff("adelay", 40, false);
			addbuff("hp", 500, false);
      Debuff d = new Debuff( Debuff.STUN, 50, .1);
      debuffs.add(d);
			cost = 9999;
		}
		if (name.equals("spartan laser")) {
			width = 20;
			length = 600;
			range = 1;
			addbuff("dmg", 200, true);
			addbuff("adelay", 400, false);
			addbuff("agi", -20, true);
			cost = 200;
		}
		if (name.equals("laser")) {
			width = 4;
			length = 500;
			range = 1;
			addbuff("dmg", 1, false);
			continuous = true;
			cost = 100;
		}
		if (name.equals("diamond laser")) {
			width = 30;
			length = 300;
			range = 4;
			addbuff("dmg", 2, false);
			continuous = true;
			cost = 150;
		}
		if (name.equals("pistol")) {
			width = 50;
			length = 50;
			range = 230;
			addbuff("dmg", 10, true);
			addbuff("adelay", 200, false);
			cost = 50;
		}
		if (name.contains("sniper rifle")) {
			width = 10;
			length = 10;
			range = 300;
			addbuff("dmg", 50, true);
			addbuff("adelay", 300, false);
			cost = 70;
		}
		if (name.equals("diamond dagger")) {
			width = 50;
			length = 70;
			range = 5;
			addbuff("dmg", 10, true);
			addbuff("agi", 100, true);
			addcrit(40, 30);
      Debuff d = new Debuff( Debuff.POISON, 50, .0);
      d.damage = 5;
      debuffs.add(d);
			cost = 30;
		}
		ImageIcon ii = new ImageIcon("resources\\images\\weapons\\"+name+".png");
		image = ii.getImage();
	}

	public void addbuff(String stat, int val, boolean raw) {
		buffs.add(new Buff(stat, val, raw));
	}

}
