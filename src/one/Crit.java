package one;

public class Crit {
	private double chance;
	private double damageMultiplier;
	public Crit(double chance, double damageMultiplier) {
		this.chance = chance;
		this.damageMultiplier = damageMultiplier;
	}
	public boolean proc() {
		if( Math.random()*100 < chance ) {
			return true;
		} else {
			return false;
		}
	}
	public int applyMultiplier(int dmg) {
			return (int)(dmg*damageMultiplier);
	}
	public String toString() {
		return chance+"% chance of "+(damageMultiplier*100)+"% dmg";
	}
	
	public boolean isBetterThan(Crit other) {
	  return this.damageMultiplier > other.damageMultiplier;
	}
}
