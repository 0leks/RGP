package one;

public class Buff {
	public Attribute stat;
	public int value;
	public boolean raw;
	public Buff(Attribute sstat, int svalue, boolean sraw) {
		stat = sstat;
		value = svalue;
		raw = sraw;
	}
	public Buff(Attribute sstat, double svalue, boolean sraw) {
		stat = sstat;
		value = (int)(svalue*100);
		raw = sraw;
	}
	public String toString() {
		if(raw)
			return stat+": "+value;
		else
			return stat+": "+value+"%";
	}
}
