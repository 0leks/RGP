package one;

public class Buff {
	public String stat;
	public int value;
	public boolean raw;
	public Buff(String sstat, int svalue, boolean sraw) {
		stat = sstat;
		value = svalue;
		raw = sraw;
	}
	public Buff(String sstat, double svalue, boolean sraw) {
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
