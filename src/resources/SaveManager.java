package resources;

import java.io.*;
import java.util.*;

public class SaveManager {

  
  public static List<SaveInstance> getSaves() {
    List<SaveInstance> saves = new LinkedList<SaveInstance>();
    File saveFolder = new File("saves");
    if( saveFolder.exists() ) {
      File[] files = saveFolder.listFiles();
      for( File file : files ) {
        SaveInstance save = new SaveInstance(file);
        saves.add(save);
      }
    }
    return saves;
  }
}
