package one;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Thing {
	World myworld;
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Image image;
	protected ArrayList<MyPolygon> poly;
	
	public Thing(int sx, int sy, int sw, int sh, World smyworld) {
		poly = new ArrayList<MyPolygon>();
		x=sx;
		y=sy;
		w=sw;
		h=sh;
		myworld = smyworld;
	}
	
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
	public int w() {
		return w;
	}
	public int h() {
		return h;
	}
	public Rectangle dim() {
		return new Rectangle(x-w/2, y-h/2, w, h);
	}
	public boolean setPos(int sx, int sy, int sw, int sh) {
		if(sw>=0 && sh>=0) {
			x = sx;
			y = sy;
			w = sw;
			h = sh;
			return true;
		} else {
			return false;
		}
	}
	public void setPos(int sx, int sy) {
		x = sx;
		y = sy;
	}
	public boolean setSize(int sw, int sh) {
		if(sw>=0 && sh>=0) {
			w = sw;
			h = sh;
			return true;
		} else {
			return false;
		}
	}
	public String toString() {
		return x+" "+y+" "+w+" "+h;
	}
}
