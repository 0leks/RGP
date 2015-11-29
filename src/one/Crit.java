package one;

import java.util.Random;

public class Crit {
	public int chance;
	public int damage;
	public Crit(int schance, int sdmg) {
		chance = schance;
		damage = sdmg;
	}
	public boolean boom() {
		if((Math.random()*100+1)<=chance) {
			return true;
		} else {
			return false;
		}
	}
	public int getdamage(int dmg) {
		//if(boom()) {
			return (int)(dmg*damage*.01);
		//}
		//return dmg;
	}
	public String toString() {
		return chance+"% chance of "+damage+"% dmg";
	}
}
