package controller;

import java.awt.event.*;

import javax.swing.*;

import gui.*;
import one.*;
import resources.*;

public class GameController implements GameControllerInterface {

  public static final int TIMER_DELAY = 60;
  public static final int REPAINT_DELAY = 20;

  private Timer timer;
  private World world;
  private Panel panel;
  private Frame frame;
  
  private Thread gameThread;
  private Thread repaintThread;
  
  public GameController() {
    frame = new Frame(this);
    
  }
  
  @Override
  public Panel startGame(Object obj) {
    String clas = "Human";
    if( obj instanceof String ) {
      clas = (String)obj;
    }
    panel = new Panel(clas);
    if( obj instanceof SaveInstance ) {
      panel.loadSave(((SaveInstance)obj).getFileNameNoExtension());
    }


    Frame.println("Creating Game Thread with delay: " + TIMER_DELAY);
    gameThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while(true) {
            long startTime = System.currentTimeMillis();
            panel.gameTic();
            long deltaTime = System.currentTimeMillis() - startTime;
            if( deltaTime > 4 ) {
              System.err.println("Took " + deltaTime + " milliseconds to compute game tic");
            }
            if( deltaTime < TIMER_DELAY ) {
              Thread.sleep(TIMER_DELAY - deltaTime);
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
            long startTime = System.currentTimeMillis();
            panel.repaint();
            long deltaTime = System.currentTimeMillis() - startTime;
            if( deltaTime > 4 ) {
              System.err.println("Took " + deltaTime + " milliseconds to compute repaint");
            }
            if( deltaTime < REPAINT_DELAY ) {
              Thread.sleep(REPAINT_DELAY - deltaTime);
            }
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
