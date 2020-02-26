package one;

import java.net.*;
import java.util.*;

import javax.swing.*;

import one.Debuff.*;

public class Weapon extends Item {
	public int width, length;
	public int range;
	public boolean continuous;
	public ArrayList<Debuff> debuffs;
	public Weapon(String sname, int amount, World m) {
		super(sname, amount, m);
		System.out.println("Created weapon with name " + sname);
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
		} else if(name.contains("ghost")) {
      rank = 8;
    } else if(name.contains("frost")) {
      rank = 9;
      debuffs.add(new Debuff(DebuffType.SLOW, 5, 0.8));
    }
		int multiplier = (int) Math.pow(rank, 2);
		if (name.contains("bomb")) {
			width = 200+40*rank;
			length = 200+40*rank;
			range = 2*rank;
			addbuff(Attribute.DAMAGE, 5+6*multiplier, true);
      Debuff d = new Debuff( DebuffType.STUN, 10 + rank*2, .5);
      debuffs.add(d);
			cost = 100+12*multiplier;
		}
		if (name.contains("mace")) {
			width = 45+10*rank;
			length = 45+10*rank;
			range = rank*2;
			addbuff(Attribute.DAMAGE, 4+3*multiplier, true);
			addbuff(Attribute.STRENGTH, 5+5*multiplier, true);
			addcrit(5+rank, 1+.35*rank);
      Debuff d = new Debuff( DebuffType.STUN, 20 + rank*6, .8);
      debuffs.add(d);
			cost = 8*multiplier;
		}
		if (name.contains("fist")) {
			width = 40+3*rank;
			length = 40+3*rank;
			range = rank/2;
			addbuff(Attribute.DAMAGE, 2+multiplier, true);
			addbuff(Attribute.HEALTH, 10+multiplier*12, true);
			addbuff(Attribute.ATTACK_DELAY, 100-7*rank, false);
			cost = multiplier;
		}
		if (name.contains("sword")) {
			width = 100+12*rank;
			length = 20+8*rank;
			range = 2*rank;
			addbuff(Attribute.DAMAGE, 3+multiplier*3, true);
			addbuff(Attribute.INTELLIGENCE, 1+3*multiplier, true);
      Debuff d = new Debuff( DebuffType.STUN, 12, .8 - rank/20.0);
      debuffs.add(d);
			cost = 5*multiplier;
		}
		if (name.contains("dagger") && !name.contains("diamond dagger")) {
			width = 20+5*rank;
			length = 40+6*rank;
			range = (int) (1.5*rank);
			addbuff(Attribute.DAMAGE, 3+2*multiplier, true);
			addbuff(Attribute.AGILITY, 1+1*multiplier/2, true);
			addbuff(Attribute.ATTACK_DELAY, 100-6*rank, false);
      Debuff d = new Debuff( DebuffType.POISON, 10 + multiplier/2);
      d.damage = 2;
      debuffs.add(d);
			cost = 6*multiplier;
		}
		if (name.contains("battleaxe")) {
			width = 120+12*rank;
			length = 30+8*rank;
			range = 30+0*rank;
			addbuff(Attribute.DAMAGE, 4+multiplier*4, true);
			addbuff(Attribute.INTELLIGENCE, 2+4*multiplier, true);
      Debuff d = new Debuff( DebuffType.STUN, 12, .75 - rank/20.0);
      debuffs.add(d);
      //TODO add new debuff weakness next attack deals less damage
			cost = 10*multiplier;
		}
		if (name.contains("spear")) {
			width = 20+5*rank;
			length = 98+12*rank;
			range = 5+3*rank;
			addbuff(Attribute.DAMAGE, 2+multiplier*3, true);
			addbuff(Attribute.REGEN, 4+5*multiplier, true);
			addcrit(10+5*rank, 1+.10*rank);
			cost = 7*multiplier;
		}
		if (name.contains("deathaura")) {
			width = 60+10*rank;
			length = 60+10*rank;
			range = -(60+10*rank)+(60+10*rank)/2-myworld.getPlayer().w/2;
//			addbuff("dmg", 10+rank*10, false);
			addbuff(Attribute.DAMAGE, 0, false);
			addbuff(Attribute.HEALTH, 100-rank*10, false);
      Debuff d = new Debuff( DebuffType.POISON, 1);
      d.damage = rank;
      debuffs.add(d);
			continuous = true;
			cost = 50+24*multiplier;
		}
    if (name.equals("ghost beam")) {
      width = 90;
      length = 750;
      range = 5;
      addbuff(Attribute.DAMAGE, 0, false);
      Debuff d = new Debuff( DebuffType.POISON, 1);
      d.damage = 5;
      debuffs.add(d);
      d = new Debuff( DebuffType.STUN, 1, .95);
      debuffs.add(d);
      continuous = true;
      cost = 500;
    }

