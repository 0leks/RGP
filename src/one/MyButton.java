package one;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MyButton {
	public String type;
	private boolean on;
	private int x, y, w, h;
	private String name;
	private Font font;
	private Color color;
	public MyButton(String stype, int sx, int sy, int sw, int sh, String sname, Color scolor) {
		type = stype;
		x = sx;
		y = sy;
		w = sw;
		h = sh;
		name = sname;
		font = new Font("Helvetica", Font.BOLD, h*4/5);
		color = scolor;
	}
	public int getWidth() {
		return w;
	}
	public int getX() {
		return x;
	}
	public void press() {
		on = !on;
	}
	public void draw(Graphics2D g) {
		Color cur = g.getColor();
		g.setColor(color);
		if(!on)
			g.drawRect(x, y, w, h);
		else
			g.fillRect(x, y, w, h);

		Font temp = g.getFont();
		g.setFont(font);
		if(!on) {
			g.drawString(name, x+2, y+h*4/5-1);
		} else {
			Color inv = new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
			g.setColor(inv);
			g.drawString(name, x+2, y+h*4/5-1);
		}
		g.setFont(temp);
		g.setColor(cur);
	}
	public boolean press(int ax, int ay) {
		if(ax>=x && ax<=x+w && ay>=y && ay<=y+h) {
			if(type.equals("but")) {
				on = true;
				return true;
			}
			else if(type.equals("switch")) {
				//on = !on;
				return true;
			}
		}
		return false;
	}
	public boolean ison(int ax, int ay) {
		if(ax>=x && ax<=x+w && ay>=y && ay<=y+h) {
			return true;
		} else {
			return false;
		}
	}
	public boolean release(int ax, int ay) {
		if(type.equals("but")) {
			on = false;
		}
		if(ax>=x && ax<=x+w && ay>=y && ay<=y+h) {
			if(type.equals("switch")) {
				on = !on;
			}
			return true;
		}
		
		return false; 
		
	}
	public boolean check() {
		return on;
	}
	public String name() {
		return name;
	}
	public boolean is(String thi) {
		if(name.equals(thi))
			return true;
		return false;
	}
}
