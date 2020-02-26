package one;

import controller.*;

public class Debuff {
  
  public enum DebuffType {
    STUN, POISON, SLOW
  }
  
  public DebuffType type;
  public int duration;
  public int damage;
  // chance of buff applied on hit is (1-chance)%
  public double chance;
  
  public Debuff( DebuffType type, int duration) {
    this.type = type;
    this.duration = duration;
    this.chance = 0;
  }
  public Debuff( DebuffType type, int duration, double chance ) {
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
    if( type == DebuffType.STUN ) {
      s+= (100-(int)(chance*100)) + "% chance of ";
      s+= toSeconds(duration) + "s stun";
    }
    else if( type == DebuffType.POISON ) {
      s+= damage + " poison dmg for ";
      s+= toSeconds(duration) + "s";
    }
    else if( type == DebuffType.SLOW ) {
      s+= (100-(int)(chance*100)) + "% chance of ";
      s+= toSeconds(duration) + "s slow";
    }
    return s;
  }
  public String toString() {
    String s = "";
    if( type == DebuffType.STUN ) {
      s += "Stun:";
      s+= "t(" + duration + ")";
      s+= "%(" + (100-(int)(chance*100)) + ")";
    }
    else if( type == DebuffType.POISON ) {
      s += "Poison:";
      s+= "t(" + duration + ")";
      s+= "d(" + damage + ")";
    }
    else if( type == DebuffType.SLOW ) {
      s += "Slow:";
      s+= "t(" + duration + ")";
      s+= "%(" + (100-(int)(chance*100)) + ")";
    }
    return s;
  }
  public double toSeconds( int tics ) {
    return GameController.TIMER_DELAY*tics*.001;
  }
}
