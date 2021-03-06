package one;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import one.Debuff.*;


public class Mob extends Thing {
  
  public enum AttackDirection {
    UP(0), RIGHT(90), DOWN(180), LEFT(270);
    private int value;
    private AttackDirection(int value) {
      this.value = value;
    }
    public int getAngle() {
      return value;
    }
    public static AttackDirection getRandomAttackDirection() {
      return AttackDirection.values()[(int) (AttackDirection.values().length*Math.random())];
    }
  }
  
  public static final Color BASH_COLOR = Color.GRAY;
  public static final Color SLOW_COLOR = new Color(20, 20, 150);
  
  private static final int STR_HEALTH_MULTIPLIER = 2;

  public static final int INVENTORY_SIZE = 5;

  private Random rand = new Random();
  
	private boolean dead;
	private String ai;
	private int xspeed;
	private int yspeed;
	
	private Rectangle attack;
	private AttackDirection attackdirection;
	private int drawDelay;
	private boolean attackReady;
	private int attackCooldown;
	/**
	 * Current health
	 */
	private int currentHealth;
	private int whiteline;
	private double healingBuffer;
	protected int money;
	protected int experience;
	protected int exptolvlup;
	protected int expatstartlvl;
	protected Weapon weapon;
	protected Race race;
	private int aiCounter;
	private boolean inshop;

  public HashMap<Attribute, Double> attributes;
	
	protected double basedamage;
	protected double damageMultiplier;
	
	protected int level;
	
	protected double maximumHealthMultiplier;
	protected double attackDelayMultiplier;
	protected double accelerationMultiplier;
	protected double agilityMultiplier;
	protected double strengthMultiplier;
	protected double intelligenceMultiplier;
	
	protected double regen;
	protected double regenMultiplier;
	
	protected int basearmor;
	protected double armorMultiplier;
	
	private ArrayList<Crit> crits;
	
	public ArrayList<Buff> buffs;
	public HashMap<DebuffType, Debuff> debuffs;
	
	protected Queue<Popup> popups;
	private Popup poisonpopup;
  private int damagefrompoison;

  public ArrayList<Item> inv;
	
	public Mob(int sx, int sy, String sai, World smyworld, Race r) {
		super(sx, sy, r.startwidth, r.startheight, smyworld);
		attributes = new HashMap<>();
		for(Attribute attribute : Attribute.values()) {
		  if(attribute == Attribute.ATTACK_DELAY)
		    continue;
      attributes.put(attribute, 1.0);
		}
    inv = new ArrayList<Item>();
		buffs = new ArrayList<Buff>();
		crits = new ArrayList<Crit>();
		popups = new ConcurrentLinkedQueue<Popup>();
		debuffs = new HashMap<>();
		for( DebuffType type : DebuffType.values() ) {
	    debuffs.put(type, new Debuff( type, 0 ));
		}
		race = r;
		ai = sai;
		addcrit(new Crit(100, 1));
		rescale();
    checkForLevelUp();
	}
  
  
	public void removeItem( Item item ) {
    for( Buff b : item.buffs) {
      subbuff(b);
    }
    for(Crit c : item.crits) {
      removeCrit(c);
    }
    inv.remove(item);
    rescale();
	}
  public void addItem(String s, int amount) {
    Item i = new Item(s, amount, myworld);
    inv.add(i);
    rescale();
    for(Buff b : i.buffs) {
      addbuff(b);
    }
    if(inv.size()>INVENTORY_SIZE) {
      removeItem(inv.get(0));
    }
    rescale();
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
    if(inv.size()>INVENTORY_SIZE) {
      removeItem(inv.get(0));
    }
    rescale();
  }
	public void lvlupto(int lvl) {
		while(level < lvl) {
			experience = exptolvlup;
			checkForLevelUp();
			rescale();
		}
	}
  public void lvlupby(int lvl) {
    while(lvl>0) {
      experience = exptolvlup;
      checkForLevelUp();
      lvl--;
      rescale();
    }
  }
	
