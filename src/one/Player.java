package one;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Player extends Mob{
	public ArrayList<Item> inv;
	public Player(int sx, int sy,  World smyworld, Race r) {
		super(sx, sy, "player", smyworld, r);
		inv = new ArrayList<Item>();
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
		alreadydead = dead;
		return dead;
	}
	public void addItem(String s, int amount) {
		Item i = new Item(s, amount, myworld);
		inv.add(i);
		System.out.println("adding item");
		lvlup("", 0);
		for(Buff b : i.buffs) {
			System.out.println("adding buff");
			addbuff(b);
		}
		if(inv.size()>=6) {
			for( Buff b : inv.get(0).buffs) {
				subbuff(b);
			}
			for(Crit c : inv.get(0).crits) {
				subcrit(c);
			}
			inv.remove(0);
		}
	}
	public int itemsininv() {
		int a = 0;
		for(Item i : inv) {
			if(i!=null)
				a++;
		}
		return a;
	}
	public void addItem(Item i) {
		inv.add(i);
		
		for(Buff b : i.buffs) {
			addbuff(b);
		}
		for(Crit c : i.crits) {
			addcrit(c);
		}
		if(inv.size()>=6) {
			for( Buff b : inv.get(0).buffs) {
				subbuff(b);
			}
			for(Crit c : inv.get(0).crits) {
				subcrit(c);
			}
			inv.remove(0);
		}
		lvlup("", 0);
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
		if(inv.size()>0) {
			inv.get(0).draw(g, 1050, 430, 80, 80, m, false);
		} else {
			g.drawRect(1050, 430, 80, 80);
		}
		if(inv.size()>1) {
			inv.get(1).draw(g, 1140, 430, 80, 80, m, false);
		} else {
			g.drawRect(1140, 430, 80, 80);
		} 
		if(inv.size()>2) {
			inv.get(2).draw(g, 960, 520, 80, 80, m, false);
		} else {
			g.drawRect(960, 520, 80, 80);
		}
		if(inv.size()>3) {
			inv.get(3).draw(g, 1050, 520, 80, 80, m, false);
		} else {
			g.drawRect(1050, 520, 80, 80);
		}
		if(inv.size()>4) {
			inv.get(4).draw(g, 1140, 520, 80, 80, m, false);
		} else {
			g.drawRect(1140, 520, 80, 80);
		}
		weapon.draw(g, 958, 428, 84, 84, m, false);
	}
	public void drawinv(Graphics2D g) {
		g.setColor(Color.red);
		if(inv.size()>0) {
			inv.get(0).draw(g, 1050, 430, 80, 80);
		} else {
			g.drawRect(1050, 430, 80, 80);
		}
		if(inv.size()>1) {
			inv.get(1).draw(g, 1140, 430, 80, 80);
		} else {
			g.drawRect(1140, 430, 80, 80);
		} 
		if(inv.size()>2) {
			inv.get(2).draw(g, 960, 520, 80, 80);
		} else {
			g.drawRect(960, 520, 80, 80);
		}
		if(inv.size()>3) {
			inv.get(3).draw(g, 1050, 520, 80, 80);
		} else {
			g.drawRect(1050, 520, 80, 80);
		}
		if(inv.size()>4) {
			inv.get(4).draw(g, 1140, 520, 80, 80);
		} else {
			g.drawRect(1140, 520, 80, 80);
		}
		weapon.draw(g, 958, 428, 84, 84);
	}
//	@Override
//	public void move() {
//		if(!dead) {
//			lvlup();
//			double hp = totalhealth();
//			if( debuffs[Debuff.POISON].duration > 0 ) {
//        debuffs[Debuff.POISON].duration--;
//        damagefrompoison += debuffs[Debuff.POISON].damage*100;
//        if( damage(debuffs[Debuff.POISON].damage) ) {
//          clearDebuffs();
//        }
//      }
//			if(health+regen()<=hp) {
//				hpup+=regen();
//			} else {
//				hpup+=hp-health;
//			}
//			if(hpup>=1) {
//				health+=hpup;
//				hpup = hpup-(int)hpup;
//			}
//			if(hpup<0) {
//				hpup = 0;
//			}
//
//      // can only move and attack if not stunned
//			if( debuffs[Debuff.STUN].duration > 0 ) {
//        debuffs[Debuff.STUN].duration --;
//      }
//			else {
//  			int col = myworld.collides(this);
//  			if( col != World.CANTMOVE) {
//  				x += xspeed;
//  				y += yspeed;
//  				if(col>0 ) {
//  					if(this.level>col)
//  						money+=1+col*2/(this.level-col);
//  					else 
//  						money+=1+col+(col-this.level);
//  				}
//  			}
//  			if(attack!=null && acd<0 && att && !inshop) {
//  				Hit hit = attack(attack);
//  				if(hit.damage > 0) {
//  					experience+=(hit.damage*(100+intelligence())*.01);
//  				} 
//  				if(hit.kill) {
//  					experience+=hit.leveloftarget*10*(100+intelligence())*.01;
//  				} 
//  				acd = (int) (adelay()*woradelay);
//  				if(weapon.continuous)  {
//  					acd = 0;
//  				}
//  			} else if(inshop) {
//  				
//  			}
//			}
//			acd--;
//			adraw--;
//		}
//	}
	
	
	@Override
	public void tic(World w) {
		myworld = w;
	}
	public void setspeed(int xs, int ys) {
		if(xs==1) {
			xspeed = accel();
		} 
		if(xs==-1) {
			xspeed = -accel();
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
			yspeed = accel();
		}
		if(ys==-1) {
			yspeed = -accel();
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
