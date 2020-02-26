package resources;

import java.awt.*;

public class ColorUtil {
  public static Color darken(Color c, int mag) {
    int randomModifier = 2 - (int)(Math.random()*5);
    int r = c.getRed() - mag + randomModifier;
    int g = c.getGreen() - mag + randomModifier;
    int b = c.getBlue() - mag + randomModifier;
    if(r<0)
      r=0;
    if(g<0)
      g=0;
    if(b<0)
      b=0;
    if(r>250)
      r=250;
    if(g>250)
      g=250;
    if(b>250)
      b=250;
    return new Color(r, g, b);
  }
  public static Color getColor(int r, int g, int b) {
    if(r<0)
      r=0;
    if(g<0)
      g=0;
    if(b<0)
      b=0;
    if(r>255)
      r=255;
    if(g>255)
      g=255;
    if(b>255)
      b=255;
    return new Color(r, g, b);
  }
}