	/**
	 * Gives this mob the weapon specified by the input string
	 */
	public void getWeap(String type) {
	  // First remove buffs from current weapon
		if(weapon!=null) {
			for(Buff b : weapon.buffs) {
				subbuff(b);
			}
			for(Crit c : weapon.crits) {
				removeCrit(c);
			}
		}
		
		// Then create new weapon and add the buffs associated with it.
		weapon = new Weapon(type, 1, myworld);
		for(Buff b : weapon.buffs) {
			addbuff(b);
		}
		for(Crit c : weapon.crits) {
			addcrit(c);
		}
	}
	public void addcrit(Crit c) {
		for(int a=0; a<crits.size(); a++) {
		  if( c.isBetterThan(crits.get(a)) ) {
        crits.add(a, c);
        return;
		  }
		}
		if(crits.size() == 0) {
			crits.add(c);
		}
	}
	public void removeCrit(Crit c) {
		crits.remove(c);
	}
	public void clearDebuffs() {
	  for( Debuff debuff : debuffs.values() ) {
	    debuff.duration = 0;
	  }
	}
	public void applyDebuff( Debuff d ) {
	  Debuff existing = debuffs.get(d.type);
	  if( d.duration > existing.duration ) {
	    debuffs.put(d.type, new Debuff(d));
	  }
	}
	
	public boolean isStunned( ) {
	  if( debuffs.get(DebuffType.STUN).duration > 0 ) {
	    return true;
	  }
	  return false;
	}

  public boolean isSlowed( ) {
    if( debuffs.get(DebuffType.SLOW).duration > 0 ) {
      return true;
    }
    return false;
  }
	
	public void addbuff(Buff b) {
		if(b.isMultiplier()){ 
			levelUpAttribute(b.stat, b.value);
		} else {
			levelUpMultiplier(b.stat, b.value*.01);
		}
	}
	public void subbuff(Buff b) {
		if(b.isMultiplier()){ 
			levelUpAttribute(b.stat, -b.value);
		} else {
			levelUpMultiplier(b.stat, 1/(double)(b.value*.01));
		}
	}
	public void rescale() {
		basedamage = (getStrength())/9;
		regen = getStrength()*.001 + attributes.get(Attribute.REGEN);
		if(attributes.get(Attribute.ACCELERATION)<0)
		  attributes.put(Attribute.ACCELERATION, 0.0);
	}
	/**
	 *  Checks if you have enough experience to level up, and levels up in that case.
	 */
	public void checkForLevelUp() {
		while(experience>=exptolvlup) {
			expatstartlvl = exptolvlup;
			exptolvlup += (int) (Math.pow(level, 2)*5)+30;
			levelUpAttribute(Attribute.ACTUAL_STRENGTH, race.strinc);
			levelUpAttribute(Attribute.ACTUAL_AGILITY, race.agiinc);
      attributes.put(Attribute.DAMAGE, attributes.get(Attribute.DAMAGE) + race.dmginc);
			level++;
		}
		rescale();
	}
	public void levelUpAttribute(Attribute attribute, int n) {
	  if( attribute == Attribute.STRENGTH ||
	      attribute == Attribute.ACTUAL_STRENGTH) {
	    attributes.put(attribute, attributes.get(attribute) + n);
			setCurrentHealth(getCurrentHealth() + STR_HEALTH_MULTIPLIER*n);
		}
    if( attribute == Attribute.AGILITY ||
        attribute == Attribute.ACTUAL_AGILITY ||
        attribute == Attribute.INTELLIGENCE ||
        attribute == Attribute.ACTUAL_INTELLIGENCE ||
        attribute == Attribute.HEALTH ||
        attribute == Attribute.DAMAGE ||
        attribute == Attribute.ACCELERATION ||
        attribute == Attribute.ARMOR) {
      attributes.put(attribute, attributes.get(attribute) + n);
		}
    if( attribute == Attribute.REGEN ) {
      attributes.put(attribute, attributes.get(attribute) + n*0.01);
		}
		rescale();
	}
	public void levelUpMultiplier(Attribute attribute, double n) {
    if( attribute == Attribute.STRENGTH ) {
			strengthMultiplier*=n;
		}
    if( attribute == Attribute.AGILITY ) {
			agilityMultiplier*=n;
		}
    if( attribute == Attribute.INTELLIGENCE ) {
			intelligenceMultiplier*=n;
		}
    if( attribute == Attribute.HEALTH ) {
			maximumHealthMultiplier*=n;
		} 
    if( attribute == Attribute.DAMAGE ) {
			damageMultiplier*=n;
		}
    if( attribute == Attribute.REGEN ) {
			regenMultiplier*=n;
		}
    if( attribute == Attribute.ACCELERATION ) {
			accelerationMultiplier*=n;
		}
    if( attribute == Attribute.ATTACK_DELAY ) {
			attackDelayMultiplier*=n;
		}
    if( attribute == Attribute.ARMOR ) {
			armorMultiplier*=n;
		}
		rescale();
	}
	/**
	 * @return true if killed, false otherwise
	 */
	public boolean damage(int d) {
	  setCurrentHealth(getCurrentHealth() - d);
		updateDeadStatus();
		return isDead();
	}
	public Rectangle nextPosition() {
		return new Rectangle(x-w/2+getXSpeed(), y-h/2+getYSpeed(), w, h);
	}
	
