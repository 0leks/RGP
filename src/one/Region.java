package one;

public class Region extends Thing {
	private Sound sound;
	public Region(int sx, int sy, int sw, int sh, World smyworld, Sound ssound) {
		super(sx, sy, sw, sh, smyworld);
		sound = ssound;
	}
	
	
	
}
