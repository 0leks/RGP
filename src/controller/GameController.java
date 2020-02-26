package controller;

import java.awt.event.*;

import javax.swing.*;

import game.*;
import gui.*;
import one.*;
import resources.*;
import sound.*;

public class GameController implements GameControllerInterface {

  public static final int TIMER_DELAY = 60;
  public static final int REPAINT_DELAY = 20;

  private Timer timer;
  private World world;
  private Panel panel;
  private Frame frame;
  
  private Thread gameThread;
  private Thread repaintThread;
  
  private SoundManager soundManager;
  
  public GameController() {
    soundManager = new SoundManager();
    soundManager.loadResources();
    frame = new Frame(this, soundManager);
  }
  
  @Override
  public Panel startGame(Object obj) {
    String clas = "Human";
    if( obj instanceof String ) {
      clas = (String)obj;
    }
    Race race = Race.parse(clas);
    panel = new Panel(race, soundManager);
    if( obj instanceof SaveInstance ) {
      panel.loadSave(((SaveInstance)obj).getFileNameNoExtension());
    }


    Frame.println("Creating Game Thread with delay: " + TIMER_DELAY);
    gameThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          long timeBuffer = 0;
          while(true) {
            long startTime = System.currentTimeMillis();
            panel.gameTic();
            long deltaTime = System.currentTimeMillis() - startTime;
            timeBuffer -= deltaTime;
            timeBuffer += TIMER_DELAY;
            if( timeBuffer > 0 ) {
              Thread.sleep(timeBuffer);
              timeBuffer = 0;
            }
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    Frame.println("Creating Repaint Thread with delay: " + REPAINT_DELAY);
    repaintThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while(true) {
            panel.repaint();
            Thread.sleep(REPAINT_DELAY);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    Frame.println("Starting Game Thread");
    gameThread.start();
    Frame.println("Starting Repaint Thread");
    repaintThread.start();
    return panel;
  }
  
}
