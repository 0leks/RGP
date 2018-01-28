package one;

import java.awt.Color;
import java.awt.Point;

public class Projectile extends Thing {
  
  private Thing source;
  private Point target;
  private Point start;
  private float distance;
  private float travelled;
  private float speed;
  
  private Color color;
  
  public Projectile(int sx, int sy, int sw, int sh, World smyworld, Thing source, Point target, float speed) {
    super(sx, sy, sw, sh, smyworld);
    this.start = new Point(sx, sy);
    this.source = source;
    this.target = target;
    int deltax = target.x - start.x;
    int deltay = target.y - start.y;
    distance = (float) Math.sqrt(deltax*deltax + deltay*deltay);
    this.speed = speed;
//    System.err.println(sx + ", " + sy + ", " + target.x + ", " + target.y);
  }
  
  public Color getColor() {
    return color;
  }
  public void setColor(Color color) {
    this.color = color;
  }
  /**
   * 
   * @return true if arrived at target, false otherwise
   */
  public boolean move() {
    travelled += speed;
    float ratio = travelled / distance;
    int newx = (int) (start.x + (target.x - start.x) * ratio);
    int newy = (int) (start.y + (target.y - start.y) * ratio);
    x = newx;
    y = newy;
//    System.err.println(travelled + ", " + distance);
    if( travelled > distance ) {
      if( source instanceof ProjectileRegion ) {
        ((ProjectileRegion) source).removeProjectile(this);
      }
      myworld.removeProjectile(this);
      return true;
    }
    return false;
  }
  
  
}
