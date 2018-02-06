package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class SaveInstance {

  private File file;
  
  private boolean infoLoaded = false;
  private int exp;
  private int money;
  private String race;
  
  public SaveInstance(File file) {
    this.file = file;
  }
  public String getFileNameNoExtension() {
    return file.getName().substring(0, file.getName().length()-4);
  }
  public String getFileName() {
    return file.getName();
  }
  public int getXP() {
    return exp;
  }
  public int getMoney() {
    return money;
  }
  public String getRace() {
    return race;
  }
  
  public void loadInfo() {
    if( !infoLoaded ) {
      BufferedReader buff = null;
      try {
        buff = new BufferedReader(new FileReader(file));
        buff.readLine();
        String line = buff.readLine();
        StringTokenizer st = new StringTokenizer(line);
        String name = st.nextToken();
        if(name.equals("Player")) {
          int xx = 0, yy = 0, money = 0, health = 0;
          int exp = 0;
          String race = "", weap = "";
          String[] temp = line.split("[,]");
          String stats = temp[0];
          st = new StringTokenizer(stats);
          st.nextToken();
          race = st.nextToken();
          exp = Integer.parseInt(st.nextToken());
          money = Integer.parseInt(st.nextToken());
          xx = Integer.parseInt(st.nextToken());
          yy = Integer.parseInt(st.nextToken());
          this.exp = exp;
          this.money = money;
          this.race = race;
          infoLoaded = true;
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      finally {
        if( buff != null ) {
          try {
            buff.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
