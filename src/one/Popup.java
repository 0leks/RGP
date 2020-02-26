package one;

import java.awt.Color;
import java.util.Random;

public class Popup extends Message{
  
  public static final int DURATION = 80;
  
	private static int dist = 40;
	protected double dx;
	protected double dy;
	protected double x;
	protected double y;
	protected Random randy;
	protected int starttime;
	protected Color color;
	public Popup(String string, int time) {
		super(string, time);
		randy = new Random();
		dx = randy.nextDouble()*3-1;
		dy = randy.nextDouble()*3-1;
		x = 0;
		y = 0;
		starttime = time;
		drawleft = 0;
		color = Color.red;
	}
	public Popup(String string, int time, Color c) {
    super(string, time);
    randy = new Random();
    dx = randy.nextDouble()*3-1;
    dy = randy.nextDouble()*3-1;
    x = 0;
    y = 0;
    starttime = time;
    drawleft = 0;
    color = c;
  }
	public int x() {
		return (int)x;
	}
	public int y() {
		return (int)y;
	}
	/**
	 * Called when popup is drawn
	 * Shifts its position over
	 * @return true if duration expired, false if still should be drawn
	 */
	public boolean drawn() {
		drawleft++;
		if(drawleft>starttime) {
			return true;
		}
		x = dist*dx*drawleft/starttime;
		y = dist*dy*drawleft/starttime;
		return false;
	}
	public boolean done() {
	  return drawleft>starttime;
	}
}
