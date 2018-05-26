package one;

public class Buff {
	public Attribute stat;
	public int value;
	private boolean multiplier;
	public Buff(Attribute sstat, int svalue, boolean multiplier) {
		stat = sstat;
		value = svalue;
		this.multiplier = multiplier;
	}
	public Buff(Attribute sstat, double svalue, boolean multiplier) {
		stat = sstat;
		value = (int)(svalue*100);
		this.multiplier = multiplier;
	}
	public String toString() {
		if(multiplier)
			return stat+": "+value;
		else
			return stat+": "+value+"%";
	}
	
	public boolean isMultiplier() {
	  return multiplier;
	}
}
