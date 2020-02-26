package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controller.*;
import one.*;
import resources.*;
import sound.*;

public class Frame extends JFrame implements MenuListener {

  private static final long serialVersionUID = 1L;
  public static boolean debugmode = true;
  private GamePanel gamePanel;
  private MainMenuPanel mainMenu;
  private NewGameMenuPanel newGameMenu;
  private ContinueGameMenuPanel loadGameMenu;
  private JPanel currentPanel;
  
  private GameControllerInterface gameController;

  public Frame(GameControllerInterface gameController, SoundManager soundManager) {
    Frame.println("Initializing Frame");
    this.gameController = gameController;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("RGP 2.0");
    setResizable(false);
    this.setUndecorated(true);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    mainMenu = new MainMenuPanel();
    newGameMenu = new NewGameMenuPanel();
    loadGameMenu = new ContinueGameMenuPanel();
    gamePanel = new GamePanel();
    mainMenu.setMenuListener(this);
    newGameMenu.setMenuListener(this);
    loadGameMenu.setMenuListener(this);
    switchTo(mainMenu);
    setVisible(true);

    soundManager.playMenuMusic();
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
      gamePanel.addGamePanel(gameController.startGame(obj));
      switchTo(gamePanel);
      gamePanel.requestFocusInWindow();
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