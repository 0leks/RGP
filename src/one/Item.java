package one;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Item {
  public String name;
  public ArrayList<Buff> buffs;
  public ArrayList<Crit> crits;
  public int amount;
  public int cost;
  protected Image image;
  public World myworld;

  public static final String[] ranks = new String[] { "wooden", "iron", "steel", "mithril", "adamant", "rune",
      "dragon" };
  public static final String[] rings = new String[] { "agilityring", "critring", "healthring", "regenring", "statring",
      "intelligencering", "strengthring" };

  public Item(String sname, int samount, World m) {
    name = sname;
    buffs = new ArrayList<Buff>();
    crits = new ArrayList<Crit>();
    amount = samount;
    myworld = m;
    init();

  }

  public static String getRandomRing() {
    return rings[(int) (Math.random() * rings.length)];
  }

  public static String getRandomRankRoulet() {
    double rand = Math.random();
    double chance = 0.5;
    if (rand > chance) {
      return "wooden";
    }
    chance = chance / 2;
    if (rand > chance) {
      return "iron";
    }
    chance = chance / 2;
    if (rand > chance) {
      return "steel";
    }
    chance = chance / 2;
    if (rand > chance) {
      return "mithril";
    }
    chance = chance / 2;
    if (rand > chance) {
      return "adamant";
    }
    chance = chance / 2;
    if (rand > chance) {
      return "rune";
    }
    chance = chance / 2;
    if (rand > chance) {
      return "dragon";
    }
    return "wooden";
  }

  public void init() {
    int rank = 0;
    if (name.contains("wooden")) {
      rank = 1;
    } else if (name.contains("iron")) {
      rank = 2;
    } else if (name.contains("steel")) {
      rank = 3;
    } else if (name.contains("mithril")) {
      rank = 4;
    } else if (name.contains("adamant")) {
      rank = 5;
    } else if (name.contains("rune")) {
      rank = 6;
    } else if (name.contains("dragon")) {
      rank = 7;
    }
    if (name.contains("squareshield")) {
      addbuff(Attribute.ARMOR, 4 + 2 * rank, true);
      addbuff(Attribute.ACCELERATION, -1, true);
      cost = 1 + 3 * rank;
    }

    if (name.equals("regenring")) {
      addbuff(Attribute.REGEN, 50, true);
      cost = 20;
    }
    if (name.equals("greaterregenring")) {
      addbuff(Attribute.REGEN, 300, true);
      cost = 80;
    }
    if (name.equals("critring")) {
      addcrit(20, 2);
      cost = 20;
    }
    if (name.equals("greatercritring")) {
      addcrit(30, 4);
      cost = 80;
    }
    if (name.equals("healthring")) {
      addbuff(Attribute.HEALTH, 150, true);
      cost = 30;
    }
    if (name.equals("greaterhealthring")) {
      addbuff(Attribute.HEALTH, 1000, true);
      cost = 100;
    }
    if (name.equals("strengthring")) {
      addbuff(Attribute.STRENGTH, 50, true);
      cost = 30;
    }
    if (name.equals("greaterstrengthring")) {
      addbuff(Attribute.STRENGTH, 300, true);
      cost = 100;
    }
    if (name.equals("agilityring")) {
      addbuff(Attribute.AGILITY, 15, true);
      cost = 20;
    }
    if (name.equals("greateragilityring")) {
      addbuff(Attribute.AGILITY, 100, true);
      cost = 80;
    }
    if (name.equals("intelligencering")) {
      addbuff(Attribute.INTELLIGENCE, 5, true);
      cost = 10;
    }
    if (name.equals("greaterintelligencering")) {
      addbuff(Attribute.INTELLIGENCE, 50, true);
      cost = 50;
    }
    if (name.equals("statring")) {
      addbuff(Attribute.STRENGTH, 10, true);
      addbuff(Attribute.AGILITY, 4, true);
      addbuff(Attribute.INTELLIGENCE, 1, true);
      addbuff(Attribute.HEALTH, 10, true);
      addbuff(Attribute.REGEN, 5, true);
      cost = 2;
    }
    if (name.equals("greaterstatring")) {
      addbuff(Attribute.STRENGTH, 100, true);
      addbuff(Attribute.AGILITY, 30, true);
      addbuff(Attribute.INTELLIGENCE, 10, true);
      addbuff(Attribute.HEALTH, 300, true);
      addbuff(Attribute.REGEN, 100, true);
      cost = 50;
    }
    if (name.equals("boots")) {
      addbuff(Attribute.ACCELERATION, 2, true);
      cost = 1;
    }
    if (name.equals("elvishboots")) {
      addbuff(Attribute.ACCELERATION, 4, true);
      addbuff(Attribute.AGILITY, 5, true);
      cost = 10;
    }
    ImageIcon ii = new ImageIcon("resources\\images\\items\\" + name + ".png");
    image = ii.getImage();
  }

  public void addbuff(Attribute stat, int val, boolean raw) {
    buffs.add(new Buff(stat, val, raw));
  }

  public void addcrit(double chance, double damageMultiplier) {
    crits.add(new Crit(chance, damageMultiplier));
  }

  public void draw(Graphics2D g, int xp, int yp, int wp, int hp) {
    g.drawImage(image, xp, yp, wp, hp, null);
    g.drawRect(xp, yp, wp, hp);
    g.drawString(amount + "", xp, yp + hp - 5);
    g.drawString(cost + "", xp, yp + 15);
  }

  public void draw(Graphics2D g, int xp, int yp, int wp, int hp, Point mouse, boolean shop) {
    g.setColor(Color.red);
    g.drawImage(image, xp, yp, wp, hp, null);
    g.drawString(amount + "", xp + wp - 17, yp + hp - 5);
    if (shop)
      g.drawString(cost + "", xp, yp + 15);

    int OFFSET = 16;
    // g.drawString("mx:"+m.x+", my:"+m.y+", xp:"+xp+", yp:"+yp+", wp:"+wp+",
    // hp:"+hp, 50, 50);
    if (mouse.x >= xp && mouse.x <= xp + wp && mouse.y >= yp && mouse.y <= yp + hp) {
      int yd = 5;
      int yq = yd + 2 + 2 * OFFSET;
      if (shop) {
        yq += 2 * OFFSET;
      }
      for (Buff b : buffs) {
        yq += OFFSET;
      }
      for (Crit c : crits) {
        yq += OFFSET;
      }
      if (this instanceof Weapon) {
        Weapon w = (Weapon) this;
        if (w.continuous) {
          yq += OFFSET;
        }
        for (Debuff d : w.debuffs) {
          yq += OFFSET;
        }
      }
      int xq = name.getBytes().length * 10;
      for (Crit c : crits) {
        int l = g.getFontMetrics().stringWidth(c.toString()) + 10;
        if (l > xq)
          xq = l;
      }
      if (this instanceof Weapon) {
        Weapon w = (Weapon) this;
        for (Debuff d : w.debuffs) {
          int l = g.getFontMetrics().stringWidth(d.drawString()) + 10;
          if (l > xq)
            xq = l;
        }
      }
      if (xq < 100) {
        xq = 100;
      }
      g.setColor(new Color(250, 200, 200));
      g.fillRect(0, 0, xq, yq - 5);
      g.setColor(Color.black);
      g.drawString(name, 5, yd += OFFSET);
      if (shop) {
        g.drawString("cost: " + cost, 5, yd += OFFSET);
        g.drawString("amount: " + amount, 5, yd += OFFSET);
      }

      for (Buff b : buffs) {
        g.drawString(b.toString(), 5, yd += OFFSET);
      }
      for (Crit c : crits) {
        g.drawString(c.toString(), 5, yd += OFFSET);
      }
      if (this instanceof Weapon) {
        Weapon w = (Weapon) this;
        if (w.continuous) {
          g.drawString("Continuous", 5, yd += OFFSET);
        }
        for (Debuff d : w.debuffs) {
          g.drawString(d.drawString(), 5, yd += OFFSET);
        }
      }

    }
  }
}
