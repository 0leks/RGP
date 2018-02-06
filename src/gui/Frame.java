package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import one.Panel;
import one.Sound;
import one.World;
import resources.*;

public class Frame extends JFrame implements MenuListener {

  private static final long serialVersionUID = 1L;
  public static int DIMX;
  public static int DIMY;
  public static int MIDX;
  public static int MIDY;
  public static final int GUIWIDTH = 300;
  public static final int GUIHEIGHT = 30;
  public static boolean debugmode = true;
  private Panel game;
  private MainMenuPanel mainMenu;
  private NewGameMenuPanel newGameMenu;
  private ContinueGameMenuPanel loadGameMenu;
  private JPanel currentPanel;
  private Sound menu;

  public Frame() {
    Frame.println("Initializing Frame");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("RGP 1.4");
    setResizable(false);
    this.setUndecorated(true);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    DIMX = this.getWidth();
    DIMY = this.getHeight();
    MIDX = (DIMX - GUIWIDTH) / 2;
    MIDY = (DIMY - GUIHEIGHT) / 2;
    mainMenu = new MainMenuPanel();
    newGameMenu = new NewGameMenuPanel();
    loadGameMenu = new ContinueGameMenuPanel();
    mainMenu.setMenuListener(this);
    newGameMenu.setMenuListener(this);
    loadGameMenu.setMenuListener(this);
    switchTo(mainMenu);
    setVisible(true);

    menu = World.initSound("mainmenu.wav", -10, true);
    if(World.playmusic)
      menu.play(-5);
  }
  private void removeCurrent() {
    if( currentPanel != null ) {
      remove(currentPanel);
      currentPanel = null;
    }
  }
  private void switchTo(JPanel panel) {
    removeCurrent();
    add(panel, BorderLayout.CENTER);
    currentPanel = panel;
    validate();
    repaint();
  }
  @Override
  public void menuAction(int action, Object obj) {
    switch (action) {
    case MenuListener.EXIT:
      System.exit(0);
      break;
    case MenuListener.NEW_GAME:
      switchTo(newGameMenu);
      break;
    case MenuListener.BACK:
      switchTo(mainMenu);
      break;
    case MenuListener.START:
      String clas = "Human";
      if( obj instanceof String ) {
        clas = (String)obj;
      }
      game = new Panel(clas);
      if( obj instanceof SaveInstance ) {
        game.loadSave(((SaveInstance)obj).getFileNameNoExtension());
      }
      if(World.playmusic ) {
        if( menu!=null) {
          menu.fadeOut(.1);
        }
      }
      switchTo(game);
      game.requestFocusInWindow();
      break;

    case MenuListener.CONTINUE:
      switchTo(loadGameMenu);
      break;
    }
  }

  public static void print(String debug) {
    if (debugmode) {
      System.err.print(debug);
    }
  }

  public static void println(String debug) {
    if (debugmode) {
      System.err.println(debug);
    }
  }

  public static void println() {
    if (debugmode) {
      System.err.println();
    }
  }

  public void exit() {
    System.exit(0);
  }
}