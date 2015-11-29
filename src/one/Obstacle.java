package one;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

public class Obstacle extends Thing {
	protected Color color;
	private boolean blockplayer;
	public Obstacle(int sx, int sy, int sw, int sh, World smyworld, Color scolor, boolean blockplayers) {
		super(sx, sy, sw, sh, smyworld);
		color = scolor;
		blockplayer = blockplayers;
	}
	public boolean blockPlayer() {
		return blockplayer;
	}
//	@Override
//	public void draw(Graphics g) {
//		if(blockplayer) {
//			Color[] colors = new Color[5];
//			colors[0] = color;
//			for(int a=1; a<colors.length && a<3; a++) {
//				colors[a] = World.darken(color, -100);
//			}
//			for(int a=3; a<colors.length; a++) {
//				colors[a] = World.darken(color, 100);
//			}
//			super.draw(g, colors);
//		} else {
//			int drawx = (x-myworld.p.x)/World.ZOOM+470;
//			int drawy = (y-myworld.p.y)/World.ZOOM+310;
//			g.drawRect(drawx-w()/2/World.ZOOM, drawy-h()/2/World.ZOOM, w()/World.ZOOM, h()/World.ZOOM);
//		}
//	}
	public String tosave() {
		int block = 0;
		if(blockplayer)
			block = 1;
		return "Wall "+x+" "+y+" "+w+" "+h+" "+color.getRed()+" "+color.getGreen()+" "+color.getBlue()+" "+block;
	}
}
