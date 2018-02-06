package gui;

public interface MenuListener {
  public static final int NEW_GAME = 0;
  public static final int EXIT = 1;
  public static final int BACK = 2;
  public static final int START = 3;
  public static final int CONTINUE = 4;
  
  public void menuAction(int action, Object obj);
}
