package one;

public class Debuff {
  
  // total types of debuffs
  public static final int TOTAL = 2;
  public static final int STUN = 0;
  public static final int POISON = 1;
  
  public int type;
  public int duration;
  public int damage;
  // chance of buff applied on hit is (1-chance)%
  public double chance;
  
  public Debuff( int type, int duration) {
    this.type = type;
    this.duration = duration;
    this.chance = 0;
  }
  public Debuff( int type, int duration, double chance ) {
    this.type = type;
    this.duration = duration;
    this.chance = chance;
  }
  public Debuff( Debuff d ) {
    this.type = d.type;
    this.duration = d.duration;
    this.chance = d.chance;
    this.damage = d.damage;
  }
  public String drawString() {
    String s = "";
    if( type == STUN ) {
      s+= (100-(int)(chance*100)) + "% chance of ";
      s+= toSeconds(duration) + "s stun";
    }
    else if( type == POISON ) {
      s+= damage + " poison dmg for ";
      s+= toSeconds(duration) + "s";
    }
    return s;
  }
  public String toString() {
    String s = "";
    if( type == STUN ) {
      s += "Stun:";
      s+= "t(" + duration + ")";
      s+= "%(" + (100-(int)(chance*100)) + ")";
    }
    else if( type == POISON ) {
      s += "Poison:";
      s+= "t(" + duration + ")";
      s+= "d(" + damage + ")";
    }
    return s;
  }
  public double toSeconds( int tics ) {
    return Panel.TIMER_DELAY*Panel.ACTCD*tics*.001;
  }
}
