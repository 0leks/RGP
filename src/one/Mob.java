package one;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Mob extends Thing{
	protected boolean dead;
	protected String ai;
	Random rand = new Random();
	protected int xspeed;
	protected int yspeed;
	protected Rectangle attack;
	protected int attackdirection;
	protected static final int ATTACKUP = 4;
	protected static final int ATTACKRIGHT = 1;
	protected static final int ATTACKDOWN = 2;
	protected static final int ATTACKLEFT = 3;
	
	protected int adraw;
	protected boolean att;
	protected int acd;
	/**
	 * Current health
	 */
	protected int health;
	protected int whiteline;
	protected double hpup;
	protected int money;
	protected int experience;
	protected int exptolvlup;
	protected int expatstartlvl;
	protected Weapon weapon;
	protected Race race;
	protected int asdf;
	protected boolean inshop;
	
	protected double basedamage;
	protected double damagebuff;
	protected double wordamage;
	
	protected int level;
	
	protected int totalhealthbuff;
	protected double worhealth;
	
	
	protected double woradelay;
	
	protected int accel;
	protected double woraccel;
	
	protected int agilitybuff;
	protected int actagility;
	protected double woragility;
	
	protected int strengthbuff;
	protected int actstrength;
	protected double worstrength;
	
	protected int intelligencebuff;
	protected int actintelligence;
	protected double worintelligence;
	
	protected double regen;
	protected double regenbuff;
	protected double worregen;
	
	protected int basearmor;
	protected int armorbuff;
	protected double worarmor;
	protected double damagetaken;
	
	protected ArrayList<Crit> crits;
	
	public ArrayList<Buff> buffs;
	public Debuff[] debuffs;
	
	protected Queue<Popup> popups;
	protected Popup poisonpopup;
  protected int damagefrompoison;
	
	
	public Mob(int sx, int sy, String sai, World smyworld, Race r) {
		super(sx, sy, r.startwidth, r.startheight, smyworld);
		poly = new ArrayList<MyPolygon>();
		buffs = new ArrayList<Buff>();
		crits = new ArrayList<Crit>();
		popups = new ConcurrentLinkedQueue<Popup>();
		debuffs = new Debuff[Debuff.TOTAL];
		debuffs[Debuff.STUN] = new Debuff( Debuff.STUN, 0 );
    debuffs[Debuff.POISON] = new Debuff( Debuff.POISON, 0 );
		race = r;
		ai = sai;
		dead = false;
		hpup = 0;
		asdf = 0;
		addcrit(new Crit(100, 100));
	}
	public void lvlupto(int lvl) {
		while(lvl>0) {
			experience = exptolvlup;
			lvlup();
			lvl--;
			rescale();
		}
		
	}
	public void getWeap(String type) {
		if(weapon!=null) {
			for(Buff b : weapon.buffs) {
				subbuff(b);
			}
			for(Crit c : weapon.crits) {
				subcrit(c);
			}
		}
		weapon = new Weapon(type, 1, myworld);
		for(Buff b : weapon.buffs) {
			addbuff(b);
		}
		for(Crit c : weapon.crits) {
			addcrit(c);
		}
	}
	public void addcrit(Crit c) {
		boolean added = false;
		//crits.add(0, c);
		//System.out.println("Crit:"+crits.get(0).toString());
		for(int a=0; a<crits.size(); a++) {
//			if(this instanceof Player)
//				System.out.println("Crit:"+crits.get(a).toString());
			if(c.damage>=crits.get(a).damage) {
				crits.add(a, c);
				added = true;
				return;
			}
		}
		if(crits.size() ==0) {
			crits.add(c);
		}
	}
	public void subcrit(Crit c) {
		crits.remove(c);
	}
	public void clearDebuffs() {
	  for( int a = 0; a < debuffs.length; a++ ) {
	    debuffs[a].duration = 0;
	  }
	}
	public void applyDebuff( Debuff d ) {
	  
	  Debuff existing = debuffs[d.type];
	  if( d.duration > existing.duration ) {
//	    System.out.println("Updated " + debuffs[d.type]);
	    debuffs[d.type] = new Debuff(d);
//	    System.out.println(" to " + debuffs[d.type]);
	  }
	  
	}
	public void addbuff(Buff b) {
		if(b.raw){ 
			lvlup(b.stat, b.value);
		} else {
			worlvlup(b.stat, b.value*.01);
		}
	}
	public void subbuff(Buff b) {
		if(b.raw){ 
			lvlup(b.stat, -b.value);
		} else {
			worlvlup(b.stat, 1/(double)(b.value*.01));
		}
	}
	public void rescale() {
		
		basedamage = (strength())/9;
		damagetaken = 100-armor();
		regen = strength()*.001 + regenbuff;
		if(accel<0)
			accel = 0;
	}
	/**
	 *  Checks if you have enough experience to level up, and levels up in that case.
	 */
	public void lvlup() {
		if(experience>=exptolvlup) {
			expatstartlvl = exptolvlup;
			exptolvlup += (int) (Math.pow(level, 2)*5)+30;
			lvlup("actstr", race.strinc);
			lvlup("actagi", race.agiinc);
			damagebuff+=race.dmginc;
			level++;
			lvlup();
		}
		rescale();
	}
	public void lvlup(String s, int n) {
		if(s.equals("str")) {
			strengthbuff+=n;
			health+=n;
		} 
		if(s.equals("actstr")) {
			actstrength+=n;
			health+=n;
		} 
		if(s.equals("agi")) {
			agilitybuff+=n;
		} 
		if(s.equals("actagi")) {
			actagility+=n;
		} 
		if(s.equals("int")) {
			intelligencebuff+=n;
		} 
		if(s.equals("actint")) {
			actintelligence+=n;
		} 
		if(s.equals("hp")) {
			totalhealthbuff+=n;
		} 
		if(s.equals("dmg")) {
			damagebuff+=n;
		}
		if(s.equals("reg")) {
			regenbuff+=n*.01;
		}
		if(s.equals("accel")) {
			accel+=n;
		}
		if(s.equals("Armor")) {
			armorbuff+=n;
		}
		rescale();
	}
	public void worlvlup(String s, double n) {
		if(s.equals("str")) {
			worstrength*=n;
		}
		if(s.equals("agi")) {
			woragility*=n;
		}
		if(s.equals("int")) {
			worintelligence*=n;
		}
		if(s.equals("hp")) {
			worhealth*=n;
		} 
		if(s.equals("dmg")) {
			wordamage*=n;
		}
		if(s.equals("reg")) {
			worregen*=n;
		}
		if(s.equals("accel")) {
			woraccel*=n;
		}
		if(s.equals("adelay")) {
			woradelay*=n;
		}
		if(s.equals("Armor")) {
			worarmor*=n;
		}
		rescale();
	}
	ArrayList<MyPolygon> poly;
	public static final int TOPLEFT = 1;
	public static final int TOPRIGHT = 2;
	public static final int BOTTOMLEFT = 3;
	public static final int BOTTOMRIGHT = 4;
//	public void draw(Graphics2D g) {
		
//		int drawx = (x-myworld.p.x)/World.ZOOM+470;
//		int drawy = (y-myworld.p.y)/World.ZOOM+310;
//		int w = super.w/World.ZOOM;
//		int h = super.h/World.ZOOM;
//		//g.fill(dim());
//		if(attack!=null && adraw>=0) {
//			int nx = (attack.x-myworld.p.x)/World.ZOOM+470;
//			int ny = (attack.y-myworld.p.y)/World.ZOOM+310;
//			int nw = attack.width/World.ZOOM;
//			int nh = attack.height/World.ZOOM;
//			if(attackdirection == 1 || attackdirection==3) {
//				nw = attack.height/World.ZOOM;
//				nh = attack.width/World.ZOOM;
//			}
//			if(nx+nw>-50 && nx-nw<990 && ny+nh>-50 && ny-nh<670) {
//				Color cur = g.getColor();
//				if(hostile()) {
//					g.setColor(Color.red);
//				} else {
//					g.setColor(Color.green);
//				}
//				Graphics2D g2d = (Graphics2D)g;
//				g2d.translate(nx, ny);
//				g2d.rotate(Math.toRadians(attackdirection*90));
//				// TODO asjkldfhajks
//				if(myworld.drawimage) {
////					g2d.drawImage(weapon.image, nx, ny, nw, nh, null);
//					g2d.drawImage(weapon.image, -nw/2, -nh/2, nw, nh, null);
//
//				}
////				g.draw(new Rectangle(nx, ny, nw, nh));
//				g.draw(new Rectangle(-nw/2, -nh/2, nw, nh));
//				g.setColor(cur);
//				g2d.rotate(Math.toRadians((4-attackdirection)*90));
//				g2d.translate(-nx, -ny);
//			}
//		} else {
//			attackdirection = 0;
//		}
//		if(!dead) {
//			g.setColor(Color.blue);
//		} else {
//			g.setColor(Color.black);
//		}
//		Color[] col = { g.getColor(), Color.red, new Color(0, 200, 0), Color.magenta, Color.cyan};
//		super.draw(g, col);
//		int distx = 0;
//		int disty = 0;
//		if(myworld.draw3d) {
//			distx = (drawx-470)/10/World.ZOOM;
//			disty = (drawy-310)/10/World.ZOOM;
//		}
//		if(drawx>-50 && drawx<990 && drawy>-50 && drawy<670 && World.ZOOM == 1) {
//			g.setColor(Color.white);
//			int l = Integer.toString(level).toCharArray().length;
//			g.drawString(this.level+"", drawx-l*5+2+distx, drawy+g.getFont().getSize()/2-1+disty);
//			
//			g.setColor(new Color(200, 200, 200));
//			double f = (double)health/totalhealth();
//			g.fillRect(drawx-w/2+distx, drawy-h/2-13+disty, whiteline/10, 8);
//
////			if( damagefrompoison > 0 ) {
////        g.setColor(new Color(0, 250, 0));
////        int green = (int) (damagefrompoison/100 * whiteline / (double)totalhealth());
////        g.fillRect(drawx-w/2+distx + whiteline/10 - green/10, drawy-h/2-13+disty, green/10, 8);
////        
////        damagefrompoison -= 1;
////        if( damagefrompoison < 0 ) 
////          damagefrompoison = 0;
////			}
//      
//			if(whiteline/10>(f*w))
//				whiteline-=w/30;
//			if(whiteline/10<f*w)
//				whiteline = (int) (f*w*10);
//			
//
//			
//			if(hostile()) {
//				g.setColor(Color.red);
//			} else {
//				g.setColor(new Color( 0, 190, 20));
//			}
//			g.drawRect(drawx-w/2+distx, drawy-h/2-13+disty, w, 8);
//			g.fillRect(drawx-w/2+distx, drawy-h/2-13+disty, (int) (f*w), 8);
//			
//			if(this instanceof Player) {
//				g.setColor(new Color(150, 150, 0));
//				g.drawRect(drawx-w/2, drawy-h/2-4, w, 4);
//				g.setColor(Color.yellow);
//				g.fillRect(drawx-w/2, drawy-h/2-4, (int) ((double)(this.experience-this.expatstartlvl)/(double)(this.exptolvlup-this.expatstartlvl)*w), 4);
//			}
////			g.setColor(cur);
//		}
//	}
//	public void drawpopups(Graphics2D g ) {
//		int drawx = (x-myworld.p.x)/World.ZOOM+470;
//		int drawy = (y-myworld.p.y)/World.ZOOM+310;
//		//int w = super.w/World.ZOOM;
//		//int h = super.h/World.ZOOM;
//		boolean draw = false;
//		if(drawx>-50 && drawx<990 && drawy>-50 && drawy<670) {
//			draw = true;
//		}
//		Iterator<Popup> itpop = popups.iterator();
//		while( itpop.hasNext() ) {
//			Popup pop = itpop.next();
//	    g.setColor(pop.color);
//			if(draw)
//				g.drawString(pop.string, drawx+pop.x(), drawy+pop.y());
//			if(pop.drawn()) {
//				popups.remove(pop);
//			}
//		}
//	}
	public boolean damage(int d) {
		health-=d;
		dead = (health<=0);
		return dead;
	}
	public Rectangle nextdim() {
		return new Rectangle(x-w/2+xspeed, y-h/2+yspeed, w, h);
	}
	public void move() {
		if(!dead) {
			rescale();
			lvlup();
			double thealth = totalhealth();
			if( debuffs[Debuff.POISON].duration > 0 ) {
			  debuffs[Debuff.POISON].duration--;
			  
			  // If die from the poison
			  if( damage(debuffs[Debuff.POISON].damage) ) {
			    clearDebuffs();
			    poisonpopup = null;
			  } else {
  			  if( poisonpopup == null || poisonpopup.done()) {
  			    damagefrompoison = debuffs[Debuff.POISON].damage;
  			    int dur = (debuffs[Debuff.POISON].duration<100)?300:debuffs[Debuff.POISON].duration;
  			    poisonpopup = new Popup(damagefrompoison+"", dur, Color.green);
  			    this.popups.add(poisonpopup);
    			}
    			else {
            damagefrompoison += debuffs[Debuff.POISON].damage;
    			  poisonpopup.string = damagefrompoison + "";
    			}
			  }
			}
			if(health+regen()<=thealth) {
				hpup+=regen();
			} else {
				if(health<thealth)
					hpup+=thealth-health;
			}
			if(hpup>=1) {
				health+=(int)hpup;
				hpup = hpup-(int)hpup;
			}
			if(hpup<0) {
				hpup = 0;
			}
			boolean nomiss = ai.contains("nomiss");
			int randomize = 5;
			if(nomiss) {
				randomize = 4;
			}
			if(ai.contains("random")) {
				int a = rand.nextInt(5);
				if(a==0) {
					int x = rand.nextInt(3)-1;
					int y = rand.nextInt(3)-1;
					xspeed = x*accel();
					yspeed = y*accel();
				}
				a = rand.nextInt(randomize);
				if(a==0) {
					setAttack("up");
				} else if(a==1) {
					setAttack("down");
				} else if(a==2) {
					setAttack("left");
				} else if(a==3) {
					setAttack("right");
				}
			}
			if(ai.contains("bettermovetowardsyou")) {
					asdf+=1;
					if(asdf>=3600)
						asdf = 0;
					int dx;
					int dy;
					if(x<myworld.p.x-accel) {
						dx = rand.nextInt(3);
						dx = (int)((dx+1)/2);
					} else if(x>myworld.p.x+accel) {
						dx = rand.nextInt(3);
						dx = -(int)((dx+1)/2);
					} else {
						dx= rand.nextInt(3)-1;
					}
					if(y<myworld.p.y-accel) {
						dy = rand.nextInt(2);
						dy = (int)((dy+1)/2);
					} else if(y>myworld.p.y+accel) {
						dy = rand.nextInt(2);
						dy = -(int)((dy+1)/2);
					} else {
						dy= rand.nextInt(3)-1;
					}
					xspeed = (int) (dx*accel());
					yspeed = (int) (dy*accel());
					int a = rand.nextInt(randomize);
					if(a==0) {
						setAttack("up");
					} else if(a==1) {
						setAttack("down");
					} else if(a==2) {
						setAttack("left");
					} else if(a==3) {
						setAttack("right");
					}
				}
			if(ai.contains("sway")) {
				asdf+=1;
				if(asdf>=360)
					asdf = 0;
				double x =  (Math.sin(2*torad(asdf)));
				double y =  (Math.cos(3*torad(asdf)));
				xspeed = (int) (x*accel());
				yspeed = (int) (y*accel());
				int a = rand.nextInt(randomize);
				if(a==0) {
					setAttack("up");
				} else if(a==1) {
					setAttack("down");
				} else if(a==2) {
					setAttack("left");
				} else if(a==3) {
					setAttack("right");
				}
			}
			if(ai.contains("horizontalpatrol")) {
				asdf+=1;
				if(asdf>=3600)
					asdf = 0;
				int lengthofzigzag = 50;
				double x =  Math.pow(-1, asdf/lengthofzigzag-(int)(asdf/(lengthofzigzag*2))*2);
				double y =  -(Math.sin(torad(40*asdf)));
				xspeed = (int) (x*accel());
				yspeed = (int) (y*0);//(int) (y*accel());
				int a = rand.nextInt(randomize);
				if(a==0) {
					setAttack("up");
				} else if(a==1) {
					setAttack("down");
				} else if(a==2) {
					setAttack("left");
				} else if(a==3) {
					setAttack("right");
				}
				
			} else if(ai.contains("zigzag")) {
				asdf+=1;
				if(asdf>=3600)
					asdf = 0;
				double x =  Math.pow(-1, asdf/20-(int)(asdf/40)*2);
				double y =  -(Math.sin(torad(3*asdf)));
				xspeed = (int) (x*accel());
				yspeed = (int) (y*accel());
				int a = rand.nextInt(randomize);
				if(a==0) {
					setAttack("up");
				} else if(a==1) {
					setAttack("down");
				} else if(a==2) {
					setAttack("left");
				} else if(a==3) {
					setAttack("right");
				}
			}
			if(ai.contains("hunter")) {
				asdf+=1;
				if(asdf>=3600)
					asdf = 0;
				int dx;
				int dy;
				if(x<myworld.p.x-accel) {
					dx = 1;
				} else if(x>myworld.p.x+accel) {
					dx = -1;
				} else {
					dx= 0;
				}
				if(y<myworld.p.y-accel) {
					dy = 1;
				} else if(y>myworld.p.y+accel) {
					dy = -1;
				} else {
					dy= 0;
				}
				xspeed = (int) (dx*accel());
				yspeed = (int) (dy*accel());
				int a = rand.nextInt(randomize);
				if(a==0) {
					setAttack("up");
				} else if(a==1) {
					setAttack("down");
				} else if(a==2) {
					setAttack("left");
				} else if(a==3) {
					setAttack("right");
				}
			}

      // can only move and attack if not stunned
			if( debuffs[Debuff.STUN].duration > 0 ) {
			  debuffs[Debuff.STUN].duration --;
			}
			else {
  			int col = myworld.collides(this);
  			if(col != World.CANTMOVE) {
  				x += xspeed;
  				y += yspeed;
  				if(col>0) {
  					experience+=col;
            if(this.level>col)
              money+=1+col*2/(this.level-col);
            else 
              money+=1+col+(col-this.level);
  				}
  			}
  			
  			// Can only attack if attack box is set, attack cooldown is ready, don't know what att is
  			// the inshop boolean is only set for the player, so it only has effect when the player is in a shop, mobs can attack anyways
  			if(attack!=null && acd<0 && att && !inshop) {
  				Hit hit = attack(attack);
  				if(hit.damage > 0) {
  					experience+=(hit.damage*(100+intelligence())*.01);
  				} 
  				if(hit.kill) {
  					experience+=hit.leveloftarget*10*(100+intelligence())*.01;
  				} 
  				acd = (int) (adelay()*woradelay);
  				if(weapon.continuous) 
  					acd = 0;
  			}
			}
			// attack cooldown ticks whether or not stunned
      acd--;
		}
		adraw--;
	}
	// TODO asdfqsdaf
	public void setAttack(String dir) {
		if(weapon == null) { //weapon has not been initializéd, so return function
			return;
		}
		int wi = weapon.width;
		int le = weapon.length;
		int ra= weapon.range;
		if(!att && acd<0) {
			if(this instanceof Player) {
				System.out.println("player is attacking " + dir);
			}
			if(dir.equals("up")) {
				attackdirection = Mob.ATTACKUP;
				attack = new Rectangle(x, y-ra-le/2-h/2, wi, le);
			}
			if (dir.equals("left")) {
				attackdirection = Mob.ATTACKLEFT;
				attack = new Rectangle(x-ra-le/2-w/2, y, le, wi);
//				attack = new Rectangle(x-ra-le/2-w/2, y, le, wi);
			}
			if (dir.equals("down")) {
				attackdirection = Mob.ATTACKDOWN;
				attack = new Rectangle(x, y+ra+h/2+le/2, wi, le);
			}
			if (dir.equals("right")) {
				attackdirection = Mob.ATTACKRIGHT;
				attack = new Rectangle(x+ra+w/2+le/2, y, le, wi);
//				attack = new Rectangle(x+ra+w/2+le/2, y, le, wi);
			}
			att = true;
			adraw = (int) (adelay()*woradelay/2);
			if (adraw<=0)
				adraw = 1;
			if(weapon.continuous) {
				adraw = 1;
			}
		}
	}
	public int getdmgaftercrit(int dmg, Mob target) {
		int afterdmg = dmg;
		//System.out.println(this.race.name+" is finding dmg after crit!");
		for(int a=0; a<crits.size(); a++) {
			Crit c = crits.get(a);
			if(c.boom()) {
				return (int) (c.getdamage(dmg)*target.damagetaken*.01);
			}
			//afterdmg = c.getdamage(dmg);
		}
		return (int)(afterdmg*target.damagetaken*.01);
		
	}
	public Hit attack(Rectangle r) {
	  
//		if(attackdirection==1 || attackdirection==3) {
//			r = new Rectangle(r.x-r.height/2, r.y-r.width/w, r.height, r.width);
//		} else {
			r = new Rectangle(r.x-r.width/2, r.y-r.height/2, r.width, r.height);
//		}
		Hit hit = new Hit();
		int mou = myworld.mobs().size();
		int dmg = damage();
		if(dmg == 0)
			dmg++;
		Iterator<Mob> itmob = myworld.mobs().iterator();
		while(itmob.hasNext()) {
			Mob m = itmob.next();
			if(m != this && m.dim().intersects(r)) {
				hit.leveloftarget = m.level;
				int dmgtodeal = getdmgaftercrit(dmg, m);
				if(m.dead) {
					hit.damage = 0;
				} else if(m.damage(dmgtodeal)) {
					if(m.race.name.equals("bigboss")) {
						myworld.addMessage("A Boss has been defeated", 100);
					}
					hit.kill = true;
					m.popups.add(new Popup(dmgtodeal+"", 300));
					hit.damage+=dmgtodeal;
				} else {
					m.popups.add(new Popup(dmgtodeal+"", 300));
					hit.damage+=dmgtodeal;
					
					for( Debuff debuff : weapon.debuffs ) {
					  if( Math.random() >= debuff.chance ) {
					    m.applyDebuff(debuff);
					  }
					}
				}
			}
			mou = myworld.mobs().size();
		}
		Player m = myworld.p;
		if(m != this && m.dim().intersects(r)) {
			int dmgtodeal = getdmgaftercrit(dmg, m);
			m.popups.add(new Popup(dmgtodeal+"", 300));
			if(m.damage(dmgtodeal)) {
				hit.kill = true;
				hit.damage+=dmgtodeal;
			} else {
				hit.damage+=dmgtodeal;
				for( Debuff debuff : weapon.debuffs ) {
          if( Math.random() >= debuff.chance ) {
            m.applyDebuff(debuff);
          }
        }
			}
		}
		att = false;
		return hit;
	}
	
	public int health() {
		return health;
	}
	public void tic(World w) {
		myworld = w;
	}
	public boolean hostile() {
		return (ai.contains("hostile"));
	}
	
	public int damage() {
		return (int) ((basedamage+damagebuff)*wordamage);
	}
	public int agility() {
		int agi = (int) ((agilitybuff+actagility)*woragility);
		if(agi>=0) 
			return agi;
		else 
			return 0;
	}
	public int intelligence() {
		int intel = (int) ((intelligencebuff+actintelligence)*worintelligence*5);
		if(intel>=1)
			return intel;
		else
			return 1;
	}
	public int armor() {
		int arm = (int) ((armorbuff+basearmor)*worarmor);
		if(arm>=100) {
			arm = 99;
		}
		return arm;
	}
	public int strength() {
		int str = (int) ((strengthbuff+actstrength)*worstrength);
		if(str>=0) 
			return str;
		else
			return 0;
	}
	public int totalhealth() {
		return (int) ((strength()+totalhealthbuff)*worhealth);
	}
	public int accel() {
		return (int) (accel*woraccel);
	}
	public int adelay() {
		
		if(weapon.continuous)
			return 0;
		else {
			int agi = (int) (agility());
			if(agi>200) {
				agi = 200;
			}
			int ad = (int)( (1500/(agi+1))*woradelay);//(int)(((150-agi/5))*woradelay);
			if(ad>200) {
				ad = 200;
			}
			return ad;
		}
	}
	public double regen() {
		return (regen)*worregen;
	}
	public String tosave() {
		String s = "Mob "+race.name+" "+experience+" "+money+" "+x+" "+y;
		s+=" "+health+" "+weapon.name.replace(' ', '_') + " " + ai.replace(' ', '_');
		return s;
	}
	public int todeg(double rad) {
		return (int) (rad*180/Math.PI);
	}
	public double torad(double deg) {
		return deg*Math.PI/180;
	}
}