	private void decrementDebuffs() {
	  for( Debuff debuff : debuffs.values() ) {
	    if( debuff.duration > 0 ) {
	      debuff.duration--;
	    }
	  }
	}
	
	public void handlePoison() {
	  Debuff poisonDebuff = debuffs.get(DebuffType.POISON);
	  if( poisonDebuff.duration > 0 ) { // If its poisoned
      // If die from the poison
      if( this.damage(poisonDebuff.damage) ) {
        clearDebuffs(); // clear debuffs so that it isn't still poisoned on respawn.
        poisonpopup = null; // delete the green poison damage popup
      } else {
        if( poisonpopup == null || poisonpopup.done()) { // if it does not currently have a poison popup
          damagefrompoison = poisonDebuff.damage;
          
          int durationOfPopup = (poisonDebuff.duration<100)?300:poisonDebuff.duration; // duration of popup is 100 or more
          
          poisonpopup = new Popup(poisonDebuff.damage+"", durationOfPopup, Color.green);
          
          this.popups.add(poisonpopup);
        }
        else {
          damagefrompoison += poisonDebuff.damage;
          poisonpopup.string = damagefrompoison + "";
        }
      }
    }
	}
	
	public void fillHealingBuffer() {
    double maximumHealth = getMaximumHealth(); // get the maximum health of this mob
    
    if(getCurrentHealth()+getHealthRegen()<=maximumHealth) { // health is an integer.
      healingBuffer+=getHealthRegen();              // hpup is used as a buffer to store up and decimal additions to heal
    } else {
      if(getCurrentHealth()<maximumHealth)
        healingBuffer+=maximumHealth-getCurrentHealth();
    }
	}
	public void useHealingBuffer() {
	  if(healingBuffer>=1) {                 // transfer from the double hpup buffer to the actual health of the mob.
	    setCurrentHealth(getCurrentHealth() + (int)healingBuffer);
      healingBuffer = healingBuffer-(int)healingBuffer;
    }
    healingBuffer = Math.max(0, healingBuffer);    // make sure hpup is non negative ( just in case )
	}
	
