package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import one.Panel;
import one.Sound;
import one.World;

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
    mainMenu.setMenuListener(this);
    newGameMenu.setMenuListener(this);
    this.add(mainMenu, BorderLayout.CENTER);
    setVisible(true);

    menu = World.initSound("mainmenu.wav", -10, true);
    if(World.playmusic)
      menu.play(-5);
  }

  @Override
  public void menuAction(int action, Object obj) {
    switch (action) {
    case MenuListener.EXIT:
      System.exit(0);
      break;
    case MenuListener.NEW_GAME:
      remove(mainMenu);
      add(newGameMenu, BorderLayout.CENTER);
      validate();
      repaint();
      break;
    case MenuListener.BACK:
      remove(newGameMenu);
      add(mainMenu, BorderLayout.CENTER);
      validate();
      repaint();
      break;
    case MenuListener.START:
      game = new Panel((String)obj);
      if(World.playmusic ) {
        if( menu!=null) {
          menu.fadeOut(.1);
        }
      }
      remove(newGameMenu);
      add(game, BorderLayout.CENTER);
      validate();
      repaint();
      game.requestFocusInWindow();
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