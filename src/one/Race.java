package one;

import java.io.Serializable;

public enum Race  {
  ELF("Elf", 4, 7, .1, 
      25, 30, 12, 6, 
      20, 8, .01, 
      36, 36, 3),
  HUMAN("Human", 3, 9, .2, 
      20, 40, 11, 7, 
      25, 6, .05, 
      38, 38, 4),
  
  DWARF("Dwarf", 2, 11, .3, 
      15, 50, 10, 9, 
      30, 5, .1, 
      40, 40, 5),
  
  WARRIOR("Warrior", 3, 9, .5, 
      25, 50, 9, 10, 
      30, 6, .09, 
      39, 39, 10),
  
  PATROL("Patrol", 3, 9, .5, 
      25, 50, 9, 10, 
      30, 15, .09, 
      39, 39, 10),
  
  SCHOLAR("Scholar", 3, 8, .1,
      18, 38, 30, 6, 
      24, 6, .04, 
      37, 37, 5),
  
  ASSASSIN("Assassin", 1, 5, 1.5, 
      30, 30, 10, 10, 
      20, 8, .09, 
      35, 35, 2),
    
  SUPER_NINJA("Super_Ninja", 12, 18, 3,
      10, 30, 25, 5,
      20, 10, .05,
      38, 38, 4),
  
  GOAT("Goat", 2, 3, 1, 
      100, 10, 1, 15, 
      10, 10, 1, 
      25, 25, 3),
  
  SNITCH("Snitch", 1, 1, 1, 
      1, 1, 1, 10, 
      1, 25, 0, 
      1, 10, 3),
  
  LICH("Lich", 10, 10, 10, 
      50, 50, 10, 10, 
      100, 0, 0.3, 
      60, 60, 40),
  
  BIGBOSS("bigboss", 5, 13, 2, 
      50, 100, 16, 10, 
      100, 0, .5, 
      180, 180, 25),
  
  FATDUMMY("fatdummy", 0, 11, 0, 
      1, 150, 1, 1, 
      150, 4, 1, 
      60, 60, 10),
  
  SMALLDUMMY("smalldummy", 0, 21, 0, 
      100, 15, 1, 1, 
      1, 8, 0, 
      40, 40, 1),
  
  BAAL("Baal", 0, 0, 0, 
      999999, 999999, 0, 999999, 
      999999, 50, 999999, 
      50, 50, 99);
  
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
	
	private Race(String sname,int sagiinc, int sstrinc, double sdmginc, int sagi, int sstr, int sint, int sdmg, int shp, int saccel, double sreg, int swidth, int sheight, int sarmor) {
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
	
	public static Race parse(String string) {
	  for( Race r : Race.values() ) {
	    if( r.name.equals(string) ) {
	      return r;
	    }
	  }
	  return null;
	}
}
