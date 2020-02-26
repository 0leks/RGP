package one;

import java.awt.Color;
import java.awt.Point;
import java.util.*;

public class Projectile extends Thing {
  
  private Thing source;
  private Point target;
  private Point start;
  private float distance;
  private float travelled;
  private float speed;
  
  private int damage;
  private ArrayList<Debuff> debuffs;
  
  private Color color;
  
  public Projectile(int sx, int sy, int sw, int sh, World smyworld, Thing source, Point target, float speed, int damage, ArrayList<Debuff> debuffs) {
    super(sx, sy, sw, sh, smyworld);
    this.start = new Point(sx, sy);
    this.source = source;
    this.target = target;
    int deltax = target.x - start.x;
    int deltay = target.y - start.y;
    distance = (float) Math.sqrt(deltax*deltax + deltay*deltay);
    this.speed = speed;
    this.damage = damage;
    this.debuffs = debuffs;
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
    Optional<Thing> collidedWithOpt = myworld.collides(this.dim());
    collidedWithOpt.ifPresent(collidedWith -> {
      if( collidedWith instanceof Mob ) {
        Mob mob = (Mob)collidedWith;
        int damageToTake = (int) (damage*mob.getDamageReduction());
        mob.popups.add(new Popup(damageToTake+"", Popup.DURATION));
        mob.damage(damageToTake);
        for( Debuff debuff : debuffs ) {
          if( Math.random() >= debuff.chance ) {
            mob.applyDebuff(debuff);
          }
        }
        myworld.removeProjectile(this);
      }
    });
    if( collidedWithOpt.isPresent() ) {
      
    }
    return false;
  }

  public String tosave() {
    // TODO SAVING AND LOADING PROJECTILES
    return "Projectile "+x+" "+y+" "+w+" "+h+" "+color.getRed()+" "+color.getGreen()+" "+color.getBlue();
  }
  
}
