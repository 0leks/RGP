package one;

import java.io.Serializable;

public class Race  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5691151138933874107L;
	public int agiinc;
	public int strinc;
	public double dmginc;
	
	public String name;
	public int startagi;
	public int startstr;
	public int startint;
	public int startdmg;
	public int starthealth;
	public int startaccel;
	public double startregen;
	public int startwidth;
	public int startheight;
	public int startarmor;
	
	public Race(String sname,int sagiinc, int sstrinc, double sdmginc, int sagi, int sstr, int sint, int sdmg, int shp, int saccel, double sreg, int swidth, int sheight, int sarmor) {
		agiinc = sagiinc;
		strinc = sstrinc;
		dmginc = sdmginc;
		name = sname;
		startagi = sagi;
		startstr = sstr;
		startint = sint;
		startdmg = sdmg;
		starthealth = shp;
		startaccel = saccel;
		startregen = sreg;
		startwidth = swidth;
		startheight = sheight;
		startarmor = sarmor;
	}
}
