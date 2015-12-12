package one;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public class Thing implements Serializable{
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
//	public void draw(Graphics g, Color[] c) {
//		int drawx = (x-myworld.p.x)/World.ZOOM+470;
//		int drawy = (y-myworld.p.y)/World.ZOOM+310;
//		if(drawx+w/2>0 && drawx-w/2<990 && drawy+h/2>0 && drawy-h/2<670) {
//			g.setColor(c[0]);
//			int w = this.w/World.ZOOM;
//			int h = this.h/World.ZOOM;
//			g.fillRect(drawx-w/2, drawy-h/2, w, h);
//			int distx = 0;
//			int disty = 0;
//			if(myworld.draw3d) {
//				distx = (drawx-470)/10;
//				disty = (drawy-310)/10;
//				poly.clear();
//				MyPolygon p;
//
//				MyPolygon bottom = new MyPolygon(c[1]);
//				bottom.addPoint(drawx-w/2+distx+w, drawy-h/2+disty+h);
//				bottom.addPoint(drawx-w/2+w, drawy-h/2+h);
//				bottom.addPoint(drawx-w/2, drawy-h/2+h);
//				bottom.addPoint(drawx-w/2+distx, drawy-h/2+disty+h);
//
//				MyPolygon right = new MyPolygon(c[2]);
//				right.addPoint(drawx-w/2+distx+w, drawy-h/2+disty+h);
//				right.addPoint(drawx-w/2+w, drawy-h/2+h);
//				right.addPoint(drawx-w/2+w, drawy-h/2);
//				right.addPoint(drawx-w/2+distx+w, drawy-h/2+disty);
//
//				MyPolygon left = new MyPolygon(c[3]);
//				left.addPoint(drawx-w/2+distx, drawy-h/2+disty);
//				left.addPoint(drawx-w/2, drawy-h/2);
//				left.addPoint(drawx-w/2, drawy-h/2+h);
//				left.addPoint(drawx-w/2+distx, drawy-h/2+disty+h);
//				
//				MyPolygon top = new MyPolygon(c[4]);
//				top.addPoint(drawx-w/2+distx, drawy-h/2+disty);
//				top.addPoint(drawx-w/2, drawy-h/2);
//				top.addPoint(drawx-w/2+w, drawy-h/2);
//				top.addPoint(drawx-w/2+distx+w, drawy-h/2+disty);
//				
//				if(distx>0) {
//					if(disty>0) {
//						poly.add(right);
//						poly.add(bottom);
//						poly.add(top);
//						poly.add(left);
//					} else {
//						poly.add(top);
//						poly.add(right);
//						poly.add(left);
//						poly.add(bottom);
//					}
//				} else {
//					if(disty>0) {
//						poly.add(left);
//						poly.add(bottom);
//						poly.add(top);
//						poly.add(right);
//					} else {
//						poly.add(top);
//						poly.add(left);
//						poly.add(right);
//						poly.add(bottom);
//					}
//				}
//				
//				for(int a=0; a<poly.size(); a++) {
//					MyPolygon po = poly.get(a);
//					g.setColor(po.color);
//					g.fillPolygon(po);
//				}
//				g.setColor(c[0]);
//				g.fillRect(drawx-w/2+distx, drawy-h/2+disty, w, h);
//			}
//		}
//	}
	
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