	public void move() {
		if(!isDead()) {
			rescale(); // something to do with stats
			checkForLevelUp(); // check if it got a level up
			decrementDebuffs();
      handlePoison();
      
      fillHealingBuffer();
      useHealingBuffer();
			
			boolean nomiss = ai.contains("nomiss");
			double chanceToHit = 0.8;
			if( nomiss ) {
			  chanceToHit = 1;
			}
			if(ai.contains("leftattack")) {
        setAttack(AttackDirection.LEFT);
			}
      if(ai.contains("rightattack")) {
        setAttack(AttackDirection.RIGHT);
      }
      if(ai.contains("upattack")) {
        setAttack(AttackDirection.UP);
      }
      if(ai.contains("downattack")) {
        setAttack(AttackDirection.DOWN);
      }
			if(ai.contains("random")) {
				int a = rand.nextInt(5);
				if(a==0) {
					int x = rand.nextInt(3)-1;
					int y = rand.nextInt(3)-1;
					setXSpeed(x*getAccel());
					setYSpeed(y*getAccel());
				}
				if( Math.random() < chanceToHit ) {
				  setAttack(AttackDirection.getRandomAttackDirection());
				}
			}
			if(ai.contains("bettermovetowardsyou")) {
					aiCounter+=1;
					if(aiCounter>=3600)
						aiCounter = 0;
					int dx;
					int dy;
					if(x<myworld.getPlayer().x - attributes.get(Attribute.ACCELERATION)) {
						dx = rand.nextInt(3);
						dx = (int)((dx+1)/2);
					} else if(x>myworld.getPlayer().x + attributes.get(Attribute.ACCELERATION)) {
						dx = rand.nextInt(3);
						dx = -(int)((dx+1)/2);
					} else {
						dx= rand.nextInt(3)-1;
					}
					if(y<myworld.getPlayer().y - attributes.get(Attribute.ACCELERATION)) {
						dy = rand.nextInt(2);
						dy = (int)((dy+1)/2);
					} else if(y>myworld.getPlayer().y + attributes.get(Attribute.ACCELERATION)) {
						dy = rand.nextInt(2);
						dy = -(int)((dy+1)/2);
					} else {
						dy= rand.nextInt(3)-1;
					}
					setXSpeed((int) (dx*getAccel()));
					setYSpeed((int) (dy*getAccel()));
	        if( Math.random() < chanceToHit ) {
	          setAttack(AttackDirection.getRandomAttackDirection());
	        }
				}
			if(ai.contains("sway")) {
				aiCounter+=1;
				if(aiCounter>=360)
					aiCounter = 0;
				double x =  (Math.sin(2*toRadians(aiCounter)));
				double y =  (Math.cos(3*toRadians(aiCounter)));
				setXSpeed((int) (x*getAccel()));
				setYSpeed((int) (y*getAccel()));
        if( Math.random() < chanceToHit ) {
          setAttack(AttackDirection.getRandomAttackDirection());
        }
			}
			if(ai.contains("horizontalpatrol")) {
				aiCounter+=1;
				if(aiCounter>=3600)
					aiCounter = 0;
				int lengthofzigzag = 50;
				double x =  Math.pow(-1, aiCounter/lengthofzigzag-(int)(aiCounter/(lengthofzigzag*2))*2);
				double y =  -(Math.sin(toRadians(40*aiCounter)));
				setXSpeed((int) (x*getAccel()));
				setYSpeed((int) (y*0));//(int) (y*accel()));
        if( Math.random() < chanceToHit ) {
          setAttack(AttackDirection.getRandomAttackDirection());
        }
				
			} else if(ai.contains("zigzag")) {
				aiCounter+=1;
				if(aiCounter>=3600)
					aiCounter = 0;
				double x =  Math.pow(-1, aiCounter/20-(int)(aiCounter/40)*2);
				double y =  -(Math.sin(toRadians(3*aiCounter)));
				setXSpeed((int) (x*getAccel()));
				setYSpeed((int) (y*getAccel()));
        if( Math.random() < chanceToHit ) {
          setAttack(AttackDirection.getRandomAttackDirection());
        }
			}
			if(ai.contains("hunter")) {
				aiCounter+=1;
				if(aiCounter>=3600)
					aiCounter = 0;
				int dx;
				int dy;
				if(x<myworld.getPlayer().x() - attributes.get(Attribute.ACCELERATION)) {
					dx = 1;
				} else if(x>myworld.getPlayer().x() + attributes.get(Attribute.ACCELERATION)) {
					dx = -1;
				} else {
					dx= 0;
				}
				if(y<myworld.getPlayer().y() - attributes.get(Attribute.ACCELERATION)) {
					dy = 1;
				} else if(y>myworld.getPlayer().y() + attributes.get(Attribute.ACCELERATION)) {
					dy = -1;
				} else {
					dy= 0;
				}
				setXSpeed((int) (dx*getAccel()));
				setXSpeed((int) (dy*getAccel()));
        if( Math.random() < chanceToHit ) {
          setAttack(AttackDirection.getRandomAttackDirection());
        }
			}

      // can only move and attack if not stunned
			if( !isStunned() ) {
			  // Attempt to move 5 times, each time a smaller distance 
			  for( int a = 0; a < 5; a++ ) {
    			int col = myworld.collides(this);
    			if(col != World.CANTMOVE) {
    				x += getXSpeed();
    				y += getYSpeed();
    				if(col>0) {
    					experience+=col;
              if(this.level>col)
                money+=1+col*2/(this.level-col);
              else 
                money+=1+col+(col-this.level);
    				}
    				break;
    			}
    			setXSpeed(getXSpeed()/2);
          setYSpeed(getYSpeed()/2);
			  }
  			handleAttacking();
			}
			// attack cooldown ticks whether or not stunned
			decrementAttackCooldown();
		}
		decremenetDrawDelay();
	}
	
