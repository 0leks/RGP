package one;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public abstract class ProjectileRegion extends Thing{

  private List<Projectile> projectiles;
  private int maxNumProjectiles;
  
  public ProjectileRegion(int sx, int sy, int sw, int sh, World smyworld, int maxNumProjectiles ) {
    super(sx, sy, sw, sh, smyworld);
    projectiles = new LinkedList<Projectile>();
    this.maxNumProjectiles = maxNumProjectiles;
  }
  
  public final void move() {
    double chance = Math.max(2*(1.0*maxNumProjectiles - projectiles.size())/maxNumProjectiles, 0.5);
    int attempts = Math.max(projectiles.size()/30, 1);
//    System.err.println("att=" + attempts + " #="+ projectiles.size());
    for( int i = 0; i < attempts && projectiles.size() < maxNumProjectiles; i++ ) {
      if( Math.random() < chance ) {
        Point loc = getNewSpawnLocation();
        Point target = getNewTargetLocation();
  
        int c = (int) (Math.random()*4);
        Color co ;
        if(c == 0) 
          co = new Color(185 + (int) (Math.random()*20), 185 + (int) (Math.random()*20), 210 + (int) (Math.random()*20));
        else if(c==1)
          co = new Color(185 + (int) (Math.random()*20), 180 + (int) (Math.random()*20), 200 + (int) (Math.random()*20));
        else if(c == 2)
          co = new Color(185 + (int) (Math.random()*20), 195 + (int) (Math.random()*20), 210 + (int) (Math.random()*20));
        else
          co= new Color(195 + (int) (Math.random()*20), 195 + (int) (Math.random()*20), 220 + (int) (Math.random()*20));
        
        Projectile p = new Projectile(loc.x, loc.y, 10, 10, myworld, this, target, getSpeed());
        p.setColor(co);
        projectiles.add(p);
        myworld.addProjectile(p);
        chance = (1.0*maxNumProjectiles - projectiles.size())/maxNumProjectiles;
      }
    }
  }
  public void removeProjectile(Projectile p) {
    if( projectiles.contains(p) ) {
      projectiles.remove(p);
    }
  }
  public abstract Point getNewSpawnLocation();
  public abstract Point getNewTargetLocation();
  public abstract float getSpeed();

}