		if (name.equals("longbow")) {
			width = 60;
			length = 210;
			range = 190;
			addbuff(Attribute.DAMAGE, 120, false);
			addbuff(Attribute.DAMAGE, 10, true);
			addcrit(15, 2);
			cost = 25;
		}
		if (name.equals("shortbow")) {
			width = 40;
			length = 130;
			range = 125;
			addbuff(Attribute.DAMAGE, 110, false);
			addbuff(Attribute.DAMAGE, 5, true);
			addcrit(15, 1.6);
			cost = 15;
		}
		if (name.equals("test")) {
			width = 70;
			length = 160;
			range = 5;
			addbuff(Attribute.AGILITY, 300, false);
			addbuff(Attribute.STRENGTH, 500, false);
			addbuff(Attribute.INTELLIGENCE, 10, false);
			addbuff(Attribute.REGEN, 500, false);
			addbuff(Attribute.ATTACK_DELAY, 40, false);
			addbuff(Attribute.HEALTH, 500, false);
      Debuff d = new Debuff( DebuffType.STUN, 50, .1);
      debuffs.add(d);
			cost = 9999;
		}
		if (name.equals("spartan laser")) {
			width = 20;
			length = 600;
			range = 1;
			addbuff(Attribute.DAMAGE, 200, true);
			addbuff(Attribute.ATTACK_DELAY, 400, false);
			addbuff(Attribute.AGILITY, -20, true);
			cost = 200;
		}
		if (name.equals("laser")) {
			width = 4;
			length = 500;
			range = 1;
			addbuff(Attribute.DAMAGE, 1, false);
			continuous = true;
			cost = 100;
		}
		if (name.equals("diamond laser")) {
			width = 30;
			length = 300;
			range = 4;
			addbuff(Attribute.DAMAGE, 2, false);
			continuous = true;
			cost = 150;
		}
		if (name.equals("pistol")) {
			width = 50;
			length = 50;
			range = 230;
			addbuff(Attribute.DAMAGE, 10, true);
			addbuff(Attribute.ATTACK_DELAY, 200, false);
			cost = 50;
		}
		if (name.contains("sniper rifle")) {
			width = 10;
			length = 10;
			range = 300;
			addbuff(Attribute.DAMAGE, 50, true);
			addbuff(Attribute.ATTACK_DELAY, 300, false);
			cost = 70;
		}
		if (name.equals("diamond dagger")) {
			width = 50;
			length = 70;
			range = 5;
			addbuff(Attribute.DAMAGE, 10, true);
			addbuff(Attribute.AGILITY, 100, true);
			addcrit(40, 0.3);
      Debuff d = new Debuff( DebuffType.POISON, 50, .0);
      d.damage = 5;
      debuffs.add(d);
			cost = 30;
		}
		URL imageResource = Weapon.class.getClassLoader().getResource("resources/images/weapons/" + name + ".png");
		if(imageResource != null) {
		  ImageIcon ii = new ImageIcon(imageResource);
	    image = ii.getImage();
		}
	}
}