	public void handleAttacking() {
	// Can only attack if attack box is set, attack cooldown is ready. No idea what att is
    // the inshop boolean is only set for the player, so it only has effect when the player is in a shop, mobs can attack anyways
    if(getAttackCooldown()<0 && isAttackReady() && !isInShop()) {
      getAttack().ifPresent(attack -> {
        Hit hit = attack(attack);
        if(hit.damage > 0) {
          experience+=(hit.damage*(100+getIntelligence())*.01);
        } 
        if(hit.kill) {
          experience+=hit.leveloftarget*10*(100+getIntelligence())*.01;
        } 
        setAttackCooldown((int) (getAttackDelay()*attackDelayMultiplier + randomAttackDelayModifier()));
        if(weapon.continuous) {
          setAttackCooldown(0);
        }
      });
    }
	}
	public static double randomAttackDelayModifier() {
	  return 0.98 + Math.random() * 0.04;
	}
	public boolean isFullInventory() {
	  return inv.size() >= 5;
	}
	public boolean isEmptyInventory() {
	  return inv.size() == 0;
	}
  public Item removeCheapestItem() {
    Item cheapest = null;
    if( !inv.isEmpty() ) {
      cheapest = inv.get(0);
      for( int i = 1; i < inv.size(); i++ ) {
        if( inv.get(i).cost < cheapest.cost ) { 
          cheapest = inv.get(i);
        }
      }
      removeItem(cheapest);
    }
    return cheapest;
  }
	public Item removeMostExpensiveItem() {
	  Item best = null;
	  if( !inv.isEmpty() ) {
	    best = inv.get(0);
	    for( int i = 1; i < inv.size(); i++ ) {
	      if( inv.get(i).cost > best.cost ) { 
	        best = inv.get(i);
	      }
	    }
	    removeItem(best);
	  }
	  return best;
	}
  public int getMostExpensiveItemCost() {
    int best = -1;
    if( !inv.isEmpty() ) {
      best = inv.get(0).cost;
      for( int i = 1; i < inv.size(); i++ ) {
        if( inv.get(i).cost > best ) { 
          best = inv.get(i).cost;
        }
      }
    }
    return best;
  }
	public int getCheapestItemCost() {
	  int cheapest = -1;
	  if( !inv.isEmpty() ) {
	    cheapest = inv.get(0).cost;
      for( int i = 1; i < inv.size(); i++ ) {
        if( inv.get(i).cost < cheapest ) { 
          cheapest = inv.get(i).cost;
        }
      }
	  }
	  return cheapest;
	}
	public String inventoryToString() {
	  String str = "Inv=(";
	  for(int i = 0; i < inv.size(); i++ ) {
	    str += inv.get(i).name + ":" + inv.get(i).cost;
	    if( i != inv.size()-1 ) {
	      str += ", ";
	    }
	  }
	  return str + ")";
	}
	public void collectItems(Mob dead) {
	  while( !isFullInventory() && !dead.isEmptyInventory() ) {
	    // take the most expensive item from dead.
	    Item best = dead.removeMostExpensiveItem();
	    if( best != null ) {
	      addItem(best);
	    }
	  }
	  int worst = getCheapestItemCost();
	  int deadBest = dead.getMostExpensiveItemCost();
	  while( !dead.isEmptyInventory() && worst != -1 && worst < deadBest ) {
	    Item best = dead.removeMostExpensiveItem();
	    removeCheapestItem();
	    addItem(best);
	    worst = getCheapestItemCost();
	    deadBest = dead.getMostExpensiveItemCost();
	  }
	}
	// TODO setAttack
	public void setAttack(AttackDirection direction) {
		if(weapon != null) {
  		int wi = weapon.width;
  		int le = weapon.length;
  		int ra= weapon.range;
  		if(!isAttackReady() && getAttackCooldown()<0) {
  		  attackdirection = direction;
  		  if( direction == AttackDirection.UP) {
  				attack = new Rectangle(x, y-ra-le/2-h/2, wi, le);
  			}
        if( direction == AttackDirection.LEFT) {
  				attack = new Rectangle(x-ra-le/2-w/2, y, le, wi);
  //				attack = new Rectangle(x-ra-le/2-w/2, y, le, wi);
  			}
        if( direction == AttackDirection.DOWN) {
  				attack = new Rectangle(x, y+ra+h/2+le/2, wi, le);
  			}
        if( direction == AttackDirection.RIGHT) {
  				attack = new Rectangle(x+ra+w/2+le/2, y, le, wi);
  //				attack = new Rectangle(x+ra+w/2+le/2, y, le, wi);
  			}
  			setAttackReady(true);
  			setDrawDelay((int) (getAttackDelay()*attackDelayMultiplier/2));
  			if (getDrawDelay()<=0) {
  			  setDrawDelay(1);
  			}
  			if(weapon.continuous) {
          setDrawDelay(1);
  			}
  		}
		}
	}
	public int getDamageAfterCritAndArmor(int dmg, Mob target) {
		for(int a=0; a<crits.size(); a++) {
			Crit c = crits.get(a);
			if(c.proc()) {
				return (int) (c.applyMultiplier(dmg)*target.getDamageReduction());
			}
		}
		return (int)(dmg*target.getDamageReduction());
		
	}
	public Hit attack(Rectangle r) {
	  
		r = new Rectangle(r.x-r.width/2, r.y-r.height/2, r.width, r.height); // center the rectangle for some reason
		Hit hit = new Hit();
		
		int dmg = getBaseDamage(); // compute how much damage this mob can do
		
		// Check each mob to see if it intersects with the attack rectangle r
		Iterator<Mob> itmob = myworld.mobs().iterator();
		while(itmob.hasNext()) {
			Mob m = itmob.next();
			if(m != this && m.dim().intersects(r)) {
				hit.leveloftarget = m.level;
				int dmgtodeal = getDamageAfterCritAndArmor(dmg, m);
				if(m.isDead()) {
				  
				} else if(m.damage(dmgtodeal)) {
					if(m.race.name.equals("bigboss")) {
						myworld.addMessage("A Boss has been defeated", 100);
					}
					hit.kill = true;
					m.popups.add(new Popup(dmgtodeal+"", Popup.DURATION));
					hit.damage+=dmgtodeal;
				} else {
					m.popups.add(new Popup(dmgtodeal+"", Popup.DURATION));
					hit.damage+=dmgtodeal;
					
					for( Debuff debuff : weapon.debuffs ) {
					  if( Math.random() >= debuff.chance ) {
					    m.applyDebuff(debuff);
					  }
					}
				}
			}
		}
		Player m = myworld.getPlayer();
		if(m != this && m.dim().intersects(r)) {
			int dmgtodeal = getDamageAfterCritAndArmor(dmg, m);
			m.popups.add(new Popup(dmgtodeal+"", Popup.DURATION));
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
		setAttackReady(false);
		return hit;
	}
	
	/**
	 * 
	 * @return the current amount of health this mob has
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	/**
	 * checks if the ai string contains "hostile"
	 * @return
	 */
	public boolean isHostile() {
		return (ai.contains("hostile"));
	}
	
	/**
	 *  compute this mob's base damage
	 * @return
	 */
	public int getBaseDamage() {
		return Math.max(0, (int) ((basedamage+attributes.get(Attribute.DAMAGE))*damageMultiplier));
	}
	
	/**
	 * compute this mob's agility stat
	 * @return
	 */
	public int getAgility() {
		int agi = (int) ((attributes.get(Attribute.AGILITY)+attributes.get(Attribute.ACTUAL_AGILITY))*agilityMultiplier);
		if(agi>=0) 
			return agi;
		else 
			return 0;
	}
	
	/**
	 * compute this mob's intelligence stat
	 * @return
	 */
	public int getIntelligence() {
		int intel = (int) ((attributes.get(Attribute.INTELLIGENCE)+attributes.get(Attribute.ACTUAL_INTELLIGENCE))*intelligenceMultiplier*5);
		if(intel>=1)
			return intel;
		else
			return 1;
	}
	
	/** 
	 * compute this mob's armor stat
	 * @return
	 */
	public int getArmor() {
		int arm = (int) ((attributes.get(Attribute.ARMOR)+basearmor)*armorMultiplier);
		if(arm>=100) {
			arm = 99;
		}
		return arm;
	}
	
	/**
	 * compute this mob's strength stat
	 * @return
	 */
	public int getStrength() {
		int str = (int) ((attributes.get(Attribute.STRENGTH)+attributes.get(Attribute.ACTUAL_STRENGTH))*strengthMultiplier);
		if(str>=0) 
			return str;
		else
			return 0;
	}
	
	/**
	 * compute the maximum amount of health this mob can have
	 * @return
	 */
	public int getMaximumHealth() {
		return (int) ((getStrength()*STR_HEALTH_MULTIPLIER+attributes.get(Attribute.HEALTH))*maximumHealthMultiplier);
	}
	
	/**
	 * compute the maximum speed that this mob can move at I think not actually sure
	 * @return
	 */
	public int getAccel() {
	  double slowMultiplier = 1.0;
	  if( isSlowed() ) {
	    slowMultiplier = 0.5;
	  }
		return (int) (attributes.get(Attribute.ACCELERATION)*accelerationMultiplier*slowMultiplier);
	}
	
	
	/** 
	 * compute the delay in tics in between this mob's attacks
	 * @return
	 */
	public int getAttackDelay() {
		
		if(weapon.continuous) { // continuous weapons like lasers have no delay between attacks
			return 0;
		}
		else {
		  
			int agility = Math.min(getAgility(), 200); // maximum agility for attack delay calculation is 200
			
			int attackdelay = (int)( ( 1500/(agility+1) ) * attackDelayMultiplier );//(int)(((150-agi/5))*woradelay);
			
			attackdelay = Math.min(attackdelay, 200); // the maximum delay is 200
			return attackdelay;
		}
	}

  public void updateDeadStatus() {
    dead = getCurrentHealth() <= 0;
  }
  
  public boolean isDead() {
    return dead;
  }
  
  public Optional<Rectangle> getAttack() {
    return Optional.ofNullable(attack);
  }
	
  public AttackDirection getAttackDirection() {
    return attackdirection;
  }
  
  public int getXSpeed() {
    return xspeed;
  }
  public int getYSpeed() {
    return yspeed;
  }
  
  public void setXSpeed(int xspeed) {
    this.xspeed = xspeed;
  }
  public void setYSpeed(int yspeed) {
    this.yspeed = yspeed;
  }
  
  public int getDrawDelay() {
    return drawDelay;
  }
  
  private void setDrawDelay(int delay) {
    drawDelay = delay;
  }
  
  private void decremenetDrawDelay() {
    drawDelay--;
  }
  
  private boolean isAttackReady() {
    return attackReady;
  }
  private void setAttackReady(boolean ready) {
    attackReady = ready;
  }
  
  private void setAttackCooldown(int cooldown) {
    attackCooldown = cooldown;
  }
  private void decrementAttackCooldown() {
    attackCooldown--;
  }
  private int getAttackCooldown() {
    return attackCooldown;
  }
  
  public int getWhiteLine() {
    return whiteline;
  }
  
  public void setWhiteLine(int whiteLine) {
    this.whiteline = whiteLine;
  }
  
  public boolean isInShop() {
    return inshop;
  }
  
  public void setInShop(boolean inshop) {
    this.inshop = inshop;
  }
  
  public void clearPopups() {
    popups.clear();
  }
  
  public double getDamageReduction() {
    return (100-getArmor())*0.01;
  }
  
  public void setHealthToMaximum() {
    currentHealth = getMaximumHealth();
  }
  public void setCurrentHealth(int amount) {
    currentHealth = amount;
  }
  
  public void initializemob(String weaponString) {
    basedamage = race.startdmg;
    attributes.put(Attribute.DAMAGE, 0.0);
    damageMultiplier = 1;
    
    level = 1;

    attributes.put(Attribute.HEALTH, (double)race.starthealth);
    maximumHealthMultiplier = 1;
    
    attackDelayMultiplier = 1;
    
    attributes.put(Attribute.ACCELERATION, (double)race.startaccel);
    accelerationMultiplier = 1;
    
    attributes.put(Attribute.AGILITY, 0.0);
    attributes.put(Attribute.ACTUAL_AGILITY, (double)race.startagi);
    agilityMultiplier = 1;
    
    attributes.put(Attribute.STRENGTH, 0.0);
    attributes.put(Attribute.ACTUAL_STRENGTH, (double)race.startstr);
    strengthMultiplier = 1;
    
    attributes.put(Attribute.INTELLIGENCE, 0.0);
    attributes.put(Attribute.ACTUAL_INTELLIGENCE, (double)race.startint);
    intelligenceMultiplier = 1;
    
    attributes.put(Attribute.REGEN, (double)race.startregen);
    regen = 0;
    regenMultiplier = 1;
    
    basearmor = race.startarmor;
    attributes.put(Attribute.ARMOR, 0.0);
    armorMultiplier = 1;
    
    getWeap(weaponString);
    weapon.puton(this);
    setHealthToMaximum();
    
    experience = 0;
    exptolvlup += (int) (Math.pow(level, 2)*10/getIntelligence())+10;
    rescale();
  }
  
	/**
	 * compute this mob's health regeneration stat
	 * @return
	 */
	public double getHealthRegen() {
		return regen*regenMultiplier;
	}
	
	/**
	 * Creates string to save this mob into a file
	 * @return
	 */
	public String tosave() {
		String s = "Mob "+race.name+" "+experience+" "+money+" "+x+" "+y;
		s+=" "+getCurrentHealth()+" "+weapon.name.replace(' ', '_') + " " + ai.replace(' ', '_');
		return s;
	}
	
	/**
	 * Converts the input radians to degrees
	 */
	public static int toDegrees(double rad) {
		return (int) (rad*180/Math.PI);
	}
	
	/**
	 * Converts the input degrees to radians
	 */
	public static double toRadians(double deg) {
		return deg*Math.PI/180;
	}
}
