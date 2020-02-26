package one;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;
import java.net.*;

import javax.swing.ImageIcon;

public class Sign extends Obstacle {
	public Image skin;
	public String skinloc;
	public String message;
	Font font;
	public int sx, sy;
	public Sign(int x, int y, int w, int h, World wo, String skin, String mess) {
		super(x, y, w, h, wo, Color.white, true);
		if(skin.contains("left")) {
			sx=w/4;
		}
		if(skin.contains("up")) {
			sy=h/8;
		}
		skinloc = skin;
    URL imageResource = Weapon.class.getClassLoader().getResource("resources/images/signs/" + skin + ".png");
    if(imageResource != null) {
      ImageIcon ii = new ImageIcon(imageResource);
      this.skin = ii.getImage();
    }
		message = mess;
		if(skin.contains("up") || skin.contains("down")) {
			font = new Font("Helvetica", Font.BOLD, h*9/16);
		} else {
			font = new Font("Helvetica", Font.BOLD, h*12/16);
		}
		
	}
//	public void draw(Graphics g) {
//		Color[] colors = new Color[5];
//		colors[0] = color;
//		for(int a=1; a<colors.length; a++) {
//			colors[a] = World.darken(color, ((a+1)/2)*50);
//		}
//		super.draw(g, colors);
//		int distx = 0;
//		int disty = 0;
//		int drawx = (x-myworld.p.x)/World.ZOOM+470;
//		int drawy = (y-myworld.p.y)/World.ZOOM+310;
//		int w = super.w/World.ZOOM;
//		int h = super.h/World.ZOOM;
//		if(drawx+w/2>-50 && drawx-w/2<990 && drawy+h/2>-50 && drawy-h/2<670) {
//			Font cur = g.getFont();
//			g.setFont(font);
//			g.setColor(Color.black);
//			if(myworld.draw3d) {
//				distx = (drawx-470)/10;
//				disty = (drawy-310)/10;
//			}
//			int mx = 0;
//			g.drawImage(skin, drawx-w/2+distx, drawy-h/2+disty, w, h, null);
//			g.drawString(message, drawx-w/2+1+sx+distx, drawy+h/4+sy+disty);
//			g.setFont(cur);
//		
//		}
//	}
	public String tosave() {
		return "Sign "+skinloc+" "+message+" "+super.tosave();
	}
}
